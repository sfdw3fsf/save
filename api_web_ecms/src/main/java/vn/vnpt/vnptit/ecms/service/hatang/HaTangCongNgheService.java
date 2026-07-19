package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import vn.vnpt.vnptit.ecms.dto.hatang.HaTangCongNgheInDTO;

@Service
public class HaTangCongNgheService {
    private final DbContext dbContext;

    public HaTangCongNgheService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_hatang_congnghe_get_all", params);
    }

    public Object getValid() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_hatang_congnghe_hieuluc",
                params);
    }
    public Object getDmHaTang() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_danhmuc_hatang",
                params);
    }
    public Object getItem(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC.sp_hatang_congnghe_by_id", params);
    }

    public Object update(HaTangCongNgheInDTO haTangCongNgheInDTO) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        SqlParameter result = new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(result);
        params.add(new SqlParameter("p_id", haTangCongNgheInDTO.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", haTangCongNgheInDTO.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hatang", haTangCongNgheInDTO.getHaTang(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", haTangCongNgheInDTO.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", haTangCongNgheInDTO.getHieuLuc(), Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC.sp_hatang_connghe_update", params);

    }

    public boolean delete(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        ArrayList<Map<String, Object>> rs = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_hatang_congnghe_delete", params);
        if (rs != null && !rs.isEmpty() && "1".equals(String.valueOf(rs.get(0).get("item"))))
            return Boolean.TRUE;

        return Boolean.FALSE;

    }
}
