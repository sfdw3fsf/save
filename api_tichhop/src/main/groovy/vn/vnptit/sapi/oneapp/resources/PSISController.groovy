package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.TAO_REQ_CHUNG
import vn.vnptit.sapi.oneapp.models.taophieubaohanh_req
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder

import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("psis")
class PSISController {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.psis.url}")
    String psisUrl

    @Value("\${spring.httpservices.psis.auth}")
    String psisAuth

    def getAccKey() {
        try {
            def resp = HttpManaged.post(["url": psisUrl + "/OneBss/GetAccessToken"],
                    ["Authorization": psisAuth,
                     "Content-Type": "application/json"], "\"T25lQnNzLlZlcjAxOkVycCQyMDIzLTIwMjQ=\"")
            def respjs = new JsonSlurper().parseText(resp)
            return respjs
        } catch (Exception e) {
            [error : e.message]
        }
    }

    @PostMapping("/lay_ds_nhathau")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> lay_ds_nhathau(
            @RequestBody TAO_REQ_CHUNG body
    ) {
        try {
            String getkey = body.key
            def resp = HttpManaged.post([url: psisUrl + "/OneBss/DanhMucDoiTuong"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "\"${getkey}\"" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["Data"] != ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString() , HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/lay_ds_vattu")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> lay_ds_vattu(
            @RequestBody TAO_REQ_CHUNG body
    ) {
        try {
            String getkey = body.key
            def resp = HttpManaged.post([url: psisUrl + "/OneBss/DanhMucDoiTuong"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "\"${getkey}\"" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["Data"] != ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString() , HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/taophieubaohanh")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> taophieubaohanh(
            @RequestBody taophieubaohanh_req body
    ) {
        try {
            def json_body = new Gson().toJson(body)
            def resp = HttpManaged.post([url: "https://vendor.psis.vnpt.vn/QLMSEXTERNALAPI/OneBss/TaoPhieuBaoHanh"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "${json_body}" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["Data"] != ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString() , HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/huyphieubaohanh")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> huyphieubaohanh(
            @RequestBody TAO_REQ_CHUNG body
    ) {
        try {
            String getkey = body.key
            def resp = HttpManaged.post([url: psisUrl + "/OneBss/HuyPhieuBaoHanh"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "\"${getkey}\"" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["IsError"] == false){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/baohanhtracuuthongtinphieu")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> baohanhtracuuthongtinphieu(
            @RequestBody TAO_REQ_CHUNG body
    ) {
        try {
            String getkey = body.key
            def resp = HttpManaged.post([url: psisUrl + "/OneBss/BaoHanhTraCuuThongTinPhieu"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "\"${getkey}\"" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["Data"] != ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString() , HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/baohanhtracuuthongtinlo")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> baohanhtracuuthongtinlo(@RequestBody TAO_REQ_CHUNG body) {
        try {
            String getkey = body.key
            def resp = HttpManaged.post([url: psisUrl + "/OneBss/BaoHanhTraCuuThongTinLo"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "\"${getkey}\"" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["Data"] != ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString() , HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/nghiemthulobaohanh")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Operation(summary = "sp_them_thongbao_push_cho_nhanvien", description = "sp_them_thongbao_push_cho_nhanvien", tags = ["sp_them_thongbao_push_cho_nhanvien"])
    ResponseEntity<BaseResponse<Void>> nghiemthulobaohanh(@RequestBody TAO_REQ_CHUNG body) {
        try {
            String getkey = body.key
            def resp = HttpManaged.post([url: psisUrl + "/OneBss/NghiemThuLoBaoHanh"],
                    [
                            "Authorization": "PSIS OneBss",
                            "XI-Authorization":"${getAccKey()["Data"]}".toString(),
                            "Content-Type": "application/json",
                    ], "\"${getkey}\"" )
            def respjs = new JsonSlurper().parseText(resp)
            if(respjs["IsError"] != true){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(respjs["Data"], null))
            }else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, respjs["Msg"].toString() , HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }
}
