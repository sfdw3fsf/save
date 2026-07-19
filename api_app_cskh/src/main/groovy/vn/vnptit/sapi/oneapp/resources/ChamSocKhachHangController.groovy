package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.ChamSocKhachHangApi
import io.swagger.model.dto.*
import io.swagger.model.request.*
import io.swagger.model.response.BasePageResponse
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.PageInfo
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.PrecheckXlscKpiRequest
import vn.vnptit.sapi.oneapp.model.request.SP_GIAOVIEC_NHANVIEN_XHH_REQUEST
import vn.vnptit.sapi.oneapp.service.SmsSendService
import vn.vnptit.sapi.oneapp.services.LogTelegramService
import vn.vnptit.sapi.oneapp.util.*

import javax.servlet.http.HttpServletRequest
import java.sql.Clob
import java.sql.Date
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class ChamSocKhachHangController implements ChamSocKhachHangApi {

    private final HttpServletRequest request
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private final ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    GenericApi genericApi
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    ObjectStoragesManaged objectStoragesManaged
    @Autowired
    ResultSetToObject resultSetToObject
    @Autowired
    DataUtils dataUtils
    @Value("\${spring.object-storages.minio-storage.bucket}")
    String bucketName
    @Value("\${internal.tichhop.url}")
    String tichhopUrl
    @Value("\${internal.app-com.url}")
    String appComUrl
    @Value("\${external.tts-url}")
    String ttsUrl
    @Autowired
    HttpManaged httpManaged

    @Autowired
    SmsSendService smsSendService;
    @Autowired
    LogTelegramService logTelegramService;
    @Autowired
    ChamSocKhachHangController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> danhMucDonViGet() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhMucController] - [danhMucDonViGet] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [danhMucDonViGet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaPhieuSuCo(String maSc) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [xoaPhieuSuCo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("maSc", maSc)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_XOAPHIEU_SUCO_TMP_DEL(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maSc, Long.parseLong(phanVungId)]) {

                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [xoaPhieuSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [xoaPhieuSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, Utils.convertMessageError(e.message), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    String GET_SYSDATE(String dataSrc, String phanVungId) {
        String response = ""
        String sqlString = "{ call CSS.SMP_GET_SYSDATE(?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                if (rs != null && rs.next()) {
                    response = rs.getString("NGAY_HT")
                }
            }
        }
        return response
    }

    @Override
    ResponseEntity<BaseResponse<Void>> dangKySuCo(DangKySuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [dangKySuCo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            // Kiểm tra input ngày tháng
            if (requestBody.ngaySc == null || requestBody.ngaySc.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày xảy ra sự cố", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            if (requestBody.ngayBh == null || requestBody.ngayBh.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày báo hỏng", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
//
//            if(requestBody.henSuaDen == null || requestBody.henSuaDen.isEmpty()) {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày hẹn kết thúc", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//            }

//            if(requestBody.ngayCn == null || requestBody.ngayCn.isEmpty()) {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày cập nhật", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//            }

            Date ngaySc
            try {
                java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngaySc)
                ngaySc = new Date(date.getTime())
            } catch (Exception e) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày sự cố không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            Date henSuaTu
            if (requestBody.henSuaTu != null && !requestBody.henSuaTu.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.henSuaTu)
                    henSuaTu = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày hẹn bắt đầu không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }


            Date henSuaDen
            if (requestBody.henSuaDen != null && !requestBody.henSuaDen.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.henSuaDen)
                    henSuaDen = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày hẹn kết thúc không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }


            Date ngayBh
            try {
                java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayBh)
                ngayBh = new Date(date.getTime())
            } catch (Exception e) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày báo hỏng không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            Date ngayTn
            if (requestBody.ngayTn != null && !requestBody.ngayTn.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayTn)
                    ngayTn = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày tiếp nhận không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            if (henSuaTu != null && henSuaDen != null && henSuaTu.getTime() > henSuaDen.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày bắt đầu hẹn phải nhỏ hơn ngày kết thúc", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            // trường hợp truyền thời gian hiện tại vẫn cho phép đăng ký sự cố, nên fix tạm cộng thêm 60 giây
            if (henSuaTu != null && now != null && (henSuaTu.getTime() + 60000) < now.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày bắt đầu hẹn phải sau ngày hiện tại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            if (ngaySc != null && now != null && ngaySc.getTime() > now.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày sự cố phải nhỏ hơn ngày hiện tại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
//            requestBody.donViTn = Long.parseLong(donViId)

            // Lấy mã sự cố từ DB
            String maSc
            String sqlString = "{ ? = call QLSC.pkg_danhmuc.lay_ma_sc(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.nguonScId,
                                     ngayBh]) { String rs ->
                    maSc = rs
                }
            }

            if (maSc != null && !maSc.isEmpty()) {
                sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_TAO_SUCO_TAM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Long.parseLong(maSc), requestBody.nguonScId, requestBody.nhomScId,
                                         ngaySc, requestBody.noiDungSc, ngayBh,
                                         requestBody.donViBh, requestBody.nhanVienBh, requestBody.dienThoaiLh,
                                         ngayTn, Long.parseLong(donViId), Long.parseLong(nhanVienId),
                                         henSuaTu, henSuaDen, new Date(now.getTime()),
                                         nguoiCn, mayCn, ipCn,
                                         Long.parseLong(phanVungId)]) {

                    }
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không lấy được mã sự cố", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            loggingManaged.info("[ChamSocKhachHangController] - [dangKySuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [dangKySuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, Utils.convertMessageError(e.message), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<Void>> dangKySuCoV2(Map<?, ?> requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [dangKySuCoV2] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            // Kiểm tra input ngày tháng
            if (requestBody.ngaySc == null || requestBody.ngaySc.toString().isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày xảy ra sự cố", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            if (requestBody.ngayBh == null || requestBody.ngayBh.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày báo hỏng", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
//
//            if(requestBody.henSuaDen == null || requestBody.henSuaDen.isEmpty()) {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày hẹn kết thúc", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//            }

//            if(requestBody.ngayCn == null || requestBody.ngayCn.isEmpty()) {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập ngày cập nhật", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//            }

            Date ngaySc
            try {
                java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngaySc)
                ngaySc = new Date(date.getTime())
            } catch (Exception e) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày sự cố không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            Date henSuaTu
            if (requestBody.henSuaTu != null && !requestBody.henSuaTu.toString().isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.henSuaTu)
                    henSuaTu = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày hẹn bắt đầu không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }


            Date henSuaDen
            if (requestBody.henSuaDen != null && !requestBody.henSuaDen.toString().isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.henSuaDen)
                    henSuaDen = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày hẹn kết thúc không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }


            Date ngayBh
            try {
                java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayBh)
                ngayBh = new Date(date.getTime())
            } catch (Exception e) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày báo hỏng không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            Date ngayTn
            if (requestBody.ngayTn != null && !requestBody.ngayTn.toString().isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayTn)
                    ngayTn = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày tiếp nhận không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            if (henSuaTu != null && henSuaDen != null && henSuaTu.getTime() > henSuaDen.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày bắt đầu hẹn phải nhỏ hơn ngày kết thúc", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            // trường hợp truyền thời gian hiện tại vẫn cho phép đăng ký sự cố, nên fix tạm cộng thêm 60 giây
            if (henSuaTu != null && now != null && (henSuaTu.getTime() + 60000) < now.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày bắt đầu hẹn phải sau ngày hiện tại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            if (ngaySc != null && now != null && ngaySc.getTime() > now.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày sự cố phải nhỏ hơn ngày hiện tại", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
//            requestBody.donViTn = Long.parseLong(donViId)

            // Lấy mã sự cố từ DB
            String maSc
            String sqlString = "{ ? = call QLSC.pkg_danhmuc.lay_ma_sc(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.nguonScId,
                                     ngayBh]) { String rs ->
                    maSc = rs
                }
            }

            if (maSc != null && !maSc.isEmpty()) {
                sqlString = "{ call qlsc.sp_tao_suco_tam_v2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Long.parseLong(maSc), requestBody.nguonScId, requestBody.nhomScId,
                                         ngaySc, requestBody.noiDungSc, ngayBh,
                                         requestBody.donViBh, requestBody.nhanVienBh, requestBody.dienThoaiLh,
                                         ngayTn, Long.parseLong(donViId), Long.parseLong(nhanVienId),
                                         henSuaTu, henSuaDen, requestBody.phanloaiSC, new Date(now.getTime()),
                                         nguoiCn, mayCn, ipCn,
                                         Long.parseLong(phanVungId)]) {

                    }
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không lấy được mã sự cố", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            loggingManaged.info("[ChamSocKhachHangController] - [dangKySuCoV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [dangKySuCoV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, Utils.convertMessageError(e.message), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

//    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieuSuCo(KhoaPhieuSuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_KHOAPHIEU(?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId, requestBody.ngayTh, requestBody.nhanVienThId,
                                     requestBody.dsNguyenNhan, requestBody.ghiChuTh, Long.parseLong(nhanVienId),
                                     requestBody.khoaPhieu, nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId)]) {

                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [khoaPhieuSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    //fix

    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieuSuCoV2(KhoaPhieuSuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            // Log request to Telegram
            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "Request: " + (requestBody != null ? objectMapper.writeValueAsString(requestBody) : ""))
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))

            //https://cntt.vnpt.vn/browse/BSS-139418
            def input_log = [:]
            try{
                input_log.action = "nghiemthu_phieu_suyhao_dokiem"
                input_log.body = requestBody
                String check_dokiem = ""
                Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                    sql.call("{ ? = call QLSC.FN_KIEMTRA_DOKIEM_NT_SC_SMP(?,?) }", [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.suCoId]) { String rs ->
                        check_dokiem = rs.toString()
                    }
                }

                input_log.check_dokiem = check_dokiem
                if(check_dokiem == "1" || check_dokiem == "2"){
                    List dstb_anhhuong = []
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call QLSC.sp_lay_port_dstb_anhhuong (?,?,?)}", [phanVungId, requestBody.suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                            dstb_anhhuong = resultSetToObject.getDynamicData(rs)
                        }
                    }
                    input_log.dstb_anhhuong = dstb_anhhuong
                    if(dstb_anhhuong.size() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Khoông lấy được thông tin đo kiểm !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    def input_dokiem = [:]
                    String managerIp = ""
                    List<String> onus = []
                    for(item in dstb_anhhuong){
                        String onu = ""
                        onu = item.slot + """|""" + item.port + """|""" + item.vpi
                        onus.add(onu)
                    }
                    managerIp = dstb_anhhuong[0].ip
                    input_dokiem.managerIp = managerIp
                    input_dokiem.onus = onus
                    input_log.input_doKiemTheoLo = input_dokiem
                    def header = [:]
                    header.put("Content-Type", "application/json; charset=UTF-8")
                    header.put("Authorization", request.getHeader("Authorization"))
                    String kq = dataUtils.doKiemTheoLo(input_dokiem, header)
                    def res = new JsonSlurper().parseText(kq)
                    input_log.kq_doKiemTheoLo = res
                    if(res.error_code != "BSS-00000000"){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Lỗi tích hợp đo kiểm theo lô " + res.message.toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    if(res.data.size == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin đo kiểm theo lô !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    List kq_dokiem = res.data
                    int count = 0
                    for(dt in kq_dokiem){
                        if(dt.onuStatusStr == "ON"){
                            count ++
                        }
                    }
                    if(check_dokiem == "1"){
                        if(count == 0){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Nghiệm thu không thành công: Tất cả thuê bao trên S1, S2 đang ở trạng thái offline.", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }else if(check_dokiem == "2"){
                        if(count/kq_dokiem.size() < 0.7){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có đủ thuê bao hoạt động từ đo kiểm không được nghiệm thu !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }
                }
            }catch (Exception e){
                input_log.ex = e.message
            }finally {
                loggingManaged.info(new ObjectMapper().writeValueAsString(input_log))
            }

            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_KHOAPHIEU(?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId, requestBody.ngayTh, requestBody.nhanVienThId,
                                     requestBody.dsNguyenNhan, requestBody.ghiChuTh, Long.parseLong(nhanVienId),
                                     requestBody.khoaPhieu, nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId)]) {

                }
            }
            
            String netMaQt = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call qlsc.PKG_SAVECSSAPI.LAY_MA_QT_BY_SUCO_ID(?,?,?) }", [requestBody.suCoId, phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        netMaQt = rs.getString("NET_MA_QT")
                    }
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - netMaQt: " + netMaQt)

            if (["QT1.6.1", "QT1.4", "QTXLSC_ACCESS", "QTXLSC_MANE", "C3_CLM_03"].contains(netMaQt)) {
                // Call external API after nghiemThuSuCoService.khoaPhieu
                try {
                    loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Jump into flow netMaQt IN (QT1.6.1, QT1.4, QTXLSC_ACCESS, QTXLSC_MANE, C3_CLM_03)")

                    // Get ma_tinh and token from context
                    String maTinh = session_.get(GenericApi.MA_TINH) != null ? session_.get(GenericApi.MA_TINH).toString() : ""
                    String token = session_.get(GenericApi.TOKEN) != null ? session_.get(GenericApi.TOKEN).toString() : ""

                    // Get extra data from stored procedure
                    Map<String, Object> spData = null
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                            List rsData = resultSetToObject.getDynamicData(rs)
                            if (rsData != null && !rsData.isEmpty()) {
                                spData = rsData[0]
                            }
                        }
                    }
                    loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - spData from PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID: " + spData)

                    String maSc = spData?.get("ma_sc") != null ? String.valueOf(spData?.get("ma_sc")) : ""
                    String phieuId = spData?.get("phieu_id") != null ? String.valueOf(spData?.get("phieu_id")) : ""
                    String noiDungXuLy = spData?.get("noi_dung_xu_ly") != null ? String.valueOf(spData?.get("noi_dung_xu_ly")) : ""
                    String mucDoId = spData?.get("mucdo_id") != null ? String.valueOf(spData?.get("mucdo_id")) : ""
                    String maQt = spData?.get("ma_qt") != null ? String.valueOf(spData?.get("ma_qt")) : ""
                    String maBuoc = spData?.get("ma_buoc") != null ? String.valueOf(spData?.get("ma_buoc")) : ""
                    String donViGiaoId = spData?.get("donvi_giao_id") != null ? String.valueOf(spData?.get("donvi_giao_id")) : ""
                    String donViNhanId = spData?.get("donvi_nhan_id") != null ? String.valueOf(spData?.get("donvi_nhan_id")) : ""

                    if ("C3_CLM_03".equals(maQt)) {
                        Map<String, Object> gwOssData = null
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(" { call qlsc.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_GW_OSS(?,?,?) } ", [maSc, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                                List rsData = resultSetToObject.getDynamicData(rs)
                                if (rsData != null && !rsData.isEmpty()) {
                                    gwOssData = rsData[0]
                                }
                            }
                        }

                        Map<String, Object> payload = new HashMap<>()
                        payload.put("loaiphieu_id", "10")
                        payload.put("tts_parent_id", maSc)

                        if (gwOssData != null) {
                            payload.put("id_tts", gwOssData.get("id_tts") != null ? String.valueOf(gwOssData.get("id_tts")) : "")
                            payload.put("noi_dung_xu_ly", gwOssData.get("noi_dung_xu_ly") != null ? String.valueOf(gwOssData.get("noi_dung_xu_ly")) : "")
                            payload.put("ma_nhanvien_xuly", gwOssData.get("ma_nhanvien_xuly") != null ? String.valueOf(gwOssData.get("ma_nhanvien_xuly")) : "")
                            payload.put("ma_donvi_xuly_hrm", gwOssData.get("ma_donvi_xuly_hrm") != null ? String.valueOf(gwOssData.get("ma_donvi_xuly_hrm")) : "")
                            payload.put("sla_date", gwOssData.get("sla_date") != null ? String.valueOf(gwOssData.get("sla_date")) : "")
                            payload.put("sla_process_time", gwOssData.get("sla_process_time") != null ? String.valueOf(gwOssData.get("sla_process_time")) : "")
                            payload.put("sla", gwOssData.get("sla") != null ? String.valueOf(gwOssData.get("sla")) : "")
                            payload.put("quahan_taibuoc", gwOssData.get("quahan_taibuoc") != null ? String.valueOf(gwOssData.get("quahan_taibuoc")) : "")
                            payload.put("quahan_toantrinh", gwOssData.get("quahan_toantrinh") != null ? String.valueOf(gwOssData.get("quahan_toantrinh")) : "")
                        } else {
                            payload.put("id_tts", "")
                            payload.put("noi_dung_xu_ly", "")
                            payload.put("ma_nhanvien_xuly", "")
                            payload.put("ma_donvi_xuly_hrm", "")
                            payload.put("sla_date", "")
                            payload.put("sla_process_time", "")
                            payload.put("sla", "")
                            payload.put("quahan_taibuoc", "")
                            payload.put("quahan_toantrinh", "")
                        }

                        String apiUrl = tichhopUrl + "/fms_alarms/sync_via_gw_oss"
                        loggingManaged.info("[ChamSocKhachHangController] - [sync_via_gw_oss Request] URL: " + apiUrl)
                        loggingManaged.info("[ChamSocKhachHangController] - [sync_via_gw_oss Request] Payload: " + payload)

                        try {
                            Map<String, String> headers = ["Content-Type": "application/json"]
                            if (token != null && !token.isEmpty()) {
                                headers.put("Authorization", token)
                            }
                            String payloadStr = objectMapper.writeValueAsString(payload)
                            String resp = httpManaged.post(["url": apiUrl], headers, payloadStr)
                            loggingManaged.info("[ChamSocKhachHangController] - [sync_via_gw_oss Response] Body: " + resp)
                            // Log sync_via_gw_oss payload + response to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                        } catch (Exception httpEx) {
                            loggingManaged.error("[ChamSocKhachHangController] - [sync_via_gw_oss HTTP Error] " + httpEx.getMessage())
                            // Log sync_via_gw_oss payload + error to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                        }
                    } else {
                        Map<String, Object> thongTinDongPhieu = null
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(" { call QLSC.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                                List rsData = resultSetToObject.getDynamicData(rs)
                                if (rsData != null && !rsData.isEmpty()) {
                                    thongTinDongPhieu = rsData[0]
                                }
                            }
                        }
                        Map<String, Object> payload = new HashMap<>()
                        payload.put("id_tts", thongTinDongPhieu?.get("id_tts") != null ? String.valueOf(thongTinDongPhieu?.get("id_tts")) : "0")
                        payload.put("id_css", null)
                        payload.put("loaiphieu_id", "10")
                        payload.put("parent_id", thongTinDongPhieu?.get("parent_id") != null ? String.valueOf(thongTinDongPhieu?.get("parent_id")) : maSc)
                        payload.put("ma_tinh", thongTinDongPhieu?.get("ma_tinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_tinh")) : maTinh)
                        payload.put("mo_ta", thongTinDongPhieu?.get("mo_ta") != null ? String.valueOf(thongTinDongPhieu?.get("mo_ta")) : noiDungXuLy)
                        payload.put("tg_batdau", thongTinDongPhieu?.get("tg_batdau") != null ? String.valueOf(thongTinDongPhieu?.get("tg_batdau")) : "")
                        payload.put("tg_dukien_xyly", thongTinDongPhieu?.get("tg_dukien_xyly") != null ? String.valueOf(thongTinDongPhieu?.get("tg_dukien_xyly")) : "")
                        payload.put("tg_ketthuc", thongTinDongPhieu?.get("tg_ketthuc") != null ? String.valueOf(thongTinDongPhieu?.get("tg_ketthuc")) : "")
                        payload.put("anhhuong_dvu", thongTinDongPhieu?.get("anhhuong_dvu") != null ? String.valueOf(thongTinDongPhieu?.get("anhhuong_dvu")) : "0")
                        payload.put("chitiet_anhhuong", thongTinDongPhieu?.get("chitiet_anhhuong") != null ? String.valueOf(thongTinDongPhieu?.get("chitiet_anhhuong")) : "")
                        payload.put("donvi_chuquan", thongTinDongPhieu?.get("donvi_chuquan") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_chuquan")) : "")
                        payload.put("dai_vt", thongTinDongPhieu?.get("dai_vt") != null ? String.valueOf(thongTinDongPhieu?.get("dai_vt")) : "")
                        payload.put("mucdo_id", thongTinDongPhieu?.get("mucdo_id") != null ? String.valueOf(thongTinDongPhieu?.get("mucdo_id")) : mucDoId)
                        payload.put("loai_suco", thongTinDongPhieu?.get("loai_suco") != null ? String.valueOf(thongTinDongPhieu?.get("loai_suco")) : "0")
                        payload.put("hethong", thongTinDongPhieu?.get("hethong") != null ? String.valueOf(thongTinDongPhieu?.get("hethong")) : "0")
                        payload.put("thietbi_id", thongTinDongPhieu?.get("thietbi_id") != null ? String.valueOf(thongTinDongPhieu?.get("thietbi_id")) : "0")
                        payload.put("ma_thietbi", thongTinDongPhieu?.get("ma_thietbi") != null ? String.valueOf(thongTinDongPhieu?.get("ma_thietbi")) : "")
                        payload.put("nguon_sc", thongTinDongPhieu?.get("nguon_sc") != null ? String.valueOf(thongTinDongPhieu?.get("nguon_sc")) : "")
                        payload.put("thongtin_chitiet", thongTinDongPhieu?.get("thongtin_chitiet") != null ? String.valueOf(thongTinDongPhieu?.get("thongtin_chitiet")) : noiDungXuLy)
                        payload.put("nguyen_nhan", thongTinDongPhieu?.get("NGUYEN_NHAN") != null ? String.valueOf(thongTinDongPhieu?.get("NGUYEN_NHAN")) : "")
                        payload.put("giai_phap", thongTinDongPhieu?.get("GIAI_PHAP") != null ? String.valueOf(thongTinDongPhieu?.get("GIAI_PHAP")) : "")
                        payload.put("bienphap_phongngua", thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA") != null ? String.valueOf(thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA")) : "")
                        payload.put("thamso_thaydoi", thongTinDongPhieu?.get("thamso_thaydoi") != null ? String.valueOf(thongTinDongPhieu?.get("thamso_thaydoi")) : "")
                        payload.put("trangthai_xly", thongTinDongPhieu?.get("trangthai_xly") != null ? String.valueOf(thongTinDongPhieu?.get("trangthai_xly")) : "1")
                        payload.put("ma_quytrinh", thongTinDongPhieu?.get("ma_quytrinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_quytrinh")) : maQt)
                        payload.put("ma_buoc", thongTinDongPhieu?.get("ma_buoc") != null ? String.valueOf(thongTinDongPhieu?.get("ma_buoc")) : maBuoc)
                        payload.put("ma_nhanvien_xuly", thongTinDongPhieu?.get("ma_nhanvien_xuly") != null ? String.valueOf(thongTinDongPhieu?.get("ma_nhanvien_xuly")) : "")
                        payload.put("donvi_nhan_id", thongTinDongPhieu?.get("donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_nhan_id")) : donViNhanId)
                        payload.put("ma_donvi_nhan_id", thongTinDongPhieu?.get("ma_donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("ma_donvi_nhan_id")) : "")
                        payload.put("donvi_giao_id", donViGiaoId)
                        payload.put("css_parent_id", "0")
                        payload.put("css_id", phieuId)
                        payload.put("donvi_xuly_id", "0")
                        payload.put("loaidoituong", "")
                        payload.put("sla_date", thongTinDongPhieu?.get("sla_date") != null ? String.valueOf(thongTinDongPhieu?.get("sla_date")) : "")
                        payload.put("sla_process_time", thongTinDongPhieu?.get("sla_process_time") != null ? String.valueOf(thongTinDongPhieu?.get("sla_process_time")) : "")
                        payload.put("sla", thongTinDongPhieu?.get("sla") != null ? String.valueOf(thongTinDongPhieu?.get("sla")) : "")
                        payload.put("quahan_taibuoc", thongTinDongPhieu?.get("quahan_taibuoc") != null ? String.valueOf(thongTinDongPhieu?.get("quahan_taibuoc")) : "")
                        payload.put("quahan_toantrinh", thongTinDongPhieu?.get("quahan_toantrinh") != null ? String.valueOf(thongTinDongPhieu?.get("quahan_toantrinh")) : "")
                        payload.put("phuongan_dexuat", java.util.Collections.emptyList())

                        loggingManaged.info("[ChamSocKhachHangController] - [SaveCSSAPI Request] URL: " + ttsUrl)
                        loggingManaged.info("[ChamSocKhachHangController] - [SaveCSSAPI Request] Payload: " + payload)

                        try {
                            String payloadStr = objectMapper.writeValueAsString(payload)
                            String resp = httpManaged.post(["url": ttsUrl], ["Content-Type": "application/json"], payloadStr)
                            loggingManaged.info("[ChamSocKhachHangController] - [SaveCSSAPI Response] Body: " + resp)
                            // Log SaveCSSAPI payload + response to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                        } catch (Exception httpEx) {
                            loggingManaged.error("[ChamSocKhachHangController] - [SaveCSSAPI HTTP Error] " + httpEx.getMessage())
                            // Log SaveCSSAPI payload + error to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                        }
                    }

                } catch (Exception ex) {
                    // Ignore external API failure so it doesn't fail the main transaction
                    loggingManaged.error("[ChamSocKhachHangController] - [SaveCSSAPI Error] " + ex.getMessage(), ex)
                }
            } else {
                loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Jump into flow netMaQt NOT IN (QT1.6.1, QT1.4, QTXLSC_ACCESS, QTXLSC_MANE, C3_CLM_03)")
                try {
                    int syncStatus = -1;
                    String syncResponse = "";
                    List data = []
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.sp_lay_thongtin_dongphieu_suco_tts(?,?,?) } ", [phanVungId, requestBody.suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                            data = resultSetToObject.getDynamicData(rs)
                        }
                    }
                    loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Response: ", new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("dataTTS", new JsonBuilder(data).toPrettyString())
                            .build())
                    if (!data.isEmpty()) {
                        for (def item : data) {
                            try {
                                Map<String, String> body = new HashMap<>();
                                body.put("data", item.get("json_input").toString());
                                String resp = httpManaged.post(["url": "http://tichhop/tts/saveCSSAPI"], ["Content-Type": "application/json"], objectMapper.writeValueAsString(body));
                                Map res = objectMapper.readValue(resp, Map.class);
                                if (res.get("errorCode").toString() == "0") {
                                    syncStatus = 1;
                                    syncResponse = objectMapper.writeValueAsString(res.get("data"));
                                } else {
                                    syncResponse = res.get("faultString").toString();
                                }
                            } catch (Exception ex) {
                                syncResponse = ex.getMessage();
                            }

                            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                sql.call("{ call QLSC.sp_capnhat_trangthai_log_tts_v2(?,?,?,?,?,?) }", [
                                        phanVungId, item.get("parent_id"), item.get("css_id"), syncStatus, item.get("json_input"), syncResponse])
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieuSuCoV3(KhoaPhieuSuCoV3Request requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            // Log request to Telegram
            logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "Request: " + (requestBody != null ? objectMapper.writeValueAsString(requestBody) : ""))
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)

            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            String ngayTh = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(requestBody.ngayTh ?: now)
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_KHOAPHIEU(?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId, ngayTh, requestBody.nhanVienThId,
                                     requestBody.dsNguyenNhan, requestBody.ghiChuTh, Long.parseLong(nhanVienId),
                                     requestBody.khoaPhieu, nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId)]) {

                }
            }

            String netMaQt = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call qlsc.PKG_SAVECSSAPI.LAY_MA_QT_BY_SUCO_ID(?,?,?) }", [requestBody.suCoId, phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        netMaQt = rs.getString("NET_MA_QT")
                    }
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - netMaQt: " + netMaQt)

            if (["QT1.6.1", "QT1.4", "QTXLSC_ACCESS", "QTXLSC_MANE", "C3_CLM_03"].contains(netMaQt)) {
                // Call external API after nghiemThuSuCoService.khoaPhieu
                try {
                loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Jump into flow netMaQt IN (QT1.6.1, QT1.4, QTXLSC_ACCESS, QTXLSC_MANE, C3_CLM_03)")

                // Get ma_tinh and token from context
                String maTinh = session_.get(GenericApi.MA_TINH) != null ? session_.get(GenericApi.MA_TINH).toString() : ""
                String token = session_.get(GenericApi.TOKEN) != null ? session_.get(GenericApi.TOKEN).toString() : ""

                // Get extra data from stored procedure
                Map<String, Object> spData = null
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(" { call QLSC.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                        List rsData = resultSetToObject.getDynamicData(rs)
                        if (rsData != null && !rsData.isEmpty()) {
                            spData = rsData[0]
                        }
                    }
                }
                loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - spData from PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID: " + spData)

                String maSc = spData?.get("ma_sc") != null ? String.valueOf(spData?.get("ma_sc")) : ""
                String phieuId = spData?.get("phieu_id") != null ? String.valueOf(spData?.get("phieu_id")) : ""
                String noiDungXuLy = spData?.get("noi_dung_xu_ly") != null ? String.valueOf(spData?.get("noi_dung_xu_ly")) : ""
                String mucDoId = spData?.get("mucdo_id") != null ? String.valueOf(spData?.get("mucdo_id")) : ""
                String maQt = spData?.get("ma_qt") != null ? String.valueOf(spData?.get("ma_qt")) : ""
                String maBuoc = spData?.get("ma_buoc") != null ? String.valueOf(spData?.get("ma_buoc")) : ""
                String donViGiaoId = spData?.get("donvi_giao_id") != null ? String.valueOf(spData?.get("donvi_giao_id")) : ""
                String donViNhanId = spData?.get("donvi_nhan_id") != null ? String.valueOf(spData?.get("donvi_nhan_id")) : ""

                if ("C3_CLM_03".equals(maQt)) {
                    Map<String, Object> gwOssData = null
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call qlsc.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_GW_OSS(?,?,?) } ", [maSc, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                            List rsData = resultSetToObject.getDynamicData(rs)
                            if (rsData != null && !rsData.isEmpty()) {
                                gwOssData = rsData[0]
                            }
                        }
                    }

                    Map<String, Object> payload = new HashMap<>()
                    payload.put("loaiphieu_id", "10")
                    payload.put("tts_parent_id", maSc)

                    if (gwOssData != null) {
                        payload.put("id_tts", gwOssData.get("id_tts") != null ? String.valueOf(gwOssData.get("id_tts")) : "")
                        payload.put("noi_dung_xu_ly", gwOssData.get("noi_dung_xu_ly") != null ? String.valueOf(gwOssData.get("noi_dung_xu_ly")) : "")
                        payload.put("ma_nhanvien_xuly", gwOssData.get("ma_nhanvien_xuly") != null ? String.valueOf(gwOssData.get("ma_nhanvien_xuly")) : "")
                        payload.put("ma_donvi_xuly_hrm", gwOssData.get("ma_donvi_xuly_hrm") != null ? String.valueOf(gwOssData.get("ma_donvi_xuly_hrm")) : "")
                    } else {
                        payload.put("id_tts", "")
                        payload.put("noi_dung_xu_ly", "")
                        payload.put("ma_nhanvien_xuly", "")
                        payload.put("ma_donvi_xuly_hrm", "")
                    }

                    String apiUrl = tichhopUrl + "/fms_alarms/sync_via_gw_oss"
                    loggingManaged.info("[ChamSocKhachHangController] - [sync_via_gw_oss Request] URL: " + apiUrl)
                    loggingManaged.info("[ChamSocKhachHangController] - [sync_via_gw_oss Request] Payload: " + payload)

                    try {
                        Map<String, String> headers = ["Content-Type": "application/json"]
                        if (token != null && !token.isEmpty()) {
                            headers.put("Authorization", token)
                        }
                        String payloadStr = objectMapper.writeValueAsString(payload)
                        String resp = httpManaged.post(["url": apiUrl], headers, payloadStr)
                        loggingManaged.info("[ChamSocKhachHangController] - [sync_via_gw_oss Response] Body: " + resp)
                        // Log sync_via_gw_oss payload + response to Telegram
                        logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                    } catch (Exception httpEx) {
                        loggingManaged.error("[ChamSocKhachHangController] - [sync_via_gw_oss HTTP Error] " + httpEx.getMessage())
                        // Log sync_via_gw_oss payload + error to Telegram
                        logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                    }
                } else {
                    Map<String, Object> thongTinDongPhieu = null
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                            List rsData = resultSetToObject.getDynamicData(rs)
                            if (rsData != null && !rsData.isEmpty()) {
                                thongTinDongPhieu = rsData[0]
                            }
                        }
                    }
                    Map<String, Object> payload = new HashMap<>()
                    payload.put("id_tts", thongTinDongPhieu?.get("id_tts") != null ? String.valueOf(thongTinDongPhieu?.get("id_tts")) : "0")
                    payload.put("id_css", null)
                    payload.put("loaiphieu_id", "10")
                    payload.put("parent_id", thongTinDongPhieu?.get("parent_id") != null ? String.valueOf(thongTinDongPhieu?.get("parent_id")) : maSc)
                    payload.put("ma_tinh", thongTinDongPhieu?.get("ma_tinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_tinh")) : maTinh)
                    payload.put("mo_ta", thongTinDongPhieu?.get("mo_ta") != null ? String.valueOf(thongTinDongPhieu?.get("mo_ta")) : noiDungXuLy)
                    payload.put("tg_batdau", thongTinDongPhieu?.get("tg_batdau") != null ? String.valueOf(thongTinDongPhieu?.get("tg_batdau")) : "")
                    payload.put("tg_dukien_xyly", thongTinDongPhieu?.get("tg_dukien_xyly") != null ? String.valueOf(thongTinDongPhieu?.get("tg_dukien_xyly")) : "")
                    payload.put("tg_ketthuc", thongTinDongPhieu?.get("tg_ketthuc") != null ? String.valueOf(thongTinDongPhieu?.get("tg_ketthuc")) : "")
                    payload.put("anhhuong_dvu", thongTinDongPhieu?.get("anhhuong_dvu") != null ? String.valueOf(thongTinDongPhieu?.get("anhhuong_dvu")) : "0")
                    payload.put("chitiet_anhhuong", thongTinDongPhieu?.get("chitiet_anhhuong") != null ? String.valueOf(thongTinDongPhieu?.get("chitiet_anhhuong")) : "")
                    payload.put("donvi_chuquan", thongTinDongPhieu?.get("donvi_chuquan") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_chuquan")) : "")
                    payload.put("dai_vt", thongTinDongPhieu?.get("dai_vt") != null ? String.valueOf(thongTinDongPhieu?.get("dai_vt")) : "")
                    payload.put("mucdo_id", thongTinDongPhieu?.get("mucdo_id") != null ? String.valueOf(thongTinDongPhieu?.get("mucdo_id")) : mucDoId)
                    payload.put("loai_suco", thongTinDongPhieu?.get("loai_suco") != null ? String.valueOf(thongTinDongPhieu?.get("loai_suco")) : "0")
                    payload.put("hethong", thongTinDongPhieu?.get("hethong") != null ? String.valueOf(thongTinDongPhieu?.get("hethong")) : "0")
                    payload.put("thietbi_id", thongTinDongPhieu?.get("thietbi_id") != null ? String.valueOf(thongTinDongPhieu?.get("thietbi_id")) : "0")
                    payload.put("ma_thietbi", thongTinDongPhieu?.get("ma_thietbi") != null ? String.valueOf(thongTinDongPhieu?.get("ma_thietbi")) : "")
                    payload.put("nguon_sc", thongTinDongPhieu?.get("nguon_sc") != null ? String.valueOf(thongTinDongPhieu?.get("nguon_sc")) : "")
                    payload.put("thongtin_chitiet", thongTinDongPhieu?.get("thongtin_chitiet") != null ? String.valueOf(thongTinDongPhieu?.get("thongtin_chitiet")) : noiDungXuLy)
                    payload.put("nguyen_nhan", thongTinDongPhieu?.get("NGUYEN_NHAN") != null ? String.valueOf(thongTinDongPhieu?.get("NGUYEN_NHAN")) : "")
                    payload.put("giai_phap", thongTinDongPhieu?.get("GIAI_PHAP") != null ? String.valueOf(thongTinDongPhieu?.get("GIAI_PHAP")) : "")
                    payload.put("bienphap_phongngua", thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA") != null ? String.valueOf(thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA")) : "")
                    payload.put("thamso_thaydoi", thongTinDongPhieu?.get("thamso_thaydoi") != null ? String.valueOf(thongTinDongPhieu?.get("thamso_thaydoi")) : "")
                    payload.put("trangthai_xly", thongTinDongPhieu?.get("trangthai_xly") != null ? String.valueOf(thongTinDongPhieu?.get("trangthai_xly")) : "1")
                    payload.put("ma_quytrinh", thongTinDongPhieu?.get("ma_quytrinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_quytrinh")) : maQt)
                    payload.put("ma_buoc", thongTinDongPhieu?.get("ma_buoc") != null ? String.valueOf(thongTinDongPhieu?.get("ma_buoc")) : maBuoc)
                    payload.put("ma_nhanvien_xuly", thongTinDongPhieu?.get("ma_nhanvien_xuly") != null ? String.valueOf(thongTinDongPhieu?.get("ma_nhanvien_xuly")) : "")
                    payload.put("donvi_nhan_id", thongTinDongPhieu?.get("donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_nhan_id")) : donViNhanId)
                    payload.put("ma_donvi_nhan_id", thongTinDongPhieu?.get("ma_donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("ma_donvi_nhan_id")) : "")
                    payload.put("donvi_giao_id", donViGiaoId)
                    payload.put("css_parent_id", "0")
                    payload.put("css_id", phieuId)
                    payload.put("donvi_xuly_id", "0")
                    payload.put("loaidoituong", "")
                    payload.put("phuongan_dexuat", java.util.Collections.emptyList())

                    loggingManaged.info("[ChamSocKhachHangController] - [SaveCSSAPI Request] URL: " + ttsUrl)
                    loggingManaged.info("[ChamSocKhachHangController] - [SaveCSSAPI Request] Payload: " + payload)

                    try {
                        String payloadStr = objectMapper.writeValueAsString(payload)
                        String resp = httpManaged.post(["url": ttsUrl], ["Content-Type": "application/json"], payloadStr)
                        loggingManaged.info("[ChamSocKhachHangController] - [SaveCSSAPI Response] Body: " + resp)
                        // Log SaveCSSAPI payload + response to Telegram
                        logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                    } catch (Exception httpEx) {
                        loggingManaged.error("[ChamSocKhachHangController] - [SaveCSSAPI HTTP Error] " + httpEx.getMessage())
                        // Log SaveCSSAPI payload + error to Telegram
                        logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                    }
                }

                } catch (Exception ex) {
                    // Ignore external API failure so it doesn't fail the main transaction
                    loggingManaged.error("[ChamSocKhachHangController] - [SaveCSSAPI Error] " + ex.getMessage(), ex)
                }
            } else {
                loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Jump into flow netMaQt NOT IN (QT1.6.1, QT1.4, QTXLSC_ACCESS, QTXLSC_MANE, C3_CLM_03)")
                try {
                int syncStatus = -1;
                String syncResponse = "";
                String nguyenNhanText = ""
                if (requestBody.nguyenNhanId != null) {
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call QLSC.PKG_NGHIEPVU.sp_get_nguyennhan(?,?,?) }", [Long.parseLong(phanVungId), requestBody.nguyenNhanId, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs != null && rs.next()) {
                                String nguyenNhan = rs.getString("nguyennhan")
                                if ((nguyenNhan != null && !nguyenNhan.trim().isEmpty()) && (nguyenNhanText == null || nguyenNhanText.isEmpty())) {
                                    nguyenNhanText = nguyenNhan
                                }
                            }
                        }
                    }
                }
                List data = []
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(" { call QLSC.sp_lay_thongtin_dongphieu_suco_tts(?,?,?) } ", [phanVungId, requestBody.suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
                loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Response: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", requestId)
                        .put("dataTTS", new JsonBuilder(data).toPrettyString())
                        .build())
                if (!data.isEmpty()) {
                    for (def item : data) {
                        try {
                            Map jsonMap = objectMapper.readValue(item.get("json_input").toString(), Map.class)

                            if (requestBody.nhomNguyenNhanId == 8L) {
                                jsonMap.put("noi_dung_xu_ly", requestBody.ghiChuTh ?: "")
                            } else if (requestBody.nhomNguyenNhanId == 9L) {
                                jsonMap.put("noi_dung_xu_ly", "")
                                jsonMap.put("bp_kiem_soat_bu", nguyenNhanText ?: "")
                            }
                            Map<String, String> body = new HashMap<>();
                            body.put("data", objectMapper.writeValueAsString(jsonMap)); // dùng jsonMap đã map
                            String resp = httpManaged.post(["url": "http://tichhop/tts/saveCSSAPI"], ["Content-Type": "application/json"], objectMapper.writeValueAsString(body));
                            Map res = objectMapper.readValue(resp, Map.class);
                            if (res.get("errorCode").toString() == "0") {
                                syncStatus = 1;
                                syncResponse = objectMapper.writeValueAsString(res.get("data"));
                            } else {
                                syncResponse = res.get("faultString").toString();
                            }
                        } catch (Exception ex) {
                            syncResponse = ex.getMessage();
                        }

                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call("{ call QLSC.sp_capnhat_trangthai_log_tts_v2(?,?,?,?,?,?) }", [
                                    phanVungId, item.get("parent_id"), item.get("css_id"), syncStatus, item.get("json_input"), syncResponse])
                        }
                    }
                }
                } catch (Exception ignored) {
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @Override
    ResponseEntity<BaseResponse<Void>> traPhieuXuLySuCo(TraPhieuRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [traPhieuSuCo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            Date ngayCn = new Date(now.getTime())
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_TRAPHIEU_XULY_UPD(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.phieuId, requestBody.ndTra, requestBody.lyDoTraId,
                                     Long.parseLong(nhanVienId), nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId)]) {

                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [traPhieuSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [traPhieuSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> traPhieuNghiemThu(TraPhieuRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [traPhieuNghiemThu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            Date ngayCn = new Date(now.getTime())
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_TRAPHIEU_NGHIEMTHU_UPD(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.phieuId, requestBody.ndTra, requestBody.lyDoTraId,
                                     Long.parseLong(nhanVienId), ngayCn, nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId)]) {

                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [traPhieuNghiemThu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [traPhieuNghiemThu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> baoTonPhieuSuCo(BaoTonRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [baoTonPhieuSuCo] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            Date ngayCn = new Date(now.getTime())
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_BAOTON_XULY_SUCO(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.phieuId, requestBody.nnTonId, ngayCn,
                                     requestBody.nhanVienBtId, Long.parseLong(donViId), requestBody.ghiChu,
                                     nguoiCn, mayCn,
                                     ipCn, Long.parseLong(phanVungId)]) {

                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [baoTonPhieuSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [baoTonPhieuSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> giaoPhieuXuLy(GiaoPhieuXuLyRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLy] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            //phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_GIAOPHIEU_XULY(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.phieuChaId, requestBody.huongGiaoId, requestBody.ndGiao,
                        requestBody.suCoId, donViId, requestBody.donViNhanId,
                        nhanVienId, requestBody.suCoPtm, requestBody.khan,
                        requestBody.chiTieuTg, nguoiCn,
                        mayCn, ipCn,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [giaoPhieuXuLy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, Utils.convertMessageError(e.message), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

//    @Override
    ResponseEntity<BaseResponse<Void>> giaoPhieuXuLyTTS(GiaoPhieuXuLyTTSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLyTTS] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String email = session_.get(USERNAME)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            //phanVungId = 26
            List ls = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call QLSC.GIAOPHIEU_XULY_NET(?,?,?,?,?,?,?,?,?,?,?,?,?) }", [
                        phanVungId, 2, requestBody.phieuChaId, requestBody.ndGiao, donViId,
                        requestBody.donViNhanId, nhanVienId, null,
                        requestBody.maBuoc, email, mayCn, ipCn, CURSOR_PARAMETER]) { ResultSet rs ->
                    ls = resultSetToObject.getDynamicData(rs)
                }
            }
            String suco_id = ls[0].suco_id
            String giaophieu_id = ls[0].giaophieu_id
            String description = ls[0].description
            if (description != "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, description, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            String ttsString = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call ? := CSS.FN_GET_JSON_DHSX_SEND_TO_TTS(?,?) }", [Sql.CLOB, phanVungId, giaophieu_id]) { Clob rs ->
                    ttsString = rs.getCharacterStream().getText()
                }
            }
            //TTS Service call

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call CSS.SP_CAPNHAT_API_NET_PROBLEMS(?,?) }", [phanVungId, giaophieu_id]) {}
            }

            loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLyTTS] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [giaoPhieuXuLyTTS] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> giaoPhieuXuLyTTSV2(GiaoPhieuXuLyTTSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLyTTSV2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            // Log request to Telegram
            logTelegramService.sendMessage("/cskh/giaoPhieuXuLyTTS", "Request: " + (requestBody != null ? objectMapper.writeValueAsString(requestBody) : ""))
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String email = session_.get(USERNAME)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            //phanVungId = 26
            List ls = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call QLSC.GIAOPHIEU_XULY_NET(?,?,?,?,?,?,?,?,?,?,?,?,?) }", [
                        phanVungId, 2, requestBody.phieuChaId, requestBody.ndGiao, donViId,
                        requestBody.donViNhanId, nhanVienId, null,
                        requestBody.maBuoc, email, mayCn, ipCn, CURSOR_PARAMETER]) { ResultSet rs ->
                    ls = resultSetToObject.getDynamicData(rs)
                }
            }
            String suco_id = ls[0].suco_id
            String giaophieu_id = ls[0].giaophieu_id
            String description = ls[0].description
            if (description != "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, description, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            // Gọi procedure để lấy mã QT
            String netMaQt = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call qlsc.PKG_SAVECSSAPI.LAY_MA_QT_BY_SUCO_ID(?,?,?) }", [suco_id, phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        netMaQt = rs.getString("NET_MA_QT")
                    }
                }
            }

            if (["QT1.6.1", "QT1.4", "QTXLSC_ACCESS", "QTXLSC_MANE", "C3_CLM_03"].contains(netMaQt)) {
                // Prepare API parameters and call the external service
                // Using placeholder example mapping as requested (to be mapped with real values later)
                Map<String, Object> payload = new HashMap<>()
                Map<String, Object> jsonData = new HashMap<>()
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call("{ call QLSC.PKG_DANHMUC_TTS.GET_JSON_DHSX_SEND_TO_TTS(?,?,?) }", [phanVungId, giaophieu_id, CURSOR_PARAMETER]) { ResultSet rs ->
                        List rsData = resultSetToObject.getDynamicData(rs)
                        if (rsData != null && !rsData.isEmpty()) {
                            Map<String, Object> dbRow = (Map<String, Object>) rsData[0]
                            jsonData.put("ID_TTS", dbRow.get("id_tts") != null ? dbRow.get("id_tts") : 0)
                            jsonData.put("LOAIPHIEU_ID", dbRow.get("loaiphieu_id") != null ? dbRow.get("loaiphieu_id") : 2)
                            jsonData.put("PARENT_ID", dbRow.get("parent_id"))
                            jsonData.put("MA_TINH", dbRow.get("ma_tinh"))
                            jsonData.put("DONVI_GIAO_ID", dbRow.get("donvi_giao_id"))
                            jsonData.put("MO_TA", dbRow.get("mo_ta"))
                            jsonData.put("TG_BATDAU", dbRow.get("tg_batdau"))
                            jsonData.put("TG_DUKIEN_XYLY", dbRow.get("tg_dukien_xyly"))
                            jsonData.put("TG_KETTHUC", dbRow.get("tg_ketthuc"))
                            jsonData.put("ANHHUONG_DVU", dbRow.get("anhhuong_dvu") != null ? dbRow.get("anhhuong_dvu") : 0)
                            jsonData.put("CHITIET_ANHHUONG", dbRow.get("chitiet_anhhuong"))
                            jsonData.put("DONVI_CHUQUAN", dbRow.get("donvi_chuquan"))
                            jsonData.put("DAI_VT", dbRow.get("dai_vt"))
                            jsonData.put("MUCDO_ID", dbRow.get("mucdo_id") != null ? dbRow.get("mucdo_id") : 3)
                            jsonData.put("LOAI_SUCO", dbRow.get("loai_suco") != null ? dbRow.get("loai_suco") : 0)
                            jsonData.put("HETHONG", dbRow.get("hethong") != null ? dbRow.get("hethong") : 0)
                            jsonData.put("THIETBI_ID", dbRow.get("thietbi_id") != null ? dbRow.get("thietbi_id") : 0)
                            jsonData.put("MA_THIETBI", dbRow.get("ma_thietbi"))
                            jsonData.put("NGUON_SC", dbRow.get("nguon_sc"))
                            jsonData.put("THONGTIN_CHITIET", dbRow.get("thongtin_chitiet"))
                            jsonData.put("NGUYEN_NHAN", dbRow.get("nguyen_nhan"))
                            jsonData.put("GIAI_PHAP", dbRow.get("giai_phap"))
                            jsonData.put("BIENPHAP_PHONGNGUA", dbRow.get("bienphap_phongngua"))
                            jsonData.put("THAMSO_THAYDOI", dbRow.get("thamso_thaydoi"))
                            jsonData.put("TRANGTHAI_XLY", dbRow.get("trangthai_xly") != null ? dbRow.get("trangthai_xly") : 1)
                            jsonData.put("MA_QUYTRINH", dbRow.get("ma_quytrinh"))
                            jsonData.put("MA_BUOC", dbRow.get("ma_buoc"))
                            jsonData.put("MA_NHANVIEN_XULY", dbRow.get("ma_nhanvien_xuly"))
                            jsonData.put("CSS_ID", dbRow.get("css_id") != null ? dbRow.get("css_id") : 0)
                            jsonData.put("DONVI_NHAN_ID", dbRow.get("donvi_nhan_id"))
                            jsonData.put("MA_DONVI_NHAN_ID", dbRow.get("ma_donvi_nhan_id"))
                            jsonData.put("SLA_DATE", dbRow.get("sla_date"))
                            jsonData.put("SLA_PROCESS_TIME", dbRow.get("sla_process_time"))
                            jsonData.put("SLA", dbRow.get("sla"))
                            jsonData.put("QUAHAN_TAIBUOC", dbRow.get("quahan_taibuoc"))
                            jsonData.put("QUAHAN_TOANTRINH", dbRow.get("quahan_toantrinh"))
                        }
                    }
                }

                payload.put("jsonData", objectMapper.writeValueAsString(jsonData))

                String apiUrl = appComUrl + "/fms_alarms/post_net_dhsx_to_tts"
                System.out.println("[post_net_dhsx_to_tts Request] URL: " + apiUrl)
                System.out.println("[post_net_dhsx_to_tts Request] Payload: " + payload)

                try {
                    String token = session_.get(GenericApi.TOKEN) != null ? session_.get(GenericApi.TOKEN).toString() : ""
                    Map<String, String> headers = ["Content-Type": "application/json"]
                    if (token != null && !token.isEmpty()) {
                        headers.put("Authorization", token)
                    }
                    String payloadStr = objectMapper.writeValueAsString(payload)
                    String resp = httpManaged.post(["url": apiUrl], headers, payloadStr)
                    System.out.println("[post_net_dhsx_to_tts Response] Body: " + resp)
                    // Log post_net_dhsx_to_tts payload + response to Telegram
                    logTelegramService.sendMessage("/cskh/giaoPhieuXuLyTTS", "post_net_dhsx_to_tts\nURL: " + apiUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                } catch (Exception httpEx) {
                    System.err.println("[post_net_dhsx_to_tts HTTP Error] " + httpEx.getMessage())
                    // Log post_net_dhsx_to_tts payload + error to Telegram
                    logTelegramService.sendMessage("/cskh/giaoPhieuXuLyTTS", "post_net_dhsx_to_tts\nURL: " + apiUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                }
            } else {
                int syncStatus = -1;
                String syncResponse = "";
                try {
                    if (!ls.isEmpty()) {
                        int i = 0
                        for (def item : ls) {
                            if (item.get("json_input") != null && item.get("json_input").toString() != "") {
                                try {
                                    Map<String, String> body = new HashMap<>();
                                    body.put("data", item.get("json_input").toString());
                                    String resp = httpManaged.post(["url": "http://tichhop/tts/saveCSSAPI"], ["Content-Type": "application/json"], objectMapper.writeValueAsString(body));
                                    loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLyTTSV2] - Response: ",
                                            new ImmutableMap.Builder<String, Object>().put("respTTS-"+i, resp).build())
                                    Map res = objectMapper.readValue(resp, Map.class);
                                    if (res.get("errorCode").toString() == "0") {
                                        syncStatus = 1;
                                        syncResponse = objectMapper.writeValueAsString(res.get("data"));
                                    } else {
                                        syncResponse = res.get("faultString").toString();
                                    }
                                } catch (Exception ex) {
                                    syncResponse = ex.getMessage();
                                }
    
                                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                    sql.call("{ call QLSC.sp_capnhat_trangthai_log_tts_v2(?,?,?,?,?,?) }", [
                                            phanVungId,item.get("parent_id"), item.get("css_id"), syncStatus, item.get("json_input"), syncResponse])
                                }
                            }
                            i++
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [giaoPhieuXuLyTTSV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [giaoPhieuXuLyTTSV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<MucDoSuCo>>> layDSMucDoSuCo() {
        List<MucDoSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSMucDoSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            //phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [8, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new MucDoSuCo(
                                mucDoId: rs.getLong("mucdo_id"),
                                mucDoSC: rs.getString("mucdo_sc"),
                                canhBaoDT: rs.getLong("canhbao_dt")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSMucDoSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSMucDoSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<HuongGiao>>> layDSHuongGiao(String quyTrinhId) {
        List<HuongGiao> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSHuongGiao] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("quyTrinhId", quyTrinhId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_HUONGGIAO(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(quyTrinhId), Long.parseLong(nhanVienId),
                                     Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new HuongGiao(
                                huongGiaoId: rs.getLong("huonggiao_id"),
                                huongGiao: rs.getString("huonggiao")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSHuongGiao] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSHuongGiao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<QuyTrinh>>> layDSQuyTrinh() {
        List<QuyTrinh> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSQuyTrinh] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_QUYTRINH(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(nhanVienId),
                                     Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new QuyTrinh(
                                quyTrinhId: rs.getLong("quytrinh_id"),
                                quyTrinh: rs.getString("quytrinh")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSQuyTrinh] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSQuyTrinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TinhTrangXuLy>>> layDSTinhTrangXuLy(LayDSTinhTrangXuLyRequest requestBody) {
        List<TinhTrangXuLy> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTinhTrangXuLy] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_XULY(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId, requestBody.trangThai,
                                     Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TinhTrangXuLy(
                                chon: rs.getString("chon"),
                                suCoPtmId: rs.getLong("suco_ptm_id"),
                                tinhTrang: rs.getString("tinhtrang"),
                                loaiPtmId: rs.getLong("loaiptm_id"),
                                ptmId: rs.getLong("ptm_id"),
                                congSoi: rs.getString("congsoi"),
                                donViId: rs.getLong("donvi_id"),
                                donViNhan: rs.getString("donvi_nhan"),
                                kieuGiao: rs.getString("kieu_giao"),
                                phieuId: rs.getLong("phieu_id"),
                                ngayGiao: rs.getString("ngaygiao"),
                                ndGiao: rs.getString("nd_giao"),
                                ttphId: rs.getLong("ttph_id"),
                                ngayTra: rs.getString("ngay_tra"),
                                ndTra: rs.getString("nd_tra"),
                                lyDoTra: rs.getString("lydotra"),
                                khan: rs.getString("khan"),
                                chiTieuTg: rs.getString("chitieu_tg"),
                                conLai: rs.getString("conlai"),
                                ngayTh: rs.getString("ngay_th"),
                                nguoiTh: rs.getString("nguoi_th"),
                                ghiChuTh: rs.getString("ghichu_th"),
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTinhTrangXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTinhTrangXuLy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NguonSuCo>>> layDSNguonSuCo() {
        List<NguonSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSNguonSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [3, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NguonSuCo(
                                nguonSuCoId: rs.getLong("nguonsc_id"),
                                moTa: rs.getString("mota")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSNguonSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSNguonSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuGiaoXuLySuCo>>> layDSPhieuGiaoXuLySuCo(LayDSPhieuGiaoXuLySuCoRequest requestBody) {
        List<PhieuGiaoXuLySuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhieuGiaoXuLySuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_XULY(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.nguonScId,
                        requestBody.maSc,
                        requestBody.ngayGiao,
                        requestBody.trangThai,
                        requestBody.mucDoScId,
                        requestBody.keoDai,
                        requestBody.huongGiaoId,
                        Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuGiaoXuLySuCo(
                                chon: rs.getString("chon"),
                                suCoId: rs.getLong("suco_id"),
                                suSoChaId: rs.getLong("suco_cha_id"),
                                maSc: rs.getString("ma_sc"),
                                phieuId: rs.getLong("phieu_id"),
                                nguonScId: rs.getLong("nguonsc_id"),
                                nhomScId: rs.getLong("nhomsc_id"),
                                nhomSc: rs.getString("nhom_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                dsThietBi: rs.getString("ds_thietbi"),
                                ngaySc: rs.getString("ngay_sc"),
                                thoiGian: rs.getString("thoigian"),
                                henSuaTu: rs.getString("hensuatu"),
                                henSuaDen: rs.getString("hensuaden"),
                                trangThaiSc: rs.getString("trangthai_sc"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mucDoId: rs.getLong("mucdo_id"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhieuGiaoXuLySuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuGiaoXuLySuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DonViGiaoPhieu>>> layDSDonViGiaoPhieu(String huongGiaoId) {
        List<DonViGiaoPhieu> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSDonViGiaoPhieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("huongGiaoId", huongGiaoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_DS_DONVI_GIAOPHIEU(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [huongGiaoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DonViGiaoPhieu(
                                donViId: rs.getLong("donvi_id"),
                                tenDV: rs.getString("ten_dv")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSDonViGiaoPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSDonViGiaoPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TrangThaiPhieu>>> layDSTrangThaiPhieu() {
        List<TrangThaiPhieu> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTrangThaiPhieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [9, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TrangThaiPhieu(
                                trangThaiId: rs.getLong("trangthai_id"),
                                trangThai: rs.getString("trangthai")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTrangThaiPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTrangThaiPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaPhieuTMP(String maSC) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [xoaPhieuTMP] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("maSC", maSC)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call qlsc.pkg_nghiepvu.SP_xoaphieu_tmp(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maSC, Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [xoaPhieuTMP] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [xoaPhieuTMP] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> laySuCoId() {
        String ret = null
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [laySuCoId] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call ? := qlsc.pkg_nghiepvu.FN_lay_suco_id(?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId)]) {
                    String out ->
                        ret = out
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [laySuCoId] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [laySuCoId] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> tiepNhanSuCoV2(TiepNhanSuCoV2Request requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            phanVungId = 26
            String suCoId = 0
            String sqlStringLayID = "{ call ? := qlsc.pkg_nghiepvu.FN_lay_suco_id(?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlStringLayID, [Sql.VARCHAR, Long.parseLong(phanVungId)]) {
                    String out ->
                        suCoId = out
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Process: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("suCoId", suCoId)
                            .build())
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_TIEPNHAN_SUCO_TAM_V2(?,?,sysdate,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        suCoId, requestBody.maSc,
                        nguoiCn, mayCn, ipCn, requestBody.tinhTrang,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, Utils.convertMessageError(e.message), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> tiepNhanSuCoV3(TiepNhanSuCoV3Request requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            phanVungId = 26
            String suCoId = 0
            String sqlStringLayID = "{ call ? := qlsc.pkg_nghiepvu.FN_lay_suco_id(?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlStringLayID, [Sql.VARCHAR, Long.parseLong(phanVungId)]) {
                    String out ->
                        suCoId = out
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Process: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("suCoId", suCoId)
                            .build())
            String tts = ""
            if (requestBody.tinhTrang != null && requestBody.tinhTrang.size() > 0) {
                tts = "<root>"
                requestBody.tinhTrang.each {
                    tts += "<node suco_ptm_id=\"$it.suco_ptm_id\" loaitbi_id=\"$it.loaitbi_id\" thietbi_id=\"$it.thietbi_id\" congsoi=\"$it.congsoi\" ghichu_tt=\"$it.ghichu_tt\" ds_tinhtrang=\"$it.ds_tinhtrang\" xoa=\"$it.xoa\" />"
                }
                tts += "</root>"
            }
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_TIEPNHAN_SUCO_TAM_V2(?,?,sysdate,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        suCoId, requestBody.maSc,
                        nguoiCn, mayCn, ipCn, tts,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [tiepNhanSuCoV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TrangThaiCNTT>>> layDSTrangThaiCNTT(String suCoId) {
        List<TrangThaiCNTT> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTrangThaiCNTT] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("suCoId", suCoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_CNTT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TrangThaiCNTT(
                                suCoPtmId: rs.getLong("suco_ptm_id"),
                                ptmId: rs.getLong("ptm_id"),
                                tenHt: rs.getString("ten_ht"),
                                capDoId: rs.getLong("capdo_id"),
                                ghiChu: rs.getString("ghichu"),
                                dsTinhTrang: rs.getString("ds_tinhtrang")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTrangThaiCNTT] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTrangThaiCNTT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TrangThaiMVT>>> layDSTinhTrangMVT(String suCoId) {
        List<TrangThaiMVT> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTinhTrangMVT] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("suCoId", suCoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_MVT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TrangThaiMVT(
                                suCoPtmId: rs.getLong("suco_ptm_id"),
                                loaiPtmId: rs.getLong("loaiptm_id"),
                                ptmId: rs.getLong("ptm_id"),
                                tenPtm: rs.getString("ten_ptm"),
                                capDoId: rs.getLong("capdo_id"),
                                ghiChu: rs.getString("ghichu"),
                                loaiPtm: rs.getString("loai_ptm"),
                                congSoi: rs.getString("congsoi"),
                                dsTinhTrang: rs.getString("ds_tinhtrang")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTinhTrangMVT] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTinhTrangMVT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TinhTrang>>> layDSTinhTrang() {
        List<TinhTrang> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTinhTrang] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [10, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TinhTrang(
//                                phanVungId: rs.getLong("phanvung_id"),
                                tinhTrangId: rs.getLong("tinhtrang_id"),
                                tinhTrangSc: rs.getString("tinhtrang_sc"),
                                nhomScId: rs.getLong("nhomsc_id"),
                                trangThai: rs.getString("trangthai")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTinhTrang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTinhTrang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhanVien>>> layDSNhanVien(String tenNV) {
        List<NhanVien> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_NHANVIEN(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [tenNV, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NhanVien(
                                nhanVienId: rs.getLong("nhanvien_id"),
                                tenNv: rs.getString("ten_nv"),
                                chucDanh: rs.getString("chucdanh"),
                                viTri: rs.getString("vitri"),
                                tenDv: rs.getString("ten_dv"),
                                sdt: rs.getString("so_dt"),
                                donViId: rs.getLong("donvi_id")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhomSuCo>>> layDSNhomSuCoTheoNV() {
        List<NhomSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_NHOM_SC_THEO_NV(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhanVienId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NhomSuCo(
                                nhomScId: rs.getLong("nhomsc_id"),
                                nhomSc: rs.getString("nhom_sc")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatSuCoTmp(CapNhatSuCoTMPRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatSuCoTmp] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_CAPNHAT_SUCO_TMP(?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.maSc,
                                     requestBody.nhomScId,
                                     requestBody.ngaySc,
                                     requestBody.noiDungSc,
                                     requestBody.ngayBh,
                                     requestBody.donViBh,
                                     requestBody.nhanVienBh,
                                     requestBody.dienThoaiLh,
                                     donViId,
                                     nhanVienId,
                                     requestBody.henSuaTu,
                                     requestBody.henSuaDen,
                                     nguoiCn,
                                     mayCn,
                                     ipCn,
                                     Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatSuCoTmp] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatSuCoTmp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatSuCo(CapNhatSuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_CAPNHAT_SUCO(?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.maSc,
                                     requestBody.ngaySc,
                                     requestBody.noiDungSc,
                                     requestBody.ngayBh,
                                     requestBody.donViBh,
                                     requestBody.nhanVienBh,
                                     requestBody.dienThoaiLh,
                                     donViId,
                                     nhanVienId,
                                     requestBody.henSuaTu,
                                     requestBody.henSuaDen,
                                     nguoiCn,
                                     mayCn,
                                     ipCn,
                                     requestBody.tinhTrang,
                                     Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<AnhChupSuCoSMP>> layDSAnhChupSuCoSMP(String maSC) {
        AnhChupSuCoSMP ret = new AnhChupSuCoSMP(maSc: maSC)
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSAnhChupSuCoSMP] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("maSC", maSC)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            //nhanVienId = 452
            //phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_ANHCHUP_SUCO_SMP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maSC, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.moTa = dataUtils.getFileUrls(rs.getString("mota"), bucketName)
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSAnhChupSuCoSMP] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSAnhChupSuCoSMP] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> capNhatAnhChupTiepNhanSC(CapNhatAnhChupTiepNhanSCRequest requestBody) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatAnhChupTiepNhanSC] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_CAPNHAT_ANHCHUP_TIEPNHAN_SC(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.maSc,
                                     requestBody.phieuId,
                                     requestBody.moTa,
                                     nguoiCn,
                                     mayCn,
                                     Long.parseLong(phanVungId), Sql.VARCHAR]) { String out -> ret = out }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatAnhChupTiepNhanSC] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatAnhChupTiepNhanSC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TienTrinhSuCo>>> layDSTienTrinhSuCo(LayDSTienTrinhSuCoRequest requestBody) {
        List<TienTrinhSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTienTrinhSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TIENTRINH_SC(?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.maSc,
                                     0,
                                     nhanVienId,
                                     requestBody.ttscId,
                                     requestBody.nguonScId,
                                     requestBody.tuNgayYc,
                                     requestBody.denNgayYc,
                                     requestBody.tuNgayHt,
                                     requestBody.denNgayHt,
                                     Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TienTrinhSuCo(
                                chon: rs.getString("chon"),
                                sucoId: rs.getLong("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                ngayTb: rs.getString("ngay_tb"),
                                ngayNt: rs.getString("ngay_nt"),
                                nhanVienTb: rs.getString("nhanvien_tb"),
                                ngayTn: rs.getString("ngay_tn"),
                                nhanVienTn: rs.getString("nhanvien_tn"),
                                nhanVienNt: rs.getString("nhanvien_nt"),
                                ghiChuXl: rs.getString("ghichu_xl"),
                                thoiGian: rs.getString("thoigian"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                trangThaiSc: rs.getString("trangthai_sc"),
                                ghiChuHong: rs.getString("ghichu_hong"),
                                ttscId: rs.getLong("ttsc_id"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTienTrinhSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTienTrinhSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BasePageResponse<List<TienTrinhSuCo>, PageInfo>> layDSTienTrinhSuCoPT(LayDSTienTrinhSuCoRequest requestBody) {
        List<TienTrinhSuCo> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTienTrinhSuCoPT] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TIENTRINH_SC_PT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.maSc,
                                     0,
                                     nhanVienId,
                                     requestBody.ttscId,
                                     requestBody.nguonScId,
                                     requestBody.tuNgayYc,
                                     requestBody.denNgayYc,
                                     requestBody.tuNgayHt,
                                     requestBody.denNgayHt,
                                     Long.parseLong(phanVungId),
                                     requestBody.pageNum, requestBody.pageRec, requestBody.totalRow,
                                     CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rs, ResultSet rsPage ->
                    while (rs.next()) {
                        ret.add(new TienTrinhSuCo(
                                chon: rs.getString("chon"),
                                sucoId: rs.getLong("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                ngayTb: rs.getString("ngay_tb"),
                                ngayNt: rs.getString("ngay_nt"),
                                nhanVienTb: rs.getString("nhanvien_tb"),
                                ngayTn: rs.getString("ngay_tn"),
                                nhanVienTn: rs.getString("nhanvien_tn"),
                                nhanVienNt: rs.getString("nhanvien_nt"),
                                ghiChuXl: rs.getString("ghichu_xl"),
                                thoiGian: rs.getString("thoigian"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                trangThaiSc: rs.getString("trangthai_sc"),
                                ghiChuHong: rs.getString("ghichu_hong"),
                                ttscId: rs.getLong("ttsc_id"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTienTrinhSuCoPT] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTienTrinhSuCoPT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DonVi>>> layDSDonVi() {
        List<DonVi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSDonVi] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [12, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DonVi(
                                id: rs.getLong("id"),
                                name: rs.getString("name"),
                                parentId: rs.getLong("parentid"),
                                muc: rs.getLong("muc")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSDonVi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSDonVi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TrangThaiSuCo>>> layDSTrangThaiSuCo() {
        List<TrangThaiSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTrangThaiSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [7, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TrangThaiSuCo(
                                ttscId: rs.getLong("ttsc_id"),
                                trangThaiSc: rs.getString("trangthai_sc")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTrangThaiSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTrangThaiSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TienTrinhSuCo2>>> layDSTienTrinhSuCo2(String suCoId) {
        List<TienTrinhSuCo2> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTienTrinhSuCo2] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("suCoId", suCoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_TIENTRINH(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TienTrinhSuCo2(
//                                phanVungId: rs.getLong("phanvung_id"),
                                tienTrinhscId: rs.getLong("tientrinhsc_id"),
                                suCoId: rs.getLong("suco_id"),
                                donViId: rs.getLong("donvi_id"),
                                nhanVienId: rs.getLong("nhanvien_id"),
                                noiDung: rs.getString("noidung"),
                                ngayCn: rs.getString("ngay_cn"),
                                nguoiCn: rs.getString("nguoi_cn"),
                                mayCn: rs.getString("may_cn"),
                                ipCn: rs.getString("ip_cn"),
                                donViCn: rs.getString("donvi_cn")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSTienTrinhSuCo2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSTienTrinhSuCo2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> themTienTrinh(ThemTienTrinhRequest requestBody) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [themTienTrinh] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_THEM_TIENTRINH_INS(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId,
                                     donViId,
                                     nhanVienId,
                                     requestBody.noiDung,
                                     nguoiCn,
                                     mayCn,
                                     ipCn,
                                     Long.parseLong(phanVungId), Sql.VARCHAR]) { String out -> ret = out }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [themTienTrinh] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [themTienTrinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> capNhatTienTrinh(CapNhatTienTrinhRequest requestBody) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatTienTrinh] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_CAPNHAT_TIENTRINH_UPD(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.tienTrinhScId,
                                     donViId,
                                     nhanVienId,
                                     requestBody.noiDung,
                                     nguoiCn,
                                     mayCn,
                                     ipCn,
                                     Long.parseLong(phanVungId), Sql.VARCHAR]) { String out -> ret = out }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatTienTrinh] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatTienTrinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> xoaTienTrinh(String tienTrinhScId) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [xoaTienTrinh] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("tienTrinhScId", tienTrinhScId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call ? := QLSC.FN_XOA_TIENTRINH_DEL(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, tienTrinhScId, Long.parseLong(phanVungId)]) { String out -> ret = out }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [xoaTienTrinh] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [xoaTienTrinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<SuCoDPN>>> layDSSuCoDPN(String nguonScId) {
        List<SuCoDPN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoDPN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("nguonScId", nguonScId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
//            donViId = 7
            String sqlString = "{ call qlsc.pkg_danhmuc.SP_LAY_DS_SUCO_DPN(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), nguonScId, nhanVienId,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new SuCoDPN(
                                suCoId: rs.getLong("suco_id"),
                                suCoChaId: rs.getLong("suco_cha_id"),
                                phieuId: rs.getLong("phieu_id"),
                                maSc: rs.getString("ma_sc"),
                                nhomScId: rs.getLong("nhomsc_id"),
                                nhomSc: rs.getString("nhom_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                dsThietBi: rs.getString("ds_thietbi"),
                                ngaySc: rs.getString("ngay_sc_ht"),
                                ngayTb: rs.getString("ngay_tb_ht"),
                                nhanVienTbId: rs.getLong("nhanvien_tb_id"),
                                nhanVienTb: rs.getString("nhanvien_tb"),
                                dienThoaiLh: rs.getString("dienthoai_lh"),
                                ngayTn: rs.getString("ngay_tn_ht"),
                                nhanVienTnId: rs.getLong("nhanvien_tn_id"),
                                nhanVienTn: rs.getString("nhanvien_tn"),
                                thoiGian: rs.getString("thoigian"),
                                henSuaTu: rs.getString("hensuatu_ht"),
                                henSuaDen: rs.getString("hensuaden_ht"),
                                mucDoId: rs.getLong("mucdo_id"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                khoaPhieu: rs.getString("khoaphieu"),
                                moTa: rs.getString("mota"),
                                quytrinh: rs.getString("QUYTRINH"),
                                quytrinh_id: Integer.parseInt(rs.getString("QUYTRINH_ID"))
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoDPN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSSuCoDPN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BasePageResponse<List<SuCoDPN>, PageInfo>> layDSSuCoDPNPT(String nguonScId, Integer pageNum, Integer pageRec, Long totalRow) {
        List<SuCoDPN> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoDPNPT] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("nguonScId", nguonScId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
//            donViId = 7
            String sqlString = "{ call qlsc.pkg_danhmuc.SP_LAY_DS_SUCO_DPN_PT(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), nguonScId, nhanVienId, pageNum, pageRec, totalRow,
                                     CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rs, ResultSet rsPage ->
                    while (rs.next()) {
                        ret.add(new SuCoDPN(
                                suCoId: rs.getLong("suco_id"),
                                suCoChaId: rs.getLong("suco_cha_id"),
                                phieuId: rs.getLong("phieu_id"),
                                maSc: rs.getString("ma_sc"),
                                nhomScId: rs.getLong("nhomsc_id"),
                                nhomSc: rs.getString("nhom_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                dsThietBi: rs.getString("ds_thietbi"),
                                ngaySc: rs.getString("ngay_sc_ht"),
                                ngayTb: rs.getString("ngay_tb_ht"),
                                nhanVienTbId: rs.getLong("nhanvien_tb_id"),
                                nhanVienTb: rs.getString("nhanvien_tb"),
                                dienThoaiLh: rs.getString("dienthoai_lh"),
                                ngayTn: rs.getString("ngay_tn_ht"),
                                nhanVienTnId: rs.getLong("nhanvien_tn_id"),
                                nhanVienTn: rs.getString("nhanvien_tn"),
                                thoiGian: rs.getString("thoigian"),
                                henSuaTu: rs.getString("hensuatu_ht"),
                                henSuaDen: rs.getString("hensuaden_ht"),
                                mucDoId: rs.getLong("mucdo_id"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                khoaPhieu: rs.getString("khoaphieu"),
                                moTa: rs.getString("mota"),
                                quytrinh: rs.getString("QUYTRINH"),
                                quytrinh_id: Integer.parseInt(rs.getString("QUYTRINH_ID")),
                                ngayBd: rs.getString("NGAY_BD"),
                                ngayKt: rs.getString("NGAY_KT")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoDPNPT] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSSuCoDPNPT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> goNhomSuCo(String suCoId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [goNhomSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("suCoId", suCoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.pkg_nghiepvu.SP_go_nhom_suco(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [goNhomSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [goNhomSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<SuCoDPN>> xoaPhieu(String suCoId) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [xoaPhieu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("suCoId", suCoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.XOAPHIEU(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), suCoId]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [xoaPhieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [xoaPhieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<SuCoCPN>>> layDSSuCoCPN(String nguonScId) {
        List<SuCoCPN> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoCPN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("nguonScId", nguonScId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
//            donViId = 7
            String sqlString = "{ call qlsc.pkg_danhmuc.SP_lay_ds_suco_cpn(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), nguonScId, nhanVienId,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new SuCoCPN(
                                chon: rs.getString("chon"),
                                suCoId: rs.getString("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                ngayTb: rs.getString("ngay_tb"),
                                nhanVienTb: rs.getString("nhanvien_tb"),
                                ngayTn: rs.getString("ngay_tn"),
                                nhanVienTn: rs.getString("nhanvien_tn"),
                                thoiGian: rs.getString("thoigian"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                quytrinh: rs.getString("QUYTRINH"),
                                quytrinh_id: Integer.parseInt(rs.getString("QUYTRINH_ID"))
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoCPN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSSuCoCPN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BasePageResponse<List<SuCoCPN>, PageInfo>> layDSSuCoCPNPT(String nguonScId, Integer pageNum, Integer pageRec, Long totalRow) {
        List<SuCoCPN> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoCPNPT] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("nguonScId", nguonScId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
//            donViId = 7
            String sqlString = "{ call qlsc.pkg_danhmuc.SP_lay_ds_suco_cpn_pt(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), nguonScId, nhanVienId, pageNum, pageRec, totalRow,
                                     CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rs, ResultSet rsPage ->
                    while (rs.next()) {
                        ret.add(new SuCoCPN(
                                chon: rs.getString("chon"),
                                suCoId: rs.getString("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                ngayTb: rs.getString("ngay_tb"),
                                nhanVienTb: rs.getString("nhanvien_tb"),
                                ngayTn: rs.getString("ngay_tn"),
                                nhanVienTn: rs.getString("nhanvien_tn"),
                                thoiGian: rs.getString("thoigian"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                quytrinh: rs.getString("QUYTRINH"),
                                quytrinh_id: Integer.parseInt(rs.getString("QUYTRINH_ID")),
                                ngayBd: rs.getString("NGAY_BD"),
                                ngayKt: rs.getString("NGAY_KT")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSSuCoCPNPT] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSSuCoCPNPT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> ganNhomSuCo(GanNhomSuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [goNhomSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call qlsc.pkg_nghiepvu.SP_gan_nhom_suco(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.dsSuCoId, requestBody.suCoChaId, Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [goNhomSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [goNhomSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<NguyenNhan>> layDSNguyenNhan(String nhomNNId) {
        List<NguyenNhan> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSNguyenNhan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("nhomNNId", nhomNNId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_DS_NGUYENNHAN(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhomNNId,
                                     Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NguyenNhan(
                                nguyenNhanId: rs.getLong("nguyennhan_id"),
                                nguyenNhan: rs.getString("nguyennhan")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSNguyenNhan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSNguyenNhan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuNghiemThuXL>>> layDSPhieuNghiemThuXL(LayDSPhieuNghiemThuXLRequest requestBody) {
        List<PhieuNghiemThuXL> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhieuNghiemThuXL] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_NGHIEMTHU_XL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.nhomScId,
                        requestBody.maSc,
                        requestBody.nguoiBaoId,
                        requestBody.nguonScId,
                        requestBody.tinhTrang,
                        requestBody.noiDungSc,
                        requestBody.ngayScTu,
                        requestBody.ngayScDen,
                        requestBody.ngayBaoTu,
                        requestBody.ngayBaoDen,
                        requestBody.mucDoScId,
                        requestBody.keoDaiTuOp,
                        requestBody.keoDaiTu,
                        requestBody.keoDaiDenOp,
                        requestBody.keoDaiDen,
                        donViId,
                        Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuNghiemThuXL(
                                chon: rs.getString("chon"),
                                suCoId: rs.getLong("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                thoiGian: rs.getString("thoigian"),
                                trangThaiSc: rs.getString("trangthai_sc"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSNguyenNhan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuNghiemThuXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    /*
    @Override
    ResponseEntity<BasePageResponse<List<PhieuNghiemThuXL>, PageInfo>> layDSPhieuNghiemThuXLPT(LayDSPhieuNghiemThuXLRequest requestBody) {
        List<PhieuNghiemThuXL> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26

            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_NGHIEMTHU_XL_PT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.nhomScId,
                        requestBody.maSc,
                        requestBody.nguoiBaoId,
                        requestBody.nguonScId,
                        requestBody.tinhTrang,
                        requestBody.noiDungSc,
                        requestBody.ngayScTu,
                        requestBody.ngayScDen,
                        requestBody.ngayBaoTu,
                        requestBody.ngayBaoDen,
                        requestBody.mucDoScId,
                        requestBody.keoDaiTuOp,
                        requestBody.keoDaiTu,
                        requestBody.keoDaiDenOp,
                        requestBody.keoDaiDen,
                        donViId,
                        Long.parseLong(nhanVienId),
                        Long.parseLong(phanVungId),
                        requestBody.pageNum, requestBody.pageRec, requestBody.totalRow, CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rs, ResultSet rsPage ->
                    while (rs.next()) {
                        ret.add(new PhieuNghiemThuXL(
                                chon: rs.getString("chon"),
                                suCoId: rs.getLong("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                thoiGian: rs.getString("thoigian"),
                                trangThaiSc: rs.getString("trangthai_sc"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuNghiemThuXLPT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    */
    @Override
    ResponseEntity<BasePageResponse<List<PhieuNghiemThuXL>, PageInfo>> layDSPhieuNghiemThuXLPT(LayDSPhieuNghiemThuXLRequest requestBody) {
        List<PhieuNghiemThuXL> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_NGHIEMTHU_XL_PT_V2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.nhomScId,
                        requestBody.maSc,
                        requestBody.nguoiBaoId,
                        requestBody.nguonScId,
                        requestBody.tinhTrang,
                        requestBody.maQuytrinh,
                        requestBody.noiDungSc,
                        requestBody.ngayScTu,
                        requestBody.ngayScDen,
                        requestBody.ngayBaoTu,
                        requestBody.ngayBaoDen,
                        requestBody.mucDoScId,
                        requestBody.keoDaiTuOp,
                        requestBody.keoDaiTu,
                        requestBody.keoDaiDenOp,
                        requestBody.keoDaiDen,
                        donViId,
                        Long.parseLong(nhanVienId),
                        Long.parseLong(phanVungId),
                        requestBody.pageNum, requestBody.pageRec, requestBody.totalRow, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuNghiemThuXL(
                                chon: rs.getString("chon"),
                                suCoId: rs.getLong("suco_id"),
                                maSc: rs.getString("ma_sc"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                thoiGian: rs.getString("thoigian"),
                                trangThaiSc: rs.getString("trangthai_sc"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu")
                        ))
                    }
//                    if (rsPage != null && rsPage.next()) {
//                        pageInfo.setPageNum(rsPage.getInt("page_num"))
//                        pageInfo.setPageSize(rsPage.getInt("page_size"))
//                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
//                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuNghiemThuXLPT_V2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuCapNhatKQXL>>> layDSPhieuCapNhatKQXL(LayDSPhieuCapNhatKQXLRequest requestBody) {
        List<PhieuCapNhatKQXL> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_DIEU_TO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.nhomScId,
                        requestBody.maSc,
                        requestBody.nguoiBaoId,
                        requestBody.mucDoKcId,
                        requestBody.nguonScId,
                        requestBody.tinhTrang,
                        requestBody.noiDungSc,
                        requestBody.noiDungGiao,
                        requestBody.ngayScTu,
                        requestBody.ngayScDen,
                        requestBody.ngayBaoTu,
                        requestBody.ngayBaoDen,
                        requestBody.ngayGiaoTu,
                        requestBody.ngayGiaoDen,
                        requestBody.mucDoScId,
                        requestBody.keoDaiTuOp,
                        requestBody.keoDaiTu,
                        requestBody.keoDaiDenOp,
                        requestBody.keoDaiDen,
                        donViId,
                        Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuCapNhatKQXL(
                                chon: rs.getString("chon"),
                                sucoId: rs.getLong("suco_id"),
                                sucoChaId: rs.getLong("suco_cha_id"),
                                phieuId: rs.getLong("phieu_id"),
                                nhomScId: rs.getLong("nhomsc_id"),
                                mucDoId: rs.getLong("mucdo_id"),
                                nguonScId: rs.getLong("nguonsc_id"),
                                maSc: rs.getString("ma_sc"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                thoiGian: rs.getString("thoigian"),
                                trangThaiPh: rs.getString("trangthai_ph"),
                                noiDungGiao: rs.getString("noidung_giao"),
                                donViGiao: rs.getString("donvi_giao"),
                                ngayGiao: rs.getString("ngaygiao"),
                                donViNhan: rs.getString("donvi_nhan"),
                                mucDoKc: rs.getString("mucdo_kc"),
                                chiTieuTg: rs.getString("chitieu_tg"),
                                conLai: rs.getString("conlai"),
                                ghiChuXl: rs.getString("ghichu_xl"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu"),
                                moTa: rs.getString("mota")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuCapNhatKQXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BasePageResponse<List<PhieuCapNhatKQXL>, PageInfo>> layDSPhieuCapNhatKQXLPT(LayDSPhieuCapNhatKQXLRequest requestBody) {
        List<PhieuCapNhatKQXL> ret = []
        PageInfo pageInfo = new PageInfo()
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanvien_id = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_DIEU_TO_PT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.nhomScId,
                        requestBody.maSc,
                        requestBody.nguoiBaoId,
                        requestBody.mucDoKcId,
                        requestBody.nguonScId,
                        requestBody.tinhTrang,
                        requestBody.noiDungSc,
                        requestBody.noiDungGiao,
                        requestBody.ngayScTu,
                        requestBody.ngayScDen,
                        requestBody.ngayBaoTu,
                        requestBody.ngayBaoDen,
                        requestBody.ngayGiaoTu,
                        requestBody.ngayGiaoDen,
                        requestBody.mucDoScId,
                        requestBody.keoDaiTuOp,
                        requestBody.keoDaiTu,
                        requestBody.keoDaiDenOp,
                        requestBody.keoDaiDen,
                        nhanvien_id,
                        Long.parseLong(phanVungId),
                        requestBody.pageNum, requestBody.pageRec, requestBody.totalRow, CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rs, ResultSet rsPage ->
                    while (rs.next()) {
                        ret.add(new PhieuCapNhatKQXL(
                                chon: rs.getString("chon"),
                                sucoId: rs.getLong("suco_id"),
                                sucoChaId: rs.getLong("suco_cha_id"),
                                phieuId: rs.getLong("phieu_id"),
                                nhomScId: rs.getLong("nhomsc_id"),
                                mucDoId: rs.getLong("mucdo_id"),
                                nguonScId: rs.getLong("nguonsc_id"),
                                maSc: rs.getString("ma_sc"),
                                mucDoSc: rs.getString("mucdo_sc"),
                                nhomSc: rs.getString("nhom_sc"),
                                nguonSc: rs.getString("nguon_sc"),
                                noiDungSc: rs.getString("noidung_sc"),
                                ngaySc: rs.getString("ngay_sc"),
                                thoiGian: rs.getString("thoigian"),
                                trangThaiPh: rs.getString("trangthai_ph"),
                                noiDungGiao: rs.getString("noidung_giao"),
                                donViGiao: rs.getString("donvi_giao"),
                                ngayGiao: rs.getString("ngaygiao"),
                                donViNhan: rs.getString("donvi_nhan"),
                                mucDoKc: rs.getString("mucdo_kc"),
                                chiTieuTg: rs.getString("chitieu_tg"),
                                conLai: rs.getString("conlai"),
                                ghiChuXl: rs.getString("ghichu_xl"),
                                canhBaoDt: rs.getString("canhbao_dt"),
                                mauNen: rs.getString("maunen"),
                                mauChu: rs.getString("mauchu"),
                                moTa: rs.getString("mota")
                        ))
                    }
                    if (rsPage != null && rsPage.next()) {
                        pageInfo.setPageNum(rsPage.getInt("page_num"))
                        pageInfo.setPageSize(rsPage.getInt("page_size"))
                        pageInfo.setRecordCount(rsPage.getLong("record_count"))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, pageInfo))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuCapNhatKQXLPT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatGhiChu(CapNhatGhiChuRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatGhiChu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_CAPNHAT_GHICHU_TH_PXL(?,?,sysdate,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.phieuId,
                        requestBody.ghiChu,
                        nguoiCn,
                        mayCn,
                        ipCn,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatGhiChu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatGhiChu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhanTuMangCanXL>>> layDSPhanTuMangCanXL(String phieuId) {
        List<PhanTuMangCanXL> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhanTuMangCanXL] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("phieuId", phieuId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PTM_PDT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        phieuId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhanTuMangCanXL(
                                loaiPtm: rs.getString("loai_ptm"),
                                tenPtm: rs.getString("ten_ptm"),
                                tinhTrang: rs.getString("tinhtrang"),
                                ghiChuTt: rs.getString("ghichu_tt"),
                                ghiChuHc: rs.getString("ghichu_hc"),
                                ngayCn: rs.getString("ngay_cn"),
                                nguoiCn: rs.getString("nguoi_cn"),
                                suCoPtmId: rs.getLong("suco_ptm_id"),
                                loaiPtmId: rs.getLong("loaiptm_id"),
                                ptmId: rs.getLong("ptm_id"),
                                congSoi: rs.getString("congsoi"),
                                moTa: rs.getString("mota")
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhanTuMangCanXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhanTuMangCanXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuDieuNghiemThu>>> layDSPhieuDieuNghiemThu(String suCoId) {
        List<PhieuDieuNghiemThu> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhieuDieuNghiemThu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("suCoId", suCoId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEUDIEU_NT(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        suCoId, donViId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuDieuNghiemThu(
                                phieuXlId: rs.getLong("phieu_xl_id"),
                                ttphXlId: rs.getLong("ttph_xl_id"),
                                ttphXl: rs.getString("ttph_xl"),
                                donViNhanId: rs.getLong("donvi_nhan_id"),
                                donViNhan: rs.getString("donvi_nhan"),
                                ngayGiao: rs.getString("ngaygiao"),
                                ndGiao: rs.getString("nd_giao"),
                                khan: rs.getString("khan"),
                                chiTieuTg: rs.getString("chitieu_tg"),
                                conLai: rs.getString("conlai"),
                                ghiChuXl: rs.getString("ghichu_xl"),
                                nhanVienXlId: rs.getLong("nhanvien_xl_id"),
                                nhanVienXl: rs.getString("nhanvien_xl"),
                                ngayXl: rs.getString("ngay_xl"),
                                phieuNtId: rs.getLong("phieu_nt_id"),
                                ttphNtId: rs.getLong("ttph_nt_id"),
                                ghiChuNt: rs.getString("ghichu_nt"),
                                nhanVienNtId: rs.getLong("nhanvien_nt_id"),
                                nhanVienNt: rs.getString("nhanvien_nt"),
                                ngayNt: rs.getString("ngay_nt"),
                        ))
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layDSPhieuDieuNghiemThu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layDSPhieuDieuNghiemThu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatGhiChuPtm(CapNhatGhiChuPtmRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatGhiChuPtm] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_CAPNHAT_GHICHU_XULY_PTM(?,?,?,sysdate,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.phieuId,
                        requestBody.suCoPtmId,
                        requestBody.ghiChu,
                        nguoiCn,
                        mayCn,
                        ipCn,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatGhiChuPtm] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatGhiChuPtm] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> hoanCongXLSuCo(HoanCongXLSuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [hoanCongXLSuCo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_HOANCONG_XULY_SUCO(?,?,?,sysdate,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.phieuId,
                        nhanVienId,
                        requestBody.ghiChu,
                        nguoiCn,
                        mayCn,
                        ipCn,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [hoanCongXLSuCo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [hoanCongXLSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> nghiemThuPhieuXL(NghiemThuPhieuXLRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [nghiemThuPhieuXL] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_NGHIEMTHU_XULY(?,?,?,sysdate,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.phieuId,
                        requestBody.ghiChu,
                        nhanVienId,
                        nguoiCn,
                        mayCn,
                        ipCn,
                        Long.parseLong(phanVungId)]) {}
            }
            loggingManaged.info("[ChamSocKhachHangController] - [nghiemThuPhieuXL] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [nghiemThuPhieuXL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<AnhNghiemThuSc>> layAnhNghiemThuSC(LayAnhNghiemThuSCRequest requestBody) {
        AnhNghiemThuSc ret = new AnhNghiemThuSc(maSc: requestBody.maSc, phieuId: requestBody.phieuId)
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [layAnhNghiemThuSC] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_LAY_ANHCHUP_NGHIEMTHU_SC_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.maSc, requestBody.phieuId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.moTa = dataUtils.getFileUrls(rs.getString("mota"), bucketName)
                    }
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [layAnhNghiemThuSC] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [layAnhNghiemThuSC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> capNhatAnhNghiemThuSC(CapNhatAnhNghiemThuSCRequest requestBody) {
        String ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatAnhNghiemThuSC] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 452
//            phanVungId = 26
            String sqlString = "{ call QLSC.SP_CAPNHAT_ANHCHUP_TIEPNHAN_SC(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.maSc,
                        requestBody.phieuId,
                        requestBody.moTa,
                        nguoiCn,
                        mayCn,
                        Long.parseLong(phanVungId),
                        Sql.VARCHAR]) { String out ->
                    ret = out
                }
            }
            loggingManaged.info("[ChamSocKhachHangController] - [capNhatAnhNghiemThuSC] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [capNhatAnhNghiemThuSC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // https://cntt.vnpt.vn/browse/ONEAPP-20123
    @Override
    ResponseEntity<BaseResponse<String>> danhSachTbscLoaihinhSel(LayDsTbscLoaiHinhSelRequest requestBody) {
        List data
        String requestId = genericApi.generateRequestId()

        try {
            loggingManaged.info("[ChamSocKhachHangController] - [sp_lay_ds_tbsc_loaihinh_sel] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)

            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TBSC_LOAIHINH_SEL(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, requestBody.p_suco_id, requestBody.p_loaitb_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [sp_lay_ds_tbsc_loaihinh_sel] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [sp_lay_ds_tbsc_loaihinh_sel] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> spGiaoViecNhanVienXhh(SP_GIAOVIEC_NHANVIEN_XHH_REQUEST requestBody) {
        String requestId = genericApi.generateRequestId()
        List<Map<String,Objects>> listResp = new ArrayList<>();
        try {
            loggingManaged.info("[CSKHController] - [spGiaoViecNhanVienXhh] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String sqlString = "{ call QLSC.SP_GIAOVIEC_NHANVIEN_XHH(?, ?, ?, ?, ?, ?, ?, ?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        requestBody.p_phanvung_id,       // IN NUMBER
                        requestBody.p_phieu_id,          // IN NUMBER
                        requestBody.p_ten_nhanvien_th,   // IN VARCHAR
                        requestBody.p_so_dt,             // IN VARCHAR
                        requestBody.p_nhanvien_giao_id,  // IN NUMBER
                        requestBody.p_nhiemvu,           // IN VARCHAR2
                        requestBody.p_ghichu,            // IN VARCHAR2
                        CURSOR_PARAMETER                      // OUT VARCHAR2
                ]) { ResultSet rs ->
                    while (rs.next()) {
                        Map rep=new HashMap()
                        rep["GIAOVIEC_ID"] =rs.getObject("GIAOVIEC_ID")
                        rep["SO_DT"] =rs.getObject("SO_DT")
                        rep['ND_SMS'] =rs.getObject("ND_SMS")
                        rep['status']=rs.getObject("status")
                        rep['trangthai']=rs.getObject("trangthai")
                        rep['ngaygiao']=rs.getObject("ngaygiao")
                        listResp.add(rep)
                    }
                }
            }
            List<Map<String,Objects>> mapList= smsSendService.sendSmsSpGiaoViecNhanVienXhh(listResp) as List<Map<String, Objects>>;
            // cap nhap trinag thai
            for (Map<String, Objects> map : mapList) {
                if(map.get("IS_SEND")){
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call QLSC.pkg_nghiepvu.SP_CAPNHAT_TRANGTHAI_GUI_SMS(?, ?) }"
                                , [requestBody.p_phanvung_id, map.get("P_DS_GIAOVIEC_ID")]) {}
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [spGiaoViecNhanVienXhh] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", listResp != null ? listResp : "")
                            .build())
            if (listResp.size()!=0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(listResp, requestId) as BaseResponse<Void>)
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Cập nhập không thành công", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [SntogponsnWs] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
     @Override
    ResponseEntity<BaseResponse<Object>> precheckXlscKpi(PrecheckXlscKpiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[ChamSocKhachHangController] - [precheckXlscKpi] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody != null ? requestBody.toString() : "")
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String token = session_.get(GenericApi.TOKEN) != null ? session_.get(GenericApi.TOKEN).toString() : ""

            // 1. Gọi SP lấy device_ip, alarm_code, target (đã trích xuất JSON trong SP)
            Map<String, Object> sucoData = null
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(" { call QLSC.PKG_SAVECSSAPI.SP_LAY_PRECHECK_GIAI_PHAP_BY_MA_SC(?,?,?) } ",
                        [requestBody.maSc, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    List rsData = resultSetToObject.getDynamicData(rs)
                    if (rsData != null && !rsData.isEmpty()) {
                        sucoData = rsData[0]
                    }
                }
            }

            if (sucoData == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId,
                        "Không tìm thấy thông tin sự cố", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            loggingManaged.info("[ChamSocKhachHangController] - [precheckXlscKpi] - SP result keys: " + sucoData.keySet() + ", values: " + sucoData)

            // 2. Lấy trực tiếp các trường đã được SP trích xuất từ JSON
            String deviceIp = (sucoData?.get("DEVICE_IP") ?: sucoData?.get("device_ip") ?: "").toString()
            String alarmCode = (sucoData?.get("ALARM_CODE") ?: sucoData?.get("alarm_code") ?: "").toString()
            String target = (sucoData?.get("TARGET") ?: sucoData?.get("target") ?: "").toString()

            if (deviceIp.isEmpty() || alarmCode.isEmpty() || target.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId,
                        "Không thể lấy kết quả precheck mới nhất do thiếu dữ liệu từ hệ thống nguồn.", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            // 3. Gọi API tichhop precheck_xlsc_kpi
            Map<String, Object> precheckPayload = ["device_ip": deviceIp, "alarm_code": alarmCode, "target": target]

            String apiUrl = tichhopUrl + "/fms_alarms/precheck_xlsc_kpi"
            Map<String, String> headers = ["Content-Type": "application/json"]
            if (token != null && !token.isEmpty()) {
                headers.put("Authorization", token)
            }

            loggingManaged.info("[ChamSocKhachHangController] - [precheckXlscKpi] - Call tichhop: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("url", apiUrl)
                            .put("payload", precheckPayload)
                            .build())

            String apiResp
            try {
                apiResp = httpManaged.post(["url": apiUrl], headers, objectMapper.writeValueAsString(precheckPayload))
            } catch (Exception ex) {
                loggingManaged.error("[ChamSocKhachHangController] - [precheckXlscKpi] - Lỗi gọi tichhop: " + apiUrl, ex)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId,
                        "Không thể kết nối đến hệ thống precheck: " + ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            def apiResult
            try {
                apiResult = new JsonSlurper().parseText(apiResp)
            } catch (Exception ignore) {
                loggingManaged.error("[ChamSocKhachHangController] - [precheckXlscKpi] - Parse response lỗi, raw: " + apiResp)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId,
                        "Lỗi parse response từ hệ thống precheck", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            loggingManaged.info("[ChamSocKhachHangController] - [precheckXlscKpi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("result", apiResult != null ? new JsonBuilder(apiResult).toString() : "")
                            .build())

            // Chỉ lấy phần data từ tichhop trả về (convert sang HashMap để có thể bổ sung field)
            Map tichhopData = apiResult?.data != null ? new LinkedHashMap(apiResult.data) : null
            if (tichhopData == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId,
                        "Hệ thống precheck không trả về dữ liệu", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            // Bổ sung alarm_code từ SP vào response cho FE hiển thị
            tichhopData.put("alarm_code", alarmCode)

            // Kiểm tra status từ tichhop
            if (tichhopData.status == "fail") {
                String failMsg = tichhopData.message ?: "Precheck thất bại"
                return ResponseEntity.ok().body(BaseResponseBuilder.of(tichhopData, requestId,
                        failMsg, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(tichhopData, requestId))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [precheckXlscKpi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieuSuCoV4(KhoaPhieuSuCoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("requestId", requestId)
                    .put("input", new JsonBuilder(requestBody).toPrettyString())
                    .put("action", "/cskh/khoaPhieuSuCo")
                    .put("username", session_.get(USERNAME))
                    .put("identifyCode", session_.get(IDENTIFY))
                    .build())
            // Log request to Telegram
            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "Request: " + (requestBody != null ? objectMapper.writeValueAsString(requestBody) : ""))
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))

            //https://cntt.vnpt.vn/browse/BSS-139418
            def input_log = [:]
            try{
                input_log.action = "nghiemthu_phieu_suyhao_dokiem"
                input_log.body = requestBody
                String check_dokiem = ""
                Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                    sql.call("{ ? = call QLSC.FN_KIEMTRA_DOKIEM_NT_SC_SMP(?,?) }", [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.suCoId]) { String rs ->
                        check_dokiem = rs.toString()
                    }
                }

                input_log.check_dokiem = check_dokiem
                if(check_dokiem == "1" || check_dokiem == "2"){
                    List dstb_anhhuong = []
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call("{ call QLSC.sp_lay_port_dstb_anhhuong (?,?,?)}", [phanVungId, requestBody.suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                            dstb_anhhuong = resultSetToObject.getDynamicData(rs)
                        }
                    }
                    input_log.dstb_anhhuong = dstb_anhhuong
                    if(dstb_anhhuong.size() == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Khoông lấy được thông tin đo kiểm !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    def input_dokiem = [:]
                    String managerIp = ""
                    List<String> onus = []
                    for(item in dstb_anhhuong){
                        String onu = ""
                        onu = item.slot + """|""" + item.port + """|""" + item.vpi
                        onus.add(onu)
                    }
                    managerIp = dstb_anhhuong[0].ip
                    input_dokiem.managerIp = managerIp
                    input_dokiem.onus = onus
                    input_log.input_doKiemTheoLo = input_dokiem
                    def header = [:]
                    header.put("Content-Type", "application/json; charset=UTF-8")
                    header.put("Authorization", request.getHeader("Authorization"))
                    String kq = dataUtils.doKiemTheoLo(input_dokiem, header)
                    def res = new JsonSlurper().parseText(kq)
                    input_log.kq_doKiemTheoLo = res
                    if(res.error_code != "BSS-00000000"){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Lỗi tích hợp đo kiểm theo lô " + res.message.toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    if(res.data.size == 0){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin đo kiểm theo lô !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    List kq_dokiem = res.data
                    int count = 0
                    for(dt in kq_dokiem){
                        if(dt.onuStatusStr == "ON"){
                            count ++
                        }
                    }
                    if(check_dokiem == "1"){
                        if(count == 0){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thuê bao hoạt động từ đo kiểm không được nghiệm thu !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }else if(check_dokiem == "2"){
                        if(count/kq_dokiem.size() < 0.7){
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có đủ thuê bao hoạt động từ đo kiểm không được nghiệm thu !", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }
                }
            }catch (Exception e){
                input_log.ex = e.message
            }finally {
                loggingManaged.info(new ObjectMapper().writeValueAsString(input_log))
            }

            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_KHOAPHIEU_V2(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId, requestBody.ngayTh, requestBody.nhanVienThId,
                                     requestBody.dsNguyenNhan, requestBody.ghiChuTh, Long.parseLong(nhanVienId),
                                     requestBody.khoaPhieu, nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId),
                                     requestBody.anhTruoc,
                                     requestBody.anhSau,
                                     requestBody.bienBan]) {

                }
            }

            String netMaQt = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call qlsc.PKG_SAVECSSAPI.LAY_MA_QT_BY_SUCO_ID(?,?,?) }", [requestBody.suCoId, phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        netMaQt = rs.getString("NET_MA_QT")
                    }
                }
            }


            if (["QT1.6.1", "QT1.4", "QTXLSC_ACCESS", "QTXLSC_MANE", "C3_CLM_03"].contains(netMaQt)) {
                // Call external API after nghiemThuSuCoService.khoaPhieu
                try {
                    // Get ma_tinh and token from context
                    String maTinh = session_.get(GenericApi.MA_TINH) != null ? session_.get(GenericApi.MA_TINH).toString() : ""
                    String token = session_.get(GenericApi.TOKEN) != null ? session_.get(GenericApi.TOKEN).toString() : ""

                    // Get extra data from stored procedure
                    Map<String, Object> spData = null
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                            List rsData = resultSetToObject.getDynamicData(rs)
                            if (rsData != null && !rsData.isEmpty()) {
                                spData = rsData[0]
                            }
                        }
                    }

                    String maSc = spData?.get("ma_sc") != null ? String.valueOf(spData?.get("ma_sc")) : ""
                    String phieuId = spData?.get("phieu_id") != null ? String.valueOf(spData?.get("phieu_id")) : ""
                    String noiDungXuLy = spData?.get("noi_dung_xu_ly") != null ? String.valueOf(spData?.get("noi_dung_xu_ly")) : ""
                    String mucDoId = spData?.get("mucdo_id") != null ? String.valueOf(spData?.get("mucdo_id")) : ""
                    String maQt = spData?.get("ma_qt") != null ? String.valueOf(spData?.get("ma_qt")) : ""
                    String maBuoc = spData?.get("ma_buoc") != null ? String.valueOf(spData?.get("ma_buoc")) : ""
                    String donViGiaoId = spData?.get("donvi_giao_id") != null ? String.valueOf(spData?.get("donvi_giao_id")) : ""
                    String donViNhanId = spData?.get("donvi_nhan_id") != null ? String.valueOf(spData?.get("donvi_nhan_id")) : ""

                    if ("C3_CLM_03".equals(maQt)) {
                        Map<String, Object> gwOssData = null
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(" { call qlsc.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_GW_OSS(?,?,?) } ", [maSc, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                                List rsData = resultSetToObject.getDynamicData(rs)
                                if (rsData != null && !rsData.isEmpty()) {
                                    gwOssData = rsData[0]
                                }
                            }
                        }

                        Map<String, Object> payload = new HashMap<>()
                        payload.put("loaiphieu_id", "10")
                        payload.put("tts_parent_id", maSc)

                        if (gwOssData != null) {
                            payload.put("id_tts", gwOssData.get("id_tts") != null ? String.valueOf(gwOssData.get("id_tts")) : "")
                            payload.put("noi_dung_xu_ly", gwOssData.get("noi_dung_xu_ly") != null ? String.valueOf(gwOssData.get("noi_dung_xu_ly")) : "")
                            payload.put("ma_nhanvien_xuly", gwOssData.get("ma_nhanvien_xuly") != null ? String.valueOf(gwOssData.get("ma_nhanvien_xuly")) : "")
                            payload.put("ma_donvi_xuly_hrm", gwOssData.get("ma_donvi_xuly_hrm") != null ? String.valueOf(gwOssData.get("ma_donvi_xuly_hrm")) : "")
                        } else {
                            payload.put("id_tts", "")
                            payload.put("noi_dung_xu_ly", "")
                            payload.put("ma_nhanvien_xuly", "")
                            payload.put("ma_donvi_xuly_hrm", "")
                        }

                        String apiUrl = tichhopUrl + "/fms_alarms/sync_via_gw_oss"

                        Long callStartTime = null
                        Long executionTime = null
                        try {
                            Map<String, String> headers = ["Content-Type": "application/json"]
                            if (token != null && !token.isEmpty()) {
                                headers.put("Authorization", token)
                            }
                            String payloadStr = objectMapper.writeValueAsString(payload)
                            callStartTime = System.currentTimeMillis()
                            String resp = httpManaged.post(["url": apiUrl], headers, payloadStr)
                            executionTime = System.currentTimeMillis() - callStartTime
                            // Log sync_via_gw_oss payload + response to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCo")
                                    .put("urlTts", apiUrl)
                                    .put("input", payloadStr)
                                    .put("response", resp)
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        } catch (Exception httpEx) {
                            if (callStartTime != null) {
                                executionTime = System.currentTimeMillis() - callStartTime
                            }
                            // Log sync_via_gw_oss payload + error to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCo")
                                    .put("urlTts", apiUrl)
                                    .put("input", objectMapper.writeValueAsString(payload))
                                    .put("response", httpEx.getMessage())
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        }
                    } else {
                        Map<String, Object> thongTinDongPhieu = null
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(" { call QLSC.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                                List rsData = resultSetToObject.getDynamicData(rs)
                                if (rsData != null && !rsData.isEmpty()) {
                                    thongTinDongPhieu = rsData[0]
                                }
                            }
                        }
                        Map<String, Object> payload = new HashMap<>()
                        payload.put("id_tts", thongTinDongPhieu?.get("id_tts") != null ? String.valueOf(thongTinDongPhieu?.get("id_tts")) : "0")
                        payload.put("id_css", null)
                        payload.put("loaiphieu_id", "10")
                        payload.put("parent_id", thongTinDongPhieu?.get("parent_id") != null ? String.valueOf(thongTinDongPhieu?.get("parent_id")) : maSc)
                        payload.put("ma_tinh", thongTinDongPhieu?.get("ma_tinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_tinh")) : maTinh)
                        payload.put("mo_ta", thongTinDongPhieu?.get("mo_ta") != null ? String.valueOf(thongTinDongPhieu?.get("mo_ta")) : noiDungXuLy)
                        payload.put("tg_batdau", thongTinDongPhieu?.get("tg_batdau") != null ? String.valueOf(thongTinDongPhieu?.get("tg_batdau")) : "")
                        payload.put("tg_dukien_xyly", thongTinDongPhieu?.get("tg_dukien_xyly") != null ? String.valueOf(thongTinDongPhieu?.get("tg_dukien_xyly")) : "")
                        payload.put("tg_ketthuc", thongTinDongPhieu?.get("tg_ketthuc") != null ? String.valueOf(thongTinDongPhieu?.get("tg_ketthuc")) : "")
                        payload.put("anhhuong_dvu", thongTinDongPhieu?.get("anhhuong_dvu") != null ? String.valueOf(thongTinDongPhieu?.get("anhhuong_dvu")) : "0")
                        payload.put("chitiet_anhhuong", thongTinDongPhieu?.get("chitiet_anhhuong") != null ? String.valueOf(thongTinDongPhieu?.get("chitiet_anhhuong")) : "")
                        payload.put("donvi_chuquan", thongTinDongPhieu?.get("donvi_chuquan") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_chuquan")) : "")
                        payload.put("dai_vt", thongTinDongPhieu?.get("dai_vt") != null ? String.valueOf(thongTinDongPhieu?.get("dai_vt")) : "")
                        payload.put("mucdo_id", thongTinDongPhieu?.get("mucdo_id") != null ? String.valueOf(thongTinDongPhieu?.get("mucdo_id")) : mucDoId)
                        payload.put("loai_suco", thongTinDongPhieu?.get("loai_suco") != null ? String.valueOf(thongTinDongPhieu?.get("loai_suco")) : "0")
                        payload.put("hethong", thongTinDongPhieu?.get("hethong") != null ? String.valueOf(thongTinDongPhieu?.get("hethong")) : "0")
                        payload.put("thietbi_id", thongTinDongPhieu?.get("thietbi_id") != null ? String.valueOf(thongTinDongPhieu?.get("thietbi_id")) : "0")
                        payload.put("ma_thietbi", thongTinDongPhieu?.get("ma_thietbi") != null ? String.valueOf(thongTinDongPhieu?.get("ma_thietbi")) : "")
                        payload.put("nguon_sc", thongTinDongPhieu?.get("nguon_sc") != null ? String.valueOf(thongTinDongPhieu?.get("nguon_sc")) : "")
                        payload.put("thongtin_chitiet", thongTinDongPhieu?.get("thongtin_chitiet") != null ? String.valueOf(thongTinDongPhieu?.get("thongtin_chitiet")) : noiDungXuLy)
                        payload.put("nguyen_nhan", thongTinDongPhieu?.get("NGUYEN_NHAN") != null ? String.valueOf(thongTinDongPhieu?.get("NGUYEN_NHAN")) : "")
                        payload.put("giai_phap", thongTinDongPhieu?.get("GIAI_PHAP") != null ? String.valueOf(thongTinDongPhieu?.get("GIAI_PHAP")) : "")
                        payload.put("bienphap_phongngua", thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA") != null ? String.valueOf(thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA")) : "")
                        payload.put("thamso_thaydoi", thongTinDongPhieu?.get("thamso_thaydoi") != null ? String.valueOf(thongTinDongPhieu?.get("thamso_thaydoi")) : "")
                        payload.put("trangthai_xly", thongTinDongPhieu?.get("trangthai_xly") != null ? String.valueOf(thongTinDongPhieu?.get("trangthai_xly")) : "1")
                        payload.put("ma_quytrinh", thongTinDongPhieu?.get("ma_quytrinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_quytrinh")) : maQt)
                        payload.put("ma_buoc", thongTinDongPhieu?.get("ma_buoc") != null ? String.valueOf(thongTinDongPhieu?.get("ma_buoc")) : maBuoc)
                        payload.put("ma_nhanvien_xuly", thongTinDongPhieu?.get("ma_nhanvien_xuly") != null ? String.valueOf(thongTinDongPhieu?.get("ma_nhanvien_xuly")) : "")
                        payload.put("donvi_nhan_id", thongTinDongPhieu?.get("donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_nhan_id")) : donViNhanId)
                        payload.put("ma_donvi_nhan_id", thongTinDongPhieu?.get("ma_donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("ma_donvi_nhan_id")) : "")
                        payload.put("donvi_giao_id", donViGiaoId)
                        payload.put("css_parent_id", "0")
                        payload.put("css_id", phieuId)
                        payload.put("donvi_xuly_id", "0")
                        payload.put("loaidoituong", "")
                        payload.put("phuongan_dexuat", java.util.Collections.emptyList())


                        Long callStartTime = null
                        Long executionTime = null
                        try {
                            String payloadStr = objectMapper.writeValueAsString(payload)
                            callStartTime = System.currentTimeMillis()
                            String resp = httpManaged.post(["url": ttsUrl], ["Content-Type": "application/json"], payloadStr)
                            executionTime = System.currentTimeMillis() - callStartTime
                            // Log SaveCSSAPI payload + response to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCo")
                                    .put("urlTts", ttsUrl)
                                    .put("input", payloadStr)
                                    .put("response", resp)
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        } catch (Exception httpEx) {
                            if (callStartTime != null) {
                                executionTime = System.currentTimeMillis() - callStartTime
                            }
                            // Log SaveCSSAPI payload + error to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCo", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCo")
                                    .put("urlTts", ttsUrl)
                                    .put("input", objectMapper.writeValueAsString(payload))
                                    .put("response", httpEx.getMessage())
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        }
                    }

                } catch (Exception ex) {
                    // Ignore external API failure so it doesn't fail the main transaction
                    loggingManaged.error("[ChamSocKhachHangController] - [SaveCSSAPI Error] " + ex.getMessage(), ex)
                }
            } else {
                try {
                    int syncStatus = -1;
                    String syncResponse = "";
                    List data = []
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.sp_lay_thongtin_dongphieu_suco_tts(?,?,?) } ", [phanVungId, requestBody.suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                            data = resultSetToObject.getDynamicData(rs)
                        }
                    }
                    loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Response: ", new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("dataTTS", new JsonBuilder(data).toPrettyString())
                            .build())
                    if (!data.isEmpty()) {
                        for (def item : data) {
                            try {
                                Map<String, String> body = new HashMap<>();
                                body.put("data", item.get("json_input").toString());
                                String resp = httpManaged.post(["url": "http://tichhop/tts/saveCSSAPI"], ["Content-Type": "application/json"], objectMapper.writeValueAsString(body));
                                Map res = objectMapper.readValue(resp, Map.class);
                                if (res.get("errorCode").toString() == "0") {
                                    syncStatus = 1;
                                    syncResponse = objectMapper.writeValueAsString(res.get("data"));
                                } else {
                                    syncResponse = res.get("faultString").toString();
                                }
                            } catch (Exception ex) {
                                syncResponse = ex.getMessage();
                            }

                            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                sql.call("{ call QLSC.sp_capnhat_trangthai_log_tts_v2(?,?,?,?,?,?) }", [
                                        phanVungId, item.get("parent_id"), item.get("css_id"), syncStatus, item.get("json_input"), syncResponse])
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [khoaPhieuSuCoV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @Override
    ResponseEntity<BaseResponse<Void>> khoaPhieuSuCoV5(KhoaPhieuSuCoV3Request requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("requestId", requestId)
                    .put("input", new JsonBuilder(requestBody).toPrettyString())
                    .put("action", "/cskh/khoaPhieuSuCoV3")
                    .put("username", session_.get(USERNAME))
                    .put("identifyCode", session_.get(IDENTIFY))
                    .build())
            // Log request to Telegram
            logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "Request: " + (requestBody != null ? objectMapper.writeValueAsString(requestBody) : ""))
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)

            java.util.Date now = Date.parse(Constant.FORMAT_DATETIME, GET_SYSDATE(dataSrc, phanVungId))
            String ngayTh = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(requestBody.ngayTh ?: now)
            String sqlString = "{ call QLSC.PKG_NGHIEPVU.SP_KHOAPHIEU_V2(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.suCoId, ngayTh, requestBody.nhanVienThId,
                                     requestBody.dsNguyenNhan, requestBody.ghiChuTh, Long.parseLong(nhanVienId),
                                     requestBody.khoaPhieu, nguoiCn,
                                     mayCn, ipCn, Long.parseLong(phanVungId),
                                     requestBody.anhTruoc,
                                     requestBody.anhSau,
                                     requestBody.bienBan]) {

                }
            }

            String netMaQt = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call("{ call qlsc.PKG_SAVECSSAPI.LAY_MA_QT_BY_SUCO_ID(?,?,?) }", [requestBody.suCoId, phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        netMaQt = rs.getString("NET_MA_QT")
                    }
                }
            }


            if (["QT1.6.1", "QT1.4", "QTXLSC_ACCESS", "QTXLSC_MANE", "C3_CLM_03"].contains(netMaQt)) {
                // Call external API after nghiemThuSuCoService.khoaPhieu
                try {

                    // Get ma_tinh and token from context
                    String maTinh = session_.get(GenericApi.MA_TINH) != null ? session_.get(GenericApi.MA_TINH).toString() : ""
                    String token = session_.get(GenericApi.TOKEN) != null ? session_.get(GenericApi.TOKEN).toString() : ""

                    // Get extra data from stored procedure
                    Map<String, Object> spData = null
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                            List rsData = resultSetToObject.getDynamicData(rs)
                            if (rsData != null && !rsData.isEmpty()) {
                                spData = rsData[0]
                            }
                        }
                    }

                    String maSc = spData?.get("ma_sc") != null ? String.valueOf(spData?.get("ma_sc")) : ""
                    String phieuId = spData?.get("phieu_id") != null ? String.valueOf(spData?.get("phieu_id")) : ""
                    String noiDungXuLy = spData?.get("noi_dung_xu_ly") != null ? String.valueOf(spData?.get("noi_dung_xu_ly")) : ""
                    String mucDoId = spData?.get("mucdo_id") != null ? String.valueOf(spData?.get("mucdo_id")) : ""
                    String maQt = spData?.get("ma_qt") != null ? String.valueOf(spData?.get("ma_qt")) : ""
                    String maBuoc = spData?.get("ma_buoc") != null ? String.valueOf(spData?.get("ma_buoc")) : ""
                    String donViGiaoId = spData?.get("donvi_giao_id") != null ? String.valueOf(spData?.get("donvi_giao_id")) : ""
                    String donViNhanId = spData?.get("donvi_nhan_id") != null ? String.valueOf(spData?.get("donvi_nhan_id")) : ""

                    if ("C3_CLM_03".equals(maQt)) {
                        Map<String, Object> gwOssData = null
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(" { call qlsc.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_GW_OSS(?,?,?) } ", [maSc, maTinh, CURSOR_PARAMETER]) { ResultSet rs ->
                                List rsData = resultSetToObject.getDynamicData(rs)
                                if (rsData != null && !rsData.isEmpty()) {
                                    gwOssData = rsData[0]
                                }
                            }
                        }

                        Map<String, Object> payload = new HashMap<>()
                        payload.put("loaiphieu_id", "10")
                        payload.put("tts_parent_id", maSc)

                        if (gwOssData != null) {
                            payload.put("id_tts", gwOssData.get("id_tts") != null ? String.valueOf(gwOssData.get("id_tts")) : "")
                            payload.put("noi_dung_xu_ly", gwOssData.get("noi_dung_xu_ly") != null ? String.valueOf(gwOssData.get("noi_dung_xu_ly")) : "")
                            payload.put("ma_nhanvien_xuly", gwOssData.get("ma_nhanvien_xuly") != null ? String.valueOf(gwOssData.get("ma_nhanvien_xuly")) : "")
                            payload.put("ma_donvi_xuly_hrm", gwOssData.get("ma_donvi_xuly_hrm") != null ? String.valueOf(gwOssData.get("ma_donvi_xuly_hrm")) : "")
                        } else {
                            payload.put("id_tts", "")
                            payload.put("noi_dung_xu_ly", "")
                            payload.put("ma_nhanvien_xuly", "")
                            payload.put("ma_donvi_xuly_hrm", "")
                        }

                        String apiUrl = tichhopUrl + "/fms_alarms/sync_via_gw_oss"

                        Long callStartTime = null
                        Long executionTime = null
                        try {
                            Map<String, String> headers = ["Content-Type": "application/json"]
                            if (token != null && !token.isEmpty()) {
                                headers.put("Authorization", token)
                            }
                            String payloadStr = objectMapper.writeValueAsString(payload)
                            callStartTime = System.currentTimeMillis()
                            String resp = httpManaged.post(["url": apiUrl], headers, payloadStr)
                            executionTime = System.currentTimeMillis() - callStartTime
                            // Log sync_via_gw_oss payload + response to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCoV3")
                                    .put("urlTts", apiUrl)
                                    .put("input", payloadStr)
                                    .put("response", resp)
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        } catch (Exception httpEx) {
                            if (callStartTime != null) {
                                executionTime = System.currentTimeMillis() - callStartTime
                            }
                            // Log sync_via_gw_oss payload + error to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "sync_via_gw_oss\nURL: " + apiUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCoV3")
                                    .put("urlTts", apiUrl)
                                    .put("input", objectMapper.writeValueAsString(payload))
                                    .put("response", httpEx.getMessage())
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        }
                    } else {
                        Map<String, Object> thongTinDongPhieu = null
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(" { call QLSC.PKG_SAVECSSAPI.LAY_THONGTIN_DONGPHIEU_BY_SUCO_ID(?,?,?) } ", [requestBody.suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                                List rsData = resultSetToObject.getDynamicData(rs)
                                if (rsData != null && !rsData.isEmpty()) {
                                    thongTinDongPhieu = rsData[0]
                                }
                            }
                        }
                        Map<String, Object> payload = new HashMap<>()
                        payload.put("id_tts", thongTinDongPhieu?.get("id_tts") != null ? String.valueOf(thongTinDongPhieu?.get("id_tts")) : "0")
                        payload.put("id_css", null)
                        payload.put("loaiphieu_id", "10")
                        payload.put("parent_id", thongTinDongPhieu?.get("parent_id") != null ? String.valueOf(thongTinDongPhieu?.get("parent_id")) : maSc)
                        payload.put("ma_tinh", thongTinDongPhieu?.get("ma_tinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_tinh")) : maTinh)
                        payload.put("mo_ta", thongTinDongPhieu?.get("mo_ta") != null ? String.valueOf(thongTinDongPhieu?.get("mo_ta")) : noiDungXuLy)
                        payload.put("tg_batdau", thongTinDongPhieu?.get("tg_batdau") != null ? String.valueOf(thongTinDongPhieu?.get("tg_batdau")) : "")
                        payload.put("tg_dukien_xyly", thongTinDongPhieu?.get("tg_dukien_xyly") != null ? String.valueOf(thongTinDongPhieu?.get("tg_dukien_xyly")) : "")
                        payload.put("tg_ketthuc", thongTinDongPhieu?.get("tg_ketthuc") != null ? String.valueOf(thongTinDongPhieu?.get("tg_ketthuc")) : "")
                        payload.put("anhhuong_dvu", thongTinDongPhieu?.get("anhhuong_dvu") != null ? String.valueOf(thongTinDongPhieu?.get("anhhuong_dvu")) : "0")
                        payload.put("chitiet_anhhuong", thongTinDongPhieu?.get("chitiet_anhhuong") != null ? String.valueOf(thongTinDongPhieu?.get("chitiet_anhhuong")) : "")
                        payload.put("donvi_chuquan", thongTinDongPhieu?.get("donvi_chuquan") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_chuquan")) : "")
                        payload.put("dai_vt", thongTinDongPhieu?.get("dai_vt") != null ? String.valueOf(thongTinDongPhieu?.get("dai_vt")) : "")
                        payload.put("mucdo_id", thongTinDongPhieu?.get("mucdo_id") != null ? String.valueOf(thongTinDongPhieu?.get("mucdo_id")) : mucDoId)
                        payload.put("loai_suco", thongTinDongPhieu?.get("loai_suco") != null ? String.valueOf(thongTinDongPhieu?.get("loai_suco")) : "0")
                        payload.put("hethong", thongTinDongPhieu?.get("hethong") != null ? String.valueOf(thongTinDongPhieu?.get("hethong")) : "0")
                        payload.put("thietbi_id", thongTinDongPhieu?.get("thietbi_id") != null ? String.valueOf(thongTinDongPhieu?.get("thietbi_id")) : "0")
                        payload.put("ma_thietbi", thongTinDongPhieu?.get("ma_thietbi") != null ? String.valueOf(thongTinDongPhieu?.get("ma_thietbi")) : "")
                        payload.put("nguon_sc", thongTinDongPhieu?.get("nguon_sc") != null ? String.valueOf(thongTinDongPhieu?.get("nguon_sc")) : "")
                        payload.put("thongtin_chitiet", thongTinDongPhieu?.get("thongtin_chitiet") != null ? String.valueOf(thongTinDongPhieu?.get("thongtin_chitiet")) : noiDungXuLy)
                        payload.put("nguyen_nhan", thongTinDongPhieu?.get("NGUYEN_NHAN") != null ? String.valueOf(thongTinDongPhieu?.get("NGUYEN_NHAN")) : "")
                        payload.put("giai_phap", thongTinDongPhieu?.get("GIAI_PHAP") != null ? String.valueOf(thongTinDongPhieu?.get("GIAI_PHAP")) : "")
                        payload.put("bienphap_phongngua", thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA") != null ? String.valueOf(thongTinDongPhieu?.get("BIENPHAP_PHONGNGUA")) : "")
                        payload.put("thamso_thaydoi", thongTinDongPhieu?.get("thamso_thaydoi") != null ? String.valueOf(thongTinDongPhieu?.get("thamso_thaydoi")) : "")
                        payload.put("trangthai_xly", thongTinDongPhieu?.get("trangthai_xly") != null ? String.valueOf(thongTinDongPhieu?.get("trangthai_xly")) : "1")
                        payload.put("ma_quytrinh", thongTinDongPhieu?.get("ma_quytrinh") != null ? String.valueOf(thongTinDongPhieu?.get("ma_quytrinh")) : maQt)
                        payload.put("ma_buoc", thongTinDongPhieu?.get("ma_buoc") != null ? String.valueOf(thongTinDongPhieu?.get("ma_buoc")) : maBuoc)
                        payload.put("ma_nhanvien_xuly", thongTinDongPhieu?.get("ma_nhanvien_xuly") != null ? String.valueOf(thongTinDongPhieu?.get("ma_nhanvien_xuly")) : "")
                        payload.put("donvi_nhan_id", thongTinDongPhieu?.get("donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("donvi_nhan_id")) : donViNhanId)
                        payload.put("ma_donvi_nhan_id", thongTinDongPhieu?.get("ma_donvi_nhan_id") != null ? String.valueOf(thongTinDongPhieu?.get("ma_donvi_nhan_id")) : "")
                        payload.put("donvi_giao_id", donViGiaoId)
                        payload.put("css_parent_id", "0")
                        payload.put("css_id", phieuId)
                        payload.put("donvi_xuly_id", "0")
                        payload.put("loaidoituong", "")
                        payload.put("phuongan_dexuat", java.util.Collections.emptyList())


                        Long callStartTime = null
                        Long executionTime = null
                        try {
                            String payloadStr = objectMapper.writeValueAsString(payload)
                            callStartTime = System.currentTimeMillis()
                            String resp = httpManaged.post(["url": ttsUrl], ["Content-Type": "application/json"], payloadStr)
                            executionTime = System.currentTimeMillis() - callStartTime
                            // Log SaveCSSAPI payload + response to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + payloadStr + "\nResponse: " + resp)
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCoV3")
                                    .put("urlTts", ttsUrl)
                                    .put("input", payloadStr)
                                    .put("response", resp)
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        } catch (Exception httpEx) {
                            if (callStartTime != null) {
                                executionTime = System.currentTimeMillis() - callStartTime
                            }
                            // Log SaveCSSAPI payload + error to Telegram
                            logTelegramService.sendMessage("/cskh/khoaPhieuSuCoV3", "SaveCSSAPI\nURL: " + ttsUrl + "\nPayload: " + objectMapper.writeValueAsString(payload) + "\nError: " + httpEx.getMessage())
                            loggingManaged.info("Dong bo sang TTS", new ImmutableMap.Builder<String, Object>()
                                    .put("action", "/cskh/khoaPhieuSuCoV3")
                                    .put("urlTts", ttsUrl)
                                    .put("input", objectMapper.writeValueAsString(payload))
                                    .put("response", httpEx.getMessage())
                                    .put("requestId", requestId)
                                    .put("username", session_.get(USERNAME))
                                    .put("identifyCode", session_.get(IDENTIFY))
                                    .put("executionTime", executionTime)
                                    .build())
                        }
                    }

                } catch (Exception ex) {
                    // Ignore external API failure so it doesn't fail the main transaction
                    loggingManaged.error("[ChamSocKhachHangController] - [SaveCSSAPI Error] " + ex.getMessage(), ex)
                }
            } else {
                try {
                    int syncStatus = -1;
                    String syncResponse = "";
                    String nguyenNhanText = ""
                    if (requestBody.nguyenNhanId != null) {
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call("{ call QLSC.PKG_NGHIEPVU.sp_get_nguyennhan(?,?,?) }", [Long.parseLong(phanVungId), requestBody.nguyenNhanId, CURSOR_PARAMETER]) { ResultSet rs ->
                                while (rs != null && rs.next()) {
                                    String nguyenNhan = rs.getString("nguyennhan")
                                    if ((nguyenNhan != null && !nguyenNhan.trim().isEmpty()) && (nguyenNhanText == null || nguyenNhanText.isEmpty())) {
                                        nguyenNhanText = nguyenNhan
                                    }
                                }
                            }
                        }
                    }
                    List data = []
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(" { call QLSC.sp_lay_thongtin_dongphieu_suco_tts(?,?,?) } ", [phanVungId, requestBody.suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                            data = resultSetToObject.getDynamicData(rs)
                        }
                    }
                    loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Response: ", new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("dataTTS", new JsonBuilder(data).toPrettyString())
                            .build())
                    if (!data.isEmpty()) {
                        for (def item : data) {
                            try {
                                Map jsonMap = objectMapper.readValue(item.get("json_input").toString(), Map.class)

                                if (requestBody.nhomNguyenNhanId == 8L) {
                                    jsonMap.put("noi_dung_xu_ly", requestBody.ghiChuTh ?: "")
                                } else if (requestBody.nhomNguyenNhanId == 9L) {
                                    jsonMap.put("noi_dung_xu_ly", "")
                                    jsonMap.put("bp_kiem_soat_bu", nguyenNhanText ?: "")
                                }
                                Map<String, String> body = new HashMap<>();
                                body.put("data", objectMapper.writeValueAsString(jsonMap)); // dùng jsonMap đã map
                                String resp = httpManaged.post(["url": "http://tichhop/tts/saveCSSAPI"], ["Content-Type": "application/json"], objectMapper.writeValueAsString(body));
                                Map res = objectMapper.readValue(resp, Map.class);
                                if (res.get("errorCode").toString() == "0") {
                                    syncStatus = 1;
                                    syncResponse = objectMapper.writeValueAsString(res.get("data"));
                                } else {
                                    syncResponse = res.get("faultString").toString();
                                }
                            } catch (Exception ex) {
                                syncResponse = ex.getMessage();
                            }

                            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                sql.call("{ call QLSC.sp_capnhat_trangthai_log_tts_v2(?,?,?,?,?,?) }", [
                                        phanVungId, item.get("parent_id"), item.get("css_id"), syncStatus, item.get("json_input"), syncResponse])
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            loggingManaged.info("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", MessageUtils.SUCCESS)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ChamSocKhachHangController] - [khoaPhieuSuCoV3] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

}