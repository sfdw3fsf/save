package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonSlurper
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.multipart.MultipartFile
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.*
import vn.vnptit.sapi.oneapp.util.LoggingInOut

import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("khdn_media")
class KHDNMediaService {

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.khdn_media.url}")
    String host

    @Value("\${spring.httpservices.khdn_media.token}")
    String token

    Gson gson = new GsonBuilder().serializeNulls().create()

    @Autowired
    NativeWebRequest request

    @LoggingInOut
    @PostMapping("/TimKiem_KHDN")
    CommonResponse TimKiem(@RequestBody TimKiem_KHDN_REQ body) {
        try {
            if (request.getHeader("Authorization") != null && request.getHeader("Authorization") != "") {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Bạn không có quyền truy cập chức năng này.", data: null)
            }

            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/v4/customer_enterprise/find"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/TimKiem_KHTN")
    CommonResponse TimKiem(@RequestBody TimKiem_KHTN_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/v3/customer_enterprise_individual/find"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/TaoMoi_KHDN_ThuCong")
    CommonResponse TaoMoi_KHDN_ThuCong(@RequestBody KHDN_ThuCong_New_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

//            String url = host + "/khtn/new_customer_enterprise/add"
            String url = host + "/khtn/v4/customer_enterprise_handmade/add"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res
            try {
                res = new JsonSlurper().parseText(resp)
            } catch (Exception ex) {
                try {
                    res = new JsonSlurper().parseText(resp.substring(1, resp.length()))
                } catch (Exception xx) {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: "Có lỗi xảy ra khi tạo mới KHDN", data: resp)
                }
            }

            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/CapNhat_KHDN_ThuCong")
    CommonResponse CapNhat_KHDN_ThuCong(@RequestBody KHDN_ThuCong_New_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

//            String url = host + "/khtn/new_customer_enterprise/update"
            String url = host + "/khtn/v4/customer_enterprise_handmade/update"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/TaoMoi_KHDN_NguonKhac")
    CommonResponse TaoMoi_KHDN_NguonKhac(@RequestBody KHDN_NguonKhac_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/customer_enterprise/add"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/CapNhat_KHDN_NguonKhac")
    CommonResponse CapNhat_KHDN_NguonKhac(@RequestBody KHDN_NguonKhac_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/customer_enterprise/update"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/Xoa_KhachHang_ThuCong")
    CommonResponse Xoa_KhachHang_ThuCong(@RequestParam String tax_id) {
        try {
            String url = host + "/khtn/v4/customer_enterprise_handmade/delete/" + tax_id
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_ChiTiet_KhachHang")
    CommonResponse Xem_ChiTiet_KhachHang(@RequestBody Xem_ChiTiet_KhachHang_REQ body) {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(host + "/khtn/v4/customer_enterprise").newBuilder()
            urlBuilder.addQueryParameter("ma_so_thue", body.ma_so_thue != null ? body.ma_so_thue : "")
            urlBuilder.addQueryParameter("ma_tinh", body.ma_tinh != null ? body.ma_tinh : "")
            if (body.khachhang_id != null && body.khachhang_id != "") {
                urlBuilder.addQueryParameter("khachhang_id", body.khachhang_id)
            }
            String url = urlBuilder.build().toString()
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DoanhThu_DichVu_TheoThang")
    CommonResponse Lay_DoanhThu_DichVu_TheoThang(@RequestParam String ma_so_thue, @RequestParam String ma_tinh, @RequestParam String month_start, @RequestParam String month_end) {
        try {
            String url = host + "/khtn/v4/total_revenue_each_service_per_month?ma_so_thue=" + ma_so_thue + "&ma_tinh=" + ma_tinh + "&month_start=" + month_start + "&month_end=" + month_end
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_ChiTiet_DichVu_Vnpt")
    CommonResponse Lay_ChiTiet_DichVu_Vnpt(@RequestParam String ma_so_thue, @RequestParam String ma_tinh) {
        try {
            String url = host + "/khtn/v4/dich_vu?ma_so_thue=" + ma_so_thue + "&ma_tinh=" + ma_tinh
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ChiTiet_PhanAnh_KhieuNai")
    CommonResponse Lay_DanhSach_ChiTiet_PhanAnh_KhieuNai(@RequestParam String ma_so_thue, @RequestParam String ma_tinh, @RequestParam String ngay_bat_dau, @RequestParam String ngay_ket_thuc) {
        try {
            String url = host + "/khtn/v4/khieu_nai?ma_so_thue=" + ma_so_thue + "&ma_tinh=" + ma_tinh + "&ngay_bat_dau=" + ngay_bat_dau + "&ngay_ket_thuc=" + ngay_ket_thuc
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ChiTiet_BaoHong")
    CommonResponse Lay_DanhSach_ChiTiet_BaoHong(@RequestParam String ma_so_thue, @RequestParam String ma_tinh, @RequestParam String ngay_bat_dau, @RequestParam String ngay_ket_thuc) {
        try {
            String url = host + "/khtn/v4/bao_hong?ma_so_thue=" + ma_so_thue + "&ma_tinh=" + ma_tinh + "&ngay_bat_dau=" + ngay_bat_dau + "&ngay_ket_thuc=" + ngay_ket_thuc
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ThongTin_Vnpt")
    CommonResponse Lay_DanhSach_ThongTin_Vnpt(@RequestParam String ma_so_thue, @RequestParam String ma_tinh) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/info_vnpt?ma_so_thue=" + ma_so_thue + "&ma_tinh=" + ma_tinh
            return execute(url, "POST", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ThongTin_Vnpt_v2")
    CommonResponse Lay_DanhSach_ThongTin_Vnpt_v2(@RequestParam String ma_so_thue, @RequestParam String ma_tinh) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/info_vnpt?ma_so_thue=" + ma_so_thue + "&ma_tinh=" + ma_tinh
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)
            String resp = httpManaged.okpost(url, headers, "", 180)
            return xlResp(resp)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    CommonResponse execute(String url, String method, Object body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)
            String resp = ""
            if (method == "GET") {
                resp = httpManaged.get(url, headers)
            } else {
                resp = httpManaged.post([url: url], headers, body != null ? new Gson().toJson(body) : "")
            }
            return xlResp(resp)
        } catch (Exception ex) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null)
        }
    }

    CommonResponse xlResp(String resp) {
        try {
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception ex) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: resp, data: null)
        }
    }

    @LoggingInOut
    @PostMapping("/TaoMoi_KhachHang_TiemNang")
    CommonResponse TaoMoi_KhachHang_TiemNang(@RequestBody KhachHang_TiemNang_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/v4/customer_enterprise_individual_handmade/add"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/CapNhat_KhachHang")
    CommonResponse CapNhat_KhachHang(@RequestBody CapNhat_KhachHang_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/v4/customer_enterprise_individual_handmade/update"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @LoggingInOut
    @PostMapping("/Xoa_KhachHang")
    CommonResponse Xoa_KhachHang(@RequestBody Xoa_KhachHang_REQ body) {
        try {
            Map<String, String> headers = new HashMap<>()
            headers.put("Content-Type", "application/json")
            headers.put("Authorization", token)

            String url = host + "/khtn/v4/customer_enterprise_individual_handmade/delete"
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_ChiTiet_KhachHang_TiemNang")
    CommonResponse Xem_ChiTiet_KhachHang_TiemNang(@RequestBody Xem_ChiTiet_KhachHang_TiemNang_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise_individual_handmade/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    //api gán AM
    @LoggingInOut
    @PostMapping("/Gan_AM_KhachHangs")
    public CommonResponse Gan_AM_KhachHangs(@RequestBody List<Import_Gan_AM_KH_REQ> body) {
        try {
            // Chuyển đổi dataRows thành JSON

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", token);

            String url = host + "/khtn/v4/customer_enterprise/update_am";
            String resp = httpManaged.post(["url": url], headers, gson.toJson(body))
//
            def res = new JsonSlurper().parseText(resp);
            if (res["code"].toString() == "0") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
            } else {
                return new CommonResponse(errorCode: 1, faultCode: res["code"], faultString: res["message"], data: res["data"])
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged);
        }
    }

    @PostMapping("/DanhSach_KhachHang_TiepCan_B2A")
    CommonResponse DanhSach_KhachHang_TiepCan_B2A(@RequestBody TimKiem_B2A_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/b2a"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/TongHop_DuLieu_KhachHang") // Thong ke theo AM
    CommonResponse TongHop_DuLieu_KhachHang(@RequestBody TongHop_DuLieu_KhachHang_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/statistic_am_revenue?am=${body.am}&ma_tinh=${body.ma_tinh}&ngay_bat_dau=${body.ngay_bat_dau}&ngay_ket_thuc=${body.ngay_ket_thuc}"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/BaoCao_Theo_PhongKHDN")
    CommonResponse BaoCao_Theo_PhongKHDN(@RequestBody BaoCao_TheoPhongKHDN_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/statistic_phongkhdn_revenue?donvi_id=${body.donvi_id}&ma_tinh=${body.ma_tinh}&ngay_bat_dau=${body.ngay_bat_dau}&ngay_ket_thuc=${body.ngay_ket_thuc}"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/BaoCao_Theo_TongCongTy")
    CommonResponse BaoCao_Theo_TongCongTy(@RequestBody BaoCao_Theo_TongCongTy_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/statistic_tct_revenue?ngay_bat_dau=${body.ngay_bat_dau}&ngay_ket_thuc=${body.ngay_ket_thuc}"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/BaoCao_Theo_GiamDocTTKD")
    CommonResponse BaoCao_Theo_GiamDocTTKD(@RequestBody BaoCao_Theo_GiamDocTTKD_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/statistic_gdttkd_revenue?ma_tinh=${body.ma_tinh}&ngay_bat_dau=${body.ngay_bat_dau}&ngay_ket_thuc=${body.ngay_ket_thuc}"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/export")
    byte[] export(@RequestBody Export_KHDN_REQ body) {
        return postExport(host + "/khtn/v4/customer_enterprise/export", body)
    }

    @PostMapping("/export_b2a")
    byte[] export_b2a(@RequestBody Export_B2A_REQ body) {
        return postExport(host + "/khtn/v4/customer_enterprise/export_b2a", body)
    }

    byte[] postExport(String url, def input) {
        byte[] result = null
        try {
            Headers headers = new Headers.Builder()
                    .add("Authorization", token)
                    .add("Content-Type", "application/json")
                    .build();

            okhttp3.RequestBody body = okhttp3.RequestBody.create(
                    new ObjectMapper().writeValueAsString(input),
                    MediaType.parse("application/json")
            )

            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(body)
                    .build()

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(180, TimeUnit.SECONDS)
                    .readTimeout(180, TimeUnit.SECONDS)
                    .writeTimeout(180, TimeUnit.SECONDS)
                    .build();

            Response response = null
            try {
                response = client.newCall(request).execute()
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response)
                }
                result = response.body().bytes()
            } catch (Exception e) {
                e.printStackTrace()
            } finally {
                if (response != null) {
                    response.close()
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return result
    }

    @PostMapping("/uploadFileImport")
    CommonResponse uploadFileImport(@RequestParam("file") MultipartFile file, @RequestParam("createBy") String createBy, @RequestParam("confirmInsert") Integer confirmInsert, @RequestParam("maTinh") String maTinh) {
        try {
            String url = host + "/khtn/v4/upload_file_import"

            Map<String, String> headers = new HashMap<>()
            headers.put("Authorization", token)

            Map<String, Object> formData = new HashMap<>()
            formData.put("createBy", createBy)
            formData.put("confirmInsert", confirmInsert)
            formData.put("maTinh", maTinh)
            String resp = httpManaged.uploadFile(url, headers, file, formData)
            try {
                def res = new ObjectMapper().readValue(resp, Map.class)
                if (res["code"].toString() == "0") {
                    return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res["data"])
                } else {
                    return new CommonResponse(errorCode: 1, faultCode: "", faultString: res["message"], data: res)
                }
            } catch (Exception ex) {
                return new CommonResponse(errorCode: 1, faultCode: "", faultString: resp, data: null)
            }
        } catch (Exception ex) {
            return new CommonResponse(errorCode: 1, faultCode: "", faultString: ex.message, data: null)
        }
    }

    @PostMapping("/Them_ThongTin_CN")
    @LoggingInOut
    CommonResponse Them_ThongTin_CN(@RequestBody Them_ThongTin_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_enterprise/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_ThongTin_CN")
    @LoggingInOut
    CommonResponse CapNhat_ThongTin_CN(@RequestBody CapNhat_ThongTin_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_enterprise/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xoa_ThongTin_CN")
    @LoggingInOut
    CommonResponse Xoa_ThongTin_CN(@RequestBody Xoa_ThongTin_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_enterprise/delete"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_ThongTin_CN")
    CommonResponse Xem_ThongTin_CN(@RequestBody Xem_ThongTin_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_enterprise/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ThongTin_CN")
    CommonResponse Lay_DanhSach_ThongTin_CN(@RequestBody Lay_DanhSach_ThongTin_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_enterprise/list"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Them_HoSo_CN")
    @LoggingInOut
    CommonResponse Them_HoSo_CN(@RequestBody Them_HoSo_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_profile/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_HoSo_CN")
    @LoggingInOut
    CommonResponse CapNhat_HoSo_CN(@RequestBody CapNhat_HoSo_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_profile/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xoa_HoSo_CN")
    @LoggingInOut
    CommonResponse Xoa_HoSo_CN(@RequestBody Xoa_HoSo_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_profile/delete"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_HoSo_CN")
    CommonResponse Xem_HoSo_CN(@RequestBody Xem_HoSo_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_profile/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_HoSo_CN")
    CommonResponse Lay_DanhSach_HoSo_CN(@RequestBody Lay_DanhSach_HoSo_CN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/personal_profile/list"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Them_HocVan")
    @LoggingInOut
    CommonResponse Them_HocVan(@RequestBody Them_HocVan_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/education/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_HocVan")
    @LoggingInOut
    CommonResponse CapNhat_HocVan(@RequestBody CapNhat_HocVan_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/education/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xoa_HocVan")
    @LoggingInOut
    CommonResponse Xoa_HocVan(@RequestBody Xoa_HocVan_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/education/delete"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_HocVan")
    CommonResponse Xem_HocVan(@RequestBody Xem_HocVan_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/education/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_HocVan")
    CommonResponse Lay_DanhSach_HocVan(@RequestBody Lay_DanhSach_HocVan_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/education/list"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Them_QuaTrinh_CongTac")
    @LoggingInOut
    CommonResponse Them_QuaTrinh_CongTac(@RequestBody Them_QuaTrinh_CongTac_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/work_process/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_QuaTrinh_CongTac")
    @LoggingInOut
    CommonResponse CapNhat_QuaTrinh_CongTac(@RequestBody CapNhat_QuaTrinh_CongTac_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/work_process/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xoa_QuaTrinh_CongTac")
    @LoggingInOut
    CommonResponse Xoa_QuaTrinh_CongTac(@RequestBody Xoa_QuaTrinh_CongTac_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/work_process/delete"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_QuaTrinh_CongTac")
    CommonResponse Xem_QuaTrinh_CongTac(@RequestBody Xem_QuaTrinh_CongTac_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/work_process/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_QuaTrinh_CongTac")
    CommonResponse Lay_DanhSach_QuaTrinh_CongTac(@RequestBody Lay_DanhSach_QuaTrinh_CongTac_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/work_process/list"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }   
    }

    @PostMapping("/Them_ThongTin_BienDong_DN")
    @LoggingInOut
    CommonResponse Them_ThongTin_BienDong_DN(@RequestBody Them_ThongTin_BienDong_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_fluctuation/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_ThongTin_BienDong_DN")
    @LoggingInOut
    CommonResponse CapNhat_ThongTin_BienDong_DN(@RequestBody CapNhat_ThongTin_BienDong_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_fluctuation/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xoa_ThongTin_BienDong_DN")
    @LoggingInOut
    CommonResponse Xoa_ThongTin_BienDong_DN(@RequestBody Xoa_ThongTin_BienDong_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_fluctuation/delete"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_ThongTin_BienDong_DN")
    CommonResponse Xem_ThongTin_BienDong_DN(@RequestBody Xem_ThongTin_BienDong_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_fluctuation/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ThongTin_BienDong_DN")
    CommonResponse Lay_DanhSach_ThongTin_BienDong_DN(@RequestBody Lay_DanhSach_ThongTin_BienDong_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_fluctuation/list"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Them_ThongTin_QuanHe_DN")
    @LoggingInOut
    CommonResponse Them_ThongTin_QuanHe_DN(@RequestBody Them_ThongTin_QuanHe_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_relationship/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_ThongTin_QuanHe_DN")
    @LoggingInOut
    CommonResponse CapNhat_ThongTin_QuanHe_DN(@RequestBody CapNhat_ThongTin_QuanHe_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_relationship/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xoa_ThongTin_QuanHe_DN")
    @LoggingInOut
    CommonResponse Xoa_ThongTin_QuanHe_DN(@RequestBody Xoa_ThongTin_QuanHe_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_relationship/delete"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Xem_ThongTin_QuanHe_DN")
    CommonResponse Xem_ThongTin_QuanHe_DN(@RequestBody Xem_ThongTin_QuanHe_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_relationship/detail"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Lay_DanhSach_ThongTin_QuanHe_DN")
    CommonResponse Lay_DanhSach_ThongTin_QuanHe_DN(@RequestBody Lay_DanhSach_ThongTin_QuanHe_DN_REQ body) {
        try {
            String url = host + "/khtn/v1/cndn/business_relationship/list"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/get_b2a_khdn")
    CommonResponse get_b2a_khdn(@RequestBody get_b2a_khdn_REQ body) {
        try {
            String url = host + "/khtn/v4/get_b2a_khdn"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Them_ChuongTrinh_BanHang")
    @LoggingInOut
    CommonResponse Them_ChuongTrinh_BanHang(@RequestBody Them_ChuongTrinh_BanHang_REQ body) {
        try {
            String url = host + "/khtn/v4/sale_program/add"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_ChuongTrinh_BanHang")
    @LoggingInOut
    CommonResponse CapNhat_ChuongTrinh_BanHang(@RequestBody CapNhat_ChuongTrinh_BanHang_REQ body) {
        try {
            String url = host + "/khtn/v4/sale_program/update"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Gan_ChuongTrinh_BanHang")
    @LoggingInOut
    CommonResponse Gan_ChuongTrinh_BanHang(@RequestBody Gan_ChuongTrinh_BanHang_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/assign_ctbh"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/Import_KhachHang_ChuongTrinh_BanHang")
    @LoggingInOut
    CommonResponse Import_KhachHang_ChuongTrinh_BanHang(@RequestBody Import_KhachHang_ChuongTrinh_BanHang_REQ body) {
        try {
            String url = host + "/khtn/v4/import_kh_ctbh"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhat_MaSoThue_KhachHang")
    @LoggingInOut
    CommonResponse CapNhat_MaSoThue_KhachHang(@RequestBody CapNhat_MaSoThue_KhachHang_REQ body) {
        try {
            String url = host + "/khtn/v4/customer_enterprise/update_mst_ctbh"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
} 