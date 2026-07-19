package vn.vnpt.vnptit.qlsc.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.*;

@Service
public class DanhMucHopDongDauTuService {
    private final DbContext dbContext;
    public DanhMucHopDongDauTuService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object danhMucHopDong(String chuoi) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vchuoi", chuoi, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("nhom_da", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "nhom_da"));
        params.add(new SqlParameter("toanha", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "toanha"));
        params.add(new SqlParameter("hinhthuc_ht", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "hinhthuc_ht"));
        params.add(new SqlParameter("hinhthuc_pcdt", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "hinhthuc_pcdt"));
        params.add(new SqlParameter("hinhthuc_dautu", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "hinhthuc_dautu"));
        params.add(new SqlParameter("tinhtrang", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "tinhtrang"));
        params.add(new SqlParameter("nganhang", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "nganhang"));
        params.add(new SqlParameter("nhanvien_ql", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "nhanvien_ql"));
        params.add(new SqlParameter("nhanvien_doisoat", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "nhanvien_doisoat"));
        params.add(new SqlParameter("chuky_tt", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "chuky_tt"));
        return dbContext.executeSpIdxWithCursorsToListMap("CSS.QUANLY_TOANHA.lay_tt_danhmuc_hopdong", params);
    }

    public Object dsHopDongDauTu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.QUANLY_TOANHA.lay_tt_hd_dautu", params);
    }
}
