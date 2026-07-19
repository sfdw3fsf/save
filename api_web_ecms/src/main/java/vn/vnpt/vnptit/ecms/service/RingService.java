package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.RingInsDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RingService {

    private final DbContext dbContext;

    public RingService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> lietKe() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_RING.SP_RING_GET_ALL", params);
    }

    public Map<String, Object> chiTiet(long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ring_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_RING.SP_RING_GET_BY_ID", params);
        if (list.size() > 0) return list.get(0);
        return null;
    }

    public Map<String, Object> them(RingInsDto ringInsDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_ring", ringInsDto.getTenRing(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghi_chu", ringInsDto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_RING.SP_RING_INS", params);
        if (list.size() > 0) return list.get(0);
        return null;
    }

    public Map<String, Object> capNhat(RingInsDto.RingUpdDto ringUpdDto) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ring_id", ringUpdDto.getRingId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_ring", ringUpdDto.getTenRing(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghi_chu", ringUpdDto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_RING.SP_RING_UPD", params);
        if (list.size() > 0) return list.get(0);
        return null;
    }

    public Map<String, Boolean> xoa(long id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ring_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_RING.SP_RING_DEL_BY_ID", params);
        // if (list.size() > 0) return list.get(0);
        Map<String, Boolean> res = new HashMap<>();
        res.put("result", true);
        return res;
    }
}
