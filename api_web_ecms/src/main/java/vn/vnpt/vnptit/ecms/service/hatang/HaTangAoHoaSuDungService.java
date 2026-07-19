package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import vn.vnpt.vnptit.ecms.dto.hatang.HaTangCongNgheInDTO;

@Service
public class HaTangAoHoaSuDungService {
    private final DbContext dbContext;

    public HaTangAoHoaSuDungService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getDataHaTangAoHoaSuDung(String loaiDanhMuc, Long thamSo1) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_danhmuc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_aohoa_sudung_danhsach", params);
    }
    public Object getDataHistoryAction(String loaiDanhMuc, Long pId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_danhmuc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id", pId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_lichsu_thaydoi", params);
    }
}
