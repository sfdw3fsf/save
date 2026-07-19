package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.CDSDiaBanApi
import io.swagger.model.dto.DanhMuc
import io.swagger.model.dto.cdsdiaban.ChiTietKR001_007
import io.swagger.model.dto.cdsdiaban.Kri
import io.swagger.model.dto.cdsdiaban.TongHopKR001_007
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.TTKD_KD_008_009_GW
import io.swagger.model.response.TTKD_KD_008_009_Response
import io.swagger.model.response.TTKD_KD_017_GW
import io.swagger.model.response.TTKD_KD_017_Response
import io.swagger.model.response.TTKD_KD_CCBSResponse
import io.swagger.model.response.cdsdiaban.BRCD_KHONG_PSC
import io.swagger.model.response.cdsdiaban.BRCD_KHONG_PSC_CT
import io.swagger.model.response.cdsdiaban.BRCD_KHONG_PSLL
import io.swagger.model.response.cdsdiaban.BRCD_KHONG_PSLL_CT
import io.swagger.model.response.cdsdiaban.CheckHRMCodeResponse
import io.swagger.model.response.cdsdiaban.DLDashboardTH
import io.swagger.model.response.cdsdiaban.DLDiaBanLyDoHuy
import io.swagger.model.response.cdsdiaban.DLDiaBanLyDoNgung
import io.swagger.model.response.cdsdiaban.DLDiaBanNguyenNhanHuy
import io.swagger.model.response.cdsdiaban.DLDiaBanNguyenNhanNgung
import io.swagger.model.response.cdsdiaban.DLDiaBanTongQuatHuy
import io.swagger.model.response.cdsdiaban.DLDiaBanTongQuatNgung
import io.swagger.model.response.cdsdiaban.TTKD_AM_002
import io.swagger.model.response.cdsdiaban.TTKD_KD_001_007_DCRS
import io.swagger.model.response.cdsdiaban.TTKD_KD_001_007_DCRS_Resp
import io.swagger.model.response.cdsdiaban.TTKD_KD_001_007_MNP
import io.swagger.model.response.cdsdiaban.TTKD_KD_001_007_MNP_Resp
import io.swagger.model.response.cdsdiaban.TTKD_KD_009
import io.swagger.model.response.cdsdiaban.TTKD_KD_009_MobileMoney
import io.swagger.model.response.cdsdiaban.TTKD_KD_008
import io.swagger.model.response.cdsdiaban.TTKD_KD_009_VnptPay
import io.swagger.model.response.cdsdiaban.TTKD_KD_010
import io.swagger.model.response.cdsdiaban.TTKD_KD_011
import io.swagger.model.response.cdsdiaban.TTKD_KD_011_014
import io.swagger.model.response.cdsdiaban.TTKD_KD_012
import io.swagger.model.response.cdsdiaban.TTKD_KD_013
import io.swagger.model.response.cdsdiaban.TTKD_KD_014
import io.swagger.model.response.cdsdiaban.TTKD_KD_017
import io.swagger.model.response.cdsdiaban.TTKD_KD_017_MyTVOTT
import io.swagger.model.response.cdsdiaban.TTKD_KD_017_TuVan
import io.swagger.model.response.cdsdiaban.TTKD_KD_018
import io.swagger.model.response.cdsdiaban.TTKD_KD_018_BD_CoCau
import io.swagger.model.response.cdsdiaban.TTKD_KD_018_BD_Cot
import io.swagger.model.response.cdsdiaban.TTKD_KD_018_DS
import io.swagger.model.response.cdsdiaban.TTKD_KD_019
import io.swagger.model.response.cdsdiaban.TTKD_KD_019_BD_CoCau
import io.swagger.model.response.cdsdiaban.TTKD_KD_019_BD_Cot
import io.swagger.model.response.cdsdiaban.TTKD_KD_019_DS
import io.swagger.model.response.cdsdiaban.TTKD_KD_021
import io.swagger.model.response.cdsdiaban.TTKD_KD_029
import io.swagger.model.response.cdsdiaban.TTKD_KD_030_032_034_035
import io.swagger.model.response.cdsdiaban.TTKD_KD_CCBS
import io.swagger.model.response.cdsdiaban.TTVT_KT_004
import io.swagger.model.response.cdsdiaban.TTVT_KT_005
import io.swagger.model.response.cdsdiaban.TTVT_KT_026
import io.swagger.model.response.cdsdiaban.TTVT_KT_027
import io.swagger.model.response.cdsdiaban.TTVT_KT_032
import io.swagger.model.response.cdsdiaban.TTVT_KT_040
import io.swagger.model.response.cdsdiaban.TTVT_KT_052
import io.swagger.model.response.cdsdiaban.TTVT_KT_053
import io.swagger.model.response.cdsdiaban.TTVT_KT_054
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_005
import io.swagger.model.response.cdsdiaban.TTVT_KT_006
import io.swagger.model.response.cdsdiaban.TTVT_KT_007
import io.swagger.model.response.cdsdiaban.TTVT_KT_010
import io.swagger.model.response.cdsdiaban.TTVT_KT_011
import io.swagger.model.response.cdsdiaban.TTVT_KT_012
import io.swagger.model.response.cdsdiaban.TTVT_KT_013
import io.swagger.model.response.cdsdiaban.TTKD_KD_015
import io.swagger.model.response.cdsdiaban.TTVT_KT_016
import io.swagger.model.response.cdsdiaban.TTVT_KT_018
import io.swagger.model.response.cdsdiaban.TTVT_KT_019
import io.swagger.model.response.cdsdiaban.TTVT_KT_020
import io.swagger.model.response.cdsdiaban.TTVT_KT_023
import io.swagger.model.response.cdsdiaban.TTVT_KT_024
import io.swagger.model.response.cdsdiaban.TTVT_KT_029
import io.swagger.model.response.cdsdiaban.TTVT_KT_030
import io.swagger.model.response.cdsdiaban.TTVT_KT_031
import io.swagger.model.response.cdsdiaban.TTVT_KT_033
import io.swagger.model.response.cdsdiaban.TTVT_KT_035
import io.swagger.model.response.cdsdiaban.TTVT_KT_039
import io.swagger.model.response.cdsdiaban.TTVT_KT_041
import io.swagger.model.response.cdsdiaban.TTVT_KT_043
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_001
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_002
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_003
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_004
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_006
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_010
import io.swagger.model.response.cdsdiaban.TTVT_TTKT_011
import io.swagger.model.dto.ThongTinMangNet
import io.swagger.model.response.cdsdiaban.ThongTinChiTietTbNgungHuy
import io.swagger.model.response.cdsdiaban.ThongTinChiTietThuCuocGachNo
import io.swagger.model.response.cdsdiaban.ThongTinMangNetRespones
import io.swagger.model.response.cdsdiaban.khieunai_brcd
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.ep.MNPClient
import vn.vnptit.sapi.oneapp.ep.SmcsClient
import vn.vnptit.sapi.oneapp.ep.VnptPayClient
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.service.CCBSService
import vn.vnptit.sapi.oneapp.util.*

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet
import java.util.stream.Collectors

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class CDSDiaBanController implements CDSDiaBanApi {
    private HttpServletRequest request
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    GenericApi genericApi
    @Autowired
    MNPClient mnpClient
    @Autowired
    SmcsClient smcsClient
    @Autowired
    CCBSService ccbsService
    @Autowired
    VnptPayClient vnptPayClient
    @Autowired
    DataUtils dataUtils

    @Autowired
    CDSDiaBanController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }
 
    @Override
    ResponseEntity<BaseResponse<List<Kri>>> dsKriV2() {
        String requestId = genericApi.generateRequestId()
        List<Kri> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dsKriV2] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                String sqlString = "with t as (\n" +
                        "select * from css.kri k\n" +
                        "inner join css.kri_config c on k.ma = c.ma_kri and c.nhom_hrm = ? \n" +
                        "left join css.kri_user_log l on k.ma = l.kri and l.user_log = ? \n" +
                        ")\n" +
                        "select MA,TEN,ICON,URL,NHOM,count(1) SL from t group by MA,TEN,ICON,URL,NHOM order by count(1) desc"
                Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->

                    sql.eachRow(sqlString, [responsePay.getInfo().getMaCDS().trim(), username]) { rs ->
                        response.add(new Kri(ma: Utils.getStringDataByKey(rs, "MA"), ten: Utils.getStringDataByKey(rs, "TEN"),
                                url: Utils.getStringDataByKey(rs, "URL"), icon: Utils.getStringDataByKey(rs, "ICON"), nhom: Utils.getStringDataByKey(rs, "NHOM")))
                    }
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, responsePay.getErrorDescription(), ErrorCodeDefine.FOR_BIDDEN))
            }
            loggingManaged.info("[CDSDiaBanController] - [dsKriV2] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsKriV2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<List<Kri>>> dsKri() {
        String requestId = genericApi.generateRequestId()
        List<Kri> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dsKri] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Kri kri1 = new Kri()
            kri1.setMa("TTKD_KD_001_007")
            kri1.setTen("Bán hàng và theo dõi kết quả bán hàng trên mobile app.")
            kri1.setUrl("viewpage1")
            kri1.setIcon("")
            kri1.setNhom("Bán hàng")
            response.add(kri1)

            Kri kri2 = new Kri()
            kri2.setMa("TTKD_KD_009")
            kri2.setTen("Cài đặt và đánh giá kết quả cài đặt app để thanh toán trực tuyến qua VNPT Pay.")
            kri2.setUrl("viewpage2")
            kri2.setIcon("")
            kri2.setNhom("Tư vấn cài APP")
            response.add(kri2)

            Kri kri3 = new Kri()
            kri3.setMa("TTVT_KT_013")
            kri3.setTen("Lịch sử hẹn xử lý.")
            kri3.setUrl("viewpage3")
            kri3.setIcon("")
            kri3.setNhom("")
            response.add(kri3)

            Kri kri4 = new Kri()
            kri4.setMa("TTKD_KD_010")
            kri4.setTen("Xuất hóa đơn.")
            kri4.setUrl("viewpage4")
            kri4.setIcon("")
            kri4.setNhom("")
            response.add(kri4)

            Kri kri5 = new Kri()
            kri5.setMa("TTKD_KD_011_014")
            kri5.setTen("Chăm sóc khách hàng.")
            kri5.setUrl("viewpage5")
            kri5.setIcon("")
            kri5.setNhom("")
            response.add(kri5)

            Kri kri6 = new Kri()
            kri6.setMa("TTKD_KD_015")
            kri6.setTen("Khách hàng tiềm năng.")
            kri6.setUrl("viewpage6")
            kri6.setIcon("")
            kri6.setNhom("")
            response.add(kri6)

            Kri kri7 = new Kri()
            kri7.setMa("TTVT_KT_004")
            kri7.setTen("Hợp đồng dùng thử.")
            kri7.setUrl("viewpage7")
            kri7.setIcon("")
            kri7.setNhom("")
            response.add(kri7)

            Kri kri8 = new Kri()
            kri8.setMa("TTVT_KT_043")
            kri8.setTen("Số lượng khảo sát phiếu B2A.")
            kri8.setUrl("viewpage8")
            kri8.setIcon("")
            kri8.setNhom("Thu thập thông tin")
            response.add(kri8)

            Kri kri9 = new Kri()
            kri9.setMa("TTVT_KT_001_002")
            kri9.setTen("NVKT thực hiện tư vấn cho khách hàng các dịch vụ của VNPT.")
            kri9.setUrl("viewpage9")
            kri9.setIcon("")
            kri9.setNhom("")
            response.add(kri9)

            Kri kri10 = new Kri()
            kri10.setMa("TTVT_KT_016")
            kri10.setTen("Quản lý thông tin báo hỏng của thuê bao từ 2 lần trở lên trong vòng 30 ngày.")
            kri10.setUrl("viewpage10")
            kri10.setIcon("")
            kri10.setNhom("")
            response.add(kri10)

            Kri kri11 = new Kri()
            kri11.setMa("TTVT_KT_018")
            kri11.setTen("Quản lý các thuê bao hỏng còn tồn trên hệ thống.")
            kri11.setUrl("viewpage11")
            kri11.setIcon("")
            kri11.setNhom("Báo hỏng")
            response.add(kri11)

            Kri kri12 = new Kri()
            kri12.setMa("TTVT_KT_006")
            kri12.setTen("Quản lý theo dõi thống kê chi tiết máy lắp đặt mới.")
            kri12.setUrl("viewpage12")
            kri12.setIcon("")
            kri12.setNhom("Lắp đặt")
            response.add(kri12)

            Kri kri13 = new Kri()
            kri13.setMa("TTVT_KT_001")
            kri13.setTen("Bán hàng và theo dõi kết quả bán hàng trên mobile app.")
            kri13.setUrl("viewpage13")
            kri13.setIcon("")
            kri13.setNhom("")
            response.add(kri13)

            Kri kri14 = new Kri()
            kri14.setMa("TTVT_KT_031")
            kri14.setTen("Theo dõi kết quả thực hiện bảo dưỡng mạng ngoại vi.")
            kri14.setUrl("viewpage14")
            kri14.setIcon("")
            kri14.setNhom("Phiếu bảo dưỡng")
            response.add(kri14)

            Kri kri15 = new Kri()
            kri15.setMa("TTVT_KT_011")
            kri15.setTen("Theo dõi thống kê thời gian xử lý lắp đặt mới dịch vụ theo nhân viên DVVT.")
            kri15.setUrl("viewpage15")
            kri15.setIcon("")
            kri15.setNhom("Lắp đặt")
            response.add(kri15)

            Kri kri16 = new Kri()
            kri16.setMa("TTVT_TTKT_005")
            kri16.setTen("Theo dõi thống kê số lượng phiếu xử lý sự cố mạng ngoại vi.")
            kri16.setUrl("viewpage16")
            kri16.setIcon("")
            kri16.setNhom("")
            response.add(kri16)

            Kri kri17 = new Kri()
            kri17.setMa("TTVT_KT_020")
            kri17.setTen("Theo dõi thống kê thời gian xử lý dịch vụ theo nhân viên DVVT.")
            kri17.setUrl("viewpage17")
            kri17.setIcon("")
            kri17.setNhom("Báo hỏng")
            response.add(kri17)

            Kri kri18 = new Kri()
            kri18.setMa("TTVT_KT_033")
            kri18.setTen("Theo dõi thống kê số lượng phiếu báo hỏng giao, phiếu báo hỏng thực hiện.")
            kri18.setUrl("viewpage18")
            kri18.setIcon("")
            kri18.setNhom("")
            response.add(kri18)

            Kri kri19 = new Kri()
            kri19.setMa("TTKD_KD_018")
            kri19.setTen("Quản lý theo dõi kết quả thu cước, thù lao - Thu quay vòng.")
            kri19.setUrl("viewpage19")
            kri19.setIcon("")
            kri19.setNhom("Thu cước")
            response.add(kri19)

            Kri kri20 = new Kri()
            kri20.setMa("TTVT_KT_024")
            kri20.setTen("Theo dõi thống kê số lượng phiếu báo hỏng.")
            kri20.setUrl("viewpage20")
            kri20.setIcon("")
            kri20.setNhom("")
            response.add(kri20)

            Kri kri21 = new Kri()
            kri21.setMa("TTVT_KT_035")
            kri21.setTen("Theo dõi thống kê kết quả bài kiểm tra.")
            kri21.setUrl("viewpage21")
            kri21.setIcon("")
            kri21.setNhom("")
            response.add(kri21)

            Kri kri22 = new Kri()
            kri22.setMa("TTVT_TTKT_004")
            kri22.setTen("Theo dõi thống kê số lượng phiếu bảo dưỡng.")
            kri22.setUrl("viewpage22")
            kri22.setIcon("")
            kri22.setNhom("")
            response.add(kri22)

            Kri kri23 = new Kri()
            kri23.setMa("TTVT_TTKT_003")
            kri23.setTen("Theo dõi thống kê số lượng phiếu báo hỏng.")
            kri23.setUrl("viewpage23")
            kri23.setIcon("")
            kri23.setNhom("")
            response.add(kri23)

            Kri kri24 = new Kri()
            kri24.setMa("TTVT_KT_041")
            kri24.setTen("Theo dõi thống kê số lượng phiếu thu thập thông tin thị trường của đối thủ.")
            kri24.setUrl("viewpage24")
            kri24.setIcon("")
            kri24.setNhom("Thu thập thông tin")
            response.add(kri24)

            Kri kri25 = new Kri()
            kri25.setMa("TTVT_KT_012")
            kri25.setTen("Theo dõi thống kê số lượng phiếu báo hỏng được giao và xử lý phiếu báo hỏng.")
            kri25.setUrl("viewpage25")
            kri25.setIcon("")
            kri25.setNhom("Báo hỏng")
            response.add(kri25)

            Kri kri26 = new Kri()
            kri26.setMa("TTVT_KT_007")
            kri26.setTen("Quản lý các thuê bao lắp đặt mới còn tồn trên hệ thống.")
            kri26.setUrl("viewpage26")
            kri26.setIcon("")
            kri26.setNhom("Lắp đặt")
            response.add(kri26)

            Kri kri27 = new Kri()
            kri27.setMa("TTKD_KD_015")
            kri27.setTen("Theo dõi kết quả thực hiện tác nghiệp.")
            kri27.setUrl("viewpage27")
            kri27.setIcon("")
            kri27.setNhom("")
            response.add(kri27)

            Kri kri28 = new Kri()
            kri28.setMa("TTVT_KT_010")
            kri28.setTen("Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt (xét quá hạn theo thời gian xử lý; hẹn lắp đặt).")
            kri28.setUrl("viewpage28")
            kri28.setIcon("")
            kri28.setNhom("")
            response.add(kri28)

            Kri kri29 = new Kri()
            kri29.setMa("TTVT_TTKT_006")
            kri29.setTen("Quản lý theo dõi thống kê số lượng phiếu chuyển tổ.")
            kri29.setUrl("viewpage29")
            kri29.setIcon("")
            kri29.setNhom("")
            response.add(kri29)

            Kri kri30 = new Kri()
            kri30.setMa("TTKD_KD_019")
            kri30.setTen("Quản lý theo dõi kết quả thu cước, thù lao - Gạch nợ.")
            kri30.setUrl("viewpage30")
            kri30.setIcon("")
            kri30.setNhom("Thu cước")
            response.add(kri30)

            Kri kri31 = new Kri()
            kri31.setMa("TTVT_TTKT_002")
            kri31.setTen("Quản lý theo dõi thống kê số lượng phiếu lắp đặt mới.")
            kri31.setUrl("viewpage31")
            kri31.setIcon("")
            kri31.setNhom("")
            response.add(kri31)

            Kri kri32 = new Kri()
            kri32.setMa("TTVT_TTKT_001")
            kri32.setTen("Quản lý theo dõi thống kê tình hình báo cáo/kiểm tra CCDC.")
            kri32.setUrl("viewpage32")
            kri32.setIcon("")
            kri32.setNhom("")
            response.add(kri32)

//            Kri kri33 = new Kri()
//            kri33.setMa("TTKD_AM_002")
//            kri33.setTen("Theo dõi thông tin thu cước.")
//            kri33.setUrl("viewpage33")
//            kri33.setIcon("")
//            kri33.setNhom("")
//            response.add(kri33)

            Kri kri34 = new Kri()
            kri34.setMa("TTVT_KT_039")
            kri34.setTen("Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng.")
            kri34.setUrl("viewpage34")
            kri34.setIcon("")
            kri34.setNhom("Thu thập thông tin")
            response.add(kri34)

            Kri kri35 = new Kri()
            kri35.setMa("TTVT_KT_019")
            kri35.setTen("Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt.")
            kri35.setUrl("viewpage35")
            kri35.setIcon("")
            kri35.setNhom("")
            response.add(kri35)

            Kri kri36 = new Kri()
            kri36.setMa("TTVT_KT_023")
            kri36.setTen("Theo dõi thống kê đánh giá của khách hàng.")
            kri36.setUrl("viewpage36")
            kri36.setIcon("")
            kri36.setNhom("Khảo sát hài lòng")
            response.add(kri36)

            Kri kri37 = new Kri()
            kri37.setMa("TTVT_KT_029")
            kri37.setTen("Theo dõi thống kê phiếu sự cố được giao, phiếu đã được xử lý.")
            kri37.setUrl("viewpage37")
            kri37.setIcon("")
            kri37.setNhom("")
            response.add(kri37)

            Kri kri38 = new Kri()
            kri38.setMa("TTVT_KT_030")
            kri38.setTen("Theo dõi thống kê công tác bảo dưỡng dịch vụ.")
            kri38.setUrl("viewpage38")
            kri38.setIcon("")
            kri38.setNhom("Phiếu bảo dưỡng")
            response.add(kri38)

            Kri kri39 = new Kri()
            kri39.setMa("TTVT_KT_026")
            kri39.setTen("Theo dõi thống kê thực hiện Thu hồi thiết bị khi thuê bao rời mạng.")
            kri39.setUrl("viewpage39")
            kri39.setIcon("")
            kri39.setNhom("")
            response.add(kri39)

            Kri kri40 = new Kri()
            kri40.setMa("TTVT_KT_005")
            kri40.setTen("Theo dõi thống kê thiết bị phải được dán ID chi tiết, quản lý Serial.")
            kri40.setUrl("viewpage40")
            kri40.setIcon("")
            kri40.setNhom("")
            response.add(kri40)

            Kri kri41 = new Kri()
            kri41.setMa("TTVT_KT_027")
            kri41.setTen("Theo dõi thống kê thực hiện tình trạng thu hồi thiết bị khi thuê bao rời mạng.")
            kri41.setUrl("viewpage41")
            kri41.setIcon("")
            kri41.setNhom("")
            response.add(kri41)

            Kri kri42 = new Kri()
            kri42.setMa("TTVT_KT_032")
            kri42.setTen("Theo dõi thống kê số lượng phiếu thi công mới, phiếu giao, phiếu thực hiện.")
            kri42.setUrl("viewpage42")
            kri42.setIcon("")
            kri42.setNhom("")
            response.add(kri42)

            Kri kri43 = new Kri()
            kri43.setMa("TTVT_KT_040")
            kri43.setTen("Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng của đối thủ.")
            kri43.setUrl("viewpage43")
            kri43.setIcon("")
            kri43.setNhom("Thu thập thông tin")
            response.add(kri43)

            Kri kri44 = new Kri()
            kri44.setMa("TTKD_KD_008")
            kri44.setTen("Cài đặt và đánh giá kết quả cài đặt app My Vnpt.")
            kri44.setUrl("viewpage44")
            kri44.setIcon("")
            kri44.setNhom("Tư vấn cài APP")
            response.add(kri44)

            Kri kri45 = new Kri()
            kri45.setMa("TTKD_KD_017")
            kri45.setTen("Theo dõi, đánh giá để xác định việc sử dụng, phân loại theo từng mục đích tương tác MyTV OTT")
            kri45.setUrl("viewpage45")
            kri45.setIcon("")
            kri45.setNhom("")
            response.add(kri45)

            Kri kri46 = new Kri()
            kri46.setMa("TTVT_KT_002")
            kri46.setTen("Theo gói thống kê sản lượng, doanh thu, hoa hồng tác nghiệp và bán gói di động")
            kri46.setUrl("viewpage46")
            kri46.setIcon("")
            kri46.setNhom("")
            response.add(kri46)

            Kri kri47 = new Kri()
            kri47.setMa("TTVT_KT_008")
            kri47.setTen("Theo dõi, đánh giá khảo sát khách hàng lắp đặt mới dịch vụ")
            kri47.setUrl("viewpage47")
            kri47.setIcon("")
            kri47.setNhom("")
            response.add(kri47)

            Kri kri48 = new Kri()
            kri48.setMa("TTVT_KT_034")
            kri48.setTen("Theo dõi thống kê tình hình báo cáo/kiểm tra CCDC")
            kri48.setUrl("viewpage48")
            kri48.setIcon("")
            kri48.setNhom("")
            response.add(kri48)

            Kri kri49 = new Kri()
            kri49.setMa("TTVT_KT_042")
            kri49.setTen("Theo dõi thống kê số lượng phiếu thu thập thông tin khách hàng")
            kri49.setUrl("viewpage49")
            kri49.setIcon("")
            kri49.setNhom("")
            response.add(kri49)

            Kri kri50 = new Kri()
            kri50.setMa("TTVT_KT_001")
            kri50.setTen("Theo dõi thống kê doanh thu, sản lượng, thù lao thực hiện lập hợp đồng điện tử")
            kri50.setUrl("viewpage50")
            kri50.setIcon("")
            kri50.setNhom("")
            response.add(kri50)

            Kri kri51 = new Kri()
            kri51.setMa("TTKD_KD_011_014")
            kri51.setTen("Chăm sóc khách hàng")
            kri51.setUrl("viewpage51")
            kri51.setIcon("")
            kri51.setNhom("")
            response.add(kri51)

            Kri kri52 = new Kri()
            kri52.setMa("TTVT_KT_044")
            kri52.setTen("Số lượng hủy thuê bao Fiber - MyTV")
            kri52.setUrl("viewpage52")
            kri52.setIcon("")
            kri52.setNhom("Thuê bao Ngưng/Hủy")
            response.add(kri52)

            Kri kri53 = new Kri()
            kri53.setMa("TTKD_KD_023")
            kri53.setTen("Số lượng hủy thuê bao Fiber - MyTV")
            kri53.setUrl("viewpage53")
            kri53.setIcon("")
            kri53.setNhom("Thuê bao Ngưng/Hủy")
            response.add(kri53)

            Kri kri54 = new Kri()
            kri54.setMa("TTVT_TTKT_008")
            kri54.setTen("Số lượng hủy thuê bao Fiber - MyTV")
            kri54.setUrl("viewpage54")
            kri54.setIcon("")
            kri54.setNhom("Thuê bao Ngưng/Hủy")
            response.add(kri54)

            Kri kri55 = new Kri()
            kri55.setMa("TTVT_KT_045")
            kri55.setTen("Số lượng ngưng thuê bao Fiber - MyTV")
            kri55.setUrl("viewpage55")
            kri55.setIcon("")
            kri55.setNhom("Thuê bao Ngưng/Hủy")
            response.add(kri55)

            Kri kri56 = new Kri()
            kri56.setMa("TTKD_KD_024")
            kri56.setTen("Số lượng ngưng thuê bao Fiber - MyTV")
            kri56.setUrl("viewpage56")
            kri56.setIcon("")
            kri56.setNhom("Thuê bao Ngưng/Hủy")
            response.add(kri56)

            Kri kri57 = new Kri()
            kri57.setMa("TTVT_TTKT_009")
            kri57.setTen("Số lượng ngưng thuê bao Fiber - MyTV")
            kri57.setUrl("viewpage57")
            kri57.setIcon("")
            kri57.setNhom("Thuê bao Ngưng/Hủy")
            response.add(kri57)

            loggingManaged.info("[CDSDiaBanController] - [dsKri] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsKri] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDashboardTH>>> dsDashboardTH() {
        String requestId = genericApi.generateRequestId()
        List<DLDashboardTH> response = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String loainv = ""
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {

                loainv = responsePay.getInfo().getMaCDS().trim()
                String sqlString = "{ call css.chuyendoiso_db.Lay_dl_dashboard_tonghop(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                    sql.call(sqlString, [phanvung_id, maNV, loainv, CURSOR_PARAMETER]) { ResultSet rs ->
                        while (rs.next()) {
                            response.add(new DLDashboardTH(maKR: Utils.getStringDataByKey(rs, "MA_KR"),
                                    maChiTieu: Utils.getStringDataByKey(rs, "MACHITIEU"),
                                    ngayCN: Utils.getStringDataByKey(rs, "NGAY_CN"),
                                    giaTri: Utils.getStringDataByKey(rs, "GIATRI"),
                                    tenCot: Utils.getStringDataByKey(rs, "TEN_COT"),
                                    ghiChu: Utils.getStringDataByKey(rs, "GHICHU"),
                                    nhomCha: Utils.getStringDataByKey(rs, "LOAICHITIEU_TEN"),
                                    isBieuDo: Utils.getStringDataByKey(rs, "NHOM_BIEUDO"),
                                    nhom: Utils.getStringDataByKey(rs, "NHOM")))
                        }
                    }
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, responsePay.getErrorDescription(), ErrorCodeDefine.FOR_BIDDEN))
            }
            loggingManaged.info("[CDSDiaBanController] - [dsDashboardTH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsDashboardTH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVu() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dsDichVu] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
//            DanhMuc danhMuc1 = new DanhMuc()
//            danhMuc1.setCODE(Constant.CDS_DIABAN.phattrien_brcd.code)
//            danhMuc1.setNAME(Constant.CDS_DIABAN.phattrien_brcd.name)
//            response.add(danhMuc1)

            DanhMuc danhMuc1CD = new DanhMuc()
            danhMuc1CD.setCODE(Constant.CDS_DIABAN.phattrien_brcd_codinh.code)
            danhMuc1CD.setNAME(Constant.CDS_DIABAN.phattrien_brcd_codinh.name)
            response.add(danhMuc1CD)

            DanhMuc danhMuc1Fiber = new DanhMuc()
            danhMuc1Fiber.setCODE(Constant.CDS_DIABAN.phattrien_brcd_fiber.code)
            danhMuc1Fiber.setNAME(Constant.CDS_DIABAN.phattrien_brcd_fiber.name)
            response.add(danhMuc1Fiber)

            DanhMuc danhMuc1MyTV = new DanhMuc()
            danhMuc1MyTV.setCODE(Constant.CDS_DIABAN.phattrien_brcd_mytv.code)
            danhMuc1MyTV.setNAME(Constant.CDS_DIABAN.phattrien_brcd_mytv.name)
            response.add(danhMuc1MyTV)

            DanhMuc danhMuc1CNTT = new DanhMuc()
            danhMuc1CNTT.setCODE(Constant.CDS_DIABAN.phattrien_brcd_cntt.code)
            danhMuc1CNTT.setNAME(Constant.CDS_DIABAN.phattrien_brcd_cntt.name)
            response.add(danhMuc1CNTT)

            DanhMuc danhMuc2 = new DanhMuc()
            danhMuc2.setCODE(Constant.CDS_DIABAN.phattrien_didong_tratruoc.code)
            danhMuc2.setNAME(Constant.CDS_DIABAN.phattrien_didong_tratruoc.name)
            response.add(danhMuc2)
            DanhMuc danhMuc3 = new DanhMuc()
            danhMuc3.setCODE(Constant.CDS_DIABAN.phattrien_didong_trasau.code)
            danhMuc3.setNAME(Constant.CDS_DIABAN.phattrien_didong_trasau.name)
            response.add(danhMuc3)
            DanhMuc danhMuc4 = new DanhMuc()
            danhMuc4.setCODE(Constant.CDS_DIABAN.phattrien_mnp.code)
            danhMuc4.setNAME(Constant.CDS_DIABAN.phattrien_mnp.name)
            response.add(danhMuc4)
            DanhMuc danhMuc5 = new DanhMuc()
            danhMuc5.setCODE(Constant.CDS_DIABAN.phattrien_bancuoc.code)
            danhMuc5.setNAME(Constant.CDS_DIABAN.phattrien_bancuoc.name)
            response.add(danhMuc5)
            DanhMuc danhMuc6 = new DanhMuc()
            danhMuc6.setCODE(Constant.CDS_DIABAN.phattrien_4g.code)
            danhMuc6.setNAME(Constant.CDS_DIABAN.phattrien_4g.name)
            response.add(danhMuc6)
            DanhMuc danhMuc7 = new DanhMuc()
            danhMuc7.setCODE(Constant.CDS_DIABAN.phattrien_dcrs.code)
            danhMuc7.setNAME(Constant.CDS_DIABAN.phattrien_dcrs.name)
            response.add(danhMuc7)

            loggingManaged.info("[CDSDiaBanController] - [dsDichVu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsDichVu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TongHopKR001_007>>> ttkd_kd_001_007_TongHop(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TongHopKR001_007> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_001_007_TongHop] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            TongHopKR001_007 danhMuc1 = new TongHopKR001_007()
            danhMuc1.setMa(Constant.CDS_DIABAN.phattrien_brcd.code)
            danhMuc1.setTen(Constant.CDS_DIABAN.phattrien_brcd.name)
            danhMuc1.setMau(Constant.CDS_DIABAN.phattrien_brcd.color)
            danhMuc1.setSoLuong("10")
            danhMuc1.setSoTien("12000000")
            response.add(danhMuc1)
            TongHopKR001_007 danhMuc2 = new TongHopKR001_007()
            danhMuc2.setMa(Constant.CDS_DIABAN.phattrien_didong_tratruoc.code)
            danhMuc2.setTen(Constant.CDS_DIABAN.phattrien_didong_tratruoc.name)
            danhMuc2.setMau(Constant.CDS_DIABAN.phattrien_didong_tratruoc.color)
            danhMuc2.setSoLuong("10")
            danhMuc2.setSoTien("12000000")
            response.add(danhMuc2)
            TongHopKR001_007 danhMuc3 = new TongHopKR001_007()
            danhMuc3.setMa(Constant.CDS_DIABAN.phattrien_didong_trasau.code)
            danhMuc3.setTen(Constant.CDS_DIABAN.phattrien_didong_trasau.name)
            danhMuc3.setMau(Constant.CDS_DIABAN.phattrien_didong_trasau.color)
            danhMuc3.setSoLuong("10")
            danhMuc3.setSoTien("12000000")
            response.add(danhMuc3)
            TongHopKR001_007 danhMuc4 = new TongHopKR001_007()
            danhMuc4.setMa(Constant.CDS_DIABAN.phattrien_mnp.code)
            danhMuc4.setTen(Constant.CDS_DIABAN.phattrien_mnp.name)
            danhMuc4.setMau(Constant.CDS_DIABAN.phattrien_mnp.color)
            danhMuc4.setSoLuong("10")
            danhMuc4.setSoTien("12000000")
            response.add(danhMuc4)
            TongHopKR001_007 danhMuc5 = new TongHopKR001_007()
            danhMuc5.setMa(Constant.CDS_DIABAN.phattrien_bancuoc.code)
            danhMuc5.setTen(Constant.CDS_DIABAN.phattrien_bancuoc.name)
            danhMuc5.setMau(Constant.CDS_DIABAN.phattrien_bancuoc.color)
            danhMuc5.setSoLuong("10")
            danhMuc5.setSoTien("12000000")
            response.add(danhMuc5)
            TongHopKR001_007 danhMuc6 = new TongHopKR001_007()
            danhMuc6.setMa(Constant.CDS_DIABAN.phattrien_4g.code)
            danhMuc6.setTen(Constant.CDS_DIABAN.phattrien_4g.name)
            danhMuc6.setMau(Constant.CDS_DIABAN.phattrien_4g.color)
            danhMuc6.setSoLuong("10")
            danhMuc6.setSoTien("12000000")
            response.add(danhMuc6)
            TongHopKR001_007 danhMuc7 = new TongHopKR001_007()
            danhMuc7.setMa(Constant.CDS_DIABAN.phattrien_dcrs.code)
            danhMuc7.setTen(Constant.CDS_DIABAN.phattrien_dcrs.name)
            danhMuc7.setMau(Constant.CDS_DIABAN.phattrien_dcrs.color)
            danhMuc7.setSoLuong("10")
            danhMuc7.setSoTien("12000000")
            response.add(danhMuc7)

            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_001_007_TongHop] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_001_007_TongHop] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ChiTietKR001_007>>> ttkd_kd_001_007_ChiTiet(String service, String account, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<ChiTietKR001_007> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_001_007_ChiTiet] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate)
                            .put("service", service).put("account", account != null ? account : "").build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String token = session_.get(TOKEN)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            switch (service) {
                case Constant.CDS_DIABAN.phattrien_brcd_codinh.code: case Constant.CDS_DIABAN.phattrien_brcd_fiber.code: case Constant.CDS_DIABAN.phattrien_brcd_mytv.code: case Constant.CDS_DIABAN.phattrien_brcd_cntt.code:
                    String maNV = session_.get(USERNAME)
                    String css = session_.get(CSS_SCH)
                    String sqlString = "{ call css.chuyendoiso_db.phattrien_brcd_v2(?,?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, service, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs.next()) {
                                String ngay = Utils.getStringDataByKey(rs, "NGAY")
                                if (ngay != null && ngay.trim().length() > 0) {
                                    ngay = Utils.convertToDay(ngay)
                                }
                                response.add(new ChiTietKR001_007(soTien: Utils.getStringNumberDataByKey(rs, "TONG_TIEN"), ngay: ngay,
                                        soLuong: Utils.getStringNumberDataByKey(rs, "SO_LAN")))
                            }
                        }
                    }
                    break
                case Constant.CDS_DIABAN.phattrien_didong_tratruoc.code:
                    ccbsService.init(token)
                    String respTT = ccbsService.get_number_of_reginfo(fdate, tdate)
                    try {
                        TTKD_KD_CCBSResponse responseTT = new Gson().fromJson(respTT, TTKD_KD_CCBSResponse.class)
                        if (responseTT != null && responseTT.getData() != null && responseTT.getData().size() > 0) {
                            for (TTKD_KD_CCBS item : responseTT.getData()) {
                                response.add(new ChiTietKR001_007(ngay: item.getNgay(), soTien: String.valueOf(item.getSoTien()), soLuong: String.valueOf(item.getSoLuong())))
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                    break
                case Constant.CDS_DIABAN.phattrien_didong_trasau.code:
                    ccbsService.init(token)
                    String respTS = ccbsService.get_number_of_regpackage(fdate, tdate)
                    try {
                        TTKD_KD_CCBSResponse responseTS = new Gson().fromJson(respTS, TTKD_KD_CCBSResponse.class)
                        if (responseTS != null && responseTS.getData() != null && responseTS.getData().size() > 0) {
                            for (TTKD_KD_CCBS item : responseTS.getData()) {
                                response.add(new ChiTietKR001_007(ngay: item.getNgay(), soTien: String.valueOf(item.getSoTien()), soLuong: String.valueOf(item.getSoLuong())))
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                    break
                case Constant.CDS_DIABAN.phattrien_mnp.code:
                    String user = session_.get(MA_NV_CCBS)
                    if (fdate != null && fdate.trim().length() > 0) {
                        fdate = fdate.replaceAll("/", "")
                    }
                    if (tdate != null && tdate.trim().length() > 0) {
                        tdate = tdate.replaceAll("/", "")
                    }
                    String resp = mnpClient.pi_oneapp_detail(user, fdate, tdate)
                    try {
                        TTKD_KD_001_007_MNP_Resp respObj = new Gson().fromJson(resp, TTKD_KD_001_007_MNP_Resp.class)
                        if (respObj != null && respObj.getErrorCode() == "0" && respObj.getResult() != null && respObj.getResult().size() > 0) {
                            for (TTKD_KD_001_007_MNP item : respObj.getResult()) {
                                response.add(new ChiTietKR001_007(ngay: item.getNgay(), soTien: item.getSoTien(), soLuong: item.getSoLuong()))
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                    break
                case Constant.CDS_DIABAN.phattrien_bancuoc.code:
                    if (fdate != null && fdate.trim().length() > 0 && tdate != null && tdate.trim().length() > 0) {
                        List<String> months = Utils.getMonthsBetween(fdate, tdate)
                        if (months != null && months.size() > 0) {
                            String joined = String.join(",", months)
                            String maNV = session_.get(MA_NV)
                            String dataSrc = "bris"
                            String sqlString = "select count(1) SL, SUM(DOANHTHU) TONGTIEN,to_char(trans_time,'dd/MM/yyyy') TRANS_TIME from qlkd3.V_bangoi_tinhluong\n" +
                                    "where mo_key in (?) and MA_NV_HRM = ? and TRANS_TIME >= to_date(?,'dd/MM/yyyy') and TRANS_TIME < to_date(?,'dd/MM/yyyy')+ 1\n" +
                                    "group by TRANS_TIME\n" +
                                    "order by to_date(TRANS_TIME,'dd/MM/yyyy') desc"
                            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                sql.eachRow(sqlString, [joined, maNV, fdate, tdate]) { rs ->
                                    response.add(new ChiTietKR001_007(ngay: Utils.getStringDataByKey(rs, "TRANS_TIME"),
                                            soTien: Utils.getStringNumberDataByKey(rs, "TONGTIEN"), soLuong: Utils.getStringNumberDataByKey(rs, "SL")))
                                }
                            }
                        }
                    }
                    break
                case Constant.CDS_DIABAN.phattrien_4g.code:
                    ccbsService.init(token)
                    String respCS = ccbsService.get_number_of_changesim(fdate, tdate)
                    try {
                        TTKD_KD_CCBSResponse responseCS = new Gson().fromJson(respCS, TTKD_KD_CCBSResponse.class)
                        if (responseCS != null && responseCS.getData() != null && responseCS.getData().size() > 0) {
                            for (TTKD_KD_CCBS item : responseCS.getData()) {
                                response.add(new ChiTietKR001_007(ngay: item.getNgay(), soTien: String.valueOf(item.getSoTien()), soLuong: String.valueOf(item.getSoLuong())))
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                    break
                case Constant.CDS_DIABAN.phattrien_dcrs.code:
                    String resp = smcsClient.dashDCRS("ONEBSS_DASHBOARD_DCRS", account, fdate, tdate)
                    try {
                        TTKD_KD_001_007_DCRS_Resp respObj = new Gson().fromJson(resp, TTKD_KD_001_007_DCRS_Resp.class)
                        if (respObj != null && respObj.getErrorCode() == "0" && respObj.getResult() != null && respObj.getResult().size() > 0) {
                            for (TTKD_KD_001_007_DCRS item : respObj.getResult()) {
                                response.add(new ChiTietKR001_007(ngay: item.getNgay(), soTien: item.getSoTien(), soLuong: item.getSoLuong()))
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                    break
            }

            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_001_007_ChiTiet] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_001_007_ChiTiet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_013>>> ttvt_kt_013(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_013> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_013] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.DS_HEN_BAOHONG_NVKT(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        String ngay = Utils.getStringDataByKey(rs, "HENSUADEN")
                        if (ngay != null && ngay.trim().length() > 0) {
                            ngay = Utils.convertToDayAndHour1(ngay)
                        }
                        response.add(new TTVT_KT_013(maTB: Utils.getStringDataByKey(rs, "MA_TB"), thoiGian: ngay,
                                trangThai: Utils.getStringDataByKey(rs, "TRANGTHAI_BH")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_013] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_013] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_004>>> ttvt_kt_004(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_004> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_004] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.danhgia_hd_dungthu_mytv(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_004(slDungThat: Utils.getStringNumberDataByKey(rs, "CHUYEN_THAT"),
                                slDungThu: Utils.getStringNumberDataByKey(rs, "DANG_DUNG_THU"),
                                slHuyDungThu: Utils.getStringNumberDataByKey(rs, "CHUYEN_HUY")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_004] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_004] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_043>>> ttvt_kt_043(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_043> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_043] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.PhieuKhaoSatB2A(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        String ngay = Utils.getStringDataByKey(rs, "NGAY_CS")
                        if (ngay != null && ngay.trim().length() > 0) {
                            ngay = Utils.convertToDayOther(ngay)
                        }
                        response.add(new TTVT_KT_043(soLuong: Utils.getStringNumberDataByKey(rs, "SL_B2A"), ngay: ngay))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_043] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_043] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_016>>> ttvt_kt_016(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_016> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_016] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_Hon2Lan30Ngay(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_016(soLuong: Utils.getStringNumberDataByKey(rs, "SO_LAN"), soMay: Utils.getStringDataByKey(rs, "SO_MAY")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_016] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_016] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_018>>> ttvt_kt_018(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_018> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_018] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_ThueBaoHong_TTVT_KT_018(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_018(ton: Utils.getStringNumberDataByKey(rs, "TON"), hoanThanh: Utils.getStringNumberDataByKey(rs, "HOAN_THANH")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_018] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_018] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_006>>> ttvt_kt_006(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_006> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_006] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.phieu_lapdatmoi_tiepnhan(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_006(ngay: Utils.getStringDataByKey(rs, "NGAY"), slMoi: Utils.getStringNumberDataByKey(rs, "MOI"),
                                daThucHien: Utils.getStringNumberDataByKey(rs, "DA_THUC_HIEN"), daTraLai: Utils.getStringNumberDataByKey(rs, "DA_TRA_LAI"),
                                daChuyenTo: Utils.getStringNumberDataByKey(rs, "DA_CHUYEN_TO"), daGiaoDi: Utils.getStringNumberDataByKey(rs, "DA_GIAO_DI"),
                                daTHHo: Utils.getStringNumberDataByKey(rs, "DA_TH_HO"), daThuHoi: Utils.getStringNumberDataByKey(rs, "DA_THU_HOI")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_006] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_006] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_006>>> ttvt_kt_006_v2(String fdate, String tdate, String service) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_006> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_006] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.phieu_lapdatmoi_tiepnhan_v2(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, service, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_006(ngay: Utils.getStringDataByKey(rs, "NGAY"), slMoi: Utils.getStringNumberDataByKey(rs, "MOI"),
                                daThucHien: Utils.getStringNumberDataByKey(rs, "DA_THUC_HIEN"), daTraLai: Utils.getStringNumberDataByKey(rs, "DA_TRA_LAI"),
                                daChuyenTo: Utils.getStringNumberDataByKey(rs, "DA_CHUYEN_TO"), daGiaoDi: Utils.getStringNumberDataByKey(rs, "DA_GIAO_DI"),
                                daTHHo: Utils.getStringNumberDataByKey(rs, "DA_TH_HO"), daThuHoi: Utils.getStringNumberDataByKey(rs, "DA_THU_HOI")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_006] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_006] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_031>>> ttvt_kt_031(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_031> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_031] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongMNV_TTVT_KT_031(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_031(duocGiao: Utils.getStringNumberDataByKey(rs, "DUOC_GIAO"),
                                hoanThanh: Utils.getStringNumberDataByKey(rs, "HOAN_THANH"), ngay: Utils.getStringDataByKey(rs, "NGAY")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_031] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_031] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_011>>> ttvt_kt_011(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_011> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_011] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.quanly_thoigian_lapdat(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_011(phieu12: Utils.getStringNumberDataByKey(rs, "PHIEU_12"),
                                phieu12_16: Utils.getStringNumberDataByKey(rs, "PHIEU_12_16"), phieu16: Utils.getStringNumberDataByKey(rs, "PHIEU_16")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_011] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_011] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_005>>> ttvt_ttkt_005(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_005> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_005] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.TT_SuCoMNV_TTVT_TTKT_005(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_005(moi: Utils.getStringNumberDataByKey(rs, "MOI"),
                                daGiao: Utils.getStringNumberDataByKey(rs, "DA_GIAO"), daTraLai: Utils.getStringNumberDataByKey(rs, "DA_TRA_LAI")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_005] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_005] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_020>>> ttvt_kt_020(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_020> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_020] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_ThoiGian_XuLyDichVu(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_020(phieu12: Utils.getStringNumberDataByKey(rs, "PHIEU_12"), tongSo: Utils.getStringNumberDataByKey(rs, "TSPHIEU"),
                                phieu12_16: Utils.getStringNumberDataByKey(rs, "PHIEU_12_16"), phieu16: Utils.getStringNumberDataByKey(rs, "PHIEU_16")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_020] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_020] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_033>>> ttvt_kt_033(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_033> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_033] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoTon_BaoHong(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_033(ngay: Utils.getStringDataByKey(rs, "NGAY"), ton: Utils.getStringNumberDataByKey(rs, "TON")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_033] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_033] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_024>>> ttvt_kt_024(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_024> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_024] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_TiepNhanTuKH(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_024(ngay: Utils.getStringDataByKey(rs, "NGAY"), soLuong: Utils.getStringNumberDataByKey(rs, "SO_PHIEU")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_024] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_033] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_035>>> ttvt_kt_035(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_035> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_035] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.TracNghiem_TTVT_KT_035(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_035(tongCH: Utils.getStringNumberDataByKey(rs, "TONG_CAUHOI"), slTraLoiDung: Utils.getStringNumberDataByKey(rs, "TRALOI_DUNG")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_035] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_035] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_004>>> ttvt_ttkt_004(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_004> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_004] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.TT_BaoDuong_TTVT_TTKT_004(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_004(moi: Utils.getStringNumberDataByKey(rs, "MOI"), ton: Utils.getStringNumberDataByKey(rs, "TON"),
                                daTra: Utils.getStringNumberDataByKey(rs, "DA_TRA"), daGiao: Utils.getStringNumberDataByKey(rs, "DA_GIAO")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_004] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_ttkt_004] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_003>>> ttvt_ttkt_003(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_003> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_003] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thongke_phieu_baohong (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_003(moi: Utils.getStringNumberDataByKey(rs, "sophieu_moi"), ton: Utils.getStringNumberDataByKey(rs, "sophieu_baoton"),
                                daTra: Utils.getStringNumberDataByKey(rs, "sophieu_tralai"), daGiao: Utils.getStringNumberDataByKey(rs, "sophieu_dagiao")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_003] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_ttkt_003] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_041>>> ttvt_kt_041(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_041> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_041] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.ThuThapThiTruongDoiThu_TTVT_KT_041 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_041(ngay: Utils.getStringDataByKey(rs, "ngay"), khac: Utils.getStringNumberDataByKey(rs, "khac"), vietnammobile: Utils.getStringNumberDataByKey(rs, "vietnam_mobile"),
                                mobifone: Utils.getStringNumberDataByKey(rs, "mobifone"), viettel: Utils.getStringNumberDataByKey(rs, "viettel"), fpt: Utils.getStringNumberDataByKey(rs, "fpt")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_041] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_041] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_007>> ttvt_kt_007(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_007 response = new TTVT_KT_007()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_007] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String ton = "0"
            String giao = "0"
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ ? = call css.chuyendoiso_db.Lay_sophieu_lapdat_ton(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, phanvung_id, username]) { Integer rs ->
                    if (rs != null && rs >= 0) {
                        ton = String.valueOf(rs)
                    }
                }
            }
            sqlString = "{ ? = call css.chuyendoiso_db.Lay_sophieu_lapdat_giao(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, phanvung_id, fdate, tdate, username]) { Integer rs ->
                    if (rs != null && rs >= 0) {
                        giao = String.valueOf(rs)
                    }
                }
            }
            response.setGiao(giao)
            response.setTon(ton)
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_007] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_007] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_007>> ttvt_kt_007_v2(String fdate, String tdate, String service) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_007 response = new TTVT_KT_007()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_007] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String ton = "0"
            String giao = "0"
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ ? = call css.chuyendoiso_db.Lay_sophieu_lapdat_ton_v2(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, phanvung_id, username, service]) { Integer rs ->
                    if (rs != null && rs >= 0) {
                        ton = String.valueOf(rs)
                    }
                }
            }
            sqlString = "{ ? = call css.chuyendoiso_db.Lay_sophieu_lapdat_giao_v2(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, phanvung_id, fdate, tdate, username, service]) { Integer rs ->
                    if (rs != null && rs >= 0) {
                        giao = String.valueOf(rs)
                    }
                }
            }
            response.setGiao(giao)
            response.setTon(ton)
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_007] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_007] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_012>>> ttvt_kt_012(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_012> response = []
        List<TTVT_KT_012> responseTmp = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_012] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Lay_sophieu_bh_hoanthanh(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_012(ngay: Utils.convertToDay(Utils.getStringDataByKey(rs, "ngay_ht")),
                                hoanThanh: Utils.getStringNumberDataByKey(rs, "soluong"), giao: "0"))
                    }
                }
            }
            sqlString = "{ call css.chuyendoiso_db.Lay_sophieu_bh_giao(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        String ngay = Utils.convertToDay(Utils.getStringDataByKey(rs, "ngaygiao"))
                        boolean existed = false
                        for (int i = 0; i < response.size(); i++) {
                            if (response.get(i).getNgay() == ngay) {
                                response.get(i).setGiao(Utils.getStringNumberDataByKey(rs, "soluong"))
                                existed = true
                                break
                            }
                        }
                        if (!existed) {
                            responseTmp.add(new TTVT_KT_012(ngay: ngay, hoanThanh: "0", giao: Utils.getStringNumberDataByKey(rs, "soluong")))
                        }
                    }
                }
            }
            if (responseTmp.size() > 0) {
                response.addAll(responseTmp)
            }
            response = response.stream().sorted({ o1, o2 ->
                int depDiff = o1.ngay <=> o2.ngay
                if (depDiff == 0) {
                    return o1.ngay <=> o2.ngay
                }
                return depDiff
            }).collect(Collectors.toList())
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_012] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_012] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_008>> ttkd_kd_008() {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_008 response = new TTKD_KD_008()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_008] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
            String css = session_.get(CSS_SCH)
            String list = ""
            Integer myvnpt_da_cai_dat = 0
            Integer myvnpt_chua_cai_dat = 0
            String sqlString = "{ call CSS.SP_LAY_DS_CAI_MYVNPT_THEONV(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        list += Utils.getStringDataByKey(rs, "soDienThoaiKH") != null && Utils.getStringDataByKey(rs, "soDienThoaiKH").trim().length() > 0
                                ? Utils.getStringDataByKey(rs, "soDienThoaiKH") : "" + ","
                        String trangThai = Utils.getStringDataByKey(rs, "trang_thai")
                        if (trangThai != null && trangThai == '1') {
                            myvnpt_da_cai_dat++
                        } else {
                            myvnpt_chua_cai_dat++
                        }
                    }
                }
            }
            response.setChua_cai(String.valueOf(myvnpt_chua_cai_dat))
            response.setDa_cai(String.valueOf(myvnpt_da_cai_dat))
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_008] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_008] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_009>> ttkd_kd_009(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_009 response = new TTKD_KD_009()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_009] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
            String css = session_.get(CSS_SCH)
            String list = ""
            if (fdate != null && fdate.trim().length() > 0) {
                fdate = Utils.convertToDayAndHour2(fdate)
            }
            if (tdate != null && tdate.trim().length() > 0) {
                tdate = Utils.convertToDayAndHour2(tdate)
            }
            String sqlString = "{ call CSS.SP_LAY_DS_CAI_MYVNPT_THEONV_V2(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        list += (Utils.getStringDataByKey(rs, "soDienThoaiKH") != null && Utils.getStringDataByKey(rs, "soDienThoaiKH").trim().length() > 0
                                ? Utils.getStringDataByKey(rs, "soDienThoaiKH") : "") + ","
                    }
                }
            }
            if (list.trim().length() > 0) {
                String resp = vnptPayClient.myvnpt_vnptpay_map(list)
                if (resp != null && resp.trim().length() > 0) {
                    try {
                        TTKD_KD_008_009_Response responsePay = new Gson().fromJson(resp, TTKD_KD_008_009_Response.class)
                        if (responsePay != null && responsePay.getDatas() != null && responsePay.getDatas().size() > 0) {
                            Integer mobile_money_da_dk = 0
                            Integer mobile_money_chua_dk = 0
                            Integer vnpt_pay_da_dinh_danh = 0
                            Integer vnpt_pay_chua_dinh_danh = 0
                            Integer vnpt_pay_da_lien_ket = 0
                            Integer vnpt_pay_chua_lien_ket = 0
                            Integer vnpt_pay_da_thanh_toan = 0
                            Integer vnpt_pay_chua_thanh_toan = 0
                            for (TTKD_KD_008_009_GW item : responsePay.getDatas()) {
                                if (item.getIsMobileMoney() != null && item.getIsMobileMoney() == 1) {
                                    mobile_money_da_dk++
                                } else {
                                    mobile_money_chua_dk++
                                }
                                if (item.getDinhDanh() != null && item.getDinhDanh() == 1) {
                                    vnpt_pay_da_dinh_danh++
                                } else {
                                    vnpt_pay_chua_dinh_danh++
                                }
                                if (item.getLienKetNH() != null && item.getLienKetNH() == 1) {
                                    vnpt_pay_da_lien_ket++
                                } else {
                                    vnpt_pay_chua_lien_ket++
                                }
                                if (item.getGiaoDich() != null && item.getGiaoDich() == 1) {
                                    vnpt_pay_da_thanh_toan++
                                } else {
                                    vnpt_pay_chua_thanh_toan++
                                }
                            }
                            TTKD_KD_009_MobileMoney mobileMoney = new TTKD_KD_009_MobileMoney()
                            mobileMoney.setChua_dk(String.valueOf(mobile_money_chua_dk))
                            mobileMoney.setDa_dk(String.valueOf(mobile_money_da_dk))
                            response.setMobile_money(mobileMoney)

                            TTKD_KD_009_VnptPay vnptPay = new TTKD_KD_009_VnptPay()
                            vnptPay.setDa_dinh_danh(String.valueOf(vnpt_pay_da_dinh_danh))
                            vnptPay.setChua_dinh_danh(String.valueOf(vnpt_pay_chua_dinh_danh))
                            vnptPay.setDa_lien_ket(String.valueOf(vnpt_pay_da_lien_ket))
                            vnptPay.setDa_thanh_toan(String.valueOf(vnpt_pay_da_thanh_toan))
                            vnptPay.setChua_lien_ket(String.valueOf(vnpt_pay_chua_lien_ket))
                            vnptPay.setChua_thanh_toan(String.valueOf(vnpt_pay_chua_thanh_toan))
                            response.setVnpt_pay(vnptPay)
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_009] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_009] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_010>> ttkd_kd_010(String chuKy) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_010 response = new TTKD_KD_010()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_010] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("chuKy", chuKy).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.solieu_xuat_hoa_don (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, chuKy, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        response.setGiayBaoCuoc(Utils.getStringNumberDataByKey(rs, "GIAYBAOCUOC"))
                        response.setBienNhanTraTruoc(Utils.getStringNumberDataByKey(rs, "BIENNHAN_TRATRUOC"))
                        response.setBienNhanThanhToan(Utils.getStringNumberDataByKey(rs, "BIENNHANTHANHTOAN"))
                        response.setPhuLucCuocVienThong(Utils.getStringNumberDataByKey(rs, "PL_CUOC_VIENTHONG"))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_010] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_010] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_002>>> ttvt_ttkt_002(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_002> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_002] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thongke_phieu_lapdat (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_002(moi: Utils.getStringNumberDataByKey(rs, "sophieu_moi"), ton: Utils.getStringNumberDataByKey(rs, "sophieu_baoton"),
                                daTra: Utils.getStringNumberDataByKey(rs, "sophieu_tralai"), daGiao: Utils.getStringNumberDataByKey(rs, "sophieu_dagiao")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_002] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_ttkt_002] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_006>>> ttvt_ttkt_006(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_006> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_006] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Lay_sophieu_chuyento (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_006(ngay: Utils.getStringDataByKey(rs, "ngaychuyen"), soLuong: Utils.getStringNumberDataByKey(rs, "soluong")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_006] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_ttkt_006] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_010>>> ttvt_kt_010(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_010> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_010] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thongke_quahan_lapdat (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_010(denHan: Utils.getStringNumberDataByKey(rs, "sl_denhan"), quaHan: Utils.getStringNumberDataByKey(rs, "sl_quahan"),
                                chuaToiHan: Utils.getStringNumberDataByKey(rs, "sl_chuatoihan")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_010] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_010] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_018>> ttkd_kd_018(String kieu, String ngay) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_018 response = new TTKD_KD_018()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_018] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kieu", kieu).put("ngay", ngay).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            List<TTKD_KD_018_BD_CoCau> bdCoCau = []
            List<TTKD_KD_018_BD_Cot> bdCot = []
            List<TTKD_KD_018_DS> danhSach = []
            String sqlString = "{ call css.chuyendoiso_db.Thuquayvong_bieudo_cocau (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kieu, ngay, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCoCau.add(new TTKD_KD_018_BD_CoCau(slMyVnpt: Utils.getStringNumberDataByKey(rs, "sl_myvnpt"), slKhac: Utils.getStringNumberDataByKey(rs, "sl_khac"),
                                slChuaThu: Utils.getStringNumberDataByKey(rs, "sl_chuathu"), tienMyVnpt: Utils.getStringNumberDataByKey(rs, "tien_myvnpt"),
                                tienChuaThu: Utils.getStringNumberDataByKey(rs, "tien_chuathu"), tienKhac: Utils.getStringNumberDataByKey(rs, "tien_khac")))
                    }
                }
            }
            response.setBdCoCau(bdCoCau)
            sqlString = "{ call css.chuyendoiso_db.Thuquayvong_bieudo_cot (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kieu, ngay, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCot.add(new TTKD_KD_018_BD_Cot(ngayTT: Utils.getStringDataByKey(rs, "ngay_tt"), slHDDaThu: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu"),
                                slHDDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu_luyke"), tienDaThu: Utils.getStringNumberDataByKey(rs, "tien_dathu"),
                                tienDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "tien_dathu_luyke")))
                    }
                }
            }
            response.setBdCot(bdCot)
            sqlString = "{ call css.chuyendoiso_db.Thuquayvong_danhsach (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kieu, ngay, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        danhSach.add(new TTKD_KD_018_DS(maTB: Utils.getStringDataByKey(rs, "ma_tb"), tinhTrang: Utils.getStringDataByKey(rs, "tinhtrang"),
                                ngayTT: Utils.getStringDataByKey(rs, "ngay_tt")))
                    }
                }
            }
            response.setDanhSach(danhSach)
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_018] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_018] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_019>> ttkd_kd_019(String kyHD) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_019 response = new TTKD_KD_019()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_019] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kyHD", kyHD).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            List<TTKD_KD_019_BD_CoCau> bdCoCau = []
            List<TTKD_KD_019_BD_Cot> bdCot = []
            List<TTKD_KD_019_DS> danhSach = []
            String sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_bieudo_cocau (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kyHD, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCoCau.add(new TTKD_KD_019_BD_CoCau(slMyVnpt: Utils.getStringNumberDataByKey(rs, "soluong_myvnpt"), slKhac: Utils.getStringNumberDataByKey(rs, "soluong_khac"),
                                slChuaThu: Utils.getStringNumberDataByKey(rs, "soluong_chua_thu"), tienMyVnpt: Utils.getStringNumberDataByKey(rs, "sotien_myvnpt"),
                                tienChuaThu: Utils.getStringNumberDataByKey(rs, "sotien_chua_thu"), tienKhac: Utils.getStringNumberDataByKey(rs, "sotien_khac")))
                    }
                }
            }
            response.setBdCoCau(bdCoCau)
            sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_bieudo_cot (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kyHD, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCot.add(new TTKD_KD_019_BD_Cot(ngayTT: Utils.getStringDataByKey(rs, "ngay_tt"), slHDDaThu: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu"),
                                slHDDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu_luyke"), tienDaThu: Utils.getStringNumberDataByKey(rs, "tien_dathu"),
                                tienDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "tien_dathu_luyke")))
                    }
                }
            }
            response.setBdCot(bdCot)
            sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_danhsach (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kyHD, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        danhSach.add(new TTKD_KD_019_DS(maTT: Utils.getStringDataByKey(rs, "ma_tt"), tinhTrang: Utils.getStringDataByKey(rs, "tinhtrang"),
                                ngayTT: Utils.getStringDataByKey(rs, "ngay_tt")))
                    }
                }
            }
            response.setDanhSach(danhSach)
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_019] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_019] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_001>>> ttvt_ttkt_001(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_001> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_001] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.CongCu_TTVT_KT_034 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_001(congCuBatBuocCo: Utils.getStringNumberDataByKey(rs, "CONG_CU_BAT_BUOC_CO"), congCuBatBuocKhong: Utils.getStringNumberDataByKey(rs, "CONG_CU_BAT_BUOC_KHONG"),
                                congCuKhongBatBuocCo: Utils.getStringNumberDataByKey(rs, "CONG_CU_KHONG_BAT_BUOC_CO"), congCuKhongBatBuocKhong: Utils.getStringNumberDataByKey(rs, "CONG_CU_KHONG_BAT_BUOC_KHONG")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_ttkt_001] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_ttkt_001] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_AM_002>>> ttkd_am_002() {
        String requestId = genericApi.generateRequestId()
        List<TTKD_AM_002> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_am_002] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thucuoc_tonghop (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTKD_AM_002(maNV: Utils.getStringDataByKey(rs, "ma_nv"), tenNV: Utils.getStringDataByKey(rs, "ten_nv"),
                                daThu: Utils.getStringNumberDataByKey(rs, "soluong_chua_thu"), chuaThu: Utils.getStringNumberDataByKey(rs, "soluong_da_thu")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_am_002] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_am_002] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_039>>> ttvt_kt_039(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_039> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_039] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.ThuThapHaTangVNPT_TTVT_KT_039 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_039(ngay: Utils.getStringDataByKey(rs, "ngay"), hopLe: Utils.getStringNumberDataByKey(rs, "HOP_LE"),
                                khongHopLe: Utils.getStringNumberDataByKey(rs, "KHONG_HOP_LE")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_039] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_039] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_019>>> ttvt_kt_019(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_019> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_019] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_TTVT_KT_019 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, username, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_019(tong: Utils.getStringNumberDataByKey(rs, "TSPHIEU"), toiHan: Utils.getStringNumberDataByKey(rs, "PHIEU_TOIHAN"),
                                chuaToiHan: Utils.getStringNumberDataByKey(rs, "PHIEU_CHUATOIHAN"), quaHan: Utils.getStringNumberDataByKey(rs, "PHIEU_QUAHAN")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_019] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_019] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_023>>> ttvt_kt_023(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_023> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_023] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.KhaoSat_TTVT_KT_023 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_023(ngay: Utils.getStringDataByKey(rs, "NGAY"), phieuTH: Utils.getStringNumberDataByKey(rs, "PHIEU_TH"),
                                phieuKS: Utils.getStringNumberDataByKey(rs, "PHIEU_KS"), phieuKSHL: Utils.getStringNumberDataByKey(rs, "PHIEU_KS_HL"),
                                phieuKSKOHL: Utils.getStringNumberDataByKey(rs, "PHIEU_KS_KOHL"), phieuKSKhac: Utils.getStringNumberDataByKey(rs, "PHIEU_KS_KHAC")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_023] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_023] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_029>>> ttvt_kt_029(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_029> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_029] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.SuCo_TTVT_KT_029 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_029(ngay: Utils.getStringDataByKey(rs, "NGAY"),
                                phieuGiao: Utils.getStringNumberDataByKey(rs, "PHIEU_GIAO"), phieuXL: Utils.getStringNumberDataByKey(rs, "PHIEU_XL")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_029] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_029] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_030>>> ttvt_kt_030(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_030> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_030] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongDV_TTVT_KT_030 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, username, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_030(phieuGiao: Utils.getStringNumberDataByKey(rs, "PHIEU_GIAO"),
                                phieuXL: Utils.getStringNumberDataByKey(rs, "PHIEU_XL"), ngay: Utils.getStringDataByKey(rs, "ngay_bh")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_030] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_030] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_KD_015>>> ttkd_kd_015(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTKD_KD_015> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_015] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thongke_khachhang_tiemnang (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTKD_KD_015(daKyHD: Utils.getStringNumberDataByKey(rs, "sl_daky_hd"), hen: Utils.getStringNumberDataByKey(rs, "sl_kh_hen"),
                                khongKyHD: Utils.getStringNumberDataByKey(rs, "sl_khongky_hd"), khac: Utils.getStringNumberDataByKey(rs, "sl_khac")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_015] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_015] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_005>> ttvt_kt_005(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_005 response = new TTVT_KT_005()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_005] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thongke_thietbi_lapdat (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        response = new TTVT_KT_005(chuaQuet: Utils.getStringNumberDataByKey(rs, "sl_thietbi_chuaquet"), daQuet: Utils.getStringNumberDataByKey(rs, "sl_thietbi_daquet"))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_005] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_005] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_026>>> ttvt_kt_026(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_026> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_026] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.ThuHoiTB_TTVT_KT_026 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, username, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_026(ngay: Utils.getStringDataByKey(rs, "ngay"), phaiTH: Utils.getStringNumberDataByKey(rs, "tbi_phai_thuhoi"),
                                daTH: Utils.getStringNumberDataByKey(rs, "tbi_da_thuhoi")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_026] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_026] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_027>> ttvt_kt_027(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_027 response = new TTVT_KT_027()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_027] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.ThuHoiTB_TTVT_KT_027 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response = new TTVT_KT_027(tbHong: Utils.getStringDataByKey(rs, "tbi_hong"), tbSdDuoc: Utils.getStringNumberDataByKey(rs, "tbi_sd_duoc"))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_027] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_027] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_032>> ttvt_kt_032(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_032 response = new TTVT_KT_032()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_032] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoTon_TCMoi_TTVT_KT_032 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        response = new TTVT_KT_032(khongThiCong: Utils.getStringNumberDataByKey(rs, "TON_KHONGTHICONG"), thiCong: Utils.getStringNumberDataByKey(rs, "TON_THICONG"))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_032] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_032] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_040>>> ttvt_kt_040(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_040> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_040] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.ThuThapHaTangDoiTu_TTVT_KT_040 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_040(ngay: Utils.getStringNumberDataByKey(rs, "NGAY"),
                                moi: Utils.getStringNumberDataByKey(rs, "THUTHAP_MOI"), lai: Utils.getStringNumberDataByKey(rs, "THUTHAP_LAI")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttvt_kt_040] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_040] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_017>> ttkd_kd_017(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_017 response = new TTKD_KD_017()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_017] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.danhgia_su_dung_mytv_ott (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        TTKD_KD_017_TuVan tuVan = new TTKD_KD_017_TuVan(daSuDungDaTuVan: Utils.getStringNumberDataByKey(rs, "DASUDUNG_DATUVAN"),
                                daSuDungChuaTuVan: Utils.getStringNumberDataByKey(rs, "DASUDUNG_CHUATUVAN"),
                                chuaSuDungDaTuVan: Utils.getStringNumberDataByKey(rs, "CHUASUDUNG_DATUVAN"))
                        response.setTuVan(tuVan)
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_017] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_017] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_017_MyTVOTT>> ttkd_kd_017_checkMyTVOTT(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_017_MyTVOTT response = new TTKD_KD_017_MyTVOTT()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_017_checkMyTVOTT] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String list = ""
            String sqlString = "select USERNAME from dulieu_" + maTinh.toLowerCase() + ".cai_app_mytv_ott_ct where TRANG_THAI = 1 and ngay_th >= to_date(? ||' 00:00:00','dd/mm/yyyy hh24:mi:ss') " +
                    "and ngay_th <= to_date(? ||' 23:59:59','dd/mm/yyyy hh24:mi:ss') and nhanvien_id in (select nhanvien_id from admin_" + maTinh.toLowerCase() + ".nhanvien where ma_nv = ?)"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.eachRow(sqlString, [fdate, tdate, maNV]) { rs ->
                    list += Utils.getStringDataByKey(rs, "USERNAME") + ","
                }
            }
            if (list.trim().length() > 0) {
                list = list.substring(0, list.length() - 1)
                String resp = vnptPayClient.mytv_ott_ps_data(list)
                if (resp != null && resp.trim().length() > 0) {
                    try {
                        TTKD_KD_017_Response responsePay = new Gson().fromJson(resp, TTKD_KD_017_Response.class)
                        if (responsePay != null && responsePay.getDatas() != null && responsePay.getDatas().size() > 0) {
                            Integer psll = 0
                            Integer kpsll = 0
                            for (TTKD_KD_017_GW item : responsePay.getDatas()) {
                                if (item.getPsLL() != null && item.getPsLL() == 1) {
                                    psll++
                                }
                                if (item.getPsLL() != null && item.getPsLL() == 0) {
                                    kpsll++
                                }
                            }
                            response.setPhatSinhLL(String.valueOf(psll))
                            response.setKhongPhatSinhLL(String.valueOf(kpsll))
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace()
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_017_checkMyTVOTT] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_017_checkMyTVOTT] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVuCSKH() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dsDichVuCSKH] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            DanhMuc danhMuc1 = new DanhMuc()
            danhMuc1.setCODE(Constant.CDS_DIABAN.TTKD_KD_011.code)
            danhMuc1.setNAME(Constant.CDS_DIABAN.TTKD_KD_011.name)
            response.add(danhMuc1)
            DanhMuc danhMuc2 = new DanhMuc()
            danhMuc2.setCODE(Constant.CDS_DIABAN.TTKD_KD_012.code)
            danhMuc2.setNAME(Constant.CDS_DIABAN.TTKD_KD_012.name)
            response.add(danhMuc2)
            DanhMuc danhMuc3 = new DanhMuc()
            danhMuc3.setCODE(Constant.CDS_DIABAN.TTKD_KD_013.code)
            danhMuc3.setNAME(Constant.CDS_DIABAN.TTKD_KD_013.name)
            response.add(danhMuc3)
            DanhMuc danhMuc4 = new DanhMuc()
            danhMuc4.setCODE(Constant.CDS_DIABAN.TTKD_KD_014.code)
            danhMuc4.setNAME(Constant.CDS_DIABAN.TTKD_KD_014.name)
            response.add(danhMuc4)

            loggingManaged.info("[CDSDiaBanController] - [dsDichVuCSKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsDichVuCSKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_011_014>> ttkd_kd_011_014(String service, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_011_014 response = new TTKD_KD_011_014()
        try {
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_011_014] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("service", service).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = ""
            switch (service) {
                case Constant.CDS_DIABAN.TTKD_KD_011.code:
                    TTKD_KD_011 list011 = new TTKD_KD_011()
                    sqlString = "{ call css.chuyendoiso_db.THONGKE_CSKH_TTKD_KD_011_013 (?,?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, 1, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs.next()) {
                                list011 = new TTKD_KD_011(chuaKS: Utils.getStringNumberDataByKey(rs, "CHUA_KS"),
                                        haiLong: Utils.getStringNumberDataByKey(rs, "HAILONG"), khongHL: Utils.getStringNumberDataByKey(rs, "KHONG_HAILONG"))
                            }
                        }
                    }
                    response.setTtkd_kd_011(list011)
                    break
                case Constant.CDS_DIABAN.TTKD_KD_012.code:
                    List<TTKD_KD_012> list012 = new ArrayList<>()
                    sqlString = "{ call css.chuyendoiso_db.Thongke_thuebao_roimang (?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [phanvung_id, fdate, tdate, maNV, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs.next()) {
                                list012.add(new TTKD_KD_012(ten: Utils.getStringDataByKey(rs, "ten_dvvt"),
                                        soLuong: Utils.getStringNumberDataByKey(rs, "soluong")))
                            }
                        }
                    }
                    response.setTtkd_kd_012(list012)
                    break
                case Constant.CDS_DIABAN.TTKD_KD_013.code:
                    TTKD_KD_013 list013 = new TTKD_KD_013()
                    sqlString = "{ call css.chuyendoiso_db.THONGKE_CSKH_TTKD_KD_011_013 (?,?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, 2, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs.next()) {
                                list013 = new TTKD_KD_013(chuaKS: Utils.getStringNumberDataByKey(rs, "CHUA_KS"),
                                        haiLong: Utils.getStringNumberDataByKey(rs, "HAILONG"), khongHL: Utils.getStringNumberDataByKey(rs, "KHONG_HAILONG"))
                            }
                        }
                    }
                    response.setTtkd_kd_013(list013)
                    break
                case Constant.CDS_DIABAN.TTKD_KD_014.code:
                    TTKD_KD_014 list014 = new TTKD_KD_014()
                    sqlString = "{ call css.chuyendoiso_db.cskh_khong_luuluong (?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs.next()) {
                                list014 = new TTKD_KD_014(chuaKS: Utils.getStringNumberDataByKey(rs, "CHUAKHAOSAT"), daKS: Utils.getStringNumberDataByKey(rs, "DAKHAOSAT"))
                            }
                        }
                    }
                    response.setTtkd_kd_014(list014)
                    break
            }
            loggingManaged.info("[CDSDiaBanController] - [ttkd_kd_011_014] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_011_014] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> logKri(String kri) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanController] - [logKri] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            dataUtils.init(phanvung_id.toString(), css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanController] - [logKri] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", MessageUtils.SUCCESS).build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [logKri] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatHuy>>> dlDiaBanTongQuat(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanTongQuatHuy> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanTongQuat] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_tongquat (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanTongQuatHuy(ngay: Utils.convertToDay(Utils.getStringDataByKey(rs, "ngay")),
                                slFiberHuy: Utils.getStringNumberDataByKey(rs, "sl_fiber_huy"),
                                slFiberPTM: Utils.getStringNumberDataByKey(rs, "sl_fiber_ptm"),
                                slMyTVHuy: Utils.getStringNumberDataByKey(rs, "sl_mytv_huy"),
                                slMyTVPTM: Utils.getStringNumberDataByKey(rs, "sl_mytv_ptm"),
                                slFiberPSC: Utils.getStringNumberDataByKey(rs, "sl_fiber_psc"),
                                slMyTVPSC: Utils.getStringNumberDataByKey(rs, "sl_mytv_psc")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanTongQuat] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanTongQuat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanHuy>>> dlDiaBanNguyenNhanHuy(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanNguyenNhanHuy> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanNguyenNhanHuy] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_nguyennhanhuy (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanNguyenNhanHuy(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                nguyenNhan: Utils.getStringDataByKey(rs, "ten_nguyen_nhan"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanNguyenNhanHuy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanNguyenNhanHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoHuy>>> dlDiaBanLyDoHuy(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanLyDoHuy> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoHuy] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_lydohuy (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanLyDoHuy(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                lyDo: Utils.getStringDataByKey(rs, "ten_yeu_to"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoHuy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanLyDoHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatNgung>>> dlDiaBanTongQuatNgung(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanTongQuatNgung> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanTongQuatNgung] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_tongquat_tn (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanTongQuatNgung(ngay: Utils.convertToDay(Utils.getStringDataByKey(rs, "ngay")),
                                slFiberNgung: Utils.getStringNumberDataByKey(rs, "sl_fiber_ngung"),
                                slFiberPTM: Utils.getStringNumberDataByKey(rs, "sl_fiber_ptm"),
                                slMyTVNgung: Utils.getStringNumberDataByKey(rs, "sl_mytv_ngung"),
                                slMyTVPTM: Utils.getStringNumberDataByKey(rs, "sl_mytv_ptm"),
                                slFiberPSC: Utils.getStringNumberDataByKey(rs, "sl_fiber_psc"),
                                slMyTVPSC: Utils.getStringNumberDataByKey(rs, "sl_mytv_psc"),
                                slKhacPTM: Utils.getStringNumberDataByKey(rs, "sl_khac_ptm"),
                                slKhacNgung: Utils.getStringNumberDataByKey(rs, "sl_khac_ngung"),
                                slKhacPSC: Utils.getStringNumberDataByKey(rs, "sl_khac_psc"),
                        ))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanTongQuatNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanTongQuatNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanNgung>>> dlDiaBanNguyenNhanNgung(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanNguyenNhanNgung> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanNguyenNhanNgung] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_nguyennhan_TN (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanNguyenNhanNgung(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                nguyenNhan: Utils.getStringDataByKey(rs, "ten_nguyen_nhan"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanNguyenNhanNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanNguyenNhanNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoNgung>>> dlDiaBanLyDoNgung(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanLyDoNgung> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_lydo_TN (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanLyDoNgung(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                lyDo: Utils.getStringDataByKey(rs, "ten_yeu_to"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_011>>> dlTTVT_TTKT_011(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_011> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlTTVT_TTKT_011] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thongke_thietbi_quet_serial (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_011(ma_nv: Utils.getStringDataByKey(rs, "ma_nv"),
                                ten_nv: Utils.getStringDataByKey(rs, "ten_nv"),
                                tong_thietbi: Utils.getStringNumberDataByKey(rs, "tong_thietbi"),
                                sl_thietbi_daquet: Utils.getStringNumberDataByKey(rs, "sl_thietbi_daquet"),
                                sl_thietbi_chuaquet: Utils.getStringNumberDataByKey(rs, "sl_thietbi_chuaquet")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlTTVT_TTKT_011] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlTTVT_TTKT_011] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_010>>> dlTTVT_TTKT_010(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_010> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlTTVT_TTKT_010] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Theodoi_KQ_Kiemtra_Daugio (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_010(ma_nv: Utils.getStringDataByKey(rs, "ma_nv"),
                                ten_nv: Utils.getStringDataByKey(rs, "ten_nv"),
                                dat: Utils.getStringNumberDataByKey(rs, "traloi_dat"),
                                khong_dat: Utils.getStringNumberDataByKey(rs, "traloi_khongdat")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<khieunai_brcd>>> dl_khieunai_brcd(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<khieunai_brcd> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dl_khieunai_brcd] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_khieunai (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, fdate, tdate, maNV, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new khieunai_brcd(ten_loai: Utils.getStringDataByKey(rs, "ten_loai"),
                                soluong_kn: Utils.getStringNumberDataByKey(rs, "soluong_kn")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dl_khieunai_brcd] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dl_khieunai_brcd] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<BRCD_KHONG_PSC>>> dlBRCDKhongPSC(String thang) {
        String requestId = genericApi.generateRequestId()
        List<BRCD_KHONG_PSC> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlBRCDKhongPSC] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("thang", thang).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String loaiNV = ""
            String resp = vnptPayClient.check_hrm_code(maNV)
            CheckHRMCodeResponse responsePay = new Gson().fromJson(resp, CheckHRMCodeResponse.class)
            if (responsePay != null && responsePay.getErrorCode() != null && responsePay.getErrorCode() == '0' && responsePay.getInfo() != null
                    && responsePay.getInfo().getMaCDS() != null && responsePay.getInfo().getMaCDS().trim().length() > 0) {
                loaiNV = responsePay.getInfo().getMaCDS().trim()
            }
            String sqlString = "{ call css.chuyendoiso_db.DL_brcd_dukien_ko_psc (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, thang, maNV, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new BRCD_KHONG_PSC(ma_nv: Utils.getStringDataByKey(rs, "ma_nv"),
                                ten_nv: Utils.getStringDataByKey(rs, "ten_nv"),
                                tong_khong_psc: Utils.getStringNumberDataByKey(rs, "tong_khong_psc"),
                                sl_fiber_khong_psc: Utils.getStringNumberDataByKey(rs, "sl_fiber_khong_psc"),
                                sl_mytv_khong_psc: Utils.getStringNumberDataByKey(rs, "sl_mytv_khong_psc")))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlBRCDKhongPSC] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlBRCDKhongPSC] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> listProvince() {
        String requestId = genericApi.generateRequestId()
        try {
            List<DanhMuc> lsDm = []
            String requestTime = genericApi.getFormattedCurrnetDate()
            String hashRequest = genericApi.getSHA256(requestId + "" + requestTime + "vnpt-it-oneapp")
            Map<String, String> input = new HashMap<>([
                    "authenData": new HashMap<>([
                            "requestId"  : requestId,
                            "requestTime": requestTime,
                            "hashRequest": hashRequest
                    ])
            ])
            String respone = HttpManaged.post(["url": "http://10.155.149.43:8905/vpsService/coverage/listProvince"], ["Content-Type": "application/json"], new Gson().toJson(input))
            def json = new JsonSlurper().parseText(respone)
            if (json["statusCode"] == 0) {
                def responseObject = json["responseObject"]
                responseObject.each { Map<String, Object> it ->
                    DanhMuc dm = new DanhMuc()
                    dm.setNAME(it["name"].toString())
                    dm.setCODE(it["code"].toString())
                    dm.setID(it["tinhtp_id"].toString().toLong())
                    lsDm.add(dm)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(lsDm))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception ex) {
            loggingManaged.error("[CDSDiaBanController] - [listProvince] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<DanhMuc>> positionCoverage(String latitude, String longitude, String tinhtp_id) {
        String requestId = genericApi.generateRequestId()
        try {
            DanhMuc dm = new DanhMuc()
            String requestTime = genericApi.getFormattedCurrnetDate()
            String hashRequest = genericApi.getSHA256(requestId + "" + requestTime + "vnpt-it-oneapp")
            Map<String, String> input = new HashMap<>([
                    "authenData": new HashMap<>([
                            "requestId"  : requestId,
                            "requestTime": requestTime,
                            "hashRequest": hashRequest
                    ]),
                    "latitude"  : latitude,
                    "longitude" : longitude,
                    "tinhtp_id" : tinhtp_id
            ])
            String respone = HttpManaged.post(["url": "http://10.155.149.43:8905/vpsService/coverage/positionCoverage"], ["Content-Type": "application/json"], new Gson().toJson(input))
            def json = new JsonSlurper().parseText(respone)
            if (json["statusCode"] == 0) {
                List<Map<String, Object>> responseObject = json["responseObject"]
                if (responseObject.size() > 0) {
                    Map<String, Object> it = responseObject.find { def ic -> ic["sourceType"] == "Mentor" }

                    if (it != null) {
                        List<Map<String, Object>> coverageData = it["coverageData"]
                        if (coverageData.size() > 0) {
                            Map<String, Object> dt = coverageData.last()
                            dm.setNAME(dt["tech"].toString())
                            dm.setCODE(dt["value"].toString())
                        }
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(dm))
                    } else {
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null))
                    }

                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception ex) {
            loggingManaged.error("[CDSDiaBanController] - [positionCoverage] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<BRCD_KHONG_PSLL>> dlBrcdKhongPsll(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        BRCD_KHONG_PSLL response = null
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlBrcdKhongPsll] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Lay_sltb_khong_PSLL (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        response = new BRCD_KHONG_PSLL(sl_3ngay_kpsll: Utils.getStringNumberDataByKey(rs, "sl_3ngay_kpsll"),
                                sl_6ngay_kpsll: Utils.getStringNumberDataByKey(rs, "sl_6ngay_kpsll"),
                                sl_9ngay_kpsll: Utils.getStringNumberDataByKey(rs, "sl_9ngay_kpsll"),
                                sl_12ngay_kpsll: Utils.getStringNumberDataByKey(rs, "sl_12ngay_kpsll"))
                    }
                }
            }
            loggingManaged.info("[CDSDiaBanController] - [dlBrcdKhongPsll] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlBrcdKhongPsll] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<ThongTinMangNetRespones>> coverageByDistance(String latitude, String longitude, String distance) {
        String requestId = genericApi.generateRequestId()
        try {
            ThongTinMangNetRespones datas = new ThongTinMangNetRespones()
            String requestTime = genericApi.getFormattedCurrnetDate()
            String hashRequest = genericApi.getSHA256(requestId + "" + requestTime + "vnpt-it-oneapp")
            Map<String, String> input = new HashMap<>([
                    "authenData": new HashMap<>([
                            "requestId"  : requestId,
                            "requestTime": requestTime,
                            "hashRequest": hashRequest
                    ]),
                    "latitude"  : latitude,
                    "longitude" : longitude,
                    "distance"  : distance
            ])
            String respone = HttpManaged.post(["url": "http://10.155.149.43:8905/vpsService/coverage/coverageByDistance"], ["Content-Type": "application/json"], new Gson().toJson(input))
            def json = new JsonSlurper().parseText(respone)
            if (json["statusCode"] == 0) {
                List<Map<String, Object>> responseObject = json["responseObject"]
                List<ThongTinMangNet> lsVnpt = []
                List<ThongTinMangNet> lsOrther = []
                if (responseObject.size() > 0) {
                    responseObject.each { Map<String, Object> item ->
                        if (item != null) {
                            List<Map<String, Object>> coverageData = item["coverageData"]
                            if (coverageData.size() > 0) {
                                coverageData.each { Map<String, Object> it ->
                                    ThongTinMangNet info = new ThongTinMangNet()
                                    info.setOperator(it["operator"].toString())
                                    info.setTech(it["tech"].toString())
                                    info.setValue(it["value"].toString())
                                    info.setLat(it["lat"].toString())
                                    info.setLng(it["lng"].toString())
                                    if (item["sourceType"] == "MENTOR" || item["sourceType"] == "ASSET") {
                                        lsVnpt.add(info)
                                    } else {
                                        lsOrther.add(info)
                                    }

                                }
                            }
                            datas.vnpt = lsVnpt
                            datas.orther = lsOrther
                        }
                    }

                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(datas))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, json["message"].toString(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception ex) {
            loggingManaged.error("[CDSDiaBanController] - [coverageByDistance] - Error:", ex)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhanVien() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> data = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(USERNAME)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.LAY_DANHSACH_NHANVIEN (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    long ind = 0
                    while (rs.next()) {
                        ind += 1
                        DanhMuc dm = new DanhMuc()
                        dm.setID(ind)
                        dm.setCODE(Utils.getStringDataByKey(rs, "ma_nv") + "#" + Utils.getStringDataByKey(rs, "ma_nd"))
                        dm.setNAME(Utils.getStringDataByKey(rs, "ten_nv"))
                        data.add(dm)
                    }
                }
            }
            if (data.size() > 1) {
                DanhMuc dm = new DanhMuc()
                dm.setID(0)
                dm.setCODE("1")
                dm.setNAME("Tất cả")
                data.add(dm)
                data.sort { it -> it.ID }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsNhanVien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinChiTietTbNgungHuy>>> dsChiTietNgungHuy(String vtungay, String vdenngay, String vloai, String vmanv_timkiem, String vis_all, String vma_tb, String vma_tt) {
        String requestId = genericApi.generateRequestId()
        List<ThongTinChiTietTbNgungHuy> responses = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dsChiTietNgungHuy] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Lay_ct_ngung_huy (?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, vtungay, vdenngay, vloai, maNV, vmanv_timkiem, vis_all, vma_tb, vma_tt, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses.add(new ThongTinChiTietTbNgungHuy(maTb: Utils.getStringNumberDataByKey(rs, "ma_tb"),
                                tenTb: Utils.getStringNumberDataByKey(rs, "ten_tb"),
                                soDt: Utils.getStringNumberDataByKey(rs, "so_dt"),
                                diaChiLd: Utils.getStringNumberDataByKey(rs, "diachi_ld"),
                                loai: Utils.getStringNumberDataByKey(rs, "loai"),
                                ngay: Utils.getStringNumberDataByKey(rs, "ngay")))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsChiTietNgungHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinChiTietThuCuocGachNo>>> dsThuCuocGachNoChiTiet(String kyhoadon, String ma_tb, String ma_tt, String mand_timkiem, String is_all) {
        String requestId = genericApi.generateRequestId()
        List<ThongTinChiTietThuCuocGachNo> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(USERNAME)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_danhsach_CT (?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kyhoadon, ma_tb, ma_tt, maNV, mand_timkiem, is_all, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses.add(new ThongTinChiTietThuCuocGachNo(maTt: Utils.getStringNumberDataByKey(rs, "ma_tt"),
                                soDt: Utils.getStringNumberDataByKey(rs, "so_dt"),
                                tongNo: Utils.getStringNumberDataByKey(rs, "tong_no"),
                                tenTt: Utils.getStringNumberDataByKey(rs, "ten_tt"),
                                diaChiTt: Utils.getStringNumberDataByKey(rs, "diachi_tt")))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsThuCuocGachNoChiTiet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<BRCD_KHONG_PSC_CT>>> dsBRCDkhongPSCchiTiet(String kyhoadon, String ma_tb, String ma_tt, String loai, String manv_timkiem, String is_all) {
        String requestId = genericApi.generateRequestId()
        List<BRCD_KHONG_PSC_CT> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.DL_brcd_dukien_ko_psc_ct (?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, kyhoadon, loai, ma_tb, ma_tt, maNV, manv_timkiem, is_all, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses.add(new BRCD_KHONG_PSC_CT(maTB: Utils.getStringNumberDataByKey(rs, "ma_tb"),
                                soDT: Utils.getStringNumberDataByKey(rs, "so_dt"),
                                loaiTB: Utils.getStringNumberDataByKey(rs, "loai_tb"),
                                tenTB: Utils.getStringNumberDataByKey(rs, "ten_tb"),
                                diaChiLD: Utils.getStringNumberDataByKey(rs, "diachi_ld")))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsBRCDkhongPSCchiTiet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<BRCD_KHONG_PSLL_CT>>> dsBRCDkhongPSLLchiTiet(String tungay, String denngay, String ma_tb, String ma_tt, String kieu, String manv_timkiem, String is_all) {
        String requestId = genericApi.generateRequestId()
        List<BRCD_KHONG_PSLL_CT> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(MA_NV)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.Lay_chitiet_khong_PSLL_TTVT_KT_064 (?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, tungay, denngay, kieu, ma_tb, ma_tt, manv_timkiem, is_all, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses.add(new BRCD_KHONG_PSLL_CT(maTB: Utils.getStringNumberDataByKey(rs, "ma_tb"),
                                dienThoaiLH: Utils.getStringNumberDataByKey(rs, "dienthoai_lh"),
                                kieu: Utils.getStringNumberDataByKey(rs, "kieu"),
                                ngaySL: Utils.getStringNumberDataByKey(rs, "ngay_solieu"),
                                diaChiLD: Utils.getStringNumberDataByKey(rs, "diachi_ld"),
                                tenTB: Utils.getStringNumberDataByKey(rs, "ten_tb")))
                    }
                }
            }
            // HoangND khoa chuc nang do bang khong co du lieu, nguoi dung thac mac nhieu 
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Chức năng chưa triển khai", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            //return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dsBRCDkhongPSLLchiTiet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_052>> ttvt_kt_052() {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_052 responses = null
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(USERNAME)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongDV_TTVT_KT_052 (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses = new TTVT_KT_052(tong: Utils.getStringNumberDataByKey(rs, "ton"),
                                denHan: Utils.getStringNumberDataByKey(rs, "ton_denhan"),
                                chuaDenHan: Utils.getStringNumberDataByKey(rs, "ton_chua_denhan"))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_052] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_052>> ttvt_kt_056() {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_052 responses = null
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String maNV = session_.get(USERNAME)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongMNV_TTVT_KT_056 (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses = new TTVT_KT_052(tong: Utils.getStringNumberDataByKey(rs, "ton"),
                                denHan: Utils.getStringNumberDataByKey(rs, "ton_denhan"),
                                chuaDenHan: Utils.getStringNumberDataByKey(rs, "ton_chua_denhan"))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_056] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_029>> ttkd_kd_029(String thang) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_029 responses = null
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String maNV = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.phattrien_brcd_thang (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, thang, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses = new TTKD_KD_029(ptmThang: Utils.getStringNumberDataByKey(rs, "ptm_thang"),
                                ptmThangTruoc: Utils.getStringNumberDataByKey(rs, "ptm_thang_truoc"))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_029] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_KD_030_032_034_035>>> ttkd_kd_030_032_034_035(String dichvu, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTKD_KD_030_032_034_035> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String maNV = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongDV_TTVT_030_32_34_35 (?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, dichvu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        TTKD_KD_030_032_034_035 item = new TTKD_KD_030_032_034_035(
                                ngay: Utils.getStringDataByKey(rs, "ngay_dk"),
                                soLuong: Utils.getStringNumberDataByKey(rs, "sltb"),
                                cuocDc: Utils.getStringDataByKey(rs, "cuoc_dc"))
                        responses.add(item)
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [TTKD_KD_030_032_034_035] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_054>> ttvt_kt_054(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_054 responses = null
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String maNV = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.ThuHoiTB_ton_TTVT_KT_054 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses = new TTVT_KT_054(
                                slQuaHan: Utils.getStringNumberDataByKey(rs, "sl_quahan"),
                                slDungHan: Utils.getStringNumberDataByKey(rs, "sl_dunghan"))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_054] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_053>>> ttvt_kt_053(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_053> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String maNV = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.XACMINH_TTVT_KT_053 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        responses.add(new TTVT_KT_053(ngay: Utils.getStringDataByKey(rs, "ngay_th"),
                                slPhieuDuocGiao: Utils.getStringNumberDataByKey(rs, "sl_phieu_giao"),
                                slPhieuHoanThanh: Utils.getStringNumberDataByKey(rs, "sl_phieu_ht")))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttvt_kt_053] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_KD_030_032_034_035>>> ttkd_kd_031_033(String dichvu, String thang) {
        String requestId = genericApi.generateRequestId()
        List<TTKD_KD_030_032_034_035> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            long phanvung_id = session_.get(ID_TINHTHANH).toString().toLong()
            String maNV = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.GIAHAN_DUNGHAN_TTVT_033_31 (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [phanvung_id, maNV, thang, dichvu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        TTKD_KD_030_032_034_035 item = new TTKD_KD_030_032_034_035(
                                ngay: Utils.getStringDataByKey(rs, "ngay_dk"),
                                soLuong: Utils.getStringNumberDataByKey(rs, "sltb"),
                                cuocDc: Utils.getStringDataByKey(rs, "cuoc_dc"))
                        responses.add(item)
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [TTKD_KD_030_032_034_035] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_KD_021>>> ttkd_kd_021(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTKD_KD_021> responses = []
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)

            Map<String, String> param = new HashMap<>([
                    "hrm_code": maNV,
                    "fdate"   : fdate,
                    "tdate"   : tdate
            ])
            def respone = HttpManaged.post(["url": "http://api-bris.vnpt.vn/kri/g/tonghop_dulieu_bangoi"], ["Content-Type": "application/json"], new Gson().toJson(param))

            Map<String, Object> json = new JsonSlurper().parseText(respone)
            if (json.error_code == "0") {

                for (int i = 0; i < json.data.size(); i++) {

                    TTKD_KD_021 item = new TTKD_KD_021()
                    item.ngay = json.data[i].logdate
                    item.slGoi = json.data[i].sl_goi
                    item.tongTien = json.data[i].tong_tien
                    responses.add(item)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(responses))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, json["message"].toString(), ErrorCodeDefine.NO_CONTENT))
            }


        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [ttkd_kd_021] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
