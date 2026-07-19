package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import groovy.sql.Sql
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.ivan.*
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut
import ws.client.ivan.BasicHttpBinding_IServicesStub
import ws.client.ivan.ServicesLocator

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@RestController
@RequestMapping("ocdm")
class OCDMService {
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

    @LoggingInOut
    @GetMapping("/bss_get_handset_sub/{subscriber}")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Lấy loại thiết bị tb (Smartphone/Non smartphone)", tags = ["ocdm"])
    CommonResponse bss_get_handset_sub(@Valid @PathVariable String subscriber) {
        String datas = 0
        try {
            Sql.newInstance(oracleManaged.getDataSource("ocdm")).withCloseable { Sql sql ->
                def rs = sql.firstRow("SELECT OCDM_sTAGE.BSS_GET_HANDSET_SUB(?) out FROM DUAL", [subscriber])
                if (rs != null && !rs.isEmpty())
                    datas = rs.get("out")
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: datas)
        } catch (Exception ex){
            println ex.getMessage()
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "", data: null)
        }
    }
}
