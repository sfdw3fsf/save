package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.CDSDiaBanHcmApi
import io.swagger.model.response.BaseResponse
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.service.VisaService
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import java.sql.ResultSet

@RestController
class CDSDiaBanHCMController implements CDSDiaBanHcmApi {
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
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    DataUtils dataUtils

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    VisaService visaService

    @Autowired
    CDSDiaBanHCMController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlTBPscConNoCuoc(String p_kycuoc, String p_loaihinh, String p_kieubc, String p_makv, String p_user) {
        List<Map<String, Object>> response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlTBPscConNoCuoc] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.BSS_91992_HCM_SLTB_PSC_CONNO_CUOC(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, p_kycuoc, username, p_loaihinh, p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlTBPscConNoCuoc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [laySlTBPscConNoCuoc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlTBHuyDangHoatDong(String p_loaibc, String p_loaihinh, String p_fdate, String p_tdate, String p_user, String p_makv) {
        List<Map<String, Object>> response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlTBHuyDangHoatDong] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.sp_LayHuyDangHD(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, p_loaibc, username, p_fdate, p_tdate, p_loaihinh, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlTBHuyDangHoatDong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [laySlTBHuyDangHoatDong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlTBDungDoYeuCau(String p_loaibc, String p_loaihinh, String p_fdate, String p_tdate, String p_user, String p_makv) {
        List<Map<String, Object>> response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlTBDungDoYeuCau] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.sp_LayDungDoYc(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, p_loaibc, username, p_fdate, p_tdate, p_loaihinh, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlTBDungDoYeuCau] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [laySlTBDungDoYeuCau] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<List<Map<String, Object>>>> layTongQuanDianBanKyThuat(String p_user, String p_thang) {
        List<Map<String, Object>> response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [layTongQuanDianBanKyThuat] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.BSS_91993_HCM_TONGQUAN_DIANBAN_KYTHUAT(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, username, p_thang, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [layTongQuanDianBanKyThuat] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [layTongQuanDianBanKyThuat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<List<Map<String, Object>>>> layChiTietGiaHanTbTraTruoc(String p_thang, String p_loaihinh, String p_kieubc, String p_makv, String p_user) {
        List<Map<String, Object>> response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [layChiTietGiaHanTbTraTruoc] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call REPORT.PKG_HCM_KPI.BSS_91990_TTVT2_KT003_TINHINH_TRATRUOC_ODIABAN(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, p_thang, username, p_loaihinh, p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [layChiTietGiaHanTbTraTruoc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [layChiTietGiaHanTbTraTruoc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<List<Map<String, Object>>>> layThongTinChiSoPhatTrien(String p_thang, String p_user) {
        List<Map<String, Object>> response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [layThongTinChiSoPhatTrien] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.sp_LayTyLeTB_KKSC_PTM_PSC_TraTruoc(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, username, p_thang, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [layThongTinChiSoPhatTrien] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [layThongTinChiSoPhatTrien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<?>> layThongTinChiSoBaoHong(Number p_kieubc, String p_loaihinh, String p_thangbc, String p_user, String p_makv) {
        List response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [layThongTinChiSoBaoHong] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.BSS_91991_TTVT2_KT006_CHISO_BAOHONG(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, p_thangbc, username, p_loaihinh,p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [layThongTinChiSoBaoHong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [layThongTinChiSoBaoHong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlThueBaoMoChemMoTD(Number p_kieubc,Number p_loaihinh,String p_thang,String p_user,String p_makv) {
        List response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlThueBaoMoChemMoTD] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.sp_LaySLThueBaoMoChemMoTD(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, p_thang, username, p_loaihinh,p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [laySlThueBaoMoChemMoTD] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [laySlThueBaoMoChemMoTD] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> thongKeThucTang(Number p_kieubc,String p_loaihinh,String p_kycuoc,  String p_user,  String p_makv) {
        List response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [thongKeThucTang] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.BSS_91994_HCM_THONGKE_THUCTANG_ODB(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId,p_kycuoc, username, p_loaihinh,p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [thongKeThucTang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [thongKeThucTang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> thongKeHopLucDiaBan(Number p_kieubc, String p_loaihinh, String p_tungay, String p_denngay,String p_user, String p_makv) {
        List response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [thongKeHopLucDiaBan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.BSS_92050_HCM_TTVT_2_KT_008_HOP_LUC_DIA_BAN(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId,p_tungay,p_denngay, username, p_loaihinh,p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [thongKeHopLucDiaBan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [thongKeHopLucDiaBan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> sp_LayThueBaoChemDungTrongThang(String p_user, Number p_loaihinh, Number p_kieubc,String p_makv) {
        List response
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CDSDiaBanHCMController] - [thongKeHopLucDiaBan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(GenericApi.CSS_SCH)
            String phanVungId = session_.get(GenericApi.ID_TINHTHANH)
            String username = "";
            if (p_user == "0" || p_user == "") {
                username = session_.get(GenericApi.USERNAME)
            } else {
                username = p_user
            }

            String sqlString = "{ call report.PKG_HCM_KPI.sp_LayThueBaoChemDungTrongThang(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource("report")).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, username, p_loaihinh,p_kieubc, p_makv, CURSOR_PARAMETER]) { ResultSet rs ->
                    response = resultSetToObject.getDynamicData(rs)
                }
            }
            loggingManaged.info("[CDSDiaBanHCMController] - [sp_LayThueBaoChemDungTrongThang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response ?: "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CDSDiaBanHCMController] - [sp_LayThueBaoChemDungTrongThang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

}
