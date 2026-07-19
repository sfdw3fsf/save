package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.CDSDiaBanV2Api
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
import io.swagger.model.response.cdsdiaban.*
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.ep.MNPClient
import vn.vnptit.sapi.oneapp.ep.SmcsClient
import vn.vnptit.sapi.oneapp.ep.VnptPayClient
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.service.CCBSService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet
import java.util.stream.Collectors

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class CDSDiaBanV2Controller implements CDSDiaBanV2Api {
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
    CDSDiaBanV2Controller(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<Kri>>> dsKri() {
        String data = "{\n    \"error\": \"BSS-00000000\",\n    \"error_code\": \"BSS-00000000\",\n    \"message\": \"Success\",\n    \"message_detail\": null,\n    \"request_id\": null,\n    \"data\": [\n        {\n            \"ma\": \"TTKD_KD_001_007\",\n            \"ten\": \"Bán hàng và theo dõi kết quả bán hàng trên mobile app.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage1\"\n        },\n        {\n            \"ma\": \"TTKD_KD_009\",\n            \"ten\": \"Cài đặt và đánh giá kết quả cài đặt app để thanh toán trực tuyến qua VNPT Pay.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage2\"\n        },\n        {\n            \"ma\": \"TTVT_KT_013\",\n            \"ten\": \"Lịch sử hẹn xử lý.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage3\"\n        },\n        {\n            \"ma\": \"TTKD_KD_010\",\n            \"ten\": \"Xuất hóa đơn.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage4\"\n        },\n        {\n            \"ma\": \"TTKD_KD_011_014\",\n            \"ten\": \"Chăm sóc khách hàng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage5\"\n        },\n        {\n            \"ma\": \"TTKD_KD_015\",\n            \"ten\": \"Khách hàng tiềm năng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage6\"\n        },\n        {\n            \"ma\": \"TTVT_KT_004\",\n            \"ten\": \"Hợp đồng dùng thử.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage7\"\n        },\n        {\n            \"ma\": \"TTVT_KT_043\",\n            \"ten\": \"Số lượng khảo sát phiếu B2A.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage8\"\n        },\n        {\n            \"ma\": \"TTVT_KT_001_002\",\n            \"ten\": \"NVKT thực hiện tư vấn cho khách hàng các dịch vụ của VNPT.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage9\"\n        },\n        {\n            \"ma\": \"TTVT_KT_016\",\n            \"ten\": \"Quản lý thông tin báo hỏng của thuê bao từ 2 lần trở lên trong vòng 30 ngày.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage10\"\n        },\n        {\n            \"ma\": \"TTVT_KT_018\",\n            \"ten\": \"Quản lý các thuê bao hỏng còn tồn trên hệ thống.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage11\"\n        },\n        {\n            \"ma\": \"TTVT_KT_006\",\n            \"ten\": \"Quản lý theo dõi thống kê chi tiết máy lắp đặt mới.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage12\"\n        },\n        {\n            \"ma\": \"TTVT_KT_001\",\n            \"ten\": \"Bán hàng và theo dõi kết quả bán hàng trên mobile app.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage13\"\n        },\n        {\n            \"ma\": \"TTVT_KT_031\",\n            \"ten\": \"Theo dõi kết quả thực hiện bảo dưỡng mạng ngoại vi.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage14\"\n        },\n        {\n            \"ma\": \"TTVT_KT_011\",\n            \"ten\": \"Theo dõi thống kê thời gian xử lý lắp đặt mới dịch vụ theo nhân viên DVVT.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage15\"\n        },\n        {\n            \"ma\": \"TTVT_TTKT_005\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu xử lý sự cố mạng ngoại vi.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage16\"\n        },\n        {\n            \"ma\": \"TTVT_KT_020\",\n            \"ten\": \"Theo dõi thống kê thời gian xử lý dịch vụ theo nhân viên DVVT.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage17\"\n        },\n        {\n            \"ma\": \"TTVT_KT_033\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu báo hỏng giao, phiếu báo hỏng thực hiện.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage18\"\n        },\n        {\n            \"ma\": \"TTKD_KD_018\",\n            \"ten\": \"Quản lý theo dõi kết quả thu cước, thù lao - Thu quay vòng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage19\"\n        },\n        {\n            \"ma\": \"TTVT_KT_024\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu báo hỏng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage20\"\n        },\n        {\n            \"ma\": \"TTVT_KT_035\",\n            \"ten\": \"Theo dõi thống kê kết quả bài kiểm tra.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage21\"\n        },\n        {\n            \"ma\": \"TTVT_TTKT_004\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu bảo dưỡng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage22\"\n        },\n        {\n            \"ma\": \"TTVT_TTKT_003\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu báo hỏng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage23\"\n        },\n        {\n            \"ma\": \"TTVT_KT_041\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu thu thập thông tin thị trường của đối thủ.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage24\"\n        },\n        {\n            \"ma\": \"TTVT_KT_012\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu báo hỏng được giao và xử lý phiếu báo hỏng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage25\"\n        },\n        {\n            \"ma\": \"TTVT_KT_007\",\n            \"ten\": \"Quản lý các thuê bao lắp đặt mới còn tồn trên hệ thống.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage26\"\n        },\n        {\n            \"ma\": \"TTKD_KD_015\",\n            \"ten\": \"Theo dõi kết quả thực hiện tác nghiệp.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage27\"\n        },\n        {\n            \"ma\": \"TTVT_KT_010\",\n            \"ten\": \"Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt (xét quá hạn theo thời gian xử lý; hẹn lắp đặt).\",\n            \"icon\": \"\",\n            \"url\": \"viewpage28\"\n        },\n        {\n            \"ma\": \"TTVT_TTKT_006\",\n            \"ten\": \"Quản lý theo dõi thống kê số lượng phiếu chuyển tổ.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage29\"\n        },\n        {\n            \"ma\": \"TTKD_KD_019\",\n            \"ten\": \"Quản lý theo dõi kết quả thu cước, thù lao - Gạch nợ.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage30\"\n        },\n        {\n            \"ma\": \"TTVT_TTKT_002\",\n            \"ten\": \"Quản lý theo dõi thống kê số lượng phiếu lắp đặt mới.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage31\"\n        },\n        {\n            \"ma\": \"TTVT_TTKT_001\",\n            \"ten\": \"Quản lý theo dõi thống kê tình hình báo cáo/kiểm tra CCDC.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage32\"\n        },\n        {\n            \"ma\": \"TTKD_AM_002\",\n            \"ten\": \"Theo dõi thông tin thu cước.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage33\"\n        },\n        {\n            \"ma\": \"TTVT_KT_039\",\n            \"ten\": \"Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage34\"\n        },\n        {\n            \"ma\": \"TTVT_KT_019\",\n            \"ten\": \"Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage35\"\n        },\n        {\n            \"ma\": \"TTVT_KT_023\",\n            \"ten\": \"Theo dõi thống kê đánh giá của khách hàng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage36\"\n        },\n        {\n            \"ma\": \"TTVT_KT_029\",\n            \"ten\": \"Theo dõi thống kê phiếu sự cố được giao, phiếu đã được xử lý.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage37\"\n        },\n        {\n            \"ma\": \"TTVT_KT_030\",\n            \"ten\": \"Theo dõi thống kê công tác bảo dưỡng dịch vụ.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage38\"\n        },\n        {\n            \"ma\": \"TTVT_KT_026\",\n            \"ten\": \"Theo dõi thống kê thực hiện Thu hồi thiết bị khi thuê bao rời mạng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage39\"\n        },\n        {\n            \"ma\": \"TTVT_KT_005\",\n            \"ten\": \"Theo dõi thống kê thiết bị phải được dán ID chi tiết, quản lý Serial.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage40\"\n        },\n        {\n            \"ma\": \"TTVT_KT_027\",\n            \"ten\": \"Theo dõi thống kê thực hiện tình trạng thu hồi thiết bị khi thuê bao rời mạng.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage41\"\n        },\n        {\n            \"ma\": \"TTVT_KT_032\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu thi công mới, phiếu giao, phiếu thực hiện.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage42\"\n        },\n        {\n            \"ma\": \"TTVT_KT_040\",\n            \"ten\": \"Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng của đối thủ.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage43\"\n        },\n        {\n            \"ma\": \"TTKD_KD_008\",\n            \"ten\": \"Cài đặt và đánh giá kết quả cài đặt app My Vnpt.\",\n            \"icon\": \"\",\n            \"url\": \"viewpage44\"\n        },\n        {\n            \"ma\": \"TTKD_KD_017\",\n            \"ten\": \"Theo dõi, đánh giá để xác định việc sử dụng, phân loại theo từng mục đích tương tác MyTV OTT\",\n            \"icon\": \"\",\n            \"url\": \"viewpage45\"\n        },\n        {\n            \"ma\": \"TTVT_KT_002\",\n            \"ten\": \"Theo gói thống kê sản lượng, doanh thu, hoa hồng tác nghiệp và bán gói di động\",\n            \"icon\": \"\",\n            \"url\": \"viewpage46\"\n        },\n        {\n            \"ma\": \"TTVT_KT_008\",\n            \"ten\": \"Theo dõi, đánh giá khảo sát khách hàng lắp đặt mới dịch vụ\",\n            \"icon\": \"\",\n            \"url\": \"viewpage47\"\n        },\n        {\n            \"ma\": \"TTVT_KT_034\",\n            \"ten\": \"Theo dõi thống kê tình hình báo cáo/kiểm tra CCDC\",\n            \"icon\": \"\",\n            \"url\": \"viewpage48\"\n        },\n        {\n            \"ma\": \"TTVT_KT_042\",\n            \"ten\": \"Theo dõi thống kê số lượng phiếu thu thập thông tin khách hàng\",\n            \"icon\": \"\",\n            \"url\": \"viewpage49\"\n        },\n        {\n            \"ma\": \"TTVT_KT_001\",\n            \"ten\": \"Theo dõi thống kê doanh thu, sản lượng, thù lao thực hiện lập hợp đồng điện tử\",\n            \"icon\": \"\",\n            \"url\": \"viewpage50\"\n        },\n        {\n            \"ma\": \"TTKD_KD_011_014\",\n            \"ten\": \"Chăm sóc khách hàng\",\n            \"icon\": \"\",\n            \"url\": \"viewpage51\"\n        }\n    ]\n}";
        String requestId = genericApi.generateRequestId()
        List<Kri> response = []
        try {
            Map<String,Object> jsData = new JsonSlurper().parseText(data);
            loggingManaged.info("[CDSDiaBanV2Controller] - [dsKri] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String maNV = session_.get(MA_NV)
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

                    int check = sql.firstRow("select count(1) SL from css.kri").get("SL").toString().toInteger();
                    if(check > 0) {
                        sql.eachRow(sqlString, [responsePay.getInfo().getMaCDS().trim(), username]) { rs ->
                            response.add(new Kri(ma: Utils.getStringDataByKey(rs, "MA"), ten: Utils.getStringDataByKey(rs, "TEN"),
                                    url: Utils.getStringDataByKey(rs, "URL"), icon: Utils.getStringDataByKey(rs, "ICON"), nhom: Utils.getStringDataByKey(rs, "NHOM")))
                        }
                    } else {
                        List<Map<String,Object>> lsData = jsData["data"]
                        lsData.each {Object it ->
                            sql.execute("insert into css.kri (ma,ten) values (?,?)",[it["ma"],it["ten"]])
                            String [] hrm = it["ma"].toString().split("_")
                            sql.execute("insert into css.kri_config (ma_kri,nhom_hrm) values (?,?)",[it["ma"],hrm[0] + "_"+ hrm[1]])
                        }
                        sql.eachRow(sqlString, [responsePay.getInfo().getMaCDS().trim(), username]) { rs ->
                            response.add(new Kri(ma: Utils.getStringDataByKey(rs, "MA"), ten: Utils.getStringDataByKey(rs, "TEN"),
                                    url: Utils.getStringDataByKey(rs, "URL"), icon: Utils.getStringDataByKey(rs, "ICON"), nhom: Utils.getStringDataByKey(rs, "NHOM")))
                        }
                    }
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, responsePay.getErrorDescription(), ErrorCodeDefine.FOR_BIDDEN))
            }
            loggingManaged.info("[CDSDiaBanV2Controller] - [dsKri] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [dsKri] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVu() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [dsDichVu] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            DanhMuc danhMuc1 = new DanhMuc()
            danhMuc1.setCODE(Constant.CDS_DIABAN.phattrien_brcd.code)
            danhMuc1.setNAME(Constant.CDS_DIABAN.phattrien_brcd.name)
            response.add(danhMuc1)
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
            DanhMuc danhMuc6= new DanhMuc()
            danhMuc6.setCODE(Constant.CDS_DIABAN.phattrien_4g.code)
            danhMuc6.setNAME(Constant.CDS_DIABAN.phattrien_4g.name)
            response.add(danhMuc6)
            DanhMuc danhMuc7 = new DanhMuc()
            danhMuc7.setCODE(Constant.CDS_DIABAN.phattrien_dcrs.code)
            danhMuc7.setNAME(Constant.CDS_DIABAN.phattrien_dcrs.name)
            response.add(danhMuc7)

            loggingManaged.info("[CDSDiaBanV2Controller] - [dsDichVu] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [dsDichVu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TongHopKR001_007>>> ttkd_kd_001_007_TongHop(String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TongHopKR001_007> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_001_007_TongHop] - Request: ",
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

            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_001_007_TongHop] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_001_007_TongHop] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ChiTietKR001_007>>> ttkd_kd_001_007_ChiTiet(String kri, String service, String account, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<ChiTietKR001_007> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_001_007_ChiTiet] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri)
                            .put("service", service).put("account", account != null ? account : "").build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String token = session_.get(TOKEN)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            switch (service) {
                case Constant.CDS_DIABAN.phattrien_brcd.code:
                    String maNV = session_.get(MA_NV)
                    String sqlString = "{ call css.chuyendoiso_db.phattrien_brcd(?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
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
                                sql.eachRow(sqlString, [joined, maNV, fdate, tdate]){rs ->
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
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_001_007_ChiTiet] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_001_007_ChiTiet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_013>>> ttvt_kt_013(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_013> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_013] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.DS_HEN_BAOHONG_NVKT(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
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
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_013] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_013] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_004>>> ttvt_kt_004(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_004> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_004] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.danhgia_hd_dungthu_mytv(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_004(slDungThat: Utils.getStringNumberDataByKey(rs, "CHUYEN_THAT"),
                                slDungThu: Utils.getStringNumberDataByKey(rs, "DANG_DUNG_THU"),
                                slHuyDungThu: Utils.getStringNumberDataByKey(rs, "CHUYEN_HUY")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_004] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_004] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_043>>> ttvt_kt_043(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_043> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_043] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.PhieuKhaoSatB2A(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        String ngay = Utils.getStringDataByKey(rs, "NGAY_CS")
                        if (ngay != null && ngay.trim().length() > 0) {
                            ngay = Utils.convertToDayOther(ngay)
                        }
                        response.add(new TTVT_KT_043(soLuong: Utils.getStringNumberDataByKey(rs, "SL_B2A"), ngay: ngay))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_043] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_043] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_016>>> ttvt_kt_016(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_016> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_016] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_Hon2Lan30Ngay(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_016(soLuong: Utils.getStringNumberDataByKey(rs, "SO_LAN"), soMay: Utils.getStringDataByKey(rs, "SO_MAY")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_016] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_016] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_018>>> ttvt_kt_018(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_018> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_018] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_ThueBaoHong_TTVT_KT_018(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_018(ton: Utils.getStringNumberDataByKey(rs, "TON"), hoanThanh: Utils.getStringNumberDataByKey(rs, "HOAN_THANH")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_018] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_018] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_006>>> ttvt_kt_006(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_006> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_006] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.phieu_lapdatmoi_tiepnhan(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_006(ngay: Utils.getStringDataByKey(rs, "NGAY"), slMoi: Utils.getStringNumberDataByKey(rs, "MOI"),
                                daThucHien: Utils.getStringNumberDataByKey(rs, "DA_THUC_HIEN"), daTraLai: Utils.getStringNumberDataByKey(rs, "DA_TRA_LAI"),
                                daChuyenTo: Utils.getStringNumberDataByKey(rs, "DA_CHUYEN_TO"), daGiaoDi: Utils.getStringNumberDataByKey(rs, "DA_GIAO_DI"),
                                daTHHo: Utils.getStringNumberDataByKey(rs, "DA_TH_HO"), daThuHoi: Utils.getStringNumberDataByKey(rs, "DA_THU_HOI")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_006] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_006] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_031>>> ttvt_kt_031(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_031> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_031] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongMNV_TTVT_KT_031(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_031(duocGiao: Utils.getStringNumberDataByKey(rs, "DUOC_GIAO"),
                                hoanThanh: Utils.getStringNumberDataByKey(rs, "HOAN_THANH"), ngay: Utils.getStringDataByKey(rs, "NGAY")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_031] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_031] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_011>>> ttvt_kt_011(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_011> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_011] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.quanly_thoigian_lapdat(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_011(phieu12: Utils.getStringNumberDataByKey(rs, "PHIEU_12"),
                                phieu12_16: Utils.getStringNumberDataByKey(rs, "PHIEU_12_16"), phieu16: Utils.getStringNumberDataByKey(rs, "PHIEU_16")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_011] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_011] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_005>>> ttvt_ttkt_005(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_005> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_005] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.TT_SuCoMNV_TTVT_TTKT_005(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_005(moi: Utils.getStringNumberDataByKey(rs, "MOI"),
                                daGiao: Utils.getStringNumberDataByKey(rs, "DA_GIAO"), daTraLai: Utils.getStringNumberDataByKey(rs, "DA_TRA_LAI")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_005] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_005] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_020>>> ttvt_kt_020(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_020> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_020] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_ThoiGian_XuLyDichVu(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_020(phieu12: Utils.getStringNumberDataByKey(rs, "PHIEU_12"), tongSo: Utils.getStringNumberDataByKey(rs, "TSPHIEU"),
                                phieu12_16: Utils.getStringNumberDataByKey(rs, "PHIEU_12_16"), phieu16: Utils.getStringNumberDataByKey(rs, "PHIEU_16")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_020] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_020] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_033>>> ttvt_kt_033(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_033> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_033] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoTon_BaoHong(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_033(ngay: Utils.getStringDataByKey(rs, "NGAY"), ton: Utils.getStringNumberDataByKey(rs, "TON")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_033] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_033] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_024>>> ttvt_kt_024(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_024> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_024] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_TiepNhanTuKH(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_024(ngay: Utils.getStringDataByKey(rs, "NGAY"), soLuong: Utils.getStringNumberDataByKey(rs, "SO_PHIEU")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_024] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_033] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_035>>> ttvt_kt_035(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_035> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_035] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.TracNghiem_TTVT_KT_035(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_035(tongCH: Utils.getStringNumberDataByKey(rs, "TONG_CAUHOI"), slTraLoiDung: Utils.getStringNumberDataByKey(rs, "TRALOI_DUNG")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_035] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_035] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_004>>> ttvt_ttkt_004(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_004> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_004] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.BaoTon_BaoHong(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_004(moi: Utils.getStringNumberDataByKey(rs, "MOI"), ton: Utils.getStringNumberDataByKey(rs, "TON"),
                                daTra: Utils.getStringNumberDataByKey(rs, "DA_TRA"), daGiao: Utils.getStringNumberDataByKey(rs, "DA_GIAO")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_004] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_ttkt_004] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_003>>> ttvt_ttkt_003(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_003> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_003] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Thongke_phieu_baohong (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_003(moi: Utils.getStringNumberDataByKey(rs, "sophieu_moi"), ton: Utils.getStringNumberDataByKey(rs, "sophieu_baoton"),
                                daTra: Utils.getStringNumberDataByKey(rs, "sophieu_tralai"), daGiao: Utils.getStringNumberDataByKey(rs, "sophieu_dagiao")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_003] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_ttkt_003] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_041>>> ttvt_kt_041(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_041> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_041] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.ThuThapThiTruongDoiThu_TTVT_KT_041 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_041(ngay: Utils.getStringDataByKey(rs, "ngay"), khac: Utils.getStringNumberDataByKey(rs, "khac"), vietnammobile: Utils.getStringNumberDataByKey(rs, "vietnam_mobile"),
                                mobifone: Utils.getStringNumberDataByKey(rs, "mobifone"), viettel: Utils.getStringNumberDataByKey(rs, "viettel"), fpt: Utils.getStringNumberDataByKey(rs, "fpt")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_041] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_041] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_007>> ttvt_kt_007(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_007 response = new TTVT_KT_007()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_007] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String ton = "0"
            String giao = "0"
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String sqlString = "{ ? = call css.chuyendoiso_db.Lay_sophieu_lapdat_ton(?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, username]) { Integer rs ->
                    if (rs != null && rs >= 0) {
                        ton = String.valueOf(rs)
                    }
                }
            }
            sqlString = "{ ? = call css.chuyendoiso_db.Lay_sophieu_lapdat_giao(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER, fdate, tdate, username]) { Integer rs ->
                    if (rs != null && rs >= 0) {
                        giao = String.valueOf(rs)
                    }
                }
            }
            response.setGiao(giao)
            response.setTon(ton)
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_007] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_007] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_012>>> ttvt_kt_012(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_012> response = []
        List<TTVT_KT_012> responseTmp = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_012] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String sqlString = "{ call css.chuyendoiso_db.Lay_sophieu_bh_hoanthanh(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_012(ngay: Utils.convertToDay(Utils.getStringDataByKey(rs, "ngay_ht")),
                                        hoanThanh: Utils.getStringNumberDataByKey(rs, "soluong"), giao: "0"))
                    }
                }
            }
            sqlString = "{ call css.chuyendoiso_db.Lay_sophieu_bh_giao(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
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
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_012] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_012] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_008>> ttkd_kd_008(String kri) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_008 response = new TTKD_KD_008()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_008] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
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
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_008] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_008] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_009>> ttkd_kd_009(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_009 response = new TTKD_KD_009()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_009] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String phanVungId = session_.get(ID_TINHTHANH)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
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
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_009] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_009] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_010>> ttkd_kd_010(String kri, String chuKy) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_010 response = new TTKD_KD_010()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_010] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("chuKy", chuKy).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.solieu_xuat_hoa_don (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, chuKy, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        response.setGiayBaoCuoc(Utils.getStringNumberDataByKey(rs, "GIAYBAOCUOC"))
                        response.setBienNhanTraTruoc(Utils.getStringNumberDataByKey(rs, "BIENNHAN_TRATRUOC"))
                        response.setBienNhanThanhToan(Utils.getStringNumberDataByKey(rs, "BIENNHANTHANHTOAN"))
                        response.setPhuLucCuocVienThong(Utils.getStringNumberDataByKey(rs, "PL_CUOC_VIENTHONG"))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_010] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_010] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_002>>> ttvt_ttkt_002(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_002> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_002] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Thongke_phieu_lapdat (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_002(moi: Utils.getStringNumberDataByKey(rs, "sophieu_moi"), ton: Utils.getStringNumberDataByKey(rs, "sophieu_baoton"),
                                daTra: Utils.getStringNumberDataByKey(rs, "sophieu_tralai"), daGiao: Utils.getStringNumberDataByKey(rs, "sophieu_dagiao")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_002] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_ttkt_002] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_006>>> ttvt_ttkt_006(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_006> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_006] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Lay_sophieu_chuyento (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_006(ngay: Utils.getStringDataByKey(rs, "ngaychuyen"), soLuong: Utils.getStringNumberDataByKey(rs, "soluong")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_006] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_ttkt_006] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_010>>> ttvt_kt_010(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_010> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_010] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Thongke_quahan_lapdat (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_010(denHan: Utils.getStringNumberDataByKey(rs, "sl_denhan"), quaHan: Utils.getStringNumberDataByKey(rs, "sl_quahan"),
                                chuaToiHan: Utils.getStringNumberDataByKey(rs, "sl_chuatoihan")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_010] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_010] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_018>> ttkd_kd_018(String kri, String kieu, String ngay) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_018 response = new TTKD_KD_018()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_018] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kieu", kieu).put("ngay", ngay).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            List<TTKD_KD_018_BD_CoCau> bdCoCau = []
            List<TTKD_KD_018_BD_Cot> bdCot = []
            List<TTKD_KD_018_DS> danhSach = []
            String sqlString = "{ call css.chuyendoiso_db.Thuquayvong_bieudo_cocau (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [kieu, ngay, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCoCau.add(new TTKD_KD_018_BD_CoCau(slMyVnpt: Utils.getStringNumberDataByKey(rs, "sl_myvnpt"), slKhac: Utils.getStringNumberDataByKey(rs, "sl_khac"),
                                slChuaThu: Utils.getStringNumberDataByKey(rs, "sl_chuathu"), tienMyVnpt: Utils.getStringNumberDataByKey(rs, "tien_myvnpt"),
                                tienChuaThu: Utils.getStringNumberDataByKey(rs, "tien_chuathu"), tienKhac: Utils.getStringNumberDataByKey(rs, "tien_khac")))
                    }
                }
            }
            response.setBdCoCau(bdCoCau)
            sqlString = "{ call css.chuyendoiso_db.Thuquayvong_bieudo_cot (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [kieu, ngay, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCot.add(new TTKD_KD_018_BD_Cot(ngayTT: Utils.getStringDataByKey(rs, "ngay_tt"), slHDDaThu: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu"),
                                slHDDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu_luyke"), tienDaThu: Utils.getStringNumberDataByKey(rs, "tien_dathu"),
                                tienDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "tien_dathu_luyke")))
                    }
                }
            }
            response.setBdCot(bdCot)
            sqlString = "{ call css.chuyendoiso_db.Thuquayvong_danhsach (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [kieu, ngay, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        danhSach.add(new TTKD_KD_018_DS(maTB: Utils.getStringDataByKey(rs, "ma_tb"), tinhTrang: Utils.getStringDataByKey(rs, "tinhtrang"),
                                ngayTT: Utils.getStringDataByKey(rs, "ngay_tt")))
                    }
                }
            }
            response.setDanhSach(danhSach)
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_018] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_018] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_019>> ttkd_kd_019(String kri, String kyHD) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_019 response = new TTKD_KD_019()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_019] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kyHD", kyHD).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            List<TTKD_KD_019_BD_CoCau> bdCoCau = []
            List<TTKD_KD_019_BD_Cot> bdCot = []
            List<TTKD_KD_019_DS> danhSach = []
            String sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_bieudo_cocau (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [kyHD, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCoCau.add(new TTKD_KD_019_BD_CoCau(slMyVnpt: Utils.getStringNumberDataByKey(rs, "soluong_myvnpt"), slKhac: Utils.getStringNumberDataByKey(rs, "soluong_khac"),
                                slChuaThu: Utils.getStringNumberDataByKey(rs, "soluong_chua_thu"), tienMyVnpt: Utils.getStringNumberDataByKey(rs, "sotien_myvnpt"),
                                tienChuaThu: Utils.getStringNumberDataByKey(rs, "sotien_chua_thu"), tienKhac: Utils.getStringNumberDataByKey(rs, "sotien_khac")))
                    }
                }
            }
            response.setBdCoCau(bdCoCau)
            sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_bieudo_cot (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [kyHD, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        bdCot.add(new TTKD_KD_019_BD_Cot(ngayTT: Utils.getStringDataByKey(rs, "ngay_tt"), slHDDaThu: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu"),
                                slHDDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "sl_hoadon_dathu_luyke"), tienDaThu: Utils.getStringNumberDataByKey(rs, "tien_dathu"),
                                tienDaThuLuyKe: Utils.getStringNumberDataByKey(rs, "tien_dathu_luyke")))
                    }
                }
            }
            response.setBdCot(bdCot)
            sqlString = "{ call css.chuyendoiso_db.Thucuoc_gachno_danhsach (?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [kyHD, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        danhSach.add(new TTKD_KD_019_DS(maTT: Utils.getStringDataByKey(rs, "ma_tt"), tinhTrang: Utils.getStringDataByKey(rs, "tinhtrang"),
                                ngayTT: Utils.getStringDataByKey(rs, "ngay_tt")))
                    }
                }
            }
            response.setDanhSach(danhSach)
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_019] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_019] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_TTKT_001>>> ttvt_ttkt_001(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_TTKT_001> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_001] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.CongCu_TTVT_KT_034 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_TTKT_001(congCuBatBuocCo: Utils.getStringNumberDataByKey(rs, "CONG_CU_BAT_BUOC_CO"), congCuBatBuocKhong: Utils.getStringNumberDataByKey(rs, "CONG_CU_BAT_BUOC_KHONG"),
                                congCuKhongBatBuocCo: Utils.getStringNumberDataByKey(rs, "CONG_CU_KHONG_BAT_BUOC_CO"), congCuKhongBatBuocKhong: Utils.getStringNumberDataByKey(rs, "CONG_CU_KHONG_BAT_BUOC_KHONG")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_ttkt_001] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_ttkt_001] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_AM_002>>> ttkd_am_002(String kri) {
        String requestId = genericApi.generateRequestId()
        List<TTKD_AM_002> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_am_002] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Thucuoc_tonghop (?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTKD_AM_002(maNV: Utils.getStringDataByKey(rs, "ma_nv"), tenNV: Utils.getStringDataByKey(rs, "ten_nv"),
                                daThu: Utils.getStringNumberDataByKey(rs, "soluong_chua_thu"), chuaThu: Utils.getStringNumberDataByKey(rs, "soluong_da_thu")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_am_002] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_am_002] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_039>>> ttvt_kt_039(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_039> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_039] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.ThuThapHaTangVNPT_TTVT_KT_039 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_039(ngay: Utils.getStringDataByKey(rs, "ngay"), hopLe: Utils.getStringNumberDataByKey(rs, "HOP_LE"),
                                khongHopLe: Utils.getStringNumberDataByKey(rs, "KHONG_HOP_LE")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_039] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_039] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_019>>> ttvt_kt_019(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_019> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_019] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String maTinh = session_.get(MA_TINH)
            String sqlString = "{ call css.chuyendoiso_db.BaoHong_TTVT_KT_019 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [username, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_019(tong: Utils.getStringNumberDataByKey(rs, "TSPHIEU"), toiHan: Utils.getStringNumberDataByKey(rs, "PHIEU_TOIHAN"),
                                chuaToiHan: Utils.getStringNumberDataByKey(rs, "PHIEU_CHUATOIHAN"), quaHan: Utils.getStringNumberDataByKey(rs, "PHIEU_QUAHAN")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_019] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_019] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_023>>> ttvt_kt_023(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_023> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_023] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.KhaoSat_TTVT_KT_023 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_023(ngay: Utils.getStringDataByKey(rs, "NGAY"), phieuTH: Utils.getStringNumberDataByKey(rs, "PHIEU_TH"),
                                phieuKS: Utils.getStringNumberDataByKey(rs, "PHIEU_KS"), phieuKSHL: Utils.getStringNumberDataByKey(rs, "PHIEU_KS_HL"),
                                phieuKSKOHL: Utils.getStringNumberDataByKey(rs, "PHIEU_KS_KOHL"), phieuKSKhac: Utils.getStringNumberDataByKey(rs, "PHIEU_KS_KHAC")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_023] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_023] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_029>>> ttvt_kt_029(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_029> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_029] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String username = session_.get(USERNAME)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.SuCo_TTVT_KT_029 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_029(ngay: Utils.getStringDataByKey(rs, "NGAY"),
                                phieuGiao: Utils.getStringNumberDataByKey(rs, "PHIEU_GIAO"), phieuXL: Utils.getStringNumberDataByKey(rs, "PHIEU_XL")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_029] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_029] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_030>>> ttvt_kt_030(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_030> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_030] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.BaoDuongDV_TTVT_KT_030 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [username, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_030(phieuGiao: Utils.getStringNumberDataByKey(rs, "PHIEU_GIAO"),
                                phieuXL: Utils.getStringNumberDataByKey(rs, "PHIEU_XL"), ngay: Utils.getStringDataByKey(rs, "ngay_bh")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_030] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_030] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTKD_KD_015>>> ttkd_kd_015(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTKD_KD_015> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_015] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Thongke_khachhang_tiemnang (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTKD_KD_015(daKyHD: Utils.getStringNumberDataByKey(rs, "sl_daky_hd"), hen: Utils.getStringNumberDataByKey(rs, "sl_kh_hen"),
                                khongKyHD: Utils.getStringNumberDataByKey(rs, "sl_khongky_hd"), khac: Utils.getStringNumberDataByKey(rs, "sl_khac")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_015] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_015] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_005>> ttvt_kt_005(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_005 response = new TTVT_KT_005()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_005] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Thongke_thietbi_lapdat (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        response = new TTVT_KT_005(chuaQuet: Utils.getStringNumberDataByKey(rs, "sl_thietbi_chuaquet"), daQuet: Utils.getStringNumberDataByKey(rs, "sl_thietbi_daquet"))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_005] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_005] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_026>>> ttvt_kt_026(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_026> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_026] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.ThuHoiTB_TTVT_KT_026 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [username, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new TTVT_KT_026(ngay: Utils.getStringDataByKey(rs, "ngay"), phaiTH: Utils.getStringNumberDataByKey(rs, "tbi_phai_thuhoi"),
                                daTH: Utils.getStringNumberDataByKey(rs, "tbi_da_thuhoi")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_026] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_026] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_027>> ttvt_kt_027(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_027 response = new TTVT_KT_027()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_027] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.ThuHoiTB_TTVT_KT_027 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response= new TTVT_KT_027(tbHong: Utils.getStringDataByKey(rs, "tbi_hong"), tbSdDuoc: Utils.getStringNumberDataByKey(rs, "tbi_sd_duoc"))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_027] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_027] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTVT_KT_032>> ttvt_kt_032(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTVT_KT_032 response = new TTVT_KT_032()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_032] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.BaoTon_TCMoi_TTVT_KT_032 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        response = new TTVT_KT_032(khongThiCong: Utils.getStringNumberDataByKey(rs, "TON_KHONGTHICONG"), thiCong: Utils.getStringNumberDataByKey(rs, "TON_THICONG"))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_032] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_032] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTVT_KT_040>>> ttvt_kt_040(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<TTVT_KT_040> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_040] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.ThuThapHaTangDoiTu_TTVT_KT_040 (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        response.add(new TTVT_KT_040(ngay: Utils.getStringNumberDataByKey(rs, "NGAY"),
                                moi: Utils.getStringNumberDataByKey(rs, "THUTHAP_MOI"), lai: Utils.getStringNumberDataByKey(rs, "THUTHAP_LAI")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttvt_kt_040] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttvt_kt_040] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_017>> ttkd_kd_017(String kri, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_017 response = new TTKD_KD_017()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_017] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.danhgia_su_dung_mytv_ott (?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        TTKD_KD_017_TuVan tuVan = new TTKD_KD_017_TuVan(daSuDungDaTuVan: Utils.getStringNumberDataByKey(rs, "DASUDUNG_DATUVAN"),
                                daSuDungChuaTuVan: Utils.getStringNumberDataByKey(rs, "DASUDUNG_CHUATUVAN"),
                                chuaSuDungDaTuVan: Utils.getStringNumberDataByKey(rs, "CHUASUDUNG_DATUVAN"))
                        response.setTuVan(tuVan)
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_017] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_017] - Error:", e)
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
            String list = ""
            String sqlString = "select USERNAME from dulieu.cai_app_mytv_ott_ct where TRANG_THAI = 1 and ngay_th >= to_date(? ||' 00:00:00','dd/mm/yyyy hh24:mi:ss') " +
                    "and ngay_th <= to_date(? ||' 23:59:59','dd/mm/yyyy hh24:mi:ss') and nhanvien_id in (select nhanvien_id from admin.nhanvien where ma_nv = ?)"
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
            loggingManaged.info("[CDSDiaBanV2Controller] - [dsDichVuCSKH] - Request: ",
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

            loggingManaged.info("[CDSDiaBanV2Controller] - [dsDichVuCSKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [dsDichVuCSKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<TTKD_KD_011_014>> ttkd_kd_011_014(String kri, String service, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        TTKD_KD_011_014 response = new TTKD_KD_011_014()
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_011_014] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("fdate", fdate).put("tdate", tdate).put("service", service).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maNV = session_.get(MA_NV)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = ""
            switch (service) {
                case Constant.CDS_DIABAN.TTKD_KD_011.code:
                    TTKD_KD_011 list011 = new TTKD_KD_011()
                    sqlString = "{ call css.chuyendoiso_db.THONGKE_CSKH_TTKD_KD_011_013 (?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [maNV, fdate, tdate, 1, CURSOR_PARAMETER]) { ResultSet rs ->
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
                    sqlString = "{ call css.chuyendoiso_db.Thongke_thuebao_roimang (?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [fdate, tdate, maNV, CURSOR_PARAMETER]) { ResultSet rs ->
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
                    sqlString = "{ call css.chuyendoiso_db.THONGKE_CSKH_TTKD_KD_011_013 (?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [maNV, fdate, tdate, 2, CURSOR_PARAMETER]) { ResultSet rs ->
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
                    sqlString = "{ call css.chuyendoiso_db.cskh_khong_luuluong (?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                        sql.call(sqlString, [maNV, fdate, tdate, CURSOR_PARAMETER]) { ResultSet rs ->
                            while (rs.next()) {
                                list014 = new TTKD_KD_014(chuaKS: Utils.getStringNumberDataByKey(rs, "CHUAKHAOSAT"), daKS: Utils.getStringNumberDataByKey(rs, "DAKHAOSAT"))
                            }
                        }
                    }
                    response.setTtkd_kd_014(list014)
                    break
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [ttkd_kd_011_014] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [ttkd_kd_011_014] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatHuy>>> dlDiaBanTongQuat(String kri, String loaiNV, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanTongQuatHuy> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [dlDiaBanTongQuat] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).put("loaiNV", loaiNV).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_tongquat (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
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
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [dlDiaBanTongQuat] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [dlDiaBanTongQuat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanHuy>>> dlDiaBanNguyenNhanHuy(String kri, String loaiNV, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanNguyenNhanHuy> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [dlDiaBanNguyenNhanHuy] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).put("loaiNV", loaiNV).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_nguyennhanhuy (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanNguyenNhanHuy(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                nguyenNhan: Utils.getStringDataByKey(rs, "ten_nguyen_nhan"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [dlDiaBanNguyenNhanHuy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [dlDiaBanNguyenNhanHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoHuy>>> dlDiaBanLyDoHuy(String kri, String loaiNV, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanLyDoHuy> response = []
        try {
            loggingManaged.info("[CDSDiaBanV2Controller] - [dlDiaBanLyDoHuy] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).put("loaiNV", loaiNV).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String username = session_.get(USERNAME)
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_lydohuy (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanLyDoHuy(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                lyDo: Utils.getStringDataByKey(rs, "ten_yeu_to"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanV2Controller] - [dlDiaBanLyDoHuy] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanV2Controller] - [dlDiaBanLyDoHuy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatNgung>>> dlDiaBanTongQuatNgung(String kri, String loaiNV, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanTongQuatNgung> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanTongQuatNgung] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).put("loaiNV", loaiNV).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_tongquat_tn (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanTongQuatNgung(ngay: Utils.convertToDay(Utils.getStringDataByKey(rs, "ngay")),
                                slFiberNgung: Utils.getStringNumberDataByKey(rs, "sl_fiber_ngung"),
                                slFiberPTM: Utils.getStringNumberDataByKey(rs, "sl_fiber_ptm"),
                                slMyTVNgung: Utils.getStringNumberDataByKey(rs, "sl_mytv_ngung"),
                                slMyTVPTM: Utils.getStringNumberDataByKey(rs, "sl_mytv_ptm"),
                                slFiberPSC: Utils.getStringNumberDataByKey(rs, "sl_fiber_psc"),
                                slMyTVPSC: Utils.getStringNumberDataByKey(rs, "sl_mytv_psc")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanTongQuatNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanTongQuatNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanNgung>>> dlDiaBanNguyenNhanNgung(String kri, String loaiNV, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanNguyenNhanNgung> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanNguyenNhanNgung] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).put("loaiNV", loaiNV).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_nguyennhan_TN (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanNguyenNhanNgung(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                nguyenNhan: Utils.getStringDataByKey(rs, "ten_nguyen_nhan"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanNguyenNhanNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanNguyenNhanNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoNgung>>> dlDiaBanLyDoNgung(String kri, String loaiNV, String fdate, String tdate) {
        String requestId = genericApi.generateRequestId()
        List<DLDiaBanLyDoNgung> response = []
        try {
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId)
                    .put("fdate", fdate).put("tdate", tdate).put("loaiNV", loaiNV).put("kri", kri).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String username = session_.get(USERNAME)
            String maTinh = session_.get(MA_TINH)
            String css = session_.get(CSS_SCH)
            String sqlString = "{ call css.chuyendoiso_db.Lay_dl_diaban_lydo_TN (?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(css)).withCloseable { sql ->
                sql.call(sqlString, [fdate, tdate, username, loaiNV, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        response.add(new DLDiaBanLyDoNgung(dichVu: Utils.getStringDataByKey(rs, "dich_vu"),
                                lyDo: Utils.getStringDataByKey(rs, "ten_yeu_to"),
                                soLan: Utils.getStringNumberDataByKey(rs, "solan")))
                    }
                }
            }
            dataUtils.init("", css, maTinh)
            dataUtils.logKri(username, kri)
            loggingManaged.info("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanController] - [dlDiaBanLyDoNgung] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}