package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.fmis.FS_KTDH_DHSXKD_REQ
import vn.vnptit.sapi.oneapp.models.fmis.FS_KTDH_DHSXKD_TTGD_XOA_REQ
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("fmis")
class FMISServiceNew {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    String fmisSoapUrl = "http://10.168.3.92:8888"

    //String fmisSoapUrl = "http://10.168.3.92/quannm/Service/ktvt/sktvthp_ct.asmx"

    String fmisKtdh = "http://10.168.3.92/quannm/Service/ktdh/sktdh_lct.asmx"

    String fmisSoapUrl_2025 = "https://fmis2025.vnpt.vn/Service/ktvt/sktvthp_ct.asmx"

    String fmisKtdh_2025 = "https://fmis2025.vnpt.vn/Service/ktdh/sktdh_lct.asmx"


    String nvl(Object object) {
        String result = ""
        if (object != null)
            result = object.toString().trim()
        return result
    }

    @PostMapping("/FS_ktdh_dhsxkd_ttgd_xoa")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse FS_ktdh_dhsxkd_ttgd_xoa(@Valid @RequestBody FS_KTDH_DHSXKD_TTGD_XOA_REQ body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            loggingManaged.info("[FMISServiceNew] - [FS_ktdh_dhsxkd_ttgd_xoa] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            def env = post1(fmisKtdh,
                    """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <FS_ktdh_dhsxkd_ttgd_xoa xmlns="http://tempuri.org/">
                                  <user>${body.user}</user>
                                  <pas>${body.pas}</pas>
                                  <ma_dvi>${body.ma_dvi}</ma_dvi>
                                  <NSD>${body.nsd}</NSD>
                                  <MAT_KHAU>${body.mat_khau}</MAT_KHAU>
                                  <DATA>${body.data}</DATA>
                                </FS_ktdh_dhsxkd_ttgd_xoa>
                              </soap:Body>
                            </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                loggingManaged.info("[FMISServiceNew] - [FS_ktdh_dhsxkd_ttgd_xoa] - Response: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", uuid).put("faultCode", fault.Code.text()).put("faultString", fault.Reason.text()).build())
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            loggingManaged.info("[FMISServiceNew] - [FS_ktdh_dhsxkd_ttgd_xoa] - Response: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuid).put("data", env.Body.FS_ktdh_dhsxkd_ttgd_xoaResponse.FS_ktdh_dhsxkd_ttgd_xoaResult.text()).build())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.FS_ktdh_dhsxkd_ttgd_xoaResponse.FS_ktdh_dhsxkd_ttgd_xoaResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/FS_ktdh_dhsxkd_ttgd_xoa_2025")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse FS_ktdh_dhsxkd_ttgd_xoa_2025(@Valid @RequestBody FS_KTDH_DHSXKD_TTGD_XOA_REQ body) {
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            loggingManaged.info("[FMISServiceNew] - [FS_ktdh_dhsxkd_ttgd_xoa] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            def env = post1(fmisKtdh_2025,
                    """<?xml version="1.0" encoding="utf-8"?>
                            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                              <soap:Body>
                                <FS_ktdh_dhsxkd_ttgd_xoa xmlns="http://tempuri.org/">
                                  <user>${body.user}</user>
                                  <pas>${body.pas}</pas>
                                  <ma_dvi>${body.ma_dvi}</ma_dvi>
                                  <NSD>${body.nsd}</NSD>
                                  <MAT_KHAU>${body.mat_khau}</MAT_KHAU>
                                  <DATA>${body.data}</DATA>
                                </FS_ktdh_dhsxkd_ttgd_xoa>
                              </soap:Body>
                            </soap:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                loggingManaged.info("[FMISServiceNew] - [FS_ktdh_dhsxkd_ttgd_xoa] - Response: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", uuid).put("faultCode", fault.Code.text()).put("faultString", fault.Reason.text()).build())
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            loggingManaged.info("[FMISServiceNew] - [FS_ktdh_dhsxkd_ttgd_xoa] - Response: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuid).put("data", env.Body.FS_ktdh_dhsxkd_ttgd_xoaResponse.FS_ktdh_dhsxkd_ttgd_xoaResult.text()).build())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.FS_ktdh_dhsxkd_ttgd_xoaResponse.FS_ktdh_dhsxkd_ttgd_xoaResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/fs_ktdh_dhsxkd")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse fs_ktdh_dhsxkd(@Valid @RequestBody FS_KTDH_DHSXKD_REQ body) {
        body.noi_dung = body.noi_dung.replaceAll("&", "&amp;")
        String thongtinChiTiet = ""
        UUID uuid = UUID.randomUUID()
        String uuidAsString = uuid.toString()
        try {
            loggingManaged.info("[FMISServiceNew] - [fs_ktdh_dhsxkd] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuidAsString).put("body", new Gson().toJson(body)).build())
            if (body.dataCT_ChungTu != null && body.dataCT_ChungTu.size() > 0) {
                for (Map<String, String> item : body.dataCT_ChungTu) {
                    thongtinChiTiet += """<CHITIET_CHUNGTU>
                                                <ID_CHITIET>${nvl(item.id_chitiet)}</ID_CHITIET>
                                                <MA_DVI_PS>${nvl(item.ma_dvi_ps)}</MA_DVI_PS>
                                                <MA_DVI_NH>${nvl(item.ma_dvi_nh)}</MA_DVI_NH>
                                                <MA_TK_KHO>${nvl(item.ma_tk_kho)}</MA_TK_KHO>
                                                <MA_TK_DU>${nvl(item.ma_tk_du)}</MA_TK_DU>
                                                <TIEN>${nvl(item.tien)}</TIEN> 
                                            </CHITIET_CHUNGTU>"""
                }
            }

            def env = post1(fmisSoapUrl,
                    """<?xml version="1.0" encoding="utf-8"?>
                        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <FS_ktdh_dhsxkd xmlns="http://tempuri.org/">
                                    <user>${body.user}</user>
                                    <pas>${body.pas}</pas>
                                    <ma_dvi>${body.ma_dvi}</ma_dvi>
                                    <NSD>${body.nsd}</NSD>
                                    <MAT_KHAU>${body.mat_khau}</MAT_KHAU>
                                    <ID_PHIEU>${body.id_phieu}</ID_PHIEU>
                                    <SO_PHIEU>${body.so_phieu}</SO_PHIEU>
                                    <NGAY_HT>${body.ngay_ht}</NGAY_HT>
                                    <LOAI>${body.loai}</LOAI>
                                    <NOI_DUNG><![CDATA[${body.noi_dung}]]></NOI_DUNG>
                                    <NGUOI_LAP>${body.nguoi_lap}</NGUOI_LAP>
                                    <EMAIL>${body.email}</EMAIL>
                                    <SO_PHIEU_DH>${body.so_phieu_dh}</SO_PHIEU_DH>
                                    <b_table_data>$thongtinChiTiet</b_table_data>
                                </FS_ktdh_dhsxkd>
                            </soap:Body>
                        </soap:Envelope>""")
            
            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                loggingManaged.info("[FMISServiceNew] - [fs_ktdh_dhsxkd] - Response: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", uuid).put("faultCode", fault.Code.text()).put("faultString", fault.Reason.text()).build())
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            loggingManaged.info("[FMISServiceNew] - [fs_ktdh_dhsxkd] - Response: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", uuid).put("data", env.Body.FS_ktdh_dhsxkdResponse.FS_ktdh_dhsxkdResult.text()).build())
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: env.Body.FS_ktdh_dhsxkdResponse.FS_ktdh_dhsxkdResult.text())
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get-token-api")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse get_token_api( @RequestBody Map<?,?> body) {
        try {
            def input = [
                "dvi_gui": body.dvi_gui,
                "email" : body.email,
                "client_id" : "ONEBSS_HACHTOAN",
                "code": "Abc123Nbvsd"
            ]
            String resp = HttpManaged.post([url : "http://10.168.3.92:8888" + "/TaiKhoan/get-token-api"], ["Content-Type": "application/json"], new ObjectMapper().writeValueAsString(input))
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: new JsonSlurper().parseText(resp))
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/hach_toan_nhandl_import")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse hach_toan_nhandl_import( @RequestBody Map<?,?> body) {
        try {
            String resp = HttpManaged.post([url : fmisSoapUrl + "/HachToan/hach_toan_nhandl_import"], ["Content-Type": "application/json", "Authorization": "Bearer " + body.token], new ObjectMapper().writeValueAsString(body.data))
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: new JsonSlurper().parseText(resp))
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
