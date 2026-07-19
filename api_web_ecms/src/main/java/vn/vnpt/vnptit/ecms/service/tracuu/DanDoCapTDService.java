package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.DanDoCapTDDtoIn;

import java.io.IOException;
import java.io.ObjectInput;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
@Service
public class DanDoCapTDService {
    private final DbContext dbContext;

    public DanDoCapTDService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> danhsach_loai_tbi_cha() throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_LOAI_THIET_BI_CHA_SEL", params);
    }

    public ArrayList<Map<String, Object>> lay_tbi_cha(int phanvung_id,DanDoCapTDDtoIn.ThietBiChaIn input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_TBI_ID",input.getLoaithietbi_id(), ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DONVI_ID",input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_THIET_BI_CHA_SEL", params);
    }

    public ArrayList<Map<String, Object>> lay_tbi_con(int phanvung_id,DanDoCapTDDtoIn.ThietBiConIn input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_THIETBI_CHA_ID",input.getThietbi_cha_id(), ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_LOAITBI_ID",input.getLoaithietbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_LAY_DS_THIETBI", params);
    }

    public ArrayList<Map<String, Object>> ds_cong_tbi(int phanvung_id,DanDoCapTDDtoIn.CongThietBiIn input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID",phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_LOAI_TBI_ID", input.getLoaithietbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_THIET_BI_ID", input.getThietbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_CONG_THIET_BI_SEL", params);
    }

    public Map<String, Object> load_cong_tbi_theo_portid(int phanvung_id,int port_id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_PORTVL_ID", port_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs= dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_THONG_TIN_CONG_SEL", params);
        return rs != null && rs.size()>0 ? rs.get(0):null;
    }

    public Map<String, Object> tt_cong_tbi_theo_dk(int phanvung_id,DanDoCapTDDtoIn.TraCuuDanDo input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID",phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_LOAITBI_ID", input.getLoaitbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_THIETBI_ID", input.getThietbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_VITRI", input.getVitri(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs= dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_THONG_TIN_CONG_THEODK_SEL", params);
        return rs != null && rs.size()>0 ? rs.get(0):null;
    }
   /* public ArrayList<Map<String, Object>> down2uplink(DanDoCapTDDtoIn.TTCongThietBiIn input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORTVL_ID", input.getPort_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_DOWNLINK_TO_UPLINK", params);
    }
    public ArrayList<Map<String, Object>> up2downlink(DanDoCapTDDtoIn.TTCongThietBiIn input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORTVL_ID", input.getPort_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_UPLINK_TO_DOWNLINK", params);
    }*/
    public ArrayList<Map<String, Object>> timkiem_tbi(int phanvung, String name) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_QUERY", name, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUU_DANDO_CAP_TD.SP_TIM_KIEM_LOAI_TBI", params);
    }
    public ArrayList<Map<String, Object>> ds_ttvt(int phanvung) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.LAY_DS_TTVT", params);
    }
    public ArrayList<Map<String, Object>> ds_tovt(int phanvung,int ttvt_id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TTVT_ID", ttvt_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.LAY_DS_TOVT", params);
    }
    public ArrayList<Map<String, Object>> ds_tramvt(int phanvung,int tovt_id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOVT_ID", tovt_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.LAY_DS_TRAMTB", params);
    }
    public ArrayList<Map<String, Object>> dandocaptd(int phanvung_id,DanDoCapTDDtoIn.TraCuuDanDo input) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_LOAITBI_ID", input.getLoaitbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_THIETBI_ID", input.getThietbi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_VITRI", input.getVitri(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.LAY_DANDO_CAP_TD", params);
    }
}
