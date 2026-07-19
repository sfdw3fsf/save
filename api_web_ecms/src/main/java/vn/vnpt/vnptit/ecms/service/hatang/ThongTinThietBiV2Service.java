package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchThietBiDto;
import vn.vnpt.vnptit.ecms.dto.hatang.UpsertThongTinChungThietBiDto;

@Service
public class ThongTinThietBiV2Service {
	private final DbContext dbContext;

	public ThongTinThietBiV2Service(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object getDanhSachThietBi(SearchThietBiDto dto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.INTEGER));
		params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));

		params.add(new SqlParameter("p_maThietBi", dto != null ? dto.getMaThietBi() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_tenThietBi", dto != null ? dto.getTenThietBi() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_trangThaiVhId", dto != null ? dto.getTrangThaiVhId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_loaiThietBi", dto != null ? dto.getLoaiThietBi() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_soHuuId", dto != null ? dto.getSoHuuId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_serial", dto != null ? dto.getSerial() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_kyHieu", dto != null ? dto.getKyHieu() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_idcId", dto != null ? dto.getIdcId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_rackId", dto != null ? dto.getRackId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_hieuLuc", dto != null ? dto.getHieuLuc() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_soTheTs", dto != null ? dto.getSoTheTs() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_idChiTietTbi", dto != null ? dto.getIdChiTietTbi() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_taiSanId", dto != null ? dto.getTaiSanId() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_khongGian", dto != null ? dto.getKhongGian() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));

		ArrayList<Map<String, Object>> dbResult = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_DS_TBI", params);

		return dbResult.stream().map(map -> {
			Map<String, Object> transformed = new LinkedHashMap<>();
			map.forEach((k, v) -> {
				if (v != null) {
					transformed.put(k.toLowerCase(), v);
				}
			});
			return transformed;
		}).collect(Collectors.toList());
	}

	public Object getDanhSachThietBiPaging(SearchThietBiDto dto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.INTEGER));
		params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));

		params.add(new SqlParameter("p_maThietBi", dto != null ? dto.getMaThietBi() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_tenThietBi", dto != null ? dto.getTenThietBi() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_trangThaiVhId", dto != null ? dto.getTrangThaiVhId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_loaiThietBi", dto != null ? dto.getLoaiThietBi() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_soHuuId", dto != null ? dto.getSoHuuId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_serial", dto != null ? dto.getSerial() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_kyHieu", dto != null ? dto.getKyHieu() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_idcId", dto != null ? dto.getIdcId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_rackId", dto != null ? dto.getRackId() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_hieuLuc", dto != null ? dto.getHieuLuc() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_soTheTs", dto != null ? dto.getSoTheTs() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_idChiTietTbi", dto != null ? dto.getIdChiTietTbi() : null, Types.NUMERIC));
		params.add(new SqlParameter("p_taiSanId", dto != null ? dto.getTaiSanId() : null, Types.VARCHAR));
		params.add(new SqlParameter("p_khongGian", dto != null ? dto.getKhongGian() : null, Types.VARCHAR));
		
		params.add(new SqlParameter("p_page_index", dto != null ? dto.getPageIndex() : 1, Types.NUMERIC));
		params.add(new SqlParameter("p_page_size", dto != null ? dto.getPageSize() : 10, Types.NUMERIC));
		
		SqlParameter pTotalRow = new SqlParameter("p_total_row", Types.NUMERIC, "p_total_row");
		params.add(pTotalRow);
		params.add(new SqlParameter("p_result", Types.REF_CURSOR, "p_result"));

		Map<String, ArrayList<Map<String, Object>>> dbResult = dbContext
				.executeSpWithCursorsToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_DS_TBI_PAGING", params);

		List<Map<String, Object>> items = dbResult.get("p_result").stream().map(map -> {
			Map<String, Object> transformed = new LinkedHashMap<>();
			map.forEach((k, v) -> {
				if (v != null) {
					transformed.put(k.toLowerCase(), v);
				}
			});
			return transformed;
		}).collect(Collectors.toList());

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("pageItems", items);
		result.put("allItems", pTotalRow.getOutValue());
		
		return result;
	}

	// public Object getDanhSachThietBiV2(vn.vnpt.vnptit.ecms.dto.hatang.SearchThietBiV2Dto dto) throws AppSqlException {
	// 	ArrayList<SqlParameter> params = new ArrayList<>();
	// 	params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.INTEGER));
	// 	params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));

	// 	params.add(new SqlParameter("p_khongGian", dto != null ? dto.getKhongGian() : null, Types.VARCHAR));
	// 	params.add(new SqlParameter("p_rackId", dto != null ? dto.getRackId() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_soHuuId", dto != null ? dto.getSoHuuId() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_loaiThietBi", dto != null ? dto.getLoaiThietBi() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_tenThietBi", dto != null ? dto.getTenThietBi() : null, Types.VARCHAR));
	// 	params.add(new SqlParameter("p_hangSxId", dto != null ? dto.getHangSxId() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_serial", dto != null ? dto.getSerial() : null, Types.VARCHAR));
	// 	params.add(new SqlParameter("p_taisanId", dto != null ? dto.getTaisanId() : null, Types.VARCHAR));
	// 	params.add(new SqlParameter("p_soTheTs", dto != null ? dto.getSoTheTs() : null, Types.VARCHAR));
	// 	params.add(new SqlParameter("p_donViQlId", dto != null ? dto.getDonViQlId() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_caNhanQlId", dto != null ? dto.getCaNhanQlId() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_pageIndex", dto != null ? dto.getPageIndex() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_pageSize", dto != null ? dto.getPageSize() : null, Types.NUMERIC));
	// 	params.add(new SqlParameter("p_result", Types.REF_CURSOR));

	// 	ArrayList<Map<String, Object>> dbResult = dbContext
	// 			.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_TBI_V2", params);

	// 	List<Map<String, Object>> result = dbResult.stream().map(map -> {
	// 		Map<String, Object> transformed = map.entrySet().stream()
	// 				.filter(entry -> entry.getValue() != null)
	// 				.collect(Collectors.toMap(
	// 						entry -> entry.getKey().toLowerCase(),
	// 						Map.Entry::getValue,
	// 						(v1, v2) -> v1,
	// 						LinkedHashMap::new));
	// 		return transformed;
	// 	}).collect(Collectors.toList());

	// 	return result;
	// }


	public Object getThongTinChungThietBiById(Number id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_id", id, Types.NUMERIC));
		params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
				Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		ArrayList<Map<String, Object>> dbResult = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_THONG_TIN_CHUNG_TBI", params);

		List<Map<String, Object>> result = dbResult.stream().map(map -> {
			Map<String, Object> transformed = map.entrySet().stream()
					.filter(entry -> entry.getValue() != null)
					.collect(Collectors.toMap(
							entry -> entry.getKey().toLowerCase(),
							Map.Entry::getValue,
							(v1, v2) -> v1,
							LinkedHashMap::new));
			return transformed;
		}).collect(Collectors.toList());

		return result.get(0);
	}

	public boolean deleteThietBi(Long id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		String p_nguoi_cn = AppRequestContext.getCurrent().getUserName();
		Number p_nhanvien_id = AppRequestContext.getCurrent().getNhanVienID();

		params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nhanvien_id", p_nhanvien_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", p_nguoi_cn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

		SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
		params.add(result);

		dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_DELETE_THIETBI_BY_ID", params);
		return "1".equals(String.valueOf(result.getOutValue()));
	}

	public Object upsertThongTinChungThietBi(UpsertThongTinChungThietBiDto dto) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		
		// Set nguoi_cn from context if not provided
		if (dto.getNguoiCn() == null || dto.getNguoiCn().isEmpty()) {
			dto.setNguoiCn(AppRequestContext.getCurrent().getUserName());
		}
		
		// Set tinh_id from context if not provided
		if (dto.getTinhId() == null) {
			dto.setTinhId(AppRequestContext.getCurrent().getPhanVungID());
		}
		
		params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mangtbi_id", dto.getMangtbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hethongtbi_id", dto.getHethongtbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaithietbi_id", dto.getLoaithietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_chungloaithietbi_id", dto.getChungloaithietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_phanloaitb_id", dto.getPhanloaitbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_thietbicha_id", dto.getThietbichaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_chuquan_id", dto.getChuquanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_mathietbi", dto.getMathietbi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_mathietbi_infra", dto.getMathietbiInfra(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ip_quantri", dto.getIpQuantri(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_kyhieu", dto.getKyhieu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_serial_number", dto.getSerialNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_model", dto.getModel(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_part_number", dto.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_trangthaivh_id", dto.getTrangthaivhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitudia_id", dto.getLoaitudiaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaikhachhang", dto.getThietbiIdc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_full_depth", dto.getFullDepth(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_hangsx_id", dto.getHangsxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_namsx", dto.getNamsx(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cs_dinhdanh", dto.getCsDinhdanh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_vaitrothietbi_id", dto.getVaitrothietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nhacc_id", dto.getNhaccId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cs_thucte", dto.getCsThucte(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_hedieuhanh_id", dto.getHedieuhanhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_sl_rackunit", dto.getSlRackunit(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_is_interface", dto.getIsInterface(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_is_power", dto.getIsPower(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_is_console", dto.getIsConsole(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_tinh_id", dto.getTinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dai", dto.getChieuDai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rong", dto.getChieuRong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cao", dto.getChieuCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_cau_hinh", dto.getCauhinh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_tyle_sd", dto.getTyleSd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_hs_cosphi", dto.getHsCosphi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loaimay", dto.getLoaimay(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_da_dinhdanh", dto.getDaDinhdanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dd_dinhdanh", dto.getDdDinhdanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_ndien", dto.getLoaiNdien(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_kieu_ld", dto.getKieuLd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_dd_thucte", dto.getDdThucte(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
		
		Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_UPSERT_THONG_TIN_CHUNG_TBI", params);
		return result.get("item");
	}
}
