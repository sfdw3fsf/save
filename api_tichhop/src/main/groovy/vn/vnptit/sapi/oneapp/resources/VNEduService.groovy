package vn.vnptit.sapi.oneapp.resources

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("VNEdu")
class VNEduService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    String url = "https://sandbox.vnedu.vn/v3/"
    String token = "75fe20c3c009a340bd3f161316f7925b"

    @GetMapping("/laydstruongthuoctinhdynamic")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse laydstruongthuoctinhdynamic(@RequestParam String idtinh) {
        try {
            String urlT = url + "?call=app.doisoat.serv.api.getListTruong&token=" + token + "&tinh_id=" + idtinh + "&user_name=vnpt.tvtung"
            Map<String, String> headers = [:]
            def resp = httpManaged.get(urlT, headers)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
        } catch(Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

}
