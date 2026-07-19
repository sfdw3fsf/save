package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ThongTinMDFService {
    private final DbContext dbContext;
    public ThongTinMDFService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> TraCuuMDFTheoSoTB(TracuuMDFAccDtoIn tracuuMDFAccDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_tb", tracuuMDFAccDtoIn.getMa_tb(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_dl_id", tracuuMDFAccDtoIn.getDonvi_dl_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMDF.LAY_DB_THEO_MATB_1", params);
        //return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public ArrayList<Map<String, Object>> Lay_tt_cap_theo_matb(LayTTCapTheoMaTBDtoIn layTTCapTheoMaTBDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vma_tb", layTTCapTheoMaTBDtoIn.getVma_tb(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMDF.Lay_tt_cap_theo_matb", params);
    }

    public ArrayList<Map<String, Object>> Lay_giando_theodaunoi(LayGianDoTheoDauNoiDtoIn layGianDoTheoDauNoiDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vketcuoi_id", layGianDoTheoDauNoiDtoIn.getVketcuoi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vvitri", layGianDoTheoDauNoiDtoIn.getVvitri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMDF.Lay_giando_theodaunoi", params);
    }

    public ArrayList<Map<String, Object>> Lay_tt_tongdai_theo_cap(LayThongTinTongDaiTheoCapDtoIn layThongTinTongDaiTheoCapDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vcap_id", layThongTinTongDaiTheoCapDtoIn.getVcap_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vdoidayvao", layThongTinTongDaiTheoCapDtoIn.getVdoidayvao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vkieu", layThongTinTongDaiTheoCapDtoIn.getVkieu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMDF.Lay_tt_tongdai_theo_cap", params);
    }

    public ArrayList<Map<String, Object>> TraCuuMDFTheoPhienDoc(TracuuMDFPhienDocDtoIn tracuuMDFAccDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vkieu", tracuuMDFAccDtoIn.getVkieu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vthuebao_id", tracuuMDFAccDtoIn.getVthuebao_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_vtongdai", tracuuMDFAccDtoIn.getVtongdai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_vkyhieupd", tracuuMDFAccDtoIn.getVkyhieupd(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_vjumper", tracuuMDFAccDtoIn.getVjumper(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMDF.LAY_THONGTIN_MDF_PD", params);
    }

    public ArrayList<Map<String, Object>> Lay_ds_cbo_tongdai(String loaidv) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidv_id", loaidv, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMDF.SP_LOAD_THONGTIN_CBO_TONGDAI", params);
    }
}
