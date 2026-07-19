package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.HopDongDungThuApi
import io.swagger.model.dto.*
import io.swagger.model.request.ChuyenChinhThucRequest
import io.swagger.model.request.HuyDungThuRequest
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.Enum
import vn.vnptit.sapi.oneapp.model.TRANGTHAI_PORT
import vn.vnptit.sapi.oneapp.service.VASCService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class HopDongDungThuController implements HopDongDungThuApi {

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
    VASCService vascService

    @Autowired
    HopDongDungThuController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDonVi() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsDonVi] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.sp_lay_donvi_co_tb_dungthu(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsDonVi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsDonVi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKhuVuc() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsKhuVuc] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.sp_lay_khuvuc_co_tb_dungthu(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("khuvuc_id"), NAME: rs.getString("ten_kv")))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsKhuVuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsKhuVuc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<HopDongDungThu>>> dsHopDong(String khuVucId, String donViId) {
        List<HopDongDungThu> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsHopDong] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
//            nhanVienId = 13431
            String sqlString = "{ call css.sp_lay_ds_hd_dungthu_chuaht_v3(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, nhanVienId, khuVucId, donViId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new HopDongDungThu(
                                ctv_id: rs.getString("ctv_id"),
                                so_dt: rs.getString("so_dt"),
                                ma_gd: rs.getString("ma_gd"),
                                nhanvien_id: rs.getString("nhanvien_id"),
                                hdtb_id: rs.getString("hdtb_id"),
                                songay: rs.getString("songay"),
                                ngay_tb: rs.getString("ngay_tb"),
                                ngay_bd: rs.getString("ngay_bd"),
                                ngay_kt: rs.getString("ngay_kt"),
                                chitietkm_id: rs.getString("chitietkm_id"),
                                may_cn: rs.getString("may_cn"),
                                ngay_cn: rs.getString("ngay_cn"),
                                nguoi_cn: rs.getString("nguoi_cn"),
                                ip_cn: rs.getString("ip_cn"),
                                dichvuvt_id: rs.getString("dichvuvt_id"),
                                trangthai: rs.getString("trangthai"),
                                ma_tb: rs.getString("ma_tb"),
                                ten_tb: rs.getString("ten_tb"),
                                diachi_tb: rs.getString("diachi_tb"),
                                loaihinh_tb: rs.getString("loaihinh_tb"),
                                loaitb_id: rs.getString("loaitb_id"),
                                thuebao_id: rs.getString("thuebao_id"),
                                dv_lhd: rs.getString("dv_lhd"),
                                ten_nvgt: rs.getString("ten_nvgt"),
                                trangthai_tv: rs.getString("trangthai_tv"),
                                nguoi_tv: rs.getString("nguoi_tv"),
                                ngay_tv: rs.getString("ngay_tv"),
                                datcoc: rs.getString("datcoc"),
                                ten_kv: rs.getString("ten_kv"),
                                khuvuc_id: rs.getString("khuvuc_id"),
                                donvi_id: rs.getString("donvi_id")
                        ))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsHopDong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsHopDong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DoiTuongThueBao>>> dsDoiTuongThueBao() {
        List<DoiTuongThueBao> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsDoiTuongThueBao] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.SP_LAY_DOITUONG_TB(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DoiTuongThueBao(doituong_id: rs.getLong("doituong_id"), ten_dt: rs.getString("ten_dt"), status: rs.getString("status")))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsDoiTuongThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsDoiTuongThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhomHuy(String loaiLDId, String loaiTBId, String kieu) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsNhomHuy] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiLDId", loaiLDId)
                    .put("loaiTBId", loaiTBId)
                    .put("kieu", kieu)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_ds_nhomhuy(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [loaiLDId, loaiTBId, kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("NHOMHUY_ID"), NAME: rs.getString("TEN_NHOM")))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsNhomHuy] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsNhomHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsLyDoHuy(String loaiLDId, String loaiTBId, String kieu, String nhomHuyId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsLyDoHuy] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiLDId", loaiLDId)
                    .put("loaiTBId", loaiTBId)
                    .put("kieu", kieu)
                    .put("nhomHuyId", nhomHuyId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_ds_lydo_huy(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, loaiLDId, loaiTBId, nhomHuyId, kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(CODE: rs.getString("stt"), ID: rs.getLong("lydohuy_id"), NAME: rs.getString("lydohuy")))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsLyDoHuy] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsLyDoHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhom(String kieu, String input) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsNhom] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("kieu", kieu)
                    .put("input", input)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.danhmuc_tracuu_kh_donhang_smp(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, kieu, input, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("ID"), NAME: rs.getString("NAME")))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsNhom] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsNhom] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhanVienResponse>>> dsNhanVien(String kieu, String loaiNVId, String donViId) {
        List<NhanVienResponse> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [dsNhom] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("kieu", kieu)
                    .put("loaiNVId", loaiNVId)
                    .put("donViId", donViId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.tracuu_ctv_donhang_smp(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, kieu, loaiNVId, donViId, nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NhanVienResponse(
                                nhanvien_id: rs.getString("nhanvien_id"),
                                ma_nv: rs.getString("ma_nv"),
                                ten_nv: rs.getString("ten_nv"),
                                diachi_nv: rs.getString("diachi_nv"),
                                so_dt: rs.getString("so_dt"),
                                ten_dv: rs.getString("ten_dv"),
                                ma_dv: rs.getString("ma_dv"),
                                ten_ht: rs.getString("ten_ht"),
                                ma_the: rs.getString("ma_the")
                        ))
                    }
                }
            }
            loggingManaged.info("[HopDongDungThuController] - [dsNhom] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [dsNhom] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> kiemTra(String hdtbId) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [kiemTra] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("hdtbId", hdtbId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call ? := css.fn_kiemtra_doituong_fiber_dungthu(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, hdtbId]) { String out -> ret = out}
            }
            loggingManaged.info("[HopDongDungThuController] - [kiemTra] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            if ("ok" == ret?.toLowerCase())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            else
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ret, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [kiemTra] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> chuyenChinhThuc(ChuyenChinhThucRequest requestBody) {
        String ret = ""
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [chuyenChinhThuc] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("requestBody", requestBody.toString()).build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String token = session_.get(TOKEN)
            vascService.init(token, dataSrc, phanVungId, nhanVienId)

            String kq = vascService.cust_Trial_UpdateInfo(requestBody.maTB, "3", "0", null, null, null);// tài khoản Mytv
            if (kq != "OK") //-7: Thuê bao đã chuyển sang dùng thật
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of("", requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            String sqlString = "{ call ? := css.FN_chuyen_hd_chinhthuc_v3(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.hdtbId, requestBody.doiTuongId, mayCn, nguoiCn, ipCn, requestBody.ctvId, requestBody.nhanVienGTId]) { String out -> ret = out}
            }
            loggingManaged.info("[HopDongDungThuController] - [chuyenChinhThuc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? ret.toString() : "")
                            .build())
            if ("ok" == ret?.toLowerCase())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            else
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ret, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [chuyenChinhThuc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> huyDungThu(HuyDungThuRequest requestBody) {
        String ret = "Hủy hợp đồng dùng thử thành công!"
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[HopDongDungThuController] - [huyDungThu] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("requestBody", requestBody.toString()).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(MA_NV)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String token = session_.get(TOKEN)
            vascService.init(token, dataSrc, phanVungId, nhanVienId)
            String kq = vascService.suspendSubscriberV2(requestBody.maTB, 13);// tài khoản Mytv
            if (kq != "OK")
            {
                ret = "Có lỗi khi cập nhật thông tin lên VASC: " + kq;
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ret, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                String sqlString = "{ call ? := css.fn_huy_hd_dungthuv2(?,?,?,?,?,?,?,?,?,?) }"
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.hdtbId, donViId, nhanVienId, requestBody.lyDoHuyId, requestBody.thangHetHan, requestBody.ghiChu, mayCn, nguoiCn, ipCn]) { }

                String sqlString1 = "{ call CSS.TRACUU_DANHBA.LAY_DS_DB_ADSL_THEO_TBID(?,?,?) }"
                sql.call(sqlString1, [phanVungId, requestBody.hdtbId, CURSOR_PARAMETER]) {ResultSet rs ->
                    if (rs.next()){
                        Long vport_id = 0
                        Long vvci_vpi_id = 0
                        if (rs.getString("port_id") != "")
                            vport_id = rs.getLong("port_id")
                        if (rs.getLong("vci_vpi_id") != "")
                            vvci_vpi_id = rs.getLong("vci_vpi_id")


                        def dsktradb = ktra_dk_port(dataSrc, phanVungId, 1, requestBody.maTB, vport_id, vvci_vpi_id)
                        def dsktrahd = ktra_dk_port(dataSrc, phanVungId, 2, requestBody.maTB, vport_id, vvci_vpi_id)
                        if (dsktradb.isEmpty() && dsktrahd.isEmpty())
                        {
                            CapNhat_TrangThai_Port(dataSrc, phanVungId, TRANGTHAI_PORT.CHUA_SD, vport_id, vvci_vpi_id)
                            CapNhat_TrangThai_PortID(dataSrc, phanVungId, TRANGTHAI_PORT.DA_SD, vport_id, mayCn, nguoiCn)
                            Capnhat_port_vatly(dataSrc, phanVungId, vport_id, TRANGTHAI_PORT.CHUA_SD)
                            CapNhat_ds_matb(dataSrc, phanVungId, "", vport_id, vvci_vpi_id)
                        }
                        else if (!dsktradb.isEmpty() && dsktrahd.isEmpty())
                        {
                            String vds_matb = dsktradb.join("; ")

                            CapNhat_TrangThai_Port(dataSrc, phanVungId, TRANGTHAI_PORT.DA_SD, vport_id, vvci_vpi_id)
                            CapNhat_TrangThai_PortID(dataSrc, phanVungId, TRANGTHAI_PORT.DA_SD, vport_id, mayCn, nguoiCn)
                            Capnhat_port_vatly(dataSrc, phanVungId, vport_id, TRANGTHAI_PORT.DA_SD)
                            CapNhat_ds_matb(dataSrc, phanVungId, vds_matb, vport_id, vvci_vpi_id)
                        }
                        else if (dsktradb.isEmpty() && !dsktrahd.isEmpty())
                        {
                            String vds_matb = dsktrahd.join("; ")

                            CapNhat_TrangThai_Port(dataSrc, phanVungId, TRANGTHAI_PORT.TAM_CAP, vport_id, vvci_vpi_id)
                            CapNhat_TrangThai_PortID(dataSrc, phanVungId, TRANGTHAI_PORT.DA_SD, vport_id, mayCn, nguoiCn)
                            Capnhat_port_vatly(dataSrc, phanVungId, vport_id, TRANGTHAI_PORT.DA_SD)
                            CapNhat_ds_matb(dataSrc, phanVungId, vds_matb, vport_id, vvci_vpi_id)
                        }
                        else if (!dsktradb.isEmpty() && !dsktrahd.isEmpty())
                        {
                            String vds_matb = (dsktradb + dsktrahd).join("; ")

                            CapNhat_TrangThai_Port(dataSrc, phanVungId, TRANGTHAI_PORT.DA_SD, vport_id, vvci_vpi_id)
                            CapNhat_TrangThai_PortID(dataSrc, phanVungId, TRANGTHAI_PORT.DA_SD, vport_id, mayCn, nguoiCn)
                            Capnhat_port_vatly(dataSrc, phanVungId, vport_id, TRANGTHAI_PORT.DA_SD)
                            CapNhat_ds_matb(dataSrc, phanVungId, vds_matb, vport_id, vvci_vpi_id)
                        }
                    }
                }
            }

            loggingManaged.info("[HopDongDungThuController] - [huyDungThu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ret, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[HopDongDungThuController] - [huyDungThu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    List<String> ktra_dk_port(String dataSrc, String phanVungId, Long kieu, String maTB, Long portId, Long vciVpiId){
        List<String> ret = []
        try {
            String sqlString = "{ call css.sp_ktra_dk_port(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, kieu, maTB, portId, vciVpiId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(rs.getString("ma_tb"))
                    }
                }
            }
        } catch (Exception e) {
        }
        ret
    }

    void CapNhat_TrangThai_Port(String dataSrc, String phanVungId, int trangThai, Long portId, Long vciVpiId){
        try {
            String sqlString = "{ call css.fn_capnhat_trangthai_port(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, trangThai, portId, vciVpiId]) { }
            }
        } catch (Exception e) {
        }
    }

    void CapNhat_TrangThai_PortID(String dataSrc, String phanVungId, int trangThai, Long portId, String mayCN, String nguoiCN){
        try {
            String sqlString = "{ call css.fn_CapNhat_TrangThai_PortID(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, portId, trangThai, mayCN, nguoiCN]) { }
            }
        } catch (Exception e) {
        }
    }

    void Capnhat_port_vatly(String dataSrc, String phanVungId, Long portId, int trangThai){
        try {
            String sqlString = "{ call css.fn_capnhat_port_vatly(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, portId, trangThai]) { }
            }
        } catch (Exception e) {
        }
    }

    void CapNhat_ds_matb(String dataSrc, String phanVungId, String dsMaTB, Long portId, Long vciVpiId){
        try {
            String sqlString = "{ call css.fn_CapNhat_ds_matb(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, dsMaTB, portId, vciVpiId]) { }
            }
        } catch (Exception e) {
        }
    }
}
