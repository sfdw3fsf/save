package vn.vnpt.vnptit.qlsc.service.cntt;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.CapNhatPhieuXlSuCoDto;
import vn.vnpt.vnptit.qlsc.dto.LayDsPhieuXuLyDto;
import vn.vnpt.vnptit.qlsc.dto.SendSMSDtoIn;
import vn.vnpt.vnptit.qlsc.dto.cntt.GiaoPhieuXlSuCoCnttDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.KhoaPhieuCnttDto;
import vn.vnpt.vnptit.qlsc.service.NghiemThuSuCoService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class GiaoPhieuSuCoCNTTService {

    private final DbContext dbContext;
    private final NghiemThuSuCoService nghiemThuSuCoService;

    public GiaoPhieuSuCoCNTTService(DbContext dbContext, NghiemThuSuCoService nghiemThuSuCoService) {
        this.dbContext = dbContext;
        this.nghiemThuSuCoService = nghiemThuSuCoService;
    }

    public Object layDsPhieuXuLy(LayDsPhieuXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_NGUONSC_ID", input.getNguonScId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MA_SC", input.getMaSc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYGIAO", input.getNgayGiao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI", input.getTrangThai(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDOSC_ID", input.getMucDoScId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_KEODAI", input.getKeoDai(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_HUONGGIAO_ID", input.getHuongGiaoId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanVungId() == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getPhanVungId(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_LAY_DS_PHIEU_XULY", params);
    }

    public Object giaoPhieuXlSuCo(GiaoPhieuXlSuCoCnttDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_cha_id", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_huonggiao_id", input.getHuonggiao_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_giao", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_giao_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_nhan_id", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_giao_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_nhan_id", input.getNhanvien_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_khan", input.getKhan(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", input.getPhanVungId() == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getPhanVungId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_GIAOPHIEU_XULY_V2", params);
        Map<String, Object> output = new HashMap<>();
        for (Map<String, Object> giaoviec : result) {
            this.sendSMS(
                    String.valueOf(giaoviec.get("SO_DT")),
                    String.valueOf(giaoviec.get("NOIDUNG"))
            );
        }
//        result.forEach(item -> {
//            SendSMSDtoIn sendDTO = new SendSMSDtoIn();
//            sendDTO.setMsisdn(String.valueOf(item.get("SO_DT")));
//            sendDTO.setContent(String.valueOf(item.get("NOIDUNG")));
//            sendDTO.setMt_type("reply");
//            try {
//                this.jobGiaoPhieuSendSMSNhanVien(sendDTO);
//            } catch (Exception e) {
//                output.put("sms", e);
//                e.printStackTrace();
//            }
//        });
        output.put("sms", true);
        output.put("giaophieu", true);
        return output;
    }

    public boolean capNhatPhieuXlSuCo(CapNhatPhieuXlSuCoDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", input.getPhanVungId() == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getPhanVungId(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_giao_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_giao_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_nhan_id", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_nhan_id", input.getNhanvien_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_giao", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_khan", input.getKhan(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_chitieu_tg", input.getChitieu_tg(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_CAPNHAT_XULY_UPD", params);
        return true;
    }

    public boolean huyPhieuXlSuCo(GiaoPhieuXlSuCoCnttDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", input.getPhanVungId() == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getPhanVungId(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.HUYGIAO_XULY", params);
        return true;
    }

    public Object dsGiaoPhieuXl(GiaoPhieuXlSuCoCnttDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", input.getPhanVungId() == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getPhanVungId(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_LAY_DS_GIAOPHIEU_XULY", params);
    }

    public boolean khoaPhieu(KhoaPhieuCnttDto input) throws AppSqlException {
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
        dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_KHOAPHIEU", params);
        return true;
    }

    public void giaoPhieuSendSMSNhanVien(SendSMSDtoIn sendDTO) {
        AppRequestContext current = AppRequestContext.getCurrent();
        RestTemplate restTemplate = new RestTemplate();
        String urlquantri = "http://quantri/internal/sendSms";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", current.getToken().getValue());
        HttpEntity<Object> request = new HttpEntity<>(sendDTO, headers);
        restTemplate.postForEntity(urlquantri, request, Object.class);
    }

    public Object sendSMS(String SDT, String noiDung) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("vso_dt", SDT, Types.NVARCHAR));
        params.add(new SqlParameter("vnoidung", noiDung, Types.NVARCHAR));
        params.add(new SqlParameter("vghichu", "giao phieu suco cntt", Types.NVARCHAR));
        params.add(new SqlParameter("vmay_cn", "auto", Types.NVARCHAR));
        params.add(new SqlParameter("vnguoi_cn", "admin", Types.NVARCHAR));
        params.add(new SqlParameter("vip_cn", "auto", Types.NVARCHAR));
        try {
            dbContext.executeSpWithCursorToListMap("css.send_sms", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
