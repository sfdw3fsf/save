package com.vn.vnptit.Oneapp_Com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.common.CallHttp;
import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.managed.LoggingManaged;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CshtSyncService {

    private static final String[] MA_TINH_LIST = {"VTU", "HCM", "HNI", "BDG"};
    private static final String SYNC_URL = "http://esb/onebss-csht/sync";

    @Autowired
    LoggingManaged loggingManaged;

    private final JsonMapper mapper = new JsonMapper();
    private final Logger logger = Logger.getLogger(getClass().getName());

    public void syncAllProvinces() {
        // Chạy tuần tự để tránh rate-limit RIMS gây fail tỉnh lớn (HNI). Tổng ~20-25 phút.
        for (String maTinh : MA_TINH_LIST) {
            try {
                syncOneProvince(maTinh);
            } catch (Exception e) {
                logger.info("[CshtSyncService] sync " + maTinh + " error: " + e.getMessage());
            }
        }
    }

    private void syncOneProvince(String maTinh) {
        String uuid = UUID.randomUUID().toString();
        Map<String, String> header = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("ma_tinh_tp", maTinh);
        body.put("system_code", "");

        ResponseObject response = CallHttp.httpPostBody(header, body, SYNC_URL);

        Map<String, String> log = new HashMap<>();
        log.put(ConstantString.REQUEST_ID, uuid);
        log.put(ConstantString.ACTION, "onebss-csht/sync " + maTinh);
        try {
            log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
            log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        loggingManaged.info(log.toString());
    }
}
