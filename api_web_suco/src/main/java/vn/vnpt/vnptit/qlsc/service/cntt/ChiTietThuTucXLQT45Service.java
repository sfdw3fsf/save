package vn.vnpt.vnptit.qlsc.service.cntt;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.cntt.ChiTietThuTucXuLyInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.DmLyDoHaTangInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.DmThuTucHaTangInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.DongThuTucInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.LuuBuocThuTucInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.TaoGiaoPhieuThuTucInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class ChiTietThuTucXLQT45Service {

    private final DbContext dbContext;

    private static final String PKG_NAME = "QLSC.PKG_NGANBH";

    public ChiTietThuTucXLQT45Service(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> chiTietThuTucXuLy(ChiTietThuTucXuLyInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_id_giaophieu_thutuc", input.getId_giaophieu_thutuc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_CHITIET_THUTUC_XULY", params);
    }


    public ArrayList<Map<String, Object>> chiTietThuTucAnhXuLy(ChiTietThuTucXuLyInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id",        AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_id_giaophieu_thutuc", input.getId_giaophieu_thutuc(),                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result",              null,                                          SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_LOAD_ANH_THUTUC", params);
    }



    public Map<String, Object> luuBuocThuTuc(LuuBuocThuTucInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_id_giaophieu_thutuc", input.getP_id_giaophieu_thutuc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thanhcong", input.getP_thanhcong(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_id_lydo", input.getP_id_lydo(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getP_ghichu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
        params.add(new SqlParameter("p_user", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
        SqlParameter outNextStep = new SqlParameter("o_next_step", java.sql.Types.NVARCHAR);
        SqlParameter outAction   = new SqlParameter("o_action",    java.sql.Types.NVARCHAR);
        SqlParameter outMessage  = new SqlParameter("o_message",   java.sql.Types.NVARCHAR);
        params.add(outNextStep);
        params.add(outAction);
        params.add(outMessage);
        dbContext.executeStoredProcedure(PKG_NAME + ".SP_LUU_BUOC_THUTUC", params);
        Map<String, Object> result = new HashMap<>();
        result.put("o_next_step", outNextStep.getOutValue());
        result.put("o_action",    outAction.getOutValue());
        result.put("o_message",   outMessage.getOutValue());
        return result;
    }

    public Map<String, Object> dongThuTuc(DongThuTucInput input) throws AppSqlException {

        Map<String, Object> result = new HashMap<>();

        try {
            if (input.getP_ghichu() == null || input.getP_ghichu().trim().isEmpty()) {
                throw new RuntimeException("Chưa nhập ghi chú");
            }
            if (input.getP_ghichu().length() > 500) {
                throw new RuntimeException("Ghi chú không vượt quá 500 ký tự");
            }

            String jsonAnh = null;
            if (input.getListAnh() != null && !input.getListAnh().isEmpty()) {
                jsonAnh = new ObjectMapper().writeValueAsString(input.getListAnh());
            }

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_phanvung_id",        AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_id_giaophieu_thutuc", input.getP_id_giaophieu_thutuc(),              SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_id_phieu",            input.getP_id_phieu(),                         SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_ghichu",              input.getP_ghichu(),                           SqlParameter.ParameterDirection.INPUT, java.sql.Types.NVARCHAR));
            params.add(new SqlParameter("p_id_nhanvien",         AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("p_list_anh_json",       jsonAnh,                                       SqlParameter.ParameterDirection.INPUT, java.sql.Types.CLOB));

            SqlParameter outAction  = new SqlParameter("o_action",  java.sql.Types.NVARCHAR);
            SqlParameter outMessage = new SqlParameter("o_message", java.sql.Types.NVARCHAR);
            params.add(outAction);
            params.add(outMessage);

            dbContext.executeStoredProcedure(PKG_NAME + ".SP_DONG_THUTUC_XULY", params);

            result.put("o_action",  outAction.getOutValue());
            result.put("o_message", outMessage.getOutValue());

        } catch (Exception e) {
            result.put("o_action",  "ERROR");
            result.put("o_message", e.getMessage());
        }

        return result;
    }

    public ArrayList<Map<String, Object>> dmThuTucHaTang(DmThuTucHaTangInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_quytrinh", input.getMa_quytrinh(),  SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_loaicanhbao", input.getLoaicanhbao(),  SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ma_diemcham", input.getMa_diemcham(),  SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_result",      null,                    SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_DM_THUTUC_HATANG", params);
    }

    public ArrayList<Map<String, Object>> dmLyDoHaTang(DmLyDoHaTangInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_proc", input.getMa_proc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".SP_DM_LYDO_HATANG", params);
    }

    public Map<String, Object> taoGiaoPhieuThuTuc(TaoGiaoPhieuThuTucInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_MAND", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_id_phieu", input.getP_id_phieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ma_proc", input.getP_ma_proc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NVARCHAR));

        SqlParameter outStatus   = new SqlParameter("o_status",    java.sql.Types.NUMERIC);
        SqlParameter outMessage  = new SqlParameter("o_message",   java.sql.Types.NVARCHAR);
        SqlParameter outIdThutuc = new SqlParameter("o_id_thutuc", java.sql.Types.NUMERIC);

        params.add(outStatus);
        params.add(outMessage);
        params.add(outIdThutuc);

        dbContext.executeStoredProcedure(PKG_NAME + ".SP_TAO_GIAOPHIEU_THUTUC", params);

        Map<String, Object> result = new HashMap<>();
        result.put("o_status",    outStatus.getOutValue());
        result.put("o_message",   outMessage.getOutValue());
        result.put("o_id_thutuc", outIdThutuc.getOutValue());
        return result;
    }

}
