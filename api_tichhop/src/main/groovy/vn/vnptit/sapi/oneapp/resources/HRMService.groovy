package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.DecompressingEntity
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.service.HopDongService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

@RestController
@RequestMapping("hrm")
class HRMService {
    @Autowired
    CacheManager cacheManager
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request
    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    HopDongService hopDongService

    ObjectMapper json = new ObjectMapper();

    class ProfileInfo
    {
        String token;
        String ma_nv;
        String tennhanvien;
        Integer donvi_id;
        String donvi;

        ProfileInfo(String _token, String _ma_nv, String _tennhanvien, Integer _donvi_id, String _donvi)
        {
            this.token = _token;
            this.ma_nv = _ma_nv;
            this.tennhanvien = _tennhanvien;
            this.donvi_id = _donvi_id;
            this.donvi = _donvi;
        }
    }

    @Value("\${spring.httpservices.hrm.url}")
    String hrmUrl

    @Value("\${spring.httpservices.hrm.token}")
    String hrmToken

    @Value("\${spring.httpservices.hrm.url_dungthu}")
    String hrmUrl_dungthu

    @Value("\${spring.httpservices.hrm.token_dungthu}")
    String hrmToken_dungthu

    static final Map<String, String> mTVK = ["501": "Điểm UQ",
                                             "502": "Điểm UQ thuộc DNUQ",
                                             "503": "Điểm bán lẻ",
                                             "506": "CTV bán hàng",
                                             "507": "CTV thu cước",
                                             "508": "Đại lý"]

    String[] string_url_post_special = [
            'chamcong/data_in_out_hinhanh',
            'chamcong/data_in_out_loi_gps',
            'chamcong/data_in_out_to_approve',
            'chamcong/dangky_dimuon_vesom',
            'chamcong/update_dulieu_dimuon_vesom_ca_nhan',
            'survey/nopbai_v2',
            'bsc/kricanhan/updatewithjson',
            'bsc/kricanhan/updatekrigiaocanhanwithjson',
            'bsc/kricanhan/createwithjson',
            'bsc/kricanhan/updatewithjsonp3',
            'bsc/kricanhan/createwithjson_ver2',
            'kridonvi/updatewithjson',
            'bsc/kridonvi/updatekrigiaodonviwithjson',
            'attendance-data-phone-json',
            'bsc/kpicanhan/phieugiaokpi_update_json'
    ]

    String[] string_url_post_special_http = [
            'survey/nopbai_v2',
            'hrm/holidays',
            'hrm/holidays/edit',
            'hrm/profiles/to_approve',
            'hrm/profiles/rollback',
            'hrm/profiles/confirm',
            'hrm/profiles/reject',
            'hrm/holidays/to_approve_1',
            'hrm/holidays/approve',
            'hrm/holidays/approve_1',
            'hrm/holidays/reject',
            'hrm/holidays/to_approve_2',
            'hrm/holidays/approve_2',
            'hrm/holidays/draft',
            'hrm/upload_attachment',
            'bsc/kpicanhan/update_xacnhanp3',
            'bsc/kpicanhan/xacnhanp3_guixacnhan',
            //'attendance-data-phone',
            'chamcong/data_in_out_action_confirm',
            'chamcong/data_in_out_action_draft',
            'chamcong/data_in_out_action_refuse',
            'chamcong/dimuon_vesom_send_approve',
            'chamcong/dimuon_vesom_approve',
            'chamcong/dimuon_vesom_cancel',
            'chamcong/dimuon_vesom_draft',
            'chamcong/dimuon_vesom_delete',
            'hrm/getemployeesbysearchadvanced',
            'bsc/kridonvi/taokribandangky',
            'bsc/kridonvi/taokri',
            'bsc/kridonvi/taofile',
            'bsc/kridonvi/xoabankri',
            'bsc/kridonvi/updatefile',
            'bsc/kridonvi/xoabankri',
            'bsc/kridonvi/guidangky',
            'kridonvi/xacnhandangkykri',
            'kridonvi/tuchoidangkykri',
            'kridonvi/guibaocaokri',
            'kridonvi/tuchoibaocaokri',
            'kridonvi/ketthucdanhgia',
            'kridonvi/updatewithjson',
            'bsc/kridonvi/xoabandangkykri',
            'bsc/kridonvi/updatekrigiaodonviwithjson',
            'bsc/kridonvi/taocongviecphieugiaophoihopchokridonvi',
            'bsc/kridonvi/taocongviechangloatchodonvi',
            'bsc/kricanhan/xacnhandangkykri',
            'bsc/kricanhan/taofile',
            'bsc/kricanhan/xoabankri',
            'bsc/kricanhan/guidangky',
            'bsc/kricanhan/xacnhandangkykri',
            'bsc/kricanhan/tuchoidangkykri',
            'bsc/kricanhan/guibaocaokri',
            'bsc/kricanhan/tuchoibaocaokri',
            'bsc/kricanhan/ketthucdanhgia',
            'bsc/kricanhan/taohesop3',
            'bsc/kricanhan/xoafile',
            'bsc/kricanhan/updatewithjson',
            'bsc/kricanhan/xoabandangkykri',
            'bsc/kricanhan/taofilechokrigiao',
            'bsc/kricanhan/updatebankrigiaocanhan',
            'bsc/kricanhan/guibaocaogiaokricanhan',
            'bsc/kricanhan/taophieugiaochokricanhan',
            'bsc/kricanhan/taocongviecchavacon',
            'bsc/kricanhan/taocongviecphoihop',
            'bsc/kricanhan/taocongviechangloat',
            'bsc/kricanhan/taocanhannhancongviechangloat',
            'bsc/kricanhan/taofilechocongviectrongkri',
            'bsc/kricanhan/xacnhanphieugiaochokricanhan',
            'bsc/kricanhan/ketthucdanhgiacongviecchokricanhan',
            'bsc/kricanhan/taokrigiaotuongung',
            'bsc/kricanhan/updatebankrigiaodedanhgia',
            'bsc/kricanhan/updatefiledinhkemdanhgiachokrigiao',
            'bsc/kricanhan/taofiledinhkemdanhgiachokrigiao',
            'bsc/kricanhan/updatekrigiaocanhanwithjson',
            'bsc/kricanhan/createwithjson',
            'bsc/kricanhan/copybandangkykri',
            'bsc/kricanhan/taofilebaocao',
            'bsc/kricanhan/danhgialai',
            'bsc/kricanhan/updatewithjsonp3',
            'bsc/kricanhan/createwithjson_ver2',
            'bsc/kpicanhan/phieugiaokpi_update',
            'bsc/kpicanhan/kpi_update',
            'bsc/kpicanhan/xacnhan',
            'bsc/kpicanhan/xacnhan_all',
            'bsc/kpicanhan/soanthao',
            'bsc/kpicanhan/guibaocao',
            'bsc/kpicanhan/tinhdiemdanhgia',
            'bsc/kpicanhan/ketthucdanhgia',
            'bsc/kpicanhan/create_p3_donvi',
            'bsc/kpicanhan/update_xacnhanp3',
            'bsc/kpicanhan/update_p3_donvi',
            'bsc/kpicanhan/diemp3canhan_update',
            'bsc/kpicanhan/xacnhanp3_guixacnhan',
            'bsc/kpicanhan/xacnhanp3_guixacnhan_all',
            'bsc/kpicanhan/xacnhanp3_reload',
            'bsc/kpicanhan/xacnhanp3_tuchoi',
            'bsc/kpicanhan/taokpi',
            'bsc/kpicanhan/taokpi_chitiet'
    ]

    @GetMapping("/oneapp_get_ma_nv")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    ResponseEntity<BaseResponse<?>> oneapp_get_ma_nv() {
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String email = session_.get(genericApi.EMAIL)
            ProfileInfo profileInfo = getProfileInfo(email)
            if (profileInfo != null)
            {
                String data = "{\"ma_nv\": \""+ profileInfo.ma_nv +"\"" +
                        ", \"tennhanvien\" : \""+ profileInfo.tennhanvien +"\"" +
                        ", \"donvi_id\": "+ profileInfo.donvi_id +
                        ", \"donvi\" : \""+ profileInfo.donvi +"\" }"
                def ret = new JsonSlurper().parseText(data)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            }
            else
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, 'Tài khoản chưa được khai báo trên hệ thống!', ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        }
        catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/oneapp_get_ma_nv_set_cache")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    ResponseEntity<BaseResponse<?>> oneapp_get_ma_nv_set_cache() {
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String email = session_.get(genericApi.EMAIL)
            ProfileInfo profileInfo = renewToken(email)//getProfileInfo(email)
            if (profileInfo != null)
            {
                String data = "{\"token\": \""+ profileInfo.token +"\"" +
                        ", \"ma_nv\": \""+ profileInfo.ma_nv +"\"" +
                        ", \"tennhanvien\" : \""+ profileInfo.tennhanvien +"\"" +
                        ", \"donvi_id\": "+ profileInfo.donvi_id +
                        ", \"donvi\" : \""+ profileInfo.donvi +"\" }"
                cacheManager.getCache(Constant.HRM_CACHE_NAME).put(email.toUpperCase() + "/serverProfileInfo", data)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(profileInfo))
            }
            else
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, 'Tài khoản chưa được khai báo trên hệ thống!', ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        }
        catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/oneapp_get_ma_nv_get_cache")
    @Produces([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    @Consumes([MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN])
    ResponseEntity<BaseResponse<?>> oneapp_get_ma_nv_get_cache() {
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String email = session_.get(genericApi.EMAIL)
            ProfileInfo profileInfo = null
            String str_profileInfo = cacheManager.getCache(Constant.HRM_CACHE_NAME).get(email.toUpperCase() + "/serverProfileInfo", String.class)

            if (str_profileInfo != null && str_profileInfo != "")
            {
                Gson g = new Gson();
                profileInfo = g.fromJson(str_profileInfo, ProfileInfo.class);
            }

            if (profileInfo != null)
            {
                String data = "{\"token\": \""+ profileInfo.token +"\"" +
                        ",\"ma_nv\": \""+ profileInfo.ma_nv +"\"" +
                        ", \"tennhanvien\" : \""+ profileInfo.tennhanvien +"\"" +
                        ", \"donvi_id\": "+ profileInfo.donvi_id +
                        ", \"donvi\" : \""+ profileInfo.donvi +"\" }"
                def ret = new JsonSlurper().parseText(data)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            }
            else
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, 'Cache chưa được!', ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        }
        catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorGet(HttpServletRequest request, @RequestParam Map <String, String> requestParams) {
        try {
            String[] arrServletPath = request.getServletPath().split("hrm/", 2)
            def res = get(arrServletPath[1], requestParams)
            if (res == null || res == ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không có dữ liệu!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            try {
                def ret = new JsonSlurper().parseText(res)
                if (arrServletPath[1].contains("lay_anhdaidien")) {
                    if (ret["success"].toString() == "true") {
                        String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + System.currentTimeMillis() + (ret["type"].toString().endsWith("png") ? ".png" : ".jpg")
                        boolean upload = objectStoragesManaged.uploadObjectHPG(fileName, Base64.getDecoder().decode(ret["result"]))
                        if (upload) {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(objectStoragesManaged.getUrlHPG(fileName)))
                        }
                    }
                } else if (arrServletPath[1].contains("DsThanhVienKenh")) {
                    if (ret["result"].toString() == "True") {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(ret["DsThanhVienKenh"]))
                    }
                } else if (arrServletPath[1].contains("dsYeuCauKhaiKenh")) {
                    if (ret["result"].toString() == "True") {
                        for (def item : ret["dsYeuCauKhaiKenh"]) {
                            item["ten_thanhvienkenh"] = mTVK.containsKey(item["ma_thanhvienkenh"].toString()) ? mTVK[item["ma_thanhvienkenh"].toString()] : item["ma_thanhvienkenh"]
                        }
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(ret["dsYeuCauKhaiKenh"]))
                    }
                }

                if (!ret["success"] && ret["message"].toString() == "Thiếu tham số truyên vào. Header truyền vào cần có access_token.") {
                    ret["message"] = "Tài khoản người dùng chưa được tạo trên hệ thống HRM. Vui lòng liên hệ với quản trị nhân sự đơn vị để được cấp tài khoản"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, null, ret["message"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } catch (Exception ex) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res, null, "Lỗi kết nối với hệ thống HRM. Vui lòng thử lại sau!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPost(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("hrm/", 2)
            def res = post(arrServletPath[1], requestParams, requestBody)
            if (res == null || res == ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không có dữ liệu!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            try {
                def ret = new JsonSlurper().parseText(res)
                if (arrServletPath[1].startsWith("hrm/holidays/approve/") && !ret["success"]) {
                    String err = ret["message"] ? ret["message"] : "Có lỗi xảy ra khi phê duyệt"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, null, err, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } catch (Exception ex) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res, null, "Lỗi kết nối với hệ thống HRM. Vui lòng thử lại sau!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/letandientu/capnhat_anhdaidien")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> capnhat_anhdaidien(@RequestBody Map<String, String> body) {
        try {
            String tenFile = body["file"].substring(body["file"].lastIndexOf("/")+1, body["file"].length())
            String type = "image/jpeg"
            if (tenFile.endsWith("png")) {
                type = "image/png"
            }
            InputStream ins = objectStoragesManaged.getObject("onebss-develop", resize(body["file"]))
            byte[] sourceBytes = IOUtils.toByteArray(ins);
            String data = Base64.getEncoder().encodeToString(sourceBytes);
            Map<String, String> hrmBody = [:]
            hrmBody["ten_file"] = tenFile
            hrmBody["type"] = type
            hrmBody["data"] = data
            String res = postFormData("letandientu/capnhat_anhdaidien/", hrmBody)
            if (res == null || res == ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không có dữ liệu!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            def ret = new JsonSlurper().parseText(res)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/letandientu/xacnhan_dangky")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> xacnhan_dangky (@RequestBody Map<String, String> requestBody) {
        try {
            String res = postFormData("letandientu/xacnhan_dangky/", requestBody)
            if (res == null || res == ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không có dữ liệu!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            def ret = new JsonSlurper().parseText(res)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String resize(String picture) {
        try {
            String serverName = "onebss-develop"
            String newPicture = picture.substring(0, picture.lastIndexOf(".")) + "_new." + picture.substring(picture.lastIndexOf(".") + 1, picture.length())
            InputStream ins = objectStoragesManaged.getObject(serverName, picture)
            boolean kq = objectStoragesManaged.uploadObject(serverName, newPicture, Utils.reduceImageQuality(ins, 1, 1, 800), null)
            if (kq) {
                return newPicture
            } else {
                return picture
            }
        } catch (Exception ex) {
            return picture
        }
    }

    String postFormData(String path, Map<String, String> input) {
        String result = ""
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)

        String _hrmUrl = hrmUrl
        String _email = email.toLowerCase()
        if (_email == "huongle@vnpt.vn") {
            _hrmUrl = hrmUrl_dungthu
        }

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(_hrmUrl + path);

            List<NameValuePair> formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair("access_token", userToken));
            for (Map.Entry<String, String> entry : input.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));
            HttpResponse response = httpClient.execute(httpPost);

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            DecompressingEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result
    }

    @PutMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorPut(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("hrm/", 2)
            def res = put(arrServletPath[1], requestParams, requestBody)
            if (res == null || res == ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không có dữ liệu!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            try {
                def ret = new JsonSlurper().parseText(res)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } catch (Exception ex) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res, null, "Lỗi kết nối với hệ thống HRM. Vui lòng thử lại sau!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @DeleteMapping("/**")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse<?>> executorDelete(HttpServletRequest request, @RequestParam Map <String, String> requestParams, @RequestBody(required = false) Object requestBody) {
        try {
            String[] arrServletPath = request.getServletPath().split("hrm/", 2)
            def res = delete(arrServletPath[1], requestParams)
            if (res == null || res == ""){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không có dữ liệu!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            try {
                def ret = new JsonSlurper().parseText(res)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } catch (Exception ex) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res, null, "Lỗi kết nối với hệ thống HRM. Vui lòng thử lại sau!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @GetMapping("/thongTinNguoiDung")
    ResponseEntity<BaseResponse<?>> thongTinNguoiDung(@RequestParam String ma_nv) {
        try {
            String url = hrmUrl + "thongTinNguoiDung?tokenCode=" + hrmToken + "&ma_nv=" + ma_nv
            Map<String, String> headers = [:]
            String resp = httpManaged.get(url, headers)
            def res = new JsonSlurper().parseText(resp)
            if (res["result"] == "True") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["ctThongTinNhanSu"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["messageReturn"], ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/thongTinNguoiDung_hrm")
    ResponseEntity<BaseResponse<?>> thongTinNguoiDung_hrm() {
        try {
            String url = hrmUrl + "thongTinNguoiDung?tokenCode=" + hrmToken + "&ma_nv=" + genericApi.getSessionData(request).get(GenericApi.MA_NV)
            Map<String, String> headers = [:]
            String resp = httpManaged.get(url, headers)
            def res = new JsonSlurper().parseText(resp)
            if (res["result"] == "True") {
                try {
                    if (res["ctThongTinNhanSu"] instanceof List) {
                        if (((List) res["ctThongTinNhanSu"]).size() > 0) {
                            if (res["ctThongTinNhanSu"][0]["trangthai"] == "confirmed") {
                                hopDongService.init(request.getHeader("Authorization"))
                                hopDongService.fn_capnhat_tt_dongbo_nhanvien()
                            }
                        }
                    }
                } catch (Exception ee) {
                }

                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["ctThongTinNhanSu"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["messageReturn"], BaseResponseBuilder.CODE_OK))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ProfileInfo renewToken(String email){
        Map<String, String> headers = [:]
        Map<String, String> params = [:]

        String _hrmToken = hrmToken
        String _hrmUrl = hrmUrl
        String _email = email.toLowerCase()
        if (_email == "huongle@vnpt.vn")
        {
            _hrmToken = hrmToken_dungthu
            _hrmUrl = hrmUrl_dungthu
        }

        params.put("tokenCode", _hrmToken)
        params.put("email", _email)
        def ls = params.collect {key, value -> key + "=" + value}.join("&")

        String url = _hrmUrl + "hrm/get_token_by_email?$ls"
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.post([url: url], headers, "")
        def ret = new JsonSlurper().parseText(resp)
        if (!ret?.success) {
            return null
        }
        String token = ret.access_token
        String ma_nv = ret.user.manhanvien
        String tennhanvien = ret.user.tennhanvien
        Integer donvi_id = ret.user.iddonvi instanceof Integer ? ret.user.iddonvi : -999
        String donvi = ret.user.donvi

        ProfileInfo profileInfo = new ProfileInfo(token, ma_nv, tennhanvien, donvi_id, donvi)
        // Set Info To Cache
        String data = "{\"token\": \""+ profileInfo.token +"\"" +
                        ", \"ma_nv\": \""+ profileInfo.ma_nv +"\"" +
                        ", \"tennhanvien\" : \""+ profileInfo.tennhanvien +"\"" +
                        ", \"donvi_id\": "+ profileInfo.donvi_id +
                        ", \"donvi\" : \""+ profileInfo.donvi +"\" }"
//        cacheManager.getCache(Constant.HRM_CACHE_NAME).put(email.toUpperCase() + "/serverProfileInfo", data)
        // End Set Info To Cache
        return profileInfo
    }

    String getToken(String email){
        String token = null
//        try {
//            ProfileInfo profileCache = profileInfoFromCache(email)
//            if (profileCache != null)
//            {
//                token = profileCache.token
//            }
//        } catch (Exception ex) {}
        if (token == null){
            ProfileInfo profileInfo = renewToken(email)
            if (profileInfo != null) {
                return profileInfo.token
            } else {
                return  ""
            }
        }
        return token
    }

    String getMaNhanVien(String email){
        String ma_nv = null
        try {
            ProfileInfo profileCache = profileInfoFromCache(email)
            if (profileCache != null)
            {
                ma_nv = profileCache.ma_nv
            }
        } catch (Exception ex) {}
        if (ma_nv == null){
            ProfileInfo profileInfo = renewToken(email)
            if (profileInfo != null) {
                return profileInfo.ma_nv
            } else {
                return  ""
            }
        }
        return ma_nv
    }

    ProfileInfo profileInfoFromCache(String email)
    {
        ProfileInfo profileInfo = null
        String str_profileInfo = cacheManager.getCache(Constant.HRM_CACHE_NAME).get(email.toUpperCase() + "/serverProfileInfo", String.class)
        if (str_profileInfo != null && str_profileInfo != "")
        {
            Gson g = new Gson();
            profileInfo = g.fromJson(str_profileInfo, ProfileInfo.class);
        }
        return profileInfo;
    }

    ProfileInfo getProfileInfo(String email){
        ProfileInfo token = null
        if (token == null || token.token == null){
            ProfileInfo profileInfo = renewToken(email)
            return profileInfo
        }
        return token
    }

    String get(String path, Map<String, String> param) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)

//        if (userToken == null || userToken == "") {
//            return "-999"
//        }

        String _hrmToken = hrmToken
        String _hrmUrl = hrmUrl
        String _email = email.toLowerCase()
        if (_email == "huongle@vnpt.vn")
        {
            _hrmToken = hrmToken_dungthu
            _hrmUrl = hrmUrl_dungthu
        }

        if (path.contains("letandientu/get_qrcode_employee")) {
            param.put("manhanvien", getMaNhanVien(email))
        }

        param.put("tokenCode", _hrmToken)
        param.put("access_token", userToken)
        def ls = param.collect {key, value -> key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString())}.join("&")
        Map<String, String> headers = [:]
        String url = _hrmUrl + path + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        headers.put("Content-Encoding", "UTF-8")
        String resp = httpManaged.get(url, headers)
        return resp
    }

    String post(String path, Map<String, String> param, Object body) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)

        String _hrmToken = hrmToken
        String _hrmUrl = hrmUrl
        String _email = email.toLowerCase()
        if (_email == "huongle@vnpt.vn")
        {
            _hrmToken = hrmToken_dungthu
            _hrmUrl = hrmUrl_dungthu
        }
        // Xử lý url đặc biệt
        for (String _strURL : string_url_post_special)
        {
            if (path.startsWith(_strURL))
            {
                body['access_token'] = userToken
            }
        }

        param.put("tokenCode", _hrmToken)
        param.put("access_token", userToken)
        if (!param.containsKey("access_token")){
            param.put("access_token", _hrmToken)
        }
        def ls = param.collect {key, value -> key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString())}.join("&")
        Map<String, String> headers = [:]
        String url = _hrmUrl + path + "?" + ls

        for (String _strURL : string_url_post_special_http)
        {
            if (path.startsWith(_strURL))
            {
                headers.put("Content-Type", "application/xml;charset=UTF-8")
            }
        }

        if (path.contains("khaiBaoKenhTuEmployee") || path.contains("chinhSuaChiTietHoSoKenh")) {
            headers.put("Content-Type", "text/plain;charset=UTF-8")
            body["tokenCode"] = hrmToken
        }

        if (!headers.containsKey("Content-Type")){
            headers.put("Content-Type", "application/json;charset=UTF-8")
        }

        headers.put("Content-Encoding", "UTF-8")
        
//        if (Arrays.asList(string_url_post_special_http).contains(path))
//        {
//            headers.put("Content-Type", "application/xml;charset=UTF-8")
//        }
//        else
//        {
//            headers.put("Content-Type", "application/json;charset=UTF-8")
//        }

        String resp = httpManaged.post([url: url], headers, json.writeValueAsString(body))
        return resp
    }

    String put(String path, Map<String, String> param, Object body) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)

        String _hrmToken = hrmToken
        String _hrmUrl = hrmUrl
        String _email = email.toLowerCase()
        if (_email == "huongle@vnpt.vn")
        {
            _hrmToken = hrmToken_dungthu
            _hrmUrl = hrmUrl_dungthu
        }

        param.put("tokenCode", _hrmToken)
        param.put("access_token", userToken)
        if (!param.containsKey("access_token")){
            param.put("access_token", _hrmToken)
        }

        def ls = param.collect {key, value -> key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString())}.join("&")
        Map<String, String> headers = [:]
        String url = _hrmUrl + path + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.put([url: url], headers, json.writeValueAsString(body))
        return resp
    }

    String delete(String path, Map<String, String> param) {
        Map<String, Object> session_ = genericApi.getSessionData(request)
        String email = session_.get(genericApi.EMAIL)
        String userToken = getToken(email)

        String _hrmToken = hrmToken
        String _hrmUrl = hrmUrl
        String _email = email.toLowerCase()
        if (_email == "huongle@vnpt.vn")
        {
            _hrmToken = hrmToken_dungthu
            _hrmUrl = hrmUrl_dungthu
        }

        param.put("tokenCode", _hrmToken)
        param.put("access_token", userToken)
        if (!param.containsKey("access_token")){
            param.put("access_token", _hrmToken)
        }
        def ls = param.collect {key, value -> key + "=" + URLEncoder.encode(value, StandardCharsets.UTF_8.toString())}.join("&")
        Map<String, String> headers = [:]
        String url = _hrmUrl + path + "?" + ls
        headers.put("Content-Type", "text/xml;charset=UTF-8")
        String resp = httpManaged.delete(url, headers)
        return resp
    }

//    @GetMapping("/renewToken")
//    @Consumes([MediaType.APPLICATION_JSON])
//    @Produces([MediaType.APPLICATION_JSON])
//    ResponseEntity<BaseResponse<?>> renewToken(HttpServletRequest request, @RequestParam Map<String, String> requestParams) {
//        try {
//            ProfileInfo profileInfo = renewToken(requestParams.email)
//
//            String _hrmUrl = hrmUrl
//            String _email = requestParams.email.toLowerCase()
//            if (_email == "huongle@vnpt.vn")
//            {
//                _hrmUrl = hrmUrl_dungthu
//            }
//
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(_hrmUrl + ": " + profileInfo.token))
//        } catch (Exception e) {
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//        }
//    }
}
