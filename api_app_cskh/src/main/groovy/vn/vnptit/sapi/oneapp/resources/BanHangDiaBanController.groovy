package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonBuilder
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.BanHangDiaBanApi
import io.swagger.model.dto.*
import io.swagger.model.request.ThemCapNhatDotBHRequest
import io.swagger.model.request.ThemCapNhatNhomBHRequest
import io.swagger.model.request.ThemKhuVucTacChienRequest
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.response.DsAnhNhomBHResponse
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils

import javax.servlet.http.HttpServletRequest
import java.sql.Date
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class BanHangDiaBanController implements BanHangDiaBanApi {

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
    ResultSetToObject resultSetToObject

    @Autowired
    DataUtils dataUtils

    @Value("\${spring.object-storages.minio-storage.bucket}")
    String bucketName

    @Autowired
    BanHangDiaBanController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<Object>> layThongTinDanhMuc(String param, Integer kieu) {
        List<Object> response = []
        String resStr = ""
        String requestId = genericApi.generateRequestId()
        try {
            if (param == null || kieu == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [layThongTinDanhMuc] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("param", param)
                    .put("kieu", kieu)
                    .build())

            //param: 0 - kieu: 0 - (Lấy danh sách hình thức)
            //param: nhanvien_id - kieu: 3 - (Lấy danh sách Đợt bán hàng)
            //param: 0 - kieu: 4 - (Lấy danh sách các khu vực tác chiến)
            //param: dotbh_id - kieu: 5 - (Lấy danh sách Khu vực tác chiến theo dotbh_id)
            //param: 0 - kieu: 6 - (Lấy danh sách loại nhóm bán hàng)
            //param: dotbh_id - kieu: 10 - (Lấy danh sách nhóm bán hàng theo dotbh_id)
            //param: nhombh_id - kieu: 14 - (Lấy thông tin đội trưởng nhóm bán hàng theo nhombh_id)
            //param: nhombh_id - kieu: 15 - (Lấy Danh sách khu vực nhóm bán hàng nhombh_id)
            //param: nhombh_id - kieu: 16 - (Lấy Danh sách thành viên theo nhombh_id)
            //param: dotbh_id - kieu: 17 - (Lấy danh sách khu vực tác chiến mặc định theo đợt bán hàng)
            //param: 0 - kieu: 18 - (Lấy danh sách đơn vị tree)
            //param: dotbh_id - kieu: 21 - (Lấy danh sách thuê bao bảo dưỡng theo dotbh_id)
            //param: 0 - kieu: 25 - (Lấy danh sách chỉ tiêu bán hàng và cskh)
            //param: nhombh_id - kieu: 27 - (Lấy Danh sách Chỉ tiêu nhóm bán hàng theo nhombh_id)
            //param: dotbh_id - kieu: 28 - (Lấy danh sách chỉ tiêu bán hàng theo đợt bán hàng)
            //param: dotbh_id - kieu: 99 - (Lấy tên nhóm mặc định)

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.sp_danhmuc_banhang_smp(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), param,
                                     kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        switch (kieu) {
                            case 0://Lấy danh sách hình thức bán hàng
                                response.add(new DanhMuc(
                                        ID: rs.getLong("HTBH_ID"),
                                        NAME: rs.getString("A")))
                                break
                            case 3: //Lấy danh sách Đợt bán hàng
                                response.add(new DotBanHang().convertDataFromDatabase(rs))
                                break
                            case 4://Lấy danh sách các khu vực tác chiến
                                response.add(new KhuVucTacChien().convertDataFromDatabase(rs))
                                break
                            case 5: //Lấy danh sách Khu vực tác chiến theo dotbh_id
                                response.add(new KhuVucTacChienDotBH().convertDataFromDatabase(rs))
                                break
                            case 6://Lấy danh sách loại nhóm bán hàng
                                response.add(new LoaiNhomBH().convertDataFromDatabase(rs))
                                break
                            case 10://Lấy danh sách nhóm bán hàng theo dotbh_id
                                response.add(new NhomBanHang().convertDataFromDatabase(rs))
                                break
                            case 14:
                                response.add(resultSetToObject.getObjectDynamicData(rs))
                                break
                            case 15:
                                response.add(resultSetToObject.getObjectDynamicData(rs))
                                break
                            case 16:
                                response.add(resultSetToObject.getObjectDynamicData(rs))
                                break
                            case 17://Lấy danh sách khu vực tác chiến mặc định theo đợt bán hàng
                                response.add(new KhuVucTacChienDotBH().convertDataFromDatabase(rs))
                                break
                            case 18://Lấy danh sách đơn vị tree
                                response.add(new DonViTree().convertDataFromDatabase(rs))
                                break
                            case 21://Lấy danh sách thuê bao bảo dưỡng theo dotbh_id
                                response.add(new ThueBaoBaoDuongDotBH().convertDataFromDatabase(rs))
                                break
                            case 25://Lấy danh sách chỉ tiêu bán hàng và cskh
                                response.add(new DanhMuc(
                                        ID: rs.getLong("chitieu_id"),
                                        NAME: rs.getString("chitieu_cd")))
                                break
                            case 27:
                                response.add(resultSetToObject.getObjectDynamicData(rs))
                                break
                            case 28://Lấy danh sách chỉ tiêu bán hàng theo đợt bán hàng
                                response.add(new ChiTieuBH().convertDataFromDatabase(rs))
                                break
                            case 99://Lấy tên nhóm mặc định
                                resStr = rs.getString("TENNHOM")
                                break
                        }
                    }
                }
            }
            String result = ""
            if (response != null && response.size() > 0) {
                result = new JsonBuilder(response).toPrettyString()
            } else if (resStr != null && !resStr.isEmpty()) {
                result = resStr
            }

            loggingManaged.info("[BanHangDiaBanController] - [layThongTinDanhMuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", result)
                            .build())
            if ((response != null && response.size() > 0) || kieu != 99) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(result))
            }
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [layThongTinDanhMuc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ThoiGianKhuVuc>> layThoiGianTheoDotChienDich(Long dotBhId) {
        String requestId = genericApi.generateRequestId()
        ThoiGianKhuVuc response
        try {
            if (dotBhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [layThoiGianTheoDotChienDich] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("dotBhId", dotBhId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.sp_danhmuc_banhang_smp(?,?,?,?) }"
            // Lấy thời gian bắt đầu, kết thúc theo mặc định theo đợt chiến dịch
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), dotBhId,
                                     7, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        response = new ThoiGianKhuVuc().convertDataFromDatabase(rs)
                    }
                }
            }
            loggingManaged.info("[BanHangDiaBanController] - [layThoiGianTheoDotChienDich] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [layThoiGianTheoDotChienDich] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> themKhuVucTacChien(ThemKhuVucTacChienRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[BanHangDiaBanController] - [themKhuVucTacChien] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())

            Date ngayBd
            if (requestBody.ngayBd != null && !requestBody.ngayBd.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayBd)
                    ngayBd = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày bắt đầu không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }

            Date ngayKt
            if (requestBody.ngayKt != null && !requestBody.ngayKt.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayKt)
                    ngayKt = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày kết thúc không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }

            if (ngayBd != null && ngayKt != null && ngayBd.getTime() > ngayKt.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Ngày bắt đầu phải trước ngày kết thúc", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ ? = call css.fn_insert_diaban_dotbh_smp(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.khuVucId,
                                     requestBody.dotBhId, ngayBd, ngayKt]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[BanHangDiaBanController] - [themKhuVucTacChien] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())
            if (response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [themKhuVucTacChien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> themCapNhatDotBanHang(ThemCapNhatDotBHRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[BanHangDiaBanController] - [themCapNhatDotBanHang] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())

            Date ngayBd
            if (requestBody.ngayBd != null && !requestBody.ngayBd.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayBd)
                    ngayBd = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày bắt đầu không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }

            Date ngayKt
            if (requestBody.ngayKt != null && !requestBody.ngayKt.isEmpty()) {
                try {
                    java.util.Date date = Date.parse(Constant.FORMAT_DATETIME, requestBody.ngayKt)
                    ngayKt = new Date(date.getTime())
                } catch (Exception e) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thông tin ngày kết thúc không đúng định dạng(${Constant.FORMAT_DATETIME})", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }

            if (ngayBd != null && ngayKt != null && ngayBd.getTime() > ngayKt.getTime()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Ngày bắt đầu phải trước ngày kết thúc", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String dsDiaBan
            if (requestBody.dsDiaBan != null) {
                dsDiaBan  = new JsonBuilder(requestBody.dsDiaBan)
            }
            String dsChiTieu
            if (requestBody.dsChiTieu != null) {
                dsChiTieu = new JsonBuilder(requestBody.dsChiTieu)
            }
            String dsBaoDuong
            if (requestBody.dsBaoDuong != null) {
                dsBaoDuong = new JsonBuilder(requestBody.dsBaoDuong)
            }

            String sqlString = "{ ? = call css.fn_capnhat_dotbh_smp(?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.dotBhId,
                                     requestBody.tenDotBh, requestBody.htbhId, requestBody.noiDung,
                                     ngayBd, ngayKt, nguoiCn, ipCn,
                                     Long.parseLong(nhanVienId), dsChiTieu,
                                     dsDiaBan, dsBaoDuong]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[BanHangDiaBanController] - [themCapNhatDotBanHang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())
            if (response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [themCapNhatDotBanHang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDanhMucDoiTruong(Long dotBhId) {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            if (dotBhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [layDanhMucDoiTruong] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("dotBhId", dotBhId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String sqlString = "{ call css.sp_lay_ds_doitruong_bh_smp(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId),
                                     Long.parseLong(donViId), dotBhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("nhanvien_id"),
                                NAME: rs.getString("ten_nv")
                        ))
                    }
                }
            }
            loggingManaged.info("[BanHangDiaBanController] - [layDanhMucDoiTruong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [layDanhMucDoiTruong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhanVienBH>>> layDsNhanVien(Long donViId, Long dotBhId) {
        String requestId = genericApi.generateRequestId()
        List<NhanVienBH> response = []
        try {
            if (donViId == null || dotBhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [layDsNhanVien] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("donViId", donViId)
                    .put("dotBhId", dotBhId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.sp_lay_ds_nhanvien_banhang_smp(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), donViId,
                                     dotBhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new NhanVienBH().convertDataFromDatabase(rs))
                    }
                }
            }
            loggingManaged.info("[BanHangDiaBanController] - [layDsNhanVien] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null && !response.isEmpty() ? new JsonBuilder(response).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [layDsNhanVien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> themCapNhatNhomBanHang(ThemCapNhatNhomBHRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[BanHangDiaBanController] - [themCapNhatNhomBanHang] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String dsDiaBan
            if (requestBody.dsDiaBan != null) {
                dsDiaBan  = new JsonBuilder(requestBody.dsDiaBan)
            }
            String dsChiTieu
            if (requestBody.dsChiTieu != null) {
                dsChiTieu = new JsonBuilder(requestBody.dsChiTieu)
            }
            String dsNhanVien
            if (requestBody.dsNhanVien != null) {
                dsNhanVien = new JsonBuilder(requestBody.dsNhanVien)
            }
            String sqlString = "{ ? = call css.fn_insert_nhombh_smp(?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.nhomBhId,
                                     requestBody.nhomBh, requestBody.loaiNhomId, requestBody.dotBhId,
                                     nguoiCn, ipCn, requestBody.diaDiem,
                                     requestBody.doiTruongId, dsChiTieu,
                                     dsDiaBan, dsNhanVien]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[BanHangDiaBanController] - [themCapNhatNhomBanHang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())
            if (response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [themCapNhatNhomBanHang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaDotBanHang(Long dotBhId) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            if (dotBhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [xoaDotBanHang] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("dotBhId", dotBhId)
                    .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ ? = call css.fn_xoa_dotbh_smp(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId),
                                     dotBhId, Long.parseLong(nhanVienId)]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[BanHangDiaBanController] - [xoaDotBanHang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())

            if (response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [xoaDotBanHang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaNhomBanHang(Long nhomBhId) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            if (nhomBhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [xoaNhomBanHang] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("nhomBhId", nhomBhId)
                    .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ ? = call css.fn_xoa_nhombh_smp(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId),
                                     nhomBhId, Long.parseLong(nhanVienId)]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[BanHangDiaBanController] - [xoaNhomBanHang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())

            if (response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [xoaNhomBanHang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThueBaoBaoDuong>>> layDsThueBaoBaoDuong() {
        String requestId = genericApi.generateRequestId()
        List<ThueBaoBaoDuong> response = []
        try {
            loggingManaged.info("[BanHangDiaBanController] - [layDsThueBaoBaoDuong] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call baohong.sp_lay_ds_baoduong_cskh_smp(?,?,?) }"
//            Gson gson = new Gson()
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new ThueBaoBaoDuong().convertDataFromDatabase(rs))
                    }
                }
            }
//            String result = ""
//            if (response != null && response.size() > 0) {
//                result = gson.toJson(response)
//            }
            loggingManaged.info("[BanHangDiaBanController] - [layDsThueBaoBaoDuong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [layDsThueBaoBaoDuong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DsAnhNhomBHResponse>>> layDsAnhNhomBH(Long nhomBhId) {
        String requestId = genericApi.generateRequestId()
        List<DsAnhNhomBHResponse> response = []
        try {
            if (nhomBhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[BanHangDiaBanController] - [layDsAnhNhomBH] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("nhomBhId", nhomBhId)
                    .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            dataUtils.init(phanVungId, dataSrc)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.LAY_ANHCHUP_NBH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), nhomBhId, null, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getDanhSach(rs, DsAnhNhomBHResponse.class)
                    }
                }
            }
            if (response != null && response?.size() > 0) {
                for (DsAnhNhomBHResponse item in response) {
                    if (item?.mota != null && !item?.mota?.isEmpty()) {
                        item.listImage = dataUtils.getFileUrls(item?.mota, bucketName)
                    }
                }
            }
            loggingManaged.info("[BanHangDiaBanController] - [layDsAnhNhomBH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response, requestId))
        } catch (Exception e) {
            loggingManaged.error("[BanHangDiaBanController] - [layDsAnhNhomBH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
