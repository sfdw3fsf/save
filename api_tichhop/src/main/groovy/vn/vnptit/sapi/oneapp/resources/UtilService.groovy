package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi

import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.servlet.http.HttpServletRequest
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("util")
class UtilService {
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request 

    @GetMapping("/download") 
    ResponseEntity download(@RequestParam String url, @RequestParam Optional<String> fileName, @RequestParam Optional<String> fileType) {
        try {
            if (!url.toLowerCase().startsWith("http://10.70.52.164:8081/") && !url.toLowerCase().startsWith("https://vnptcab.vn:8888/")) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "URL không hợp lệ", ErrorCodeDefine.SUCCESS));
            } else {
                if (url.toLowerCase().startsWith("http://10.70.52.164:8081/") && (!url.toLowerCase().contains("hdsd") || !url.toLowerCase().endsWith(".html"))) {
                    if (!url.toLowerCase().endsWith("jpg") && !url.toLowerCase().endsWith("png") && !url.toLowerCase().endsWith("doc")
                        && !url.toLowerCase().endsWith("docx") && !url.toLowerCase().endsWith("pdf")
                        && !url.toLowerCase().endsWith("xlsx") && !url.toLowerCase().endsWith("xls")) {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "URL không hợp lệ", ErrorCodeDefine.SUCCESS));
                    }
                }
                if (url.toLowerCase().startsWith("https://vnptcab.vn:8888/") 
                        && (!url.toLowerCase().endsWith(".jpg") && !url.toLowerCase().endsWith(".jpeg") && !url.toLowerCase().endsWith(".png"))) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "URL không hợp lệ", ErrorCodeDefine.SUCCESS));
                }
            }
            
            String requestId = UUID.randomUUID().toString()
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            String type = fileType.orElse("")
            if (type == "base64"){
                String base64 = response.body().bytes().encodeBase64().toString()
                return ResponseEntity.ok().body(base64);
            }
            def rs = new InputStreamResource(response.body().byteStream());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment;filename=${fileName.orElse("")}")
                    .body(rs);
        } catch (Exception e) {
            return null
        }
    }

    // @GetMapping("/checkIP")
    // ResponseEntity checkIP() {
    //     try {
    //         String localHost = InetAddress.getLocalHost().getHostAddress();
    //         return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, localHost, ErrorCodeDefine.SUCCESS))
    //     } catch (UnknownHostException e) {
    //         return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Lỗi rồi", ErrorCodeDefine.SUCCESS))
    //     }     
    // }

    def post(String path, Object data) {
        Map<String, String> headers = [:]
        String url = url + "api/dhsxkd/post"
        headers.put("Authorization", "Bearer " + getToken().token)
        headers.put("Content-Type", "application/json")
        headers.put("Content-Encoding", "UTF-8")
        def body = [
                Command: path,
                Data: data
        ]
        String resp = httpManaged.post(["url": url], headers, JsonOutput.toJson(body))
        return new JsonSlurper().parseText(resp)
    }

    @PostMapping("/KiemTraUserVi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse KiemTraUserVi(@RequestBody Map<String, String> input) {
        try {  
            Map<String, String> headers = new HashMap<>()
            headers.put("Authorization", "vnpt-cab_api_vnptpay")

            String matinh = input.matinh.toUpperCase()
            if (matinh.toUpperCase() == "HAG") {
                matinh = "HGI"
            } else if (matinh.toUpperCase() == "BCN") {
                matinh = "BKN"
            } else if (matinh.toUpperCase() == "DAN") {
                matinh = "DNO"
            } else if (matinh.toUpperCase() == "GPC") {
                matinh = "KHDN"
            }

            String url = "https://vnptcab.vn:8888/api/VNPTPay/KiemTraUserVi"
            url += "?user_vi=" + input.user_vi
            url += "&testMode=" + input.istest
            url += "&matinh=" + matinh

            String resp = httpManaged.post(["url": url], headers, "")
            resp = resp.replace("\\\\", "\\").replace("\\\"", "\"")
            resp = resp.replace("\"{", "{").replace("}\"", "}")
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: new JsonSlurper().parseText(resp))
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
