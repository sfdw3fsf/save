package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.qlnt.*
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("qlnt")
class QLNTService {
    static String attrType = "@xsi:type"
    static String attrItemType = "@soapenc:arrayType"
    static String attrHref = "@href"
    static String attrNull = "@xsi:nil"

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

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

    @Value("\${spring.defaultconfigs.qlts-url}")
    String qlts_url

    @Value("\${spring.defaultconfigs.qlts-url-old}")
    String qlts_url_old

    @Value("\${spring.defaultconfigs.qlts-token}")
    String qlts_token

    @PostMapping("/deleteTruyenDanBts")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse deleteTruyenDanBts(@Valid @RequestBody DeleteTruyenDanBts body) {
        try {
            def action = "asset_get_station_by_code"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    Response post(String url, Map<String, String> headers, Map<String, Object> params) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");

        def builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        params.each { k, v -> builder.addFormDataPart(k, v.toString()) }
        def body = builder.build();
        Request request = new Request.Builder()
                .url(url).method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        return response
    }
}
