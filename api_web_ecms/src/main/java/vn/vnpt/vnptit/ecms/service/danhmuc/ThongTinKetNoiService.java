package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatHuongKetNoiDTO;
import vn.vnpt.vnptit.ecms.dto.ThongTinKetNoiIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ThongTinKetNoiService {
    private final DbContext dbContext;
    public ThongTinKetNoiService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }
    public Map<String, Object> ketnoiDslam(int phanvung_id,ThongTinKetNoiIn.KetNoi item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", item.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_n", item.getLoaithietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_d", item.getLoaithietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_thietbi_id_n", item.getThietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_thietbi_id_d", item.getThietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_link_code", item.getLink_code(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", item.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_chk_port", item.getChk_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loai_kn", item.getHuong_ket_noi(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_KETNOI_DSLAM", params);
        if(!(result == null || result.size() == 0))
        {
            return result.get(0);
        }
        return null;
    }
    public Map<String, Object> ketnoiDslam_v2(int phanvung_id,ThongTinKetNoiIn.KetNoi item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", item.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_n", item.getLoaithietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_d", item.getLoaithietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_thietbi_id_n", item.getThietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_thietbi_id_d", item.getThietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_link_code", item.getLink_code(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", item.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_chk_port", item.getChk_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaikn_id", item.getHuong_ket_noi(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_KETNOI_DSLAM_V2", params);
        if(!(result == null || result.size() == 0))
        {
            return result.get(0);
        }
        return null;
    }
    public boolean capNhatHuongKN(CapNhatHuongKetNoiDTO item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("p_port", item.getPort(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("p_loaikn_id", item.getHuong_ket_noi(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_CAPNHAT_HUONG_KN", params);
        if (result != null && !result.isEmpty()) {
            if ("1".equals(String.valueOf(result.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public Map<String, Object> goketnoi(int phanvung_id,ThongTinKetNoiIn.GoKetNoi item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", item.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", item.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_n", item.getLoaithietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_d", item.getLoaithietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_GO_KETNOI", params);
        if(!(result == null || result.size() == 0))
        {
            return result.get(0);
        }
        return null;
    }
    public Map<String, Object>loadthongtinketnoi(int phanvung_id,int madslam_port) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", madslam_port, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LOAD_TTKETNOI", params);
        if(result==null ||result.size()==0)
            result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LOAD_TTKETNOI2", params);
        if(!(result == null || result.size() == 0))
        {
            return result.get(0);
        }
        return null;
    }
    public Map<String, Object>loadthongtinketnoi_v2(int phanvung_id,int madslam_port) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", madslam_port, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LOAD_TTKETNOI_V2", params);
        if(result==null ||result.size()==0)
            result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LOAD_TTKETNOI2_V2", params);
        if(!(result == null || result.size() == 0))
        {
            return result.get(0);
        }
        return null;
    }

    public Map<String, Object> layMaKetNoi(int phanvung_id, ThongTinKetNoiIn.MaKetNoi item) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", item.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", item.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.LAY_MA_KETNOI", params);
        if(!(result == null || result.size() == 0))
        {
            return result.get(0);
        }
        return null;
    }
    public ArrayList<Map<String, Object>> dsHuongKetNoi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_TBI", "1,2,3,17", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_HUONG_KETNOI_SEL", params);
    }
    public ArrayList<Map<String, Object>> dsThietBi(int phanvung_id, int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_TBI", id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_HIENTHI_TBI_THEO_TRAMTB", params);
    }

    public ArrayList<Map<String, Object>> dsCardDsl(int phanvung_id, int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DSLAM_ID", id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_CARD_DSL_THEO_DSLAM_ID_SEL", params);
    }

    public ArrayList<Map<String, Object>> dsCardGpon(int phanvung_id,  int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DSLAM_ID", id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_CARD_GPON_THEO_DSLAM_ID_SEL", params);
    }

    public ArrayList<Map<String, Object>> dsCardMane(int phanvung_id, int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DSLAM_ID", id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_CARD_MANE_THEO_DSLAM_ID_SEL", params);
    }

    public Map<String,ArrayList<Map<String, Object>>> dsPort_Module_CardDsl(int phanvung_id, ThongTinKetNoiIn.InputItem input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CARDDSLAM_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_EDIT", input.getType(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_PORT_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"dsPort"));
        params.add(new SqlParameter("P_MODULE_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"dsModule"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_PORT_MODULE_CUA_CARDDSLAM_SEL", params);
    }

    public ArrayList<Map<String, Object>> dsPort_CardGpon(int phanvung_id, int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CARDGPON_ID", id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_PORT_CUA_CARDGPON_SEL", params);
    }

    public Map<String,ArrayList<Map<String, Object>>> dsPortModule_CardMane(int phanvung_id, ThongTinKetNoiIn.InputItem input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CARDDSLAM_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_EDIT", input.getType(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_PORT_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"dsPort"));
        params.add(new SqlParameter("P_MODULE_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"dsModule"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_PORT_MODULE_CUA_CARDMANE_SEL", params);
    }

    public ArrayList<Map<String, Object>> dsPortSwitch(int phanvung_id, ThongTinKetNoiIn.InputItem input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_TBI_SWITCH_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_EDIT", input.getType(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_PORT_CUA_SWITCH_SEL", params);
    }

    public ArrayList<Map<String, Object>> dsPortMouleDsl(int phanvung_id, ThongTinKetNoiIn.InputItem input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_MODULE_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_EDIT", input.getType(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_PORT_CUA_MODULE_DSLAM_SEL", params);
    }

    public ArrayList<Map<String, Object>> dsPortMouleMane(int phanvung_id, ThongTinKetNoiIn.InputItem input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_MODULE_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_EDIT", input.getType(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_PORT_CUA_MODULE_MANE_SEL", params);
    }
    public ArrayList<Map<String, Object>> dsTuRack_theo_tbi(int phanvung_id, ThongTinKetNoiIn.LayTuRack input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_TBI_ID_N", input.getTbi_n_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_TBI_ID_D", input.getTbi_d_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LAY_TURACK_THEO_TBI", params);
    }
}
