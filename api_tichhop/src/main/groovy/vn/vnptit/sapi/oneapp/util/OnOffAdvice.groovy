package vn.vnptit.sapi.oneapp.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.ClassUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.service.HopDongService

import javax.servlet.http.HttpServletRequest
import java.lang.reflect.Method
import java.lang.reflect.Parameter

@Aspect
@Component
class OnOffAdvice {

    @Autowired(required = false)
    private HttpServletRequest request;

    ObjectMapper json = new ObjectMapper();

    @Value("\${spring.defaultconfigs.onoff}")
    String onoff

    @Autowired
    GenericApi genericApi

    @Autowired
    HopDongService hopDongService

    List<String> lstPassServices = new ArrayList<String>(Arrays.asList("/cem-canhbao/","/general/callapi", "/solienlac/", "/VNEdu/", "/util/download", "/bnm/testPortV2", "/smartca/", "/ky_so/", "/khdn/search_khtn", "/mediagw/getQRCode",
            "/cts/TestPort/TestOnuPorts", "/ctslinetest/testOnuByPortVisa", "/mnp/checkParticipant", "/ccbs/", "/fms_alarms/",
            "/ctsnewpost/testOnuPorts", "/ctsnewpost/testOnuPortsvb8362", "/ctsnewpost/testOnuByPortVisa", "/siptrunking/", "vnptinvoice",
            "/ctsnewpost/doKiem", "/vasc/", "/email/send-email-v2", "/vnptinvoice/", "/smcs/", "/eContract/", "/vnptpay/",
            "/obtelesale/", "/hrm/xacThucNhanVien" , "/hrm/thongTinNguoiDung" , "/qlts/", "/vnptinvoice/", "/khdn_media/", "/fmis/","/vnptai/lay_thongtin_tuvan", "/precheck_access_hw_kpi","/post_net_dhsx_to_tts","/vnface/","/vims/","/siptrunking/","/onebss-csht/"))

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    Object postfilter(ProceedingJoinPoint pjp) {
        boolean kt = false
        for (String item : lstPassServices) {
            if (request.getRequestURI().contains(item)) {
                kt = true
                break
            }
        }

        if (!kt) {
            def session = genericApi.getSessionData(request)
            String token = session[GenericApi.TOKEN]
            hopDongService.init(token)
            kt = hopDongService.get_app_config()
        }

        if (kt)
        {
            Object out = pjp.proceed()
            out
        } else {
            Signature signature =  pjp.getSignature();
            Class returnType = ((MethodSignature) signature).getReturnType();
            if (returnType.getTypeName() == CommonResponse.getTypeName()){
                return new CommonResponse(errorCode: -1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này [TICHHOP]", data: "")
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Bạn không có quyền thực hiện chức năng này [TICHHOP]", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    Object getfilter(ProceedingJoinPoint pjp) {
        boolean kt = false
        for (String item : lstPassServices) {
            if (request.getRequestURI().contains(item)) {
                kt = true
                break
            }
        }

        if (!kt) {
            def session = genericApi.getSessionData(request)
            String token = session[GenericApi.TOKEN]
            hopDongService.init(token)
            kt = hopDongService.get_app_config()
        }

        if (kt) {
            Object out = pjp.proceed()
            out
        } else {
            Signature signature =  pjp.getSignature();
            Class returnType = ((MethodSignature) signature).getReturnType();
            if (returnType.getTypeName() == CommonResponse.getTypeName()){
                return new CommonResponse(errorCode: -1, faultCode: "", faultString: "Bạn không có quyền thực hiện chức năng này [TICHHOP]", data: "")
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Bạn không có quyền thực hiện chức năng này [TICHHOP]", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}