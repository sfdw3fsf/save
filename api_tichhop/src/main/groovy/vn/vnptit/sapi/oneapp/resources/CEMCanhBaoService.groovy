package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.sql.OutParameter
import groovy.sql.Sql
import vn.vnptit.sapi.oneapp.util.GenericApi
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import org.springframework.beans.factory.annotation.Value
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

import java.sql.ResultSet
import java.sql.ResultSetMetaData

@RestController
@RequestMapping("cem-canhbao")
class CEMCanhBaoService {

    private final HttpServletRequest request

    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }

    @Autowired
    GenericApi genericApi

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    HttpManaged httpManaged

    @Value('${cem.primary.base-url}')
    String cemPrimaryBaseUrl

    @Value('${cem.primary.tickets-path}')
    String cemPrimaryTicketsPath

    @Value('${cem.primary.api-key}')
    String cemPrimaryApiKey

    @Autowired
    CEMCanhBaoService(HttpServletRequest request) {
        this.request = request
    }

    @PostMapping("/sync-net-ticket")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "Đồng bộ phiếu cho Net", description = "dev: phucth", tags = ["CEM Canh bao"])
    ResponseEntity<BaseResponse> insertTtCanhBaoCemBrcd(@RequestBody Map<String, Object> body) {
        UUID uuid = UUID.randomUUID()
        String requestId = uuid.toString()
        String requestTime = OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"))


        try {
            String requestBody = objectMapper.writeValueAsString([
                    "source"      : "OneBSS",
                    "request_time": requestTime,
                    "tickets"     : [body]

            ])
            Map<String, String> headers = [
                    "Content-Type": "application/json",
                    "API-KEY"     : cemPrimaryApiKey
            ]

            Object cemResponseObject = null
            String cemResponse = httpManaged.put(["url": cemPrimaryBaseUrl + cemPrimaryTicketsPath], headers, requestBody)
            cemResponseObject = objectMapper.readValue(cemResponse, Map)

            return ResponseEntity.ok().body(BaseResponseBuilder.of(cemResponseObject, requestId))
        } catch (Exception e) {
            String errorMessage = "Data Object truyền vào: " + objectMapper.writeValueAsString(body)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(errorMessage, requestId, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }
}
