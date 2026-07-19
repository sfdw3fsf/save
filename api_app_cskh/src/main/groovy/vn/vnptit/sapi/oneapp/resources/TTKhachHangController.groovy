package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.TTKhachHangApi
import io.swagger.model.dto.*
import io.swagger.model.request.CapNhatLienHeRequest
import io.swagger.model.request.TTKH_CapNhatRequest
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.TTKH_DanhMucChucNang
import io.swagger.model.response.TTKH_VatTuThueBao
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException

@RestController
class TTKhachHangController implements TTKhachHangApi {

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
    TTKhachHangController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<ThueBaoKH>> thongTinTB(String maTb) {
        ThueBaoKH ret = null
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [thongTinTB] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("maTb", maTb)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI.LAY_THONGTIN_THUEBAO(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), maTb, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret = new ThueBaoKH(
                                maTb: rs.getString("MA_TB"),
                                thueBaoId: rs.getLong("THUEBAO_ID"),
                                thanhToanId: rs.getLong("THANHTOAN_ID"),
                                khachHangId: rs.getLong("KHACHHANG_ID")
                        )
                    }
                }
            }
            loggingManaged.info("[TTKhachHangController] - [thongTinTB] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [thongTinTB] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<LienHeKH>>> dsLienHe(Long kieu, Long id, Long all) {
        List<LienHeKH> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [dsLienHe] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("kieu", kieu)
                    .put("id", id)
                    .put("all", all)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String sqlString = "{ call css.pkg_thongtin_lienhe.lay_ds_thongtin_lienhe(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), kieu, id, all, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new LienHeKH(
                                ttlhId: rs.getString("ttlh_id"),
                                tenLh: rs.getString("ten_lh"),
                                diaChi: rs.getString("diachi"),
                                dienThoai: rs.getString("dienthoai"),
                                vaiTroId: rs.getString("doituongcs_id"),
                                tenVaiTro: rs.getString("tendoituongcs"),
                                khachHangId: rs.getString("khachhang_id"),
                                maKh: hasColumn(rs, "ma_kh") ? rs.getString("ma_kh") : "",
                                maTt: hasColumn(rs, "ma_tt") ? rs.getString("ma_tt") : "",
                                maTb: hasColumn(rs, "ma_tb") ? rs.getString("ma_tb") : "",
                                email: rs.getString("email"),
                                mangXh: rs.getString("mang_xh"),
                                ngayCn: rs.getString("ngay_cn"),
                                nguoiCn: rs.getString("nguoi_cn")
                        ))
                    }
                }
            }
            loggingManaged.info("[TTKhachHangController] - [dsLienHe] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [dsLienHe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData()
        int columns = rsmd.getColumnCount()
        for (int x = 1; x <= columns; x++) {
            if (columnName.toLowerCase().equals(rsmd.getColumnName(x).toLowerCase())) {
                return true
            }
        }
        return false
    }

    @Override
    ResponseEntity<BaseResponse<Void>> huyGanLienHe(Long khacHangId, Long ttlhId, Long kieu) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [huyGanLienHe] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("khacHangId", khacHangId)
                    .put("ttlhId", ttlhId)
                    .put("kieu", kieu)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String donViId = session_.get(genericApi.ID_DONVI)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(genericApi.MA_NV)
            String mayCn = session_.get(genericApi.DEVICE_ID)
            String ipCn = session_.get(genericApi.DEVICE_IP)
            String sqlString = "{ call css.pkg_thongtin_lienhe.huygan_lienhe_v2(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), khacHangId, ttlhId, kieu, nhanVienId, donViId, nguoiCn, mayCn, ipCn, Sql.VARCHAR]) { String rs -> ret = rs}
            }
            loggingManaged.info("[TTKhachHangController] - [huyGanLienHe] - Response: ", new ImmutableMap.Builder<String, Object>().put("result", ret).build())
            if (ret == "OK")
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, null, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [huyGanLienHe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaLienHe(Long ttlhId) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [xoaLienHe] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("ttlhId", ttlhId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String donViId = session_.get(genericApi.ID_DONVI)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(genericApi.MA_NV)
            String mayCn = session_.get(genericApi.DEVICE_ID)
            String ipCn = session_.get(genericApi.DEVICE_IP)
            String sqlString = "{ call css.pkg_thongtin_lienhe.xoa_lienhe_v2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), ttlhId, nhanVienId, donViId, nguoiCn, mayCn, ipCn, Sql.VARCHAR]) { String rs -> ret = rs}
            }
            loggingManaged.info("[TTKhachHangController] - [xoaLienHe] - Response: ", new ImmutableMap.Builder<String, Object>().put("result", ret).build())
            if (ret == "OK")
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, null, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [xoaLienHe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsChung(Long kieu, Long id) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [dsVaiTro] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String sqlString = "{ call css.pkg_thongtin_lienhe.lay_thongtin_lienhe_capnhat(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), kieu, id, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        if (kieu == 4) // Danh sách vai trò
                            ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                        else if (kieu == 1) // Danh sách khách hàng
                            ret.add(new DanhMuc(ID: rs.getLong("KHACHHANG_ID"), NAME: rs.getString("MA_KH")))
                        else if (kieu == 2) // Danh sách thanh toán
                            ret.add(new DanhMuc(ID: rs.getLong("THANHTOAN_ID"), NAME: rs.getString("MA_TT")))
                        else if (kieu == 3) // Danh sách thuê bao
                            ret.add(new DanhMuc(ID: rs.getLong("THUEBAO_ID"), NAME: rs.getString("MA_TB")))
                    }
                }
            }
            loggingManaged.info("[TTKhachHangController] - [dsVaiTro] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [dsVaiTro] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> danhMuc(Long kieu) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [danhMuc] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String sqlString = "{ CALL css.pkg_thongtin_lienhe.lay_danhmuc_ttlh(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                    }
                }
            }
            loggingManaged.info("[TTKhachHangController] - [danhMuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null && ret.size() > 0 ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [danhMuc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> ktLienHe(CapNhatLienHeRequest requestBody) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [ktLienHe] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)

            String sqlString = "{ CALL css.pkg_thongtin_lienhe.kiemtra_trung_sdt(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.ttlhId, requestBody.dsKhachHangId[0], requestBody.dienThoaiGiaTri, Sql.VARCHAR]) {String rs -> ret = rs}
            }
            loggingManaged.info("[TTKhachHangController] - [ktLienHe] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null ? ret.toString() : "").build())
            if (ret.contains("OK")){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            }
            else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ret, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [ktLienHe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatLienHe(CapNhatLienHeRequest requestBody) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [capNhatLienHe] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("requestId", requestId)
                            .put("requestBody", requestBody)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String donViId = session_.get(genericApi.ID_DONVI)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(genericApi.MA_NV)
            String maNguoiDung = session_.get(genericApi.USERNAME)
            String mayCn = session_.get(genericApi.DEVICE_ID)
            String ipCn = session_.get(genericApi.DEVICE_IP)

            String mucDichSdDt = "<MUCDICH>"
            requestBody.dienThoaiDSMucDichId.each {mucDichSdDt += "<giatri><value>${it}</value></giatri>"}
            mucDichSdDt += "</MUCDICH>"

            String mucDichSdMxh = "<MUCDICH>"
            requestBody.facebookDSMucDichId.each {mucDichSdMxh += "<mxh><id>${it}</id><loai>1</loai></mxh>"}
            requestBody.zaloDSMucDichId.each {mucDichSdMxh += "<mxh><id>${it}</id><loai>2</loai></mxh>"}
            mucDichSdMxh += "</MUCDICH>"

            String mucDichSdEmail = "<MUCDICH>"
            requestBody.emailDSMucDichId.each {mucDichSdEmail += "<giatri><value>${it}</value></giatri>"}
            mucDichSdEmail += "</MUCDICH>"

            String khachHang = "<KHACHHANG>"
            if (requestBody.kieu == 1)
                requestBody.dsKhachHangId.each {khachHang += "<giatri><value>${it}</value></giatri>"}
            khachHang += "</KHACHHANG>"

            String thanhToan = "<THANHTOAN>"
            if (requestBody.kieu == 2)
                requestBody.dsThanhToanId.each {thanhToan += "<giatri><value>${it}</value></giatri>"}
            thanhToan += "</THANHTOAN>"

            String thueBao = "<THUEBAO>"
            if (requestBody.kieu == 3)
                requestBody.dsThueBaoId.each {thueBao += "<giatri><value>${it}</value></giatri>"}
            thueBao += "</THUEBAO>"

            String lienHeDt = "<LIENHE><sdt><name>${requestBody.dienThoaiGiaTri}</name><value>1</value></sdt></LIENHE>"

            String lienHeMxh = "<LIENHE><mxh><name>${requestBody.facebookGiaTri}</name><value>1</value></mxh><mxh><name>${requestBody.zaloGiaTri}</name><value>2</value></mxh></LIENHE>"

            String lienHeEmail = "<EMAIL><giatri><value>${requestBody.emailGiaTri}</value></giatri></EMAIL>"

            String sqlString = "{ CALL css.pkg_thongtin_lienhe.capnhat_ttlh_smp_v2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.thueBaoId, requestBody.ttlhId, requestBody.vaiTroId, requestBody.tenLh, requestBody.diaChi,
                                     mucDichSdDt, mucDichSdDt, mucDichSdMxh, mucDichSdEmail, khachHang, thanhToan,
                                     thueBao, lienHeDt, lienHeMxh, lienHeEmail, requestBody.dienThoaiGiaTri, requestBody.emailGiaTri,
                                     nhanVienId, donViId, nguoiCn, mayCn, ipCn, Sql.VARCHAR]) {String rs -> ret = rs}
            }
            loggingManaged.info("[TTKhachHangController] - [capNhatLienHe] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("result", ret != null ? ret.toString() : "").build())
            if (ret.contains("OK-")){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            }
            else{
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ret, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [capNhatLienHe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<LienHeKHChiTiet>> ctLienHe(Long ttlhId) {
        LienHeKHChiTiet ret = new LienHeKHChiTiet()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [ctLienHe] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("ttlhId", ttlhId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String nhanVienId = session_.get(genericApi.ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            ret.ttlhId = ttlhId

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                //6.1. Lấy thông tin địa chỉ, tên liên hệ, vai trò
                String sqlString = "{ call css.pkg_thongtin_lienhe.lay_thongtin_theo_ttlh(?,?,?,?) }"
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.vaiTroId = rs.getLong("DOITUONGCS_ID")
                        ret.tenVaiTro = rs.getString("TENDOITUONGCS")
                        ret.tenLh = rs.getString("TEN_LH")
                        ret.diaChi = rs.getString("DIACHI")
                    }
                }
                //6.2. Lấy thông tin xem liên hệ sử dụng cho những đối tượng nào
                sql.call(sqlString, [Long.parseLong(phanVungId), 3, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        if (rs.getString("TTKH_ID") == "1"){
                            ret.dsKhachHang.add(new DanhMuc(ID: rs.getLong("DB_ID"), NAME: rs.getString("MA")))
                        }
                        if (rs.getString("TTKH_ID") == "2"){
                            ret.dsThanhToan.add(new DanhMuc(ID: rs.getLong("DB_ID"), NAME: rs.getString("MA")))
                        }
                        if (rs.getString("TTKH_ID") == "3"){
                            ret.dsThueBao.add(new DanhMuc(ID: rs.getLong("DB_ID"), NAME: rs.getString("MA")))
                        }
                    }
                }
                //6.3. Lấy thông tin số điện thoại
                sql.call(sqlString, [Long.parseLong(phanVungId), 4, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.dienThoaiLoaiId = rs.getLong("LOAILH_ID")
                        ret.dienThoaiTenLoai = rs.getString("LOAI_LH")
                        ret.dienThoaiGiaTri = rs.getString("SO_DT")
                    }
                }
                //6.7 Lấy id mục  đích sử dụng cho số điện thoại
                sql.call(sqlString, [Long.parseLong(phanVungId), 10, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        String md = rs.getString("ID")
                        String tenMD = rs.getString("NAME")
                        if (md != null && md != "")
                        {
                            if (md.contains(',')) md.split(",").eachWithIndex { it, index ->
                                ret.dienThoaiDSMucDich.add(new DanhMuc(ID: Long.parseLong(it), NAME: tenMD.split(",")[index]))
                            }
                            else ret.dienThoaiDSMucDich.add(new DanhMuc(ID: Long.parseLong(md), NAME: tenMD))
                        }
                    }
                }
                //6.4 Lấy thông tin email
                sql.call(sqlString, [Long.parseLong(phanVungId), 5, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.emailGiaTri = rs.getString("EMAIL")
                    }
                }
                //6.6 Lấy id mục  đích sử dụng cho Email
                sql.call(sqlString, [Long.parseLong(phanVungId), 7, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        String md = rs.getString("ID")
                        String tenMD = rs.getString("NAME")
                        if (md != null && md != "")
                        {
                            if (md.contains(',')) md.split(",").eachWithIndex { it, index ->
                                ret.emailDSMucDich.add(new DanhMuc(ID: Long.parseLong(it), NAME: tenMD.split(",")[index]))
                            }
                            else ret.emailDSMucDich.add(new DanhMuc(ID: Long.parseLong(md), NAME: tenMD))
                        }
                    }
                }
                //6.5 Lấy thông tin MXH
                sql.call(sqlString, [Long.parseLong(phanVungId), 9, ttlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        String loaiId = rs.getString("LOAIMXH_ID")
                        String md = rs.getString("MUCDICH")
                        String tenMD = rs.getString("MUCDICH_SD")
                        if (loaiId == "1"){
                            ret.facebookGiaTri = rs.getString("MANG_XH")
                            if (md != null && md != "")
                            {
                                if (md.contains(',')) md.split(",").eachWithIndex { it, index ->
                                    ret.facebookDSMucDich.add(new DanhMuc(ID: Long.parseLong(it), NAME: tenMD.split(",")[index]))
                                }
                                else ret.facebookDSMucDich.add(new DanhMuc(ID: Long.parseLong(md), NAME: tenMD))
                            }
                        }else if (loaiId == "2"){
                            ret.zaloGiaTri = rs.getString("MANG_XH")
                            if (md != null && md != "")
                            {
                                if (md.contains(',')) md.split(",").eachWithIndex { it, index ->
                                    ret.zaloDSMucDich.add(new DanhMuc(ID: Long.parseLong(it), NAME: tenMD.split(",")[index]))
                                }
                                else ret.zaloDSMucDich.add(new DanhMuc(ID: Long.parseLong(md), NAME: tenMD))
                            }
                        }
                    }
                }
            }
            loggingManaged.info("[TTKhachHangController] - [ctLienHe] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [ctLienHe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKH_DanhMucChucNang>> dsChucNang() {
        TTKH_DanhMucChucNang ret = new TTKH_DanhMucChucNang()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [dsChucNang] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String sqlString = "{ CALL css.lay_dm_vattu_bs(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), CURSOR_PARAMETER, CURSOR_PARAMETER, CURSOR_PARAMETER, CURSOR_PARAMETER,
                                     CURSOR_PARAMETER, CURSOR_PARAMETER, CURSOR_PARAMETER, CURSOR_PARAMETER]) {
                    ResultSet rsVatTu, ResultSet rsKho, ResultSet rsLoHang, ResultSet rsKieuTB, ResultSet rsTinhTrang, ResultSet rsTrangBi, ResultSet rsDichVuVT, ResultSet rsDonViTinh ->
                        List<TTKH_VatTu> vatTuList = new ArrayList<>()
                        while (rsVatTu.next()) {
                            vatTuList.add(new TTKH_VatTu(id: Utils.getLongDataByKey(rsVatTu,"vattu_id"), ten: Utils.getStringDataByKey(rsVatTu,"ten_vt"), dvTinhId: Utils.getLongDataByKey(rsVatTu,"dvitinh_id")))
                        }
                        ret.setDsVatTu(vatTuList)

                        List<TTKH_Kho> khos = new ArrayList<>()
                        while (rsKho.next()) {
                            khos.add(new TTKH_Kho(id: rsKho.getLong("kho_id"), ten: rsKho.getString("ten_kho"), donViId: rsKho.getLong("donvi_id")))
                        }
                        ret.setDsKho(khos)

                        List<TTKH_LoHang> loHangs = new ArrayList<>()
                        while (rsLoHang.next()) {
                            loHangs.add(new TTKH_LoHang(loHang: rsLoHang.getString("lohang"), lh: rsLoHang.getString("lh"), donGia: rsLoHang.getString("dongia")))
                        }
                        ret.setDsLoHang(loHangs)

                        List<TTKH_KieuThietBi> kieuThietBis = new ArrayList<>()
                        while (rsKieuTB.next()) {
                            kieuThietBis.add(new TTKH_KieuThietBi(id: rsKieuTB.getLong("kieutbi_id"), ten: rsKieuTB.getString("kieu_tbi")))
                        }
                        ret.setDsKieuTB(kieuThietBis)

                        List<TTKH_TinhTrang> tinhTrangs = new ArrayList<>()
                        while (rsTinhTrang.next()) {
                            tinhTrangs.add(new TTKH_TinhTrang(id: rsTinhTrang.getLong("tinhtrangtbi_id"), ten: rsTinhTrang.getString("tinhtrang_tbi"),
                                    thuTu: rsTinhTrang.getString("thutu"), ghiChu: rsTinhTrang.getString("ghichu")))
                        }
                        ret.setDsTinhTrang(tinhTrangs)

                        List<TTKH_TrangBi> trangBis = new ArrayList<>()
                        while (rsTrangBi.next()) {
                            trangBis.add(new TTKH_TrangBi(id: rsTrangBi.getLong("trangbi_id"), ten: rsTrangBi.getString("tentrangbi"), htMacDinh: rsTrangBi.getString("ht_macdinh")))
                        }
                        ret.setDsTrangBi(trangBis)

                        List<TTKH_DichVuVT> dichVuVTS = new ArrayList<>()
                        while (rsDichVuVT.next()) {
                            dichVuVTS.add(new TTKH_DichVuVT(id: rsDichVuVT.getLong("dichvuvt_id"), ten: rsDichVuVT.getString("ten_dvvt"),
                                    ma: rsDichVuVT.getString("ma_dvvt"), ghiChu: rsDichVuVT.getString("ghichu"), suDung: rsDichVuVT.getString("sudung")))
                        }
                        ret.setDsDichVuVT(dichVuVTS)

                        List<TTKH_DonViTinh> donViTinhs = new ArrayList<>()
                        while (rsDonViTinh.next()) {
                            donViTinhs.add(new TTKH_DonViTinh(id: rsDonViTinh.getLong("dvitinh_id"), ten: rsDonViTinh.getString("dvi_tinh")))
                        }
                        ret.setDsDonViTinh(donViTinhs)
                }
            }
            loggingManaged.info("[TTKhachHangController] - [dsChucNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [dsChucNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKH_VatTuThueBao>> dsVatTuThueBao(Long dichVuVTId, Integer loai, String maTB) {
        TTKH_VatTuThueBao ret = new TTKH_VatTuThueBao()
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [dsVatTuThueBao] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("dichVuVTId", dichVuVTId).put("loai", loai).put("maTB", maTB).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String sqlString = "{ CALL qlvt.lay_tt_vattu_tb(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), maTB, dichVuVTId, loai, CURSOR_PARAMETER, CURSOR_PARAMETER]) {
                    ResultSet rsThueBao, ResultSet rsVatTu ->
                        List<TTKH_DsVatTu> vatTuList = new ArrayList<>()
                        while (rsVatTu.next()) {
                            vatTuList.add(new TTKH_DsVatTu(vatTuId: Utils.getLongDataByKey(rsVatTu,"vattu_id"), kieuTBId: Utils.getLongDataByKey(rsVatTu,"kieutbi_id"),
                                    tinhTrangId: Utils.getLongDataByKey(rsVatTu,"tinhtrangtbi_id"), trangBiId: Utils.getLongDataByKey(rsVatTu,"trangbi_id"),
                                    khoId: Utils.getLongDataByKey(rsVatTu,"kho_id"), sdvtId: Utils.getLongDataByKey(rsVatTu,"sdvt_id"),
                                    dvTinhId: Utils.getLongDataByKey(rsVatTu,"dvitinh_id"), thietBiId: Utils.getLongDataByKey(rsVatTu,"thietbi_id"),
                                    loHang: Utils.getStringDataByKey(rsVatTu,"lohang"), ngaySD: Utils.getStringDataByKey(rsVatTu,"ngay_sd"),
                                    serialGP: Utils.getStringDataByKey(rsVatTu,"serial_gp"), ghiChu: Utils.getStringDataByKey(rsVatTu,"ghichu"),
                                    tenVT: Utils.getStringDataByKey(rsVatTu,"ten_vt"), soLuong: Utils.getStringDataByKey(rsVatTu,"soluong"),
                                    dvTinh: Utils.getStringDataByKey(rsVatTu,"dvi_tinh"), bsVT: Utils.getStringDataByKey(rsVatTu,"bs_vt")))
                        }
                        ret.setDsVatTu(vatTuList)

                        List<TTKH_DsThueBao> thueBaos = new ArrayList<>()
                        while (rsThueBao.next()) {
                            thueBaos.add(new TTKH_DsThueBao(thueBaoId: Utils.getLongDataByKey(rsThueBao,"thuebao_id"), ngaySD: Utils.getStringDataByKey(rsThueBao,"ngay_sd"),
                                    donViId: Utils.getLongDataByKey(rsThueBao,"donvi_id"), maTB: Utils.getStringDataByKey(rsThueBao,"ma_tb"), tenTB: Utils.getStringDataByKey(rsThueBao,"ten_tb")))
                        }
                        ret.setDsThueBao(thueBaos)
                }
            }
            loggingManaged.info("[TTKhachHangController] - [dsVatTuThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", ret != null ? ret.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [dsVatTuThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Long>> capNhatTTKH(TTKH_CapNhatRequest body) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TTKhachHangController] - [capNhatTTKH] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("body", body).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(genericApi.CSS_SCH)
            String phanVungId = session_.get(genericApi.ID_TINHTHANH)
            String mayCn = session_.get(genericApi.DEVICE_ID)
            String nguoiCn = session_.get(genericApi.USERNAME)
            String ipCn = session_.get(genericApi.DEVICE_IP)
            Long sdvtId
            String sqlString = "{ CALL qlvt.day_vattu_bs(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), String.valueOf(body.getThueBaoId()), String.valueOf(body.getVatTuId()), String.valueOf(body.getKhoId()),
                                     body.getLoHang(), body.getNgaySD(), String.valueOf(body.getKieuTBId()), String.valueOf(body.getDvTinhId()), String.valueOf(body.getTinhTrangId()),
                                     String.valueOf(body.getTrangBiId()), body.getSerialGP(), body.getGhiChu(), body.getDonGia(), mayCn, nguoiCn, ipCn, String.valueOf(body.getThietBiId()),
                                     String.valueOf(body.getSdvtId()), body.getLoai(), body.getKieu(), body.getSerialNumber(), CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        if (rs != null && rs.next()) {
                            sdvtId = Utils.getLongDataByKey(rs, "sdvt_id")
                        }
                }
            }
            loggingManaged.info("[TTKhachHangController] - [capNhatTTKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("result", sdvtId != null ? sdvtId.toString() : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(sdvtId))
        } catch (Exception e) {
            loggingManaged.error("[TTKhachHangController] - [capNhatTTKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
