package vn.vnpt.vnptit.qlsc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.vnpt.Utils.LoggingManaged;
import vn.vnpt.common.success.model.HttpManaged;
import org.springframework.web.client.RestTemplate;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GiaoPhieuXuLySuCoTtsService {

    private static final Logger logger = LoggerFactory.getLogger(GiaoPhieuXuLySuCoTtsService.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LoggingManaged loggingManaged;

    @Value("${internal.appcom.url}")
    private String appcomUrl;

    private final DbContext dbContext;

    public GiaoPhieuXuLySuCoTtsService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object layDsPhieuXuLyNet(LayDsPhieuXuLyNetDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VNGUONSC_ID", input.getNguonsc_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VMA_SC", input.getMa_sc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VNGAYGIAO", input.getNgaygiao(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("VTRANGTHAI", input.getTrangthai(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VMUCDOSC_ID", input.getMucdosc_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VKEODAI", input.getKeodai(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VHUONGGIAO_ID", input.getHuonggiao_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        // Bo sung 14/12 theo yeu cau
        params.add(new SqlParameter("VNHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(
                new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        // return
        // dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_TTS.LAY_DS_PHIEU_XULY_NET",
        // params);
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_TTS.LAY_DS_PHIEU_XULY_NET_V2", params);
    }

    public Object layDsQtBuocXuLy(LayDsQtBuocXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MA_QT", input.getMa_qt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(
                new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DS_QT_BUOC_XULY", params);
    }



    public Object layDsQtBuocXuLyV2(LayDsQtBuocXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MA_QT", input.getMa_qt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DS_QT_BUOC_XULY_v2", params);
    }

    public Object lay_ds_phieu_dagiao(Long suco_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VSUCO_ID", suco_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(
                new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_TTS.LAY_DS_PHIEU_DAGIAO", params);
    }

    public Object lay_donvi_theo_buoc_xuly(LayDsQtBuocXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MA_QT", input.getMa_qt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(
                new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DONVI_THEO_BUOC_XULY", params);
    }

    public Object giaophieu_xuly_net(GiaoPhieuXuLyNetDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTYPE", input.getType(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VPHIEU_CHA_ID", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VND_GIAO", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VDONVI_GIAO_ID", AppRequestContext.getCurrent().getDonViID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VDONVI_NHAN_ID", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VNHANVIEN_GIAO_ID", AppRequestContext.getCurrent().getNhanVienID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VMA_QUYTRINH", input.getMa_quytrinh(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("VMA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VEMAIL_NHANVIEN", AppRequestContext.getCurrent().getMaNhanVien(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VMAY_CN", AppRequestContext.getCurrent().getClientName(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VIP_CN", AppRequestContext.getCurrent().getClientIpAddress(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(
                new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.GIAOPHIEU_XULY_NET", params);
    }

    public Object giaophieu_xuly_net_v2(GiaoPhieuXuLyNetDto input) throws AppSqlException {
        Map logData = new HashMap();
        try {
            logData.put("action", "giaophieu_xuly_net_v2");
            logData.put("nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien());
            try {
                logData.put("input", objectMapper.writeValueAsString(input));
            } catch (Exception ignored) {
            }

            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VTYPE", input.getType(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VPHIEU_CHA_ID", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VND_GIAO", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VDONVI_GIAO_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VDONVI_NHAN_ID", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VNHANVIEN_GIAO_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VMA_QUYTRINH", input.getMa_quytrinh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("VMA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VEMAIL_NHANVIEN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VMAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VIP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
            ArrayList<Map<String, Object>> data = dbContext.executeSpWithCursorToListMap("QLSC.GIAOPHIEU_XULY_NET", params);

            try {
                logData.put("GIAOPHIEU_XULY_NET", objectMapper.writeValueAsString(data));
            } catch (Exception ignored) {
            }

            int syncStatus = -1;
            String syncResponse = "";
            try {
                if (!data.isEmpty()) {
                    for (int i=0; i<data.size(); i++) {
                        Map<String, Object> item = data.get(i);
                        if (item.get("JSON_INPUT") != null && !"".equals(item.get("JSON_INPUT").toString())) {
                            try {
                                Map<String, String> body = new HashMap<>();
                                body.put("data", item.get("JSON_INPUT").toString());
                                String resp = HttpManaged.post("http://tichhop/tts/saveCSSAPI", objectMapper.writeValueAsString(body));
                                logData.put("respTTS-" + i, resp);
                                Map res = objectMapper.readValue(resp, Map.class);
                                if ("0".equals(res.get("errorCode").toString())) {
                                    syncStatus = 1;
                                    syncResponse = objectMapper.writeValueAsString(res.get("data"));
                                } else {
                                    syncResponse = res.get("faultString").toString();
                                }
                            } catch (Exception ex) {
                                syncResponse = ex.getMessage();
                            }

                            ArrayList<SqlParameter> paramsTTS = new ArrayList<SqlParameter>();
                            paramsTTS.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                            paramsTTS.add(new SqlParameter("p_parent_id", item.get("PARENT_ID"), java.sql.Types.NUMERIC));
                            paramsTTS.add(new SqlParameter("p_css_id", item.get("CSS_ID"), java.sql.Types.NUMERIC));
                            paramsTTS.add(new SqlParameter("p_sync_status", syncStatus, java.sql.Types.INTEGER));
                            paramsTTS.add(new SqlParameter("p_jsinput", item.get("JSON_INPUT").toString(), Types.NVARCHAR));
                            paramsTTS.add(new SqlParameter("p_response", syncResponse, java.sql.Types.NVARCHAR));
                            dbContext.executeStoredProcedure("QLSC.sp_capnhat_trangthai_log_tts_v2", paramsTTS);
                            logData.put("sp_capnhat_trangthai_log_tts_v2", "success");
                        }
                    }
                }
            } catch (Exception ex) {
                logData.put("errorTTS", ex.getMessage());
            }

            return data;
        } catch (Exception ex) {
            logData.put("error", ex.getMessage());
            throw ex;
        } finally {
            loggingManaged.info(logData);
        }
    }

    @SuppressWarnings("unchecked")
    public Object giaophieu_xuly_net_v3(GiaoPhieuXuLyNetV3Dto input) throws AppSqlException, JsonProcessingException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTYPE", input.getType(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VPHIEU_CHA_ID", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VND_GIAO", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VDONVI_GIAO_ID", AppRequestContext.getCurrent().getDonViID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VDONVI_NHAN_ID", input.getDonvi_nhan_id(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VNHANVIEN_GIAO_ID", AppRequestContext.getCurrent().getNhanVienID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VMA_QUYTRINH", input.getMa_quytrinh(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("VMA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VEMAIL_NHANVIEN", AppRequestContext.getCurrent().getMaNhanVien(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VMAY_CN", AppRequestContext.getCurrent().getClientName(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VIP_CN", AppRequestContext.getCurrent().getClientIpAddress(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT,
                java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> data = dbContext.executeSpWithCursorToListMap("QLSC.GIAOPHIEU_XULY_NET",
                params);
        if (!data.isEmpty()) {
            for (Map<String, Object> item : data) {
                if (item.get("JSON_INPUT") != null && !"".equals(item.get("JSON_INPUT").toString())) {
                    // Parse JSON_INPUT từ SP, uppercase toàn bộ keys
                    String jsonInputStr = item.get("JSON_INPUT").toString().replaceAll("[\\x00-\\x1F]", "");
                    Map<String, Object> rawMap = objectMapper.readValue(
                            jsonInputStr, Map.class);
                    Map<String, Object> jsonMap = toUpperCaseKeys(rawMap);
                    String maQuyTrinhSp = jsonMap.get("MA_QUYTRINH") != null
                            ? jsonMap.get("MA_QUYTRINH").toString() : "";
                    boolean isC3ClmO3 = "C3_CLM_03".equals(maQuyTrinhSp);
                    if (isC3ClmO3) {
                        savePadxCellClk(item, input);
                    }
                }
            }
        }
        return data;
    }

    private static final Set<String> MA_QUYTRINH_SEND_TTS = new HashSet<>(
            java.util.Arrays.asList("QT1.6.1", "QTXLSC_MANE", "QTXLSC_ACCESS", "QT1.4", "C3_CLM_03"));

    @SuppressWarnings("unchecked")
    public Object giaophieu_xuly_net_v4(GiaoPhieuXuLyNetV3Dto input) throws AppSqlException, JsonProcessingException {
        Map logData = new HashMap();
        try {
            logData.put("action", "giaophieu_xuly_net_v4");
            logData.put("nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien());
            try {
                logData.put("input", objectMapper.writeValueAsString(input));
            } catch (Exception ignored) {
            }

            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VTYPE", input.getType(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VPHIEU_CHA_ID", input.getPhieu_cha_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VND_GIAO", input.getNd_giao(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VDONVI_GIAO_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VDONVI_NHAN_ID", input.getDonvi_nhan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VNHANVIEN_GIAO_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("VMA_QUYTRINH", input.getMa_quytrinh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("VMA_BUOC", input.getMa_buoc(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VEMAIL_NHANVIEN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VMAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("VIP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
            params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
            ArrayList<Map<String, Object>> data = dbContext.executeSpWithCursorToListMap("QLSC.GIAOPHIEU_XULY_NET", params);

            try {
                logData.put("GIAOPHIEU_XULY_NET", objectMapper.writeValueAsString(data));
            } catch (Exception ignored) {
            }

            int syncStatus = -1;
            String syncResponse = "";
            try {
                if (!data.isEmpty()) {
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, Object> item = data.get(i);
                        if (item.get("JSON_INPUT") != null && !"".equals(item.get("JSON_INPUT").toString())) {
                            // Lấy ma_quytrinh: ưu tiên input, fallback từ JSON_INPUT SP
                            String maQuyTrinh = (input.getMa_quytrinh() != null && !input.getMa_quytrinh().isEmpty())
                                    ? input.getMa_quytrinh() : "";
                            Map<String, Object> jsonMap = null;
                            try {
                                String jsonInputStr = item.get("JSON_INPUT").toString().replaceAll("[\\x00-\\x1F]", " ");
                                Map<String, Object> rawMap = objectMapper.readValue(
                                        jsonInputStr, Map.class);
                                jsonMap = toUpperCaseKeys(rawMap);
                                if (maQuyTrinh.isEmpty()) {
                                    maQuyTrinh = jsonMap.get("MA_QUYTRINH") != null
                                            ? jsonMap.get("MA_QUYTRINH").toString() : "";
                                }
                                if ("C3_CLM_03".equals(maQuyTrinh)) {
                                    savePadxCellClk(item, input);
                                }
                            } catch (Exception ignored) {
                            }

                            boolean isSendTts = MA_QUYTRINH_SEND_TTS.contains(maQuyTrinh);

                            if (isSendTts) {
                                // Goi app-com voi cac quy trinh chuyen de 5
                                try {
                                    Object cssIdObj = item.get("CSS_ID");
                                    if (cssIdObj != null && !cssIdObj.toString().isEmpty()
                                            && !"null".equals(cssIdObj.toString())) {
                                        Long cssId = Long.parseLong(cssIdObj.toString());
                                        String appcomResp = callPostNetDhsxToTts(cssId);
                                        logData.put("appcom_resp-" + i, appcomResp);
                                        // Nếu response có status = 1 thì cập nhật api_net_problems
                                        try {
                                            if (appcomResp != null && !appcomResp.startsWith("error:")) {
                                                Map<String, Object> respMap = objectMapper.readValue(appcomResp, Map.class);
                                                Object statusVal = respMap.get("status");
                                                if (statusVal != null && "1".equals(statusVal.toString())) {
                                                    capnhat_api_net_problems(cssId); 
                                                }
                                            }
                                        } catch (Exception exNet) {
                                        }
                                    }
                                } catch (Exception ex) {
                                    logData.put("errorAppcom-" + i, ex.getMessage());
                                }
                            } else {
                                // Goi TTS qua tichhop theo luong hien trang
                                try {
                                    Map<String, String> body = new HashMap<>();
                                    body.put("data", item.get("JSON_INPUT").toString());
                                    String resp = vn.vnpt.common.success.model.HttpManaged.post(
                                            "http://tichhop/tts/saveCSSAPI", objectMapper.writeValueAsString(body));
                                    logData.put("respTTS-" + i, resp);
                                    Map res = objectMapper.readValue(resp, Map.class);
                                    if ("0".equals(res.get("errorCode").toString())) {
                                        syncStatus = 1;
                                        syncResponse = objectMapper.writeValueAsString(res.get("data"));
                                    } else {
                                        syncResponse = res.get("faultString").toString();
                                    }
                                } catch (Exception ex) {
                                    syncResponse = ex.getMessage();
                                }

                                // Cập nhật trạng thái log TTS
                                ArrayList<SqlParameter> paramsTTS = new ArrayList<SqlParameter>();
                                paramsTTS.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                paramsTTS.add(new SqlParameter("p_parent_id", item.get("PARENT_ID"), java.sql.Types.NUMERIC));
                                paramsTTS.add(new SqlParameter("p_css_id", item.get("CSS_ID"), java.sql.Types.NUMERIC));
                                paramsTTS.add(new SqlParameter("p_sync_status", syncStatus, java.sql.Types.INTEGER));
                                paramsTTS.add(new SqlParameter("p_jsinput", item.get("JSON_INPUT").toString(), Types.NVARCHAR));
                                paramsTTS.add(new SqlParameter("p_response", syncResponse, java.sql.Types.NVARCHAR));
                                dbContext.executeStoredProcedure("QLSC.sp_capnhat_trangthai_log_tts_v2", paramsTTS);
                                logData.put("sp_capnhat_trangthai_log_tts_v2", "success");
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                logData.put("errorTTS", ex.getMessage());
            }

            return data;
        } catch (Exception ex) {
            logData.put("error", ex.getMessage());
            throw ex;
        } finally {
            try {
                logger.info(objectMapper.writeValueAsString(logData));
            } catch (Exception ignored) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    private String callPostNetDhsxToTts(Long cssId) {
        try {
            ArrayList<Map<String, Object>> jsonList = (ArrayList<Map<String, Object>>) get_json_dhsx_send_to_tts(cssId);
            if (jsonList == null || jsonList.isEmpty()) return "no data from GET_JSON_DHSX_SEND_TO_TTS";

            String jsonData = objectMapper.writeValueAsString(jsonList.get(0));

            Map<String, String> body = new HashMap<>();
            body.put("jsonData", jsonData);
            String bodyJson = objectMapper.writeValueAsString(body);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            AppRequestContext current = AppRequestContext.getCurrent();
            if (current.getToken() != null && current.getToken().getValue() != null) {
                httpHeaders.set("Authorization", current.getToken().getValue());
            }

            String apiUrl = appcomUrl + "/fms_alarms/post_net_dhsx_to_tts";
            logger.info("[post_net_dhsx_to_tts] URL: " + apiUrl + " | Body: " + bodyJson);

            HttpEntity<String> request = new HttpEntity<>(bodyJson, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            String resp = response.getBody();
            logger.info("[post_net_dhsx_to_tts] Response: " + resp);
            return resp;
        } catch (Exception ex) {
            logger.error("[post_net_dhsx_to_tts] Error: " + ex.getMessage(), ex);
            return "error: " + ex.getMessage();
        }
    }

    public Object get_json_dhsx_send_to_tts(Long css_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_CSS_ID", css_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_TTS.GET_JSON_DHSX_SEND_TO_TTS", params);
    }

    public Object capnhat_api_net_problems(Long css_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_CSS_ID", css_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_TTS.SP_CAPNHAT_API_NET_PROBLEMS", params);
    }

    /**
     * Chuyển toàn bộ key trong Map sang UPPERCASE để Groovy bên tichhop đọc đúng.
     * SP trả về JSON với lowercase keys (id_tts, ma_tinh, loaiphieu_id...)
     * nhưng Groovy val() function truy cập bằng UPPERCASE (val("ID_TTS"), val("MA_TINH")...).
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> toUpperCaseKeys(Map<String, Object> source) {
        Map<String, Object> result = new HashMap<>();
        if (source == null) return result;
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String upperKey = entry.getKey().toUpperCase();
            Object value = entry.getValue();
            if (value instanceof Map) {
                result.put(upperKey, toUpperCaseKeys((Map<String, Object>) value));
            } else {
                result.put(upperKey, value);
            }
        }
        return result;
    }
    //save phuong an de xua quy trinh cell clk
    @SuppressWarnings("unchecked")
    private void savePadxCellClk(Map<String, Object> item, GiaoPhieuXuLyNetV3Dto input) {
        try {
            Object giaophieuId = item.get("GIAOPHIEU_ID");
            if (giaophieuId == null) return;
            if (input.getJson_input() == null || input.getJson_input().isEmpty()) return;

            java.util.List<PhuongAnDeXuatDto> padxDtos = objectMapper.readValue(
                    input.getJson_input(),
                    objectMapper.getTypeFactory().constructCollectionType(
                            java.util.List.class, PhuongAnDeXuatDto.class));

            if (padxDtos.isEmpty()) return;

            Integer phanloaiPadxId = padxDtos.get(0).getPhanloaiPadxId();
            if (phanloaiPadxId == null || phanloaiPadxId == 0) return;

            java.util.List<Map<String, Object>> padxListForSp = new java.util.ArrayList<>();
            for (PhuongAnDeXuatDto padx : padxDtos) {
                Map<String, Object> padxMap = padx.toGroovyMap();

                // FILE_DINHKEM: FE có thể gửi object Map hoặc JSON string
                Object fileDinhkemRaw = padxMap.get("FILE_DINHKEM");
                if (fileDinhkemRaw instanceof Map) {
                    Map<?, ?> fileMap = (Map<?, ?>) fileDinhkemRaw;
                    padxMap.put("FILE_DINHKEM", fileMap.get("filename") != null
                            ? fileMap.get("filename").toString() : "");
                } else if (fileDinhkemRaw instanceof String) {
                    String fileDinhkem = (String) fileDinhkemRaw;
                    if (fileDinhkem.contains("filename")) {
                        try {
                            Map<String, Object> fileMap = objectMapper.readValue(fileDinhkem, Map.class);
                            padxMap.put("FILE_DINHKEM", fileMap.getOrDefault("filename", "").toString());
                        } catch (Exception ignored) {}
                    }
                }

                padxListForSp.add(padxMap);
            }

            String padxListJson = objectMapper.writeValueAsString(padxListForSp);

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("vphanvung_id",
                    AppRequestContext.getCurrent().getPhanVungID(),
                    SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("vphieu_id",
                    giaophieuId,
                    SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("vphanloai_padx_id",
                    phanloaiPadxId,
                    SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
            params.add(new SqlParameter("vjson_input",
                    padxListJson,
                    SqlParameter.ParameterDirection.INPUT, java.sql.Types.CLOB));

            dbContext.executeStoredProcedure("QLSC.save_padx_cell_clk", params);

        } catch (Exception ex) {
        }
    }

    public Object layDsQtBuocXuLyTheoMaPhieu(LayDsQtBuocXuLyTheoMaPhieuDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", input.getPhanvung_id(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(
                new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DS_QT_BUOC_XULY_THEO_MAPHIEU_V2", params);
    }
}