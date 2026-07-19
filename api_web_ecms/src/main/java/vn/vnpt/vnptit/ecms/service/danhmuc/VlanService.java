package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.VlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.hatang.DMLienQuanVlanDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class VlanService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> layDs() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_DS_VLAN", parameters);
    }

    public Boolean updateInsert(VlanDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_identity_code",  AppRequestContext.getCurrent().getIdentityCode(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id", input.getId() != null ? input.getId().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_idc_id", input.getIdc_id() != null ? input.getIdc_id().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thietbi_id", input.getThietbi_id() != null ? input.getThietbi_id().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_vrf_id", input.getVrf_id() != null ? input.getVrf_id().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_zone_id", input.getZone_id() != null ? input.getZone_id().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghi_chu", input.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_hieu_luc", input.getHieu_luc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_VLAN_UPDATE_INSERT", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean xoa(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_VLAN_DELETE", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> layDsDmLienQuan(DMLienQuanVlanDtoIn dmLienQuanVlanDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_vlan_id", dmLienQuanVlanDtoIn.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_danhmuc", dmLienQuanVlanDtoIn.getDanhmuc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_DANHMUC_LIENQUAN_QUANLY_VLAN", parameters);
    }

    public ArrayList<Map<String, Object>>  tt_subnet(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_vlan_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.SP_TT_SUBNET_FROM_VLAN", parameters);
    }
}
