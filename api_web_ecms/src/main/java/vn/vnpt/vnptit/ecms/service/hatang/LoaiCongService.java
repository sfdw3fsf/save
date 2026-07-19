package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class LoaiCongService {
    private final DbContext dbContext;

    public LoaiCongService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getItems() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_LOAICONG_GET_ALL", params);
    }

    public Object getItem(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC.SP_LOAICONG_GET_ITEM", params);
    }

    public Object insert(String ten, Number phanLoai, Number thuocNhom, Number camModule, String tocDoPort, String ghiChu, Number hieuLuc) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));
        params.add(new SqlParameter("p_phanloai", phanLoai, Types.NUMERIC));
        params.add(new SqlParameter("p_thuocnhom", thuocNhom, Types.NUMERIC));
        params.add(new SqlParameter("p_cam_module", camModule, Types.NUMERIC));
        params.add(new SqlParameter("p_tocdo_port", tocDoPort, Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", hieuLuc, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_LOAICONG_INSERT", params);
        return result.getOutValue();
    }

    public boolean update(Number id, String ten, Number phanLoai, Number thuocNhom, Number camModule, String tocDoPort, String ghiChu, Number hieuLuc) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));
        params.add(new SqlParameter("p_phanloai", phanLoai, Types.NUMERIC));
        params.add(new SqlParameter("p_thuocnhom", thuocNhom, Types.NUMERIC));
        params.add(new SqlParameter("p_cam_module", camModule, Types.NUMERIC));
        params.add(new SqlParameter("p_tocdo_port", tocDoPort, Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", hieuLuc, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_LOAICONG_UPDATE", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean delete(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_LOAICONG_DELETE", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteItems(Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids",  StringUtils.join(ids, ";"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_LOAICONG_DELETE_ITEMS", params);
        return result.getOutValue().toString().equals("1");
    }
}
