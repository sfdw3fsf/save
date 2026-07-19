package vn.vnptit.sapi.oneapp.resources

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.util.concurrent.atomic.AtomicBoolean

@RestController
@RequestMapping("fake-response")
class FakeResponseService {

    @Autowired
    @Qualifier("fakeSuccessCommonResponseValue")
    AtomicBoolean fakeSuccessCommonResponseValue

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @GetMapping("/value")
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse getValue() {
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: fakeSuccessCommonResponseValue.get())
    }

    @GetMapping("/set-value/{newValue}")
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse setValue(@PathVariable("newValue") String newValue) {
        fakeSuccessCommonResponseValue.set(newValue.equalsIgnoreCase("true"))
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: fakeSuccessCommonResponseValue)
    }

    @GetMapping("/test")
    @FakeSuccessCommonResponse
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse test() {
        return new CommonResponse(errorCode: -1, faultCode: "error", faultString: "error", data: "error")
    }
}
