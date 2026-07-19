package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.Utils.ConstantString;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.ThucHienToiUuTaiNguyen;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
@Service
public class ThucHienToiUuTaiNguyenService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> dsYeuCauToiUu(ThucHienToiUuTaiNguyen thucHienToiUuTaiNguyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter(ConstantString.P_RSOUT, null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        parameters.add(new SqlParameter(ConstantString.P_IDENTIFY_CODE, AppRequestContext.getCurrent().getIdentityCode(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_thang_nam", thucHienToiUuTaiNguyen.getThang_nam(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_toiuu", thucHienToiUuTaiNguyen.getLoai_toiuu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_trangthai_toiuu", thucHienToiUuTaiNguyen.getTrangthai_xacnhan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_thuchien_toiuu_tainguyen.sp_danhsach_yeucau_toiuu", parameters);
    }

    public ArrayList<Map<String, Object>> xacNhanToiUu(ThucHienToiUuTaiNguyen thucHienToiUuTaiNguyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter(ConstantString.P_RSOUT, null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        parameters.add(new SqlParameter(ConstantString.P_IDENTIFY_CODE, AppRequestContext.getCurrent().getIdentityCode(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id_yeucau", thucHienToiUuTaiNguyen.getPhieu_toiuu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_thuchien_toiuu_tainguyen.sp_xacnhan_toiuu", parameters);
    }
    public ArrayList<Map<String, Object>> dsMayAo(ThucHienToiUuTaiNguyen thucHienToiUuTaiNguyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter(ConstantString.P_RSOUT, null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        parameters.add(new SqlParameter(ConstantString.P_IDENTIFY_CODE, AppRequestContext.getCurrent().getIdentityCode(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id_yeucau", thucHienToiUuTaiNguyen.getPhieu_toiuu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_thuchien_toiuu_tainguyen.sp_danhsach_mayao_toiuu", parameters);
    }
    public ArrayList<Map<String, Object>> updatePhieuToiUu(ThucHienToiUuTaiNguyen thucHienToiUuTaiNguyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter(ConstantString.P_RSOUT, null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        parameters.add(new SqlParameter(ConstantString.P_IDENTIFY_CODE, AppRequestContext.getCurrent().getIdentityCode(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id_yeucau", thucHienToiUuTaiNguyen.getPhieu_toiuu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String p_thongtin_mayao = objectMapper.writeValueAsString(thucHienToiUuTaiNguyen.getDanhsach_mayao());
        parameters.add(new SqlParameter("p_thongtin_mayao", p_thongtin_mayao, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        }catch (Exception e) {
			// TODO: handle exception
		}
        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_thuchien_toiuu_tainguyen.sp_update_phieu_toiuu", parameters);
    }
}
