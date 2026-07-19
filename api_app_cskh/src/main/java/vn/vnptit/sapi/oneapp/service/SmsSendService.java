package vn.vnptit.sapi.oneapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.vnptit.sapi.oneapp.model.SendSMSDtoIn;
import vn.vnptit.sapi.oneapp.resources.GenericApi;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsSendService {

    @Autowired
    private GenericApi genericApi;
    @Autowired
    private HttpServletRequest request;


    public List<Map<String, Object>> sendSmsSpGiaoViecNhanVienXhh(List<Map<String, Object>> map) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (map == null || map.isEmpty()) {
            return list;
        }
        for (Map<String, Object> item : map) {
            if (item.get("status").toString().equals("0")) {
                Map<String, Object> stringObjectMap = new HashMap<>();
                Object soDT = item.get("SO_DT");
                Object noiDung = item.get("ND_SMS");
                boolean isSend = this.smsSend(soDT.toString(), noiDung.toString());
                stringObjectMap.put("P_DS_GIAOVIEC_ID", item.get("GIAOVIEC_ID"));
                stringObjectMap.put("IS_SEND", isSend);
                list.add(stringObjectMap);
            }
        }
        return list;
    }

    private boolean smsSend(String soDT, String noiDung) {
        SendSMSDtoIn item = new SendSMSDtoIn();
        item.setMsisdn(soDT);
        item.setContent(noiDung);
        return this.smsSend(item);
    }


    private boolean smsSend(SendSMSDtoIn obj) {
        Map<String, Object> session_ = genericApi.getSessionData(request);

        String token = session_.get(GenericApi.getTOKEN()).toString();
        RestTemplate restTemplate = new RestTemplate();
        String soDT = obj.getMsisdn();
        if (!(soDT == null || soDT.equals("") || soDT.startsWith("84"))) {
            if (soDT.startsWith("0"))
                soDT = soDT.substring(1);
            soDT = "84" + soDT;
        }
        // soDT = "84915445554";
        obj.setMsisdn(soDT);
        obj.setMt_type("reply");
        String urlquantri = "http://quantri/internal/sendSms";
        HttpHeaders headers = new HttpHeaders();
        // set content-type header
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<Object> request = new HttpEntity<>(obj, headers);
        try {
            ResponseEntity<Object> result = restTemplate.postForEntity(urlquantri, request, Object.class);
            Map<String, Object> resultBody = (Map<String, Object>) result.getBody();
            Map<String, Object> resultBodyData = (Map<String, Object>) resultBody.get("data");
            int ecode = Integer.parseInt(resultBodyData.get("error_code").toString());
            return ecode == 0;
        } catch (Exception e) {

        }
        return false;
    }

}
