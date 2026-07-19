package vn.vnpt.vnptit.qlsc.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo.*;
import vn.vnpt.vnptit.qlsc.model.ThongTinNguoiDung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TienTrinhSuCoService {

    private final DbContext dbContext;

    public TienTrinhSuCoService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object DsNguonSuCo(Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        // dbContext.setConnection(ConnectionManager.Connections.QLSC);
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_NGUON_SC", params);
    }

    public Object DsTrangThaiSuCo(Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        // dbContext.setConnection(ConnectionManager.Connections.QLSC);
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TRANGTHAI_SC", params);
    }

    public Object DsSuCo(TimSuCo input, Integer phanVungId, Integer nhanVienId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ma_sc", input.getMaSuCo(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_id", input.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", nhanVienId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ttsc_id", input.getTrangThaiSuCoID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguonsc_id", input.getNguonSuCoId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tungay_yc", input.getTuNgayYC(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_denngay_yc", input.getDenNgayYC(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tungay_ht", input.getTuNgayHT(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_denngay_ht", input.getDenNgayHT(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        // dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TIENTRINH_SC", params);
    }

    public Object DsTienTrinh(Integer phanVungId, Long suCoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_suco_id", suCoId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        // dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_TIENTRINH", params);
    }

    public Boolean CheckTienTrinh(Long tienTrinhId, ThongTinNguoiDung ttnd) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", ttnd.getPhanVungId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TIENTRINHSC_ID", tienTrinhId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", ttnd.getUserName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        // dbContext.setConnection(ConnectionManager.Connections.CSS);
        ArrayList<Map<String, Object>> result = dbContext
                .executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_CHECK_TIENTRINH", params);
        return result != null && result.size() > 0 ? true : false;
    }

    public Object ThemTienTrinh(ThemTienTrinh input, ThongTinNguoiDung ttnd) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_suco_id", input.getSuCoId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", ttnd.getPhanVungId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", ttnd.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", ttnd.getNhanVienId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", input.getNoiDung(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", ttnd.getUserName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", ttnd.getMayCN(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", ttnd.getIpCN(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_TIENTRINH_SC_INS", params);
    }

    public Object CapNhatTienTrinh(CapNhatTienTrinh input, ThongTinNguoiDung ttnd) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", ttnd.getPhanVungId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", ttnd.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", ttnd.getNhanVienId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", input.getNoiDung(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", ttnd.getUserName(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", ttnd.getMayCN(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", ttnd.getIpCN(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tientrinhsc_id", input.getTienTrinhSCId(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_TIENTRINH_SC_UPD", params);
    }

    public boolean XoaTienTrinh(XoaTienTrinh input, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_tientrinhsc_id", input.getTienTrinhSCId(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext
                .executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_TIENTRINH_SC_DEL", params);
        Map<String, Object> map = rs.get(0);
        return "1".equals(map.get("RESULT").toString());
    }

    public ArrayList<Map<String, Object>> them_tientrinh_sms(TienTrinhSMS tienTrinhSMS, ThongTinNguoiDung ttnd)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        String[] dsSoDt = tienTrinhSMS.getDsSodt().split(";");
        for (String soDt : dsSoDt) {
            params = new ArrayList<SqlParameter>();
            sendSMSDtoIn obj = new sendSMSDtoIn();
            obj.setContent(
                    "SC TTSC " + tienTrinhSMS.getMaSc() + " " + ttnd.getUserName() + ": " + tienTrinhSMS.getNoidung());
            obj.setMsisdn("84" + soDt.substring(1));
            obj.setMt_type("reply");
            Integer status = sendSMS_VNPT(obj);
            params.add(
                    new SqlParameter("p_phanvung_id", ttnd.getPhanVungId(), ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_tientrinhsc_id", tienTrinhSMS.getTientrinhscId(), ParameterDirection.INPUT,
                    Types.NUMERIC));
            params.add(
                    new SqlParameter("p_nhanvien_id", ttnd.getNhanVienId(), ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_donvi_id", ttnd.getDonViId(), ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_sodt", soDt, ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_sc", tienTrinhSMS.getMaSc(), ParameterDirection.INPUT, Types.VARCHAR));
            params.add(
                    new SqlParameter("p_noidung", tienTrinhSMS.getNoidung(), ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_status", status, ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nguoi_cn", ttnd.getUserName(), ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_may_cn", ttnd.getMayCN(), ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ip_cn", ttnd.getIpCN(), ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
            dbContext.executeSpWithCursorToListMap("QLSC.PKG_NGHIEPVU.SP_NHANTIN_TIENTRINH", params);
            Map<String, Object> map = new HashMap<>();
            map.put("sdt", soDt);
            map.put("status", status);
            result.add(map);
        }
        return result;
    }

    public static int sendSMS_VNPT(sendSMSDtoIn obj) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String urlquantri = "http://quantri/service/sendSms";
            HttpHeaders headers = new HttpHeaders();
            // set content-type header
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> request = new HttpEntity<>(obj, headers);
            ResponseEntity<Object> result = restTemplate.postForEntity(urlquantri, request, Object.class);
            System.out.println(result.getBody());
            String str = result.getBody().toString();
            str = str.replace("=", ":");
            JSONObject jo = new JSONObject(str);
            System.out.println(jo);
            if ("BSS-00000000".equals(jo.get("error_code").toString())) {
                return 1;
            }
            // StringBuilder result = new StringBuilder();
            // String str = "http://10.35.60.141:298/cskh.asmx/SendSMS6_VNPT?receive=" +
            // receiver + "&content=" + content + "&key=vnpthaiphong_cskh_pttb";
            // URL url = new URL(str);
            // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // conn.setRequestMethod("GET");
            // BufferedReader rd = new BufferedReader(new
            // InputStreamReader(conn.getInputStream()));
            // String line;
            // while ((line = rd.readLine()) != null)
            // result.append(line);
            // rd.close();
            //
            // return result.toString().equals("<?xml version=\"1.0\"
            // encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">0</string>") ? 1 :
            // 0;
            return 0;
        } catch (Exception ex) {
            return -1;
        }
    }

    public Object layDsSoDienThoai(ThongTinNguoiDung ttnd) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", ttnd.getPhanVungId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", ttnd.getNhanVienId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_SO_DT", params);
    }

    public Object layDsSmsDaGui(Long tienTrinhScId, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tientrinhsc_id", tienTrinhScId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_SMS_DAGUI", params);
    }

    public Object layDsNhanVienNt(Long suCoId, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", suCoId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_NHANVIEN_NT", params);
    }

    public Object LuuBuocXuLy(LuuBuocXuLyInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id_buoc", input.getIdBuoc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thanhcong", input.getThanhCong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_lydo", input.getIdLyDo(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_proc", input.getMaProc(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_NAMLBH.SP_LUU_BUOC_XULY", params);
    }

    public Object dongGiaoPhieuThuTuc(DongGiaoPhieuThuTucInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ID_GIAOPHIEU_THUTUC", input.getIdGiaoPhieuThuTuc(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_id_phieu", input.getIdPhieu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_nd", AppRequestContext.getCurrent().getUserName(), ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_GHICHU", input.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_DS_ID_ANH", input.getDsIdAnh(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_data", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_NAMLBH.SP_DONG_GIAOPHIEU_THUTUC", params);
    }
}
