package vn.vnpt.vnptit.ecms.service.hatang;

import java.io.IOException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.vnptit.ecms.dto.hatang.*;
import vn.vnpt.vnptit.ecms.dto.idc.IDCRackRequestDTO;
import vn.vnpt.vnptit.ecms.dto.idc.IdcRackBatchValidateResponseDTO;
import vn.vnpt.vnptit.ecms.dto.idc.CauHinhPhanCungThietBiDtoIn;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucIDCService;
import com.google.gson.Gson;

@Service
public class ImportTaiNguyenService {
	
	@Autowired
	DbContext dbContext;

	@Autowired
	DanhMucIDCService danhMucIDCService;

	@Autowired
	CauHinhPhanCungThietBiService cauHinhPhanCungThietBiService;

	public Object validateDanhSachThietBi(List<ImportThietBiItemDto> deviceList) throws AppSqlException {
		if (deviceList == null || deviceList.isEmpty()) {
			return new HashMap<>();
		}

		String nguoiCn = AppRequestContext.getCurrent().getUserName();
		Number tinhId = AppRequestContext.getCurrent().getPhanVungID();
		Number nhanvienId = AppRequestContext.getCurrent().getNhanVienID();

		Map<String, Number> idcCache = new HashMap<>();
		Map<String, Number> rackCache = new HashMap<>();
		Map<String, Number> matsanCache = new HashMap<>();
		Map<String, Number> phongCache = new HashMap<>();
		Map<Number, Map<Integer, Number>> rackUnitCache = new HashMap<>();

		List<String> errorMessages = new ArrayList<>();
		List<Number> failedStt = new ArrayList<>();

		for (int i = 0; i < deviceList.size(); i++) {
			ImportThietBiItemDto item = deviceList.get(i);
			String rowPrefix = "Dòng " + (i + 1) + ": ";
			try {
				// 1. Resolve infrastructure IDs (IDC, Rack, Units)
				Map<String, Number> resolvedIds = resolveInfrastructure(item, idcCache, rackCache, matsanCache, phongCache, rackUnitCache);


				// 2. Execute DB-level validation (SP_IMPORT_THIETBI with p_only_check=1)
				executeImportStoredProcedure(item, resolvedIds, nguoiCn, nhanvienId, tinhId, 1);

			} catch (Exception e) {
				errorMessages.add(rowPrefix + getFriendlyErrorMessage(e));
				if (item.getStt() != null) {
					failedStt.add(item.getStt());
				}
			}
		}

		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", deviceList.size());
		result.put("errorCount", errorMessages.size());
		result.put("errorMessages", errorMessages);
		result.put("failedStt", failedStt);
		return result;
	}

	public Object importDanhSachThietBi(List<ImportThietBiItemDto> deviceList) throws AppSqlException {
		if (deviceList == null || deviceList.isEmpty()) {
			throw new AppSqlException("Danh sách thiết bị cần import không được rỗng.");
		}

		String nguoiCn = AppRequestContext.getCurrent().getUserName();
		Number tinhId = AppRequestContext.getCurrent().getPhanVungID();
		Number nhanvienId = AppRequestContext.getCurrent().getNhanVienID();

		Map<String, Number> idcCache = new HashMap<>(); 
		Map<String, Number> rackCache = new HashMap<>(); 
		Map<String, Number> matsanCache = new HashMap<>();
		Map<String, Number> phongCache = new HashMap<>();
		Map<Number, Map<Integer, Number>> rackUnitCache = new HashMap<>();

		List<String> errorMessages = new ArrayList<>();
		List<Number> failedStt = new ArrayList<>();
		List<Map<String, Object>> detailResults = new ArrayList<>();
		int successCount = 0;

		for (int i = 0; i < deviceList.size(); i++) {
			ImportThietBiItemDto item = deviceList.get(i);
			String rowPrefix = "Dòng " + (i + 1) + ": ";

			Map<String, Object> rowResult = new HashMap<>();
			rowResult.put("stt", item.getStt());
			rowResult.put("ten", item.getTen());
			rowResult.put("serial", item.getSerialNumber());

			try {
				// 1. Resolve infrastructure IDs (IDC, Rack, Units)
				Map<String, Number> resolvedIds = resolveInfrastructure(item, idcCache, rackCache, matsanCache, phongCache, rackUnitCache);

				// 2. Execute import stored procedure
				Number thietbiId = executeImportStoredProcedure(item, resolvedIds, nguoiCn, nhanvienId, tinhId, 0);

				if (thietbiId != null && thietbiId.longValue() > 0) {
					// 3. Hardware config for Server Blade (9181)
					if (item.getLoaithietbiId() != null && item.getLoaithietbiId().intValue() == 9181) {
						if (item.getSlKheBlade() != null && item.getSlKheBlade().intValue() > 0) {
							CauHinhPhanCungThietBiDtoIn hwDto = new CauHinhPhanCungThietBiDtoIn();
							hwDto.setThietbi_id(thietbiId);
							hwDto.setSl_kheblade(item.getSlKheBlade());
							cauHinhPhanCungThietBiService.capNhatCauHinhPhanCung(hwDto);
						}
					}
					successCount++;
					rowResult.put("success", true);
					rowResult.put("thietbi_id", thietbiId);
				} else {
					String msg = "Không tạo được thiết bị, không xác định được lỗi.";
					errorMessages.add(rowPrefix + msg);
					rowResult.put("success", false);
					rowResult.put("error", msg);
					if (item.getStt() != null) failedStt.add(item.getStt());
				}
			} catch (Exception e) {
				String msg = getFriendlyErrorMessage(e);
				errorMessages.add(rowPrefix + msg);
				rowResult.put("success", false);
				rowResult.put("error", msg);
				if (item.getStt() != null) {
					failedStt.add(item.getStt());
				}
			}
			detailResults.add(rowResult);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", deviceList.size());
		result.put("successCount", successCount);
		result.put("errorCount", errorMessages.size());
		result.put("errorMessages", errorMessages);
		result.put("failedStt", failedStt);
		return result;
	}

	/**
	 * Giải mã và kiểm tra ID của IDC, Rack và Units dựa trên tên tắt
	 */
	private Map<String, Number> resolveInfrastructure(
			ImportThietBiItemDto item,
			Map<String, Number> idcCache,
			Map<String, Number> rackCache,
			Map<String, Number> matsanCache,
			Map<String, Number> phongCache,
			Map<Number, Map<Integer, Number>> rackUnitCache) throws AppSqlException {
		
		item.parseAndSetUnitIds();
		
		String idcTenTat = item.getIdcId();
		String rackTenTat = item.getRackId();
		String roomTenTat = item.getPhongId();
		String matSanTenTat = item.getMatsanId();
		
		Map<String, Number> resolved = new HashMap<>();

		// 1. IDC Resolution (Mandatory)
		if (idcTenTat == null || idcTenTat.isEmpty()) {
			throw new AppSqlException("Mã IDC không được để trống.");
		}
		
		Number idcId = idcCache.get(idcTenTat);
		if (idcId == null) {
			idcId = getIdcIdByTenTat(idcTenTat);
			if (idcId == null) {
				throw new AppSqlException("Không tìm thấy IDC có mã tắt '" + idcTenTat + "'");
			}
			idcCache.put(idcTenTat, idcId);
		}
		resolved.put("idcId", idcId);

		// Initialize all location fields to null
		resolved.put("matsanId", null);
		resolved.put("phongId", null);
		resolved.put("rackId", null);

		// 2. Tầng / Mặt sàn (Nếu có)
		if (matSanTenTat != null && !matSanTenTat.isEmpty()) {
			String matSanCacheKey = idcId + "_" + matSanTenTat;
			Number matSanId = matsanCache.get(matSanCacheKey);
			if (matSanId == null) {
				matSanId = getMatsanIdByTenTat(matSanTenTat, idcId.toString());
				if (matSanId == null) {
					throw new AppSqlException("Không tìm thấy Mặt sàn có mã tắt '" + matSanTenTat + "' tại IDC '" + idcTenTat + "'");
				}
				matsanCache.put(matSanCacheKey, matSanId);
			}
			resolved.put("matsanId", matSanId);
		}

		// 3. Phòng / Zone (Nếu có)
		if (roomTenTat != null && !roomTenTat.isEmpty()) {
			String phongCacheKey = idcId + "_" + roomTenTat;
			Number phongId = phongCache.get(phongCacheKey);
			if (phongId == null) {
				phongId = getPhongIdByTenTat(roomTenTat, idcId.toString());
				if (phongId == null) {
					throw new AppSqlException("Không tìm thấy Phòng/Zone có mã tắt '" + roomTenTat + "' tại IDC '" + idcTenTat + "'");
				}
				phongCache.put(phongCacheKey, phongId);
			}
			resolved.put("phongId", phongId);
		}

		// 4. Rack / RackUnit (Nếu có)
		if (rackTenTat != null && !rackTenTat.isEmpty()) {
			// Cache key bao gồm phongTenTat để tránh trùng rack trong cùng IDC nhưng khác phòng
			String phongSuffix = (roomTenTat != null && !roomTenTat.isEmpty()) ? "_" + roomTenTat : "";
			String rackCacheKey = idcId + "_" + rackTenTat + phongSuffix;
			Number rackId = rackCache.get(rackCacheKey);
			if (rackId == null) {
				rackId = getRackIdByTenTat(rackTenTat, idcId.toString(), roomTenTat);
				if (rackId == null) {
					String errorMsg = "Không tìm thấy Rack có mã tắt '" + rackTenTat + "' tại IDC '" + idcTenTat + "'";
					if (roomTenTat != null && !roomTenTat.isEmpty()) {
						errorMsg += " và Phòng/Zone '" + roomTenTat + "'";
					}
					throw new AppSqlException(errorMsg);
				}
				rackCache.put(rackCacheKey, rackId);
			}
			resolved.put("rackId", rackId);

			// Unit Resolution within Rack
			if (item.getUnitIndex() != null && !item.getUnitIndex().isEmpty()) {
				Map<Integer, Number> unitMap = rackUnitCache.computeIfAbsent(rackId, rId -> {
					try {
						return getUnitMapByRackId(rId.toString());
					} catch (AppSqlException e) {
						return new HashMap<>();
					}
				});

				Number actualUpUnitId = (item.getUpUnitId() != null) ? unitMap.get(item.getUpUnitId().intValue()) : null;
				Number actualDownUnitId = (item.getDownUnitId() != null) ? unitMap.get(item.getDownUnitId().intValue()) : null;

				// Validate: both units must exist if specified in index
				if (item.getUpUnitId() != null && actualUpUnitId == null) {
					throw new AppSqlException("Không tìm thấy Unit " + item.getUpUnitId() + " trong Rack '" + rackTenTat + "'");
				}
				if (item.getDownUnitId() != null && actualDownUnitId == null) {
					throw new AppSqlException("Không tìm thấy Unit " + item.getDownUnitId() + " trong Rack '" + rackTenTat + "'");
				}

				item.setUpUnitId(actualUpUnitId);
				item.setDownUnitId(actualDownUnitId);
			}
		}

		return resolved;
	}

	/**
	 * Thực thi database stored procedure SP_IMPORT_THIETBI
	 */
	private Number executeImportStoredProcedure(
			ImportThietBiItemDto item,
			Map<String, Number> resolvedIds,
			String nguoiCn,
			Number nhanvienId,
			Number tinhId,
			int onlyCheck) throws AppSqlException {
		
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_loaithietbi_id", item.getLoaithietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hieu_luc", item.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_so_the_ts", item.getSoTheTs(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_project_code", item.getProjectCode(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ma_ts", item.getMaTs(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ten", item.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_mangtbi_id", item.getMangtbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_phanloaitb_id", item.getPhanloaitbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_serial_number", item.getSerialNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_model", item.getModel(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_part_number", item.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_trang_thai_vh_id", item.getTrangThaiVhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hangsx_id", item.getHangsxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_namsx", item.getNamsx(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cs_dinhdanh", item.getCsDinhdanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_da_dinhdanh", item.getDaDinhdanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dd_dinhdanh", item.getDdDinhdanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_ndien", item.getLoaiNdien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ngay_lapdat", item.getNgayLapdat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ngay_sudung", item.getNgaySudung(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_cpu", item.getCpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ram", item.getRam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hdd", item.getHdd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_fan", item.getFan(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ssd", item.getSsd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nvme", item.getNvme(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_gpu", item.getGpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sl_congnguon", item.getSlCongnguon(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sl_congdl", item.getSlCongdl(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sl_congdkh", item.getSlCongdkh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sl_congnguon_vao", item.getSlCongnguonVao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sl_congnguon_ra", item.getSlCongnguonRa(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_khong_gian_lap_dat", item.getKhongGianLapDat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_idc_id", resolvedIds.get("idcId"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rack_id", resolvedIds.get("rackId"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_matsan_id", resolvedIds.get("matsanId"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_phong_id", resolvedIds.get("phongId"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_up_unit_id", item.getUpUnitId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_down_unit_id", item.getDownUnitId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_nhanvien_id", nhanvienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_tinh_id", tinhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mota", item.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_hs_cosphi", item.getHsCosphi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_tyle_sd", item.getTyleSd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaimay", item.getLoaimay(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", item.getToadoX(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", item.getToadoY(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mat_rackunit", item.getMatRackunit(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_canh_rackunit", item.getCanhRackunit(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieu_ld", item.getKieuLd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_donviqly_id", item.getDonviqlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ma_vtu", item.getMaVtu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_nguoiqly_name", item.getNguoiqlyName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ma_tbi_sv_blade", item.getMaTbiServerBlade(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ma_tbi_tu_dia", item.getMaTbiTuDia(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ma_tbi_network", item.getMaTbiNetwork(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_chuquan_id", item.getChuquanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_only_check", onlyCheck, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

		dbContext.executeStoredProcedure("ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_IMPORT_THIETBI", params);

		return (Number) params.stream()
				.filter(p -> "p_result".equalsIgnoreCase(p.getName()))
				.findFirst()
				.map(SqlParameter::getOutValue)
				.orElse(null);
	}

	/**
	 * Chuyển đổi exception thành thông báo lỗi thân thiện với người dùng
	 */
	private String getFriendlyErrorMessage(Exception e) {
		if (e instanceof AppSqlException) {
			AppSqlException appEx = (AppSqlException) e;
			String appMessage = appEx.getAppMessage();
			if (appMessage != null && !appMessage.isEmpty()) {
				return appMessage;
			}
		}

		Throwable cause = e;
		while (cause != null) {
			if (cause instanceof java.sql.SQLException) {
				java.sql.SQLException sqlEx = (java.sql.SQLException) cause;
				int errorCode = sqlEx.getErrorCode();
				switch (errorCode) {
					case 20102: return "Khoảng Unit lắp đặt đã được sử dụng";
					case 20101: return "Unit lắp đặt không tồn tại";
					case 20403: return "Không có quyền import thiết bị vào IDC này";
					default:
						AppSqlException appSqlEx = new AppSqlException(sqlEx);
						String msg = appSqlEx.getAppMessage();
						if (msg != null && !msg.isEmpty()) return msg;
				}
				break;
			}
			cause = cause.getCause();
		}
		
		return e.getMessage() != null ? e.getMessage() : "Lỗi không xác định khi import";
	}

	/**
	 * Lấy IDC ID từ tên tắt
	 * Gọi danh mục IDC-BY-TENTAT với tham số 1 là tên tắt IDC (ví dụ: "NTL")
	 * Trả về ID
	 */
	private Number getIdcIdByTenTat(String idcTenTat) throws AppSqlException {
		Object result = danhMucIDCService.getDataDanhMuc("IDC-BY-TENTAT", idcTenTat, null);
		
		if (result instanceof List) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = (List<Map<String, Object>>) result;
			if (!items.isEmpty()) {
				Map<String, Object> item = items.get(0);
				return (Number) item.get("ID");
			}
		}
		
		return null;
	}
	
	/**
	 * Lấy Rack ID từ tên tắt rack và IDC ID, có thể lọc theo phòng/zone nếu có trùng tên rack.
	 * Gọi danh mục IDC-RACK-BY-TENTAT với:
	 *   - tham số 1: tên tắt rack (ví dụ: "BV52")
	 *   - tham số 2: IDC ID
	 * Nếu có phongTenTat, lọc kết quả theo PHONG_ID tương ứng để chọn đúng rack.
	 */
	private Number getRackIdByTenTat(String rackTenTat, String idcId, String phongTenTat) throws AppSqlException {
		Object result = danhMucIDCService.getDataDanhMuc("IDC-RACK-BY-TENTAT", rackTenTat, idcId);
		
		if (result instanceof List) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = (List<Map<String, Object>>) result;
			if (!items.isEmpty()) {
				// Nếu CÓ truyền vào tên Phòng -> Check nghiêm ngặt, bắt buộc phải nằm trong phòng này!
				if (phongTenTat != null && !phongTenTat.trim().isEmpty()) {
					Number resolvedPhongId = getPhongIdByTenTat(phongTenTat, idcId);
					if (resolvedPhongId != null) {
						for (Map<String, Object> item : items) {
							Number itemPhongId = (Number) item.get("PHONG_ID");
							if (itemPhongId != null && itemPhongId.longValue() == resolvedPhongId.longValue()) {
								return (Number) item.get("ID");
							}
						}
					}
					// Có truyền Phòng nhưng không khớp Rack nào trong DB -> Rack ở vị trí này Không Tồn Tại!
					return null;
				}
				
				// Nếu KHÔNG truyền Phòng (mặc định) -> Trả về kết quả đầu tiên tìm thấy
				return (Number) items.get(0).get("ID");
			}
		}
		
		return null;
	}

	/**
	 * Lấy danh sách unit của rack và build map: unit_index -> unit_id
	 * Gọi danh mục IDC-RACK-UNIT-BY-RACK với tham số 1 là Rack ID
	 */
	/**
	 * Response format từ IDC-RACK-UNIT-BY-RACK:
	 * [{"ID": 914858, "TEN": "BV52_U_1", "VITRI": 1}, {"ID": 914857, "TEN": "BV52_U_2", "VITRI": 2}, ...]
	 */
	private Map<Integer, Number> getUnitMapByRackId(String rackId) throws AppSqlException {
		Map<Integer, Number> unitMap = new HashMap<>();
		
		Object result = danhMucIDCService.getDataDanhMuc("IDC-RACK-UNIT-BY-RACK", rackId, null);
		
		if (result instanceof List) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> units = (List<Map<String, Object>>) result;
			for (Map<String, Object> unit : units) {
				Number unitId = (Number) unit.get("ID");
				Number vitri = (Number) unit.get("VITRI");
				
				if (unitId != null && vitri != null) {
					unitMap.put(vitri.intValue(), unitId);
				}
			}
		}
		
		return unitMap;
	}

	private Number getMatsanIdByTenTat(String tenTat, String idcId) throws AppSqlException {
		Object result = danhMucIDCService.getDataDanhMuc("IDC-MATSAN-BY-TENTAT", tenTat, idcId);
		if (result instanceof List) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = (List<Map<String, Object>>) result;
			if (!items.isEmpty()) {
				Map<String, Object> item = items.get(0);
				return (Number) item.get("ID");
			}
		}
		return null;
	}

	private Number getPhongIdByTenTat(String tenTat, String idcId) throws AppSqlException {
		Object result = danhMucIDCService.getDataDanhMuc("IDC-PHONG-BY-TENTAT", tenTat, idcId);
		if (result instanceof List) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = (List<Map<String, Object>>) result;
			if (!items.isEmpty()) {
				Map<String, Object> item = items.get(0);
				return (Number) item.get("ID");
			}
		}
		return null;
	}

	public IdcRackBatchValidateResponseDTO validateIdcRack(List<IDCRackRequestDTO> dtos) throws AppSqlException {
		Map<String, String> ERROR_MESSAGE_MAP = getErrorMessageMap();

		int errorCount = 0;
		StringBuilder errorMessage = new StringBuilder();
		ArrayList<Map<String, Object>> response = new ArrayList<>();

		//Bước 1: check trùng lặp tên rack trong dto trước
		Map<String, Number> taiSanIdMap = new HashMap<>();
		Map<String, Number> rackShortNameMap = new HashMap<>();
		Boolean hasValid1 = false;

		for (IDCRackRequestDTO dto : dtos) {
			int errorCountForDto = errorCount;
			String taiSanId = dto.getId() == null ? "" : dto.getId().trim().toLowerCase();
			String shortName = dto.getMa_tb() == null ? "" : dto.getMa_tb().trim().toLowerCase();

			if (!taiSanId.isEmpty()) {
				if (taiSanIdMap.containsKey(taiSanId)) {
					errorCount++;

					if (errorMessage.length() > 0) errorMessage.append("\n");
					errorMessage
							.append("Dòng ")
							.append(dto.getStt())
							.append(": ID tài sản đã tồn tại trong danh sách.");
				} else {
					taiSanIdMap.put(taiSanId, dto.getStt());
				}
			}
			if (!shortName.isEmpty()) {

				String key;
				String message;

				if (dto.getPhong() != null && !dto.getPhong().trim().isEmpty()) {
					key = shortName + "_PHONG_" + dto.getPhong().toUpperCase();
					message = "Tên tắt cùng Phòng đã tồn tại trong danh sách";
				} else if (dto.getIdc() != null && !dto.getIdc().trim().isEmpty()) {
					key = shortName + "_IDC_" + dto.getIdc().toUpperCase();
					message = "Tên tắt cùng IDC đã tồn tại trong danh sách";
				} else {
					key = shortName;
					message = "Tên tắt đã tồn tại trong danh sách";
				}

				if (rackShortNameMap.containsKey(key)) {
					errorCount++;

					if (errorMessage.length() > 0) errorMessage.append("\n");
					errorMessage
							.append("Dòng ")
							.append(dto.getStt())
							.append(": ")
							.append(message);
				} else {
					rackShortNameMap.put(key, dto.getStt());
				}
			}
			hasValid1 = errorCount == errorCountForDto;
		}

		Boolean hasValid2 = false;
		//Bước 2: tiếp tục kiểm tra với db
		for (IDCRackRequestDTO dto : dtos) {
			try {
				ArrayList<SqlParameter> params = new ArrayList<>();
				params.add(new SqlParameter("p_stt", dto.getStt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
				params.add(new SqlParameter("p_taisan_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
				params.add(new SqlParameter("p_ten_rack", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_tentat", dto.getMa_tb(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
				params.add(new SqlParameter("p_loairack_id", dto.getLoairack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
				params.add(new SqlParameter("p_hang_sx", dto.getHang_sx(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_donvi_idc", dto.getDonvi_idc(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_idc", dto.getIdc(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_toanha", dto.getToanha(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_toado_matsan", dto.getToado_matsan(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_matsan", dto.getMatsan(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_phong", dto.getPhong(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_donvi_sohuu", dto.getDonvi_sohuu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_donvi_ql", dto.getDonvi_ql(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_phong_ql", dto.getPhong_ql(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_nhanvien_qls", dto.getNhanvien_qls(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
				params.add(new SqlParameter("p_error_codes", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
				params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
				dbContext.executeStoredProcedure("ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_VALIDATE_IDC_RACK", params);

				String errorCodes = null;
				List<Map<String, Object>> rows = null;

				for (SqlParameter param : params) {
					if ("p_error_codes".equals(param.getName()) && param.getOutValue() != null) {
						errorCodes = String.valueOf(param.getOutValue());
					}
					if ("p_res".equals(param.getName()) && param.getOutValue() != null) {
						rows = (List<Map<String, Object>>) param.getOutValue();
					}
				}

				if (errorCodes != null && !errorCodes.isEmpty()) {
					String[] codes = errorCodes.split(",");

					for (String code : codes) {

						String key = code;
						String extra = "";

						if (code.contains(":")) {
							key = code.substring(0, code.indexOf(":"));
							extra = code.substring(code.indexOf(":") + 1);
						}

						String msg = ERROR_MESSAGE_MAP.getOrDefault(key, "Dữ liệu không hợp lệ");

						if ("NHANVIEN_QL_NOT_VALID".equals(key)) {
							msg += " (" + extra + ")";
						}

						if ("IDC_NOT_VALID".equals(key)){
							if (!dto.getToanha().isEmpty()) {
								msg = "Không tìm thấy Tòa nhà " + dto.getToanha() + " tại IDC " + dto.getIdc();
							} else if (!dto.getMatsan().isEmpty()) {
								msg = "Không tìm thấy Mặt sàn " + dto.getMatsan() + " tại IDC " + dto.getIdc();
							} else if (!dto.getPhong().isEmpty()) {
								msg = "Không tìm thấy Phòng " + dto.getPhong() + " tại IDC " + dto.getIdc();
							} else {
								msg = "Không tìm thấy IDC " + dto.getIdc() + " trong hệ thống";
							}
						}
						if ("TOANHA_NOT_VALID".equals(key)){
							if (!dto.getMatsan().isEmpty()) {
								msg = "Không tìm thấy Mặt sàn " + dto.getMatsan() + " tại Tòa nhà " + dto.getToanha();
							} else if (!dto.getPhong().isEmpty()) {
								msg = "Không tìm thấy Phòng " + dto.getPhong() + " tại Tòa nhà " + dto.getToanha();
							} else {
								msg = "Không tìm thấy Tòa nhà " + dto.getToanha() + " trong hệ thống";
							}
						}
						if ("MATSAN_NOT_VALID".equals(key)){
							if (!dto.getPhong().isEmpty()) {
								msg = "Không tìm thấy Phòng " + dto.getPhong() + " tại Mặt sàn " + dto.getMatsan();
							} else {
								msg = "Không tìm thấy Mặt sàn " + dto.getMatsan() + " trong hệ thống";
							}
						}
						if ("PHONG_NOT_VALID".equals(key)){
							msg = "Không tìm thấy Phòng " + dto.getPhong() + " trong hệ thống";
						}

						errorCount++;
						if (errorMessage.length() > 0) errorMessage.append("\n");
						errorMessage.append("Dòng ")
								.append(dto.getStt())
								.append(": ")
								.append(msg);
					}
				} else {
					hasValid2 = true;
				}

				Map<String, Object> row = new HashMap<>();
				if (rows != null && !rows.isEmpty()) {
					row = rows.get(0);
				}
				response.add(row);
			} catch (AppSqlException e) {
				errorCount++;

				if (errorMessage.length() > 0) errorMessage.append("\n");

				errorMessage.append("Dòng ")
						.append(dto.getStt())
						.append(": Lỗi hệ thống khi kiểm tra dữ liệu");
			}
		}
		try {
			Set<Number> errorSttSet = extractErrorStt(errorMessage.toString());

			response.removeIf(row -> {
				Object stt = row.get("STT");
				if (stt == null) return false;

				if (stt instanceof Number) {
					return errorSttSet.contains(((Number) stt).intValue());
				}
				return errorSttSet.contains(Integer.parseInt(stt.toString()));
			});
			return new IdcRackBatchValidateResponseDTO(errorCount, errorMessage.toString(), response, hasValid1 && hasValid2);
		} catch (IOException e) {
			throw new AppSqlException(e);
		}
	}

	public Map<String, Object> batchInsertIdcRack(List<IDCRackRequestDTO> dtos) throws AppSqlException {
		IdcRackBatchValidateResponseDTO validateResult = validateIdcRack(dtos);

		Set<Number> errorSttSet = extractErrorStt(validateResult.getErrorMessage());
		List<Number> successStt = new ArrayList<>();

		for (IDCRackRequestDTO dto : dtos) {

			if (errorSttSet.contains(dto.getStt())) {
				continue;
			}

			String nguoiCn = AppRequestContext.getCurrent().getUserName();
			Number tinhId = AppRequestContext.getCurrent().getPhanVungID();

			ArrayList<SqlParameter> params = new ArrayList<>();
			params.add(new SqlParameter("p_the_ts", dto.getThe_ts(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
			params.add(new SqlParameter("p_ma_duan", dto.getMa_duan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
			params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
			params.add(new SqlParameter("p_ten_rack", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
			params.add(new SqlParameter("p_loairack_id", dto.getLoairack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_ngay_sd", dto.getNgay_sd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
			params.add(new SqlParameter("p_tentat", dto.getMa_tb(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
			params.add(new SqlParameter("p_hangsx_id", dto.getHangsx_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_idc_id", dto.getIdc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_toadomatsan_id", dto.getToadomatsan_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_phong_id", dto.getPhong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_toado_x", dto.getToado_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_toado_y", dto.getToado_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_donvisohuu_id", dto.getDonvisohuu_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_donviql_id", dto.getDonviql_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_phongql_id", dto.getPhongql_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_nhanvienql_ids", dto.getNhanvienql_ids(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
			params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
			params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
			params.add(new SqlParameter("p_tinh_id", tinhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

			Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_INSERT_IDC_RACK_FOR_BATCH", params);

			if (result != null && result.containsKey("RACK_ID") && result.get("RACK_ID") != null) {
				successStt.add(dto.getStt());
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("SUCCESS_ITEMS_STT", successStt);
		response.put("VALIDATION_ERRORS", validateResult);

		return response;
	}

	private Map<String, String> getErrorMessageMap() {
		Map<String, String> ERROR_MESSAGE_MAP = new HashMap<>();
		ERROR_MESSAGE_MAP.put("DUPLICATE_TAISAN_ID", "ID tài sản đã tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("DUPLICATE_MA_RACK", "Tên tắt đã tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("DUPLICATE_MA_RACK_PHONG", "Tên tắt đã tồn tại trong cùng Phòng");
		ERROR_MESSAGE_MAP.put("DUPLICATE_MA_RACK_IDC", "Tên tắt đã tồn tại trong cùng IDC");
		ERROR_MESSAGE_MAP.put("IDC_NOT_VALID", "Thông tin vị trí không hợp lệ");
		ERROR_MESSAGE_MAP.put("TOADO_MATSAN_NOT_VALID", "Tọa độ mặt sàn không tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("HANG_SX_NOT_VALID", "Hãng sản xuất không tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("DONVI_SOHUU_NOT_VALID", "Đơn vị sở hữu không tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("DONVI_QL_NOT_VALID", "Đơn vị quản lý không tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("PHONG_QL_NOT_VALID", "Phòng quản lý không tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("NHANVIEN_QL_NOT_VALID", "Nhân viên quản lý không tồn tại trong hệ thống");
		ERROR_MESSAGE_MAP.put("LOAI_RACK_NOT_VALID", "Không tìm thấy loại rack trong hệ thống");
		return ERROR_MESSAGE_MAP;
	}

	private Set<Number> extractErrorStt(String errorMessage) {

		Set<Number> result = new HashSet<>();

		if (errorMessage == null || errorMessage.isEmpty()) {
			return result;
		}

		Pattern pattern = Pattern.compile("Dòng\\s+(\\d+)");
		Matcher matcher = pattern.matcher(errorMessage);

		while (matcher.find()) {
			result.add(Integer.parseInt(matcher.group(1)));
		}

		return result;
	}

	    public Object validateImportKetNoiBatch(List<ImportKetNoiItemDto> list) throws AppSqlException {

        String json = new Gson().toJson(list);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, Types.CLOB));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_VALIDATE_KETNOI_BATCH",
                params
        );

        List<Map<String, Object>> dbResult = null;

        for (SqlParameter p : params) {
            if ("p_rs".equals(p.getName())) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> temp = (List<Map<String, Object>>) p.getOutValue();
                dbResult = temp;
            }
        }

        List<Map<String, Object>> results = new ArrayList<>();

        if (dbResult != null) {
            for (Map<String, Object> row : dbResult) {

                Map<String, Object> r = new HashMap<>();
                r.put("stt", row.get("STT"));

                String err = (String) row.get("ERROR_MESSAGE");

                if (err == null) {
                    r.put("isValid", true);
                    r.put("messages", new ArrayList<>());
                } else {
                    r.put("isValid", false);
                    r.put("messages", Arrays.asList(err));
                }

                results.add(r);
            }
        }

        return results;
    }

    public Object importDanhSachKetNoi(List<ImportKetNoiItemDto> ketNoiList) throws AppSqlException {
		if (ketNoiList == null || ketNoiList.isEmpty()) {
			throw new AppSqlException("Danh sách kết nối cần import không được rỗng.");
		}

		List<String> errorMessages = new ArrayList<>();
		int successCount = 0;

		for (int i = 0; i < ketNoiList.size(); i++) {
			ImportKetNoiItemDto item = ketNoiList.get(i);
			String rowPrefix = "Dòng " + (i + 1) + ": ";
			try {
				List<ImportKetNoiItemDto> singleList = Collections.singletonList(item);
				String json = JsonConvert.serialize(singleList);
				executeImportKetNoiStoredProcedure(json);
				successCount++;
			} catch (Exception e) {
				errorMessages.add(rowPrefix + getFriendlyErrorMessage(e));
			}
		}

		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", ketNoiList.size());
		result.put("successCount", successCount);
		result.put("errorCount", errorMessages.size());
		result.put("errorMessages", errorMessages);
		result.put("failedStt", new ArrayList<>());
		return result;
	}

	    private void executeImportKetNoiStoredProcedure(String json) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.import_ketnoi_idc", params);
    }

    //  Import IDC, toa nha, mat san, phong

    public Object validateImportIdcBatch(List<IdcImportDTO> list) throws AppSqlException {

        // convert list -> json
        String json = new Gson().toJson(list);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, Types.CLOB));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_VALIDATE_IDC_BATCH",
                params
        );

        List<Map<String, Object>> dbResult = null;

        for (SqlParameter p : params) {
            if ("p_rs".equals(p.getName())) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> temp = (List<Map<String, Object>>) p.getOutValue();
                dbResult = temp;
            }
        }

        // Map lại theo format FE đang cần
        List<Map<String, Object>> results = new ArrayList<>();

        if (dbResult != null) {
            for (Map<String, Object> row : dbResult) {

                Map<String, Object> r = new HashMap<>();
                r.put("stt", row.get("STT"));

                String err = (String) row.get("ERROR_MESSAGE");

                if (err == null) {
                    r.put("isValid", true);
                    r.put("messages", new ArrayList<>());
                } else {
                    r.put("isValid", false);
                    r.put("messages", Arrays.asList(err));
                }

                results.add(r);
            }
        }

        return results;
    }


    public Object importIdc(List<IdcImportDTO> list) throws AppSqlException {

        List<Map<String, Object>> results = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            IdcImportDTO dto = list.get(i);

            List<SqlParameter> params = new ArrayList<>();

            params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat", dto.getTentat(), Types.VARCHAR));
            params.add(new SqlParameter("p_dia_chi", dto.getDiaChi(), Types.NVARCHAR));
            params.add(new SqlParameter("p_ten_donvi", dto.getDonviQuanly(), Types.NVARCHAR));

            params.add(new SqlParameter(
                    "p_trangthai_vh",
                    String.valueOf(dto.getTrangthaiVh()),
                    Types.NVARCHAR
            ));

            params.add(new SqlParameter("p_new_id", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_message", Types.NVARCHAR,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_success", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            dbContext.executeStoredProcedure(
                    "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_IMPORT_IDC_FULL",
                    params
            );

            Map<String, Object> result = new HashMap<>();
            result.put("stt", dto.getStt());
            result.put("index", i);

            for (SqlParameter p : params) {
                if ("p_new_id".equals(p.getName()))
                    result.put("newId", p.getOutValue());

                if ("p_message".equals(p.getName()))
                    result.put("message", p.getOutValue());

                if ("p_success".equals(p.getName()))
                    result.put("success",
                            "1".equals(String.valueOf(p.getOutValue())));
            }

            results.add(result);
        }

        return results;
    }


    public Object validateImportToanhaBatch(List<ToanhaImportDTO> list) throws AppSqlException {

        String json = new Gson().toJson(list);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, Types.CLOB));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_VALIDATE_TOANHA_BATCH",
                params
        );

        List<Map<String, Object>> dbResult = null;

        for (SqlParameter p : params) {
            if ("p_rs".equals(p.getName())) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> temp = (List<Map<String, Object>>) p.getOutValue();
                dbResult = temp;
            }
        }

        List<Map<String, Object>> results = new ArrayList<>();

        if (dbResult != null) {
            for (Map<String, Object> row : dbResult) {

                Map<String, Object> r = new HashMap<>();
                r.put("stt", row.get("STT"));

                String err = (String) row.get("ERROR_MESSAGE");

                if (err == null) {
                    r.put("isValid", true);
                    r.put("messages", new ArrayList<>());
                } else {
                    r.put("isValid", false);
                    r.put("messages", Arrays.asList(err));
                }

                results.add(r);
            }
        }

        return results;
    }


    public Object importToanha(List<ToanhaImportDTO> list) throws AppSqlException {

        List<Map<String, Object>> results = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            ToanhaImportDTO dto = list.get(i);

            List<SqlParameter> params = new ArrayList<>();

            params.add(new SqlParameter("p_tentat_idc", dto.getTentatIdc(), Types.NVARCHAR));
            params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat", dto.getTentat(), Types.VARCHAR));
            params.add(new SqlParameter("p_ten_mucdich", dto.getTenMucdich(), Types.NVARCHAR));
            params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));

            params.add(new SqlParameter("p_new_id", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_message", Types.NVARCHAR,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_success", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            dbContext.executeStoredProcedure(
                    "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_IMPORT_TOANHA_FULL",
                    params
            );

            Map<String, Object> result = new HashMap<>();
            result.put("stt", dto.getStt());
            result.put("index", i);

            for (SqlParameter p : params) {

                if ("p_new_id".equals(p.getName()))
                    result.put("newId", p.getOutValue());

                if ("p_message".equals(p.getName()))
                    result.put("message", p.getOutValue());

                if ("p_success".equals(p.getName()))
                    result.put(
                            "success",
                            "1".equals(String.valueOf(p.getOutValue()))
                    );
            }

            results.add(result);
        }

        return results;
    }

    public Object validateImportMatsanBatch(List<MatsanImportDTO> list)
            throws AppSqlException {
        String json = new Gson().toJson(list);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, Types.CLOB));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_VALIDATE_MATSAN_BATCH",
                params
        );

        List<Map<String, Object>> dbResult = null;

        for (SqlParameter p : params) {
            if ("p_rs".equals(p.getName())) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> temp = (List<Map<String, Object>>) p.getOutValue();
                dbResult = temp;
            }
        }

        List<Map<String, Object>> results = new ArrayList<>();

        if (dbResult != null) {
            for (Map<String, Object> row : dbResult) {

                Map<String, Object> r = new HashMap<>();
                r.put("stt", row.get("STT"));

                String err = (String) row.get("ERROR_MESSAGE");

                if (err == null) {
                    r.put("isValid", true);
                    r.put("messages", new ArrayList<>());
                } else {
                    r.put("isValid", false);
                    r.put("messages", Arrays.asList(err));
                }

                results.add(r);
            }
        }

        return results;
    }


    public Object importMatsan(List<MatsanImportDTO> list) throws AppSqlException {

        List<Map<String, Object>> results = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            MatsanImportDTO dto = list.get(i);

            List<SqlParameter> params = new ArrayList<>();

            params.add(new SqlParameter("p_tentat_idc", dto.getTentatIdc(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat_toanha", dto.getTentatToanha(), Types.NVARCHAR));

            params.add(new SqlParameter("p_ten", dto.getTenMatsan(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat", dto.getTentat(), Types.VARCHAR));

            Double dienTich = null;
            try {
                if (dto.getDienTich() != null)
                    dienTich = Double.parseDouble(dto.getDienTich().toString());
            } catch (Exception ex) {
            }

            params.add(new SqlParameter("p_dien_tich", dienTich, Types.NUMERIC));

            params.add(new SqlParameter("p_ten_trangthai", dto.getTenTrangthai(), Types.NVARCHAR));
            params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));

            params.add(new SqlParameter("p_new_id", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_message", Types.NVARCHAR,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_success", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            dbContext.executeStoredProcedure(
                    "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_IMPORT_MATSAN_FULL",
                    params
            );

            Map<String, Object> result = new HashMap<>();
            result.put("stt", dto.getStt());
            result.put("index", i);

            for (SqlParameter p : params) {

                if ("p_new_id".equals(p.getName()))
                    result.put("newId", p.getOutValue());

                if ("p_message".equals(p.getName()))
                    result.put("message", p.getOutValue());

                if ("p_success".equals(p.getName()))
                    result.put(
                            "success",
                            "1".equals(String.valueOf(p.getOutValue()))
                    );
            }

            results.add(result);
        }

        return results;
    }

    public Object validateImportPhongBatch(List<PhongImportDTO> list)
            throws AppSqlException {

        String json = new Gson().toJson(list);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, Types.CLOB));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_VALIDATE_PHONG_BATCH",
                params
        );

        List<Map<String, Object>> dbResult = null;

        for (SqlParameter p : params) {
            if ("p_rs".equals(p.getName())) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> temp = (List<Map<String, Object>>) p.getOutValue();
                dbResult = temp;
            }
        }

        List<Map<String, Object>> results = new ArrayList<>();

        if (dbResult != null) {
            for (Map<String, Object> row : dbResult) {

                Map<String, Object> r = new HashMap<>();
                r.put("stt", row.get("STT"));

                String err = (String) row.get("ERROR_MESSAGE");

                if (err == null) {
                    r.put("isValid", true);
                    r.put("messages", new ArrayList<>());
                } else {
                    r.put("isValid", false);
                    r.put("messages", Arrays.asList(err));
                }

                results.add(r);
            }
        }

        return results;
    }


    public Object importPhong(List<PhongImportDTO> list) throws AppSqlException {

        List<Map<String, Object>> results = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            PhongImportDTO dto = list.get(i);

            List<SqlParameter> params = new ArrayList<>();

            params.add(new SqlParameter("p_tentat_idc", dto.getTentatIdc(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat_toanha", dto.getTentatToanha(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat_matsan", dto.getTentatMatsan(), Types.NVARCHAR));

            params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat", dto.getTentat(), Types.VARCHAR));

            Double dienTich = null;
            try {
                if (dto.getDienTich() != null)
                    dienTich = Double.parseDouble(dto.getDienTich().toString());
            } catch (Exception ex) {
            }

            params.add(new SqlParameter("p_dien_tich", dienTich, Types.NUMERIC));

            params.add(new SqlParameter("p_ten_mucdich", dto.getTenMucdich(), Types.NVARCHAR));
            params.add(new SqlParameter("p_ten_trangthai", dto.getTenTrangthai(), Types.NVARCHAR));

            params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));

            params.add(new SqlParameter("p_new_id", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_message", Types.NVARCHAR,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            params.add(new SqlParameter("p_success", Types.NUMERIC,
                    String.valueOf(SqlParameter.ParameterDirection.OUTPUT)));

            dbContext.executeStoredProcedure(
                    "ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_IMPORT_PHONG_FULL",
                    params
            );

            Map<String, Object> result = new HashMap<>();
            result.put("stt", dto.getStt());
            result.put("index", i);

            for (SqlParameter p : params) {

                if ("p_new_id".equals(p.getName()))
                    result.put("newId", p.getOutValue());

                if ("p_message".equals(p.getName()))
                    result.put("message", p.getOutValue());

                if ("p_success".equals(p.getName()))
                    result.put(
                            "success",
                            "1".equals(String.valueOf(p.getOutValue()))
                    );
            }

            results.add(result);
        }

        return results;
    }

    // ==========================================================
    // PRE-PROCESS KẾT NỐI: Gán tên cho port trước khi import
    // ==========================================================

    /**
     * Gán tên port cho các port chưa có tên trước khi import kết nối:
     * 1. Tìm thiết bị dựa trên mã truyền vào (SO_THE_TS)
     * 2. Lấy danh sách port theo loại port + ID thiết bị, sắp xếp theo SOTHUTU
     * 3. Gán tên vào cho những port chưa có tên (TEN IS NULL), ưu tiên theo SOTHUTU
     */
    public Object preprocessKetNoiPorts(List<ImportKetNoiItemDto> list) throws AppSqlException {
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }

        // Bước 1: Nhóm tên port theo "thiết_bị|loại_cổng" (cả nguồn và đích)
        Map<String, List<String>> devicePortMap = new LinkedHashMap<>();

        for (ImportKetNoiItemDto item : list) {
            // Nguồn
            if (item.getMaTbNguon() != null && item.getMaTbNguon().trim().length() > 0) {
                String keyNguon = item.getMaTbNguon().trim() + "|" + item.getLoaiCongNguonId();
                if (!devicePortMap.containsKey(keyNguon)) {
                    devicePortMap.put(keyNguon, new ArrayList<String>());
                }
                if (item.getTenPortNguon() != null && item.getTenPortNguon().trim().length() > 0) {
                    String tenPort = item.getTenPortNguon().trim();
                    if (!devicePortMap.get(keyNguon).contains(tenPort)) {
                        devicePortMap.get(keyNguon).add(tenPort);
                    }
                }
            }

            // Đích
            if (item.getMaTbDich() != null && item.getMaTbDich().trim().length() > 0) {
                String keyDich = item.getMaTbDich().trim() + "|" + item.getLoaiCongDichId();
                if (!devicePortMap.containsKey(keyDich)) {
                    devicePortMap.put(keyDich, new ArrayList<String>());
                }
                if (item.getTenPortDich() != null && item.getTenPortDich().trim().length() > 0) {
                    String tenPort = item.getTenPortDich().trim();
                    if (!devicePortMap.get(keyDich).contains(tenPort)) {
                        devicePortMap.get(keyDich).add(tenPort);
                    }
                }
            }
        }

        // Bước 2 & 3: Cho mỗi thiết bị + loại cổng → gán tên
        List<Map<String, Object>> results = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : devicePortMap.entrySet()) {
            String[] parts = entry.getKey().split("\\|", 2);
            String soTheTs = parts[0];
            Long loaiCongId = null;
            if (parts.length > 1 && !"null".equals(parts[1])) {
                try {
                    loaiCongId = Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    loaiCongId = null;
                }
            }
            List<String> portNames = entry.getValue();

            Map<String, Object> deviceResult = new LinkedHashMap<>();
            deviceResult.put("soTheTs", soTheTs);
            deviceResult.put("loaiCongId", loaiCongId);
            deviceResult.put("portNamesFromImport", portNames);

            // 1. Tìm thiết bị theo SO_THE_TS
            Number thietBiId = getThietBiIdBySoTheTs(soTheTs);
            if (thietBiId == null) {
                deviceResult.put("thietBiId", null);
                deviceResult.put("status", "ERROR");
                deviceResult.put("message", "Không tìm thấy thiết bị với mã: " + soTheTs);
                results.add(deviceResult);
                continue;
            }
            deviceResult.put("thietBiId", thietBiId);

            // 2. Lấy danh sách port chưa có tên, sắp xếp theo SOTHUTU
            List<Map<String, Object>> unnamedPorts = getUnnamedPorts(thietBiId, loaiCongId);
            deviceResult.put("totalUnnamedPorts", unnamedPorts.size());

            if (unnamedPorts.isEmpty()) {
                deviceResult.put("status", "NO_UNNAMED_PORTS");
                deviceResult.put("message", "Không có port nào chưa đặt tên");
                deviceResult.put("assigned", new ArrayList<>());
                results.add(deviceResult);
                continue;
            }

            // 3. Gán tên cho port chưa có tên theo thứ tự SOTHUTU
            List<Map<String, Object>> assigned = new ArrayList<>();
            int assignIndex = 0;

            for (int i = 0; i < portNames.size() && assignIndex < unnamedPorts.size(); i++) {
                String tenPort = portNames.get(i);
                Map<String, Object> port = unnamedPorts.get(assignIndex);
                Number portId = (Number) port.get("ID");
                Number soThuTu = (Number) port.get("SOTHUTU");

                // UPDATE TEN cho port này
                String updateSql = "UPDATE ECMS.THIETBI_PORT SET TEN = '"
                        + tenPort.replace("'", "''")
                        + "' WHERE ID = " + portId;
                dbContext.executeNonQuery(updateSql);

                Map<String, Object> assignedItem = new LinkedHashMap<>();
                assignedItem.put("portId", portId);
                assignedItem.put("soThuTu", soThuTu);
                assignedItem.put("tenDaGan", tenPort);
                assigned.add(assignedItem);

                assignIndex++;
            }

            deviceResult.put("status", "OK");
            deviceResult.put("assigned", assigned);
            deviceResult.put("assignedCount", assigned.size());

            if (portNames.size() > unnamedPorts.size()) {
                deviceResult.put("message", "Có " + (portNames.size() - unnamedPorts.size())
                        + " tên port không đủ port trống để gán");
            }

            results.add(deviceResult);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("devices", results);
        response.put("totalDevices", results.size());
        return response;
    }

    /**
     * Tìm THIETBI.ID theo SO_THE_TS
     */
    private Number getThietBiIdBySoTheTs(String soTheTs) throws AppSqlException {
        if (soTheTs == null || soTheTs.trim().isEmpty()) return null;
        String sql = "SELECT ID FROM ECMS.THIETBI WHERE (SO_THE_TS = '"
                + soTheTs.trim().replace("'", "''")
                + "' OR TAISAN_ID = '"
                + soTheTs.trim().replace("'", "''")
                + "') AND HIEU_LUC = 1 AND ROWNUM = 1";
        List<Map<String, Object>> rows = dbContext.executeQueryToListMap(sql);
        if (rows != null && !rows.isEmpty()) {
            return (Number) rows.get(0).get("ID");
        }
        return null;
    }

    /**
     * Lấy danh sách port chưa có tên (TEN IS NULL) theo thiết bị + loại cổng,
     * sắp xếp theo SOTHUTU ASC
     */
    private List<Map<String, Object>> getUnnamedPorts(Number thietBiId, Long loaiCongId) throws AppSqlException {
        if (thietBiId == null) return new ArrayList<>();
        String sql = "SELECT ID, SOTHUTU, LOAICONG_ID FROM ECMS.THIETBI_PORT"
                + " WHERE THIETBI_ID = " + thietBiId
                + " AND HIEU_LUC = 1"
                + " AND (TEN IS NULL OR TRIM(TEN) IS NULL)";
        if (loaiCongId != null) {
            sql += " AND LOAICONG_ID = " + loaiCongId;
        }
        sql += " ORDER BY SOTHUTU ASC";
        List<Map<String, Object>> rows = dbContext.executeQueryToListMap(sql);
        return rows != null ? rows : new ArrayList<Map<String, Object>>();
    }

}

