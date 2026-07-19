package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class ToiUuTaiNguyenAoHoaService {
	private DbContext dbContext;

    public ToiUuTaiNguyenAoHoaService(DbContext dbContext) {
        this.dbContext = dbContext;
    }
    
    public List<Map<String, Object>> DsYeuCauToiUu(String thang, Long loaiToiUu, Long trangThaiXacNhan) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THANG", thang, Types.NVARCHAR));
        params.add(new SqlParameter("P_LOAI_TOIUU", loaiToiUu, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_XACNHAN", trangThaiXacNhan, Types.NUMERIC));
        params.add(new SqlParameter("P_IDENTIFY_CODE", AppRequestContext.getCurrent().getIdentityCode(), Types.NVARCHAR));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_TOIUU_TAINGUYEN.SP_DS_YEUCAU_TOIUU", params);
    }
    
    public List<Map<String, Object>> DsMayChuToiUu(String thang, Long loaiToiUu, Long trangThaiXacNhan) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THANG", thang, Types.NVARCHAR));
        params.add(new SqlParameter("P_LOAI_TOIUU", loaiToiUu, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_XACNHAN", trangThaiXacNhan, Types.NUMERIC));
        params.add(new SqlParameter("P_IDENTIFY_CODE", AppRequestContext.getCurrent().getIdentityCode(), Types.NVARCHAR));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_TOIUU_TAINGUYEN.SP_DS_MAYCHU_TOIUU", params);
    }
    
    public Boolean XacNhanToiUu(Long loaiXacNhan, String data) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAI_XACNHAN", loaiXacNhan, Types.NUMERIC));
        params.add(new SqlParameter("P_DATA", data, Types.CLOB));
        params.add(new SqlParameter("P_IDENTIFY_CODE", AppRequestContext.getCurrent().getIdentityCode(), Types.NVARCHAR));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        dbContext.executeStoredProcedure("ECMS.PKG_TOIUU_TAINGUYEN.SP_XACNHAN_TOIUU", params);
		return true;
    }
}
