package vn.vnptit.sapi.oneapp.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import groovy.json.JsonBuilder;
import groovy.sql.OutParameter;
import groovy.sql.Sql;
import io.swagger.api.TienIchVaHoTroApi
import io.swagger.model.request.BaoCaoChiTietKSRequest
import io.swagger.model.request.BaoCaoKHDaKSRequest
import io.swagger.model.request.BaoCaoTyLeChonTraLoiRequest;
import io.swagger.model.request.CapNhatTinhNangNoiBatRequest
import io.swagger.model.request.CapNhat_traloiks_mytvRequest
import io.swagger.model.request.ChiTietCauHoiKSMyTV
import io.swagger.model.request.LayDSCauHoiKSTheoNhom
import io.swagger.model.request.LayDSCauHoiMyTVRequest
import io.swagger.model.request.SaveThongTinCauHoiCauTLRequest;
import io.swagger.model.request.ThemTinhNangNoiBatRequest
import io.swagger.model.request.ThongKeTongHopRequest
import io.swagger.model.request.XoaCauHoiRequest;
import io.swagger.model.request.XoaTinhNangNoiBatRequest;
import io.swagger.model.response.BaseResponse;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject;
import vn.vnptit.sapi.oneapp.managed.LoggingManaged;
import vn.vnptit.sapi.oneapp.managed.OracleManaged;
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder;
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine;


import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet
import java.text.SimpleDateFormat;

@RestController
public class TienIchVaHoTroController implements TienIchVaHoTroApi {

    private HttpServletRequest request
    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    private ObjectMapper objectMapper

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy")

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    OracleManaged oracleManaged

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    public TienIchVaHoTroController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    public ResponseEntity<BaseResponse<String>> them_tinhnang_noibat(ThemTinhNangNoiBatRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            String data = "Thành công"
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = java.lang.Integer.parseInt(session_.get("id_tinhthanh").toString())
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")

            loggingManaged.info("[TienIchVaHoTroController] - [them_tinhnang_noibat] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                    sql.call("{ call CSS.SP_THEM_TINHNANG_NOIBAT(?,?,?,?) }", [
                            phanVungId, vnhanvien_id, requestBody.p_tieude, requestBody.p_mota])
            }

            loggingManaged.info("[TienIchVaHoTroController] - [them_tinhnang_noibat] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [them_tinhnang_noibat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of("Thất bại", requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List>> dsTinhNangNoiBat() {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")


            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.SP_LAY_DS_TINHNANG_NOIBAT(?,?,?) }", [
                        phanVungId, vnhanvien_id,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [dsTinhNangNoiBat] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [dsTinhNangNoiBat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    public ResponseEntity<BaseResponse<String>> capnhat_tinhnang_noibat(CapNhatTinhNangNoiBatRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            String data = "Thành công"
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = java.lang.Integer.parseInt(session_.get("id_tinhthanh").toString())
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")

            loggingManaged.info("[TienIchVaHoTroController] - [capnhat_tinhnang_noibat] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.SP_CAPNHAT_TINHNANG_NOIBAT(?,?,?,?,?) }", [
                        phanVungId, requestBody.p_camnang_id,vnhanvien_id, requestBody.p_tieude, requestBody.p_mota])
            }

            loggingManaged.info("[TienIchVaHoTroController] - [capnhat_tinhnang_noibat] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [capnhat_tinhnang_noibat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of("Thất bại", requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    public ResponseEntity<BaseResponse<String>> xoa_tinhnang_noibat(XoaTinhNangNoiBatRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            String data = "Thành công"
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = java.lang.Integer.parseInt(session_.get("id_tinhthanh").toString())
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")

            loggingManaged.info("[TienIchVaHoTroController] - [xoa_tinhnang_noibat] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.SP_XOA_TINHNANG_NOIBAT(?,?,?) }", [
                        phanVungId, requestBody.p_camnang_id,vnhanvien_id])
            }

            loggingManaged.info("[TienIchVaHoTroController] - [xoa_tinhnang_noibat] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [xoa_tinhnang_noibat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of("Thất bại", requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> danhMucNhomCauHoiKSMyTV() {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")


            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_DANHMUC_NHOM_CAUHOIKS_MYTV(?,?,?) }", [
                        phanVungId, vnhanvien_id,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [danhMucNhomCauHoiKSMyTV] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [danhMucNhomCauHoiKSMyTV] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> layDSCauHoiKSMyTv(LayDSCauHoiMyTVRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [layDSCauHoiKSMyTv] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_LAY_DS_CAUHOIKS_MYTV(?,?,?,?) }", [
                        phanVungId, requestBody.p_nhomch_id, vnhanvien_id,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [layDSCauHoiKSMyTv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [layDSCauHoiKSMyTv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> chitiet_cauhoiks_mytv(ChiTietCauHoiKSMyTV requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [chitiet_cauhoiks_mytv] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_CHITIET_CAUHOIKS_MYTV(?,?,?,?) }", [
                        phanVungId, requestBody.p_cauhoi_id, vnhanvien_id,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [chitiet_cauhoiks_mytv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [chitiet_cauhoiks_mytv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> capnhat_cauhoiks_mytv(SaveThongTinCauHoiCauTLRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            String data = ""
            loggingManaged.info("[TienIchVaHoTroController] - [capnhat_cauhoiks_mytv] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            String sqlString = "{ ? = call css.pkg_khtn_3b.fn_capnhat_cauhoiks_mytv(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, vnhanvien_id, requestBody.p_cauhoi_id, requestBody.p_loaich_id,requestBody.p_nhomch_id, requestBody.p_cauhoi, requestBody.p_traloi ]) { String rs ->
                    data = rs
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [capnhat_cauhoiks_mytv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [capnhat_cauhoiks_mytv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> xoa_cauhoiks_mytv(XoaCauHoiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            String data = ""
            loggingManaged.info("[TienIchVaHoTroController] - [xoa_cauhoiks_mytv] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            String sqlString = "{ ? = call css.pkg_khtn_3b.fn_xoa_cauhoiks_mytv(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.p_cauhoi_id ,vnhanvien_id]) { String rs ->
                    data = rs
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [xoa_cauhoiks_mytv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [xoa_cauhoiks_mytv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> lay_ds_cauhoiks_theo_nhomch(LayDSCauHoiKSTheoNhom requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            //
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [lay_ds_cauhoiks_theo_nhomch] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_LAY_DS_CAUHOIKS_THEO_NHOMCH(?,?,?,?) }", [
                        phanVungId, requestBody.p_nhomch_id, vnhanvien_id,CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [layDSCauHoiKSMyTv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [layDSCauHoiKSMyTv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> capnhat_traloiks_mytv(CapNhat_traloiks_mytvRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            String data = ""
            String nguoi_cn = session_.get("id_nhanvien_dhsx").toString()
            String may_cn = "VNPT Employee"
            loggingManaged.info("[TienIchVaHoTroController] - [capnhat_traloiks_mytv] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            String sqlString = "{ ? = call css.pkg_khtn_3b.fn_capnhat_traloiks_mytv(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, vnhanvien_id, requestBody.p_khachhang_id, requestBody.p_ten_kh, requestBody.p_sodt_kh, requestBody.p_diachi_kh, requestBody.p_ghichu, requestBody.p_traloi, may_cn, nguoi_cn ]) { String rs ->
                    data = rs
                }
            }

            if(!data.equals("OK")) {
                throw new Exception(data)
            }

            loggingManaged.info("[TienIchVaHoTroController] - [capnhat_traloiks_mytv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [capnhat_traloiks_mytv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( "Thất bại", requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> dsDonViKSMyTV(Long p_kieu) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [dsDonViKSMyTV] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", p_kieu == null ? "null" : p_kieu)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_LAY_DS_DONVIKS_MYTV(?,?,?) }", [
                        phanVungId, p_kieu,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [dsDonViKSMyTV] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [dsDonViKSMyTV] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> dsNhanVienDonVi(Long p_donvi_id) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [dsNhanVienDonVi] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", p_donvi_id)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_LAY_DS_NHANVIEN_DONVI(?,?,?) }", [
                        phanVungId, p_donvi_id,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [dsNhanVienDonVi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [dsNhanVienDonVi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<List>> thongKeTongHop(ThongKeTongHopRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = requestBody.p_nhanvien_id
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [thongKeTongHop] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())
            String tu_ngay = formatter.format(requestBody.p_tungay)
            String den_ngay =formatter.format(requestBody.p_denngay)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_BAOCAO_TONGHOPKS_MYTV(?,?,?,?,?,?,?) }", [
                        phanVungId, requestBody.p_donvi_id,vnhanvien_id,requestBody.p_nhomch_id,tu_ngay, den_ngay ,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [thongKeTongHop] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [thongKeTongHop] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> baoCaoTyLeChonTL(BaoCaoTyLeChonTraLoiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = requestBody.p_nhanvien_id
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [baoCaoTyLeChonTL] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())
            String tu_ngay = formatter.format(requestBody.p_tungay)
            String den_ngay =formatter.format(requestBody.p_denngay)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_BAOCAO_TYLE_CHON_TRALOI(?,?,?,?,?,?,?,?) }", [
                        phanVungId, requestBody.p_cauhoi_id,requestBody.p_donvi_id, vnhanvien_id,requestBody.p_nhomch_id,tu_ngay,den_ngay ,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [baoCaoTyLeChonTL] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [baoCaoTyLeChonTL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    ///
    @Override
    ResponseEntity<BaseResponse<List>> baoCaoKHDaKS(BaoCaoKHDaKSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            List data = []
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = requestBody.p_nhanvien_id
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [baoCaoKHDaKS] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())
            String tu_ngay = formatter.format(requestBody.p_tungay)
            String den_ngay =formatter.format(requestBody.p_denngay)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_BAOCAO_KHACHHANG_DAKS(?,?,?,?,?,?,?) }", [
                        phanVungId,requestBody.p_donvi_id, vnhanvien_id,requestBody.p_nhomch_id,tu_ngay, den_ngay ,CURSOR_PARAMETER]) { ResultSet rs ->
                    if( rs != null){
                        data = resultSetToObject.getDynamicData(rs)
                    }
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [baoCaoKHDaKS] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [baoCaoKHDaKS] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<Map<String,Object>>> BaoCaoChiTietKS(BaoCaoChiTietKSRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String,Object> data = new HashMap<>()
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            String dataSrc = session_.get("css_sch")
            loggingManaged.info("[TienIchVaHoTroController] - [BaoCaoChiTietKS] - Request: ", new ImmutableMap.
                    Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", requestBody)
                    .build())

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable {sql ->
                sql.call("{ call CSS.PKG_KHTN_3B.SP_BAOCAO_CHITIET_KHAOSAT(?,?,?,?,?) }", [
                        phanVungId,requestBody.p_tbtl_id, requestBody.p_nhomch_id, CURSOR_PARAMETER, CURSOR_PARAMETER]) { ResultSet rsThongTinKH, rsTraLoi->
                        data.p_thongtin_kh = resultSetToObject.getDynamicData(rsThongTinKH)
                        data.p_traloi = resultSetToObject.getDynamicData(rsTraLoi)
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [BaoCaoChiTietKS] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [BaoCaoChiTietKS] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> phanQuyenBacCaoKSMytv() {
        String requestId = genericApi.generateRequestId()
        try{
            Map<String, Object> session_ = genericApi.getSessionData(request)
            Integer phanVungId = Integer.parseInt(session_.get("id_tinhthanh"))
            Integer vnhanvien_id = Integer.parseInt(session_.get("id_nhanvien_dhsx"))
            String dataSrc = session_.get("css_sch")
            String data = ""
            String sqlString = "{ ? = call css.pkg_khtn_3b.fn_phanquyen_baocaoks_mytv(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, vnhanvien_id ]) { String rs ->
                    data = rs
                }
            }

            loggingManaged.info("[TienIchVaHoTroController] - [phanQuyenBacCaoKSMytv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", data != null ? new JsonBuilder(data) : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
        }catch (Exception e) {
            loggingManaged.error("[TienIchVaHoTroController] - [phanQuyenBacCaoKSMytv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of( "Thất bại", requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
