package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.google.gson.Gson
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.DVITCareApi
import io.swagger.model.dto.*
import io.swagger.model.dto.DVITCare.COMBOBOX_GIATRI_V2
import io.swagger.model.request.*
import io.swagger.model.response.BaseResponse
import io.swagger.model.response.DVITCareLayThongtinBaotriLienheResponse
import io.swagger.model.response.DVITCareLayThongtinBaotriTonghopResponse
import oracle.jdbc.OracleTypes
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.DVITCareCapnhatKqITCareMT03Request
import vn.vnptit.sapi.oneapp.model.request.DVITCareDSDonvXulyRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareGhilaiThuoctinhSUBBDDVRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareGhilaiThuoctinhSUBRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareGiaophieuKSLapDatMoiRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareGiaophieuPhoihopThicongRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareHienthiThongtinDVCNTTRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareHiethiThongtinDVCNTTBDDVRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareHuyGiaoPhieuPhoihopRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareKiemtraDKGiaoPhoihopRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayDSThuocTinhSubV2Request
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayDSThuoctinhSUBBDDVRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayDsKhuvucTheoHdDbRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayTTNhanvienYCKhaosatRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayThongtinBaotriLienheRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayThongtinBaotriRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareLayThongtinBaotriTonghopRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareObjCapNhat_KhaoSatRequest
import vn.vnptit.sapi.oneapp.model.request.DVITCareXoaHdtbCnttSubRequest
import vn.vnptit.sapi.oneapp.model.request.INSERT_HOSO_ITCARE_SMPRequest
import vn.vnptit.sapi.oneapp.model.response.DVITCareHIENTHITHONGTINDVCNTTResponse
import vn.vnptit.sapi.oneapp.util.*

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class DVITCareController implements DVITCareApi {

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
    ResultSetToObject resultSetToObject

    @Autowired
    DVITCareController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    ResponseEntity<BaseResponse> lay_ds_hdtb_tiepnhan_ks(DVITCareLayDsHdtbTiepnhanKsRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanvien_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.LAY_DS_HDTB_TIEPNHAN_KS(?,?,?,?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdkh_id,
                                     requestBody.vtthd_id,
                                     requestBody.vloaihd_id,
                                     Long.parseLong(nhanvien_id),
                                     requestBody.vma_gd,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_hdtb_tiepnhan_ks] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_db_theo_matb(DVITCareLayDbTheoMatbRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.TRACUU_DANHBA_COMMON.lay_db_theo_matb(?,?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.in_ma_tb,
                                     requestBody.in_donvi_dl_id,
                                     requestBody.in_dichvuvt_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_db_theo_matb] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsloaigiayto() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "select LOAIGT_ID ID, LOAI_GT Name from CSS.loai_gt order by MA_GT"
            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsloaigiayto] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsgioitinh() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "select 0 ID, 'Nam' NAME from dual \n" +
                    "union \n" +
                    "select 1 ID, 'Nữ' NAME from dual"
            List<DanhMuc> data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsgioitinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // Hàm chung lấy danh sách
    List getDVITCareDSResponse(String dataSrc, String phanVungId, String _vCHUOI){
        String sqlString = "{ call css.bangtudien_common.LAY_DANHMUC_LAPMOI_TT_CHUNG_V7" +
                "(?,?," +
                "?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?) }"

        List data = []
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Long.parseLong(phanVungId), _vCHUOI,
                                 CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,
                                 CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,
                                 CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,
                                 CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,
                                 CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,
                                 CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER,CURSOR_PARAMETER
            ]) { ResultSet rsTRANGBI, ResultSet rsLOAI_TB, ResultSet rsCHU_QUAN, ResultSet rsTIEN_KM, ResultSet rsDOITUONG,
                 ResultSet rsDICHVU_VT, ResultSet rsLOAIHINH_TB, ResultSet rsLOAI_KHL, ResultSet rsLOAI_GT, ResultSet rsPHANLOAI_KH,
                 ResultSet rsLOAI_KH, ResultSet rsNGANHANG, ResultSet rsHINHTHUC_TT, ResultSet rsHINHTHUC_TC, ResultSet rsDANTOC,
                 ResultSet rsNGANHNGHE, ResultSet rsQUOCTICH, ResultSet rsHINHTHUC_TTHI, ResultSet rsKIEU_HD, ResultSet rsGOI_KT,
                 ResultSet rsDONVI_QL, ResultSet rsKIEU_LD, ResultSet rsNHACC_CU, ResultSet rsCONGNGHE, ResultSet rsHINHTHUC_CM,
                 ResultSet rsTHOIHAN, ResultSet rsLOAI_CSHT, ResultSet rsDOITAC, ResultSet rsLOAIIP
                ->
                switch (_vCHUOI){
                    case "QUOCTICH":
                        data = resultSetToObject.getDynamicData(rsQUOCTICH)
                        break;
                    case "NGANHNGHE":
                        data = resultSetToObject.getDynamicData(rsNGANHNGHE)
                        break;
                    case "DICHVU_VT":
                        data = resultSetToObject.getDynamicData(rsDICHVU_VT)
                        break;
                    case "LOAIHINH_TB":
                        data = resultSetToObject.getDynamicData(rsLOAIHINH_TB)
                        break;
                    case "DOITUONG":
                        data = resultSetToObject.getDynamicData(rsDOITUONG)
                        break;
                }
            }
        }
        return data
    }

    @Override
    ResponseEntity<BaseResponse> dsquoctich() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String _vCHUOI = 'QUOCTICH'
            List data = getDVITCareDSResponse(dataSrc, phanVungId, _vCHUOI)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsquoctich] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> dsnganhnghe() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String _vCHUOI = 'NGANHNGHE'
            List data = getDVITCareDSResponse(dataSrc, phanVungId, _vCHUOI)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsnganhnghe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> dsdichvu_vt() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String _vCHUOI = 'DICHVU_VT'
            List data = getDVITCareDSResponse(dataSrc, phanVungId, _vCHUOI)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsdichvu_vt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> dsloaihinh_tb() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String _vCHUOI = 'LOAIHINH_TB'
            List data = getDVITCareDSResponse(dataSrc, phanVungId, _vCHUOI)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsloaihinh_tb] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> dskieuyeucau() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "select kieuld_id ID, ten_kieuld Name from css.kieu_ld where kieuld_id = 571"
            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
            List<DanhMuc> data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dskieuyeucau] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> dsdonvixuly(DVITCareDSDonvXulyRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)

            String sqlString = "{ call CSS.DANHMUC_TRACUU_KH_DONHANG_SMP(?,?,?,?) }"
            List<DanhMuc> data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                      60,
                                      requestBody.vloaidv_id,
                                      CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsdonvixuly] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> dsdoituong() {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String _vCHUOI = 'DOITUONG'
            List data = getDVITCareDSResponse(dataSrc, phanVungId, _vCHUOI)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [dsdoituong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lap_hop_dong_xoa_hdkh(DVITCareLapHopDongXoaHDKHRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAP_HOP_DONG.XOA_HDKH_V2(?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdkh_id,
                                     requestBody.vloaihd_id]) { Object rs ->
                    if (rs != null) message = (String) rs
                }
            }
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lap_hop_dong_xoa_hdkh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_danhsach_hd_ks_cntt(DVITCareLayDanhSachHDKSCNTTRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String donvi_id = session_.get(ID_DONVI)
            String sqlString = "{ call CSS.LAY_DANHSACH_HD_KS_CNTT(?,?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vdichvuvt_id,
                                     Long.parseLong(donvi_id),
                                     Long.parseLong(nguoidung_id),
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_danhsach_hd_ks_cntt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> capnhat_hoanthanh_ks_cntt(DVITCareCapnhatHoanthanhKSCNTTRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            LocalDateTime now = LocalDateTime.now()
            String ngay_ht = dtf.format(now)

            String sqlString = "{ call CSS.CAPNHAT_HOANTHANH_KS_CNTT(?,?,?,?,?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     requestBody.vketquaks,
                                     requestBody.vloaihd_id,
                                     requestBody.vphieu_id,
                                     Long.parseLong(nguoidung_id),
                                     may_cn,
                                     ngay_ht,
                                     user_name]) { Object rs ->
                    if (rs != null) message = (String) rs
                }
            }
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [capnhat_hoanthanh_ks_cntt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_ds_thuebao_thicong_cntt_mobile(DVITCareLayDSThuebaoThicongCNTTMobileRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.LAY_DS_THUEBAO_THICONG_CNTT_MOBILE(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     Long.parseLong(nguoidung_id),
                                     requestBody.vtthd_id,
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_thuebao_thicong_cntt_mobile] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_ct_thuebao_thicong_mobile(DVITCareLayCTThuebaoThicongMobileRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_CT_THUEBAO_THICONG_MOBILE(?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.VHDTB_ID,
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ct_thuebao_thicong_mobile] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> danhmuc_tracuu_kh_donhang_smp(DVITCareDanhmucTracuuKHDonhangSMPRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.DANHMUC_TRACUU_KH_DONHANG_SMP(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vinput_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [danhmuc_tracuu_kh_donhang_smp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_ds_khuvuc_theo_hd_db(DVITCareLayDsKhuvucTheoHdDbRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.TRACUU_DANHBA.lay_ds_khuvuc_theo_hd_db(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     requestBody.vinhd_db,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_khuvuc_theo_hd_db] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> chaytuyen_nvtc_thuno(DVITCareChayTuyenNvtcThunoRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call qltn.chaytuyen_nvtc_thuno_v4(?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vma_tt,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [chaytuyen_nvtc_thuno] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> quayvong_lay_tt_phieu_nvtc(DVITCareQuayVongLayTTPhieuNvtcRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.quayvong_lay_tt_phieu_nvtc(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     Long.parseLong(nguoidung_id),
                                     requestBody.vthuebao_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [quayvong_lay_tt_phieu_nvtc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_nhom_lydo_tratruoc(DVITCareLayNhomLydoTratruocRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.LAY_NHOM_LYDO_TRATRUOC(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vnhomcon_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)

                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_nhom_lydo_tratruoc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> capnhat_kq_quayvong_itcare(DVITCareCapnhatKqQuayvongItCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.capnhat_kq_quayvong_itcare(?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vhdtb_id ,
                                     requestBody.vphieu_id  ,
                                     requestBody.vthuebao_id  ,
                                     requestBody.vkqxl_id  ,
                                     requestBody.vghichu  ,
                                     requestBody.vngay_hen  ,
                                     requestBody.vghichu_hen  ,
                                     requestBody.vten_kh  ,
                                     requestBody.vdienthoai_kh  ,
                                     Long.parseLong(nguoidung_id),
                                     Sql.VARCHAR]) { String verr ->
                    message = verr
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [capnhat_kq_quayvong_itcare] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> capnhat_thanhly_quayvong_itcare(DVITCareCapnhatThanhlyQuayvongItCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.capnhat_thanhly_quayvong_itcare(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vhdtb_id ,
                                     requestBody.vphieu_id  ,
                                     requestBody.vthuebao_id  ,
                                     requestBody.vkqxl_id  ,
                                     requestBody.vghichu  ,
                                     requestBody.vngay_hen  ,
                                     requestBody.vghichu_hen  ,
                                     requestBody.vten_kh  ,
                                     requestBody.vdienthoai_kh  ,
                                     Long.parseLong(nguoidung_id),
                                     requestBody.vma_nd  ,
                                     Sql.VARCHAR]) { String verr ->
                    message = verr
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [capnhat_thanhly_quayvong_itcare] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> kiemtra_luong_thaotac_it_care(DVITCareKiemtraLuongThaotacITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ ? = call css.kiemtra_luong_thaotac_it_care(?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     requestBody.vluong_id,
                                     requestBody.vthaotac_id
                ]) { String rs ->
                    message = rs
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                message = "Quy trình chưa được gán luồng thác tác này, vui lòng liên hệ admin để được hỗ trợ!"
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [kiemtra_luong_thaotac_it_care] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_donvi_phoihop_itcare(DVITCareLayDonviPhoihopITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donvi_id = session_.get(ID_DONVI)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.lay_donvi_phoihop_itcare(?,?,?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     Long.parseLong(donvi_id),
                                     Long.parseLong(nguoidung_id),
                                     requestBody.vphieu_id  ,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_donvi_phoihop_itcare] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_ds_phieu_phoihop_it_care(DVITCareLayDsPhieuPhoihopITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_ds_phieu_phoihop_it_care(?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vphieu_id  ,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_phieu_phoihop_it_care] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> capnhat_lichhen_khaosat(DVITCareCapnhatLichhenKhaosatRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donvi_id = session_.get(ID_DONVI)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)
            String sqlString = "{ ? = call css.capnhat_lichhen_khaosat(?,?,?,?,?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     Long.parseLong(nguoidung_id),
                                     Long.parseLong(donvi_id),
                                     requestBody.vnoidung_hen,
                                     requestBody.vtungay,
                                     requestBody.vdenngay,
                                     user_name,
                                     may_cn
                ]) { String rs ->
                    message = rs
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [capnhat_lichhen_khaosat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_ds_cauhoi_theoloainv(DVITCareLayDsCauhoiTheoLoaiNVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call FIMS.lay_ds_cauhoi_theoloainv(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloainv_id  ,
                                     requestBody.vhinhthucks_id  ,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_cauhoi_theoloainv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_thuebao_xacnhan_baotri(DVITCareLayThuebaoXacnhanBaotriRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call FIMS.lay_thuebao_xacnhan_baotri(?,?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vtag  ,
                                     requestBody.vid  ,
                                     Long.parseLong(nguoidung_id),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_thuebao_xacnhan_baotri] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_ketqua_traloi_itcare(DVITCareLayKetquaTraloiITCareRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.lay_ketqua_traloi_itcare(?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vtbtl_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ketqua_traloi_itcare] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> capnhat_kq_itcare_mt(DVITCareCapnhatKQITCareMTRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)
            String sqlString = "{ call FIMS.capnhat_kq_itcare_mt02(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        Long.parseLong(phanVungId),
                        requestBody.vhdtb_id,
                        requestBody.vbaohong_id,
                        requestBody.vtltb_id,
                        requestBody.vnguoi_tl,
                        requestBody.vdienthoai_lh,
                        Long.parseLong(nguoidung_id),
                        requestBody.vhinhthuc,
                        requestBody.vtrangthai,
                        requestBody.vghichu,
                        requestBody.vds_traloi,
                        user_name,
                        may_cn,
                        vip_cn,
                        requestBody.vnguoidung_id,
                        Sql.VARCHAR]) { String verr ->
                    message = verr
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [capnhat_kq_itcare_mt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    //    2. Giao việc 2.1. Load combobox đơn vị
    //    2.2. Load combobox nhân viên
    //    2.3. Load nhiệm vụ
    @Override
    ResponseEntity<BaseResponse> danhmuc_thongtin_gnv(DVITCareDanhMucThongTinGNVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String donvi_id = session_.get(ID_DONVI)
            String sqlString = "{ call CSS.DANHMUC_THONGTIN_GNV(?,?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     donvi_id,
                                     nguoidung_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [danhmuc_thongtin_gnv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    //    2.4. Load danh sách phiếu giao
    @Override
    ResponseEntity<BaseResponse> lay_ds_gv_theo_phieu_id(DVITCareLayDsGVTheoPhieuIDRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DS_GV_THEO_PHIEU_ID(?,?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        Long.parseLong(phanVungId),
                        requestBody.vkieu,
                        requestBody.vphieu_id,
                        CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_gv_theo_phieu_id] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    //    2.5. Giao việc và xóa giao việc
    @Override
    ResponseEntity<BaseResponse> giaoviec_thuquayvong_nvkt(DVITCareGiaoviecThuquayvongNvktRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.giaoviec_thuquayvong_nvkt(?,?,?,?,?,?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        Long.parseLong(phanVungId),
                        requestBody.vkieu,
                        requestBody.vphieu_id,
                        requestBody.vthuebao_id,
                        requestBody.vnhanvien_th_id,
                        Long.parseLong(nguoidung_id),
                        requestBody.vghichu,
                        requestBody.vnhiemvu_id,
                        requestBody.vnhiemvu,
                        Sql.VARCHAR]) { String verr ->
                    message = verr
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [giaoviec_thuquayvong_nvkt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_thongtin_baotri_tonghop(DVITCareLayThongtinBaotriTonghopRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.lay_thongtin_baotri_tonghop(?,?,?,?,?,?) }"
            DVITCareLayThongtinBaotriTonghopResponse data = new DVITCareLayThongtinBaotriTonghopResponse();
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu_id,
                                     requestBody.vid,
                                     Long.parseLong(nguoidung_id),
                                     CURSOR_PARAMETER,
                                     CURSOR_PARAMETER]) { ResultSet rs_d_hd, ResultSet rs_ds_dt ->
                    data.setD_hd(resultSetToObject.getDynamicData(rs_d_hd))
                    data.setDs_dt(resultSetToObject.getDynamicData(rs_ds_dt))
                }
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_thongtin_baotri_tonghop] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_thongtin_baotri_lienhe(DVITCareLayThongtinBaotriLienheRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_thongtin_baotri_lienhe(?,?,?,?,?,?) }"
            DVITCareLayThongtinBaotriLienheResponse data = new DVITCareLayThongtinBaotriLienheResponse();
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu_id,
                                     requestBody.vid,
                                     requestBody.vloai_id,
                                     CURSOR_PARAMETER,
                                     CURSOR_PARAMETER]) { ResultSet rs_d_zalo, ResultSet rs_ds_email ->
                    data.setZalo(resultSetToObject.getDynamicData(rs_d_zalo))
                    data.setEmail(resultSetToObject.getDynamicData(rs_ds_email))
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_thongtin_baotri_lienhe] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> capnhat_kq_itcare_mt03(DVITCareCapnhatKqITCareMT03Request requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String donvi_id       = session_.get(ID_DONVI)
            String vmay_cn =    session_.get(DEVICE_ID)
            String vnguoi_cn = session_.get(USERNAME)

            String sqlString = "{ call FIMS.capnhat_kq_itcare_mt03(?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vkieu,
                                     requestBody.vgr_id,
                                     requestBody.vhdkh_id,

                                     requestBody.vds_id,
                                     requestBody.vbaohong_id,
                                     requestBody.vloai_id,

                                     requestBody.vnhanvien_th,
                                     requestBody.vdonvi,
                                     requestBody.vdienthoai_nv,

                                     requestBody.vten_kh,
                                     requestBody.vnguoi_dd,
                                     requestBody.vchucvu,

                                     requestBody.vdienthoai_kh,
                                     requestBody.vgoi_dv,
                                     requestBody.vsl_tbi,

                                     requestBody.vtinh_trang,
                                     requestBody.vnguyennhan,
                                     requestBody.vkhacphuc,

                                     requestBody.vketqua_id,
                                     Long.parseLong(nguoidung_id),
                                     Long.parseLong(donvi_id),
                                     vmay_cn,
                                     vnguoi_cn,
                                     Sql.VARCHAR]) { String verr ->
                    message = verr
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [capnhat_kq_itcare_mt03] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> kiemtra_dk_giao_phoihop(DVITCareKiemtraDKGiaoPhoihopRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ ? = call css.kiemtra_dk_giao_phoihop(?,?,?,?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     requestBody.vphieu_id,
                                     requestBody.vdonvi_id,
                                     requestBody.vten_dv
                ]) { String rs ->
                    message = rs
                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [kiemtra_dk_giao_phoihop] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> giaophieu_phoihop_thicong(DVITCareGiaophieuPhoihopThicongRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)
            String vnhanvien_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.giaophieu_phoihop_thicong(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vphieu_id,
                                     vnhanvien_id,
                                     requestBody.vnoidung_giao,
                                     requestBody.vdonvi_nhan_id,
                                     may_cn,
                                     user_name
                ]) {
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [giaophieu_phoihop_thicong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    //    3.3 Chi tiết hợp đồng CNTT 1. Form thuộc tính chi tiết
    @Override
    ResponseEntity<BaseResponse> hienthi_thongtin_dvcntt(DVITCareHienthiThongtinDVCNTTRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)

            List dt_cntt = []
            String sqlString = "{ call CSS.TRACUU_HOPDONG.lay_ds_hdtb_cntt_theo_hdtb_id(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.hdtb_id,
                                     CURSOR_PARAMETER
                ]) {ResultSet rs ->
                    dt_cntt = resultSetToObject.getDynamicData(rs)
                }
            }

            List dt_properties = []
            sqlString = "{ call CABMAN.LAY_DANHSACH_THUOCTINH_V3(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT",
                                     "00000",
                                     CURSOR_PARAMETER
                ]) {ResultSet rs ->
                    dt_properties = resultSetToObject.getDynamicData(rs)
                }
            }

            List dtCompare = []
            sqlString = "{ call css.lay_tentruong_itcare(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vtthd_id,
                                     requestBody.vloaitb_id,
                                     CURSOR_PARAMETER
                ]) {ResultSet rs ->
                    dtCompare = resultSetToObject.getDynamicData(rs)
                }
            }

            Integer kiemtra_luong_itcare = 0
            sqlString = "{ ? = call css.kiemtra_luong_itcare(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.INTEGER,
                                     Long.parseLong(phanVungId),
                                     requestBody.vluong_id
                ]) {Integer rs ->
                    kiemtra_luong_itcare = rs
                }
            }

            List<DVITCareHIENTHITHONGTINDVCNTTResponse> data = []

            if (dt_properties.size() == 0){
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            }

            // Vòng lặp dt_properties để gán giá trị cho List DVITCareHIENTHITHONGTINDVCNTTResponse
            for (int i=0; i < dt_properties.size(); i++)
            {
                DVITCareHIENTHITHONGTINDVCNTTResponse obj = new DVITCareHIENTHITHONGTINDVCNTTResponse()
                obj.setsFieldName(dt_properties[i]["field_name"].toString())
                obj.setsCaption(dt_properties[i]["ten_hienthi"].toString())
                obj.setiAtt(Byte.parseByte(dt_properties[i]["att"].toString()))
                obj.setiDataType(Byte.parseByte(dt_properties[i]["data_type"].toString()))
                obj.setsMaxValue(dt_properties[i]["max_value"].toString())
                obj.setsMinvalue(dt_properties[i]["min_value"].toString())

                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS")
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy")
                SimpleDateFormat dateFormatter_s = new SimpleDateFormat("s")
                if (obj.sFieldName != "MA_TB") {
                    if (obj.iAtt == 2) {
                        Date date = dateParser.parse(dt_cntt[0][obj.sFieldName.toLowerCase()].toString())
                        obj.setsValue(dateFormatter.format(date))
                    } else if (obj.iAtt == 9) {
                        if (dt_cntt[0][obj.sFieldName.toLowerCase()] == null) {
                            obj.setsValue("")
                        } else {
                            Date date = dateParser.parse(dt_cntt[0][obj.sFieldName.toLowerCase()].toString())
                            obj.setsValue(dateFormatter_s.format(date))
                        }
                    } else {
                        obj.setsValue(dt_cntt[0][obj.sFieldName.toLowerCase()].toString())
                    }
                }
                else {
                    obj.setsValue("MA_TB")
                }

                obj.setnFieldLength((dt_properties[i]["field_length"] == null || dt_properties[i]["field_length"].toString() =="") ? 0 : Integer.parseInt(dt_properties[i]["field_length"].toString()))
                obj.setbIsNotNull(!(dt_properties[i]["field_nullable"] == null || dt_properties[i]["field_nullable"].toString() == ""
                        || dt_properties[i]["field_nullable"].toString() != "0"))
                obj.setbIsUnique(!(dt_properties[i]["field_unique"] == null || dt_properties[i]["field_unique"].toString() == ""
                        || dt_properties[i]["field_unique"].toString() != "0"))

                obj.setsLookUpTableName(dt_properties[i]["parent_table"].toString())
                obj.setsLookUpKeyField(dt_properties[i]["parent_keyfield"].toString())
                obj.setsLookUpValueField(dt_properties[i]["parent_descfield"].toString())
                obj.setStrDepandField(dt_properties[i]["depend_field"].toString())

                if (Byte.parseByte(dt_properties[i]["att"].toString()) != 4)
                {
                    obj.setStrSql(dt_properties[i]["lookup_sql"].toString())
                }
                else
                {
                    if (dt_properties[i]["lookup_sql"].toString() != "" && dt_properties[i]["lookup_sql"].toString() != null && dt_properties[i]["lookup_sql"].toString() != "null")
                    {
                        obj.setStrSql(dt_properties[i]["lookup_sql"].toString())
                    }
                    else
                    {
                        obj.StrSql = ReturnSqlStr(data, obj, requestBody.vloaitb_id);
                    }
                }


                if (Byte.parseByte(dt_properties[i]["att"].toString()) == 4)
                {
                    List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql)
                    if (Integer.parseInt(((obj.sValue == "" || obj.sValue == "null") ? "0" : obj.sValue)) > 0)
                    {
                        String _sValue = Integer.parseInt((obj.sValue == "" || obj.sValue == "null") ? "0" : obj.sValue)
                        List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, _sValue.toString())
                        if (dv.size() > 0)
                        {
                            obj.cbb_value = obj.sValue;
                            obj.cbb_display = dt[0].cbb_display
                        }

                    }
                    else
                    {
                        obj.setCbb_value(obj.sValue)
                        obj.setCbb_display("")
                    }
                }

                if (Byte.parseByte(dt_properties[i]["att"].toString()) == 10)
                {
                    List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql)
                    if (obj.sValue.toString().length() > 0)
                    {
                        List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                        if (dv.size() > 0)
                        {
                            dt = dv
                            if (dt.size() > 0)
                            {
                                String strName = ""
                                for (COMBOBOX_GIATRI_V2 dr : dt)
                                {
                                    strName += dr.getCbb_display() + ",";
                                }
                                if (strName.length() > 0)
                                {
                                    obj.cbb_value = obj.sValue;
                                    obj.cbb_display = strName.substring(0, strName.length() - 1)
                                }
                            }

                        }
                    }
                    else
                    {
                        obj.setCbb_value(obj.sValue)
                        obj.setCbb_display("")
                    }
                }

                obj.setShowGT(false)

                if (kiemtra_luong_itcare > 0)
                {
                    obj.setbIsEnable(CheckEnableField(dtCompare, obj.sFieldName))
                }
                else
                {
                    obj.setbIsEnable(false)
                }
                data.add(obj)
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [hienthi_thongtin_dvcntt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    // 3.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.1. Load thông tin
    @Override
    ResponseEntity<BaseResponse> lay_ds_thuoctinh_sub_v2(DVITCareLayDSThuocTinhSubV2Request requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)

            List dsTables = []
            String sqlString = "{ call css.lay_thongtin_bs_hdcntt_itcare(?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    dsTables = resultSetToObject.getDynamicData(rs)
                }
            }
            JSONArray dtNew = new JSONArray()
            JSONArray dt_Source = new JSONArray()
            List temp_dt_Source = []
            int colNum = 0
            ResultSetMetaData rsmd
            //JSONArray json_dt_Source = new JSONArray()
            sqlString = "{ call CSS.pkg_common.lay_ds_hdtb_cntt_sub(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT_SUB",
                                     requestBody.vFieldNames,
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    temp_dt_Source = resultSetToObject.getDynamicData(rs)
                    rsmd = rs.getMetaData()
                    colNum = rsmd.getColumnCount()

                }
            }
            // Tạo bảng chứa Properties của DataSource
            List<String> list_properties = []
            if (temp_dt_Source.size() > 0)
            {
                for (int i = 0; i < colNum; i++) {
                    list_properties.add(temp_dt_Source[0].collect()[i].key)
                }
            }
            dt_Source = new JSONArray(temp_dt_Source)
            String[] __INVISIBLE_COLUMN = [ "HDTB_ID", "FIELD_ID_CHA", "HDTB_SUB_ID" ]

            // 21/02/2023: Chỗ này chỉ là check để add Column thì nếu chuyển Json thì không cần
            // Chỗ này cần chú ý Debug xem đúng ý đồ không

//            ResultSetMetaData rsmd = dt_Source.getMetaClass()
//            for (int i = 0; i < colNum; i++) {
//                if (!__INVISIBLE_COLUMN.contains(rsmd.getColumnName(i)))
//                {
//                    List row = GetComboBoxWithCondition_cntt_sub(dsTables, rsmd.getColumnName(i))
//                    if (row != null && row.size() > 0) {
//                        // Chỗ này làm sao để Add được 1 Properties cho Object????
//                        // dt_Source.Columns.Add(dt_Source.Columns[i].ColumnName + "_Val", typeof(string));
//                    }
//                }
//            }

            // Chỗ này tính toán chỉ để add Column, Json thì không cần
            for (int j = 0; j < dt_Source.size(); j++) {
                JSONObject dr = dt_Source.getJSONObject(j)
                for (int i = 0; i < colNum; i++) {
                    if (!__INVISIBLE_COLUMN.contains(list_properties[i].toUpperCase()))
                    {
                        List row = GetComboBoxWithCondition_cntt_sub(dsTables, list_properties[i])
                        if (row != null && row.size() > 0)
                        {
                            List captionrow = GetComboBoxWithCondition_cntt_sub(dt_Source.toList(), dr[list_properties[i]] as String)
                            if (captionrow != null && captionrow.size() > 0) {
                                // Chỗ này làm sao để Add được 1 Properties cho Object????
                                dr.put(list_properties[i] + "_Val", captionrow[0][1])
                            }
                        }
                    }
                }
            }

            List dt_properties = []
            sqlString = "{ call CSS.pkg_common.Lay_danhsach_thuoctinh_sub(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT_SUB",
                                     requestBody.vFieldNames,
                                     "00000",
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    dt_properties = resultSetToObject.getDynamicData(rs)
                }
            }

            if (dt_Source.size() == 0)
            {
                if (dt_properties.size() > 0)
                {
                    // Chỗ này add Column ko cần
                    // dtNew.Columns.Add("thuoctinh_sub", typeof(string));

                    List<DVITCareHIENTHITHONGTINDVCNTTResponse> ItemList = []
                    for (int i = 0; i < dt_properties.size(); i++)
                    {
                        Object row = dt_properties[i];
                        DVITCareHIENTHITHONGTINDVCNTTResponse obj = new DVITCareHIENTHITHONGTINDVCNTTResponse()
                        obj.sFieldName = row["field_name"] != null ? row["field_name"].toString() : null
                        obj.sCaption = row["ten_hienthi"] != null ? row["ten_hienthi"].toString() : null
                        obj.iAtt = Byte.parseByte(row["att"].toString())
                        obj.iDataType = Byte.parseByte(row["data_type"].toString())
                        obj.sMaxValue = row["max_value"] != null ? row["max_value"].toString() : null
                        obj.sMinvalue = row["min_value"] != null ? row["min_value"].toString() : null
                        obj.sValue = row["field_value"] != null ? row["field_value"].toString() : null
                        obj.nFieldLength = ((row["field_nullable"] == null || row["field_nullable"].toString() == "")
                                ? 0 : Integer.parseInt(row["field_nullable"].toString()))
                        obj.bIsNotNull = !(row["field_nullable"] == null || row["field_nullable"].toString() == "" || row["field_nullable"].toString() != "0")


                        obj.bIsUnique = !(row["field_unique"] == null || row["field_unique"].toString() == "" || row["field_unique"].toString() == "0")
                        obj.sLookUpTableName = row["parent_table"] != null ? row["parent_table"].toString() : null
                        obj.sLookUpKeyField = row["parent_keyfield"] != null ? row["parent_keyfield"].toString() : null
                        obj.sLookUpValueField = row["parent_descfield"] != null ? row["parent_descfield"].toString() : null
                        obj.strDepandField = row["depend_field"] != null ? row["depend_field"].toString() : null
                        //ItemList[i].StrSql = row["lookup_sql"].toString();
                        if (Byte.parseByte(row["att"].toString()) != 4)
                        {
                            obj.StrSql = row["lookup_sql"] != null ? row["lookup_sql"].toString() : null
                        }
                        else
                        {
                            if (row["lookup_sql"] != null && row["lookup_sql"].toString() != "")
                            {
                                obj.StrSql = row["lookup_sql"].toString()
                            }
                            else
                            {
                                obj.StrSql = ReturnSqlStr(ItemList, obj, requestBody.vloaitb_id)
                            }
                        }
                        if (Byte.parseByte(row["att"].toString()) == 4)
                        {
                            List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql)
                            if (Integer.parseInt((obj.sValue == "" ? "0" : obj.sValue)) > 0)
                            {
                                List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                                if (dv.size() > 0)
                                {
                                    obj.cbb_value = obj.sValue;
                                    obj.cbb_display = dt[0].cbb_display
                                }
                            }
                            else
                            {
                                obj.cbb_value = obj.sValue;
                                obj.cbb_display = "";
                            }

                        }
                        if (Byte.parseByte(row["att"].toString()) == 10)
                        {
                            List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql.replace("{?DB2}", "CSS"))
                            if (obj.sValue.toString().length() > 0)
                            {
                                List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                                if (dv.size() > 0)
                                {
                                    dt = dv
                                    if (dt.size() > 0)
                                    {
                                        String strName = ""
                                        for (COMBOBOX_GIATRI_V2 dr1 : dt)
                                        {
                                            strName += dr1["cbb_display"].toString() + ","
                                        }
                                        if (strName.length() > 0)
                                        {
                                            obj.cbb_value = obj.sValue;
                                            obj.cbb_display = strName.substring(0, strName.length() - 1);
                                        }
                                    }

                                }
                            }
                            else
                            {
                                obj.cbb_value = obj.sValue;
                                obj.cbb_display = "";
                            }
                        }

                        obj.showGT = false;
                        obj.bIsEnable = true;
                        ItemList.add(obj)
                    }
                    // Kết thúc vòng For
                    //Chỗ này cần xem lại
                    //String sJsonItemList = JsonConvert.SerializeObject(ItemList);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
                    String sJsonItemList = ow.writeValueAsString(ItemList)
                    JSONObject obj = new JSONObject()
                    obj.append("thuoctinh_sub", ItemList)
                    dtNew.put(obj)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(dtNew.toList()))
            }
            // Chuyển Json
            // Chỗ này xem lại Add Kiểu gì
            // dt_Source.Columns.Add("thuoctinh_sub", typeof(string));
            for (int j = 0; j < dt_Source.size(); j++)
            {
                JSONObject dr = dt_Source.getJSONObject(j)

                List<DVITCareHIENTHITHONGTINDVCNTTResponse> ItemList = []
                for (int i = 0; i < dt_properties.size(); i++)
                {
                    Object row = dt_properties[i];
                    DVITCareHIENTHITHONGTINDVCNTTResponse obj = new DVITCareHIENTHITHONGTINDVCNTTResponse()
                    obj.sFieldName = row["field_name"] != null ? row["field_name"].toString() : null
                    obj.sCaption = row["ten_hienthi"] != null ? row["ten_hienthi"].toString() : null
                    obj.iAtt = Byte.parseByte(row["att"].toString())
                    obj.iDataType = Byte.parseByte(row["data_type"].toString())
                    obj.sMaxValue = row["max_value"] != null ? row["max_value"].toString() : null
                    obj.sMinvalue = row["min_value"] != null ? row["min_value"].toString() : null
                    obj.sValue = row["field_value"] != null ? row["field_value"].toString() : null
                    obj.nFieldLength = ((row["field_nullable"] == null || row["field_nullable"].toString() == "")
                            ? 0 : Integer.parseInt(row["field_nullable"].toString()))
                    obj.bIsNotNull = !(row["field_nullable"] == null || row["field_nullable"].toString() == "" || row["field_nullable"].toString() != "0")


                    obj.bIsUnique = !(row["field_unique"] == null || row["field_unique"].toString() == "" || row["field_unique"].toString() == "0")
                    obj.sLookUpTableName = row["parent_table"] != null ? row["parent_table"].toString() : null
                    obj.sLookUpKeyField = row["parent_keyfield"] != null ? row["parent_keyfield"].toString() : null
                    obj.sLookUpValueField = row["parent_descfield"] != null ? row["parent_descfield"].toString() : null
                    obj.strDepandField = row["depend_field"] != null ? row["depend_field"].toString() : null
                    //ItemList[i].StrSql = row["lookup_sql"].toString();
                    if (Byte.parseByte(row["att"].toString()) != 4)
                    {
                        obj.StrSql = row["lookup_sql"] != null ? row["lookup_sql"].toString() : null
                    }
                    else
                    {
                        if (row["lookup_sql"] != null && row["lookup_sql"].toString() != "")
                        {
                            obj.StrSql = row["lookup_sql"].toString()
                        }
                        else
                        {
                            obj.StrSql = ReturnSqlStr(ItemList, obj, requestBody.vloaitb_id)
                        }
                    }
                    if (Byte.parseByte(row["att"].toString()) == 4)
                    {
                        List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql)
                        if (Integer.parseInt((obj.sValue == "" ? "0" : obj.sValue)) > 0)
                        {
                            List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, Integer.parseInt(obj.sValue == "" ? "0" : obj.sValue) as String)
                            if (dv.size() > 0)
                            {
                                dt = dv
                                obj.cbb_value = obj.sValue;
                                obj.cbb_display = dt[0].cbb_display
                            }
                        }
                        else
                        {
                            obj.cbb_value = obj.sValue;
                            obj.cbb_display = "";
                        }

                    }
                    if (Byte.parseByte(row["att"].toString()) == 10)
                    {
                        List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql.replace("{?DB2}", "CSS"))
                        if (obj.sValue.toString().length() > 0)
                        {
                            List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                            if (dv.size() > 0)
                            {
                                dt = dv
                                if (dt.size() > 0)
                                {
                                    String strName = "";
                                    for (Object dr1 : dt)
                                    {
                                        strName += dr1["cbb_display"].toString() + ","
                                    }
                                    if (strName.length() > 0)
                                    {
                                        obj.cbb_value = obj.sValue;
                                        obj.cbb_display = strName.substring(0, strName.length() - 1);
                                    }
                                }

                            }
                        }
                        else
                        {
                            obj.cbb_value = obj.sValue;
                            obj.cbb_display = "";
                        }
                    }
                    obj.showGT = false;
                    obj.bIsEnable = true;
                    ItemList.add(obj)
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
                String sJsonItemList = ow.writeValueAsString(ItemList)
                dr.append("thuoctinh_sub", ItemList)
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(dt_Source.toList()))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_ds_thuoctinh_sub_v2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 3.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.2. Thêm/Sửa thông tin
    ResponseEntity<BaseResponse> ghilai_thuoctinh_sub(DVITCareGhilaiThuoctinhSUBRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String user_name = session_.get(USERNAME)
            String may_cn = session_.get(DEVICE_ID)

            int vthaotac = 0;
            if (requestBody.kieu == 0) vthaotac = 1;
            else if (requestBody.kieu == 1) vthaotac = 2;


            String KIEM_TRA_UPDATE_HDTB_CNTT_SUB = ""
            String sqlString = "{ ? = call CSS.KIEM_TRA_UPDATE_HDTB_CNTT_SUB(?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     vthaotac,
                                     requestBody.vloai,
                                     requestBody.vid,
                                     requestBody.vappend_name
                ]) {
                    String rs ->
                        KIEM_TRA_UPDATE_HDTB_CNTT_SUB = rs
                }
            }
            // Nếu khác OK thì Return luôn
            if (KIEM_TRA_UPDATE_HDTB_CNTT_SUB != "OK")
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(KIEM_TRA_UPDATE_HDTB_CNTT_SUB))
            }

            // CheckDataError_Sub (xem file api)
            String strCheckInput = CheckDataError_Sub(requestBody.itemList, requestBody.itemList.size())
            if (strCheckInput != "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            List dsTables = []
            sqlString = "{ call css.lay_thongtin_bs_hdcntt_itcare(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     CURSOR_PARAMETER
                ]) {
                    ResultSet rs ->
                        dsTables = resultSetToObject.getDynamicData(rs)
                }
            }

            JSONArray ds_sub = new JSONArray()
            List temp_ds_sub = []
            int colNum = 0
            sqlString = "{ call css.pkg_common.lay_ds_hdtb_cntt_sub(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vid,
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT_SUB",
                                     requestBody.vappend_name,
                                     CURSOR_PARAMETER
                ]) {
                    ResultSet rs ->
                        temp_ds_sub = resultSetToObject.getDynamicData(rs)
                        ResultSetMetaData rsmd = rs.getMetaData()
                        colNum = rsmd.getColumnCount()
                }
            }
            ds_sub = new JSONArray(temp_ds_sub)
            // Tạo bảng chứa Properties của DataSource
            List<String> list_properties = []
            if (temp_ds_sub.size() > 0)
            {
                for (int i = 0; i < colNum; i++) {
                    list_properties.add(temp_ds_sub[0].collect()[i].key)
                }
            }
            String[] __INVISIBLE_COLUMN = [ "HDTB_ID", "FIELD_ID_CHA", "HDTB_SUB_ID" ]
            // Đoạn này làm chỉ để Add thêm Column, Dùng Json để xử lý
//            for (int i = 0; i < colNum; i++)
//                if (!__INVISIBLE_COLUMN.Contains(viewDanhSach.Columns[i].ColumnName))
//                {
//
//                    DataRow row = dsTables.Tables[0].Select("PARENT_KEYFIELD = '" + viewDanhSach.Columns[i].ColumnName + '\'').FirstOrDefault();
//                    if (row != null)
//                    {
//                        viewDanhSach.Columns.Add(viewDanhSach.Columns[i].ColumnName + "_Val", typeof(string));
//                    }
//                }

            for (int j = 0; j < ds_sub.size(); j++)
            {
                JSONObject dr = ds_sub.getJSONObject(j)
                for (int i = 0; i < colNum; i++) {
                    if (!__INVISIBLE_COLUMN.contains(list_properties[i].toUpperCase()))
                    {
                        List row = GetComboBoxWithCondition_cntt_sub(dsTables, list_properties[i])
                        if (row != null && row.size() > 0) {
                            List captionrow = GetComboBoxWithCondition_cntt_sub(ds_sub.toList(), dr[list_properties[i]] as String)
                            if (captionrow != null && captionrow.size() > 0)
                            {
                                dr.put(list_properties[i] + "_Val", captionrow[0][1])
                            }
                        }
                    }
                }
            }
            Boolean kt_sl = false
            for (int i = 0; i < requestBody.itemList.size(); i++)
            {
                if (requestBody.itemList[i].sFieldName.toUpperCase() == "SL_TU")
                    kt_sl = true
            }

            Double sl_tu = 0
            Double sl_den = 0
            Double dongia = 0
            Double gia_trongoi = 0
            Double gia_vnmobile = 0
            Double gia_vina = 0
            Double gia_viettel = 0
            Double gia_mobi = 0
            Double gia_gtel = 0

            Double gia_trongnuoc = 0
            Double gia_quocte = 0
            Double gia_luutru = 0

            // Kiểm tra số lượng trước khi insert
            if (kt_sl && ds_sub.size() > 0)
            {
                for (int index = 0; index < requestBody.itemList.size(); ++index)
                {
                    if (requestBody.itemList[index].sFieldName == "SL_TU")
                    {
                        sl_tu = Double.parseDouble(requestBody.itemList[index].sValue)
                    }
                    if (requestBody.itemList[index].sFieldName == "SL_DEN"){
                        sl_den = Double.parseDouble(requestBody.itemList[index].sValue)
                    }
                    if (requestBody.itemList[index].sFieldName == "DONGIA")
                        dongia = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_TRONGOI")
                        gia_trongoi = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_VNMOBILE")
                        gia_vnmobile = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_VINA")
                        gia_vina = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_VIETTEL")
                        gia_viettel = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_MOBI")
                        gia_mobi = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_GTEL")
                        gia_gtel = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_DOWNLOAD_TN")
                        gia_trongnuoc = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_DOWNLOAD_QT")
                        gia_quocte = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName == "GIA_LUUTRU")
                        gia_luutru = Double.parseDouble(requestBody.itemList[index].sValue)
                }

                if (dongia != 0 && gia_trongoi != 0)
                {
                    strCheckInput = "Chỉ được nhập 1 trong 2 loại Đơn giá hoặc Giá trọn gói !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                if (gia_trongoi != 0 && (gia_vnmobile != 0 || gia_vina != 0 || gia_viettel != 0 || gia_mobi != 0 || gia_gtel != 0))
                {
                    strCheckInput = "Chỉ được nhập 1 trong 2 loại Giá trọn gói hoặc đơn giá từng nhà mạng !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                if (gia_trongoi != 0 && (gia_trongnuoc != 0 || gia_quocte != 0 || gia_luutru != 0))
                {
                    strCheckInput = "Chỉ được nhập 1 trong 2 loại Giá trọn gói hoặc đơn giá thành phần !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                if (requestBody.vloaitb_id == 112) //  LoaiHinhTB.CDN
                {
                    if (gia_trongnuoc != 0 && (gia_trongoi != 0 || gia_quocte != 0 || gia_luutru != 0))
                    {
                        strCheckInput = "Giá trong nước khác 0. Các Đơn giá còn lại phải = 0 !"
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    if (gia_quocte != 0 && (gia_trongoi != 0 || gia_trongnuoc != 0 || gia_luutru != 0))
                    {
                        strCheckInput = "Giá quốc tế khác 0. Các Đơn giá còn lại phải = 0 !"
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    if (gia_luutru != 0 && (gia_trongoi != 0 || gia_trongnuoc != 0 || gia_quocte != 0))
                    {
                        strCheckInput = "Giá lưu trữ khác 0. Các Đơn giá còn lại phải = 0 !"
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                }

                if (sl_tu >= sl_den)
                {
                    strCheckInput = "Số lượng từ không được lớn hơn số lượng đến !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

                Double _sl_tu = 0
                Double _sl_den = 0

                if (vthaotac == 1) // Nhập mới
                {
                    if (requestBody.vloaitb_id == 112) // LoaiHinhTB.CDN
                    {
                        for (int i = 0; i < ds_sub.size(); i++)
                        {
                            _sl_tu = Double.parseDouble(ds_sub[i]["SL_TU"].toString());
                            _sl_den = Double.parseDouble(ds_sub[i]["SL_DEN"].toString());

                            if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                    || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                    || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                            {
                                strCheckInput = "Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !"
                                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                            }
                        }
                    }
                    else if (requestBody.vloaitb_id == 39) // LoaiHinhTB.VNPT_COLOCATION
                    {
                        // ko kiểm tra gì hết
                    }
                    else
                    {
                        for (int i = 0; i < ds_sub.size(); i++)
                        {
                            _sl_tu = Double.parseDouble(ds_sub[i]["SL_TU"].toString());
                            _sl_den = Double.parseDouble(ds_sub[i]["SL_DEN"].toString());

                            if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                    || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                    || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                            {
                                strCheckInput = "Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !"
                                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                            }
                        }
                    }
                }
                else
                {
                    if (requestBody.vloaitb_id != 112 && requestBody.vloaitb_id != 39)  // LoaiHinhTB.CDN và LoaiHinhTB.VNPT_COLOCATION
                    {
                        for (int i = 0; i < ds_sub.size(); i++)
                        {
                            if (requestBody.vhdtb_sub_id != Long.parseLong(ds_sub[i]["HDTB_SUB_ID"].toString()))
                            {
                                _sl_tu = Double.parseDouble(ds_sub[i]["SL_TU"].toString());
                                _sl_den = Double.parseDouble(ds_sub[i]["SL_DEN"].toString());

                                if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                        || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                        || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                                {
                                    strCheckInput = "Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !"
                                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                                }
                            }
                        }
                    }
                }
            }

            //var dataAcess = new DataAcess();
            if (vthaotac == 1)//Thêm mới
            {
                Map Strsql = generateInsert_Sub(dataSrc, phanVungId, requestBody.vid, requestBody.itemList, requestBody.vappend_name)
                if (requestBody.itemList.size() > 0)
                {
                    //dataAcess.exeSQL(Strsql);
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.query(Strsql["str"], Strsql["params"]) {
                        }
                    }
                }

            }
            else if (vthaotac == 2)//Cập nhật thông tin
            {
                Map rStrsql = generateUpdate_Sub(requestBody.vhdtb_sub_id, requestBody.itemList, phanVungId)
                String Strsql = rStrsql["sql"]
                if (requestBody.itemList.size() > 0) {
                    //dataAcess.exeSQL(Strsql);
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.query(Strsql, rStrsql["params"]) {
                        }
                    }
                }

            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))


        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [ghilai_thuoctinh_sub] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    private Map generateInsert_Sub(String dataSrc, String phanVungId, long hdtb_id, List<DVITCareHIENTHITHONGTINDVCNTTResponse> ItemList, String vappend_name)
    {
        List params = []
        long vhdtb_sub_id = 0
        String sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "HDTB_CNTT_SUB", 1, 10]) { Integer rs ->
                vhdtb_sub_id = rs
            }
        }
        String str = ""
        str = "insert into " + "CSS.HDTB_CNTT_SUB("
        String field = ""
        String values = ""
        for (int i = 0; i < ItemList.size(); i++)
        {
            field += ItemList[i].sFieldName + " ,"
            if (ItemList[i].iDataType == KIEU_DL().get("CST_DATE")) {
                values += "to_date(substr(?, 0, 10), 'dd/MM/yyyy'), "
            }
            else {
                values += "?, "
            }
            params.add(ItemList[i].sValue)
        }
        field = "hdtb_id, field_id_cha, hdtb_sub_id, phanvung_id, " + field
        field = field.substring(0, field.length() - 2)
        values = hdtb_id + ", '" + vappend_name + "'," + vhdtb_sub_id + "," + Long.parseLong(phanVungId) + "," + values
        values = values.substring(0, values.length() - 2)
        str += field + ") VALUES (" + values + ")"
        return ["str": str, "params": params]
    }

    private Map generateUpdate_Sub(long vhdtb_sub_id, List<DVITCareHIENTHITHONGTINDVCNTTResponse> ItemList, String phanvung_id)
    {
        Map result = [:]
        List params = []
        String str = ""
        str = "update CSS.HDTB_CNTT_SUB SET "
        List validFieldNames = ["HDTB_ID",
                                "FIELD_ID_CHA",
                                "GIA_VINA",
                                "GIA_MOBI",
                                "GIA_VIETTEL",
                                "GIA_VNMOBILE",
                                "GIA_GTEL",
                                "GIA_TRONGOI",
                                "SL_TU",
                                "SL_DEN",
                                "HDTB_SUB_ID",
                                "DOITAC_ID",
                                "CUOC_DOITAC",
                                "LOAIDT_ID",
                                "DONGIA",
                                "GIA_I_TELECOM",
                                "GIA_DOWNLOAD_TN",
                                "GIA_DOWNLOAD_QT",
                                "GIA_LUUTRU",
                                "LUULUONG_ID",
                                "DONGIA_LL",
                                "CHECK_NGAY",
                                "NGAY_BD",
                                "NGAY_KT",
                                "SO_DICH",
                                "DIACHI_LD",
                                "TG_CHUYENDOI",
                                "VUNGDT_ID",
                                "MA_TB",
                                "TEN_TB",
                                "LOAIHINH_TB",
                                "THOIGIAN_KT",
                                "THOIGIAN_BD",
                                "HDTB_CON_ID",
                                "THUEBAO_CON_ID",
                                "TINH_ID",
                                "GIA_REDDI",
                                "CPU_SUB",
                                "GIA_MMS_GTEL",
                                "GIA_MMS_ITELECOM",
                                "GIA_MMS_MOBILE",
                                "GIA_MMS_VIETTEL",
                                "GIA_MMS_VINAPHONE",
                                "GIA_MMS_VNMOBILE",
                                "HDD_SUB",
                                "NGUOI_SD_SUB",
                                "OS_SUB",
                                "RAM_SUB",
                                "SCREEN_SUB"]
        for (int i = 0; i < ItemList.size(); i++)
        {
            if (validFieldNames.contains(ItemList[i].sFieldName.toUpperCase())) {
                if (ItemList[i].iDataType == KIEU_DL().get("CST_DATE")) {
                    str += ItemList[i].sFieldName + " = to_date(substr(?, 0, 10), 'dd/MM/yyyy') ,"
                }
                else {
                    str += ItemList[i].sFieldName + " = ? ,"
                }
                params.add(ItemList[i].sValue)
            }
        }
        str = str.substring(0, str.length() - 1)
        str += " where phanvung_id = ? and hdtb_sub_id = ? "
        params.add(phanvung_id)
        params.add(vhdtb_sub_id)
        result["sql"] = str
        result["params"] = params
        return result
    }

    private Map KIEU_DL()
    {
        Map<String, Integer> map = new HashMap<String, Integer>()
        map.put("CST_TEXT", 0)
        map.put("CST_NUMBER", 1)
        map.put("CST_DATE", 2)
        return map
    }

    private String CheckDataError_Sub(List<DVITCareHIENTHITHONGTINDVCNTTResponse> NetItems, int ItemCount)
    {
        try
        {
            String flag = "OK"

            int i = 0
            double  TempNum = 0
            for (int k = 0; k < ItemCount; k++)
            {
                switch (NetItems[k].iDataType)
                {
                    case KIEU_DL().get("CST_TEXT"):
                        if (NetItems[k].sValue == null || NetItems[k].sValue.toString() == "")
                        {
                            return "Phải nhập " + NetItems[k].sCaption + "."
                        }

                        if (NetItems[k].bIsNotNull && NetItems[k].sValue == "")
                        {
                            return "Phải nhập " + NetItems[k].sCaption + ".";
                        }
                        i = NetItems[k].sValue.length();
                        if (NetItems[k].bIsNotNull)
                        {
                            if (i == 0)
                            {
                                return "Phải nhập " + NetItems[k].sCaption + ".";
                            }
                        }
                        if (!((NetItems[k].sMaxValue == null || NetItems[k].sMaxValue.toString() == "")
                                && (NetItems[k].sMinvalue == null || NetItems[k].sMinvalue.toString() == ""))
                        )
                        {
                            if (i > Integer.parseInt(NetItems[k].sMaxValue) || i < Integer.parseInt(NetItems[k].sMinvalue))
                            {
                                if (Integer.parseInt(NetItems[k].sMaxValue) > 0 && Integer.parseInt(NetItems[k].sMinvalue) > 0)
                                    if (Integer.parseInt(NetItems[k].sMaxValue) > 0)
                                    {
                                        return NetItems[k].sCaption + " không thể lớn hơn " + NetItems[k].sMaxValue + " ký tự.";
                                    }
                                    else
                                    {
                                        return "Phải nhập " + NetItems[k].sCaption + ".";
                                    }
                            }
                        }
                        break;
                    case KIEU_DL().get("CST_NUMBER"):
                        if (NetItems[k].sValue == null || NetItems[k].sValue.toString() == "")
                        {
                            return "Phải nhập " + NetItems[k].sCaption + "."
                        }
                        if (NetItems[k].sValue.length() > 0 && !CheckIsNumber(NetItems[k].sValue))
                        {
                            return NetItems[k].sCaption + " phải là kiểu số.";
                        }
                        TempNum = Double.parseDouble((NetItems[k].sValue == null || NetItems[k].sValue.toString() == "") ? "-1" : NetItems[k].sValue);

                        if (NetItems[k].bIsNotNull && ((NetItems[k].sValue == null || NetItems[k].sValue.toString() == "")))
                        {
                            return "Phải nhập " + NetItems[k].sCaption + ".";
                        }
                        i = NetItems[k].sValue.length();
                        if (!(((NetItems[k].sMaxValue == null || NetItems[k].sMaxValue.toString() == "")
                                && (NetItems[k].sMinvalue == null || NetItems[k].sMinvalue.toString() == ""))))
                        {
                            if (i > Integer.parseInt(NetItems[k].sMaxValue) || i > Integer.parseInt(NetItems[k].sMinvalue))
                            {
                                if (Integer.parseInt(NetItems[k].sMaxValue) > 0 && Integer.parseInt(NetItems[k].sMinvalue) > 0)
                                    if (Integer.parseInt(NetItems[k].sMaxValue) > 0)
                                    {
                                        return NetItems[k].sCaption + " không thể lớn hơn " + NetItems[k].sMaxValue + " ký tự.";
                                    }
                            }
                        }
                        if (Double.parseDouble(NetItems[k].sValue) < 0)
                        {
                            return NetItems[k].sCaption + " không được nhỏ hơn 0."
                        }
                        break
                    case KIEU_DL().get("CST_DATE"):
                        if (NetItems[k].sValue == null || NetItems[k].sValue.toString() == "")
                        {
                            return NetItems[k].sCaption + " phải có giá trị."
                        }
                        break;
                    default:
                        flag = "OK"
                        break;
                }
                //if (flag == "OK") return flag;
            }
            return "OK"
        }
        catch (Exception ex)
        {
            return "Có lỗi: " + ex;
        }
    }

    private static Boolean CheckIsNumber(String str)
    {
        try{
            double Num;
            Num = Double.parseDouble(str);
            return true
        }
        catch (Exception )
        {
            return false
        }
    }

    private Boolean CheckEnableField(List dtCompare, String FieldName)
    {
        Boolean check = false

        for (String s : dtCompare)
        {
            if (s == FieldName)
            {
                check = true
                break
            }
        }

        return check;
    }

    //rebuild
    private List<COMBOBOX_GIATRI_V2> LAY_COMBOBOX_GIATRI_V2(String dataSrc, String vcaulenh)
    {
        try
        {
            if(vcaulenh.contains("url")){
                def res = new JsonSlurper().parseText(vcaulenh)
                List<COMBOBOX_GIATRI_V2> data = []
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.query(res["str"], res["params"]) { ResultSet rs ->
                        while (rs != null && rs.next()) {
                            data.add(new COMBOBOX_GIATRI_V2(
                                    cbb_value: rs.getString(1),
                                    cbb_display: rs.getString(2)))
                        }
                    }
                }
            }
            List<COMBOBOX_GIATRI_V2> data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(vcaulenh, []) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        data.add(new COMBOBOX_GIATRI_V2(
                                cbb_value: rs.getString(1),
                                cbb_display: rs.getString(2)))
                    }
                }
            }
            return data;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private List<COMBOBOX_GIATRI_V2> GetComboBoxWithCondition(List<COMBOBOX_GIATRI_V2> data, String condition)
    {
        try
        {
            List<COMBOBOX_GIATRI_V2> dataReturn = []
            String[] _array_condition = condition.split(",")
            for (COMBOBOX_GIATRI_V2 obj : data)
            {
                if (obj.cbb_value in _array_condition)
                {
                    dataReturn.add(obj)
                }
            }
            return dataReturn;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private List GetComboBoxWithCondition_cntt_sub(List data, String condition)
    {
        try
        {
            List dataReturn = []
            for (Object obj : data)
            {
                if (obj.parent_keyfield == condition)
                {
                    dataReturn.add(obj)
                }
            }
            return dataReturn;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private String ReturnSqlStr(List<DVITCareHIENTHITHONGTINDVCNTTResponse> ListItem, DVITCareHIENTHITHONGTINDVCNTTResponse obj, int vloaitb_id)
    {
        List<?> params = new ArrayList<>()
        String DB2 = "CSS";
        String DB1 = "ADMIN";
        String DB5 = "CABMAN";
        String s = "";
        switch (obj.sLookUpTableName)
        {
            case "PHANLOAI_DV":
                s = "select ?," + "? from " + DB2 + ".? WHERE LOAITB_ID = " + vloaitb_id + " ORDER BY " + obj.sLookUpKeyField;
                params.add(obj.sLookUpKeyField)
                params.add(obj.sLookUpKeyField)
                params.add(obj.sLookUpTableName)
                break;
            case "TOCDO_ADSL":
                s = "Select TOCDO_ID, THUONGHIEU FROM " + DB2 + ".?";
                params.add(obj.sLookUpTableName)
                s += " WHERE SUDUNG = 1 and LOAITB_ID = " + vloaitb_id;
                if (vloaitb_id == 12 || vloaitb_id == 116
                        || vloaitb_id == 117 || vloaitb_id == 132 || vloaitb_id == 35)
                    for (DVITCareHIENTHITHONGTINDVCNTTResponse type: ListItem)
                    {
                        if (type.sFieldName == "DAILY")
                            s += " and kieu_mega in (2," + Integer.parseInt((type.sValue == null || type.sValue.trim().length()) ? "0" : type.sValue).toString() + ")";
                    }

                if (vloaitb_id == 39 || vloaitb_id == 139 || vloaitb_id == 78)
                    for (DVITCareHIENTHITHONGTINDVCNTTResponse type : ListItem)
                    {
                        if (type.sFieldName == "PHANLOAI_ID")
                            s += " and kieu_mega = " + Integer.parseInt(type.sValue).toString()
                    }

                s += " ORDER BY TOCDO_ID";

                break;
            case "HE_DIEU_HANH":
                for (DVITCareHIENTHITHONGTINDVCNTTResponse type : ListItem)
                {
                    if (type.sFieldName == "TOCDO_ID")
                    {
                        if (type.sValue == "")
                        {
                            s = "select ?,? from " + DB2 + ".? ORDER BY " + obj.sLookUpKeyField;
                            params.add(obj.sLookUpKeyField)
                            params.add(obj.sLookUpValueField)
                            params.add(obj.sLookUpTableName)
                        }
                        else
                        {
                            s = "select a.hedieuhanh_id, a.he_dieu_hanh  ";
                            s += " from " + DB2 + ".he_dieu_hanh a, " + DB2 + ".hdh_tocdo b ";
                            s += " where a.hedieuhanh_id = b.hedieuhanh_id ";
                            s += " and b.tocdo_id = " + Integer.parseInt(type.sValue);
                            s += " order by a.hedieuhanh_id ";
                        }
                    }
                }
                break;
            case "ABC":
                s = "Select vungpv_id, tenvung_pv FROM " + DB5 + ".vungphucvu WHERE trangthai='1' order by tenvung_pv";
                break;
            case "DUNGLUONG":
                s = "Select DUNGLUONG AS MA_SO,DUNGLUONG AS TEN from " + DB5 + ".DUNGLUONG order by to_number(ten)";
                break;
            case "TUYEN_CAP":
                s = "Select tuyencap_id, tuyen_cap from " + DB5 + ".?";
                params.add(obj.sLookUpTableName)
                break;
            case "TUYEN_CB":
                s = "Select tuyencb_id, tuyen_cb from " + DB5 + ".?";
                params.add(obj.sLookUpTableName)
                break;
            case "TUYEN_COT":
                s = "Select tuyencot_id, tuyen_cot from " + DB5 + ".?"
                params.add(obj.sLookUpTableName)
                break;
                /*case "{?CSS}.LINHVUC":
                    s = "Select linhvuc_id, linhvuc from " + DatabaseConstants.DB2 + ".LINHVUC where phanloai_sms is null or phanloai_sms like '%" + PhanLoai_ID + "%'";
                    break;*/
            default:
                s = "select ?, ? from ?" ;
                params.add(obj.sLookUpKeyField)
                params.add(obj.sLookUpValueField)
                params.add(obj.sLookUpTableName)
                break;
        }
        s = s.replace("{?ADMIN}", DB1);
        s = s.replace("{?CSS}", DB2);
        def excutes = ["str":s, "params": params]
        return new Gson().toJson(excutes)
    }

    @Override
    ResponseEntity<BaseResponse> xoa_hdtb_cntt_sub(DVITCareXoaHdtbCnttSubRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.xoa_hdtb_cntt_sub(?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdtb_sub_id
                ]) {}
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [xoa_hdtb_cntt_sub] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> huy_giao_phieu_phoihop(DVITCareHuyGiaoPhieuPhoihopRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String sqlString = "{ ? = call css.huy_giao_phieu_phoihop(?,?) }"
            String message = ""
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     requestBody.vphieu_id]) { String rs ->
                    message = rs

                }
            }
            if (message == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [huy_giao_phieu_phoihop] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> lay_tt_nhanvien_yc_khaosat(DVITCareLayTTNhanvienYCKhaosatRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.lay_tt_nhanvien_yc_khaosat(?,?,?) }"
            List data = []
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_tt_nhanvien_yc_khaosat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 4.5 Tổng hợp bảo trì 4. Xem trước mẫu file
    @Override
    ResponseEntity<BaseResponse> lay_thongtin_baotri(DVITCareLayThongtinBaotriRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call css.lay_thongtin_baotri(?,?,?,?,?,?,?) }"
            DVITCareLayThongtinBaotriTonghopResponse data = new DVITCareLayThongtinBaotriTonghopResponse();
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vgr_id,
                                     requestBody.vkieu_id,
                                     requestBody.vloai_id,
                                     requestBody.vbieumau_id,
                                     CURSOR_PARAMETER,
                                     CURSOR_PARAMETER]) { ResultSet rs_d_hd, ResultSet rs_ds_dt ->
                    data.setD_hd(resultSetToObject.getDynamicData(rs_d_hd))
                    data.setDs_dt(resultSetToObject.getDynamicData(rs_ds_dt))
                }
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_thongtin_baotri] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 4.3 Chi tiết hợp đồng CNTT 1. Load các thông tin thuộc tính
    @Override
    ResponseEntity<BaseResponse> hienthi_thongtin_dvcntt_bddv(DVITCareHiethiThongtinDVCNTTBDDVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)

            List dt_cntt = []
            String sqlString = "{ call CSS.TRACUU_HOPDONG.LAY_DS_DBTHUEBAO_CNTT_THEO_THUEBAO_ID(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.in_thuebao_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    dt_cntt = resultSetToObject.getDynamicData(rs)
                }
            }

            List dt_properties = []
            sqlString = "{ call cabman.LAY_DANHSACH_THUOCTINH_V3(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT",
                                     "00000",
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    dt_properties = resultSetToObject.getDynamicData(rs)
                }
            }

            List<DVITCareHIENTHITHONGTINDVCNTTResponse> data = []
            if (dt_properties.size() == 0)
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            }

            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS")
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy")
            SimpleDateFormat dateFormatter_s = new SimpleDateFormat("s")

            for (int i = 0; i < dt_properties.size(); i++)
            {
                Object row = dt_properties[i];
                DVITCareHIENTHITHONGTINDVCNTTResponse obj = new DVITCareHIENTHITHONGTINDVCNTTResponse()
                obj.sFieldName = row["field_name"] != null ? row["field_name"].toString() : null
                obj.sCaption = row["ten_hienthi"] != null ? row["ten_hienthi"].toString() : null
                obj.iAtt = Byte.parseByte(row["att"].toString())
                obj.iDataType = Byte.parseByte(row["data_type"].toString())
                obj.sMaxValue = row["max_value"] != null ? row["max_value"].toString() : null
                obj.sMinvalue = row["min_value"] != null ? row["min_value"].toString() : null

                if (obj.sFieldName != "MA_TB") {
                    if (obj.iAtt == 2) {
                        Date date = dateParser.parse(dt_cntt[0][obj.sFieldName.toLowerCase()].toString())
                        // obj.setsValue(dateFormatter.format(date))

                        obj.sValue = dateFormatter.format(date)
                    } else if (obj.iAtt == 9) {
                        if (dt_cntt[0][obj.sFieldName.toLowerCase()] == null) {
                            obj.sValue = ""
                        } else {
                            Date date = dateParser.parse(dt_cntt[0][obj.sFieldName.toLowerCase()].toString())
                            obj.sValue = dateFormatter_s.format(date)
                            //Convert.ToDateTime(dt_cntt.Rows[0][obj.sFieldName]).toString("s", CultureInfo.InvariantCulture);
                        }
                    } else{
                        obj.sValue = dt_cntt[0][obj.sFieldName.toLowerCase()].toString()
                    }

                }
                else {
                    obj.sValue = "MA_TB"
                }

                //ItemList[i].sValue = row["field_value"].toString();
                obj.nFieldLength = (row["field_length"] == null || row["field_length"].toString() == "") ? 0 : Integer.parseInt(row["field_length"].toString())
                obj.bIsNotNull = !(row["field_nullable"] == null || row["field_nullable"].toString() == "" || row["field_nullable"].toString() != "0")
                obj.bIsUnique = !(row["field_unique"] == null || row["field_unique"].toString() == "" || row["field_unique"].toString() == "0")
                obj.sLookUpTableName = row["parent_table"] != null ? row["parent_table"].toString() : null
                obj.sLookUpKeyField = row["parent_keyfield"] != null ? row["parent_keyfield"].toString() : null
                obj.sLookUpValueField = row["parent_descfield"] != null ? row["parent_descfield"].toString() : null
                obj.strDepandField = row["depend_field"] != null ? row["depend_field"].toString() : null
                if (Byte.parseByte(row["att"].toString()) != 4)
                {
                    obj.StrSql = row["lookup_sql"] != null ? row["lookup_sql"].toString() : null
                }
                else
                {
                    if (row["lookup_sql"] != null && row["lookup_sql"].toString() != "" && row["lookup_sql"] != "null")
                    {
                        obj.StrSql = row["lookup_sql"].toString()
                    }
                    else
                    {
                        obj.StrSql = ReturnSqlStr(data, obj, requestBody.vloaitb_id)
                    }
                }
                if (Byte.parseByte(row["att"].toString()) == 4)
                {

                    List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql);
                    if (Integer.parseInt((obj.sValue == "" || obj.sValue == "null" ? "0" : obj.sValue)) > 0)
                    {
                        List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, Integer.parseInt(obj.sValue == "" || obj.sValue == "null" ? "0" : obj.sValue) as String)
                        if (dv.size() > 0)
                        {
                            dt = dv
                            obj.cbb_value = obj.sValue
                            obj.cbb_display = dt[0].cbb_display
                        }
                    }
                    else
                    {
                        obj.cbb_value = obj.sValue;
                        obj.cbb_display = "";
                    }

                }
                if (Byte.parseByte(row["att"].toString()) == 10)
                {
                    List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql.replace("{?DB2}", "CSS"))
                    if (obj.sValue.toString().length() > 0)
                    {
                        List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                        if (dv.size() > 0)
                        {
                            dt = dv
                            if (dt.size() > 0)
                            {
                                String strName = ""
                                for (Object dr : dt)
                                {
                                    strName += dr["cbb_display"].toString() + ","
                                }
                                if (strName.length() > 0)
                                {
                                    obj.cbb_value = obj.sValue;
                                    obj.cbb_display = strName.substring(0, strName.length() - 1)
                                }
                            }

                        }
                    }
                    else
                    {
                        obj.cbb_value = obj.sValue
                        obj.cbb_display = ""
                    }
                }

                obj.showGT = false;
                obj.bIsEnable = false; // không cho phép sửa thuộc tính
                data.add(obj)
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [hienthi_thongtin_dvcntt_bddv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 4.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.1. Load thông tin
    @Override
    ResponseEntity<BaseResponse> lay_ds_thuoctinh_sub_bddv(DVITCareLayDSThuoctinhSUBBDDVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)

            List dsTables = []
            String sqlString = "{ call css.lay_thongtin_bs_hdcntt_itcare(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    dsTables = resultSetToObject.getDynamicData(rs)
                }
            }
            JSONArray dtNew = new JSONArray()
            JSONArray dt_Source = new JSONArray()
            List temp_dt_Source = []
            int colNum = 0
            sqlString = "{ call css.pkg_common.lay_ds_db_cntt_sub(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vthuebao_id,
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT_SUB",
                                     requestBody.vappend_name,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    temp_dt_Source = resultSetToObject.getDynamicData(rs)
                    ResultSetMetaData rsmd = rs.getMetaData()
                    colNum = rsmd.getColumnCount()
                }
            }

            // Tạo bảng chứa Properties của DataSource
            List<String> list_properties = []
            if (temp_dt_Source.size() > 0)
            {
                for (int i = 0; i < colNum; i++) {
                    list_properties.add(temp_dt_Source[0].collect()[i].key)
                }
            }
            dt_Source = new JSONArray(temp_dt_Source)
            String[] __INVISIBLE_COLUMN = ["THUEBAO_ID", "FIELD_ID_CHA", "THUEBAO_SUB_ID"]
            for (int j = 0; j < dt_Source.size(); j++) {
                JSONObject dr = dt_Source.getJSONObject(j)
                for (int i = 0; i < colNum; i++) {
                    if (!__INVISIBLE_COLUMN.contains(list_properties[i].toUpperCase()))
                    {
                        List row = GetComboBoxWithCondition_cntt_sub(dsTables, list_properties[i])
                        if (row != null && row.size() > 0)
                        {
                            List captionrow = GetComboBoxWithCondition_cntt_sub(dt_Source.toList(), dr[list_properties[i]] as String)
                            if (captionrow != null && captionrow.size() > 0) {
                                // Chỗ này làm sao để Add được 1 Properties cho Object????
                                dr.put(list_properties[i] + "_Val", captionrow[0][1])
                            }
                        }
                    }
                }
            }

            List dt_properties = []
            sqlString = "{ call CSS.pkg_common.Lay_danhsach_thuoctinh_sub(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT_SUB",
                                     requestBody.vappend_name,
                                     "00000",
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    dt_properties = resultSetToObject.getDynamicData(rs)
                }
            }

            if (dt_Source.size() == 0)
            {
                if (dt_properties.size() > 0)
                {
                    // Chỗ này add Column ko cần
                    // dtNew.Columns.Add("thuoctinh_sub", typeof(string));
                    List<DVITCareHIENTHITHONGTINDVCNTTResponse> ItemList = []
                    for (int i = 0; i < dt_properties.size(); i++)
                    {
                        Object row = dt_properties[i];
                        DVITCareHIENTHITHONGTINDVCNTTResponse obj = new DVITCareHIENTHITHONGTINDVCNTTResponse()
                        obj.sFieldName = row["field_name"] != null ? row["field_name"].toString() : null
                        obj.sCaption = row["ten_hienthi"] != null ? row["ten_hienthi"].toString() : null
                        obj.iAtt = Byte.parseByte(row["att"].toString())
                        obj.iDataType = Byte.parseByte(row["data_type"].toString())
                        obj.sMaxValue = row["max_value"] != null ? row["max_value"].toString() : null
                        obj.sMinvalue = row["min_value"] != null ? row["min_value"].toString() : null
                        obj.sValue = row["field_value"] != null ? row["field_value"].toString() : null
                        obj.nFieldLength = ((row["field_nullable"] == null || row["field_nullable"].toString() == "")
                                ? 0 : Integer.parseInt(row["field_nullable"].toString()))
                        obj.bIsNotNull = !(row["field_nullable"] == null || row["field_nullable"].toString() == "" || row["field_nullable"].toString() != "0")


                        obj.bIsUnique = !(row["field_unique"] == null || row["field_unique"].toString() == "" || row["field_unique"].toString() == "0")
                        obj.sLookUpTableName = row["parent_table"] != null ? row["parent_table"].toString() : null
                        obj.sLookUpKeyField = row["parent_keyfield"] != null ? row["parent_keyfield"].toString() : null
                        obj.sLookUpValueField = row["parent_descfield"] != null ? row["parent_descfield"].toString() : null
                        obj.strDepandField = row["depend_field"] != null ? row["depend_field"].toString() : null
                        //ItemList[i].StrSql = row["lookup_sql"].toString();
                        if (Byte.parseByte(row["att"].toString()) != 4)
                        {
                            obj.StrSql = row["lookup_sql"] != null ? row["lookup_sql"].toString() : null
                        }
                        else
                        {
                            if (row["lookup_sql"] != null && row["lookup_sql"].toString() != "")
                            {
                                obj.StrSql = row["lookup_sql"].toString()
                            }
                            else
                            {
                                obj.StrSql = ReturnSqlStr(ItemList, obj, requestBody.vloaitb_id)
                            }
                        }
                        if (Byte.parseByte(row["att"].toString()) == 4)
                        {
                            List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql)
                            if (Integer.parseInt((obj.sValue == "" ? "0" : obj.sValue)) > 0)
                            {
                                List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                                if (dv.size() > 0)
                                {
                                    obj.cbb_value = obj.sValue;
                                    obj.cbb_display = dt[0].cbb_display
                                }
                            }
                            else
                            {
                                obj.cbb_value = obj.sValue
                                obj.cbb_display = ""
                            }

                        }
                        if (Byte.parseByte(row["att"].toString()) == 10)
                        {
                            List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql.replace("{?DB2}", "CSS"))
                            if (obj.sValue.toString().length() > 0)
                            {
                                List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                                if (dv.size() > 0)
                                {
                                    dt = dv
                                    if (dt.size() > 0)
                                    {
                                        String strName = ""
                                        for (COMBOBOX_GIATRI_V2 dr1 : dt)
                                        {
                                            strName += dr1["cbb_display"].toString() + ","
                                        }
                                        if (strName.length() > 0)
                                        {
                                            obj.cbb_value = obj.sValue;
                                            obj.cbb_display = strName.substring(0, strName.length() - 1);
                                        }
                                    }

                                }
                            }
                            else
                            {
                                obj.cbb_value = obj.sValue
                                obj.cbb_display = ""
                            }
                        }

                        obj.showGT = false
                        obj.bIsEnable = true
                        ItemList.add(obj)
                    }
                    // Kết thúc vòng For
                    //Chỗ này cần xem lại
                    //String sJsonItemList = JsonConvert.SerializeObject(ItemList);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
                    String sJsonItemList = ow.writeValueAsString(ItemList)
                    JSONObject obj = new JSONObject()
                    obj.append("thuoctinh_sub", ItemList)
                    dtNew.put(obj)
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(dtNew.toList()))
            }

            for (int j = 0; j < dt_Source.size(); j++)
            {
                JSONObject dr = dt_Source.getJSONObject(j)

                List<DVITCareHIENTHITHONGTINDVCNTTResponse> ItemList = []
                for (int i = 0; i < dt_properties.size(); i++)
                {
                    Object row = dt_properties[i];
                    DVITCareHIENTHITHONGTINDVCNTTResponse obj = new DVITCareHIENTHITHONGTINDVCNTTResponse()
                    obj.sFieldName = row["field_name"] != null ? row["field_name"].toString() : null
                    obj.sCaption = row["ten_hienthi"] != null ? row["ten_hienthi"].toString() : null
                    obj.iAtt = Byte.parseByte(row["att"].toString())
                    obj.iDataType = Byte.parseByte(row["data_type"].toString())
                    obj.sMaxValue = row["max_value"] != null ? row["max_value"].toString() : null
                    obj.sMinvalue = row["min_value"] != null ? row["min_value"].toString() : null
                    obj.sValue = row["field_value"] != null ? row["field_value"].toString() : null
                    obj.nFieldLength = ((row["field_nullable"] == null || row["field_nullable"].toString() == "")
                            ? 0 : Integer.parseInt(row["field_nullable"].toString()))
                    obj.bIsNotNull = !(row["field_nullable"] == null || row["field_nullable"].toString() == "" || row["field_nullable"].toString() != "0")


                    obj.bIsUnique = !(row["field_unique"] == null || row["field_unique"].toString() == "" || row["field_unique"].toString() == "0")
                    obj.sLookUpTableName = row["parent_table"] != null ? row["parent_table"].toString() : null
                    obj.sLookUpKeyField = row["parent_keyfield"] != null ? row["parent_keyfield"].toString() : null
                    obj.sLookUpValueField = row["parent_descfield"] != null ? row["parent_descfield"].toString() : null
                    obj.strDepandField = row["depend_field"] != null ? row["depend_field"].toString() : null
                    //ItemList[i].StrSql = row["lookup_sql"].toString();
                    if (Byte.parseByte(row["att"].toString()) != 4)
                    {
                        obj.StrSql = row["lookup_sql"] != null ? row["lookup_sql"].toString() : null
                    }
                    else
                    {
                        if (row["lookup_sql"] != null && row["lookup_sql"].toString() != "")
                        {
                            obj.StrSql = row["lookup_sql"].toString()
                        }
                        else
                        {
                            obj.StrSql = ReturnSqlStr(ItemList, obj, requestBody.vloaitb_id)
                        }
                    }
                    if (Byte.parseByte(row["att"].toString()) == 4)
                    {
                        List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql)
                        if (Integer.parseInt((obj.sValue == "" ? "0" : obj.sValue)) > 0)
                        {
                            List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, Integer.parseInt(obj.sValue == "" ? "0" : obj.sValue) as String)
                            if (dv.size() > 0)
                            {
                                dt = dv
                                obj.cbb_value = obj.sValue;
                                obj.cbb_display = dt[0].cbb_display
                            }
                        }
                        else
                        {
                            obj.cbb_value = obj.sValue
                            obj.cbb_display = ""
                        }

                    }
                    if (Byte.parseByte(row["att"].toString()) == 10)
                    {
                        List<COMBOBOX_GIATRI_V2> dt = LAY_COMBOBOX_GIATRI_V2(dataSrc, obj.StrSql.replace("{?DB2}", "CSS"))
                        if (obj.sValue.toString().length() > 0)
                        {
                            List<COMBOBOX_GIATRI_V2> dv = GetComboBoxWithCondition(dt, obj.sValue.toString())
                            if (dv.size() > 0)
                            {
                                dt = dv
                                if (dt.size() > 0)
                                {
                                    String strName = ""
                                    for (Object dr1 : dt)
                                    {
                                        strName += dr1["cbb_display"].toString() + ","
                                    }
                                    if (strName.length() > 0)
                                    {
                                        obj.cbb_value = obj.sValue
                                        obj.cbb_display = strName.substring(0, strName.length() - 1)
                                    }
                                }

                            }
                        }
                        else
                        {
                            obj.cbb_value = obj.sValue
                            obj.cbb_display = ""
                        }
                    }
                    obj.showGT = false
                    obj.bIsEnable = true
                    ItemList.add(obj)
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
                String sJsonItemList = ow.writeValueAsString(ItemList)
                dr.append("thuoctinh_sub", ItemList)
            }


            return ResponseEntity.ok().body(BaseResponseBuilder.of(dt_Source.toList()))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [hienthi_thongtin_dvcntt_bddv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    // 4.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.2. Thêm/Sửa/Xóa thông tin
    ResponseEntity<BaseResponse> ghilai_thuoctinh_sub_bddv(DVITCareGhilaiThuoctinhSUBBDDVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String user_name = session_.get(USERNAME)
            String vnguoi_cn = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)

            int vthaotac = 0;
            if (requestBody.kieu == 0) vthaotac = 1;
            else if (requestBody.kieu == 1) vthaotac = 2;


            String KIEM_TRA_UPDATE_HDTB_CNTT_SUB = ""
            String sqlString = "{ ? = call CSS.KIEM_TRA_UPDATE_HDTB_CNTT_SUB(?,?,?,?,?) }"
            Long vid = 0
            if (requestBody.vloai == 1)
            {
                vid = requestBody.vthuebao_id
            } else if (requestBody.vloai == 2) {
                vid = requestBody.vbaohong_id
            }
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR,
                                     Long.parseLong(phanVungId),
                                     vthaotac,
                                     requestBody.vloai,
                                     vid,
                                     requestBody.vappend_name
                ]) {
                    String rs ->
                        KIEM_TRA_UPDATE_HDTB_CNTT_SUB = rs
                }
            }
            // Nếu khác OK thì Return luôn
            if (KIEM_TRA_UPDATE_HDTB_CNTT_SUB != "OK")
            {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(KIEM_TRA_UPDATE_HDTB_CNTT_SUB))
            }

            // CheckDataError_Sub (xem file api)
            String strCheckInput = CheckDataError_Sub(requestBody.itemList, requestBody.itemList.size())
            if (strCheckInput != "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            List dsTables = []
            sqlString = "{ call css.lay_thongtin_bs_hdcntt_itcare(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaitb_id,
                                     CURSOR_PARAMETER
                ]) {
                    ResultSet rs ->
                        dsTables = resultSetToObject.getDynamicData(rs)
                }
            }

            JSONArray ds_sub = new JSONArray()
            List temp_ds_sub = []
            int colNum = 0
            sqlString = "{ call css.pkg_common.lay_ds_db_cntt_sub(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vthuebao_id,
                                     requestBody.vloaitb_id,
                                     "HDTB_CNTT_SUB",
                                     requestBody.vappend_name,
                                     CURSOR_PARAMETER
                ]) {
                    ResultSet rs ->
                        temp_ds_sub = resultSetToObject.getDynamicData(rs)
                        ResultSetMetaData rsmd = rs.getMetaData()
                        colNum = rsmd.getColumnCount()
                }
            }
            ds_sub = new JSONArray(temp_ds_sub)
            // Tạo bảng chứa Properties của DataSource
            List<String> list_properties = []
            if (temp_ds_sub.size() > 0)
            {
                for (int i = 0; i < colNum; i++) {
                    list_properties.add(temp_ds_sub[0].collect()[i].key)
                }
            }
            String[] __INVISIBLE_COLUMN = [ "THUEBAO_ID", "FIELD_ID_CHA", "THUEBAO_SUB_ID" ]
            // Đoạn này làm chỉ để Add thêm Column, Dùng Json để xử lý
//            for (int i = 0; i < colNum; i++)
//                if (!__INVISIBLE_COLUMN.Contains(viewDanhSach.Columns[i].ColumnName))
//                {
//
//                    DataRow row = dsTables.Tables[0].Select("PARENT_KEYFIELD = '" + viewDanhSach.Columns[i].ColumnName + '\'').FirstOrDefault();
//                    if (row != null)
//                    {
//                        viewDanhSach.Columns.Add(viewDanhSach.Columns[i].ColumnName + "_Val", typeof(string));
//                    }
//                }

            for (int j = 0; j < ds_sub.size(); j++)
            {
                JSONObject dr = ds_sub.getJSONObject(j)
                for (int i = 0; i < colNum; i++) {
                    if (!__INVISIBLE_COLUMN.contains(list_properties[i].toUpperCase()))
                    {
                        List row = GetComboBoxWithCondition_cntt_sub(dsTables, list_properties[i])
                        if (row != null && row.size() > 0) {
                            List captionrow = GetComboBoxWithCondition_cntt_sub(ds_sub.toList(), dr[list_properties[i]] as String)
                            if (captionrow != null && captionrow.size() > 0)
                            {
                                dr.put(list_properties[i] + "_Val", captionrow[0][1])
                            }
                        }
                    }
                }
            }
            Boolean kt_sl = false
            for (int i = 0; i < requestBody.itemList.size(); i++)
            {
                if (requestBody.itemList[i].sFieldName.toUpperCase() == "SL_TU")
                    kt_sl = true
            }

            Double sl_tu = 0
            Double sl_den = 0
            Double dongia = 0
            Double gia_trongoi = 0
            Double gia_vnmobile = 0
            Double gia_vina = 0
            Double gia_viettel = 0
            Double gia_mobi = 0
            Double gia_gtel = 0

            Double gia_trongnuoc = 0
            Double gia_quocte = 0
            Double gia_luutru = 0

            // Kiểm tra số lượng trước khi insert
            if (kt_sl && ds_sub.size() > 0)
            {
                for (int index = 0; index < requestBody.itemList.size(); ++index)
                {
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "SL_TU")
                    {
                        sl_tu = Double.parseDouble(requestBody.itemList[index].sValue)
                    }
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "SL_DEN"){
                        sl_den = Double.parseDouble(requestBody.itemList[index].sValue)
                    }
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "DONGIA")
                        dongia = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_TRONGOI")
                        gia_trongoi = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_VNMOBILE")
                        gia_vnmobile = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_VINA")
                        gia_vina = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_VIETTEL")
                        gia_viettel = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_MOBI")
                        gia_mobi = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_GTEL")
                        gia_gtel = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_DOWNLOAD_TN")
                        gia_trongnuoc = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_DOWNLOAD_QT")
                        gia_quocte = Double.parseDouble(requestBody.itemList[index].sValue)
                    if (requestBody.itemList[index].sFieldName.toUpperCase() == "GIA_LUUTRU")
                        gia_luutru = Double.parseDouble(requestBody.itemList[index].sValue)
                }

                if (dongia != 0 && gia_trongoi != 0)
                {
                    strCheckInput = "Chỉ được nhập 1 trong 2 loại Đơn giá hoặc Giá trọn gói !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                if (gia_trongoi != 0 && (gia_vnmobile != 0 || gia_vina != 0 || gia_viettel != 0 || gia_mobi != 0 || gia_gtel != 0))
                {
                    strCheckInput = "Chỉ được nhập 1 trong 2 loại Giá trọn gói hoặc đơn giá từng nhà mạng !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
                if (gia_trongoi != 0 && (gia_trongnuoc != 0 || gia_quocte != 0 || gia_luutru != 0))
                {
                    strCheckInput = "Chỉ được nhập 1 trong 2 loại Giá trọn gói hoặc đơn giá thành phần !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

                if (sl_tu >= sl_den)
                {
                    strCheckInput = "Số lượng từ không được lớn hơn số lượng đến !"
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

                Double _sl_tu = 0
                Double _sl_den = 0

                if (requestBody.kieu == 0) // Nhập mới
                {
                    for (int i = 0; i < ds_sub.size(); i++)
                    {
                        _sl_tu = Double.parseDouble(ds_sub[i]["SL_TU"].toString());
                        _sl_den = Double.parseDouble(ds_sub[i]["SL_DEN"].toString());

                        if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                        {
                            strCheckInput = "Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !"
                            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                        }
                    }
                }
                else
                {

                    for (int i = 0; i < ds_sub.size(); i++)
                    {
                        if (requestBody.vthuebao_sub_id != Long.parseLong(ds_sub[i]["HDTB_SUB_ID"].toString()))
                        {
                            _sl_tu = Double.parseDouble(ds_sub[i]["SL_TU"].toString());
                            _sl_den = Double.parseDouble(ds_sub[i]["SL_DEN"].toString());

                            if ((sl_tu >= _sl_tu && sl_tu <= _sl_den)
                                    || (sl_den >= _sl_tu && sl_den <= _sl_den)
                                    || (sl_tu <= _sl_tu && sl_den >= _sl_den))
                            {
                                strCheckInput = "Trùng khoảng số lượng Từ - Đến. Bạn hãy kiểm tra lại !"
                                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, strCheckInput, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                            }
                        }
                    }
                }
            }

            if (requestBody.itemList != null && requestBody.itemList.size() > 0)
            {
                JSONArray dt_thuoctinh_sub = new JSONArray()
                JSONObject obj = new JSONObject()
                for (int i = 0; i < requestBody.itemList.size(); i++)
                {
                    obj.put(requestBody.itemList[i].sFieldName.toString(), requestBody.itemList[i].sValue)
                }
                dt_thuoctinh_sub.put(obj)

                String str_dt_thuoctinh_sub = dt_thuoctinh_sub.toString()

                if (requestBody.kieu == 2)//Xóa
                {
                    vthaotac = 3
                }

                sqlString = "{ ? = call css.update_tdtt_db_cntt_sub(?,?,?,?,?,?,?,?,?) }"
                String update_result = ""
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.VARCHAR,
                                         Long.parseLong(phanVungId),
                                         requestBody.vthuebao_id,
                                         requestBody.vbaohong_id,
                                         requestBody.vthuebao_sub_id,
                                         vmay_cn,
                                         vnguoi_cn,
                                         str_dt_thuoctinh_sub,
                                         vthaotac,
                                         requestBody.vappend_name
                    ]) {
                        String rs ->
                            update_result = rs
                    }
                }
                if (update_result == "1")
                {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, update_result, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [ghilai_thuoctinh_sub_bddv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    // 1.5 Hoàn thành phiếu
    @Override
    ResponseEntity<BaseResponse> giaophieu_ks_lapdatmoi(DVITCareGiaophieuKSLapDatMoiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String nguoidung_id = session_.get(ID_NHANVIEN_DHSX)

            // B1. Lấy danh sách hướng giao quy trình
            List data_huonggiao_id = []
            String sqlString = "{ call css.lay_huongiao_quytrinh_itcare(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     33, // LoaiHopDong.KHAOSATDATMOI
                                     571, // KieuLapDat.KHAOSATDATMOI
                                     2, // thutu_id
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    data_huonggiao_id = resultSetToObject.getDynamicData(rs)
                }
            }

            int huonggiao_id = 0
            if (data_huonggiao_id != null && data_huonggiao_id.size() > 0)
            {
                huonggiao_id = data_huonggiao_id[0]["huonggiao_id"]
            }
            // B2. Cập nhật trạng thái hd_thuebao
            sqlString = "{ call css.capnhat_trangthai_hd_thuebao_itcare(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     4, // tthd_id
                                     requestBody.vhdtb_id]) {}
            }

            // B3. Lấy đơn vị nhận
            List _ds = []
            Integer colNum = 0
            sqlString = "{ call css.lay_donvi_nhan_itcare(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vhdtb_id,
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    _ds = resultSetToObject.getDynamicData(rs)
                }
            }

            List<String> list_properties = []
            if (_ds.size() > 0)
            {
                colNum = _ds[0].size()
                for (int i = 0; i < colNum; i++) {
                    list_properties.add(_ds[0].collect()[i].key)
                }
            }


            // B4. Kiểm tra điều kiện và thực hiện gen lệnh

            int vdonvi_id = Integer.parseInt(session_.get(ID_DONVI))
            int vnhanvien_id = Integer.parseInt(session_.get(ID_NHANVIEN_DHSX))

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            LocalDateTime now = LocalDateTime.now()
            String vngay_cn = dtf.format(now)

            String vma_nd = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)
            String sophieu = "0"
            String vnoidung_giao = "Phiếu khảo sát đặt mới"
            int kt_daucuoi = 1
            Boolean _Giaoviec_td = false
            Boolean _Giaoviec_NVKD = false

            Boolean kt = false
            Boolean auto_gv = false  // hoàng pkn : 04/05/2016. Phục vụ tự động giao việc cho nhân viên ở đơn vị nhận phiếu
            Boolean kt_dichchuyen = false
            Boolean auto_giao_OMC = false
            Boolean gp_omc = false
            Boolean ktphieu = false
            List _dsPhieu = []

            for (int k = 0; k < colNum; k++)
            {
                if (list_properties[k] == "KIEUDV_ID")
                {
                    kt = true
                }
                if (list_properties[k] == "AUTO_GV")
                {
                    auto_gv = true
                }
                if (list_properties[k] == "DC_DAUCUOI")
                {
                    kt_dichchuyen = true
                }
                if (list_properties[k] == "AUTO_GIAO_OMC")
                {
                    auto_giao_OMC = true
                }
                if (list_properties[k] == "KTPHIEU")
                {
                    ktphieu = true
                }
            }
            long vphieu_id = 0
            int _index = 0

            for (int i = 0; i< _ds.size(); i++)
            {
                // B5. Update trạng thái phiếu cũ
                int _kt_dichchuyen = kt_dichchuyen ? 1 : 0
                int _ktphieu = ktphieu ? 1: 0
                String _dc_daucuoi = _ds[i]["dc_daucuoi"] != null ? _ds[i]["dc_daucuoi"].toString() : ""
                sqlString = "{ call css.capnhat_trangthai_phieu_cu_itcare(?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Long.parseLong(phanVungId),
                                         Long.parseLong(_ds[i]["phieu_id"].toString()),
                                         Long.parseLong(_ds[i]["hdtb_id"].toString()),
                                         huonggiao_id,
                                         kt_daucuoi,
                                         _kt_dichchuyen,
                                         _ktphieu,
                                         _dc_daucuoi
                    ]) {
                    }
                }

                // B6.+b7 Update trạng thái hd_thuebao
                sqlString = "{ call css.capnhat_tthd_theo_huonggiao_itcare(?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Long.parseLong(phanVungId),
                                         huonggiao_id,
                                         Long.parseLong(_ds[i]["hdtb_id"].toString()),
                                         kt_dichchuyen ? 1 : 0,
                                         _ds[i]["dc_daucuoi"].toString()
                    ]) {
                    }
                }

                // B8. Nếu chưa tồn tại phiếu cùng hướng giao thì mới giao phiếu (Tránh tinh trạng 2 người cùng giao phiếu)
                sqlString = "{ ? = call css.kiemtra_phieu_cung_huonggiao_itcare(?,?,?,?) }"
                Integer kq_kiemtra = 0
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER,
                                         Long.parseLong(phanVungId),
                                         huonggiao_id,
                                         Long.parseLong(_ds[i]["hdtb_id"].toString()),
                                         Long.parseLong(_ds[i]["phieu_id"].toString())
                    ]) {Integer rs ->
                        kq_kiemtra = rs
                    }
                }
                // kq>0 là đã có phiếu giao, =0 là chưa có
                vphieu_id = 0
                if (kq_kiemtra == 0)
                {
                    sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "GIAOPHIEU", 1, 10]) { Integer rs ->
                            vphieu_id = rs
                        }
                    }
                    // Insert phiếu
                    sqlString = "{ call css.insert_giaophieu_itcare(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Long.parseLong(phanVungId),
                                             vphieu_id,
                                             Long.parseLong(_ds[i]["hdtb_id"] != null ? _ds[i]["hdtb_id"].toString() : 0 as String),
                                             vdonvi_id,  // donvi_id của user
                                             vnhanvien_id, //nhanvien_id của user
                                             Integer.parseInt(_ds[i]["donvinhan_id"] != null ? _ds[i]["donvinhan_id"].toString() : 0 as String), //donvi_id lấy từ B3
                                             1,
                                             huonggiao_id,
                                             vngay_cn, //--dd/mm/yyyy hh24:mi:ss
                                             vnoidung_giao,
                                             1,
                                             Long.parseLong(_ds[i]["phieu_id"] != null ? _ds[i]["phieu_id"].toString() : "0"),
                                             4,
                                             Integer.parseInt(_ds[i]["tthd_id"] != null ? _ds[i]["tthd_id"].toString() : "0"),
                                             sophieu,
                                             vma_nd, //username của user
                                             vngay_cn,   //dd/mm/yyyy hh24:mi:ss
                                             vmay_cn,  // app cũ fix 'VNPT-Cab'
                                             vip_cn,  // app cũ fix 'VNPT-Cab'
                                             Integer.parseInt(_ds[i]["kieudv_id"] != null ? _ds[i]["kieudv_id"].toString() : "0")]) {
                        }
                    }
                    if (auto_gv == true) {
                        // B11. Lấy phiếu trả ID
                        sqlString = "{ ? = call css.lay_ds_phieutra_id_itcare(?,?,?,?,?) }"
                        long vphieutra_id = 0
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(sqlString, [Sql.INTEGER,
                                                 Long.parseLong(phanVungId),
                                                 Long.parseLong(_ds[i]["hdtb_id"] != null ? _ds[i]["hdtb_id"].toString() : "0"),
                                                 huonggiao_id,
                                                 3, //: TRANGTHAI_PH.DATRALAI
                                                 Long.parseLong(_ds[i]["donvinhan_id"] != null ? _ds[i]["donvinhan_id"].toString() : "0")
                            ]) { Integer rs ->
                                vphieutra_id = rs
                            }
                        }
                        if (vphieutra_id != 0) {
                            // B9. Giao phiếu nhân viên
                            sqlString = "{ call css.insert_giaophieu_nv_itcare(?,?,?,?,?,?,?,?,?,?,?) }"
                            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                sql.call(sqlString, [Long.parseLong(phanVungId),
                                                     vphieu_id,
                                                     vnhanvien_id,
                                                     vngay_cn,
                                                     vnhanvien_id,
                                                     null,
                                                     null,
                                                     null,
                                                     0,
                                                     null,
                                                     vphieutra_id
                                ]) {}
                            }
                        }
                    }
                } else {
                    // Đã có phiếu giao
                    if (Integer.parseInt(_ds[i]["dichvuvt_id"] != null ? _ds[i]["dichvuvt_id"].toString() : "0") == 9) //DichVuVienThong.TSL
                    {
                        // B10.  Kiểm tra hướng giao
                        // --0 là không có
                        sqlString = "{ ? = css.kiemtra_huonggiao_itcare(?,?,?) }"
                        Integer huonggiao_kiemtra = 0
                        List data_huonggiao = []
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(sqlString, [Sql.INTEGER,
                                                 Long.parseLong(phanVungId),
                                                 huonggiao_id,
                                                 CURSOR_PARAMETER
                            ]) { Integer rs, ResultSet rs_data ->
                                huonggiao_kiemtra = rs
                                data_huonggiao = resultSetToObject.getDynamicData(rs_data)
                            }
                        }

                        if (huonggiao_kiemtra > 0)
                        {
                            if (data_huonggiao != null && data_huonggiao.size() > 0)
                            {
                                if (data_huonggiao[0]["tthd_id"].toString() == "4") // DANG_THI_CONG
                                {
                                    vphieu_id = 0
                                    sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                        sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "GIAOPHIEU", 1, 10]) { Integer rs ->
                                            vphieu_id = rs
                                        }
                                    }

                                    // Insert phiếu
                                    sqlString = "{ call css.insert_giaophieu_itcare(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                        sql.call(sqlString, [Long.parseLong(phanVungId),
                                                             vphieu_id,
                                                             Long.parseLong(_ds[i]["hdtb_id"] != null ? _ds[i]["hdtb_id"].toString() : "0"),
                                                             vdonvi_id,  // donvi_id của user
                                                             vnhanvien_id, //nhanvien_id của user
                                                             Integer.parseInt(_ds[i]["donvinhan_id"] != null ? _ds[i]["donvinhan_id"].toString() : "0"), //donvi_id lấy từ B3
                                                             1,
                                                             huonggiao_id,
                                                             vngay_cn, //--dd/mm/yyyy hh24:mi:ss
                                                             vnoidung_giao,
                                                             1,
                                                             Long.parseLong(_ds[i]["phieu_id"] != null ? _ds[i]["phieu_id"].toString() : "0"),
                                                             4,
                                                             Integer.parseInt(_ds[i]["tthd_id"] != null ? _ds[i]["tthd_id"].toString() : "0"),
                                                             sophieu,
                                                             vma_nd, //username của user
                                                             vngay_cn,   //dd/mm/yyyy hh24:mi:ss
                                                             vmay_cn,  // app cũ fix 'VNPT-Cab'
                                                             vip_cn,  // app cũ fix 'VNPT-Cab'
                                                             Integer.parseInt(_ds[i]["kieudv_id"].toString())]) {
                                        }
                                    }
                                    //
                                    if (auto_gv == true)
                                    {
                                        sqlString = "{ ? = call css.lay_ds_phieutra_id_itcare(?,?,?,?,?) }"
                                        long vphieutra_id = 0
                                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                            sql.call(sqlString, [Sql.INTEGER,
                                                                 Long.parseLong(phanVungId),
                                                                 Long.parseLong(_ds[i]["hdtb_id"] != null ? _ds[i]["hdtb_id"].toString() : "0"),
                                                                 huonggiao_id,
                                                                 3, //: TRANGTHAI_PH.DATRALAI
                                                                 Long.parseLong(_ds[i]["donvinhan_id"] != null ? _ds[i]["donvinhan_id"].toString() : "0")
                                            ]) { Integer rs ->
                                                vphieutra_id = rs
                                            }
                                        }
                                        if (vphieutra_id != 0)
                                        {
                                            // B9. Giao phiếu nhân viên
                                            sqlString = "{ call css.insert_giaophieu_nv_itcare(?,?,?,?,?,?,?,?,?,?,?) }"
                                            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                                                sql.call(sqlString, [Long.parseLong(phanVungId),
                                                                     vphieu_id,
                                                                     vnhanvien_id,
                                                                     vngay_cn,
                                                                     vnhanvien_id,
                                                                     null,
                                                                     null,
                                                                     null,
                                                                     0,
                                                                     null,
                                                                     vphieutra_id
                                                ]) {  }
                                            }
                                        }
                                    }
                                    //
                                }
                            }
                        }
                    }
                }
                // Add vào để xử lý bên dưới
                _dsPhieu.push(vphieu_id)

                //
                // B13.  Lấy giờ hẹn đến
                sqlString = "{ call css.lay_giohen_den_itcare(?,?,?) }"
                List ds_giohen = []
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Long.parseLong(phanVungId),
                                         requestBody.vhdtb_id,
                                         CURSOR_PARAMETER
                    ]) {ResultSet rs ->
                        ds_giohen = resultSetToObject.getDynamicData(rs)
                    }
                }

                if (ds_giohen != null && ds_giohen.size() > 0)
                {
                    // B14. Cập nhật thời gian cam kết
                    sqlString = "{ call css.capnhat_thoigian_camket_itcare(?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Long.parseLong(phanVungId),
                                             vphieu_id,
                                             ds_giohen[0]["giohen_den"].toString()
                        ]) {
                        }
                    }
                }

                // Kết thúc for
            }


            for (int i = 0; i< _ds.size(); i++)
            {
                // B16.  Nếu là kênh tiếp nhận 700 thì sẽ tự động giao 1 phiếu về Phòng BH theo điểm chia

                sqlString = "{ ? = call css.lay_kenh_tiepnhan_itcare(?,?) }"
                Integer ds_kenh_tiepnhan = 0
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Sql.INTEGER,
                            Long.parseLong(phanVungId),
                            Long.parseLong(_ds[i]["hdtb_id"] != null ? _ds[i]["hdtb_id"].toString() : "0")
                    ]) {Integer rs ->
                        ds_kenh_tiepnhan = rs
                    }
                }

                if (ds_kenh_tiepnhan != null && ds_kenh_tiepnhan != 1)
                {
                    sqlString = "{ ? = call css.giaophieu_700(?,?,?,?,?,?,?,?) }"
                    String out_ = ""
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [
                                Sql.VARCHAR,
                                Long.parseLong(phanVungId),
                                Long.parseLong(_ds[i]["hdtb_id"] != null ? _ds[i]["hdtb_id"].toString() : "0"),
                                Long.parseLong(_ds[i]["phieu_id"] != null ? _ds[i]["phieu_id"].toString() : "0"),
                                vnhanvien_id,
                                15000,
                                vmay_cn,
                                vma_nd,
                                2
                        ]) {String rs ->
                            out_ = rs
                        }
                    }
                }


            }

            for (int i = 0; i <= _dsPhieu.size() - 1; i++) {
                if (_Giaoviec_td && _dsPhieu[i] != 0) {
                    sqlString = "{ call  css.tudong_giaophieu_nv_ld_v2(?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [

                                Long.parseLong(phanVungId),
                                _dsPhieu[i],
                                vnhanvien_id,
                                "Kiểm tra và xử lý"
                        ]) {
                        }
                    }
                }
                if (_Giaoviec_NVKD && _dsPhieu[i] != 0) {
                    sqlString = "{ call css.TUDONG_GIAOPHIEU_NVKD_v2(?,?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [

                                Long.parseLong(phanVungId),
                                _dsPhieu[i],
                                vnhanvien_id,
                                "Kiểm tra và xử lý"
                        ]) {
                        }
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [lay_thongtin_baotri] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> themmoiphieuks_datmoi(DVITCareObjCapNhat_KhaoSatRequest objPhieu) {
        String requestId = genericApi.generateRequestId()
        try {

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String vma_nd = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)

            int vdonvi_id = Integer.parseInt(session_.get(ID_DONVI))
            int vnhanvien_id = Integer.parseInt(session_.get(ID_NHANVIEN_DHSX))

            int vhuonggiao_id = 0

            String sqlString = "{ call CSS.PHANLOAI_KH_THEO_LOAIKH(?,?,?) }"
            List dtPhanLoaiKH = []
            int phanloaikh_id = 0
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                                     objPhieu.objHDKH_KS[0].LOAI_KH,
                                     CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    dtPhanLoaiKH = resultSetToObject.getDynamicData(rs)
                }
            }
            if (dtPhanLoaiKH != null && dtPhanLoaiKH.size() > 0)
            {
                if (dtPhanLoaiKH[0]["loai"].toString() == "1")
                {
                    phanloaikh_id = Integer.parseInt(dtPhanLoaiKH[0]["giatri"].toString())
                }
            }
            // Thêm mới
            if (objPhieu.isAdd) {
                String str1sql = "select 1 from ADMIN.thamso_md where ma_ts = 'SINH_MATB_KS' and ten_ts = '1' and phanvung_id=" + phanVungId.toString();
                List data_str1sql = []
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(str1sql, []) { ResultSet rs ->
                        data_str1sql = resultSetToObject.getDynamicData(rs)
                    }
                }
                if (data_str1sql != null && data_str1sql.size() > 0)
                {
                    sqlString = "{ ? = call CSS.SINH_MATB_KHAOSAT(?,?) }"
                    String SINH_MATB = ""
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Sql.VARCHAR,
                                             Long.parseLong(phanVungId),
                                             1
                        ]) { String rs ->
                            SINH_MATB = rs
                        }
                    }
                    objPhieu.objHDTB_KS[0].MA_TB = SINH_MATB
                }


                long _HDKH_ID = 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "HD_KHACHHANG", 1, 10]) { Integer rs ->
                        _HDKH_ID = rs
                    }
                }
                objPhieu.objHDKH_KS[0].HDKH_ID = _HDKH_ID
                String response = ""
                sqlString = "{ call ? := CSS.LAP_HOP_DONG.SINH_MAGD(?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId),
                                         objPhieu.objHDKH_KS[0].LOAIHD_ID]) { String rs ->
                        response = rs
                    }
                }
                objPhieu.objHDKH_KS[0].MA_GD = response // new DataUtils().SINH_MAGD(objPhieu.objHDKH_KS[0].LOAIHD_ID, matinh);

                sqlString = "{ call CSS.INSERT_HDKH_PHIEU_KS(?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?) }"
                String insert_out = ""
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            1,
                            objPhieu.objHDKH_KS[0].HDKH_ID,
                            objPhieu.objHDKH_KS[0].MA_GD,
                            objPhieu.objHDKH_KS[0].KHACHHANG_ID,
                            objPhieu.objHDKH_KS[0].MA_KH,
                            objPhieu.objHDKH_KS[0].TEN_KH,
                            objPhieu.objDiaChiKH[0].DIACHI,
                            objPhieu.objHDKH_KS[0].MST,
                            objPhieu.objHDKH_KS[0].NGAY_SN,
                            objPhieu.objHDKH_KS[0].LOAIGT_ID,
                            objPhieu.objHDKH_KS[0].SO_GT,
                            objPhieu.objHDKH_KS[0].NGAYCAP,
                            objPhieu.objHDKH_KS[0].NOICAP,
                            objPhieu.objHDKH_KS[0].DONVI_NV_ID,
                            vdonvi_id,
                            vnhanvien_id,
                            objPhieu.objHDKH_KS[0].KHLON_ID,
                            objPhieu.objHDKH_KS[0].LOAI_KH,
                            objPhieu.objHDKH_KS[0].LOAIHD_ID,
                            vma_nd,
                            vmay_cn,
                            vip_cn,
                            vnhanvien_id,
                            objPhieu.objHDKH_KS[0].NHANVIENGT_ID,
                            objPhieu.objHDKH_KS[0].GIOITINH,
                            objPhieu.objHDKH_KS[0].SO_DT,
                            objPhieu.objHDKH_KS[0].QUOCTICH_ID,
                            objPhieu.objHDKH_KS[0].NGANHNGHE_ID,
                            objPhieu.objHDKH_KS[0].NGAYHETHAN_GT,
                            objPhieu.objHDKH_KS[0].TNC1_ID,
                            objPhieu.objHDKH_KS[0].TNC2_ID,
                            objPhieu.objHDKH_KS[0].TNC3_ID,
                            phanloaikh_id,
                            4,
                            Sql.VARCHAR
                    ]) { String verr ->
                        insert_out = verr
                    }
                }
                String s = ""
                if (insert_out != "OK")
                {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, insert_out, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

                int huonggiaotn_id = 0
                int quytrinh_id = 0
                List ds_huonggiao_quytrinh = []

                sqlString = "{ call css.lay_huongiao_quytrinh_itcare(?,?,?,?,?) }"

                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Long.parseLong(phanVungId),
                                         33,
                                         571,
                                         1,
                                         CURSOR_PARAMETER
                    ]) { ResultSet rs ->
                        ds_huonggiao_quytrinh = resultSetToObject.getDynamicData(rs)
                    }
                }

                if (ds_huonggiao_quytrinh != null && ds_huonggiao_quytrinh.size() > 0)
                {
                    huonggiaotn_id = Integer.parseInt(ds_huonggiao_quytrinh[0]["huonggiao_id"].toString())
                    vhuonggiao_id = huonggiaotn_id;
                    quytrinh_id = Integer.parseInt(ds_huonggiao_quytrinh[0]["quytrinh_id"].toString())
                }
                s = ""
                objPhieu.objHDTB_KS[0].QUYTRINH_ID = quytrinh_id
                long _HD_THUEBAO= 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "HD_THUEBAO", 1, 10]) { Integer rs ->
                        _HD_THUEBAO = rs
                    }
                }
                objPhieu.objHDTB_KS[0].HDTB_ID = _HD_THUEBAO

                sqlString = "{ call CSS.INSERT_HDTB_PHIEU_KS(?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?,?,?,?,?,?) }"
                insert_out = ""
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            objPhieu.objHDKH_KS[0].HDKH_ID,
                            objPhieu.objHDTB_KS[0].THUEBAO_ID,
                            objPhieu.objHDTB_KS[0].MA_TB,
                            objPhieu.objHDTB_KS[0].TEN_TB,
                            objPhieu.objHDTB_KS[0].DIACHI_LD,
                            objPhieu.objHDTB_KS[0].DIACHI_TB,
                            571,
                            objPhieu.objHDTB_KS[0].DOITUONG_ID,
                            objPhieu.objHDTB_KS[0].TTHD_ID,
                            objPhieu.objHDTB_KS[0].LOAITB_ID,
                            objPhieu.objHDTB_KS[0].DICHVUVT_ID,
                            objPhieu.objHDTB_KS[0].DONVI_ID,
                            vma_nd,
                            vmay_cn,
                            objPhieu.objHDTB_KS[0].QUYTRINH_ID,
                            vip_cn,
                            objPhieu.objHDTB_KS[0].SOLUONG,
                            objPhieu.objHDTB_KS[0].GHICHU,
                            Sql.VARCHAR
                    ]) { String verr ->
                        insert_out = verr
                    }
                }

                if (insert_out != "OK")
                {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, insert_out, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }

                // Cập nhật biến động
                for (int i = 0; i < objPhieu.objHDTB_KS.size(); i++) {
                    sqlString = "{ call CSS.LAP_HOP_DONG.CAPNHAT_BIENDONG(?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Long.parseLong(phanVungId), objPhieu.objHDTB_KS[i].HDTB_ID])
                                { }
                    }

                    // Chuyển dữ liệu hdtb sang giao phiếu

                    sqlString = "{ call CSS.DIEUHANH_THICONG.chuyen_dl_hdtb_sang_giaophieu(?,?,?) }"
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        sql.call(sqlString, [Long.parseLong(phanVungId),
                                             objPhieu.objHDTB_KS[i].HDTB_ID,
                                             vhuonggiao_id])
                                { }
                    }
                }

                int loaiDonVi = 0;
                if (objPhieu.objHDTB_KS[0].LOAITB_ID == 270) // LoaiHinhTB.IT_CARE
                {
                    loaiDonVi = 700 //LOAI_DV.TO_HATANG_TTVT;
                }
                else
                {
                    loaiDonVi = 5 //LOAI_DV.TRAM_VT;
                }

                // //HDTB_DV_DATA
                sqlString = "{ call css.capnhat_hdtb_con(?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            1,
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            loaiDonVi,
                            objPhieu.objHDTB_KS[0].DONVI_ID,
                            2,
                            objPhieu.objHDTB_ADSL_KS[0].CULY
                    ])
                            { }
                }

                //HDTB_ADSL_DATA Gộp vào bên trên capnhat_hdtb_con


                //DIA CHI KH
                long _DIACHI = 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "DIACHI", 1, 10]) { Integer rs ->
                        _DIACHI = rs
                    }
                }
                objPhieu.objDiaChiKH[0].DIACHI_ID = _DIACHI

                sqlString = "{ call css.capnhat_diachi_kh_khaosat(?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            1,
                            objPhieu.objDiaChiKH[0].DIACHI_ID,
                            objPhieu.objDiaChiKH[0].TINH_ID,
                            objPhieu.objDiaChiKH[0].QUAN_ID,
                            objPhieu.objDiaChiKH[0].PHUONG_ID,
                            objPhieu.objDiaChiKH[0].PHO_ID,
                            objPhieu.objDiaChiKH[0].AP_ID,
                            objPhieu.objDiaChiKH[0].KHU_ID,
                            objPhieu.objDiaChiKH[0].SONHA,
                            objPhieu.objDiaChiKH[0].DIACHI,
                            objPhieu.objHDKH_KS[0].HDKH_ID
                    ])
                            { }
                }

                //diachi_hdkh

                //Dia Chi TB
                _DIACHI = 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "DIACHI", 1, 10]) { Integer rs ->
                        _DIACHI = rs
                    }
                }
                objPhieu.objDiaChiTB[0].DIACHI_ID = _DIACHI // new DataUtils().GetKey("DIACHI", matinh);
                _DIACHI = 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "DIACHI", 1, 10]) { Integer rs ->
                        _DIACHI = rs
                    }
                }
                objPhieu.objDiaChiLD[0].DIACHI_ID = _DIACHI

                sqlString = "{ call css.capnhat_diachi_tb_khaosat(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            1,
                            objPhieu.objDiaChiTB[0].DIACHI_ID,
                            objPhieu.objDiaChiTB[0].TINH_ID,
                            objPhieu.objDiaChiTB[0].QUAN_ID,
                            objPhieu.objDiaChiTB[0].PHUONG_ID,
                            objPhieu.objDiaChiTB[0].PHO_ID,
                            objPhieu.objDiaChiTB[0].AP_ID,
                            objPhieu.objDiaChiTB[0].KHU_ID,
                            objPhieu.objDiaChiTB[0].SONHA,
                            objPhieu.objDiaChiTB[0].DIACHI,
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            objPhieu.objDiaChiLD[0].DIACHI_ID,
                            objPhieu.objDiaChiLD[0].TINH_ID,
                            objPhieu.objDiaChiLD[0].QUAN_ID,
                            objPhieu.objDiaChiLD[0].PHUONG_ID,
                            objPhieu.objDiaChiLD[0].PHO_ID,
                            objPhieu.objDiaChiLD[0].AP_ID,
                            objPhieu.objDiaChiLD[0].KHU_ID,
                            objPhieu.objDiaChiLD[0].SONHA,
                            objPhieu.objDiaChiLD[0].DIACHI

                    ])
                            { }
                }

                //Dia Chi LDat


                //Dia Chi HDTB


                /////Insert_HDTB_KV

                //Dia Chi TB
                _DIACHI = 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "DIACHI", 1, 10]) { Integer rs ->
                        _DIACHI = rs
                    }
                }
                objPhieu.objDiaChiTB[0].DIACHI_ID = _DIACHI
                sqlString = "{ call css.capnhat_hdtb_kv_khaosat(?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            objPhieu.objHDTB_KV_KS[0].KHUVUC_ID,
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            4,
                            vma_nd,
                            vmay_cn,
                            vip_cn,
                            objPhieu.objHDTB_KV_KS[0].KIEUKV_ID


                    ])
                            { }
                }
                // Kết thúc thêm
            }
            else
            {
                // Bắt đầu sửa
                sqlString = "{ call CSS.INSERT_HDKH_PHIEU_KS(?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?,?,?,?,?," +
                        "?,?,?,?,?,?) }"
                String insert_out = ""
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            2,
                            objPhieu.objHDKH_KS[0].HDKH_ID,
                            objPhieu.objHDKH_KS[0].MA_GD,
                            objPhieu.objHDKH_KS[0].KHACHHANG_ID,
                            objPhieu.objHDKH_KS[0].MA_KH,
                            objPhieu.objHDKH_KS[0].TEN_KH,
                            objPhieu.objDiaChiKH[0].DIACHI,
                            objPhieu.objHDKH_KS[0].MST,
                            objPhieu.objHDKH_KS[0].NGAY_SN,
                            objPhieu.objHDKH_KS[0].LOAIGT_ID,
                            objPhieu.objHDKH_KS[0].SO_GT,
                            objPhieu.objHDKH_KS[0].NGAYCAP,
                            objPhieu.objHDKH_KS[0].NOICAP,
                            objPhieu.objHDKH_KS[0].DONVI_NV_ID,
                            vdonvi_id,
                            vnhanvien_id,
                            objPhieu.objHDKH_KS[0].KHLON_ID,
                            objPhieu.objHDKH_KS[0].LOAI_KH,
                            objPhieu.objHDKH_KS[0].LOAIHD_ID,
                            vma_nd,
                            vmay_cn,
                            vip_cn,
                            vnhanvien_id,
                            objPhieu.objHDKH_KS[0].NHANVIENGT_ID,
                            objPhieu.objHDKH_KS[0].GIOITINH,
                            objPhieu.objHDKH_KS[0].SO_DT,
                            objPhieu.objHDKH_KS[0].QUOCTICH_ID,
                            objPhieu.objHDKH_KS[0].NGANHNGHE_ID,
                            objPhieu.objHDKH_KS[0].NGAYHETHAN_GT,
                            objPhieu.objHDKH_KS[0].TNC1_ID,
                            objPhieu.objHDKH_KS[0].TNC2_ID,
                            objPhieu.objHDKH_KS[0].TNC3_ID,
                            phanloaikh_id,
                            4,
                            Sql.VARCHAR
                    ]) { String verr ->
                        insert_out = verr
                    }
                }
                if (insert_out != "OK")
                {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, insert_out, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }


                // Cập nhật hdtb
                sqlString = "{ call css.capnhat_hdtb_khaosat(?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            objPhieu.objHDTB_KS[0].MA_TB,
                            objPhieu.objHDTB_KS[0].TEN_TB,
                            objPhieu.objHDTB_KS[0].DIACHI_LD,
                            objPhieu.objHDTB_KS[0].DIACHI_TB,
                            objPhieu.objHDTB_KS[0].DOITUONG_ID,
                            objPhieu.objHDTB_KS[0].LOAITB_ID,
                            objPhieu.objHDTB_KS[0].DICHVUVT_ID,
                            objPhieu.objHDTB_KS[0].SOLUONG,
                            objPhieu.objHDTB_KS[0].DONVI_ID,
                            vma_nd,
                            vmay_cn

                    ])
                            { }
                }

                // Cập nhật hdtb_dv
//                sqlString = "UPDATE  css.hdtb_dv SET DONVI_ID ="+ objPhieu.objHDTB_KS[0].DONVI_ID +" " +
//                        "WHERE LOAIDV_ID =5 AND HDTB_ID="+ objPhieu.objHDTB_KS[0].HDTB_ID +" AND PHANVUNG_ID = " + phanVungId
//                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
//                    sql.call(sqlString, [])
//                            { }
//                }

                int loaiDonVi = 0;
                if (objPhieu.objHDTB_KS[0].LOAITB_ID == 270) // LoaiHinhTB.IT_CARE
                {
                    loaiDonVi = 700 //LOAI_DV.TO_HATANG_TTVT;
                }
                else
                {
                    loaiDonVi = 5 //LOAI_DV.TRAM_VT;
                }
                sqlString = "{ call css.capnhat_hdtb_con(?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            2,
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            loaiDonVi,
                            objPhieu.objHDTB_KS[0].DONVI_ID,
                            2,
                            objPhieu.objHDTB_ADSL_KS[0].CULY
                    ])
                            { }
                }




                //DIA CHI KH
                sqlString = "{ call css.capnhat_diachi_kh_khaosat(?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            2,
                            objPhieu.objDiaChiKH[0].DIACHI_ID,
                            objPhieu.objDiaChiKH[0].TINH_ID,
                            objPhieu.objDiaChiKH[0].QUAN_ID,
                            objPhieu.objDiaChiKH[0].PHUONG_ID,
                            objPhieu.objDiaChiKH[0].PHO_ID,
                            objPhieu.objDiaChiKH[0].AP_ID,
                            objPhieu.objDiaChiKH[0].KHU_ID,
                            objPhieu.objDiaChiKH[0].SONHA,
                            objPhieu.objDiaChiKH[0].DIACHI,
                            objPhieu.objHDKH_KS[0].HDKH_ID

                    ])
                            { }
                }

                // Cập nhật địa chỉ thuê bao
                //Dia Chi LDat

                sqlString = "{ call css.capnhat_diachi_tb_khaosat(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            2,
                            objPhieu.objDiaChiTB[0].DIACHI_ID,
                            objPhieu.objDiaChiTB[0].TINH_ID,
                            objPhieu.objDiaChiTB[0].QUAN_ID,
                            objPhieu.objDiaChiTB[0].PHUONG_ID,
                            objPhieu.objDiaChiTB[0].PHO_ID,
                            objPhieu.objDiaChiTB[0].AP_ID,
                            objPhieu.objDiaChiTB[0].KHU_ID,
                            objPhieu.objDiaChiTB[0].SONHA,
                            objPhieu.objDiaChiTB[0].DIACHI,
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            objPhieu.objDiaChiLD[0].DIACHI_ID,
                            objPhieu.objDiaChiLD[0].TINH_ID,
                            objPhieu.objDiaChiLD[0].QUAN_ID,
                            objPhieu.objDiaChiLD[0].PHUONG_ID,
                            objPhieu.objDiaChiLD[0].PHO_ID,
                            objPhieu.objDiaChiLD[0].AP_ID,
                            objPhieu.objDiaChiLD[0].KHU_ID,
                            objPhieu.objDiaChiLD[0].SONHA,
                            objPhieu.objDiaChiLD[0].DIACHI

                    ])
                            { }
                }

                // Tạo hợp đồng thuê bao khu vực

                Long _DIACHI = 0
                sqlString = "{ ? = call admin.GET_KEYS(?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.INTEGER, Long.parseLong(phanVungId), "DIACHI", 1, 10]) { Integer rs ->
                        _DIACHI = rs
                    }
                }
                objPhieu.objDiaChiTB[0].DIACHI_ID = _DIACHI
                sqlString = "{ call css.capnhat_hdtb_kv_khaosat(?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [
                            Long.parseLong(phanVungId),
                            objPhieu.objHDTB_KV_KS[0].KHUVUC_ID,
                            objPhieu.objHDTB_KS[0].HDTB_ID,
                            4,
                            vma_nd,
                            vmay_cn,
                            vip_cn,
                            objPhieu.objHDTB_KV_KS[0].KIEUKV_ID
                    ])
                            { }
                }
                // Kết thúc sửa

            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [xoa_hdtb_cntt_sub] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse> insert_hoso_itcare_smp(INSERT_HOSO_ITCARE_SMPRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId =  session_.get(ID_TINHTHANH)
            String vnguoi_cn = session_.get(USERNAME)
            String vmay_cn = session_.get(DEVICE_ID)
            String vip_cn = session_.get(DEVICE_IP)
            String sqlString = "{ call CSS.INSERT_HOSO_ITCARE_SMP(?,?,?,?,?,?,?,?,?,?,?) }"
            String data = null
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId),
                                     requestBody.vloaifile_id,
                                     requestBody.vdb_id,
                                     requestBody.vloaidb_id,
                                     requestBody.vfile_name,
                                     requestBody.vfile_url,
                                     requestBody.vghichu,
                                     vmay_cn,
                                     vnguoi_cn,
                                     vip_cn,
                                     Sql.VARCHAR]) { String verr ->
                    data = verr
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))

        } catch (Exception e) {
            loggingManaged.error("[DVITCareController] - [insert_hoso_itcare_smp] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
