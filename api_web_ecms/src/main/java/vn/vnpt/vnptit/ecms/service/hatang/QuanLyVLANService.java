package vn.vnpt.vnptit.ecms.service.hatang;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.hatang.InsertSubnetVLanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.UpsertVLANDto;

@Service
public class QuanLyVLANService {
        private final DbContext dbContext;

        public QuanLyVLANService(DbContext dbContext) {
                this.dbContext = dbContext;
        }

        public Object get_vlan_by_zone(Long id) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                params.add(new SqlParameter("p_zone_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                dbContext.setConnection(ConnectionManager.Connections.CSS);
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.SP_GET_VLAN_BY_ZONE",
                                params);
        }

        public ArrayList<Map<String, Object>> searchVLAN(JsonNode searchJsonData) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
                params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
                params.add(new SqlParameter("p_donvi_qly_id", AppRequestContext.getCurrent().getDonViID(),
                                ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

                ArrayList<Map<String, Object>> result = dbContext
                                .executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_SEARCH_VLAN", params);
                // normalize keys to lower case
                return result.stream()
                                .map(this::normalizeKeys)
                                .collect(Collectors.toCollection(ArrayList::new));
        }

        public Object upsertVLAN(UpsertVLANDto request) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_id", request.getId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ten", request.getTen(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_idc_id", request.getIdcId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_vrf_id", request.getVrfId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_zone_id", request.getZoneId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ghi_chu", request.getGhiChu(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_hieu_luc", request.getHieuLuc(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));

                SqlParameter result = new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.NUMERIC);
                params.add(result);

                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_IP.SP_UPSERT_VLAN", params);

                return result.getOutValue();
        }

        public Object deleteVLAN(Long vlanId) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vlan_id", vlanId, SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                SqlParameter result = new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.NUMERIC);
                params.add(result);
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_IP.SP_DELETE_VLAN", params);

                return result.getOutValue();
        }

        public ArrayList<Map<String, Object>> searchSubnet(JsonNode searchJsonData) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
                params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
                params.add(new SqlParameter("p_donvi_qly_id", AppRequestContext.getCurrent().getDonViID(),
                                ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

                ArrayList<Map<String, Object>> result = dbContext
                                .executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_SEARCH_SUBNET", params);
                // normalize keys to lower case
                return result.stream()
                                .map(this::normalizeKeys)
                                .collect(Collectors.toCollection(ArrayList::new));
        }

        public ArrayList<Map<String, Object>> getSubnetByVLan(Long id) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vlan_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                ArrayList<Map<String, Object>> result = dbContext
                                .executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_SUBNET_BY_VLAN",
                                                params);
                // normalize keys to lower case
                return result.stream()
                                .map(this::normalizeKeys)
                                .collect(Collectors.toCollection(ArrayList::new));
        }

        public Object insertSubnetVLan(InsertSubnetVLanDto request) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vlan_id", request.getVlanId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_subnet_id", request.getSubnetId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                SqlParameter result = new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.VARCHAR);
                params.add(result);
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_IP.SP_INSERT_SUBNET_VLAN", params);

                return result.getOutValue();
        }

        public boolean insertBatchSubnetVLan(Long vlanId, List<Integer> subnetList)
                        throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                try {
                        params.add(new SqlParameter("P_JSON", JsonConvert.serialize(subnetList),
                                        ParameterDirection.INPUT,
                                        Types.CLOB));
                } catch (Exception e) {
                        throw new AppSqlException(e);
                }
                params.add(new SqlParameter("P_VLAN_ID", vlanId, ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                                ParameterDirection.INPUT, Types.VARCHAR));

                SqlParameter result = new SqlParameter("P_RES", null, ParameterDirection.OUTPUT, Types.NUMERIC);
                params.add(result);

                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_IP.SP_BATCH_SUBNET_VLAN", params);

                BigDecimal rs = (BigDecimal) result.getOutValue();
                return rs != null && rs.intValue() == 1;
        }

        public Object deleteSubnetVLan(Long subnetVLanId) throws AppSqlException {
                List<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_subnet_vlan_id", subnetVLanId, SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                ParameterDirection.INPUT, Types.VARCHAR));
                SqlParameter result = new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.VARCHAR);
                params.add(result);
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_IP.SP_DELETE_SUBNET_VLAN", params);

                return result.getOutValue();
        }

        private Map<String, Object> normalizeKeys(Map<String, Object> dbResult) {
                if (dbResult == null) {
                        return new LinkedHashMap<>();
                }

                return dbResult.entrySet().stream()
                                .filter(e -> e.getValue() != null)
                                .collect(Collectors.toMap(
                                                e -> e.getKey().toLowerCase(),
                                                Map.Entry::getValue,
                                                (v1, v2) -> v1,
                                                LinkedHashMap::new));
        }
}
