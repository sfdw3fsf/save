package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat

@RestController
@RequestMapping("mediagw")
class MediaService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.mediagw.url}")
	String url

    @Value("\${spring.httpservices.mediagw.version}")
	String version

    @Value("\${spring.httpservices.mediagw.accountId}")
	String accountId

    @Value("\${spring.httpservices.mediagw.partnerId}")
	String partnerId

    @Value("\${spring.httpservices.mediagw.privateKey}")
	String privateKey 

    @Value("\${spring.httpservices.mediagw.hddt.version}")
	String hddt_version

    @Value("\${spring.httpservices.mediagw.hddt.accountId}")
	String hddt_accountId

    @Value("\${spring.httpservices.mediagw.hddt.partnerId}")
	String hddt_partnerId

    @Value("\${spring.httpservices.mediagw.hddt.privateKey}")
	String hddt_privateKey 

    @PostMapping("/getQRCode")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getQRCode(@RequestBody Map<String, String> input) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "MediaService")
        inputLogData.put("action", "getQRCode")
        long start = System.currentTimeMillis()
        try {     
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")

            String requestId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
            String abc = requestId + "|" + partnerId + "|" + accountId + "|" + privateKey + "|" + version
            String secureCode = toHexString(getSHA(abc))
            input.version = version
            input.requestId = requestId
            input.partnerId = partnerId
            input.accountId = accountId
            input.secureCode = secureCode
            
            String urlR = url + "/gen-link/payment-link-qr-data"
            def res = [:]
            for (int i=0; i<5; i++) {
                inputLogData.put("time", i)
                inputLogData.put("body", new Gson().toJson(input))
                String resp = ""
                try {
                    resp = httpManaged.post(["url": urlR], headers, new Gson().toJson(input))
                    res = new JsonSlurper().parseText(resp)
                    if (res["errorCode"].toString() == "00" || 
                            (!res["errorDescription"].toString().contains("Xử lý thất bại") && !res["errorDescription"].toString().contains("Thông tin truy cập API không chính xác")))
                    {
                        break
                    }
                } catch(Exception ex) {
                    if (ex.message.contains("Unable to determine")) {
                        res["errorCode"] = "-96"
                        res["errorDescription"] = resp
                    } else {
                        res["errorCode"] = "-97"
                        res["errorDescription"] = ex.message
                    }
                }
                Thread.sleep(2000)

                // Sinh requestId moi
                requestId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())    
                abc = requestId + "|" + partnerId + "|" + accountId + "|" + privateKey + "|" + version
                secureCode = toHexString(getSHA(abc))
                input.requestId = requestId
                input.secureCode = secureCode
            }

            inputLogData.put("resp", new Gson().toJson(res))

            if (res["errorCode"].toString() == "00") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res["data"]["qrData"], data: res["data"]["shortenLink"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["errorCode"], faultString: res["errorDescription"], data: res)
            }      
        } catch(Exception e) {
            inputLogData.put("error", e.message)
            return CommonResponse.resolveException(e, loggingManaged)
        } finally {
            inputLogData["duration"] = System.currentTimeMillis() - start
            loggingManaged.info(inputLogData)
        }
    }

    @PostMapping("/getQRCode_HoaDonVNPT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getQRCode_HoaDonVNPT(@RequestBody Map<String, String> input) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "MediaService")
        inputLogData.put("action", "getQRCode_HoaDonVNPT")
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            def body = [:]
            String requestId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
            String abc = requestId + "|" + hddt_partnerId + "|" + hddt_accountId + "|" + hddt_privateKey + "|" + hddt_version
            String secureCode = toHexString(getSHA(abc))
            body.version = hddt_version
            body.requestId = requestId
            body.partnerId = hddt_partnerId
            body.accountId = hddt_accountId
            body.secureCode = secureCode
            body.serviceName = "HOADONVNPT"
            body.requestDate = new SimpleDateFormat("yyyyMMdd").format(new Date())
            body.data = input
            
            String urlR = url + "/gen-link/gen-link-mobileads-service"
            def res = [:]
            for (int i=0; i<5; i++) {
                inputLogData.put("time", i)
                inputLogData.put("body", new Gson().toJson(body))
                String resp = ""
                try {
                    resp = httpManaged.post(["url": urlR], headers, new Gson().toJson(body))
                    res = new JsonSlurper().parseText(resp)
                    if (res["errorCode"].toString() == "00" || 
                            (!res["errorDescription"].toString().contains("Xử lý thất bại") && !res["errorDescription"].toString().contains("Thông tin truy cập API không chính xác")))
                    {
                        break
                    }
                } catch(Exception ex) {
                    if (ex.message.contains("Unable to determine")) {
                        res["errorCode"] = "-96"
                        res["errorDescription"] = resp
                    } else {
                        res["errorCode"] = "-97"
                        res["errorDescription"] = ex.message
                    }
                }
                Thread.sleep(2000)

                // Sinh requestId moi
                requestId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())    
                abc = requestId + "|" + hddt_partnerId + "|" + hddt_accountId + "|" + hddt_privateKey + "|" + hddt_version
                secureCode = toHexString(getSHA(abc))
                input.requestId = requestId
                input.secureCode = secureCode
            }

            inputLogData.put("resp", new Gson().toJson(res))
            loggingManaged.info(inputLogData)
            if (res["errorCode"].toString() == "00") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: res["data"]["qrData"], data: res["data"]["shortenLink"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["errorCode"], faultString: res["errorDescription"], data: res)
            }      
        } catch(Exception e) {
            inputLogData.put("error", e.message)
            loggingManaged.info(inputLogData)
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256")
        return md.digest(input.getBytes(StandardCharsets.UTF_8))
    }

    String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash)
        StringBuilder hexString = new StringBuilder(number.toString(16))

        while (hexString.length() < 64) {
            hexString.insert(0, '0')
        }

        return hexString.toString()
    }

    @PostMapping("/inquire-qr-data-sms")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse inquireqrdatasms(@RequestBody Map<String, String> input) {
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("class", "MediaService")
        inputLogData.put("action", "inquire-qr-data-sms")
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")

            String requestId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
            String abc = requestId + "|" + partnerId + "|" + accountId + "|" + privateKey + "|" + version
            String secureCode = toHexString(getSHA(abc))
            String body = """
            {
                "phoneNumber": "${input["so_dt"]}",
                "orderCode": "${input["ma_tb"]}",
                "orderPaymentCode": "${input["ma_gd"]}",
                "serviceName": "${input["dichvu"]}",
                "provinceCode": "${input["ma_tinh"]}",
                "version": "${version}",
                "requestId": "${requestId}",
                "secureCode": "${secureCode}",
                "accountId": "${accountId}",
                "partnerId": "${partnerId}"
            }
            """
            def bodys = [:]
            bodys.phoneNumber = input["so_dt"]
            bodys.orderCode = input["ma_tb"]
            bodys.orderPaymentCode = input["ma_gd"]
            bodys.serviceName = input["dichvu"]
            bodys.provinceCode = input["ma_tinh"]
            bodys.version = version
            bodys.requestId = requestId
            bodys.secureCode = secureCode
            bodys.accountId = accountId
            bodys.partnerId = partnerId
            inputLogData.put("url", url)
            inputLogData.put("body", new Gson().toJson(bodys))
            String urlR = url + "/gen-link/inquire-qr-data-sms"
            String resp = httpManaged.post(["url": urlR], headers, new Gson().toJson(bodys))
            inputLogData.put("response", resp)
            loggingManaged.info(inputLogData)
            def res = new JsonSlurper().parseText(resp)
            if (res["errorCode"].toString() == "00") {
                def dateFormat = new SimpleDateFormat("dd/MM/yyyy")
                List datas = (List) res["data"]
                if(datas.size() > 0){
                    datas.each { x -> x.smsDate = dateFormat.format(new Date(x.smsDate))}
                }
                //return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: datas)
            } else {
                // telegramService.sendMessage("<b>Input</b>: " + new Gson().toJson(input) + "\n<b>Output</b>: " + res["errorDescription"], "-4135528883")
                return new CommonResponse(errorCode: 1, faultCode: res["errorCode"], faultString: res["errorDescription"], data: res)
            }
        } catch(Exception e) {
            inputLogData.put("exception", e.message)
            loggingManaged.info(inputLogData)
            // telegramService.sendMessage("<b>Input</b>: " + new Gson().toJson(input) + "\n<b>Output</b>: " + e.message, "-4135528883")
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
