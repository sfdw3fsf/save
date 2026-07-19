package vn.vnpt.vnptit.qlsc.service.cntt;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.dto.cntt.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class CapNhatKQXuLySuCoCNTTService {
    private final DbContext dbContext;
    private static final String PKG_NAME  = "QLSC.PKG_SUCO_CNTT";
    private final NghiemThuSuCoCNTTService nghiemThuSuCoCNTTService;
    private final ScheduledExecutorService scheduler;

    public CapNhatKQXuLySuCoCNTTService(DbContext dbContext,
                                        NghiemThuSuCoCNTTService nghiemThuSuCoCNTTService,
                                        ScheduledExecutorService scheduler) {
        this.dbContext = dbContext;
        this.nghiemThuSuCoCNTTService = nghiemThuSuCoCNTTService;
        this.scheduler = scheduler;
    }

    public ArrayList<Map<String, Object>> getDSDanhMuc(String ds)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
        parameters.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.INTEGER));
        parameters.add(new SqlParameter("p_ds", ds, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LOAD_DANHMUC", parameters);
    }

    public ArrayList<Map<String, Object>> getDSDanhMucKhac(String sTable, String sCond)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
        parameters.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), java.sql.Types.INTEGER));
        parameters.add(new SqlParameter("P_TABLE", sTable, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_COND", sCond, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LOAD_DANHMUC_KHAC", parameters);
    }

    public ArrayList<Map<String, Object>>  ds_phieu_dieuto(PhieuDieuCnttToInputDTO input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_nhomsc_id", input.getnhomsc_id(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ma_sc", input.getma_sc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nguoibao_id", input.getnguoibao_id(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_mucdokc_id", input.getmucdokc_id(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguonsc_id", input.getnguonsc_id(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_tinhtrang", input.gettinhtrang(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_noidung_sc", input.getnoidung_sc(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_noidung_giao", input.getnoidung_giao(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngaysc_tu", input.getngaysc_tu(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngaysc_den", input.getngaysc_den(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngaybao_tu", input.getngaybao_tu(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngaybao_den", input.getngaybao_den(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngaygiao_tu", input.getngaygiao_tu(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngaygiao_den", input.getngaygiao_den(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_mucdosc_id", input.getmucdosc_id(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_tu_op", input.getkeodai_tu_op(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_tu", input.getkeodai_tu(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_den_op", input.getkeodai_den_op(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_keodai_den", input.getkeodai_den(), SqlParameter.ParameterDirection.INPUT,java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id",  AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id",  AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LAY_DS_PHIEU_DIEU_TO_01", params);
    }

    public ArrayList<Map<String, Object>> ds_phantu_mang(int phanvung_id, int phieu_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_id", phieu_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LAY_DS_PTM_PDT", params);
    }

    public ArrayList<Map<String, Object>> ds_giaoviec(int phanvung_id, int phieu_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_id", phieu_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LAY_DS_GV_PDT", params);
    }


    public boolean capnhat_ghichu(int PHANVUNG_ID, int P_PHIEU_ID, String P_GHICHU) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", PHANVUNG_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", P_PHIEU_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", P_GHICHU, SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_CAPNHAT_GHICHU_TH_PXL", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("RESULT").toString());
    }

    public boolean traphieu_suco(TraPhieuSuCoCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nd_tra", input.getNoi_dung(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_lydotra_id", input.getLydotra_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_tra_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_TRAPHIEU_XULY_UPD", params);
        return true;
    }

    public Object xuLySMSGiaoViecNV(GiaoViecNvCNTTInput input) throws AppSqlException {
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) this.giaoviec_nv(input);
        if(!(items == null || items.size() == 0)) {
            for (int i = 0; i < items.size(); i++) {
                Map<String, Object> item = items.get(i);
                Object soDT = item.get("SO_DT");
                Object noiDung = item.get("ND_SMS");
                Object giaoViecID = item.get("GIAOVIEC_ID");
                result.add(giaoViecID);
                this.smsSend(soDT.toString(), noiDung.toString());
            }
        }
        return result;
    }

    public Object giaoviec_nv(GiaoViecNvCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_SUCO_PTM_ID", input.getSuco_ptm_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DS_GIAOVIEC", input.getDs_giaoviec(), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("P_DS_ND_SMS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_GIAOVIEC_NV_01_UPD", params);
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

    /*
        COMMENTS
    * */

    public ArrayList<Map<String, Object>> ds_comments(int phanvung_id_sc, int suco_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id_sc, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_SUCO_ID", suco_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LAY_DS_GHICHU_PHIEUXL", params);
    }

    public boolean add_comment(CommentCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_NV_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_SC_ID", input.getPhanvung_sc_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_COMMENT_ID", input.getComment_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", input.getComment_content(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
params.add(new SqlParameter("P_FILES", input.getAttach_files(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));

        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_THEM_GHICHU_PHIEUXL_2", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("RESULT").toString());
    }

    public boolean update_comment(CommentCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_NV_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_SC_ID", input.getPhanvung_sc_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_COMMENT_ID", input.getComment_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", input.getComment_content(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_CAPNHAT_GHICHU_PHIEUXL", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("RESULT").toString());
    }

    public boolean delete_comment(CommentCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_NV_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_SC_ID", input.getPhanvung_sc_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_COMMENT_ID", input.getComment_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_XOA_GHICHU_PHIEUXL", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("RESULT").toString());
    }


    public boolean capnhat_ghichu_dichvu(GhiChuXuLyDichVuInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_SUCO_PTM_ID", input.getSuco_ptm_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", input.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_CAPNHAT_GHICHU_XULY_DICHVU", params);
        return true;
    }


    public boolean capnhat_baoton(CapNhatBaoTonCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nnton_id", input.getNnton_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_bt", input.getNgay_bt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_bt_id", input.getNhanvien_bt_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_bt_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_BAOTON_XULY_SUCO", params);
        return true;
    }

    public boolean huy_baoton(HuyBaoTonCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_id", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nnton_id", input.getNnton_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap(PKG_NAME + ".sp_huyphieu_bt_xuly_suco_del", params);
        return true;
    }

    public boolean hoan_cong(HoanCongPXLCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEU_ID", input.getphieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", input.getghi_chu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RETURNS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_HOANCONG_XULY_SUCO", params);
        Map<String, Object> map = list.get(0);
        try {
            long sucoId = Long.parseLong(map.get("RESULT").toString());
            //this.nghiemThuTuDongTask(input.getPhanvung_id(), sucoId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            long sucoId = Long.parseLong(map.get("RESULT").toString());
            String ma_sc = map.get("MA_SC").toString();
            this.nghiemThuSuCoCNTTService.xuLyGiaoViec(sucoId, "Đã xử lý xong sự cố " + ma_sc + ", đề nghị anh/chị kiểm tra lại phiếu.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void nghiemThuTuDongTask(int phanvungId, long sucoId) {
        Runnable task = () -> {
            try {
                //System.out.println("Running task: Job auto nghiệm thu | Thread: " + Thread.currentThread().getName() + " | Time: " + java.time.LocalTime.now());
                this.nghiemThuSuCoCNTTService.nghiemThuTuDongWithSuCoId(phanvungId, sucoId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        // task run auto nghiem thu
        ScheduledFuture<?> future = scheduler.schedule(task, 30, TimeUnit.MINUTES);

        // task auto chua chay xong thi cung cancel
        scheduler.schedule(() -> {
            if (!future.isDone()) {
                //System.out.println("Job timeout, cancel and remove.");
                future.cancel(true); // sẽ tự remove khỏi queue
            }
        }, 35, TimeUnit.MINUTES);
    }


    public ArrayList<Map<String, Object>> layDsTinhTrangDichVuCNTT(TinhTrangDichVuCNTTInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(), Types.INTEGER));
        params.add(new SqlParameter("RETURNDS", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LAY_DS_TINHTRANG_DV_CNTT", params);
    }

    public ArrayList<Map<String, Object>> ds_nhanvien_xuly(int VPHANVUNG_ID,int VPHIEU_ID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", VPHANVUNG_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VPHIEU_ID", VPHIEU_ID, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LAY_DS_NHANVIEN_XL", params);
    }

    public Object xuLySMSHuyGiaoViec(Long idGiaoViec) throws AppSqlException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("Result", false);
        result.put("Msg", "Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)");
        Map<String, Object> item = (Map<String, Object>) this.huy_giaoviec_nv(idGiaoViec);
        if(item != null) {
            Object soDT = item.get("SO_DT");
            Object noiDung = item.get("ND_SMS");
            if(!(soDT == null || noiDung == null)) {
                boolean sms = this.smsSend(soDT.toString(), noiDung.toString());
                if(sms) {
                    result.replace("Result", true);
                    result.replace("Msg", "Gửi tin nhắn thành công");
                }
            }
            else {
                result.replace("Msg", "Không gửi được tin nhắn (hãy kiểm tra lại số điện thoại hoặc hệ thống nhắn tin)");
            }
        }
        return result;
    }

    public Object huy_giaoviec_nv(Long idGiaoViec) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_GIAOVIEC_ID", idGiaoViec, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_ND_GUI_SMS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("QLSC.pkg_nghiepvu.SP_HUY_GIAOVIEC_NV_01_DEL", params);
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    public boolean capnhat_anhchup_cntt(CapNhatAnhChupCNTTDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MOTA", input.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_CAPNHAT_ANHCHUP_TIEPNHAN_SC", params);
        return true;
    }

public boolean delete_file_comment(Long idPhanVungSC, Long idFIleComment) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", idPhanVungSC, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("FILE_ID", idFIleComment, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap(PKG_NAME + ".DELETE_FILE_COMMENT", params);
        return true;
    }	

}
