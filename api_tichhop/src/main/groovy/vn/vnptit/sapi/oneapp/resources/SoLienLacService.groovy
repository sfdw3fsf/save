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
@RequestMapping("solienlac")
class SoLienLacService {
    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    String url = "http://s19.zneunuaansgdmyhomework.vnedu.vn/v5/"
    String token = "75fe20c3c009a340bd3f161316f7925b"

    @GetMapping("/laydstruongthuoctinhdynamic")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse laydstruongthuoctinhdynamic(@RequestParam String idtinh) {
        try {
            String urlT = url + "?call=app.doisoat.serv.api.getListTruong&token=" + token + "&tinh_id=" + idtinh + "&user_name=vnpt.tvtung"
            Map<String, String> headers = [:]
            def resp = httpManaged.get(urlT, headers)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/laydshocsinhtronglop")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse laydshocsinhtronglop(@RequestParam String idtruong, @RequestParam String idlop) {
        try {
            String urlT = url + "?call=app.doisoat.serv.api.getListHocSinhLop&token=" + token + "&truong_id=" + idtruong + "&lop_hoc_id=" + idlop + "&user_name=vnpt.tvtung"
            Map<String, String> headers = [:]
            def resp = httpManaged.get(urlT, headers)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/laydssothuoctinhdynamic")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse laydssothuoctinhdynamic(@RequestParam String idtinh) {
        try {
            String urlT = url + "?call=app.doisoat.serv.api.getListTruong&token=" + token + "&tinh_id=" + idtinh + "&user_name=vnpt.tvtung&get_school=0"
            Map<String, String> headers = [:]
            def resp = httpManaged.get(urlT, headers)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/laydstruongphonggiaoduc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse laydstruongphonggiaoduc(@RequestParam String idtinh, String pgd_hoac_nhatruong) {
        try {
            if ("NT".equals(pgd_hoac_nhatruong))
                return this.laydstruongthuoctinhdynamic(idtinh)
            else return this.laydssothuoctinhdynamic(idtinh)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}