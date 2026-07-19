package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucParam;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucIDCService {

    private final DbContext dbContext;

    public DanhMucIDCService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getDataDanhMuc(String loaiDanhMuc, String thamSo1, String thamSo2) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_loai_danhmuc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so2", thamSo2, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DANHMUC.SP_DANHMUC_DANHSACH", params);
    }

    public Map<String, Object> getDataDanhMucV2(List<DanhMucParam> paramList) throws AppSqlException {
        Map<String, Object> result = new HashMap<>();
        if (paramList != null) {
            for (DanhMucParam param : paramList) {
                Object data = getDataDanhMuc(param.getLoaiDanhMuc(), param.getThamSo1(), param.getThamSo2());
                result.put(param.getLoaiDanhMuc(), data);
            }
        }
        return result;
    }

    public Object getDataDanhMucMatSan() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DANHMUC.SP_DANHMUC_MATSAN", params);
    }

    public Object getDataDanhMucHangSX() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DANHMUC.SP_DANHMUC_HANG_SX", params);
    }
}
