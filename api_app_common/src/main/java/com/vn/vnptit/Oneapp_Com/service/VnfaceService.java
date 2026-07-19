package com.vn.vnptit.Oneapp_Com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.HttpClientUtil;
import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.managed.LoggingManaged;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.model.VnFaceAccessTokenTichHopResponse;

@Service
public class VnfaceService {

    private static final String ACCESS_TOKEN_ENDPOINT = "http://tichhop/vnface/account/access-token";
    private static final String HIS_CHECKIN_ENDPOINT = "http://tichhop/vnface/his-checkin";

    @Autowired
    LoggingManaged loggingManaged;

    private final JsonMapper mapper = new JsonMapper();
    private final HttpClientUtil httpClientUtil = new HttpClientUtil();

    public ResponseEntity<Object> getAccountAccessToken(String uuid, String token) {
        ResponseObject modelResponse = new ResponseObject();
        modelResponse.setRequest_id(uuid);

        String endpoint = ACCESS_TOKEN_ENDPOINT;
        String tokenPayload = token.split(" ")[1];
        String userCode = JwtService.getMaNhanVienFromToken(tokenPayload);
        if (userCode == null || userCode.trim().isEmpty()) {
            modelResponse = Utils.badRequest(uuid);
            modelResponse.setMessage("Không lấy được mã nhân viên");
            return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
        }

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userCode", userCode);

        Map<String, String> headers = new HashMap<>();
        headers.put(ConstantString.AUTHORIZATION, token);

        try {
            Object rawData = httpClientUtil.doPost(endpoint, headers, requestBody, Object.class);
            Map<String, Object> responseMap = mapper.convertValue(rawData, Map.class);
            Integer errorCode = toInteger(responseMap.get("errorCode"));
            Map<String, Object> dataMap = asMap(responseMap.get("data"));
            Object tokenObjectRaw = dataMap == null ? null : dataMap.get("object");

            Map<String, Object> log = new HashMap<>();
            log.put(ConstantString.REQUEST_ID, uuid);
            log.put(ConstantString.ACTION, endpoint);
            log.put(ConstantString.IDENTIFYCODE, JwtService.getIdentifyFromToken(tokenPayload));
            log.put(ConstantString.USERNAME, JwtService.getUsernameFromToken(tokenPayload));
            log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBody));
            log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rawData));
            loggingManaged.info(log);

            if (Integer.valueOf(0).equals(errorCode) && tokenObjectRaw != null) {
                VnFaceAccessTokenTichHopResponse.TokenObject tokenObject = mapper.convertValue(tokenObjectRaw,
                        VnFaceAccessTokenTichHopResponse.TokenObject.class);
                modelResponse.setError("200");
                modelResponse.setMessage("Success");
                modelResponse.setData(tokenObject);
                return ResponseEntity.status(Integer.parseInt("200")).body(modelResponse);
            }

            ResponseObject badRequest = Utils.badRequest(uuid);
            badRequest.setMessage(resolveTichHopErrorMessage(responseMap, dataMap));
            return ResponseEntity.status(Integer.parseInt(badRequest.getError())).body(badRequest);
        } catch (JsonProcessingException ex) {
            loggingManaged.error(buildErrorLog(uuid, endpoint, ex.getMessage()));
            modelResponse = Utils.critcalReponse(uuid);
            modelResponse.setMessage("Khong xu ly duoc du lieu ket qua");
            return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
        } catch (Exception ex) {
            loggingManaged.error(buildErrorLog(uuid, endpoint, ex.getMessage()));
            modelResponse = Utils.badRequest(uuid);
            modelResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
        }
    }

    public ResponseEntity<Object> getCheckinHistory(String uuid, String token, String uuidHisCheckin) {
        ResponseObject modelResponse = new ResponseObject();
        modelResponse.setRequest_id(uuid);

        if (uuidHisCheckin == null || uuidHisCheckin.trim().isEmpty()) {
            modelResponse = Utils.badRequest(uuid);
            modelResponse.setMessage("uuidHisCheckin khong duoc de trong");
            return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
        }

        String endpoint = HIS_CHECKIN_ENDPOINT + "/" + uuidHisCheckin.trim();

        Map<String, String> headers = new HashMap<>();
        headers.put(ConstantString.AUTHORIZATION, token);

        try {
            Object rawData = httpClientUtil.doGet(endpoint, headers, Object.class);
            Map<String, Object> responseMap = mapper.convertValue(rawData, Map.class);

            // Spring Boot default error (timestamp, status, error, path)
            if (responseMap.containsKey("timestamp")) {
                ResponseObject badRequest = Utils.badRequest(uuid);
                Object error = responseMap.get("error");
                badRequest.setMessage(error instanceof String ? (String) error : "Khong lay duoc lich su checkin");
                return ResponseEntity.status(Integer.parseInt(badRequest.getError())).body(badRequest);
            }

            Integer errorCode = toInteger(responseMap.get("errorCode"));

            if (Integer.valueOf(0).equals(errorCode)) {
                Object data = responseMap.get("data");
                Map<String, Object> dataMap = asMap(data);
                // tichhop tra errorCode=0 nhung data co statusCode loi
                if (dataMap != null && dataMap.containsKey("statusCode")) {
                    ResponseObject badRequest = Utils.badRequest(uuid);
                    Object dataError = dataMap.get("error");
                    badRequest.setMessage(dataError instanceof String ? (String) dataError : "Khong lay duoc lich su checkin");
                    return ResponseEntity.status(Integer.parseInt(badRequest.getError())).body(badRequest);
                }
                modelResponse.setError("200");
                modelResponse.setMessage("Success");
                modelResponse.setData(data);
                return ResponseEntity.status(200).body(modelResponse);
            }

            ResponseObject badRequest = Utils.badRequest(uuid);
            Object faultString = responseMap.get("faultString");
            badRequest.setMessage(faultString instanceof String && !((String) faultString).trim().isEmpty()
                    ? (String) faultString : "Khong lay duoc lich su checkin");
            return ResponseEntity.status(Integer.parseInt(badRequest.getError())).body(badRequest);
        } catch (Exception ex) {
            loggingManaged.error(buildErrorLog(uuid, endpoint, ex.getMessage()));
            modelResponse = Utils.badRequest(uuid);
            modelResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
        }
    }

    private Integer toInteger(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    private Map<String, Object> asMap(Object value) {
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        return null;
    }

    private String resolveTichHopErrorMessage(Map<String, Object> responseMap, Map<String, Object> dataMap) {
        if (dataMap != null) {
            Object detailError = dataMap.get("error");
            if (detailError instanceof String && !((String) detailError).trim().isEmpty()) {
                return (String) detailError;
            }

            Object message = dataMap.get("message");
            if (message instanceof String && !((String) message).trim().isEmpty()) {
                return (String) message;
            }
        }

        Object faultString = responseMap.get("faultString");
        if (faultString instanceof String && !((String) faultString).trim().isEmpty()) {
            return (String) faultString;
        }

        return "Khong lay duoc access token";
    }

    private Map<String, Object> buildErrorLog(String uuid, String action, String exMessage) {
        Map<String, Object> log = new HashMap<>();
        log.put(ConstantString.REQUEST_ID, uuid);
        log.put(ConstantString.ACTION, action);
        log.put(ConstantString.LOG_EX_MESSAGE, exMessage);
        return log;
    }
}