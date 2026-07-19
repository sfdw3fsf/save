package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonBuilder
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.CSKHApi
import io.swagger.api.DanhGiaNhanVienApi
import io.swagger.model.dto.CauHoiKS
import io.swagger.model.dto.CauHoiKhaoSatData
import io.swagger.model.dto.DanhMuc
import io.swagger.model.dto.FileApp
import io.swagger.model.dto.HopDongDGNV
import io.swagger.model.dto.TraLoiKS
import io.swagger.model.request.CapNhatKSRequest
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.ep.ZaloClient
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.*
import vn.vnptit.sapi.oneapp.model.response.*
import vn.vnptit.sapi.oneapp.util.*

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet
import java.util.function.Function
import java.util.stream.Collectors

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class DanhGiaNhanVienController implements DanhGiaNhanVienApi {
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
    DanhGiaNhanVienController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<BaseResponse<List<HopDongDGNV>>> dsHopDong(Long trangThaiId, String thang) {
        String requestId = genericApi.generateRequestId()
        List<HopDongDGNV> response = []
        try {
            loggingManaged.info("[DanhGiaNhanVienController] - [dsHopDong] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call fims.lay_ds_thuebao_ks_ttvt(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), trangThaiId, thang, Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        HopDongDGNV item = new HopDongDGNV().convertDataFromDatabase(rs)
                        response.add(item)
                    }
                }
            }
            loggingManaged.info("[DanhGiaNhanVienController] - [dsHopDong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[DanhGiaNhanVienController] - [dsHopDong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<CauHoiKhaoSatData>> dsCauHoiKS() {
        String requestId = genericApi.generateRequestId()
        CauHoiKhaoSatData response = new CauHoiKhaoSatData()
        try {
            loggingManaged.info("[DanhGiaNhanVienController] - [dsCauHoiKS] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call FIMS.LAY_DS_CAUHOI_THEOLOAINV(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 95, 27, CURSOR_PARAMETER]) { ResultSet rs ->
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
                            List<CauHoiKS> cauHoiKSList = response.getDsCauHoiPhu()
                            if (cauHoiKSList.size() > 0) {
                                boolean existed = false
                                for (int i = 0; i < cauHoiKSList.size(); i++) {
                                    if (item.getNoiDung() != null && cauHoiKSList.get(i).getNoiDung() != null
                                            && item.getNoiDung().trim().toLowerCase() == cauHoiKSList.get(i).getNoiDung().trim().toLowerCase()) {
                                        existed = true
                                        response.getDsCauHoiPhu().get(i).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                                        break
                                    }
                                }
                                if (!existed) {
                                    response.getDsCauHoiPhu().add(new CauHoiKS().convertDataFromPojo(item))
                                    response.getDsCauHoiPhu().get(response.getDsCauHoiPhu().size() - 1).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                                }
                            } else {
                                response.getDsCauHoiPhu().add(new CauHoiKS().convertDataFromPojo(item))
                                response.getDsCauHoiPhu().get(0).getDsTraLoi().add(new CauHoiKS().convertDataFromPojo(item))
                            }
                        }
                    }
                }
            }
            loggingManaged.info("[DanhGiaNhanVienController] - [dsCauHoiKS] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[DanhGiaNhanVienController] - [dsCauHoiKS] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsCauHoiKSv2() {
        String requestId = genericApi.generateRequestId()
        CauHoiKhaoSatData response = new CauHoiKhaoSatData()
        try {
            loggingManaged.info("[DanhGiaNhanVienController] - [dsCauHoiKSv2] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call FIMS.LAY_DS_CAUHOI_THEOLOAINV(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 95, 27, CURSOR_PARAMETER]) { ResultSet rs ->
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
            loggingManaged.error("[DanhGiaNhanVienController] - [dsCauHoiKSv2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsDaTraLoi(Long tltbId) {
        String requestId = genericApi.generateRequestId()
        List<CauHoiKS> response = []
        try {
            loggingManaged.info("[DanhGiaNhanVienController] - [dsDaTraLoi] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call fims.lay_ct_traloi_ks_nvkt(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), tltbId, CURSOR_PARAMETER]) { ResultSet rs ->
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
            loggingManaged.info("[DanhGiaNhanVienController] - [dsDaTraLoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", response != null ? new JsonBuilder(response) : "").build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[DanhGiaNhanVienController] - [dsDaTraLoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> cnDanhGia(CapNhatKSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhGiaNhanVienController] - [cnDanhGia] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("body", requestBody).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiDungId = session_.get(NGUOIDUNG_ID)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String nguoiCn = session_.get(USERNAME)
            String dsTL = "<cauhoi>"
            if (requestBody.getDsTraLoi() != null && requestBody.getDsTraLoi().size() > 0) {
                for (TraLoiKS traLoiKS : requestBody.getDsTraLoi()) {
                    dsTL += "<khaosat>"
                    dsTL += "<cauhoi_id>" + traLoiKS.getCauHoiId() + "</cauhoi_id>"
                    dsTL += "<traloi_id>" + traLoiKS.getTraLoiId() + "</traloi_id>"
                    dsTL += "<noidung>" + traLoiKS.getNoiDung() + "</noidung>"
                    dsTL += "</khaosat>"
                }
            }
            dsTL += "</cauhoi>"
            String message = ""
            String sqlString = "{ call FIMS.capnhat_khaosat_cldv_nvkt(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.getHdtbId(), requestBody.getTltbId(), requestBody.getTenNV(), requestBody.getSdtNV(),
                                     Long.parseLong(nhanVienId), 27, 1, requestBody.getGhiChu(), dsTL, nguoiCn, mayCn, ipCn, Long.parseLong(nguoiDungId), Sql.VARCHAR]) { String rs ->
                    if (rs != null) {
                        if (rs.trim().toUpperCase() == 'OK') {
                            message = ""
                        } else {
                            message = rs
                        }
                    } else {
                        message = rs
                    }
                }
            }
            loggingManaged.info("[DanhGiaNhanVienController] - [cnDanhGia] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", message).build())
            if (message == "") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        } catch (Exception e) {
            loggingManaged.error("[DanhGiaNhanVienController] - [cnDanhGia] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
