package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
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
import vn.vnptit.sapi.oneapp.models.MeshWifiVerify
import vn.vnptit.sapi.oneapp.service.TraCuuService
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.LoggingInOutVisa

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("mesh_wifi")
class MeshWifiService {

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    HttpManaged httpManaged
    @Autowired
    TraCuuService traCuuService
    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.mesh_wifi.url}")
    String url

    @Value("\${spring.httpservices.mesh_wifi.key}")
    String key

    @GetMapping("/verifyDeviceActivation")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @LoggingInOutVisa
    CommonResponse verifyDeviceActivation(@Parameter(in = ParameterIn.QUERY, description = "Serial number", required = true, schema = @Schema()) @Valid @RequestParam(value = "serialNumber", required = false) String serialNumber,
                                          @Parameter(in = ParameterIn.QUERY, description = "Area code", required = true, schema = @Schema()) @Valid @RequestParam(value = "areaCode", required = false) String areaCode,
                                          @Parameter(in = ParameterIn.QUERY, description = "Package code", required = true, schema = @Schema()) @Valid @RequestParam(value = "packageCode", required = false) String packageCode,
                                          @Parameter(in = ParameterIn.QUERY, description = "Package type", required = true, schema = @Schema()) @Valid @RequestParam(value = "packageType", required = false) String packageType) {
        try {
            if (serialNumber.startsWith("ZTE")) {
                return new CommonResponse(errorCode: 900, faultCode: "900", faultString: "ZTE Mesh device", data: null)
            }

            // https://cntt.vnpt.vn/browse/KHDN-27569
            try {
                String loai = traCuuService.fn_tracuu_serial_ma_vt(request.getHeader("Authorization"), serialNumber)
                if (loai == "1" || loai == "2") {
                    def ret = [errorCode: 200, faultCode: "", faultString: "", data: null, activationStatus: true]
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
                }
            } catch (Exception ignored) {
            }

            Map<String, String> searchParams = [:]
            searchParams.put("serialNumber", serialNumber)
            searchParams.put("areaCode", areaCode)
            if (packageCode) {
                searchParams.put("packageCode", packageCode)
            }
            if (packageType) {
                searchParams.put("packageType", packageType)
            }
            def res = get("verifyDeviceActivation", searchParams)
            def ret = new JsonSlurper().parseText(res)
            int errorCode = ret["errorCode"]?.toString()?.toInteger()
            String message = ret["errorMessage"]
            return new CommonResponse(errorCode: (errorCode == 200 ? 0 : errorCode), faultCode: "$errorCode", faultString: message, data: ret)
//            def ret = [errorCode: 200, faultCode: "", faultString: "", data: null, activationStatus:true]
//            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/verifyDeviceActivation_v3")
    @LoggingInOutVisa
    CommonResponse verifyDeviceActivation_v3(@RequestBody MeshWifiVerify body) {
        try {
            if (body.areaCode != "THA" && body.areaCode != "QTI" && body.areaCode != "HNI") {
                return verifyDeviceActivation(body.serialNumber, body.areaCode, body.packageCode, body.packageType)
            }
            if (body.serialNumber.startsWith("ZTE")) {
                return new CommonResponse(errorCode: 900, faultCode: "900", faultString: "ZTE Mesh device", data: null)
            }

            // https://cntt.vnpt.vn/browse/KHDN-27569
            try {
                String loai = traCuuService.fn_tracuu_serial_ma_vt(request.getHeader("Authorization"), body.serialNumber)
                if (loai == "1" || loai == "2") {
                    def ret = [errorCode: 200, faultCode: "", faultString: "", data: null, activationStatus: true]
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
                }
            } catch (Exception ignored) {
            }

            Map<String, String> headers = [:]
            headers.put("Authorization", key)
            headers.put("Content-Type", "application/json")

            String urlF = url + "one-mesh/api/v2/verifyDeviceActivation"
            def res = httpManaged.okpost(urlF, headers, objectMapper.writeValueAsString(body), 270)
            try {
                def ret = new JsonSlurper().parseText(res)
                int errorCode = ret["errorCode"]?.toString()?.toInteger()
                String message = ret["errorMessage"]
                if (errorCode == 400) {
                    message = "Yêu cầu chưa được xác thực"
                }
                if (errorCode == 405) {
                    message = "Tham số không hợp lệ"
                }
                if (errorCode == 407) {
                    message = "Thiết bị không tồn tại"
                }
                if (errorCode == 409) {
                    message = "Thiết bị mất kết nối"
                }
                if (errorCode == 408) {
                    message = "Yêu cầu vượt quá thời gian chờ"
                }
                if (errorCode == 412) {
                    message = "Ip public của thiết bị không hợp lệ (ngoài dải VNPT), ipPublicDetect = ${ret["ipPublicDetect"]}"
                }
                if (errorCode == 413) {
                    message = "Thiết bị không khớp chủng loại"
                }
                if (errorCode == 414) {
                    message = "Thiết bị đang ở chế độ xuất xưởng"
                }
                if (errorCode == 415) {
                    message = "Thông tin Gpon serial, mã fiber không khớp, gponSerialDetect = ${ret["gponSerialDetect"]}, fiberCodeDetect = ${ret["fiberCodeDetect"]}, ipPublicDetect = ${ret["ipPublicDetect"]}"
                }
                if (errorCode == 500) {
                    message = "Lỗi hệ thống"
                }
                return new CommonResponse(errorCode: (errorCode == 200 ? 0 : errorCode), faultCode: "$errorCode", faultString: message, data: ret)
            } catch (Exception ignored) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: res, data: null)
            }
//            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/verifyDeviceActivation_v2")
    @LoggingInOutVisa
    CommonResponse verifyDeviceActivation_v2(@RequestBody Map<String, String> body) {
        try {
            if (body.serialNumber.startsWith("ZTE")) {
                return new CommonResponse(errorCode: 900, faultCode: "900", faultString: "ZTE Mesh device", data: null)
            }

            // https://cntt.vnpt.vn/browse/KHDN-27569
            try {
                String loai = traCuuService.fn_tracuu_serial_ma_vt(request.getHeader("Authorization"), body.serialNumber)
                if (loai == "1" || loai == "2") {
                    def ret = [errorCode: 200, faultCode: "", faultString: "", data: null, activationStatus: true]
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
                }
            } catch (Exception ignored) {
            }

            Map<String, String> searchParams = [:]
            searchParams.put("serialNumber", body.serialNumber)
            searchParams.put("areaCode", body.areaCode)
            if (body.packageCode) {
                searchParams.put("packageCode", body.packageCode)
            }
            if (body.packageType) {
                searchParams.put("packageType", body.packageType)
            }
            def res = get("verifyDeviceActivation", searchParams)
            def ret = new JsonSlurper().parseText(res)
            int errorCode = ret["errorCode"]?.toString()?.toInteger()
            String message = ret["errorMessage"]
            return new CommonResponse(errorCode: (errorCode == 200 ? 0 : errorCode), faultCode: "$errorCode", faultString: message, data: ret)
//            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


    String get(String path, Map<String, String> param) {
        def ls = param.collect { key, value -> key + "=" + value }.join("&")
        Map<String, String> headers = [:]
        String url = url + path + "?" + ls
        headers.put("Authorization", key)
        headers.put("Content-Type", "application/json")
        String resp = httpManaged.get(url, headers)
        return resp
    }
}
