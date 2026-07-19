package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonSlurper
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
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.HuyHopDongEContract_REQ
import vn.vnptit.sapi.oneapp.models.econtract.TaoHopDongREQ
import vn.vnptit.sapi.oneapp.models.SubmitDraft_REQ
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

@RestController
@RequestMapping("eContract")
class VNPTEContractService {

    @Autowired
    HttpManaged httpManaged

    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.vnpt_econtract.url}")
    String domain

    @Value("\${spring.httpservices.vnpt_econtract.token_id}")
    String token_id

    @Value("\${spring.httpservices.vnpt_econtract.token_key}")
    String token_key

    @Autowired
    NativeWebRequest request

    @Autowired
    GenericApi genericApi

    // Tra cứu danh sách hợp đồng mẫu
    @GetMapping("/TraCuuDanhSachHopDongMau")
    CommonResponse TraCuuDanhSachHopDongMau(@RequestParam String typeSubID, @RequestParam String typeReqID, @RequestParam(required = false) String loaiKH) {
        try {
            // String url = domain + "/template-service/one-bss/template-group?Type_Sub_ID=" + typeSubID + "&Type_Req_ID=" + typeReqID
            // return execute(url, "GET", null)

            def session = genericApi.getSessionData(request)
            String maTinh = session[GenericApi.MA_TINH]

            // Fake hopdong
            def result = [:]
            def template_Group_Ids = [] 
            def data = [:]
            
            if (loaiKH && loaiKH == "1") {
                data.template_fields_id = "676e4fc3a3fdd8980e30ce8c"
                data.template_group_id = "676e50f68d27813f6be63c30"
                data.template_group_name = "KHDN_ONEBSS_Bộ hồ sơ mẫu (Di động - Internet - Truyền hình trả sau)"
                data.template_id = "676e4fc3a3fdd8980e30ce8d"
                template_Group_Ids.add(data)

                data = [:]
                data.template_fields_id = "6937d56478e4a73989acae93"
                data.template_group_id = "6937993a8a97874999cec741"
                data.template_group_name = "Mẫu 05_KHDN_Hợp đồng mẫu viễn thông truyền hình trả trước"
                data.template_id = "6937989378e4a73989acae35"
                template_Group_Ids.add(data)

                data = [:]
                data.template_fields_id = "6937ec5978e4a73989acaee7"
                data.template_group_id = "69379a5078e4a73989acae3f"
                data.template_group_name = "Mẫu 06_KHDN_Hợp đồng mẫu viễn thông truyền hình trả sau"
                data.template_id = "693798b578e4a73989acae3a"
                template_Group_Ids.add(data)
            } else {
                if (maTinh == "HCM") {
                    data.template_fields_id = "6818357f065c1bce57857b72"
                    data.template_group_id = "6819729f9bc8d115c4dd516c"
                    data.template_group_name = "Mẫu 03. Trả sau KHCN_Hợp đồng mẫu (có Cam kết)"
                    data.template_id = "66bb0850c32f0404c30e4397"
                    template_Group_Ids.add(data)

                    data = [:]
                    data.template_fields_id = "681835ec065c1bce57857b7b"
                    data.template_group_id = "6819732ba6e1c03d93335d74"
                    data.template_group_name = "Mẫu 02. Trả trước KHCN_Hợp đồng mẫu (có Cam kết)"
                    data.template_id = "680a5799cb630202c3a58d41"
                    template_Group_Ids.add(data)
                } else {
                    data.template_fields_id = "68073e8afe7c6cfd9085d1d3"
                    data.template_group_id = "5ee83d5d4e859fd00d9cf627"
                    data.template_group_name = "Mẫu 03. Trả sau_KHCN_Hợp đồng mẫu"
                    data.template_id = "5ee83d8d38a35f04df861a63"
                    template_Group_Ids.add(data)

                    data = [:]
                    data.template_fields_id = "680a582d85682dc663b8f0ae"
                    data.template_group_id = "680a58ab85682dc663b8f0b4"
                    data.template_group_name = "Mẫu 02. Trả trước_KHCN_Hợp đồng mẫu"
                    data.template_id = "680a582d85682dc663b8f0af"
                    template_Group_Ids.add(data)
                }
            }

            result.Template_Group_Ids = template_Group_Ids
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Lấy bộ biến của hợp đồng mẫu
    @GetMapping("/LayBoBienCuaHopDongMau")
    CommonResponse LayBoBienCuaHopDongMau(@RequestParam String idTemplateField) {
        try {
            String url = domain + "/template-service/fields/intergrate/" + idTemplateField
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Tải hợp đồng mẫu
    @GetMapping("/TaiHopDongMau")
    CommonResponse TaiHopDongMau(@RequestParam String idTemplate) {
        try {
            String url = domain + "/template-service/templates/integrate/" + idTemplate +"/download"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Tạo hợp đồng
    @LoggingInOut
    @PostMapping("/TaoHopDong")
//    @FakeSuccessCommonResponse
    CommonResponse TaoHopDong(@RequestBody TaoHopDongREQ body) {
        try {
            String url = domain + "/management-service/contracts/create"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Tra cứu hợp đồng
    @GetMapping("/TraCuuHopDong")
    CommonResponse TraCuuHopDong(@RequestParam(required = false) String maKhachHang,
                                 @RequestParam(required = false) String maGiaoDich, @RequestParam(required = false) String maDonHang,
                                 @RequestParam(required = false) String maThueBao, @RequestParam(required = false) String maSoThue,
                                 @RequestParam(required = false) String contractId, @RequestParam(required = false) String soGiayTo,
                                 @RequestParam(required = false) String soDienThoai, @RequestParam(required = false) String tuNgay,
                                 @RequestParam(required = false) String denNgay,@RequestParam(required = false) String maHrmNguoiXuLy) {
        try {
            String url = domain + "/management-service/v3/parties/contract-list-no-page?"
            if (maKhachHang && !maKhachHang.isEmpty()) {
                url += ("&maKhachHang=" + maKhachHang)
            }
            if (maGiaoDich && !maGiaoDich.isEmpty()) {
                url += ("&maGiaoDich=" + maGiaoDich)
            }
            if (maDonHang && !maDonHang.isEmpty()) {
                url += ("&maDonHang=" + maDonHang)
            }
            if (maThueBao && !maThueBao.isEmpty()) {
                url += ("&maThueBao=" + maThueBao)
            }
            if (maSoThue && !maSoThue.isEmpty()) {
                url += ("&maSoThue=" + maSoThue)
            }
            if (contractId && !contractId.isEmpty()) {
                url += ("&contractId=" + contractId)
            }
            if (soGiayTo && !soGiayTo.isEmpty()) {
                url += ("&soGiayTo=" + soGiayTo)
            }
            if (soDienThoai && !soDienThoai.isEmpty()) {
                url += ("&soDienThoai=" + soDienThoai)
            }
            if (tuNgay && !tuNgay.isEmpty()) {
                url += ("&tuNgay=" + tuNgay)
            }
            if (denNgay && !denNgay.isEmpty()) {
                url += ("&denNgay=" + denNgay)
            }
            if (maHrmNguoiXuLy && !maHrmNguoiXuLy.isEmpty()) {
                url += ("&maHrmNguoiXuLy=" + maHrmNguoiXuLy)
            }
            return execute(url.replace("?&", "?"), "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Lấy thong tin nguoi tao hop dong ben eContract
    @GetMapping("/LayThongTinBenB")
    CommonResponse LayThongTinBenB(@RequestParam String hrmCode) {
        try {
            String url = domain + "/management-service/onebss/" + hrmCode + "/party-signer-info"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Tra cứu hợp đồng chưa ký
    @GetMapping("/TraCuuHopDongChuaKy")
    CommonResponse TraCuuHopDongChuaKy(@RequestParam String customerCode) {
        try {
            String url = domain + "/management-service/contracts/customer-not-yet-signed?customerCode=" + customerCode
            CommonResponse resp = execute(url, "GET", null)
            try {
                if (resp.errorCode == 0 && resp.data["data"] && ((List) resp.data["data"]).size() > 0) {
                    List respNoBBNT = resp.data["data"].findAll( { it["category"] != "BBNT" } )
                    resp.data["data"] = respNoBBNT
                    return resp
                } else {
                    return resp
                }
            } catch (Exception ex) {
                return resp
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Hủy hợp đồng
    @LoggingInOut
    @PostMapping("/HuyHopDong")
    CommonResponse HuyHopDong(@RequestParam String contractId, @RequestBody HuyHopDongEContract_REQ body) {
        try {
            String url = domain + "/management-service/contracts/integrate/cancel/" + contractId
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Gửi hợp đồng auto
    @LoggingInOut
    @PostMapping("/SubmitSignAuto")
    CommonResponse SubmitSignAuto(@RequestBody SubmitDraft_REQ body) {
        try {
            String requestId = UUID.randomUUID().toString()
            loggingManaged.info("[VNPTEContractService] - [SubmitSignAuto] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("contractId", body.contractId).build())
            String url = domain + "/management-service/contracts/" + body.contractId + "/argumentId/" + requestId + "/submit-signauto"
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Gửi hợp đồng
    @LoggingInOut
    @PostMapping("/SubmitDraft")
    CommonResponse SubmitDraft(@RequestBody SubmitDraft_REQ body) {
        try {
            String url = domain + "/management-service/contracts/" + body.contractId + "/submit-draft"
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // Lấy danh sách hợp đồng - https://cntt.vnpt.vn/browse/SRCR-31482
    @PostMapping("/LayDanhSachHopDong")
    CommonResponse LayDanhSachHopDong(@RequestBody Map body) {
        try {
            String url = domain + "/management-service/contracts/onebss/contracts-valid"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    CommonResponse execute(String url, String method, Object body) {
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json")
        headers.put("Token-Id", token_id)
        headers.put("Token-Key", token_key)
        String resp = ""
        if (method == "GET") {
            resp = httpManaged.get(url, headers)
        } else {
            resp = httpManaged.post([url: url], headers, body != null ? new Gson().toJson(body) : "")
        }
        return xlResp(resp)
    }

    CommonResponse xlResp(String resp) {
        try {
            def res = new JsonSlurper().parseText(resp)
            if (res["message"].toString() == "ECT-00000000") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["object"])
            } else {
                String error = res["message"] + ": "
                try {
                    if (res["errors"] && ((List) res["errors"]).size() > 0) {
                        for (def er : res["errors"]) {
                            error += (er["message"] + ", ")
                        }
                    } else if (res["error"] && ((List) res["error"]).size() > 0) {
                        for (String er : res["error"]) {
                            error += (er + ", ")
                        }
                    }
                } catch (Exception ex) {}

                return new CommonResponse(errorCode: 1, faultCode: res["statusCode"].toString(), faultString: error, data: res)
            }
        } catch (Exception ex) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: resp, data: "")
        }
    } 

}
