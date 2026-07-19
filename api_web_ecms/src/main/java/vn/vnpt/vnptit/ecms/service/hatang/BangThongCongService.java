package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.BangThongCongDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class BangThongCongService {
    private final DbContext dbContext;

    public BangThongCongService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getItems() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_hatang_danhmuc.sp_bangthong_cong_get_all", params);
    }

    public Object insert(BangThongCongDto item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", item.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_bang_thong", item.getBangThong(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", item.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", item.getHieuLuc(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc.sp_bangthong_cong_insert", params);
        return result.getOutValue();
    }

    public Object update(BangThongCongDto item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", item.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", item.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_bang_thong", item.getBangThong(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", item.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", item.getHieuLuc(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc.sp_bangthong_cong_update", params);
        return result.getOutValue();
    }

    public Object delete(String ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", ids, Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc.sp_bangthong_cong_delete", params);
        return result.getOutValue();
    }
}
