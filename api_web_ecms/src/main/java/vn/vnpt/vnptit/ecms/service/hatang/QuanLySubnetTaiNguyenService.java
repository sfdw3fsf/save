
package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;

@Service
public class QuanLySubnetTaiNguyenService {

    @Autowired
    private DbContext dbContext;

    public List<Map<String, Object>> get_ds_subnet_tainguyen() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", Types.REF_CURSOR));   // OUT
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_ALL_SUBNET", params);
    }

    public List<Map<String, Object>> get_ds_subnet_droplist() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", Types.REF_CURSOR));   // OUT
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_ALL_SUBNET_DROPLIST", params);
    }

    public List<Map<String, Object>> get_ds_htm_droplist() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", Types.REF_CURSOR));   // OUT
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_ALL_HATANG_MANG_DROPLIST", params);
    }

    public List<Map<String, Object>> getHtmDroplistByIdc(Integer idcId)
            throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter(
                "P_IDC_ID",
                idcId,
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));
        params.add(new SqlParameter(
                "P_RS",
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_HATANG_MANG_BY_IDC",
                params
        );
    }


    public List<Map<String, Object>> getSubnetConByParentId(Integer parentId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_PARENT_ID", parentId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC
        ));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_SUBNET_BY_PARENT",
                params
        );
    }

    public Map<String, Object> getSubnetById(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_ID", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> result =
                dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_SUBNET_BY_ID", params);

        if (result == null || result.isEmpty()) {
            return null;
        }

        Map<String, Object> base = new LinkedHashMap<>(result.get(0));

        base.remove("VLAN_ID");
        base.remove("VLAN_TEN");
        base.remove("VLAN_HIEU_LUC");
        base.remove("IDC_TEN");

        // --- Build VLAN array ---
        List<Map<String, Object>> vlanList = new ArrayList<>();

        for (Map<String, Object> row : result) {

            Object vlanId = row.get("VLAN_ID");

            if (vlanId == null) {
                continue;
            }

            Map<String, Object> vlan = new LinkedHashMap<>();
            vlan.put("VLAN_ID", vlanId);
            vlan.put("VLAN_TEN", row.get("VLAN_TEN"));
            vlan.put("VLAN_HIEU_LUC", row.get("VLAN_HIEU_LUC"));
            vlan.put("IDC_TEN", row.get("IDC_TEN"));

            vlanList.add(vlan);
        }
        base.put("VLAN", vlanList);
        return base;
    }


    public List<Map<String, Object>> getPhanLoaiIP() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_RS",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_DS_PHANLOAI_IP",
                params
        );
    }

    public List<Map<String, Object>> getLoaiIP() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "P_RS",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_DS_LOAI_IP",
                params
        );
    }

    public List<Map<String, Object>> get_ds_vlan_droplist() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", Types.REF_CURSOR));   // OUT
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_VLAN_DROPLIST", params);
    }

    public List<Map<String, Object>> getVlanDroplistByIdc(Integer idcId)
            throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter(
                "P_IDC_ID",
                idcId,
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));
        params.add(new SqlParameter(
                "P_RS",
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_VLAN_DROPLIST_BY_IDC",
                params
        );
    }


    public Map<String, Object> createSubnet(Map<String, Object> payload) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_TEN", payload.get("TEN"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HTM_ID", payload.get("HTM_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIIP_ID", payload.get("LOAIIP_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAIIP_ID", payload.get("PHANLOAIIP_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GATEWAY", payload.get("GATEWAY"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUBNET", payload.get("SUBNET"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SBMASK", payload.get("SBMASK"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IDC_ID", payload.get("IDC_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SNCHA_ID", payload.get("SNCHA_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SINH_IP", payload.get("SINH_IP"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_QUANGBA_IP", payload.get("QUANGBA_IP"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_CCDV_ID", payload.get("TT_CCDV_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDICHSD_ID", payload.get("MUCDICHSD_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THAMCHIEU", payload.get("THAMCHIEU"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HIEU_LUC", payload.get("HIEU_LUC"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHI_CHU", payload.get("GHI_CHU"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NOIBO", payload.get("NOIBO"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DONVIQL_ID", payload.get("DONVIQL_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // --- NEW: send VLAN list as JSON ---
        Object vlanObj = payload.get("VLAN");
        String vlanJson = null;

        try {
            vlanJson = (vlanObj == null) ? null : JsonConvert.serialize(vlanObj);
        } catch (IOException e) {
            throw new AppSqlException(e);
        }

        params.add(new SqlParameter(
                "P_VLAN_JSON",
                vlanJson,
                SqlParameter.ParameterDirection.INPUT,
                Types.CLOB
        ));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> rs =
                dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_CREATE_SUBNET", params);

        if (rs == null || rs.isEmpty()) {
            return null;
        }
        return rs.get(0);
    }


    public void updateSubnetVlan(Integer subnetId, List<Map<String, Object>> vlanList)
            throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", subnetId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        try {
            params.add(new SqlParameter(
                    "P_JSON",
                    JsonConvert.serialize(vlanList),
                    SqlParameter.ParameterDirection.INPUT,
                    Types.CLOB
            ));
        } catch (IOException e) {
            throw new AppSqlException(e);
        }

        params.add(new SqlParameter("P_NGUOI_CN", "CSS", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter out = new SqlParameter("P_RES", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_IP.SP_UPDATE_SUBNET_VLAN", params);

        BigDecimal res = (BigDecimal) out.getOutValue();
        if (res == null || res.intValue() != 1)
            throw new AppSqlException("Update VLAN failed");
    }

    public Map<String, Object> updateSubnet(Integer id, Map<String, Object> payload) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_ID", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", payload.get("TEN"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HTM_ID", payload.get("HTM_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIIP_ID", payload.get("LOAIIP_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAIIP_ID", payload.get("PHANLOAIIP_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GATEWAY", payload.get("GATEWAY"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUBNET", payload.get("SUBNET"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SBMASK", payload.get("SBMASK"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IDC_ID", payload.get("IDC_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SNCHA_ID", payload.get("SNCHA_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SINH_IP", payload.get("SINH_IP"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_QUANGBA_IP", payload.get("QUANGBA_IP"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_CCDV_ID", payload.get("TT_CCDV_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDICHSD_ID", payload.get("MUCDICHSD_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THAMCHIEU", payload.get("THAMCHIEU"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HIEU_LUC", payload.get("HIEU_LUC"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHI_CHU", payload.get("GHI_CHU"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NOIBO", payload.get("NOIBO"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DONVIQL_ID", payload.get("DONVIQL_ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        List<Map<String, Object>> rs =
                dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_UPDATE_SUBNET", params);

        if (rs == null || rs.isEmpty()) {
            return null;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> vlanList = (List<Map<String, Object>>) payload.get("VLAN");

        updateSubnetVlan(id, vlanList);

        return rs.get(0);
    }

    //Get danh sách ip dựa vào subnet và subnet con của subnet
    public List<Map<String, Object>> getSubnetAndIps(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_SUBNET_AND_IPS", params);
    }

    //Get Vrf by subnet Id
    public List<Map<String, Object>> getVrfBySubnetId(Integer parentId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", parentId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC
        ));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR
        ));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_VRF_BY_SUBNET",
                params
        );
    }

    //Get Zone By Subnet Id
    public List<Map<String, Object>> getZoneBySubnetId(Integer parentId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", parentId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC
        ));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR
        ));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_ZONE_BY_SUBNET",
                params
        );
    }

    public List<Map<String, Object>> generateIp(Integer subnetId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", subnetId,
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));

        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_RS", null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_SINH_IP_TU_SUBNET",
                params
        );
    }

    //Get Lich Su Tac Dong By Subnet Id
    public List<Map<String, Object>> getLichSuBySubnetId(Integer subnetId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", subnetId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC
        ));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR
        ));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.GET_LICHSU_TACDONG_BY_KHOA",
                params
        );
    }

    public List<Map<String, Object>> deleteSubnet(Integer subnetId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", subnetId,
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));

        params.add(new SqlParameter("P_RS", null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_DELETE_SUBNET",
                params
        );
    }

    public List<Map<String, Object>> searchSubnet(String searchJson)
            throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "P_SEARCH_JSON",
                searchJson,
                SqlParameter.ParameterDirection.INPUT,
                Types.CLOB
        ));

        params.add(new SqlParameter(
                "P_RS",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_SEARCH_SUBNET",
                params
        );
    }


    //Get Lich Su Tac Dong By Subnet Id
    public List<Map<String, Object>> getThietBiBySubnetId(Integer subnetId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_SUBNET_ID", subnetId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC
        ));

        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR
        ));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_THIETBI_BY_SUBNET",
                params
        );
    }


}
