package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import com.google.gson.Gson
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.CSKHApi
import io.swagger.model.dto.DanhMuc
import io.swagger.model.dto.FileApp
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class CSKHController implements CSKHApi {
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
    DataUtils dataUtils

    @Autowired
    ResultSetToObject resultSetToObject

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    ZaloClient zaloClient

    @Autowired
    CSKHController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }


    @Override
    ResponseEntity<BaseResponse<List<PhieuCSKHResponse>>> layDsPhieuCSKH() {
        String requestId = genericApi.generateRequestId()
        List<PhieuCSKHResponse> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsPhieuCSKH] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.SP_LAY_DS_PHIEUCSKH_SMP(?,?,?) }"
//
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, PhieuCSKHResponse.class)
                    }
                }
            }

            loggingManaged.info("[CSKHController] - [layDsPhieuCSKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsPhieuCSKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Object>>> layDsTacNghiep(Long phieutnId, Integer kieu) {
        String requestId = genericApi.generateRequestId()
        List<Object> response = []
        try {
            if (phieutnId == null || kieu == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsTacNghiep] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieutnId", phieutnId)
                            .put("kieu", kieu)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.SP_LAY_DS_TACNGHIEP_CSKH_SMP(?,?,?,?) }"
//
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieutnId, kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        if (kieu == 1) {
                            long loainc_id = rs.getLong("LOAINC_ID")
                            switch (loainc_id) {
                                case 20:
                                    CSKHResponse cskhResponse = new CSKHResponse().convertFromDatabase(rs)
                                    if (cskhResponse.mota != null && !cskhResponse.mota.isEmpty()) {
                                        cskhResponse.listImage = getFileUrls(cskhResponse.mota)
                                    }
                                    response.add(cskhResponse)
                                    break
                                case 23:
                                    ThuThapTTLHResponse thuThapTTLHResponse = new ThuThapTTLHResponse().convertFromDatabase(rs)
                                    if (thuThapTTLHResponse.mota != null && !thuThapTTLHResponse.mota.isEmpty()) {
                                        thuThapTTLHResponse.listImage = getFileUrls(thuThapTTLHResponse.mota)
                                    }
                                    response.add(thuThapTTLHResponse)
                                    break
                                case 178:
                                    CSKH36Response cskh36Response = new CSKH36Response().convertFromDatabase(rs)
                                    if (cskh36Response.mota != null && !cskh36Response.mota.isEmpty()) {
                                        cskh36Response.listImage = getFileUrls(cskh36Response.mota)
                                    }
                                    response.add(cskh36Response)
                                    break
                            }
                        } else if (kieu == 2) {
                            TuVanDungThuMyTVResponse tuVanDungThuMyTVResponse = new TuVanDungThuMyTVResponse().convertFromDatabase(rs)
                            if (tuVanDungThuMyTVResponse.mota != null && !tuVanDungThuMyTVResponse.mota.isEmpty()) {
                                tuVanDungThuMyTVResponse.listImage = getFileUrls(tuVanDungThuMyTVResponse.mota)
                            }
                            response.add(tuVanDungThuMyTVResponse)
                        }
                    }
                }
            }

            loggingManaged.info("[CSKHController] - [layDsTacNghiep] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsTacNghiep] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<Object>>> layDsDanhMucCSKH36(Integer loai, Long param) {
        String requestId = genericApi.generateRequestId()
        List<Object> response = []
        try {
            // loai = 1 - param: null : Lấy danh sách hình thức khảo sát
            // loai = 2 - param: null : Lấy danh sách mục Cài App MyVNPT và VNPT Pay (Chỉ dùng cho KH Cá nhân)
            // loai = 4 - param: null : Lấy danh sách Chất lượng DV
            // loai = 5 - param: loaimxh_id : Lấy danh sách mục đích sử dụng MXH
            // loai = 6 - param: null : Lấy danh sách mục đích sử dụng Email
            // loai = 7 - param: PHIEUTN_ID : Lấy danh sách sticker báo hỏng đã chụp

            if (loai == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsDanhMucCSKH36] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("loai", loai)
                            .put("param", param != null ? param : "")
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call CSS.LAY_DANH_MUC_CSKH36T(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), loai, param, null, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        if (loai == 5 || loai == 6) {
                            response.add(new DanhMuc(
                                    ID: rs.getLong("mucdich_id"),
                                    NAME: rs.getString("mucdich_sd")
                            ))
                        } else if (loai == 7) {
                            response.add(new StickerBHResponse(
                                    phieu_id: rs.getLong("phieu_id"),
                                    sticker_bh: rs.getString("sticker_bh"),
                                    listImage: getFileUrls(rs.getString("sticker_bh"))
                            ))
                        } else {
                            response.add(new DanhMuc(
                                    ID: rs.getLong("ID"),
                                    NAME: rs.getString("TEN")
                            ))
                        }

                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsDanhMucCSKH36] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsDanhMucCSKH36] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatStickerBH(CapNhatStickerBHRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [capNhatStickerBH] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.CAPNHAT_ANH_STICKERBH(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.phieutn_id, 0, requestBody.sticker_bh, Sql.VARCHAR]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [capNhatStickerBH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [capNhatStickerBH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatThongTinDiaChi(CapNhatThongTinDiaChiRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [capNhatThongTinDiaChi] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String sqlString = "{ ? = call CSS.PKG_CSKH_36T.CAPNHAT_DIACHI_CSKH36T(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.thuebao_id,
                                     requestBody.diachi, requestBody.sonha, requestBody.quan_id, requestBody.phuong_id,
                                     requestBody.pho_id, requestBody.khu_id, requestBody.ap_id, requestBody.dacdiem_id,
                                     requestBody.kinhdo, requestBody.vido, requestBody.ghichu, mayCn, ipCn, 18, nguoiCn]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [capNhatThongTinDiaChi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [capNhatThongTinDiaChi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> luuThongTinCSKH36(LuuThongTinCSKH36Request requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [luuThongTinCSKH36] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            dataUtils.init(phanVungId, dataSrc)
            List<DS_DB> ds_db = []
            List<DS_MXH> ds_mxh = []
            List<DS_MD> ds_md = []
            List<DS_MD_EMAIL> ds_md_email = []
//            Long vttlh_id = requestBody?.obj?.lh?.thongtin?.ttlh_id
//            String vten_lh = requestBody?.obj?.lh?.thongtin?.ten_lh
//            String vso_dt = requestBody?.obj?.lh?.thongtin?.dienthoai
//            String vemail = requestBody?.obj?.lh?.thongtin?.email

            if (requestBody?.obj?.lh?.loai != null && requestBody?.obj?.lh?.loai?.size() > 0) {
                for (LOAI_LH item in requestBody.obj.lh.loai) {
                    if (item.ischeck) {
                        ds_db.add(new DS_DB(DB_ID: item?.db_id, TTKH_ID: item?.ttkh_id))
                    }
                }
            }

            if (requestBody?.obj?.lh?.lst_email != null && requestBody?.obj?.lh?.lst_email?.size() > 0) {
                for (MUCDICH_LH item in requestBody?.obj?.lh?.lst_email) {
                    if (item.ischeck) {
                        ds_md_email.add(new DS_MD_EMAIL(MUCDICH_ID: item?.mucdich_id))
                    }
                }
            }

            if (requestBody?.obj?.lh?.mxh != null && requestBody?.obj?.lh?.mxh?.size() > 0) {
                for (MANGXH_LH item in requestBody?.obj?.lh?.mxh) {
                    long key = dataUtils.GET_KEYS("HDLH")
                    ds_mxh.add(new DS_MXH(RLH_ID: key, LOAIMXH_ID: item?.loaimxh_id, MANG_XH: item?.nickname))
                    if (item?.lst_mucdich != null && item?.lst_mucdich?.size() > 0) {
                        for (MUCDICH_LH md_lh in item?.lst_mucdich) {
                            ds_md.add(new DS_MD(RLH_ID: key, MUCDICH_ID: md_lh?.mucdich_id))
                        }
                    }
                }
            }
            String j_dsdb = new JsonBuilder(ds_db)
            String j_ds_mxh = new JsonBuilder(ds_mxh)
            String j_md = new JsonBuilder(ds_md)
            String j_md_email = new JsonBuilder(ds_md_email)
            String sqlString = "{ ? = call CSS.LUU_LIENHE_36T(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.thuebao_id,
                                     requestBody?.obj?.lh?.thongtin?.dienthoai, "", requestBody?.obj?.lh?.thongtin?.email, j_md_email,
                                     j_ds_mxh, j_md, j_dsdb, 1, "Cập nhật thông tin LH 36T", requestBody?.obj?.lh?.thongtin?.ten_lh,
                                     mayCn, nguoiCn, Long.parseLong(nhanVienId)]) { String rs ->
                    response = rs
                }
            }
            if (response != null && response.toUpperCase().startsWith("OK")) {
                List<DS_SDT> ds_sdt = []
                if (requestBody?.obj?.ds_app != null && requestBody?.obj?.ds_app?.size() > 0) {
                    for (DS_APP item in requestBody?.obj?.ds_app) {
                        ds_sdt.add(new DS_SDT(SO_DT: item?.so_dk, LOAI_APP: item?.app?.id))
                    }
                }
                j_dsdb = new JsonBuilder(ds_sdt)
                sqlString = "{ ? = call CSS.LUU_PHIEU_CSKH_36T(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.khachhang_id,
                                         requestBody.thuebao_id, requestBody.hinhthuc_id, requestBody.chatluongdv_id, requestBody.is_baohong,
                                         requestBody.is_baoduong, j_dsdb, requestBody?.obj?.daidien?.hoten, requestBody?.obj?.daidien?.ngaysinh,
                                         requestBody?.obj?.daidien?.sodt, requestBody?.obj?.daidien?.email, requestBody?.obj?.ketoan?.hoten,
                                         requestBody?.obj?.ketoan?.sodt, requestBody?.obj?.ketoan?.email, requestBody?.obj?.it?.hoten,
                                         requestBody?.obj?.it?.sodt, requestBody?.obj?.it?.email, Long.parseLong(nhanVienId),
                                         mayCn, nguoiCn]) { String rs ->
                        response = rs
                    }
                }

            }
            loggingManaged.info("[CSKHController] - [luuThongTinCSKH36] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [luuThongTinCSKH36] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinKHDNResponse>>> layThongTinKHDN(Long doituongcsId, String makh) {
        String requestId = genericApi.generateRequestId()
        List<ThongTinKHDNResponse> response = []
        try {
            if (doituongcsId == null || makh == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHDN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("khachhangId", doituongcsId)
                            .put("makh", makh)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_THONGTIN_CHAMSOC(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), doituongcsId, makh, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, ThongTinKHDNResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHDN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinKHDN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ThongTinLienHeResponse>> layThongTinLienHe(Long hdkhId) {
        String requestId = genericApi.generateRequestId()
        ThongTinLienHeResponse response
        try {
            if (hdkhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHDN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("hdkhId", hdkhId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_THONGTIN_HDLH_36T(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), hdkhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        List<ThongTinLienHeResponse> listData = resultSetToObject.getListObject(rs, ThongTinLienHeResponse.class)
                        if (listData != null && !listData.isEmpty()) {
                            response = listData.get(0)
                        }
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHDN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            if (response != null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }

        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinKHDN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CaiAppResponse>>> layDsCaiApp(Long phieutnId) {
        String requestId = genericApi.generateRequestId()
        List<CaiAppResponse> response = []
        try {
            if (phieutnId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHDN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieutnId", phieutnId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHSACH_CAI_APP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieutnId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, CaiAppResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHDN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinKHDN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<MucDichEmailResponse>>> layThongTinMucDichEmail(Long hdlhId) {
        String requestId = genericApi.generateRequestId()
        List<MucDichEmailResponse> response = []
        try {
            if (hdlhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinMucDichEmail] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("hdlhId", hdlhId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_TT_MUCDICH_EMAIL(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), hdlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, MucDichEmailResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layThongTinMucDichEmail] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinMucDichEmail] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<MXHResponse>>> layDsMXH(Long hdlhId) {
        String requestId = genericApi.generateRequestId()
        List<MXHResponse> response = []
        try {
            if (hdlhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsMXH] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("hdlhId", hdlhId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHSACH_HDLH_MXH(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), hdlhId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, MXHResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsMXH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsMXH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinLienHeCapNhatChoResponse>>> layDsThongTinLienHeCapNhatCho(Long ttlh_id, Long thanhtoan_id, Long thuebao_id) {
        String requestId = genericApi.generateRequestId()
        List<ThongTinLienHeCapNhatChoResponse> response = []
        try {
            if (ttlh_id == null || thanhtoan_id == null || thuebao_id == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsThongTinLienHeCapNhatCho] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("ttlh_id", ttlh_id)
                            .put("thanhtoan_id", thanhtoan_id)
                            .put("thuebao_id", thuebao_id)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHSACH_GHEP_LIENHE(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), ttlh_id, thanhtoan_id, thuebao_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, ThongTinLienHeCapNhatChoResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsThongTinLienHeCapNhatCho] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsThongTinLienHeCapNhatCho] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ThongTinPhieuCSKH36Response>> layThongTinPhieuCSKH36(Long phieuId) {
        String requestId = genericApi.generateRequestId()
        ThongTinPhieuCSKH36Response response
        try {
            if (phieuId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinPhieuCSKH36] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieuId", phieuId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_TT_PHIEU_CSKH(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieuId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        List<ThongTinPhieuCSKH36Response> listData = resultSetToObject.getListObject(rs, ThongTinPhieuCSKH36Response.class)
                        if (listData != null && !listData.isEmpty()) {
                            response = listData.get(0)
                        }
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layThongTinPhieuCSKH36] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            if (response == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinPhieuCSKH36] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> hoanThienPhieuCSKH36(LuuThongTinCSKH36Request requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [hoanThienPhieuCSKH36] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            dataUtils.init(phanVungId, dataSrc)
            List<DS_DB> ds_db = []
            List<DS_MXH> ds_mxh = []
            List<DS_MD> ds_md = []
            List<DS_MD_EMAIL> ds_md_email = []

            if (requestBody?.obj?.lh?.loai != null && requestBody?.obj?.lh?.loai?.size() > 0) {
                for (LOAI_LH item in requestBody.obj.lh.loai) {
                    if (item.ischeck) {
                        ds_db.add(new DS_DB(DB_ID: item?.db_id, TTKH_ID: item?.ttkh_id))
                    }
                }
            }

            if (requestBody?.obj?.lh?.lst_email != null && requestBody?.obj?.lh?.lst_email?.size() > 0) {
                for (MUCDICH_LH item in requestBody?.obj?.lh?.lst_email) {
                    if (item.ischeck) {
                        ds_md_email.add(new DS_MD_EMAIL(MUCDICH_ID: item?.mucdich_id))
                    }
                }
            }

            if (requestBody?.obj?.lh?.mxh != null && requestBody?.obj?.lh?.mxh?.size() > 0) {
                for (MANGXH_LH item in requestBody?.obj?.lh?.mxh) {
                    long key = dataUtils.GET_KEYS("HDLH")
                    ds_mxh.add(new DS_MXH(RLH_ID: key, LOAIMXH_ID: item?.loaimxh_id, MANG_XH: item?.nickname))
                    if (item?.lst_mucdich != null && item?.lst_mucdich?.size() > 0) {
                        for (MUCDICH_LH md_lh in item?.lst_mucdich) {
                            ds_md.add(new DS_MD(RLH_ID: key, MUCDICH_ID: md_lh?.mucdich_id))
                        }
                    }
                }
            }
            String j_dsdb = new JsonBuilder(ds_db)
            String j_ds_mxh = new JsonBuilder(ds_mxh)
            String j_md = new JsonBuilder(ds_md)
            String j_md_email = new JsonBuilder(ds_md_email)
            String sqlString = "{ ? = call CSS.CAPNHAT_LIENHE_36T_V2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.thuebao_id,
                                     requestBody?.obj?.lh?.thongtin?.dienthoai, "", requestBody?.obj?.lh?.thongtin?.email, j_md_email,
                                     j_ds_mxh, j_md, j_dsdb, 1, "Cập nhật thông tin LH 36T", requestBody?.obj?.lh?.thongtin?.ten_lh,
                                     mayCn, nguoiCn, Long.parseLong(nhanVienId)]) { String rs ->
                    response = rs
                }
            }
            if (response != null && response.toUpperCase().startsWith("OK")) {
                List<DS_SDT> ds_sdt = []
                if (requestBody?.obj?.ds_app != null && requestBody?.obj?.ds_app?.size() > 0) {
                    for (DS_APP item in requestBody?.obj?.ds_app) {
                        ds_sdt.add(new DS_SDT(SO_DT: item?.so_dk, LOAI_APP: item?.app?.id))
                    }
                }
                j_dsdb = new JsonBuilder(ds_sdt)
                sqlString = "{ ? = call CSS.HOANTHIEN_PHIEU_CSKH_36T_V2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.khachhang_id,
                                         requestBody.thuebao_id, requestBody.hinhthuc_id, requestBody.chatluongdv_id, requestBody.is_baohong,
                                         requestBody.is_baoduong, j_dsdb, requestBody?.obj?.daidien?.hoten, requestBody?.obj?.daidien?.ngaysinh,
                                         requestBody?.obj?.daidien?.sodt, requestBody?.obj?.daidien?.email, requestBody?.obj?.ketoan?.hoten,
                                         requestBody?.obj?.ketoan?.sodt, requestBody?.obj?.ketoan?.email, requestBody?.obj?.it?.hoten,
                                         requestBody?.obj?.it?.sodt, requestBody?.obj?.it?.email, Long.parseLong(nhanVienId),
                                         mayCn, nguoiCn]) { String rs ->
                        response = rs
                    }
                }

            }
            loggingManaged.info("[CSKHController] - [hoanThienPhieuCSKH36] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [hoanThienPhieuCSKH36] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDanhMucCNKH() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDanhMucCNKH] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 0, 1, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("camnhankh_id"),
                                NAME: rs.getString("camnhankh")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDanhMucCNKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDanhMucCNKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhomNguyenNhanKHLResponse>>> layDsNhomNguyenNhanKHL() {
        String requestId = genericApi.generateRequestId()
        List<NhomNguyenNhanKHLResponse> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsNhomNguyenNhanKHL] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 0, 11, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, NhomNguyenNhanKHLResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsNhomNguyenNhanKHL] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsNhomNguyenNhanKHL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NguyenNhanKHLResponse>>> layDsNguyenNhanKHL(Long nhomnnId) {
        String requestId = genericApi.generateRequestId()
        List<NguyenNhanKHLResponse> response = []
        try {
            if (nhomnnId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsNguyenNhanKHL] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("nhomnnId", nhomnnId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), nhomnnId, 12, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, NguyenNhanKHLResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsNguyenNhanKHL] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsNguyenNhanKHL] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThueBaoKhaoSatResponse>>> layDsThueBaoKhaoSat(Long khachhangId, Long phieutnId, Long loaincId) {
        String requestId = genericApi.generateRequestId()
        List<ThueBaoKhaoSatResponse> response = []
        try {
            if (khachhangId == null || phieutnId == null || loaincId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsThueBaoKhaoSat] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("khachhangId", khachhangId)
                            .put("phieutnId", phieutnId)
                            .put("loaincId", loaincId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DS_THUEBAOKH_SMP_V2(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), khachhangId, phieutnId, loaincId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, ThueBaoKhaoSatResponse.class)
                    }
                }
            }
            if (response != null && response.size() > 0) {
                for (ThueBaoKhaoSatResponse item in response) {
                    if (item.mota != null && !item.mota.isEmpty()) {
                        item.listImage = getFileUrls(item.mota)
                    }
                    if (item.mota3g != null && !item.mota3g.isEmpty()) {
                        item.listImage3g = getFileUrls(item.mota3g)
                    }
                    if (item.mota4g != null && !item.mota4g.isEmpty()) {
                        item.listImage4g = getFileUrls(item.mota4g)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsThueBaoKhaoSat] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsThueBaoKhaoSat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsMangXaHoi() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsMangXaHoi] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 0, 2, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("loaimxh_id"),
                                NAME: rs.getString("loai_mxh")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsMangXaHoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsMangXaHoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinLienHeHopDongResponse>>> layThongTinLienHeHopDong(Long phieutnId, Integer kieu) {
        String requestId = genericApi.generateRequestId()
        List<ThongTinLienHeHopDongResponse> response = []
        try {
            if (phieutnId == null || kieu == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinLienHeHopDong] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieutnId", phieutnId)
                            .put("kieu", kieu)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieutnId, kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, ThongTinLienHeHopDongResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layThongTinLienHeHopDong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinLienHeHopDong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TTLHCapNhatChoResponse>>> layDsTTLHCapNhatCho(Long phieutnId, Long loaincId) {
        String requestId = genericApi.generateRequestId()
        List<TTLHCapNhatChoResponse> response = []
        try {
            if (phieutnId == null || loaincId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsTTLHCapNhatCho] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieutnId", phieutnId)
                            .put("loaincId", loaincId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            int kieu
            if (loaincId == 20) {
                kieu = 6
            } else if (loaincId == 23) {
                kieu = 7
            }
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieutnId, kieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, TTLHCapNhatChoResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsTTLHCapNhatCho] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsTTLHCapNhatCho] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatPhieuCSKH(CapNhatPhieuCSKHRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [capNhatPhieuCSKH] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String donViId = session_.get(ID_DONVI)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            Gson gson = new Gson()
            String jsLienHe = ""
            String jsKhaoSatDV = ""
            String jsToaDo = ""

            if (requestBody != null && requestBody.dslienhe != null) {
                jsLienHe = new JsonBuilder(requestBody.dslienhe)
            }
            if (requestBody != null && requestBody.dskhaosatdv != null) {
                jsKhaoSatDV = new JsonBuilder(requestBody.dskhaosatdv)
            }
            if (requestBody != null && requestBody.toado != null) {
                jsToaDo = new JsonBuilder(requestBody.toado)
            }

            String sqlString = "{ ? = call CSS.FN_CAPNHAT_PHIEU_CSKH(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.khachhang_id,
                                     requestBody.thanhtoan, requestBody.thuebao, jsLienHe, requestBody.ten_lh,
                                     requestBody.diachi, mayCn, nguoiCn, Long.parseLong(nhanVienId),
                                     Long.parseLong(donViId), requestBody.loainc_id, jsKhaoSatDV, jsToaDo]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [capNhatPhieuCSKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [capNhatPhieuCSKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> hoanThienPhieuCSKH(HoanThienPhieuCSKHRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [hoanThienPhieuCSKH] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)

            String sqlString = "{ ? = call CSS.FN_HOANTHIEN_PHIEU_CSKH(?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.khachhang_id,
                                     0, 1, 1, 1, Long.parseLong(nhanVienId), requestBody.loainc_id, mayCn, nguoiCn]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [hoanThienPhieuCSKH] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [hoanThienPhieuCSKH] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> capNhatZaloThanhToan(CapNhatZaloThanhToanRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [capNhatZaloThanhToan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.UPDATE_ZALO_THANHTOAN(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.phieutn_id, 4, requestBody.khong_smp,
                                     requestBody.so_dt, requestBody.quantam, nguoiCn, mayCn, ipCn, Sql.VARCHAR]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [capNhatZaloThanhToan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [capNhatZaloThanhToan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> checkZalo(String sdt, Integer kieu) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            if (sdt == null || kieu == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [checkZalo] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("sdt", sdt)
                            .put("kieu", kieu)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String oaKey = ""
            String sqlString = "{ call CSS.MOBILE_LAY_THONGTIN_OA(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null && rs.next()) {
                        oaKey = rs.getString("OAKey")
                    }
                }
            }
            if (kieu == 1) {//Kiểm tra số điện thoại đã quan tâm Zalo VNPT chưa
//                response = zaloClient.checkFollowOA(sdt, oaKey)
//                if (response != null && response != "0") {
//                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số điện thoại chưa quan tâm Zalo VNPT.", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
//                }
            } else {
                response = zaloClient.checkExistsZalo(sdt, oaKey)
                if (response != null && response != "0") {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Số điện thoại Zalo không tồn tại!", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                }
            }

            loggingManaged.info("[CSKHController] - [checkZalo] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response)
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [checkZalo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ZaloThanhToanResponse>>> layZaloThanhToan(Long phieutnId) {
        String requestId = genericApi.generateRequestId()
        List<ZaloThanhToanResponse> response = []
        try {
            if (phieutnId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layZaloThanhToan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieutnId", phieutnId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_ZALO_THANHTOAN(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieutnId, 4, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, ZaloThanhToanResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layZaloThanhToan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layZaloThanhToan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DichVuTiemNangResponse>>> layDsDichVuTiemNang(Long phieutnId) {
        String requestId = genericApi.generateRequestId()
        List<DichVuTiemNangResponse> response = []
        try {
            if (phieutnId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsDichVuTiemNang] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("phieutnId", phieutnId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DS_TIEMNANGDV_SMP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), phieutnId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, DichVuTiemNangResponse.class)
                    }
                }
            }
            if (response != null && !response.isEmpty()) {
                for (DichVuTiemNangResponse item in response) {
                    if (item.mota != null && !item.mota.isEmpty()) {
                        item.listImage = getFileUrls(item.mota)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsDichVuTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsDichVuTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DichVuResponse>>> layDsDichVu() {
        String requestId = genericApi.generateRequestId()
        List<DichVuResponse> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsDichVu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), null, 20, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, DichVuResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsDichVu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsDichVu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsTrangThaiDichVu() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsTrangThaiDichVu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), null, 22, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("trangthai_id"),
                                NAME: rs.getString("trangthai")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsTrangThaiDichVu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsTrangThaiDichVu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsNhaCungCap() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsNhaCungCap] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), null, 23, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("chuquan_id"),
                                NAME: rs.getString("tenchuquan")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsNhaCungCap] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsNhaCungCap] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsHinhThucThanhToan() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsHinhThucThanhToan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), null, 21, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("kieutt_id"),
                                NAME: rs.getString("kieu_tt")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsHinhThucThanhToan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsHinhThucThanhToan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsChatLuongDichVu() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsChatLuongDichVu] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), null, 24, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("chatluongdv_id"),
                                NAME: rs.getString("chatluong_dv")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsChatLuongDichVu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsChatLuongDichVu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsChungLoaiTV() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsChungLoaiTV] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), null, 25, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("chungloaitv_id"),
                                NAME: rs.getString("chungloai_tv")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsChungLoaiTV] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsChungLoaiTV] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Long>> capNhatDichVuTiemNang(CapNhatDichVuTiemNangRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [capNhatDichVuTiemNang] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String jsDiDong
            if (requestBody != null && requestBody.didongs != null) {
                jsDiDong = new JsonBuilder(requestBody.didongs)
            }

            String sqlString = "{ call CSS.FN_CAPNHAT_DICHVU_TIEMNANG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.tiemnangdv_id, requestBody.phieutn_id, requestBody.khachhang_id,
                                     requestBody.loaitb_id, requestBody.trangthai_id, requestBody.chungloaitv_id, requestBody.modeltv,
                                     requestBody.nammuatv, requestBody.chuquan_id, requestBody.chatluongdv_id, requestBody.tocdo_up,
                                     requestBody.tocdo_down, requestBody.kieu_tt, requestBody.thang_ktdc, requestBody.mota,
                                     requestBody.ghichu, jsDiDong, Sql.VARCHAR]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [capNhatDichVuTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            if (response != null && response.toUpperCase().startsWith("OK")) {
                String[] strSplit = response.split("-")
                if (strSplit != null && strSplit.size() == 2) {
                    Long tiemnangdv_id = Long.parseLong(strSplit[1]?.trim())
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(tiemnangdv_id, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
                } else {
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
                }
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [capNhatDichVuTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> xoaDichVuTiemNang(Long tiemnangDvId) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            if (tiemnangDvId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [xoaDichVuTiemNang] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("tiemnangDvId", tiemnangDvId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.DEL_TIEMNANGDV_SMP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), tiemnangDvId, Sql.VARCHAR]) { String rs ->
                    response = rs
                }
            }
            loggingManaged.info("[CSKHController] - [xoaDichVuTiemNang] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            if (response != null && response.toUpperCase() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [xoaDichVuTiemNang] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<ThongTinKHTNResponse>> layThongTinKHTN(Long khachhangId) {
        String requestId = genericApi.generateRequestId()
        ThongTinKHTNResponse response
        try {
            if (khachhangId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layThongTinKHTN] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("khachhangId", khachhangId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_TT_KH_CSKH_KHTN_SMP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), khachhangId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        List<ThongTinKHTNResponse> listData = resultSetToObject.getListObject(rs, ThongTinKHTNResponse.class)
                        if (listData != null && !listData.isEmpty()) {
                            response = listData.get(0)
                        }
                    }
                }
            }

            loggingManaged.info("[CSKHController] - [layThongTinKHTN] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            if (response == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layThongTinKHTN] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsKetQuaTuVan() {
        String requestId = genericApi.generateRequestId()
        List<DanhMuc> response = []
        try {
            loggingManaged.info("[CSKHController] - [layDsKetQuaTuVan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DANHMUC_CSKH_SMP(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), 0, 16, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs != null && rs.next()) {
                        response.add(new DanhMuc(
                                ID: rs.getLong("tttn_id"),
                                NAME: rs.getString("trangthai_tn")
                        ))
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsKetQuaTuVan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsKetQuaTuVan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<KetQuaTuVanResponse>> capNhatKetQuaTuVan(CapNhatKetQuaTuVanRequest requestBody) {
        String requestId = genericApi.generateRequestId()
        KetQuaTuVanResponse response
        try {
            loggingManaged.info("[CSKHController] - [capNhatKetQuaTuVan] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String donViId = session_.get(ID_DONVI)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.CAPNHAT_KQ_TVDT_MYTV(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.phieutn_id, requestBody.tttn_id, requestBody.ghichu,
                                     Long.parseLong(nhanVienId), Long.parseLong(donViId), nguoiCn, mayCn,
                                     ipCn, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        List<KetQuaTuVanResponse> listData = resultSetToObject.getListObject(rs, KetQuaTuVanResponse.class)
                        if (listData != null && !listData.isEmpty()) {
                            response = listData.get(0)
                        }
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [capNhatKetQuaTuVan] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            if (response != null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [capNhatKetQuaTuVan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DichVuDiDongResponse>>> layDsDichVuDiDong(Long tiemnangdvId) {
        String requestId = genericApi.generateRequestId()
        List<DichVuDiDongResponse> response = []
        try {
            if (tiemnangdvId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[CSKHController] - [layDsDichVuDiDong] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("tiemnangdvId", tiemnangdvId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.LAY_DS_TNDVDIDONG_SMP(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), tiemnangdvId, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs != null) {
                        response = resultSetToObject.getListObject(rs, DichVuDiDongResponse.class)
                    }
                }
            }
            loggingManaged.info("[CSKHController] - [layDsDichVuDiDong] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? new JsonBuilder(response) : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [layDsDichVuDiDong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> chitietThongTinKHTTTB(ChiTietThongTinKHTTTBRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [chitietThongTinKHTTTB] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.pkg_thaydoi_thongtin.SP_LAY_CT_THONGTIN_THUEBAO(?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.p_db_id, requestBody.p_loai, requestBody.p_kieu, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [chitietThongTinKHTTTB] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [chitietThongTinKHTTTB] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> CapNhatHDTDTTTheoThueBao(CapNhatHDTDTTTheoThueBaoRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [CapNhatHDTDTTTheoThueBao] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String donViId = session_.get(ID_DONVI)
            String token = session_.get(TOKEN)

            String sqlString = "{ ? = call css.pkg_thaydoi_thongtin.FN_CAPNHAT_HD_TDTT_THEO_THUEBAO(?,?,?,?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.p_hdkh_id, requestBody.p_thuebao_id, Long.parseLong(nhanVienId), Long.parseLong(donViId),
                         requestBody.p_kieuld_id, requestBody.p_dulieu, nguoiCn, mayCn, ipCn]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[CSKHController] - [CapNhatHDTDTTTheoThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
//            response = "OK|8912498"
            if (response != null && response.toUpperCase().contains("OK")) {
                Integer p_hdtb_id = Integer.parseInt(response.toUpperCase().replace("OK|", ""))
                String _p_cf_in = "-1"
                String _p_cf_out = ""
                String _p_msg = ""
                List _p_srv = []
                List _p_ct_hd = []
                List _p_ct_nv = []
                List output = []
                def datas = new JsonSlurper().parseText(requestBody.p_dulieu)

                dataUtils.init(phanVungId, dataSrc)
                boolean recall = true
                boolean wssuccess = true
                String wsmsg = ""
                while (recall){
                    Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                        String sqlString_item = "{ call css.sp_hoanthien_hoso_tdtt (?,?,?,?,?,?,?,?,?,?,?,?,?) }"
                        sql.call(sqlString_item,
                                [phanVungId, p_hdtb_id, nhanVienId, donViId, mayCn, ipCn, nguoiCn, _p_cf_in, Sql.VARCHAR, Sql.VARCHAR, CURSOR_PARAMETER, CURSOR_PARAMETER, CURSOR_PARAMETER]) {
                            String p_cf_out, String p_msg, ResultSet p_srv, ResultSet p_ct_hd, ResultSet p_ct_nv ->
                                _p_cf_out = p_cf_out
                                _p_msg = p_msg
                                _p_srv = resultSetToObject.getDynamicData(p_srv)
                                _p_ct_hd = resultSetToObject.getDynamicData(p_ct_hd)
                                _p_ct_nv = resultSetToObject.getDynamicData(p_ct_nv)
                        }
                    }
                    if (!wssuccess){
                        return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, wsmsg, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
                    }
                    def cf_out = _p_cf_out.split("|")
                    recall = false
                    switch (_p_msg){
                        case "ONEAPP_CALL_WEBSERVICE|ThayDoi_DoiTuong_Visa":
                            String doituong_moi = datas.DOITUONG_ID
                            String account = datas.MA_TB
                            String cusType = dataUtils.MAP_ID("doituong_id_vdc", "css.doituong", "where doituong_id = " + doituong_moi)
                            visaService.init(token)
                            wsmsg = visaService.changeCustomerType(account, new Date(), Integer.parseInt(cusType), null, null, null)
                            if (wsmsg == "OK"){
                                _p_cf_in = cf_out[0]
                            }else{
                                _p_cf_in = cf_out[1]
                                wssuccess = false
                            }
                            recall = true
                            break
                        case "ONEAPP_CALL_WEBSERVICE|ChangeSubscriber":
                            def data_srv = _p_srv[0]
                            //Chưa test nên chưa biết lấy được những gì từ hàm DB ra, chưa unit test
                            String ten_kh = data_srv.ten_kh
                            String ngaysn_kh = data_srv.ngaysn_kh
                            String sex = data_srv.sex
                            String FirstAdd = data_srv.FirstAdd
                            String SecondAdd = data_srv.SecondAdd
                            String IDNo = data_srv.IDNo
                            String cellno = data_srv.cellno
                            String TelNo = data_srv.TelNo
                            String email = data_srv.email
                            String YM = data_srv.YM
                            String website = data_srv.website
                            String faxno = data_srv.faxno
                            String IPTVAccount = data_srv.IPTVAccount

                            String tinh_id = data_srv.tinh_id
                            String phuong_id = data_srv.phuong_id
                            String pho_id = data_srv.pho_id
                            vascService.init(token)
                            vascService.init(dataSrc, phanVungId, nhanVienId)
                            wsmsg = vascService.changeSubscriber_v2(ten_kh, ngaysn_kh, sex, FirstAdd, SecondAdd, IDNo, cellno, TelNo,
                                    email, YM, website, faxno, IPTVAccount, tinh_id, phuong_id, pho_id)
                            if (wsmsg == "OK"){
                                _p_cf_in = cf_out[0]
                            }else{
                                _p_cf_in = cf_out[1]
                                wssuccess = false
                            }
                            recall = true
                            break
                    }
                }
                if (_p_msg == "OK")
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.SUCCESS, ErrorCodeDefine.SUCCESS))
                else
                    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, _p_msg, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, response, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [CapNhatHDTDTTTheoThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachTaiKhoanChinh(DanhSachTaiKhoanChinhRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachTaiKhoanChinh] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.HT_DS_TB_CHINH(?,?,?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vkieu, requestBody.vma_tb, requestBody.vUpper, requestBody.vtocdo_id,
                         requestBody.vkhachhang_id, requestBody.vhdkh_id, requestBody.vdiachi_ld, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachTaiKhoanChinh] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachTaiKhoanChinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachMucCuocThueBao(DanhSachMucCuocThueBaoRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachMucCuocThueBao] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.LAY_DS_MUCUOC_TB(?,?,?,?,?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vkhuld_id, requestBody.vapld_id, requestBody.vphold_id, requestBody.vphuongld_id,
                         requestBody.vloaitb_id, requestBody.vdoituong_id, requestBody.vtocdo_id, requestBody.vmuccuoc_id, requestBody.vngay, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachMucCuocThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachMucCuocThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachMucCuocThueBao2(DanhSachMucCuocThueBaoRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachMucCuocThueBao2] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.pkg_thaydoi_thongtin.LAY_DS_MUCUOC_TB(?,?,?,?,?,?,?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vkhuld_id, requestBody.vapld_id, requestBody.vphold_id, requestBody.vphuongld_id,
                         requestBody.vloaitb_id, requestBody.vdoituong_id, requestBody.vtocdo_id, requestBody.vmuccuoc_id, requestBody.vngay, requestBody.vmuccuoctb_id, requestBody.vkieuld_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachMucCuocThueBao2] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachMucCuocThueBao2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> ComboDSMaDaiDienTheoThanhToan(ComboDSMaDaiDienTheoThanhToanRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [ComboDSMaDaiDienTheoThanhToan] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.HT_COMBO_MA_DAIDIEN(?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.p_thanhtoan_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [ComboDSMaDaiDienTheoThanhToan] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [ComboDSMaDaiDienTheoThanhToan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<?>> ChuanhoaKhacHangB2A(ChuanhoaKhacHangB2ARequest requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [ChuanhoaKhacHangB2A] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)
            String donViId = session_.get(ID_DONVI)

            String sqlString = "{ ? = call css.b2a.fn_chuanhoa_khachhang_b2a(?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [Sql.VARCHAR, Long.parseLong(phanVungId), requestBody.v_khachhang_id]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[CSKHController] - [ChuanhoaKhacHangB2A] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [CapNhatHDTDTTTheoThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachSuCoTacDongMangLuoi(DanhSachSuCoTacDongMangLuoiRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call qlsc.sp_lay_ds_suco_tdml(?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vphanloai, requestBody.vtungay, requestBody.vdenngay, requestBody.vtrangthai, requestBody.vnoidung_tk, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }


    @Override
    ResponseEntity<BaseResponse<List>> DanhSachThueBaoBiAnhHuong(DanhSachThueBaoBiAnhHuongRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachThueBaoBiAnhHuong] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call qlsc.PKG_DANHMUC.SP_LAY_DS_THUEBAO_BI_AH(?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vsuco_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachThueBaoBiAnhHuong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachThueBaoBiAnhHuong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachSuCoTheoMaTB(DanhSachSuCoTheoMaTBRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachSuCoTheoMaTB] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call QLSC.sp_lay_suco_theo_ma_tb(?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.maTB, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachSuCoTheoMaTB] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachSuCoTheoMaTB] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachDichVuVienThong(DanhSachDichVuVienThongRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachDichVuVienThong] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.webapi_ex.Post_List_v2 (?,?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, "DANHSACH_DICHVU_VT", requestBody.p_para1, requestBody.p_para2, requestBody.p_para3, requestBody.p_para4, requestBody.p_para5, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachDichVuVienThong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachDichVuVienThong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachLoaiHinhThueBao(DanhSachLoaiHinhThueBaoRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachLoaiHinhThueBao] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.webapi_ex.Post_List_v2 (?,?,?,?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, "DANHSACH_LOAIHINH_TB", requestBody.p_para1, requestBody.p_para2, requestBody.p_para3, requestBody.p_para4, requestBody.p_para5, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachLoaiHinhThueBao] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachLoaiHinhThueBao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> HienThiThongSoKyThuat(HienThiThongSoKyThuatRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [HienThiThongSoKyThuat] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call baohong.ht_thongso_kythuat (?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.p_thuebao_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [HienThiThongSoKyThuat] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [HienThiThongSoKyThuat] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachThueBaoLapKem(DanhSachThueBaoLapKemRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachThueBaoLapKem] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call CSS.TRACUU_DANHBA.LAY_DS_TB_CUNG_MADOICAP (?,?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vma_tb, requestBody.vdichvuvt_id, requestBody.vkieu, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachThueBaoLapKem] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachThueBaoLapKem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> DanhSachLichSuHong(DanhSachLichSuHongRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [DanhSachLichSuHong] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call baohong.BAOHONG_119.Lay_ds_baohong_theo_ma_tb (?,?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.vma_tb, requestBody.vdichvuvt_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [DanhSachLichSuHong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [DanhSachLichSuHong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> NguyenNhanHong(NguyenNhanHongRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [NguyenNhanHong] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call baohong.BAOHONG_119.sp_lay_ct_hong (?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.p_baohong_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [NguyenNhanHong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [NguyenNhanHong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> ChiTietTonMayHong(ChiTietTonMayHongRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [ChiTietTonMayHong] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call baohong.BAOHONG_119.sp_lay_ct_ton_mayhong (?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.p_baohong_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [ChiTietTonMayHong] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [ChiTietTonMayHong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> NhanVienThucHien(NhanVienThucHienRequest requestBody) {
        List data = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[CSKHController] - [NhanVienThucHien] - Request: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)
            String mayCn = session_.get(DEVICE_ID)
            String ipCn = session_.get(DEVICE_IP)

            String sqlString = "{ call baohong.BAOHONG_119.sp_lay_ds_nhanvien_th (?,?,?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString,
                        [phanVungId, requestBody.p_baohong_id, CURSOR_PARAMETER]) {
                    ResultSet rs ->
                        data = resultSetToObject.getDynamicData(rs)
                }
            }

            loggingManaged.info("[CSKHController] - [NhanVienThucHien] - Response: ",
                    new ImmutableMap.Builder<String, Object>().put("request_id", requestId).put("result", data != null && data.size() > 0 ? data.toString() : "").build())
            if (data.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [NhanVienThucHien] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> CapNhatVatTuSuDungVt(FN_CAPNHAT_VATTU_SUDUNG_VT_REQUEST requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [CapNhatVatTuSuDungVt] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)

            String sqlString = "{ ? = call QLVT.FN_CAPNHAT_VATTU_SUDUNG_VT(?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.vsdvt_id, requestBody.vthuebao_id,
                                     requestBody.vserial, requestBody.vvattu_id, requestBody.vthietbi_id, requestBody.vmay_cn,
                                     requestBody.vip_cn, nguoiCn]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[CSKHController] - [CapNhatVatTuSuDungVt] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))

        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [CapNhatVatTuSuDungVt] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<Void>> CapNhatDoiTbiSerial(FN_CAPNHAT_DOI_TBI_SERIAL_REQUEST requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [CapNhatDoiTbiSerial] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String nguoiCn = session_.get(USERNAME)

            String sqlString = "{ ? = call QLVT.FN_CAPNHAT_DOI_TBI_SERIAL(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, phanVungId, requestBody.vsdvt_id, requestBody.vserial_cu,
                                     requestBody.vserial_moi, requestBody.vserial_gp_moi,requestBody.vmay_cn, nguoiCn,
                                     requestBody.vip_cn]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[CSKHController] - [CapNhatDoiTbiSerial] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))
            
        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [CapNhatDoiTbiSerial] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Value("\${spring.object-storages.minio-storage.bucket}")
    String bucketName

    private List<FileApp> getFileUrls(String moTa) {
        List<FileApp> files = new ArrayList<>()
        String baseDHSX = Constant.BASE_IMAGE_CAB
        if (moTa != null && moTa.trim().length() > 0 && Utils.isXMLLike(moTa)) {
            List<FileApp> tmp = Utils.getUrlFromXML(moTa)
            for (FileApp file : tmp) {
                String urlTmp = file.getUrl();
                if (urlTmp.contains(Constant.MINIO_STORAGE_KEY)) {
                    urlTmp = objectStoragesManaged.getUrl(urlTmp, bucketName)
                } else if (urlTmp.indexOf("http") == 0) {
                    file.setUrl(urlTmp)
                } else {
                    //urlTmp = Constant.BASE_IMAGE_CAB + urlTmp
                    urlTmp = objectStoragesManaged.getUrl164(baseDHSX, urlTmp)
                }
                file.setUrl(urlTmp)
                files.add(file)
            }
        } else {
            if (moTa != null && moTa.trim().length() > 0) {
                if (moTa.contains("|")) {
                    String[] str = moTa.split("\\|")
                    for (String url : str) {
                        FileApp file = new FileApp()
                        file.setId(-1)
                        if (url.contains(Constant.MINIO_STORAGE_KEY)) {
                            url = objectStoragesManaged.getUrl(url, bucketName)
                        } else if (url.indexOf("http") == 0) {
                            file.setUrl(url)
                        } else {
                            //url = Constant.BASE_IMAGE_CAB + url
                            url = objectStoragesManaged.getUrl164(baseDHSX, url)
                        }
                        file.setUrl(url)
                        files.add(file)
                    }
                } else {
                    FileApp file = new FileApp()
                    file.setId(-1)
                    String url = moTa
                    if (url.contains(Constant.MINIO_STORAGE_KEY)) {
                        url = objectStoragesManaged.getUrl(url, bucketName)
                    } else if (url.indexOf("http") == 0) {
                        file.setUrl(url)
                    } else {
                        url = Constant.BASE_IMAGE_CAB + url
                    }
                    file.setUrl(url)
                    files.add(file)
                }
            }
        }
        return files
    }

    @Override
    ResponseEntity<BaseResponse<Void>> SntogponsnWs(SNTOGPONSN_WS_REQUEST requestBody) {
        String requestId = genericApi.generateRequestId()
        String response = ""
        try {
            loggingManaged.info("[CSKHController] - [SntogponsnWs] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("requestBody", new JsonBuilder(requestBody))
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String sqlString = "{ ? = call CSS.SNTOGPONSN_WS(?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Sql.VARCHAR, requestBody.vserial]) { String rs ->
                    response = rs
                }
            }

            loggingManaged.info("[CSKHController] - [SntogponsnWs] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", response != null ? response : "")
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(response))

        } catch (Exception e) {
            loggingManaged.error("[CSKHController] - [SntogponsnWs] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
}
