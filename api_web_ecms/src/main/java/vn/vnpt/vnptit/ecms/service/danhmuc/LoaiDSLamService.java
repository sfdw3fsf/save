package vn.vnpt.vnptit.ecms.service.danhmuc;


import oracle.jdbc.OracleTypes;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.model.LoaiDsLamModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class LoaiDSLamService {

    private final DbContext dbContext;
    public LoaiDSLamService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getLoaiDsLam() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_LOAI_DSLAM.SP_GET_DSLAM", params);
    }

    public int addLoaiDsLam(LoaiDsLamModel loaiDsLam) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_NHOM_DSLAM_ID", loaiDsLam.getNhom_dslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_DSLAM", loaiDsLam.getLoai_dslam(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHICHU", loaiDsLam.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_THAMSO", loaiDsLam.getThamSo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_TD", loaiDsLam.getPort_td(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_TS", loaiDsLam.getBras_ts(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_LOAI_DSLAM.SP_INSERT_DSLAM", params);
        return Integer.parseInt(String.valueOf(rs.get(0).values().toArray()[0]));
    }

    public ArrayList<Map<String, Object>> editLoaiDsLamById(LoaiDsLamModel loaiDsLam, int loaiId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_NHOM_DSLAM_ID", loaiDsLam.getNhom_dslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_DSLAM_ID", loaiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_DSLAM", loaiDsLam.getLoai_dslam(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHICHU", loaiDsLam.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_THAMSO", loaiDsLam.getThamSo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_TD", loaiDsLam.getPort_td(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_TS", loaiDsLam.getBras_ts(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_LOAI_DSLAM.SP_UPDATE_DSLAM", params);
    }

    public void deleteLoaiDsLamById(int id) throws AppSqlException {
        SqlParameter param = new SqlParameter("P_LOAI_DSLAM_ID", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC);
        dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_LOAI_DSLAM.SP_DELETE_DSLAM", param);
    }

    public ArrayList<Map<String, Object>> getLoaiDsLamById(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAI_DSLAM_ID", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_LOAI_DSLAM.SP_GET_DSLAM_BY_ID", params);
    }
}
