package vn.vnpt.vnptit.qlsc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import vn.vnpt.Utils.LoggingManaged;
import vn.vnpt.common.success.model.HttpManaged;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.dto.cntt.KhoaPhieuCnttDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class NghiemThuSuCoService {
    private final DbContext dbContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LoggingManaged loggingManaged;

    public NghiemThuSuCoService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object napDsNguonSC() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_NGHIEMTHU_SUCO.SP_NAP_DS_NGUON_SC", params);
    }

    public Object napDsMucDoSC() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_NGHIEMTHU_SUCO.SP_NAP_DS_MUCDO_SC", params);
    }

    public Object napDsTinhTrangSC() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_NGHIEMTHU_SUCO.SP_NAP_DS_TINHTRANG_SC", param);
    }

    public Object napDsNhomSc() throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_NGHIEMTHU_SUCO.SP_NAP_DS_NHOM_SC", params);
    }

	public Object layDsPhieuDieuNt(int suCoId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_suco_id", suCoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEUDIEU_NT", params);
    }

	public Map<String, Object> layAnhChupPhieuNghiemThuSc(int phieuId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", phieuId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.lay_anhchup_phieu_nghiemthu_sc", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Object layDsPhieuNghiemThuSc(DanhSachPhieuNghiemThu danhSachPhieuNghiemThu) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_nhomsc_id", danhSachPhieuNghiemThu.getNhomScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_sc", danhSachPhieuNghiemThu.getMaSc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoibao_id", danhSachPhieuNghiemThu.getNguoiBaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguonsc_id", danhSachPhieuNghiemThu.getNguonScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinhtrang", danhSachPhieuNghiemThu.getTinhTrangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_noidung_sc", danhSachPhieuNghiemThu.getNoiDungSc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngaysc_tu", danhSachPhieuNghiemThu.getNgayScTu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngaysc_den", danhSachPhieuNghiemThu.getNgayScDen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngaybao_tu", danhSachPhieuNghiemThu.getNgayBaoTu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngaybao_den", danhSachPhieuNghiemThu.getNgayBaoDen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mucdosc_id", danhSachPhieuNghiemThu.getMucDoScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_tu_op", danhSachPhieuNghiemThu.getKeoDaiTuOp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_tu", danhSachPhieuNghiemThu.getKeoDaiTu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_den_op", danhSachPhieuNghiemThu.getKeoDaiDenOp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_den", danhSachPhieuNghiemThu.getKeoDaiDen(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_NGHIEMTHU_XL", params);
    }

	public Map<String, Object> nghiemThuSuCotd(NghiemThuSuCo nghiemThuSuCo) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", nghiemThuSuCo.getPhieuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", nghiemThuSuCo.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("QLSC.PKG_NGHIEPVU.SP_NGHIEMTHU_XULY", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public boolean traPhieuNghiemThu(TraPhieuSuCoInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_tra", input.getNoi_dung(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_lydotra_id", input.getLydotra_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_tra_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_TRAPHIEU_NGHIEMTHU_UPD", params);
        return true;
    }

    public Object timKiemDsThietBi(TimKiemDsThietBiDto input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_TEN_TBI", input.getTen_tbi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NHOMTBI_ID", input.getNhomtbi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITBI_ID", input.getLoaitbi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KIEUTBI_ID", input.getKieutbi_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id() == null ? 0 : input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DUNGLUONG_ID", input.getDungluong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOPMC_ID", input.getLopmc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAIKC_ID", input.getPhanloaikc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_CHON_TBI", params);
    }

    public Object laySoDtNhacViec(Long phieuId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", phieuId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SO_DT", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result =  dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_LAY_SO_DT_NHACVIEC_SEL", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public boolean khoaPhieu(KhoaPhieuDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_th", input.getNgay_th(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_th_id", input.getNhanvien_th_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ds_nguyennhan", input.getDs_nguyennhan(), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_ghichu_th", input.getGhichu_th(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_khoaphieu", input.getKhoaphieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_KHOAPHIEU", params);
        return true;
    }

    public boolean khoaPhieuV2(KhoaPhieuDto input) throws AppSqlException {
        Map logData = new HashMap();
        try {
            logData.put("action", "khoaPhieuV2");
            logData.put("nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien());
            try {
                logData.put("input", objectMapper.writeValueAsString(input));
            } catch (Exception ignored) {
            }

            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_ngay_th", input.getNgay_th(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("p_nhanvien_th_id", input.getNhanvien_th_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_ds_nguyennhan", input.getDs_nguyennhan(), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
            params.add(new SqlParameter("p_ghichu_th", input.getGhichu_th(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_khoaphieu", input.getKhoaphieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_KHOAPHIEU", params);

            try {
                int syncStatus = -1;
                String syncResponse = "";
                ArrayList<SqlParameter> paramsG = new ArrayList<>();
                paramsG.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                paramsG.add(new SqlParameter("vsuco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                paramsG.add(new SqlParameter("P_SO_DT", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
                ArrayList<Map<String, Object>> data = dbContext.executeSpWithCursorToListMap("QLSC.sp_lay_thongtin_dongphieu_suco_tts", paramsG);

                try {
                    logData.put("sp_lay_thongtin_dongphieu_suco_tts", objectMapper.writeValueAsString(data));
                } catch (Exception ignored) {
                }
                if (!data.isEmpty()) {
                    for (int i=0; i<data.size(); i++) {
                        Map<String, Object> item = data.get(i);
                        try {
                            Map<String, String> body = new HashMap<>();
                            body.put("data", item.get("JSON_INPUT").toString());
                            String resp = HttpManaged.post("http://tichhop/tts/saveCSSAPI", objectMapper.writeValueAsString(body));
                            logData.put("respTTS-" + i, resp);
                            Map res = objectMapper.readValue(resp, Map.class);
                            if ("0".equals(res.get("errorCode").toString())) {
                                syncStatus = 1;
                                syncResponse = objectMapper.writeValueAsString(res.get("data"));
                            } else {
                                syncResponse = res.get("faultString").toString();
                            }
                        } catch (Exception ex) {
                            syncResponse = ex.getMessage();
                        }

                        ArrayList<SqlParameter> paramsTTS = new ArrayList<SqlParameter>();
                        paramsTTS.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                        paramsTTS.add(new SqlParameter("p_parent_id", item.get("PARENT_ID"), java.sql.Types.NUMERIC));
                        paramsTTS.add(new SqlParameter("p_css_id", item.get("CSS_ID"), java.sql.Types.NUMERIC));
                        paramsTTS.add(new SqlParameter("p_sync_status", syncStatus, java.sql.Types.INTEGER));
                        paramsTTS.add(new SqlParameter("p_jsinput", item.get("JSON_INPUT").toString(), Types.NVARCHAR));
                        paramsTTS.add(new SqlParameter("p_response", syncResponse, java.sql.Types.NVARCHAR));
                        dbContext.executeStoredProcedure("QLSC.sp_capnhat_trangthai_log_tts_v2", paramsTTS);
                        logData.put("sp_capnhat_trangthai_log_tts_v2", "success");
                    }
                }
            } catch (Exception ex) {
                logData.put("errorTTS", ex.getMessage());
            }
            return true;
        } catch (Exception ex) {
            logData.put("error", ex.getMessage());
            throw ex;
        } finally {
            loggingManaged.info(logData);
        }
    }

    public Map<String, Object> layDataChoSaveCSSAPI(Long sucoId, String maTinh) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_suco_id", sucoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_tinh", maTinh, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("QLSC.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID", params);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }

    public Map<String, Object> layThongTinDongPhieuGwOss(String maSc, String maTinh) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_sc", maSc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_tinh", maTinh, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("qlsc.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_GW_OSS", params);
        return result != null && !result.isEmpty() ? result.get(0) : null;
    }
    
    public Object xuLyGiaoViec(Long phieuId, String noiDung) throws AppSqlException
    {
    	Map<String, Object> result = new HashMap<String, Object>();
		result.put("Result", false);
		result.put("Msg", "Không gửi được tin nhắn");
		Map<String, Object> item = (Map<String, Object>) this.laySoDtNhacViec(phieuId);
		if(item != null) {
			Object soDT = item.get("SO_DT");
			if(!(soDT == null || soDT.toString().equals(""))) {
				if(this.smsSend(soDT.toString(), noiDung)) {
					result.replace("Result", true);
					result.replace("Msg", "");
				}
			}
			else {
				result.replace("Msg", "Không tìm thấy thông tin số điện thoại để thực hiện nhắn tin!");
			}
		}
		return result;
    }

    public boolean nghiemThu(KhoaPhieuCnttDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", input.getPhanvung_id() == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getPhanvung_id(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_th_id", input.getNhanvien_th_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu_th", input.getGhichu_th(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_khoaphieu", input.getKhoaphieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.sp_nghiemthu", params);
        return true;
    }


    public boolean smsSend(String soDT, String noiDung) {
    	SendSMSDtoIn item = new SendSMSDtoIn();
    	item.setMsisdn(soDT);
    	item.setContent(noiDung);
    	return this.smsSend(item);
	}

    public boolean smsSend(SendSMSDtoIn obj) {
		AppRequestContext current = AppRequestContext.getCurrent();
		RestTemplate restTemplate = new RestTemplate();
		String soDT = obj.getMsisdn();
		if (!(soDT == null || soDT.equals("") || soDT.startsWith("84"))) {
			if (soDT.startsWith("0"))
				soDT = soDT.substring(1);
			soDT = "84" + soDT;
		}
		// soDT = "84915445554";
		obj.setMsisdn(soDT);
		obj.setMt_type("reply");
		String urlquantri = "http://quantri/internal/sendSms";
		HttpHeaders headers = new HttpHeaders();
		// set content-type header
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", current.getToken().getValue());
		HttpEntity<Object> request = new HttpEntity<>(obj, headers);
		try {
			ResponseEntity<Object> result = restTemplate.postForEntity(urlquantri, request, Object.class);
			Map<String, Object> resultBody = (Map<String, Object>) result.getBody();
			Map<String, Object> resultBodyData = (Map<String, Object>) resultBody.get("data");
			int ecode = Integer.parseInt(resultBodyData.get("error_code").toString());
			return ecode == 0;
		} catch (Exception e) {

		}
		return false;
	}
}
