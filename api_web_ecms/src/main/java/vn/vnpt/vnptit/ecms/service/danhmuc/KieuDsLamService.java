package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.KieuDsLamInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class KieuDsLamService {

    private final DbContext dbContext;
    public KieuDsLamService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDsKieuDsLam() throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.KIEUDSLAM.GET_KIEU_DSLAM", params);

    }

    public Map<String, Object> getDsKieuDsLamById(int dsLamId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KIEUDSLAM_ID", dsLamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.KIEUDSLAM.GET_KIEU_DSLAM_BY_ID", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> taoKieuDsLam(KieuDsLamInput kieuDslam) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("KIEUDSLAM_PARAM", kieuDslam.getKieuDslam(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.KIEUDSLAM.TAO_KIEUDSLAM", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> capNhatKieuDsLam(KieuDsLamInput kieuDslam) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("KIEUDSLAM_ID_PARAM", kieuDslam.getKieuDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("KIEUDSLAM_PARAM", kieuDslam.getKieuDslam(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.KIEUDSLAM.CAPNHAT_KIEUDSLAM", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public boolean xoaKieuDsLamById(int dsLamId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KIEUDSLAM_ID", dsLamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, int.class));
//        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.KIEUDSLAM.XOA_KIEUDSLAM", params);

		ArrayList<?> result = dbContext.executeSpWithCursorToEntities("ECMS.KIEUDSLAM.XOA_KIEUDSLAM", params);
        if (!(result == null || result.size() == 0)) {
            return Integer.parseInt(result.get(0).toString()) > 0;
        }
        return false;
    }

}
