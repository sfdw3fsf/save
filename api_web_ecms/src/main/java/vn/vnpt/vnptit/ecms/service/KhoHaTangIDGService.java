package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangIDGThietBiDTO;

@Service
public class KhoHaTangIDGService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> getListKhoHaTangIDG()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_KHOHATANG_IDG_DANHSACH", parameters);
    }

    public ArrayList<Map<String, Object>> getKhoHaTangIDG(Number id)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", id, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_KHOHATANG_IDG_ITEM", parameters);
    }

    public Boolean updateIpStatus(String input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ds", input, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_KHOHATANG_IDG_UPDATE", parameters);
        if (rs != null && !rs.isEmpty()){
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean deleteIDG(Long input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_out", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_KHOHATANG_IDG_DELETE", parameters);
        if (rs != null && !rs.isEmpty()){
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> hatangAohoaTheoIDC()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_HATANGAOHOA_THEO_IDC", parameters);
    }

    public ArrayList<Map<String, Object>> hatangSmartCloudTheoIDC()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_HATANGSMARTCLOUD_THEO_IDC", parameters);
    }

    public ArrayList<Map<String, Object>> hatangIDGTheoIDC()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_HATANGIDG_THEO_IDC", parameters);
    }
    public ArrayList<Map<String, Object>> lichsuHatangIDG()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_KHOHATANG_IDG_LICHSU", parameters);
    }

    public ArrayList<Map<String, Object>> aoHoaIDGThietBi(String input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_hatang_id", input, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_GET_THIETBI_IDG", parameters);
    }

    public ArrayList<Map<String, Object>> aoHoaIDGThietBiThem(KhoHaTangIDGThietBiDTO input) throws AppSqlException {
        // Validate input
        if (input == null || input.getThietBiHinhThanhList() == null || input.getThietBiHinhThanhList().isEmpty()) {
            throw new IllegalArgumentException("ThietBiHinhThanhList cannot be null or empty");
        }
        
        input.getThietBiHinhThanhList().forEach(item -> {
            List<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_hatang_id", item.getId(), Types.NUMERIC));
            parameters.add(new SqlParameter("p_thietbi_id", item.getThietbi_id(), Types.NUMERIC));
            parameters.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
            parameters.add(new SqlParameter("p_ngay_cn", item.getNgay_cn(), Types.NUMERIC));

            parameters.add(new SqlParameter("p_result",  Types.NUMERIC));
            try {
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_2.SP_THIETBI_HINHTHANH_HATANG_IDG_THIETBI_INSERT", parameters);
            } catch (AppSqlException e) {
                throw new RuntimeException(e);
            }
        });
        return aoHoaIDGThietBi(String.valueOf(input.getThietBiHinhThanhList().get(0).getId()));
    }

    public Object deleteThietBiIDG(String id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_HATANGIDG_THIETBI_DELETE", params);
        return Boolean.TRUE;
    }
}
