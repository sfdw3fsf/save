package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonBuilder
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.CTSApi
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.DoQuangRequest
import vn.vnptit.sapi.oneapp.service.CTSService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.getMA_TINH
import static vn.vnptit.sapi.oneapp.resources.GenericApi.getTOKEN

@RestController
class CTSController implements CTSApi{
    private HttpServletRequest request
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    CTSService ctsService


    @Autowired
    ResultSetToObject resultSetToObject


    @Autowired
    CTSController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    private Map<String,Object> SP_LAY_THONGTIN_PORT_MATB(Integer phanVungId, String maThueBao,String dataSrc) throws
            Exception{
        Map<String,Object> data = null
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->

            //CSS.SP_LAY_THONGTIN_PORT_MATB(?,?,?)
            sql.call("{ call CSS.LAY_PORT_THEOMATB(?,?,?,?) }", [
                    phanVungId, maThueBao, 2,  CURSOR_PARAMETER]) { ResultSet rs ->
                if( rs != null && rs.next()){
                    data = resultSetToObject.getObjectDynamicData(rs)
                }
            }
        }
        return data
    }

    @Override
    ResponseEntity<BaseResponse<Map<String,Object>>> layThongTinPortCuaMaThueBao(String maThueBao) {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String,Object> data = null
            Map<String, Object> session_ = genericApi.getSessionData(request);
            Integer phanVungId = java.lang.Integer.parseInt(session_.get("id_tinhthanh").toString())
            String dataSrc = session_.get("css_sch")
            if (maThueBao == null || maThueBao.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            loggingManaged.info("[CTSController] - [layThongTinPortCuaMaThueBao] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("maTb", maThueBao)
                    .build())

            data = SP_LAY_THONGTIN_PORT_MATB(phanVungId, maThueBao, dataSrc)

            loggingManaged.info("[CTSController] - [layThongTinPortCuaMaThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            if (data == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, HttpStatus.NO_CONTENT))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[CTSController] - [layThongTinPortCuaMaThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Map<String,Object>>> doQuang(DoQuangRequest doQuangRequest) {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String,Object> data = null
            Map<String, Object> resDoQuang = new HashMap<>()
            resDoQuang.TestOnuPorts = null
            resDoQuang.spLayThongTinPortMatb = null
            resDoQuang.GetUserInforByAccount = null
            resDoQuang.GetDeviceInformationByOptical = null
            Map<String, Object> session_ = genericApi.getSessionData(request);
            Integer phanVungId = java.lang.Integer.parseInt(session_.get("id_tinhthanh").toString())
            String dataSrc = session_.get("css_sch")
            String token = session_.get("token")
            ctsService.init(token)
            if (doQuangRequest.maThueBao == null || doQuangRequest.maThueBao.isEmpty()) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            data = SP_LAY_THONGTIN_PORT_MATB(phanVungId, doQuangRequest.maThueBao, dataSrc)
            if (data == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId,
                        "Không có dữ liệu thông tin port", HttpStatus.NO_CONTENT))
            }
            resDoQuang.spLayThongTinPortMatb = data
            /*
            def res = ctsService.TestOnuPort(data.deviceip.toString() , data.rack.toString(), data.
                    sefl.toString(), data.
                    slot.toString(), data.
                    port.toString(), String.valueOf(data.vpi), String.valueOf(data.vci.toString()))
             */
            def res = ctsService.testOnuPort_vb8362(data.ip.toString().trim(), "", data.rack.toString().trim(),
                    data.slot.toString().trim(), data.sport.toString().trim(), String.valueOf(data.vpi).trim())
            if(res != null && res["data"] != null ) {
                resDoQuang.TestOnuPorts = res["data"][0]
                //resDoQuang.TestOnuPorts["OnuDistance"] = resDoQuang.TestOnuPorts["OnuDistance"].toString()
                resDoQuang.TestOnuPorts["Distance"] = resDoQuang.TestOnuPorts["Distance"].toString()
                resDoQuang.TestOnuPorts["OnuDistance"] = resDoQuang.TestOnuPorts["Distance"].toString()
                resDoQuang.TestOnuPorts["OnuTx"] = resDoQuang.TestOnuPorts["OnuPowerTx"].toString()
                resDoQuang.TestOnuPorts["OltTx"] = resDoQuang.TestOnuPorts["OltPowerTx"].toString()
                resDoQuang.TestOnuPorts["OltRx"] = resDoQuang.TestOnuPorts["OltPowerRx"].toString()
                resDoQuang.TestOnuPorts["OnuRx"] = resDoQuang.TestOnuPorts["OnuPowerRx"].toString()
                resDoQuang.TestOnuPorts["onuDescription"] = resDoQuang.TestOnuPorts["Decr"].toString()
                resDoQuang.TestOnuPorts["AuthMode"] = resDoQuang.TestOnuPorts["AuthMode"].toString()
                resDoQuang.TestOnuPorts["RateDS"] = resDoQuang.TestOnuPorts["RateDS"].toString()
                resDoQuang.TestOnuPorts["RateUS"] = resDoQuang.TestOnuPorts["RateUS"].toString()
                resDoQuang.TestOnuPorts["AttenuationDs"] = resDoQuang.TestOnuPorts["AttenuationDs"].toString()
                resDoQuang.TestOnuPorts["AttenuationUs"] = resDoQuang.TestOnuPorts["AttenuationUs"].toString()
                resDoQuang.TestOnuPorts["PhaseState"] = resDoQuang.TestOnuPorts["PhaseState"].toString()
            }
            String serialNo
            def resUserInfoByAccount = ctsService.getUserInforByAccount(doQuangRequest.maThueBao,
                    doQuangRequest.idDieuKienTimKiem)
            if(resUserInfoByAccount != null && resUserInfoByAccount["data"] != null) {
                resDoQuang.GetUserInforByAccount = resUserInfoByAccount["data"][0]
                serialNo =  resUserInfoByAccount["data"][0]["SERIALNO"]
            }
            if(serialNo != null) {
                def resDeviceInformationByOptical = ctsService.getDeviceInformationByOptical(serialNo)
                if(resDeviceInformationByOptical != null && resDeviceInformationByOptical["data"] != null) {
                    resDoQuang.GetDeviceInformationByOptical = resDeviceInformationByOptical["data"][0]
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resDoQuang))
        }catch (Exception e) {
            loggingManaged.error("[CTSController] - [layThongTinPortCuaMaThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


}
