package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.netflix.discovery.converters.Auto
import groovy.sql.Sql
import io.swagger.model.BaseResponse
import okhttp3.MultipartBody
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.ekyc.DownFileModel
import vn.vnptit.sapi.oneapp.properties.DataSourceProperties
import vn.vnptit.sapi.oneapp.service.FileService
import vn.vnptit.sapi.oneapp.util.BSSCrypto
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.DUtils
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import groovy.json.JsonSlurper
import org.apache.commons.text.StringEscapeUtils
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut
import java.util.concurrent.TimeUnit

import javax.validation.Valid;
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.concurrent.ThreadLocalRandom

@RestController
@RequestMapping("ekyc")
class EKYCService {
    @Autowired
    CacheManager cacheManager
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

    @Autowired
    FileService fileService

    String _host = "https://api.idg.vnpt.vn"

    @LoggingInOut
    @PostMapping("/HashFileEKYC")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> HashFileEKYC(@Valid @org.springframework.web.bind.annotation.RequestBody  Map<String, Object> req) {
        try {
            String path = "/file-service/v1/addFile"
            String urlT = _host + path
            String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A"
          //  byte[] kq = fileService.downloadFile(req["url"].toString())
            DownFileModel file_download = fileService.downloadFile_v2(req["url"].toString())
            def gson = new Gson();
           // File f = fileService.createFileFromBytes(kq, "cccd.pdf")
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .build();
          //  okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), json_body);

            okhttp3.RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", file_download.file_name,
                            okhttp3.RequestBody.create(file_download.file_content, okhttp3.MediaType.parse("application/octet-stream")))

                    .addFormDataPart("title","Hashing document")
                    .addFormDataPart("description","Hashing document")
                    .build();

            Request request = new Request.Builder()
                    .url(_host + "/file-service/v1/addFile")
                    .method("POST", body)
                    .addHeader("mac-address", "EGOV-DIGDOC-WEB-API")
                    .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build();
            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class);
            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        } catch(Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @LoggingInOut
    @PostMapping("/HashFileEKYC_v2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> HashFileEKYC_v2(@RequestParam("files") org.springframework.web.multipart.MultipartFile files) {
        try {
            // Lấy trực tiếp tên file và mảng byte từ MultipartFile được truyền vào
            String fileName = files.getOriginalFilename()
            byte[] fileBytes = files.getBytes()

            def gson = new Gson()

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .build()

            // Build form data body sử dụng dữ liệu từ file upload
            okhttp3.RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName,
                            okhttp3.RequestBody.create(fileBytes, okhttp3.MediaType.parse("application/octet-stream")))
                    .addFormDataPart("title", "Hashing document")
                    .addFormDataPart("description", "Hashing document")
                    .build()

            Request request = new Request.Builder()
                    .url(_host + "/file-service/v1/addFile")
                    .method("POST", body)
            //                    .addHeader("mac-address", "EGOV-DIGDOC-WEB-API")
                    .addHeader("mac-address", "TEST1")
                    .addHeader("Token-id","e206e1ff-4df6-268d-e053-63199f0a3731")
            // Lưu ý: Hardcode token ở đây không được khuyến khích trong môi trường production
                    .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build()

            Response response = client.newCall(request).execute()

            String responseBody = response.body().string()
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class)

            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        } catch(Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @LoggingInOut
    @PostMapping("/LayThongTinGiayToTuyThan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> LayThongTinGiayToTuyThan(@Valid @org.springframework.web.bind.annotation.RequestBody  LinkedTreeMap<String, Object> req) {
        try {
            String path = "/rpa-service/aidigdoc/v1/ocr/giay-to-tuy-than"
            String url = _host + path
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS).build();
            //  okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), json_body);

            // set media type
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

            def gson = new Gson();
            String JSON = gson.toJson(req);

            okhttp3.RequestBody body = RequestBody.create(JSON, mediaType)
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("mac-address", "TEST1")
                    .addHeader("Token-id","e206e1ff-4df6-268d-e053-63199f0a3731")
                    .addHeader("Token-key", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJq1sdUXUxiELa6VtKJhzbnB1uh+C0WmQ8opSU5daTdOE/axOIxLzBk+tgKnCIZu5xFXrZP2NzwdIA0sEuTfM90CAwEAAQ==")
                    .addHeader("Authorization",  "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class);
            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        }
        catch(Exception e)
        {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @LoggingInOut
    @PostMapping("/XacThucDKKD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> XacThucDKKD(@Valid @org.springframework.web.bind.annotation.RequestBody  LinkedTreeMap<String, Object> req) {
        try {
            String path = "/rpa-service/aidigdoc/v1/ocr/dang-ky-kinh-doanh"
            String url = _host + path
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .build();
            //  okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), json_body);

            // set media type
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

            def gson = new Gson();
            String JSON = gson.toJson(req);

            okhttp3.RequestBody body = RequestBody.create(JSON, mediaType)
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("mac-address", "TEST1")
                    .addHeader("Token-id","e206e1ff-4df6-268d-e053-63199f0a3731")
                    .addHeader("Token-key", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJq1sdUXUxiELa6VtKJhzbnB1uh+C0WmQ8opSU5daTdOE/axOIxLzBk+tgKnCIZu5xFXrZP2NzwdIA0sEuTfM90CAwEAAQ==")
                    .addHeader("Authorization",  "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class);
            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        }
        catch(Exception e)
        {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @LoggingInOut
    @PostMapping("/XacThucDKHKD")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> XacThucDKHKD(@Valid @org.springframework.web.bind.annotation.RequestBody  LinkedTreeMap<String, Object> req) {
        try {
            String path = "/rpa-service/aidigdoc/v1/ocr/dang-ky-ho-kinh-doanh"
            String url = _host + path
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS)
                    .build();
            //  okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), json_body);

            // set media type
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

            def gson = new Gson();
            String JSON = gson.toJson(req);

            okhttp3.RequestBody body = RequestBody.create(JSON, mediaType)
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("mac-address", "TEST1")
                    .addHeader("Token-id","e206e1ff-4df6-268d-e053-63199f0a3731")
                    .addHeader("Token-key", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJq1sdUXUxiELa6VtKJhzbnB1uh+C0WmQ8opSU5daTdOE/axOIxLzBk+tgKnCIZu5xFXrZP2NzwdIA0sEuTfM90CAwEAAQ==")
                    .addHeader("Authorization",  "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class);
            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        }
        catch(Exception e)
        {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @LoggingInOut
    @PostMapping("/XacThucPhieuYC")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> XacThucPhieuYC(@Valid @org.springframework.web.bind.annotation.RequestBody  LinkedTreeMap<String, Object> req) {
        try {
            String path = "/rpa-service/aidigdoc/v1/ocr/phieu-de-nghi-cung-cap-ca"
            String url = _host + path
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS).build();
            //  okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), json_body);

            // set media type
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

            def gson = new Gson();
            String JSON = gson.toJson(req);

            okhttp3.RequestBody body = RequestBody.create(JSON, mediaType)
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("mac-address", "TEST1")
                    .addHeader("Token-id","e206e1ff-4df6-268d-e053-63199f0a3731")
                    .addHeader("Token-key", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJq1sdUXUxiELa6VtKJhzbnB1uh+C0WmQ8opSU5daTdOE/axOIxLzBk+tgKnCIZu5xFXrZP2NzwdIA0sEuTfM90CAwEAAQ==")
                    .addHeader("Authorization",  "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class);
            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        }
        catch(Exception e)
        {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @LoggingInOut
    @PostMapping("/XacThucHopDong")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> XacThucHopDong(@Valid @org.springframework.web.bind.annotation.RequestBody  LinkedTreeMap<String, Object> req) {
        try {
            String path = "/rpa-service/aidigdoc/v1/ocr/hop-dong-cung-cap-ca"
            String url = _host + path
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(0, TimeUnit.SECONDS) // Không giới hạn thời gian kết nối
                    .readTimeout(0, TimeUnit.SECONDS)    // Không giới hạn thời gian đọc dữ liệu
                    .writeTimeout(0, TimeUnit.SECONDS).build();
            //  okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), json_body);

            // set media type
            okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

            def gson = new Gson();
            String JSON = gson.toJson(req);

            okhttp3.RequestBody body = RequestBody.create(JSON, mediaType)
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("mac-address", "TEST1")
                    .addHeader("Token-id","e206e1ff-4df6-268d-e053-63199f0a3731")
                    .addHeader("Token-key", "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJq1sdUXUxiELa6VtKJhzbnB1uh+C0WmQ8opSU5daTdOE/axOIxLzBk+tgKnCIZu5xFXrZP2NzwdIA0sEuTfM90CAwEAAQ==")
                    .addHeader("Authorization",  "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdWQiOlsicmVzdHNlcnZpY2UiXSwidXNlcl9uYW1lIjoiZHVvbmdwZEB2bnB0LnZuIiwic2NvcGUiOlsicmVhZCJdLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdCIsIm5hbWUiOiJkdW9uZ3BkQHZucHQudm4iLCJ1dWlkX2FjY291bnQiOiJkYzlhYTkxZS02OGNjLTQ4ZDMtZTA1My02MjE5OWYwYTM4MDciLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6ImQwZDEyZWVmLWM2OGMtNGYxOS04YTBiLTFjYzdmZjdjMzdmZiIsImNsaWVudF9pZCI6ImFkbWluYXBwIn0.0qttleqQD17Hvbuau1BD27oGReA0P2jqPeLJfVaHGJ_w-kydYExSR6BPikdNSLVLkJXKeMyhdK2gjOU-xyrIjuH_lA5KSPMte2Vbp_FTwn8VTpOIn-VOMkbmCOTOLduP73LNeqk_-YFDxTKVrisS5r8MsVILUs7eq36zv3xqTRcpya2cd-NXDnllWqwC0q3YySFx_LNDURZwd3Vxu6__hUy_Qwd7CjDoFDSwYmHDTMPR3Vckia6LJZuHBOBCff0l38yX-dM0UIF_Y0RWoGBIz30xY2ki9Kauc3wQlN4bQQxdIPsjZ2yu1NScsq_x3SLSEDNEZQkuY19fS_KL7Ixu0A")
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            LinkedTreeMap<String, Object> kq_up = gson.fromJson(responseBody, LinkedTreeMap.class);
            return ResponseEntity.ok().body(BaseResponseBuilder.of(kq_up))
        }
        catch(Exception e)
        {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
