package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.KiemTraTrungViTriDsLamModuleDtoInp;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDslamModule;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDslamModule;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DslamModuleService {
    private final DbContext dbContext;

    public DslamModuleService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

//    public ArrayList<Map<String, Object>> DsDslamModule() throws AppSqlException {
//        SqlParameter param = new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
//        dbContext.setConnection(ConnectionManager.Connections.CSS);
//        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_DSLAM_MODULE", param);
//    }

//    public ArrayList<Map<String, Object>> DsDslamModule(Long tramTbId, Long dslamId, Integer phanVungId) throws AppSqlException {
//        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//        params.add(new SqlParameter("p_tramtb_id", tramTbId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
//        params.add(new SqlParameter("P_dslam_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
//        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
//        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
//        dbContext.setConnection(ConnectionManager.Connections.CSS);
//        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_DSLAM_MODULE", params);
//    }

    public Map<String, Object> ChiTietDslamModule(Integer phanVungId, Long moduleDslId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_moduledsl_id", moduleDslId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DSLAM_MODULE", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, ArrayList<Map<String, Object>>> ThemDslamModule(ThemDslamModule input, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_carddsl_id", input.getCardDslId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaimodule_id", input.getLoaiModuleId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vitri", input.getViTri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", input.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_partnumber", input.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS_DSLAM", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "dslamModule"));
        params.add(new SqlParameter("P_RS_PORT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "dsPort"));
        // lvhquan - chỉnh sửa khi thêm mới trả về cả port module và dslam module
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_DANHMUC.SP_DSLAM_MODULE_INS", params);
//        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, ArrayList<Map<String, Object>>> CapNhatDslamModule(Integer phanVungId, CapNhatDslamModule input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_carddsl_id", input.getCardDslId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaimodule_id", input.getLoaiModuleId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vitri", input.getViTri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", input.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_partnumber", input.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_moduledsl_id", input.getModuleDslId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS_DSLAM", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "dslamModule"));
        params.add(new SqlParameter("P_RS_PORT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "dsPort"));
        // lvhquan - chỉnh sửa khi cập nhật trả về cả port module và dslam module
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_DANHMUC.SP_DSLAM_MODULE_UPD", params);
//        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Boolean XoaDslamModule(Integer phanVungId, Long moduleDslId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_moduledsl_id", moduleDslId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_DSLAM_MODULE_DEL", params);
        return  true;
    }

    public boolean kiemTraTrungSerial(String serial) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", serial, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return "1".equals(dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_KIEMTRA_SERIAL", params).get(0).get("RESULT").toString());
    }

    public boolean kiemTraTrungViTriCamCard(KiemTraTrungViTriDsLamModuleDtoInp obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CARDDSL_ID", obj.getCardDslId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", obj.getVitri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return "1".equals(dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_KIEMTRA_VITRI_CAM_CARD", params).get(0).get("RESULT").toString());
    }

    public Map<String, ArrayList<Map<String, Object>>> LayDsLamModuleVaPort(Long moduleDslId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_MODULEDSL_ID", moduleDslId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS_DSLAM", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "dslamModule"));
        params.add(new SqlParameter("P_RS_PORT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "dsPort"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_DANHMUC.SP_GET_DSLAM_MODULE_AND_PORT_MOMDULE", params);
    }
}
