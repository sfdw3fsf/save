package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.HuongKetNoiBrasInput;
import vn.vnpt.vnptit.ecms.dto.RackInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RackService {
    private final DbContext dbContext;

    public RackService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> layRack(int phanVungId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_RACK.SP_LAY_RACK", params);

    }

    public Map<String, Object> layRackTheoId(Long id, int phanVungId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> res =  dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_RACK.SP_LAY_RACK_THEO_RACK_ID", params);
        return res.size() != 0 ? res.get(0) : null;
    }

    public Map<String, Object> themRack(RackInput rackInput, int phanVungId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack", rackInput.getRACK(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_shelf", rackInput.getSHELF(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> res = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_RACK.SP_TAO_RACK", params);
        return res.size() != 0 ? res.get(0) : null;
    }

    public Map<String, Object> suaRack(RackInput.RackInputIns rackInput, int phanVungId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack_id", rackInput.getRACK_ID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack", rackInput.getRACK(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_shelf", rackInput.getSHELF(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> res =  dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_RACK.SP_CAP_NHAT_RACK", params);
        return res.size() != 0 ? res.get(0) : null;
    }

    public Map<String, Object> xoaRack(String id, int phanVungId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> res = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_RACK.SP_XOA_RACK", params);
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("result:", "1".equals(res.get(0).get("KQ").toString()));
        return returnValue;
    }
}
