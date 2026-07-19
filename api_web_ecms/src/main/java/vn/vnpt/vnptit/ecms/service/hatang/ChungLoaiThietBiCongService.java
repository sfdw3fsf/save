package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ChungLoaiThietBiCongService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> layDSThietBiDauNoiCong(Long id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.SP_LAY_DANHSACH_THIETBI_DAUNOI_CHUNGLOAI_THIETBI_CONG", parameters);
    }

    public ArrayList<Map<String, Object>> layDSChungLoaiThietBiCong(Long id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.SP_LAY_THONGTIN_CHUNGLOAI_THIETBI_CONG_ID", parameters);
    }
    public String capNhatChungLoaiThietBiCong(String pDs,Long pId) throws AppSqlException {

        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thongtin", pDs, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_id", pId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_QUANLY.sp_capnhat_thongtin_chungloai_thietbi", parameters);
        return "OK";
    }
    public ArrayList<Map<String, Object>> layDSLichSuThayDoiChungLoaiThietBiCong(Long id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.SP_LAY_THONGTIN_LICHSU_THAYDOI_CHUNGLOAI_THIETBI_CONG_ID", parameters);
    }

}
