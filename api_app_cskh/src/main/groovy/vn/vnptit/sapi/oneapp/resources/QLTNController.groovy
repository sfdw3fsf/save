package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.sql.OutParameter
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
//import sun.security.util.ArrayUtil
import vn.vnptit.sapi.oneapp.model.request.ChangeDslPortProfileRequest
import vn.vnptit.sapi.oneapp.model.request.LookUpRASAccountRequest
import vn.vnptit.sapi.oneapp.service.CCBSService
import vn.vnptit.sapi.oneapp.service.VisaService
import vn.vnptit.sapi.oneapp.service.CTSService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
@RequestMapping("qltn")
class QLTNController {
    private final HttpServletRequest request
    private final ObjectMapper objectMapper

    @Autowired
    CCBSService ccbsService
    @Autowired
    GenericApi genericApi
    @Autowired(required = false)
    HttpServletRequest request;

    @Autowired
    QLTNController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @PostMapping("/ls_no_tra")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    ResponseEntity<BaseResponse<?>> ls_no_tra(@RequestBody Map<String, String> param) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String token = session_.get(TOKEN)
        ccbsService.init(token)
        String tu_kyhoadon = param.tu_kyhoadon
        String den_kyhoadon = param.den_kyhoadon
        String ma_kh = param.ma_kh
        String so_tb = param.so_tb
        def ret = [:]

        def ret1 = ccbsService.qltn_ts_tt_no_tonghop(den_kyhoadon, ma_kh, so_tb)
        ret.tt_tonghop = ret1
        def ret2 = ccbsService.qltn_ts_tracuu_ls_tra(tu_kyhoadon, den_kyhoadon, ma_kh, so_tb)
        ret.ls_tra = ret2
        def ret3 = ccbsService.qltn_ts_tracuu_ls_no(tu_kyhoadon, den_kyhoadon, ma_kh, so_tb)
        ret.ls_no = ret3

        return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
    }
}
