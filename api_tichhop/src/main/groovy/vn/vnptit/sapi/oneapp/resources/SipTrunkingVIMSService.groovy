package vn.vnptit.sapi.oneapp.resources

import com.jcraft.jsch.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut
import vn.vnptit.sapi.oneapp.util.SSHUtil

@RestController
@RequestMapping("siptrunking/vims")
class SipTrunkingVIMSService {

    @Autowired
    NativeWebRequest request

    @Autowired
    GenericApi genericApi

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    SSHUtil sshUtil

    @Value("\${spring.httpservices.siptrunking.vims.ip}")
    String ipserver

    @Value("\${spring.httpservices.siptrunking.vims.username}")
    String username

    @Value("\${spring.httpservices.siptrunking.vims.password}")
    String password

    @Value("\${spring.httpservices.siptrunking.vims.hni.sbg.port}")
    int hni_sbg_port

    @Value("\${spring.httpservices.siptrunking.vims.hni.ipw.port}")
    int hni_ipw_port

    @Value("\${spring.httpservices.siptrunking.vims.hcm.sbg.port}")
    int hcm_sbg_port

    @Value("\${spring.httpservices.siptrunking.vims.hcm.ipw.port}")
    int hcm_ipw_port

    @GetMapping("/checkExistsIPW")
    CommonResponse checkExistsIPW(@RequestParam("ip") String ip, @RequestParam("loc") String loc) {
        try {
            Session session = sshUtil.connect(ipserver, loc == "HNI" ? hni_ipw_port : hcm_ipw_port, username, password)
            if (session == null) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Không ssh được đến server. Vui lòng kiểm tra lại thông tin", data: null)
            }

            String requestId = UUID.randomUUID().toString()
            loggingManaged.info(["requestId": requestId, "siptrunk": ip, "loc": loc,
                                 "username" : genericApi.getSessionData(request)[GenericApi.USERNAME]])
            def reverseIp = ip.tokenize('.').reverse().join('.')
            def commands = ["list naptrrecord ${reverseIp}.in-addr.arpa"]
            String resp = sshUtil.runIpwCli(requestId, session, username, password, commands)

            sshUtil.disconnect(session)

            if (resp.startsWith("No matching object")) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Địa chỉ IP chưa tồn tại, có thể khai báo", data: resp)
            }

            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Địa chỉ IP đã tồn tại", data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/createIPW")
    CommonResponse createIPW(@RequestBody Map<String, Object> body) {
        try {
            if (!request.getHeader("Authorization")) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Request không hợp lệ", data: null)
            }
            Session session = sshUtil.connect(ipserver, body.loc == "HNI" ? hni_ipw_port : hcm_ipw_port, username, password)
            if (session == null) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Không ssh được đến server. Vui lòng kiểm tra lại thông tin", data: null)
            }

            String requestId = UUID.randomUUID().toString()
            loggingManaged.info(["requestId": requestId, "siptrunk": body.ip, "accountName": body.accountName, "loc": body.loc,
                                 "username" : genericApi.getSessionData(request)[GenericApi.USERNAME]])

            def reverseIp = body.ip.tokenize('.').reverse().join('.')
            def commands = ["create naptrrecord ${reverseIp}.in-addr.arpa -set container=HCMIMS1;order=100;preference=10;flags=\"u\";service=\"SIP+D2U\";regexp=\"!^.\$!sip: ${body.accountName}@ims.vnpt.vn!\"",
                            "create naptrrecord ${reverseIp}.in-addr.arpa -set container=HN.IMS1;order=100;preference=10;flags=\"u\";service=\"SIP+D2U\";regexp=\"!^.\$!sip: ${body.accountName}@ims.vnpt.vn!\"",
                            "update dnsserver"]
            String resp = sshUtil.runIpwCli(requestId, session, username, password, commands)
            if (resp?.contains("already exists")) {
                sshUtil.disconnect(session)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "Khai mới IPW thành công", data: resp)

            }
            sshUtil.disconnect(session)
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Khai mới IPW không thành công!", data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/configureSBG")
    CommonResponse configureSBG(@RequestBody Map<String, Object> body) {
        try {
//            if (request.getHeader("Authorization")) {
//                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Request không hợp lệ", data: null)
//            }

            Session session = sshUtil.connect(ipserver, body.loc == "HNI" ? hni_sbg_port : hcm_sbg_port, username, password)
            if (session == null) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Không ssh được đến server. Vui lòng kiểm tra lại thông tin", data: null)
            }

            String resp = sshUtil.configureSBG(session, body.ip as String)
            sshUtil.disconnect(session)

            if (resp.startsWith("Success")) {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Có lỗi xảy ra khi khai mới SBG", data: resp)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
