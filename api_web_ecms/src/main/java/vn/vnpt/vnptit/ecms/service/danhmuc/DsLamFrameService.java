package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DsLamFrameDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class DsLamFrameService {

    private final DbContext dbContext;

    public DsLamFrameService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDsDsLamFrame() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add (new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DSLAM_FRAME.LAY_DS_DSLAM_FRAME", params);
    }

    public Map<String, Object> getDsDsLamFrameId(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        ArrayList<Map<String, Object>> rs;
        params.add(new SqlParameter("p_dslam_id", id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.PKG_DSLAM_FRAME.SP_LAY_DSLAM_FRAME_BYID", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> themDsLamFrame(DsLamFrameDto.DslamFrameIns dsLamFrameIns) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        ArrayList<Map<String, Object>> rs;
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_FRAME", dsLamFrameIns.getTenFrame(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DSLAM_ID", dsLamFrameIns.getDsLamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", dsLamFrameIns.getViTri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.PKG_DSLAM_FRAME.SP_DSLAM_FRAME_INS", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> capNhatDsLamFrame(DsLamFrameDto.DslamFrameUpd dsLamFrameUpd) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        ArrayList<Map<String, Object>> rs;
        params.add(new SqlParameter("P_FRAMEDSL_ID", dsLamFrameUpd.getFrameDslId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_FRAME", dsLamFrameUpd.getTenFrame(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DSLAM_ID", dsLamFrameUpd.getDsLamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", dsLamFrameUpd.getViTri(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.PKG_DSLAM_FRAME.SP_DSLAM_FRAME_UPD", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public boolean xoa(long frameDslId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_FRAMEDSL_ID", frameDslId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<?> rs = dbContext.executeSpWithCursorToEntities("ECMS.PKG_DSLAM_FRAME.SP_DSLAM_FRAME_DEL", params);
        if (!(rs == null || rs.size() == 0)) {
            return rs.get(0).toString().equals("{RESULT=1}");
        }
        return false;
    }
}
