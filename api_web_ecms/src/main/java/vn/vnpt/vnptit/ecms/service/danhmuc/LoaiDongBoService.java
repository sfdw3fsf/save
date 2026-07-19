package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatLoaiDongBoDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemLoaiDongBoDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LoaiDongBoService {
    @Autowired
    DbContext dbContext;
    public ArrayList<Map<String, Object>> lay_ds_loaidongbo()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAI_DONGBO.SP_LOAI_DONGBO_SEL", parameters);
    }
    public Map<String, Object> lay_loaidongbo_theo_loaidb_id(Long loaidbId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_loaidb_id", loaidbId != null ? loaidbId: null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAI_DONGBO.SP_LOAI_DONGBO_SEL_BY_LOAIDB_ID", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }
    public Map<String, Object> tao_loaidongbo(ThemLoaiDongBoDtoIn loaiDongBo)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_dongbo", loaiDongBo.getLoaiDongbo() != null ? loaiDongBo.getLoaiDongbo() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAI_DONGBO.SP_LOAI_DONGBO_INS", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }
    public Map<String, Object> capnhat_loaidongbo(CapNhatLoaiDongBoDtoIn loaiDongBo)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaidb_id", loaiDongBo.getLoaidbId() != null ? loaiDongBo.getLoaidbId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_dongbo", loaiDongBo.getLoaiDongbo() != null ? loaiDongBo.getLoaiDongbo() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAI_DONGBO.SP_LOAI_DONGBO_UPD", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }
    public Boolean xoa_loaidongbo(Long loaidbId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_loaidb_id", loaidbId != null ? loaidbId.toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_LOAI_DONGBO.SP_LOAI_DONGBO_DEL", parameters);
        if (rs != null && rs.size() > 0){
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
