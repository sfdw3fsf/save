package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.HknSwitchDeleteDtoInput;
import vn.vnpt.vnptit.ecms.dto.HknSwitchUpdateDtoInput;
import vn.vnpt.vnptit.ecms.dto.ThongTinKetNoiIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class HknSwitchService {

    private DbContext dbContext;

    public HknSwitchService(DbContext dbContext) {
        this.dbContext = dbContext;
    }


    public Map<String, Object> ketNoiSwitch(HknSwitchUpdateDtoInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_porvl_id_n", input.getPorvl_id_n(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitbi_id_n", input.getLoaitbi_id_n(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_porvl_id_d", input.getPorvl_id_d(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitbi_id_d", input.getLoaitbi_id_d(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id_n", input.getThietbi_id_n(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id_d", input.getThietbi_id_d(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tt_port_id", input.getTt_port_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_link_code", input.getLink_code(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KN_SWITCH.SP_HKN_SWITCH_UPD", params).get(0);
    }

    public boolean goKetNoiSwitch(HknSwitchDeleteDtoInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_porvl_id_n", input.getPorvl_id_n(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_porvl_id_d", input.getPorvl_id_d(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tt_port_id", input.getTt_port_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_link_code", input.getLink_code(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        Map<String, Object> map =dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KN_SWITCH.SP_HKN_SWITCH_DEL", params).get(0);
        return "1".equals(map.get("KETQUA").toString()) ;
    }

    public ArrayList<Map<String, Object>> layTbiTheoTram(int loaitbiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("phanVungId", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitbi_id", loaitbiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HUONG_KN_SWITCH.SP_GET_TBI_THEOTRAM", params);
    }

    public ArrayList<Map<String, Object>> layCardManeTheoDsLam(int dslamID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("phanVungId", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", dslamID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HUONG_KN_SWITCH.SP_GET_CARDMANE_THEODSLAMID", params);
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
}
