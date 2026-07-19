package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.cloudcamera.OneHome_REQ
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("cloud_camera")
class CloudCameraService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.cloudcamera.url}")
    String cloudCameraUrl

    CommonResponse xlResp(String data) {
        try {
            def resp = new JsonSlurper().parseText(data)
            if (resp["status"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: (resp["description"] ? resp["description"] : resp["message"]), data: resp)
            }
        } catch (Exception ex) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: data)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_DangKyGoiCuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_DangKyGoiCuoc(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_DangKyGoiCuoc xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_DangKyGoiCuoc>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_DangKyGoiCuocResponse.OneHome_DangKyGoiCuocResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_DoiGoiCuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_DoiGoiCuoc(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_DoiGoiCuoc xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_DoiGoiCuoc>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_DoiGoiCuocResponse.OneHome_DoiGoiCuocResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_GiaHanThueBao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_GiaHanThueBao(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_GiaHanThueBao xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_GiaHanThueBao>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_GiaHanThueBaoResponse.OneHome_GiaHanThueBaoResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_HuyThueBao")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_HuyThueBao(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_HuyThueBao xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_HuyThueBao>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_HuyThueBaoResponse.OneHome_HuyThueBaoResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_KhoiPhucDichVu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_KhoiPhucDichVu(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_KhoiPhucDichVu xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_KhoiPhucDichVu>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_KhoiPhucDichVuResponse.OneHome_KhoiPhucDichVuResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_TamNgungDichVu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_TamNgungDichVu(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_TamNgungDichVu xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_TamNgungDichVu>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_TamNgungDichVuResponse.OneHome_TamNgungDichVuResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_DangKyGoiCuocTichHop")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_DangKyGoiCuocTichHop(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_DangKyGoiCuocTichHop xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_DangKyGoiCuocTichHop>
                            </soap:Body>
                        </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_DangKyGoiCuocTichHopResponse.OneHome_DangKyGoiCuocTichHopResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_CapNhat_ThongTin_VatTu_GoiCuocTichHop")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_CapNhat_ThongTin_VatTu_GoiCuocTichHop(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_CapNhat_ThongTin_VatTu_GoiCuocTichHop xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_CapNhat_ThongTin_VatTu_GoiCuocTichHop>
                            </soap:Body>
                        </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_CapNhat_ThongTin_VatTu_GoiCuocTichHopResponse.OneHome_CapNhat_ThongTin_VatTu_GoiCuocTichHopResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_TraCuu_ThongTin_ThietBi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_TraCuu_ThongTin_ThietBi(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_TraCuu_ThongTin_ThietBi xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_TraCuu_ThongTin_ThietBi>
                            </soap:Body>
                        </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_TraCuu_ThongTin_ThietBiResponse.OneHome_TraCuu_ThongTin_ThietBiResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_TraCuu_ThongTin_GoiCuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_TraCuu_ThongTin_GoiCuoc(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_TraCuu_ThongTin_GoiCuoc xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_TraCuu_ThongTin_GoiCuoc>
                            </soap:Body>
                        </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_TraCuu_ThongTin_GoiCuocResponse.OneHome_TraCuu_ThongTin_GoiCuocResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_TraCuu_ThongTin_TaiKhoan_Theo_ThietBi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_TraCuu_ThongTin_TaiKhoan_Theo_ThietBi(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_TraCuu_ThongTin_TaiKhoan_Theo_ThietBi xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_TraCuu_ThongTin_TaiKhoan_Theo_ThietBi>
                            </soap:Body>
                        </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_TraCuu_ThongTin_TaiKhoan_Theo_ThietBiResponse.OneHome_TraCuu_ThongTin_TaiKhoan_Theo_ThietBiResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/OneHome_TraCuu_ThongTin_ThietBi_Theo_TaiKhoan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse OneHome_TraCuu_ThongTin_ThietBi_Theo_TaiKhoan(@Valid @RequestBody OneHome_REQ body) {
        try {
            def env = post1(cloudCameraUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <OneHome_TraCuu_ThongTin_ThietBi_Theo_TaiKhoan xmlns="http://tempuri.org/">
                                    <jsonCam>${body.jsonCam}</jsonCam>
                                </OneHome_TraCuu_ThongTin_ThietBi_Theo_TaiKhoan>
                            </soap:Body>
                        </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            return xlResp(env.Body.OneHome_TraCuu_ThongTin_ThietBi_Theo_TaiKhoanResponse.OneHome_TraCuu_ThongTin_ThietBi_Theo_TaiKhoanResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    GPathResult post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("Host", "fmis.vnpt.vn")
        headers.put("Content-Type", "text/xml; charset=utf-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return new XmlSlurper().parseText(resp)
    }
}
