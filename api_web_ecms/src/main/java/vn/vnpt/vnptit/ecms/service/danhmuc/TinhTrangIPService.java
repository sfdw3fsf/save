package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.TinhTrangIPDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map;

@Service
public class TinhTrangIPService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> getListIPStatus()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_TINHTRANG_IP_DANHSACH", parameters);
    }

    public Boolean update(String input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ds", input, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_TINHTRANG_IP_CAPNHAT", parameters);
        if (rs != null && !rs.isEmpty()){
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }/*

    public Boolean updateIpStatus(TinhTrangIPDTO input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input.getpId() != null ? input.getpId().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten", input.getpTen(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghi_chu", input.getpGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hieu_luc", input.getpHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_TINHTRANG_IP_CAPNHAT", parameters);
        if (rs != null && !rs.isEmpty()){
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    */

    public Boolean deleteIpStatus(Long input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_TINHTRANG_IP_DELETE", parameters);
        if (rs != null && !rs.isEmpty()){
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> getDSDanhMuc(String ds)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ds", ds, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_LOAD_DANHMUC", parameters);
    }

    public ArrayList<Map<String, Object>> getDSDanhMucKhac(String table, String conds)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_TABLE", table, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_COND", conds, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_2.SP_LOAD_DANHMUC_KHAC", parameters);
    }
}
