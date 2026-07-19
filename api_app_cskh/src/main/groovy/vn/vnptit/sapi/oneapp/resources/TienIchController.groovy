package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.GroovyRowResult
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.TienIchApi
import io.swagger.model.dto.*
import io.swagger.model.request.*
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.TTThueBaoCCBSResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.*
import vn.vnptit.sapi.oneapp.model.response.BaseVASCResponse
import vn.vnptit.sapi.oneapp.model.response.SubscriberInfoModel
import vn.vnptit.sapi.oneapp.service.*
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class TienIchController implements TienIchApi {

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
    DataUtils dataUtils
    @Autowired
    VTNService vtnService
    @Autowired
    CCBSService ccbsService
    @Autowired
    SPSService spsService

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    TichHopService tichHopService

    @Autowired
    CTSService ctsService

    @Autowired
    TienIchController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> danhMucDonViGet() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
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
            loggingManaged.error("[TienIchController] - [danhMucDonViGet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Port>> traCuuPort(String maDt) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            Port res = new Port(
                    status: "OFF",
                    account: "thanh1234",
                    soAo: "FTTH1231231",
                    dsLamSw: "Van Cao OLT 123",
                    port: "1/1//2/11/39",
                    onuType: "iGate-GW040",
                    onuSerial: "000000.FTTH12398798",
                    onuRxTx: "",
                    oltRxTx: "-8.00000/4.021",
                    speed: "Default/Default",
                    chatLuong: "NA/NA",
                    moTa: "FTTH98731873"
            )

//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [traCuuPort] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> resetPort(String account) {
//        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Reset port thành công", ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [resetPort] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Wifi>>> xemThongTinWifi(String account) {
        List<Wifi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
            for (int i = 0; i < 8; i++) {
                ret.add(new Wifi(
                        enable: "True",
                        ssid: "Hieu",
                        pass: "",
                        kieuXt: "11i",
                        kieuMh: "AESEncryption"
                ))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [xemThongTinWifi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Port>> setPortProfile(String account, String speed) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            Port res = new Port(
                    status: "OFF",
                    account: "thanh1234",
                    soAo: "FTTH1231231",
                    dsLamSw: "Van Cao OLT 123",
                    port: "1/1//2/11/39",
                    onuType: "iGate-GW040",
                    onuSerial: "000000.FTTH12398798",
                    onuRxTx: "",
                    oltRxTx: "-8.00000/4.021",
                    speed: "Default/Default",
                    chatLuong: "NA/NA",
                    moTa: "FTTH98731873"
            )

//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(res))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [setPortProfile] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> doiPassWifi(DoiPassWifiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Đổi pass wifi thành công", ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [doiPassWifi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Visa>> traCuuVisa(String account) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }

            Visa ret = new Visa(
                    loaiTaiKhoan: "NF500T500P0_O3",
                    accountNumber: "19405116",
                    accountName: "phuong210787",
                    email: "phuong210787@vnn.vn",
                    ngayTao: "09/12/2014 6:52:26",
                    pass: "123456 Qua webservice",
                    tinh: "HPG",
                    tenKh: "Lê Thị Phương",
                    diaChiKh: "17b/16/29/139, Ngô Gia Tự, Phường Đằng Lâm, Quận Hải An",
                    sdt: "456160272",
                    status: "ACTIVE",
                    service: "NF500T500P0_03",
                    serviceId: "ADSL",
                    dslamId: 41340,
                    jnprIpv6DelegatedPoolName: "FTTH-V6-LAN",
                    jnprIpv6EgressPolicyName: "F500T500-In-V6",
                    brasId: 6598,
                    framedIpv6Pool: "FTTH-V6-WAN",
                    typeId: "F500T500PO"
            )
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [traCuuVisa] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Vasc>> traCuuVasc(String account) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }

            Vasc ret = new Vasc(
                    trangThai: "Đang sử dụng bình thường",
                    system: "Mytv VMP",
                    serialStb: "63f45eb0595b8624",
                    pass: "123456",
                    cellNo: "0911121268",
                    mac: "A2:38:06:38:F9:8C",
                    portDsl: "10.35.105.22/1/1/10/7",
                    deviceName: "HPG.TNN.TXA. OLT.AL21",
                    maHd: "HPG-10-214772",
                    loaiKh: "Khách hàng cá nhân - hộ gia đình",
                    ngayKyHd: "05/12/2016",
                    goiCuoc: "Nâng cao",
                    ngayTinhCuoc: "06/12/2016",
                    tenKh: "Đào Thị Mừng",
                    ngaySinh: "16/04/1965",
                    sdt: "0986123456",
                    gioiTinh: "Nam",
                    diaChi: "Xóm 2 - Trịnh Hưởng - Xã Thiên Hương - Huyện Thủy Nguyên",
                    tinh: "Hải Phòng",
                    email: "mungdt@vnpt.com",
                    fax: "fax-387189173897",
                    soCmt: "151447006"
            )
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [traCuuVasc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Vtn>> traCuuVtn(String account) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }

            Vtn ret = new Vtn(
                    nodeMtas: "Mở gọi đi / Mở gọi đến",
                    nodeHss: "Ok",
                    nodeIpw: "Ok",
                    tenThueBao: "Công Ty Cổ Phần Anh Khuê Sài Gòn",
                    ngayTao: "12/22/2018 5:49:57",
                    loaiHinhTb: "Thuê bao POST",
                    diaChiLd: "Gian hàng L1 - K13 Vincom Plaza khu đô thị Vinhome số 155, Đường Bạch Đằng, Phường Thượng Lý, Quận Hồng Bàng, Hải Phòng",
                    pass: "842253529998",
                    status: "Hoạt động bình thường"
            )
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [traCuuVtn] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Vtn>> traCuuThongTinHoTroMyTv(String model) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String response = ""
            String sqlString = "{ ? = call CSS.FN_thongtin_tv_hotro_app_mytv(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, model, Long.parseLong(phanVungId)]) { String res ->
                    response = res
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[ThiCongController] - [traCuuThongTinHoTroMyTv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> doiPassAdslFtthMytv(String account, String pass) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Đổi pass thành công", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [doiPassAdslFtthMytv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ChiTietThueBao>> traCuuChiTietThueBao(String maTb) {
        String requestId = genericApi.generateRequestId()
        try {
            if (maTb == null || maTb.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            ChiTietThueBao response
            String sqlString = "{ call CSS.tracuu_chitiet_tb_theo_ma_tb(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), maTb, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        response = new ChiTietThueBao().convertDataFromDatabase(rs)
                    }
                }
            }
            if (response == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, HttpStatus.NO_CONTENT))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [traCuuChiTietThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ChiTietThueBao>>> traCuuChiTietThueBao_vt(String maTb) {
        String requestId = genericApi.generateRequestId()
        try {
            if (maTb == null || maTb.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            List<ChiTietThueBao> response = []
            String sqlString = "{ call CSS.tracuu_chitiet_tb_theo_ma_tb(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), maTb, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new ChiTietThueBao().convertDataFromDatabase(rs))
                    }
                }
            }
            if (response == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, HttpStatus.NO_CONTENT))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [traCuuChiTietThueBao_vt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Integer>> chuyenDoiMytvVMP(ChuyenDoiMytvVMPRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            if (requestBody == null || requestBody.maTb == null || requestBody.maTb.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[TienIchController] - [chuyenDoiMytvVMP] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody))
                    .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String nguoiDungId = session_.get(NGUOIDUNG_ID)
            String token = session_.get(TOKEN)
            String ma_tinh = session_.get(MA_TINH)
            dataUtils.init(phanVungId, dataSrc)
            vascService.init(token)
            Gson gson = new Gson()
            String sqlString = ""
            String response = ""
            String lyDo = "KH Yêu cầu chuyển đổi"
            long thuebao_id = -1
            String vmp
            sqlString = "select vmp, a.thuebao_id from css.db_thuebao a, css.db_adsl b where a.phanvung_id = b.phanvung_id and a.phanvung_id = ? and a.thuebao_id = b.thuebao_id and a.ma_tb= ? and loaitb_id = 61"
            GroovyRowResult rowResult
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                rowResult = sql.firstRow(sqlString, phanVungId, requestBody.maTb)
            }
            if (rowResult == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tồn tại thuê bao MyTV có mã ${requestBody.maTb} trên hệ thống, hãy chắc chắn rằng bạn đã gõ đúng mã, có phân biệt chữ hoa/thường.", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            } else {
                vmp = rowResult.getProperty("vmp").toString()
                thuebao_id = Long.parseLong(rowResult.getProperty("thuebao_id").toString())
            }
            if (requestBody.kieuVmp == vmp) {
                if (requestBody.kieuVmp == "1") {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(0, requestId, "Thuê bao hiện đã là VMP rồi.", ErrorCodeDefine.SUCCESS))
                } else if (requestBody.kieuVmp == "0") {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(0, requestId, "Thuê bao hiện đã là MyTV thường rồi", ErrorCodeDefine.SUCCESS))
                }
            }
            BaseVASCResponse<SubscriberInfoModel> resObj
            SubscriberInfoModel subInfo
            response = vascService.getSubscriberInfo(requestBody.maTb)
            if (response != null) {
                resObj = gson.fromJson(response, new TypeToken<BaseVASCResponse<SubscriberInfoModel>>() {
                }.getType())
                subInfo = resObj.data
            }
            if (subInfo != null) {
                if (subInfo.fromSys == requestBody.kieuVmp) {
                    // Update database
                    sqlString = "{ ? = call CSS.WEBAPI_EX.CAPNHAT_VMP_DB_ADSL(?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.kieuVmp, thuebao_id]) { String rs ->
                            response = rs
                        }
                    }
                    if (response != null && response?.toUpperCase() == "OK") {
                        if (requestBody.kieuVmp == "1") {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(1, requestId, "Thuê bao trên hệ thống VASC đang là VMP, đã đồng bộ vào dữ liệu ĐHSX <span  style=\"color:#00ff00\">thành công!</span>", ErrorCodeDefine.SUCCESS))
                        } else if (requestBody.kieuVmp == "0") {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(1, requestId, "Thuê bao trên hệ thống VASC đang là MyTV thường, đã đồng bộ vào dữ liệu ĐHSX <span  style=\"color:#00ff00\">thành công!</span>", ErrorCodeDefine.SUCCESS))
                        }
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "<span  style=\"color:#00ff00\">$response</span>", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                }

                def res = vascService.changeSystem(lyDo, requestBody.maTb, requestBody.kieuVmp)
                if (res != null && res["errorCode"] != 0) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "<span style=\"color:#ff0000\">Có lỗi trong quá trình đồng bộ thuê bao ${requestBody?.maTb} lên hệ thống VASC.<br/>Mã lỗi:${res["errorCode"]} : ${res["faultString"]}</span>", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                // Update database
                sqlString = "{ ? = call CSS.WEBAPI_EX.CAPNHAT_VMP_DB_ADSL(?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.kieuVmp, thuebao_id]) { String rs ->
                        response = rs
                    }
                }
                if (response != null && response?.toUpperCase() != "OK") {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "<span  style=\"color:#00ff00\">$response</span>", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin thuê bao ${requestBody.maTb} trên hệ thống VASC", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            if (requestBody.kieuVmp == "1") {
                dataUtils.Log_Action(nguoiCn, "VNPT-Cab", "MyTV_VMP", "MyTV_VMP vma_tb= ${requestBody.maTb}, vkieuvmp=${requestBody.kieuVmp} ,vlydo=${lyDo} ,vnguoidung_id= ${nguoiDungId}", "VNPT-Cab", "Chuyển MyTV -> VMP")
            } else {
                dataUtils.Log_Action(nguoiCn, "VNPT-Cab", "MyTV_VMP", "MyTV_VMP vma_tb= ${requestBody.maTb}, vkieuvmp=${requestBody.kieuVmp} ,vlydo=${lyDo} ,vnguoidung_id= ${nguoiDungId}", "VNPT-Cab", "Chuyển VMP -> MyTV")
            }
            boolean isChangeSuccess = true
            if (requestBody.kieuVmp == "1") {
                response = "Chuyển sang VMP <span  style=\"color:#00ff00\">thành công!</span>"
            } else if (requestBody.kieuVmp == "0") {
                response = "Chuyển sang MyTV thường <span  style=\"color:#00ff00\">thành công!</span>"
            } else {
                isChangeSuccess = false
                response = "<span style=\"color:#ff0000\">Lỗi: Không rõ tham số ?IS_VMP</span>"
            }
            loggingManaged.info("[TienIchController] - [chuyenDoiMytvVMP] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(isChangeSuccess ? 1 : null, requestId, response, isChangeSuccess ? ErrorCodeDefine.SUCCESS : ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [chuyenDoiMytvVMP] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "<span style=\"color:#ff0000\">" + e.message + "</span>", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> kiemTraTrangThaiVMP(String maTb, Integer kieuVmp) {
        String requestId = genericApi.generateRequestId()
        try {
            if (maTb == null || maTb.isEmpty() || kieuVmp == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String response = ""
            String sqlString = "{ ? = call CSS.FN_TRANGTHAI_VMP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), maTb, kieuVmp]) { String rs ->
                    response = rs
                }
            }
            if (response == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [kiemTraTrangThaiVMP] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> resetModem(String account) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Reset modem thành công", ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [resetModem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> backupCauHinhModem(String account) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Backup cấu hình modem thành công", ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [backupCauHinhModem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaFileBackupCauHinhModem(String account) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Xóa file backup cấu hình modem thành công", ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [xoaFileBackupCauHinhModem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Integer>> layThongTinDonViTheoMaTb(String maTb) {
        String requestId = genericApi.generateRequestId()
        try {
            if (maTb == null || maTb.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            int response
            String sqlString = "{ ? = call CSS.FN_get_donvi(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, maTb, Long.parseLong(phanVungId)]) { Integer rs ->
                    response = rs
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [layThongTinDonViTheoMaTb] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> kiemTraQuyenNvThaoTacKetCuoi(Long donViId) {
        String requestId = genericApi.generateRequestId()
        try {
            if (donViId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            int response = 0
            String sqlString = "{ ? = call CSS.FN_Quyen_ThaoTac_KetCuoi(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, donViId, Long.parseLong(nhanVienId),
                                     Long.parseLong(phanVungId)]) { Integer rs ->
                    response = rs
                }
            }
            if (response == 1) { // Có quyền
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn có quyền thao tác trên kết cuối", ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn không có quyền thao tác trên kết cuối", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [kiemTraQuyenNvThaoTacKetCuoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> thayDoiSettopbox(ThayDoiSettopboxRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
//            String sqlString = "{ call qltn_hpg.API_APP.DS_Donvi_ctv(?) }"
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
//                    while (rs.next()) {
//                        ret.add(new DanhMuc(ID: rs.getLong("donvi_id"), NAME: rs.getString("ten_dv")))
//                    }
//                }
//            }
//            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thay đổi Settopbox thành công", ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [thayDoiSettopbox] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<HopDongPhuLuc>>> dsHopDongPhuLuc(String maGD) {
        List<HopDongPhuLuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.MOBILE_LAYDS_THANHTOANHD_SMP(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), maGD == null || maGD.trim().length() == 0 ? null : maGD,
                                     maGD == null || maGD.trim().length() == 0 ? 1 : 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    ret.add(new HopDongPhuLuc().convertDataFromDatabase(rs))
                }
            }
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [dsHopDongPhuLuc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinGhiNhanP2P>>> layThongTinGhiNhan(String maTB) {
        List<ThongTinGhiNhanP2P> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            maTB = maTB.trim()
            List<ThongTinGhiNhanP2P> dt = new ArrayList<>()
            String sqlString = "{ call css.lay_thongtin_ghinhan_p2p(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, maTB, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        dt.add(new ThongTinGhiNhanP2P().convertDataFromDatabase(rs))
                    }
                }
            }
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 2, maTB, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ThongTinGhiNhanP2P dt_tmp = new ThongTinGhiNhanP2P()
                        dt_tmp.setId(rs.getLong("ID"))
                        dt_tmp.setName(rs.getString("NAME"))
                        //Vi VNPT Pay
                        if (dt_tmp.getId() == 1) {
                            if (dt.size() > 0) {
                                if (dt.get(0).getViVNPTPay() == "1") {
                                    String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                                        sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTViId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                                            if (rsNV != null && rsNV.next()) {
                                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                                item.setId(dt_tmp.getId())
                                                item.setName(dt_tmp.getName())
                                                item.setViVNPTPay(dt.get(0).getViVNPTPay())
                                                item.setNgayCnVi(dt.get(0).getNgayCnVi())
                                                item.setMaTB(maTB)
                                                item.setNhanVienGTViId(dt.get(0).getNhanVienGTViId())
                                                item.setMaNhanVienGTVi(rsNV.getString("MA_NV"))
                                                item.setTenNhanVienGTVi(rsNV.getString("TEN_NV"))
                                                item.setSdtNhanVienGTVi(rsNV.getString("SO_DT"))
                                                item.setDvNhanVienGTVi(rsNV.getString("TEN_DV"))
                                                ret.add(item)
                                            }
                                        }
                                    }
                                } else {
                                    ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                    item.setId(dt_tmp.getId())
                                    item.setName(dt_tmp.getName())
                                    item.setViVNPTPay(dt.get(0).getViVNPTPay())
                                    item.setNgayCnVi(dt.get(0).getNgayCnVi())
                                    item.setMaTB(maTB)
                                    item.setNhanVienGTViId(null)
                                    item.setMaNhanVienGTVi("")
                                    item.setTenNhanVienGTVi("")
                                    item.setSdtNhanVienGTVi("")
                                    item.setDvNhanVienGTVi("")
                                    ret.add(item)
                                }
                            } else {
                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                item.setId(dt_tmp.getId())
                                item.setName(dt_tmp.getName())
                                item.setViVNPTPay("0")
                                item.setNgayCnVi("")
                                item.setMaTB(maTB)
                                item.setNhanVienGTViId(null)
                                item.setMaNhanVienGTVi("")
                                item.setTenNhanVienGTVi("")
                                item.setSdtNhanVienGTVi("")
                                item.setDvNhanVienGTVi("")
                                ret.add(item)
                            }
                        } else if (dt_tmp.getId() == 2) { //Mobile Money
                            if (dt.size() > 0) {
                                if (dt.get(0).getMobileMoney() == "1") {
                                    String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                                        sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTMMId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                                            if (rsNV != null && rsNV.next()) {
                                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                                item.setId(dt_tmp.getId())
                                                item.setName(dt_tmp.getName())
                                                item.setMobileMoney(dt.get(0).getMobileMoney())
                                                item.setNgayCnMM(dt.get(0).getNgayCnMM())
                                                item.setMaTB(maTB)
                                                item.setNhanVienGTMMId(dt.get(0).getNhanVienGTMMId())
                                                item.setMaNhanVienGTMM(rsNV.getString("MA_NV"))
                                                item.setTenNhanVienGTMM(rsNV.getString("TEN_NV"))
                                                item.setSdtNhanVienGTMM(rsNV.getString("SO_DT"))
                                                item.setDvNhanVienGTMM(rsNV.getString("TEN_DV"))
                                                ret.add(item)
                                            }
                                        }
                                    }
                                } else {
                                    ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                    item.setId(dt_tmp.getId())
                                    item.setName(dt_tmp.getName())
                                    item.setMobileMoney(dt.get(0).getMobileMoney())
                                    item.setNgayCnMM(dt.get(0).getNgayCnMM())
                                    item.setMaTB(maTB)
                                    item.setNhanVienGTMMId(null)
                                    item.setMaNhanVienGTMM("")
                                    item.setTenNhanVienGTMM("")
                                    item.setSdtNhanVienGTMM("")
                                    item.setDvNhanVienGTMM("")
                                    ret.add(item)
                                }
                            } else {
                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                item.setId(dt_tmp.getId())
                                item.setName(dt_tmp.getName())
                                item.setMobileMoney("0")
                                item.setNgayCnMM("")
                                item.setMaTB(maTB)
                                item.setNhanVienGTMMId(null)
                                item.setMaNhanVienGTMM("")
                                item.setTenNhanVienGTMM("")
                                item.setSdtNhanVienGTMM("")
                                item.setDvNhanVienGTMM("")
                                ret.add(item)
                            }
                        } else if (dt_tmp.getId() == 3) { //Merchan P2P
                            if (dt.size() > 0) {
                                if (dt.get(0).getP2p() == "1") {
                                    String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                                        sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTP2PId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                                            if (rsNV != null && rsNV.next()) {
                                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                                item.setId(dt_tmp.getId())
                                                item.setName(dt_tmp.getName())
                                                item.setP2p(dt.get(0).getP2p())
                                                item.setNgayCnP2P(dt.get(0).getNgayCnP2P())
                                                item.setMaTB(maTB)
                                                item.setNhanVienGTP2PId(dt.get(0).getNhanVienGTP2PId())
                                                item.setMaNhanVienGTP2P(rsNV.getString("MA_NV"))
                                                item.setTenNhanVienGTP2P(rsNV.getString("TEN_NV"))
                                                item.setSdtNhanVienGTP2P(rsNV.getString("SO_DT"))
                                                item.setDvNhanVienGTP2P(rsNV.getString("TEN_DV"))
                                                ret.add(item)
                                            }
                                        }
                                    }
                                } else {
                                    ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                    item.setId(dt_tmp.getId())
                                    item.setName(dt_tmp.getName())
                                    item.setP2p(dt.get(0).getP2p())
                                    item.setNgayCnP2P(dt.get(0).getNgayCnP2P())
                                    item.setMaTB(maTB)
                                    item.setNhanVienGTP2PId(null)
                                    item.setMaNhanVienGTP2P("")
                                    item.setTenNhanVienGTP2P("")
                                    item.setSdtNhanVienGTP2P("")
                                    item.setDvNhanVienGTP2P("")
                                    ret.add(item)
                                }
                            } else {
                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                item.setId(dt_tmp.getId())
                                item.setName(dt_tmp.getName())
                                item.setP2p("0")
                                item.setNgayCnP2P("")
                                item.setMaTB(maTB)
                                item.setNhanVienGTP2PId(null)
                                item.setMaNhanVienGTP2P("")
                                item.setTenNhanVienGTP2P("")
                                item.setSdtNhanVienGTP2P("")
                                item.setDvNhanVienGTP2P("")
                                ret.add(item)
                            }
                        }
                    }
                }
            }
            if (ret != null && ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [layThongTinGhiNhan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    // Sửa hàm lấy thông tin v2, check thêm ccbs
    //Hàm ghi nhận nếu ko hợp lệ vẫn cho làm tiếp
    @Override
    ResponseEntity<BaseResponse<Void>> ghiNhanTuVan(TuVanMobileMoneyRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TienIchController] - [ghiNhanTuVan] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", new JsonBuilder(requestBody).toPrettyString()).build())
            String maTB = requestBody.getMaTB()
            String maTBGoc = requestBody.getMaTB()
            if (maTB == null || maTB.trim().length() == 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin số thuê bao", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            } else {
                if (!Utils.isPhone(maTB)) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }
            String soTB
            if (maTB.trim().indexOf("0") == 0) {
                maTB = maTB.trim().substring(1)
                soTB = "84" + maTB
            } else {
                soTB = "84" + maTB
                maTB = "0" + maTB.trim()
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            //Check thuê bao CCBS
            String token = session_.get(TOKEN)
            ccbsService.init(token)
            String resp = ccbsService.ts_tracuu_laytt_tb(soTB)
            try {
                TTThueBaoCCBSResponse ttThueBaoCCBSResponse = new Gson().fromJson(resp, TTThueBaoCCBSResponse.class)
                if (ttThueBaoCCBSResponse.getErrorCode() != null) {
                    if (ttThueBaoCCBSResponse.getErrorCode() != ErrorCodeDefine.SUCCESS) {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ttThueBaoCCBSResponse.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    } else {
                        if (ttThueBaoCCBSResponse.getData() != null) {
                            if (ttThueBaoCCBSResponse.getData().get(0).getKhoso() == null || ttThueBaoCCBSResponse.getData().get(0).getKhoso().contains("ERR")) {
                                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                            }
                        } else {
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin số thuê bao", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            } catch (Exception ex) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            //Ghi nhận
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            int vkq_vnptpay = 0
            int vkq_mobile_money = 0
            int vkq_p2p = 0
            for (int i = 0; i < requestBody.getDsGhiNhan().size(); i++) {
                if (requestBody.getDsGhiNhan().get(i).getId() == 1) {
                    vkq_vnptpay = requestBody.getDsGhiNhan().get(i).getKetQua()
                } else if (requestBody.getDsGhiNhan().get(i).getId() == 2) {
                    vkq_mobile_money = requestBody.getDsGhiNhan().get(i).getKetQua()
                } else if (requestBody.getDsGhiNhan().get(i).getId() == 3) {
                    vkq_p2p = requestBody.getDsGhiNhan().get(i).getKetQua()
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Đã có lỗi khi ghi nhận kết quả, vui lòng thử lại sau (Mã lỗi: 404)!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }
            String message = ""
            String sqlString = "{ call css.smp_ghinhan_kq_mobile_money(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), maTBGoc, vkq_vnptpay, vkq_mobile_money, vkq_p2p,
                                     Long.parseLong(nhanVienId), Long.parseLong(donViId), Sql.VARCHAR]) { String rs ->
                    if (rs != null && rs == "OK") {
                        message = ""
                    } else {
                        if (rs != null) {
                            message = rs
                        } else {
                            message = "Ghi nhận không thành công!"
                        }
                    }
                }
            }
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thành công!", ErrorCodeDefine.SUCCESS))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [ghiNhanTuVan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ThongTinGhiNhanP2Pv2>> layThongTinGhiNhanV2(String maTB) {
        ThongTinGhiNhanP2Pv2 rep = new ThongTinGhiNhanP2Pv2()
        List<ThongTinGhiNhanP2P> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            maTB = maTB.trim()
            List<ThongTinGhiNhanP2P> dt = new ArrayList<>()
            String sqlString = "{ call css.lay_thongtin_ghinhan_p2p(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, maTB, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        dt.add(new ThongTinGhiNhanP2P().convertDataFromDatabase(rs))
                    }
                }
            }
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 2, maTB, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ThongTinGhiNhanP2P dt_tmp = new ThongTinGhiNhanP2P()
                        dt_tmp.setId(rs.getLong("ID"))
                        dt_tmp.setName(rs.getString("NAME"))
                        //Vi VNPT Pay
                        if (dt_tmp.getId() == 1) {
                            if (dt.size() > 0) {
                                if (dt.get(0).getViVNPTPay() == "1") {
                                    String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                                        sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTViId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                                            if (rsNV != null && rsNV.next()) {
                                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                                item.setId(dt_tmp.getId())
                                                item.setName(dt_tmp.getName())
                                                item.setViVNPTPay(dt.get(0).getViVNPTPay())
                                                item.setNgayCnVi(dt.get(0).getNgayCnVi())
                                                item.setMaTB(maTB)
                                                item.setNhanVienGTViId(dt.get(0).getNhanVienGTViId())
                                                item.setMaNhanVienGTVi(rsNV.getString("MA_NV"))
                                                item.setTenNhanVienGTVi(rsNV.getString("TEN_NV"))
                                                item.setSdtNhanVienGTVi(rsNV.getString("SO_DT"))
                                                item.setDvNhanVienGTVi(rsNV.getString("TEN_DV"))
                                                ret.add(item)
                                            }
                                        }
                                    }
                                } else {
                                    ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                    item.setId(dt_tmp.getId())
                                    item.setName(dt_tmp.getName())
                                    item.setViVNPTPay(dt.get(0).getViVNPTPay())
                                    item.setNgayCnVi(dt.get(0).getNgayCnVi())
                                    item.setMaTB(maTB)
                                    item.setNhanVienGTViId(null)
                                    item.setMaNhanVienGTVi("")
                                    item.setTenNhanVienGTVi("")
                                    item.setSdtNhanVienGTVi("")
                                    item.setDvNhanVienGTVi("")
                                    ret.add(item)
                                }
                            } else {
                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                item.setId(dt_tmp.getId())
                                item.setName(dt_tmp.getName())
                                item.setViVNPTPay("0")
                                item.setNgayCnVi("")
                                item.setMaTB(maTB)
                                item.setNhanVienGTViId(null)
                                item.setMaNhanVienGTVi("")
                                item.setTenNhanVienGTVi("")
                                item.setSdtNhanVienGTVi("")
                                item.setDvNhanVienGTVi("")
                                ret.add(item)
                            }
                        } else if (dt_tmp.getId() == 2) { //Mobile Money
                            if (dt.size() > 0) {
                                if (dt.get(0).getMobileMoney() == "1") {
                                    String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                                        sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTMMId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                                            if (rsNV != null && rsNV.next()) {
                                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                                item.setId(dt_tmp.getId())
                                                item.setName(dt_tmp.getName())
                                                item.setMobileMoney(dt.get(0).getMobileMoney())
                                                item.setNgayCnMM(dt.get(0).getNgayCnMM())
                                                item.setMaTB(maTB)
                                                item.setNhanVienGTMMId(dt.get(0).getNhanVienGTMMId())
                                                item.setMaNhanVienGTMM(rsNV.getString("MA_NV"))
                                                item.setTenNhanVienGTMM(rsNV.getString("TEN_NV"))
                                                item.setSdtNhanVienGTMM(rsNV.getString("SO_DT"))
                                                item.setDvNhanVienGTMM(rsNV.getString("TEN_DV"))
                                                ret.add(item)
                                            }
                                        }
                                    }
                                } else {
                                    ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                    item.setId(dt_tmp.getId())
                                    item.setName(dt_tmp.getName())
                                    item.setMobileMoney(dt.get(0).getMobileMoney())
                                    item.setNgayCnMM(dt.get(0).getNgayCnMM())
                                    item.setMaTB(maTB)
                                    item.setNhanVienGTMMId(null)
                                    item.setMaNhanVienGTMM("")
                                    item.setTenNhanVienGTMM("")
                                    item.setSdtNhanVienGTMM("")
                                    item.setDvNhanVienGTMM("")
                                    ret.add(item)
                                }
                            } else {
                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                item.setId(dt_tmp.getId())
                                item.setName(dt_tmp.getName())
                                item.setMobileMoney("0")
                                item.setNgayCnMM("")
                                item.setMaTB(maTB)
                                item.setNhanVienGTMMId(null)
                                item.setMaNhanVienGTMM("")
                                item.setTenNhanVienGTMM("")
                                item.setSdtNhanVienGTMM("")
                                item.setDvNhanVienGTMM("")
                                ret.add(item)
                            }
                        } else if (dt_tmp.getId() == 3) { //Merchan P2P
                            if (dt.size() > 0) {
                                if (dt.get(0).getP2p() == "1") {
                                    String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                                        sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTP2PId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                                            if (rsNV != null && rsNV.next()) {
                                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                                item.setId(dt_tmp.getId())
                                                item.setName(dt_tmp.getName())
                                                item.setP2p(dt.get(0).getP2p())
                                                item.setNgayCnP2P(dt.get(0).getNgayCnP2P())
                                                item.setMaTB(maTB)
                                                item.setNhanVienGTP2PId(dt.get(0).getNhanVienGTP2PId())
                                                item.setMaNhanVienGTP2P(rsNV.getString("MA_NV"))
                                                item.setTenNhanVienGTP2P(rsNV.getString("TEN_NV"))
                                                item.setSdtNhanVienGTP2P(rsNV.getString("SO_DT"))
                                                item.setDvNhanVienGTP2P(rsNV.getString("TEN_DV"))
                                                ret.add(item)
                                            }
                                        }
                                    }
                                } else {
                                    ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                    item.setId(dt_tmp.getId())
                                    item.setName(dt_tmp.getName())
                                    item.setP2p(dt.get(0).getP2p())
                                    item.setNgayCnP2P(dt.get(0).getNgayCnP2P())
                                    item.setMaTB(maTB)
                                    item.setNhanVienGTP2PId(null)
                                    item.setMaNhanVienGTP2P("")
                                    item.setTenNhanVienGTP2P("")
                                    item.setSdtNhanVienGTP2P("")
                                    item.setDvNhanVienGTP2P("")
                                    ret.add(item)
                                }
                            } else {
                                ThongTinGhiNhanP2P item = new ThongTinGhiNhanP2P()
                                item.setId(dt_tmp.getId())
                                item.setName(dt_tmp.getName())
                                item.setP2p("0")
                                item.setNgayCnP2P("")
                                item.setMaTB(maTB)
                                item.setNhanVienGTP2PId(null)
                                item.setMaNhanVienGTP2P("")
                                item.setTenNhanVienGTP2P("")
                                item.setSdtNhanVienGTP2P("")
                                item.setDvNhanVienGTP2P("")
                                ret.add(item)
                            }
                        }
                    }
                }
            }
            rep.setHopLe(1)
            String soTB
            if (maTB.trim().indexOf("0") == 0) {
                maTB = maTB.trim().substring(1)
                soTB = "84" + maTB
            } else {
                soTB = "84" + maTB
                maTB = "0" + maTB.trim()
            }
            //Check thuê bao CCBS
            String token = session_.get(TOKEN)
            ccbsService.init(token)
            String resp = ccbsService.ts_tracuu_laytt_tb(soTB)
            try {
                TTThueBaoCCBSResponse ttThueBaoCCBSResponse = new Gson().fromJson(resp, TTThueBaoCCBSResponse.class)
                if (ttThueBaoCCBSResponse.getErrorCode() != null) {
                    if (ttThueBaoCCBSResponse.getErrorCode() != ErrorCodeDefine.SUCCESS) {
                        rep.setHopLe(0)
                    } else {
                        if (ttThueBaoCCBSResponse.getData() != null) {
                            if (ttThueBaoCCBSResponse.getData().get(0).getKhoso() == null || ttThueBaoCCBSResponse.getData().get(0).getKhoso().contains("ERR")) {
                                rep.setHopLe(0)
                            }
                        } else {
                            rep.setHopLe(0)
                        }
                    }
                } else {
                    rep.setHopLe(0)
                }
            } catch (Exception ex) {
                ex.printStackTrace()
                rep.setHopLe(0)
            }
            if (ret != null && ret.size() > 0) {
                rep.setThongTinGhiNhan(ret)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(rep, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [layThongTinGhiNhanV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> ghiNhanTuVanV2(TuVanMobileMoneyRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TienIchController] - [ghiNhanTuVanV2] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("requestBody", new JsonBuilder(requestBody).toPrettyString()).build())
            String maTB = requestBody.getMaTB()
            String maTBGoc = requestBody.getMaTB()
            if (maTB == null || maTB.trim().length() == 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không có thông tin số thuê bao", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            } else {
                if (!Utils.isPhone(maTB)) {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
//            String soTB
//            if (maTB.trim().indexOf("0") == 0) {
//                maTB = maTB.trim().substring(1)
//                soTB = "84" + maTB
//            } else {
//                soTB = "84" + maTB
//                maTB = "0" + maTB.trim()
//            }
//            //Check thuê bao CCBS
//            String token = session_.get(TOKEN)
//            ccbsService.init(token)
//            String resp = ccbsService.ts_tracuu_laytt_tb(soTB)
//            try {
//                TTThueBaoCCBSResponse ttThueBaoCCBSResponse = new Gson().fromJson(resp, TTThueBaoCCBSResponse.class)
//                if (ttThueBaoCCBSResponse.getErrorCode() != null) {
//                    if (ttThueBaoCCBSResponse.getErrorCode() != ErrorCodeDefine.SUCCESS) {
//                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ttThueBaoCCBSResponse.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//                    } else {
//                        if (ttThueBaoCCBSResponse.getData() != null) {
//                            if (ttThueBaoCCBSResponse.getData().get(0).getKhoso() == null || ttThueBaoCCBSResponse.getData().get(0).getKhoso().contains("ERR")) {
//                                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//                            }
//                        } else {
//                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin số thuê bao", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//                        }
//                    }
//                } else {
//                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//                }
//            } catch (Exception ex) {
//                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số thuê bao không hợp lệ", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//            }
            //Ghi nhận
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            int vkq_vnptpay = 0
            int vkq_mobile_money = 0
            int vkq_p2p = 0
            for (int i = 0; i < requestBody.getDsGhiNhan().size(); i++) {
                if (requestBody.getDsGhiNhan().get(i).getId() == 1) {
                    vkq_vnptpay = requestBody.getDsGhiNhan().get(i).getKetQua()
                } else if (requestBody.getDsGhiNhan().get(i).getId() == 2) {
                    vkq_mobile_money = requestBody.getDsGhiNhan().get(i).getKetQua()
                } else if (requestBody.getDsGhiNhan().get(i).getId() == 3) {
                    vkq_p2p = requestBody.getDsGhiNhan().get(i).getKetQua()
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Đã có lỗi khi ghi nhận kết quả, vui lòng thử lại sau (Mã lỗi: 404)!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }
            String message = ""
            String sqlString = "{ call css.smp_ghinhan_kq_mobile_money(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), maTBGoc, vkq_vnptpay, vkq_mobile_money, vkq_p2p,
                                     Long.parseLong(nhanVienId), Long.parseLong(donViId), Sql.VARCHAR]) { String rs ->
                    if (rs != null && rs == "OK") {
                        message = ""
                    } else {
                        if (rs != null) {
                            message = rs
                        } else {
                            message = "Ghi nhận không thành công!"
                        }
                    }
                }
            }
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Thành công!", ErrorCodeDefine.SUCCESS))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))

        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [ghiNhanTuVanV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsCauHoiKS_NT(DVITCareLayDsCauhoiTheoLoaiNVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        CauHoiKhaoSatData response = new CauHoiKhaoSatData()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call FIMS.LAY_DS_CAUHOI_THEOLOAINV_V3(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.vloainv_id, requestBody.vhinhthucks_id, requestBody.vhdtb_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        CauHoiKS item = new CauHoiKS().convertDataFromDatabase(rs)
                        if (item.getRchId() == 0) {
                            //Câu hỏi chính
                            List<CauHoiKS> cauHoiKSList = response.getDsCauHoiChinh()
                            if (cauHoiKSList.size() > 0) {
                                boolean existed = false
                                for (int i = 0; i < cauHoiKSList.size(); i++) {
                                    if (item.getNoiDung() != null && cauHoiKSList.get(i).getNoiDung() != null
                                            && item.getNoiDung().trim().toLowerCase() == cauHoiKSList.get(i).getNoiDung().trim().toLowerCase()) {
                                        existed = true
                                        response.getDsCauHoiChinh().get(i).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                                        break
                                    }
                                }
                                if (!existed) {
                                    response.getDsCauHoiChinh().add(new CauHoiKS().convertDataFromPojo(item))
                                    response.getDsCauHoiChinh().get(response.getDsCauHoiChinh().size() - 1).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                                }
                            } else {
                                response.getDsCauHoiChinh().add(new CauHoiKS().convertDataFromPojo(item))
                                response.getDsCauHoiChinh().get(0).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                            }
                        } else {
                            //Câu hỏi phụ
                            response.getDsCauHoiPhu().add(new CauHoiKS().convertDataFromPojo(item))
                        }
                    }
                }
            }
            for (int i = 0; i < response.getDsCauHoiChinh().size(); i++) {
                for (int j = 0; j < response.getDsCauHoiChinh().get(i).getDsTraLoi().size(); j++) {
                    List<CauHoiKS> cauHoiKSList = new ArrayList<>()
                    for (int k = 0; k < response.getDsCauHoiPhu().size(); k++) {
                        if (response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getRchId1() == response.getDsCauHoiPhu().get(k).getRchId()) {
                            cauHoiKSList.add(new CauHoiKS().convertDataFromPojo(response.getDsCauHoiPhu().get(k)))
                        }
                    }
                    List<CauHoiKS> dsCauHoiPhu = new ArrayList<>()
                    for (int k = 0; k < cauHoiKSList.size(); k++) {
                        if (dsCauHoiPhu.size() > 0) {
                            boolean existed = false
                            for (int m = 0; m < dsCauHoiPhu.size(); m++) {
                                if (cauHoiKSList.get(k).getNoiDung() != null && dsCauHoiPhu.get(m).getNoiDung() != null
                                        && cauHoiKSList.get(k).getNoiDung().trim().toLowerCase() == dsCauHoiPhu.get(m).getNoiDung().trim().toLowerCase()) {
                                    existed = true
                                    dsCauHoiPhu.get(i).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                                    break
                                }
                            }
                            if (!existed) {
                                dsCauHoiPhu.add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                                dsCauHoiPhu.get(response.getDsCauHoiChinh().size() - 1).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                            }
                        } else {
                            dsCauHoiPhu.add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                            dsCauHoiPhu.get(0).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                        }
                    }
                    response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).setDsCauHoiPhu(dsCauHoiPhu)
                }
            }
            Collections.sort(response.getDsCauHoiChinh(), new Comparator<CauHoiKS>() {
                @Override
                int compare(final CauHoiKS object1, final CauHoiKS object2) {
                    return object1.getThuTu() <=> object2.getThuTu()
                }
            })
            for (int i = 0; i < response.getDsCauHoiChinh().size(); i++) {
                Collections.sort(response.getDsCauHoiChinh().get(i).getDsTraLoi(), new Comparator<CauHoiKS>() {
                    @Override
                    int compare(final CauHoiKS object1, final CauHoiKS object2) {
                        return object1.getThuTuTL() <=> object2.getThuTuTL()
                    }
                })
                for (int j = 0; j < response.getDsCauHoiChinh().get(i).getDsTraLoi().size(); j++) {
                    Collections.sort(response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getDsCauHoiPhu(), new Comparator<CauHoiKS>() {
                        @Override
                        int compare(final CauHoiKS object1, final CauHoiKS object2) {
                            return object1.getThuTu() <=> object2.getThuTu()
                        }
                    })
                    for (int k = 0; k < response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getDsCauHoiPhu().size(); k++) {
                        Collections.sort(response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getDsCauHoiPhu().get(k).getDsTraLoi(), new Comparator<CauHoiKS>() {
                            @Override
                            int compare(final CauHoiKS object1, final CauHoiKS object2) {
                                return object1.getThuTuTL() <=> object2.getThuTuTL()
                            }
                        })
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getDsCauHoiChinh()))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsCauHoiKS() {
        String requestId = genericApi.generateRequestId()
        CauHoiKhaoSatData response = new CauHoiKhaoSatData()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call FIMS.LAY_DS_CAUHOI_THEOLOAINV_V2(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 6, 29, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        CauHoiKS item = new CauHoiKS().convertDataFromDatabase(rs)
                        if (item.getRchId() == 0) {
                            //Câu hỏi chính
                            List<CauHoiKS> cauHoiKSList = response.getDsCauHoiChinh()
                            if (cauHoiKSList.size() > 0) {
                                boolean existed = false
                                for (int i = 0; i < cauHoiKSList.size(); i++) {
                                    if (item.getNoiDung() != null && cauHoiKSList.get(i).getNoiDung() != null
                                            && item.getNoiDung().trim().toLowerCase() == cauHoiKSList.get(i).getNoiDung().trim().toLowerCase()) {
                                        existed = true
                                        response.getDsCauHoiChinh().get(i).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                                        break
                                    }
                                }
                                if (!existed) {
                                    response.getDsCauHoiChinh().add(new CauHoiKS().convertDataFromPojo(item))
                                    response.getDsCauHoiChinh().get(response.getDsCauHoiChinh().size() - 1).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                                }
                            } else {
                                response.getDsCauHoiChinh().add(new CauHoiKS().convertDataFromPojo(item))
                                response.getDsCauHoiChinh().get(0).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                            }
                        } else {
                            //Câu hỏi phụ
                            response.getDsCauHoiPhu().add(new CauHoiKS().convertDataFromPojo(item))
                        }
                    }
                }
            }
            for (int i = 0; i < response.getDsCauHoiChinh().size(); i++) {
                for (int j = 0; j < response.getDsCauHoiChinh().get(i).getDsTraLoi().size(); j++) {
                    List<CauHoiKS> cauHoiKSList = new ArrayList<>()
                    for (int k = 0; k < response.getDsCauHoiPhu().size(); k++) {
                        if (response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getRchId1() == response.getDsCauHoiPhu().get(k).getRchId()) {
                            cauHoiKSList.add(new CauHoiKS().convertDataFromPojo(response.getDsCauHoiPhu().get(k)))
                        }
                    }
                    List<CauHoiKS> dsCauHoiPhu = new ArrayList<>()
                    for (int k = 0; k < cauHoiKSList.size(); k++) {
                        if (dsCauHoiPhu.size() > 0) {
                            boolean existed = false
                            for (int m = 0; m < dsCauHoiPhu.size(); m++) {
                                if (cauHoiKSList.get(k).getNoiDung() != null && dsCauHoiPhu.get(m).getNoiDung() != null
                                        && cauHoiKSList.get(k).getNoiDung().trim().toLowerCase() == dsCauHoiPhu.get(m).getNoiDung().trim().toLowerCase()) {
                                    existed = true
                                    dsCauHoiPhu.get(i).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                                    break
                                }
                            }
                            if (!existed) {
                                dsCauHoiPhu.add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                                dsCauHoiPhu.get(response.getDsCauHoiChinh().size() - 1).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                            }
                        } else {
                            dsCauHoiPhu.add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                            dsCauHoiPhu.get(0).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(cauHoiKSList.get(k)))
                        }
                    }
                    response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).setDsCauHoiPhu(dsCauHoiPhu)
                }
            }
            Collections.sort(response.getDsCauHoiChinh(), new Comparator<CauHoiKS>() {
                @Override
                int compare(final CauHoiKS object1, final CauHoiKS object2) {
                    return object1.getThuTu() <=> object2.getThuTu()
                }
            })
            for (int i = 0; i < response.getDsCauHoiChinh().size(); i++) {
                Collections.sort(response.getDsCauHoiChinh().get(i).getDsTraLoi(), new Comparator<CauHoiKS>() {
                    @Override
                    int compare(final CauHoiKS object1, final CauHoiKS object2) {
                        return object1.getThuTuTL() <=> object2.getThuTuTL()
                    }
                })
                for (int j = 0; j < response.getDsCauHoiChinh().get(i).getDsTraLoi().size(); j++) {
                    Collections.sort(response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getDsCauHoiPhu(), new Comparator<CauHoiKS>() {
                        @Override
                        int compare(final CauHoiKS object1, final CauHoiKS object2) {
                            return object1.getThuTu() <=> object2.getThuTu()
                        }
                    })
                    for (int k = 0; k < response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getDsCauHoiPhu().size(); k++) {
                        Collections.sort(response.getDsCauHoiChinh().get(i).getDsTraLoi().get(j).getDsCauHoiPhu().get(k).getDsTraLoi(), new Comparator<CauHoiKS>() {
                            @Override
                            int compare(final CauHoiKS object1, final CauHoiKS object2) {
                                return object1.getThuTuTL() <=> object2.getThuTuTL()
                            }
                        })
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response.getDsCauHoiChinh()))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsDaTraLoi(Long hdtbId) {
        String requestId = genericApi.generateRequestId()
        List<CauHoiKS> response = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call fims.lay_ct_traloi_kscl_goicuoc(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), hdtbId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        CauHoiKS item = new CauHoiKS()
                        item.setCauHoiId(rs.getLong("CAUHOI_ID"))
                        item.setTraLoiId(rs.getLong("TRALOI_ID"))
                        item.setTltbId(rs.getLong("TBTL_ID"))
                        item.setNoiDung(rs.getString("NOIDUNG"))
                        response.add(item)
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> cnDanhGia(CapNhatKSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TienIchController] - [cnDanhGia] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiDungId = session_.get(NGUOIDUNG_ID)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(USERNAME)
            String dsTL = "["
            if (requestBody.getDsTraLoi() != null && requestBody.getDsTraLoi().size() > 0) {
                for (int i = 0; i < requestBody.getDsTraLoi().size(); i++) {
                    dsTL += "{"
                    dsTL += "\"cauhoi_id\":\"" + requestBody.getDsTraLoi().get(i).getCauHoiId() + "\","
                    dsTL += "\"traloi_id\":\"" + requestBody.getDsTraLoi().get(i).getTraLoiId() + "\","
                    dsTL += "\"noidung\":\"" + requestBody.getDsTraLoi().get(i).getNoiDung() + "\""
                    if (i == requestBody.getDsTraLoi().size() - 1) {
                        dsTL += "}"
                    } else {
                        dsTL += "},"
                    }
                }
            }
            dsTL += "]"
            String message = ""
            Long vhtks_id
            if (requestBody.getHtks_id() == 0 || requestBody.getHtks_id() == null){
                vhtks_id = 27
            }else{
                vhtks_id = requestBody.getHtks_id()
            }
            String sqlString = "{ call ? := FIMS.fn_capnhat_khaosat_nvkt_v2(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.getHdtbId(), requestBody.getTltbId(), requestBody.getTenNV(), requestBody.getSdtNV(),
                                     Long.parseLong(nhanVienId), vhtks_id, 1, requestBody.getGhiChu(), dsTL, nguoiCn, mayCn, ipCn, Long.parseLong(nguoiDungId)]) { String rs ->
                    if (rs != null) {
                        if (rs.trim().toUpperCase() == 'OK') {
                            message = ""
                        } else {
                            message = rs
                        }
                    } else {
                        message = ""
                    }
                }
            }
            loggingManaged.info("[TienIchController] - [cnDanhGia] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [cnDanhGia] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> doiPassIMS(DoiPassIMSRequest body) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[TienIchController] - [doiPassIMS] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("body", body).build())
            if (body.getNewPassword() == null || body.getNewPassword().length() == 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Password IMS chưa nhập!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String mayCN = "VNPT Employee"
            String nguoiCN = session_.get(USERNAME)
            String str = "select tentat from css.tinh where tinh_id=?"
            GroovyRowResult dtTinhBanCheo = null
            dataUtils.init(phanVungId, dataSrc)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                dtTinhBanCheo = sql.firstRow(dataUtils.replaceSchema(str), [phanVungId])
            }
            if (dtTinhBanCheo != null && !dtTinhBanCheo.isEmpty()) {
                String matinhBanCheo = dtTinhBanCheo.get("tentat").toString().toUpperCase()
                String maTinhBan = matinhBanCheo
                if (matinhBanCheo == "KHDN") maTinhBan = "dbn"
                if (maTinhBan == "THA" || maTinhBan == "LCI") {
                    if (body.getNewPassword().length() != 12) {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Password IMS phải đúng 12 ký tự!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                }
                boolean ims_vtn = false
                String dauSoIMSStr = ""
                str = "select dauso_ims from css.tinh where tentat = ?"
                GroovyRowResult dauSoIMS = null
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    dauSoIMS = sql.firstRow(dataUtils.replaceSchema(str), [maTinhBan])
                }
                if (dauSoIMS != null && !dauSoIMS.isEmpty()) {
                    dauSoIMSStr = dauSoIMS.get("dauso_ims").toString()
                }
                if (body.getLoaiTBId() != 62 && body.getLoaiTBId() != 63) {
                    //region IMS CŨ
                    String token = session_.get(TOKEN)
                    vtnService.init(token)
                    SetPasswordForRoot req = new SetPasswordForRoot()
                    req.setExternal_id(dauSoIMSStr + body.getMaTB())
                    req.setSubscirber_pass(body.getNewPassword())
                    String kq = vtnService.setPasswordForRoot(req)
                    if (kq == "OK") {
                        ims_vtn = true
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    //endregion
                } else {
                    //region IMS mới
                    String loaiTBIdVTNStr = ""
                    str = "select MA_LHTB from css.loaihinh_tb where LOAITB_ID = ?"
                    GroovyRowResult loaiTBIdVTN = null
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        loaiTBIdVTN = sql.firstRow(dataUtils.replaceSchema(str), [body.getLoaiTBId()])
                    }
                    if (loaiTBIdVTN != null && !loaiTBIdVTN.isEmpty()) {
                        loaiTBIdVTNStr = loaiTBIdVTN.get("MA_LHTB").toString()
                    }
                    if (loaiTBIdVTNStr == "-1" || loaiTBIdVTNStr == "") {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "[DHSX] Không lấy được mã loại hình", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    String token = session_.get(TOKEN)
                    spsService.init(token)
                    DoiPass req = new DoiPass()
                    String msisdn = dauSoIMSStr + body.getMaTB()
                    String user_password = body.getNewPassword()
                    String service_action = loaiTBIdVTNStr == "SIP" ? "ism-sip-change-pass" : "ism-pots-change-pass"
                    String kq = spsService.ts_ism_modify_subscriber(msisdn, service_action, user_password)
                    if (kq == "OK") {
                        ims_vtn = true
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, kq, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    //endregion
                }
                if (ims_vtn) {
                    String message = ""
                    String sqlString = "{ ? = call css.fn_capnhat_matkhau_ims(?,?,?,?,?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), body.getMaTB(), body.getKieu(), body.getKieu() == 1 ? body.getThueBaoId() : body.getHdtbId(),
                                             body.getNewPassword(), Long.parseLong(nhanVienId), Long.parseLong(donViId), mayCN, nguoiCN]) { String rs ->
                            if (rs != null) {
                                if (rs.trim().toUpperCase() == 'OK') {
                                    message = ""
                                } else {
                                    message = rs
                                }
                            } else {
                                message = ""
                            }
                        }
                    }
                    loggingManaged.info("[TienIchController] - [doiPassIMS] - Response: ",
                            new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
                    if (message == "") {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null))
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Có lỗi đổi Pass IMS", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Không tìm thấy thông tin tỉnh!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [doiPassIMS] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<List>> LayDSThietBiTheoThueBao(LayDSThietBiTheoThueBaoRequest requestBody) {
        List data = []
        List output = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String token = session_.get(TOKEN)
            String ma_tinh = session_.get(MA_TINH)

            ctsService.init(token)
            Gson gson = new Gson()

            def response = [:]

            String sqlString = "{ call css.so_lay_ds_thietbi_theo_madoicap(?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vma_tb, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            for (int j = 0; j < data.size(); j++) {
                if (data.size() > 0 && data[j]["serial_gp"] != null && data[j]["loaitb_id"] != 58) {
                    output.add([
                            serialNumber: data[j]["serial_gp"],
                            maTB        : requestBody.vma_tb,
                    ])
                } else {
                    if (data[j]["loaitb_id"] == 58) {
                        List data_2 = []
                        String sqlString_2 = "{ call CSS.sp_lay_port_theo_matb(?,?,?,?) }"
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(sqlString_2,
                                    [phanVungId, data[j]['ma_tb'], 2, CURSOR_PARAMETER]) {
                                ResultSet rs_2 ->
                                    data_2 = resultSetToObject.getDynamicData(rs_2)
                            }
                        }
                        for (int i = 0; i < data_2.size(); i++) {
                            response = ctsService.testOnuPort(data_2[i]["ip"], String.valueOf(data_2[i]["rack"]), String.valueOf(data_2[i]["shelf"]), String.valueOf(data_2[i]["slot"]), String.valueOf(data_2[i]["sport"]), String.valueOf(data_2[i]["vpi"]))
                            if (response['data'] != null)
                                if (response['data']['OnuStatusStr'] != null && response['data']['OnuStatusStr'].toString().toUpperCase() == 'OFF')
                                    output.add([
                                            serialNumber: data[j]["serial_gp"],
                                            maTB        : requestBody.vma_tb,
                                    ])
                                else
                                    output.add([
                                            serialNumber: response['data']['OnuSerial'],
                                            maTB        : requestBody.vma_tb,
                                    ])
                        }
                    }
                }
            }
            if (output.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(output))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [LayDSThietBiTheoThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<List>> TraCuuTTTBFiberMyTVBeta(TraCuuTTTBFiberMyTVBetaRequest requestBody) {
        List data = []
        List output = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String token = session_.get(TOKEN)
            String ma_tinh = session_.get(MA_TINH)

            def response = post(requestBody.ma_fiber)

            if (response.Code == 1) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response.Data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [TraCuuTTTBFiberMyTVBeta] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<?>> TraCuuTTTBFiberMyTVBeta2(TraCuuTTTBFiberMyTVBeta2Request requestBody) {
        List data = []
        List output = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)

            String sqlString = "{ call css.sp_kt_canhbao_b2a(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, requestBody.khachHangId, requestBody.longtitude, requestBody.latitude, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            String jsonString = data[0]?.l_value
            def ret = []
            if (jsonString != null && jsonString.trim() != "") {
                ret = new JsonSlurper().parseText(jsonString)
            }

//            sqlString = "SELECT ma_tb from css.db_thuebao a where a.phanvung_id = ? and a.khachhang_id = ? and a.trangthaitb_id = 1 and loaitb_id = 58"
//            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
//            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                def rows = sql.rows(sqlString, [phanVungId, requestBody.khachHangId])
//                for (r in rows) {
//                    String ma_tb = r.get("ma_tb").toString()
//                    def response = post(ma_tb)
//                    if (response.Code == 1) {
//                        ret.add([
//                                stt        : ret.size() + 1,
//                                name       : "Khách hàng fiber",
//                                mota       : "Khách hàng fiber không có myTV fix nhưng sử dụng smarTV",
//                                icon       : "ic_search_file",
//                                id_cn      : 0,
//                                url        : "",
//                                is_required: 0
//                        ])
//                        break
//                    }
//                }
//            }


            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [TraCuuTTTBFiberMyTVBeta2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ThongTinGhiNhanP2Pv2New>> layThongTinGhiNhanV3(String maTB) {
        ThongTinGhiNhanP2Pv2New rep = new ThongTinGhiNhanP2Pv2New()
        List<ThongTinGhiNhanP2PNew> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            maTB = maTB.trim()
            List<ThongTinGhiNhanP2P> dt = new ArrayList<>()
            tichHopService.init(session_.get(TOKEN).toString())
            String isVNPTPay = "0"
            String isMobiMoney = "0"
            String isMerchan = "0"

            Map<String, Object> obPay = tichHopService.checkUserPayByPhone(maTB)
            if (obPay.errorCode == 0) {
                isVNPTPay = obPay.data.isExist.toString()
                isMobiMoney = obPay.data.hasMMAccount == 1 ? "1" : "0"
                isMerchan = obPay.data.merchantP2pStatus.toString().toInteger() == 1 ? "1" : "0"
            }

            String sqlString = "{ call css.lay_thongtin_ghinhan_p2p(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 1, maTB, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        dt.add(new ThongTinGhiNhanP2P().convertDataFromDatabase(rs))
                    }
                }
            }


            if (dt.size() > 0) {
                String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                    sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTViId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                        if (rsNV != null && rsNV.next()) {
                            ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                            item.setId(0)
                            item.setService("Ví VNPT PAY")
                            item.setMediaStatus(isVNPTPay.toLong())
                            item.setNgayCn(dt.get(0).getNgayCnVi())
                            item.setMaTB(maTB)
                            item.setIdNv(dt.get(0).getNhanVienGTViId())
                            item.setMaNv(rsNV.getString("MA_NV"))
                            item.setTenNv(rsNV.getString("TEN_NV"))
                            item.setSdtNv(rsNV.getString("SO_DT"))
                            item.setDonVi(rsNV.getString("TEN_DV"))
                            ret.add(item)
                        } else {
                            ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                            item.setId(0)
                            item.setService("Ví VNPT PAY")
                            item.setMediaStatus(isVNPTPay.toLong())
                            item.setNgayCn("")
                            item.setMaTB(maTB)
                            item.setIdNv(0)
                            item.setMaNv("")
                            item.setTenNv("")
                            item.setSdtNv("")
                            item.setDonVi("")
                            ret.add(item)
                        }
                    }
                }
            } else {
                ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                item.setId(0)
                item.setService("Ví VNPT PAY")
                item.setMediaStatus(isVNPTPay.toLong())
                item.setNgayCn("")
                item.setMaTB(maTB)
                item.setIdNv(0)
                item.setMaNv("")
                item.setTenNv("")
                item.setSdtNv("")
                item.setDonVi("")
                ret.add(item)
            }
            if (dt.size() > 0) {
                String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                    sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTMMId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                        if (rsNV != null && rsNV.next()) {
                            ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                            item.setId(1)
                            item.setService("Mobile Money")
                            item.setMediaStatus(isMobiMoney.toLong())
                            item.setNgayCn(dt.get(0).getNgayCnMM())
                            item.setMaTB(maTB)
                            item.setIdNv(dt.get(0).getNhanVienGTMMId())
                            item.setMaNv(rsNV.getString("MA_NV"))
                            item.setTenNv(rsNV.getString("TEN_NV"))
                            item.setSdtNv(rsNV.getString("SO_DT"))
                            item.setDonVi(rsNV.getString("TEN_DV"))
                            ret.add(item)
                        } else {
                            ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                            item.setId(1)
                            item.setService("Mobile Money")
                            item.setMediaStatus(isMobiMoney.toLong())
                            item.setNgayCn("")
                            item.setMaTB(maTB)
                            item.setIdNv(0)
                            item.setMaNv("")
                            item.setTenNv("")
                            item.setSdtNv("")
                            item.setDonVi("")
                            ret.add(item)
                        }
                    }
                }
            } else {
                ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                item.setId(1)
                item.setService("Mobile Money")
                item.setMediaStatus(isMobiMoney.toLong())
                item.setNgayCn("")
                item.setMaTB(maTB)
                item.setIdNv(0)
                item.setMaNv("")
                item.setTenNv("")
                item.setSdtNv("")
                item.setDonVi("")
                ret.add(item)
            }
            if (dt.size() > 0) {
                String sqlNVString = "{ call css.lay_tt_nhanvien_gt_myvnpt_smp(?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sqlNV ->
                    sqlNV.call(sqlNVString, [Long.parseLong(phanVungId), dt.get(0).getNhanVienGTP2PId(), CURSOR_PARAMETER]) { ResultSet rsNV ->
                        if (rsNV != null && rsNV.next()) {
                            ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                            item.setId(2)
                            item.setService("Merchan P2P")
                            item.setMediaStatus(isMobiMoney.toLong())
                            item.setNgayCn(dt.get(0).getNgayCnP2P())
                            item.setMaTB(maTB)
                            item.setIdNv(dt.get(0).getNhanVienGTP2PId())
                            item.setMaNv(rsNV.getString("MA_NV"))
                            item.setTenNv(rsNV.getString("TEN_NV"))
                            item.setSdtNv(rsNV.getString("SO_DT"))
                            item.setDonVi(rsNV.getString("TEN_DV"))
                            ret.add(item)
                        } else {
                            ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                            item.setId(2)
                            item.setService("Merchan P2p")
                            item.setMediaStatus(isMobiMoney.toLong())
                            item.setNgayCn("")
                            item.setMaTB(maTB)
                            item.setIdNv(0)
                            item.setMaNv("")
                            item.setTenNv("")
                            item.setSdtNv("")
                            item.setDonVi("")
                            ret.add(item)
                        }
                    }
                }
            } else {
                ThongTinGhiNhanP2PNew item = new ThongTinGhiNhanP2PNew()
                item.setId(2)
                item.setService("Merchan P2p")
                item.setMediaStatus(isMobiMoney.toLong())
                item.setNgayCn("")
                item.setMaTB(maTB)
                item.setIdNv(0)
                item.setMaNv("")
                item.setTenNv("")
                item.setSdtNv("")
                item.setDonVi("")
                ret.add(item)
            }


            rep.setHopLe(1)
            String soTB
            if (maTB.trim().indexOf("0") == 0) {
                maTB = maTB.trim().substring(1)
                soTB = "84" + maTB
            } else {
                soTB = "84" + maTB
                maTB = "0" + maTB.trim()
            }
            //Check thuê bao CCBS
            String token = session_.get(TOKEN)
            ccbsService.init(token)
            String resp = ccbsService.ts_tracuu_laytt_tb(soTB)
            try {
                TTThueBaoCCBSResponse ttThueBaoCCBSResponse = new Gson().fromJson(resp, TTThueBaoCCBSResponse.class)
                if (ttThueBaoCCBSResponse.getErrorCode() != null) {
                    if (ttThueBaoCCBSResponse.getErrorCode() != ErrorCodeDefine.SUCCESS) {
                        rep.setHopLe(0)
                    } else {
                        if (ttThueBaoCCBSResponse.getData() != null) {
                            if (ttThueBaoCCBSResponse.getData().get(0).getKhoso() == null || ttThueBaoCCBSResponse.getData().get(0).getKhoso().contains("ERR")) {
                                rep.setHopLe(0)
                            }
                        } else {
                            rep.setHopLe(0)
                        }
                    }
                } else {
                    rep.setHopLe(0)
                }
            } catch (Exception ex) {
                ex.printStackTrace()
                rep.setHopLe(0)
            }
            if (ret != null && ret.size() > 0) {
                rep.setThongTinGhiNhan(ret)
                return ResponseEntity.ok().body(BaseResponseBuilder.of(rep, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[TienIchController] - [layThongTinGhiNhanV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    def post(String ma_fiber) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build()
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json")
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{ \r\n    \"username\": \"mytv_beta_api\", \r\n    \"password\": \"mytv_beta_api@12356\", \r\n    \"ma_fiber\": \"" + ma_fiber + "\"\r\n}")
        Request request = new Request.Builder()
                .url("http://gatewayccos.vnpt.vn/api/mytvbeta/tracuut3")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build()
        Response response = client.newCall(request).execute()

        return new JsonSlurper().parseText(response.body().string())
    }

    //

}
