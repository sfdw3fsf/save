package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonBuilder
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.BanHangViDienTuThongTin
import io.swagger.api.ChamSocKhachHangApi
import io.swagger.model.dto.*
import io.swagger.model.request.*
import io.swagger.model.response.BasePageResponse
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.PageInfo
import io.swagger.v3.oas.annotations.Parameter
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.OneAppApplication
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.*

import javax.servlet.http.HttpServletRequest
import java.sql.Clob
import java.sql.Date
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class BanHangViDienTuThongTinController implements BanHangViDienTuThongTin{

    private final HttpServletRequest request;
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private final ObjectMapper objectMapper;

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
    @Autowired
    HttpManaged httpManaged

    ObjectMapper json = new ObjectMapper();



    class ObjThongTinPayment
    {
        String payment_code;
        String phanvung_id;
        String bill_month;
        String identifycode;
        String user;
        String payment_method;

        ObjThongTinPayment(String _payment_code, String _phanvung_id,
                           String _bill_month, String _identifycode,
                           String _user, String _payment_method)
        {
            this.payment_code = _payment_code;
            this.phanvung_id = _phanvung_id;
            this.bill_month = _bill_month;
            this.identifycode = _identifycode;
            this.user = _user;
            this.payment_method = _payment_method;
        }
    }

    @Autowired
    BanHangViDienTuThongTinController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    @Override
    ResponseEntity<BaseResponse> thanhToanViEU(BanHangViDienTuThongTinRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String path = "/banHangViDienTuThongTin/thanhtoan_vi_eu"
        Map input_log = [:]
        try {
            input_log["request"] = new Gson().toJson(requestBody)
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String identifycode = session_.get(IDENTIFY)
            String user = session_.get(USERNAME)

            String _code = requestBody.code
            // tạo array string để for
            String[] arrrayCode = _code.split(',')

            String sql_thongtin_payment = "{ ? = call banhang.pkg_thanhtoan_vi_dientu.fn_thongtin_payment(?,?) }"
            if (phanVungId == "21") {
                sql_thongtin_payment = "{ ? = call banhang.pkg_thanhtoan_vi_dientu.fn_thongtin_payment_retry(?,?,21) }"
            }
            String ttsString = ""
            String urlPost = "http://10.156.4.85/mediagw/g/inquire_partner"

            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json;charset=UTF-8")

            String _order_not_exists = ""

            for (int i = 0; i < arrrayCode.length; ++i) {
                ttsString = ""
                try {
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sql_thongtin_payment, [Sql.CLOB, arrrayCode[i], 1])
                                { Clob rs ->
                                    ttsString = rs.getCharacterStream().getText()
                                }
                    }
                } catch (Exception ex){
                    _order_not_exists += arrrayCode[i] + " call function Lỗi; "
                }
                input_log["kq_call_func"] = ttsString
                if (ttsString != ""){
                    String _paymentCode = ttsString
                    // Tạo Body Post lên
                    ObjThongTinPayment body = new ObjThongTinPayment(
                            _paymentCode, phanVungId.toString(), "", identifycode, user, "99"
                    )

                    input_log["input_inquire_partner"] = new Gson().toJson(body)
                    try {
                        String resp = httpManaged.post([url: urlPost], headers, json.writeValueAsString(body))
                        input_log["kq_inquire_partner"] = resp
                        // return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
                    }
                    catch (Exception ex) {
                        // return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        _order_not_exists += arrrayCode[i] + " post inquire_partner Lỗi; "
                        input_log["loi_inquire_partner"] = ex.message
                    }
                }
            }

            if (_order_not_exists == "")
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of("Đã hoàn thành: " + _code))
            }
            else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, _order_not_exists, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception ex) {
            input_log["error"] = ex.message
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ex.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }finally {
            loggingManaged.info(input_log)
        }
    }


}
