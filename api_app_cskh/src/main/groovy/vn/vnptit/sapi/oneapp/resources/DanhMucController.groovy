package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.json.JsonBuilder
import groovy.sql.OutParameter
import groovy.sql.Sql
import io.swagger.api.DanhMucApi
import io.swagger.model.dto.*
import io.swagger.model.request.ChonThietBiRequest
import io.swagger.model.request.PhieuXuLyRequest
import io.swagger.model.request.TraCuuDanhBaRequest
import io.swagger.model.response.BaseResponse
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.convert.ResultSetToObject
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.request.DichVuVTKieuYeuCauRequest
import vn.vnptit.sapi.oneapp.util.*

import javax.servlet.http.HttpServletRequest
import java.sql.ResultSet

import static vn.vnptit.sapi.oneapp.resources.GenericApi.*

@RestController
class DanhMucController implements DanhMucApi {

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
    DanhMucController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> danhMucDonViGet() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId();
        try {
            loggingManaged.info("[DanhMucController] - [danhMucDonViGet] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH);
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
            loggingManaged.error("[DanhMucController] - [danhMucDonViGet] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TiepNhanSuCo>>> dsTiepNhanSuCoTamThoi(String maSc, Long nguonScId) {
        List<TiepNhanSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            // Mặc định sẽ truyền lên nguồn sự cố bằng 1
            if (nguonScId == null) {
                nguonScId = 1
            }
            if (maSc == null) {
                maSc = ""
            }
            loggingManaged.info("[DanhMucController] - [dsTiepNhanSuCoTamThoi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("maSc", maSc)
                    .put("nguonScId", nguonScId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoiDungId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call QLSC.PKG_XULY_SUCO_MNV.SP_LAY_DS_SUCO_TAM_V2(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maSc, nguonScId, Long.parseLong(nguoiDungId),Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER,]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TiepNhanSuCo(
                                chon: rs.getString("CHON"),
//                                phanVungId: rs.getLong("PHANVUNG_ID"),
                                maSc: rs.getString("MA_SC"),
                                nguonScId: rs.getLong("NGUONSC_ID"),
                                nhomScId: rs.getLong("NHOMSC_ID"),
                                ngaySc: rs.getDate("NGAY_SC")?.format(Constant.FORMAT_DATETIME1),
                                noiDungPhanAnh: rs.getString("NOIDUNG_SC"),
                                noiDungSc: rs.getString("NOIDUNG_SC"),
                                ngayBh: rs.getDate("NGAY_BH")?.format(Constant.FORMAT_DATETIME1),
                                donViBhId: rs.getLong("DONVI_BH_ID"),
                                nhanVienBhId: rs.getLong("NHANVIEN_BH_ID"),
                                dienThoaiLh: rs.getString("DIENTHOAI_LH"),
                                ngayTn: rs.getDate("NGAY_TN")?.format(Constant.FORMAT_DATETIME1),
                                donViTnId: rs.getLong("DONVI_TN_ID"),
                                nhanVienTnId: rs.getLong("NHANVIEN_TN_ID"),
                                henSuaTu: rs.getDate("HENSUATU")?.format(Constant.FORMAT_DATETIME1),
                                henSuaDen: rs.getDate("HENSUADEN")?.format(Constant.FORMAT_DATETIME1),
                                ngayCn: rs.getDate("NGAY_CN")?.format(Constant.FORMAT_DATETIME1),
                                nguoiCn: rs.getString("NGUOI_CN"),
                                mayCn: rs.getString("MAY_CN"),
                                ipCn: rs.getString("IP_CN"),
                                moTa: rs.getString("MOTA"),
                                donViBh: rs.getString("DONVI_BH"),
                                donViTn: rs.getString("DONVI_TN"),
                                nhanVienBh: rs.getString("NHANVIEN_BH"),
                                nhanVienTn: rs.getString("NHANVIEN_TN"),
                                nhomSc: rs.getString("NHOM_SC"),
                                nguoiBh: rs.getString("NGUOI_BH"),
                                nguoiTn: rs.getString("NGUOI_TN"),
                                thoiGian: rs.getString("THOIGIAN"),
                                phanTuLoi: rs.getString("PHANTU_LOI"),
                                idPhanTuLoi : rs.getLong("PTM_ID"),
                                batDau: rs.getString("NGAY_BATDAU"),
                                ketThuc:rs.getString("NGAY_KETTHUC"),
                                flagGiaoViec:rs.getString("FLAG_GIAO_VIEC"),
                                dsIdPhanTuLoi:rs.getString("DS_IDPHANTULOI")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsTiepNhanSuCoTamThoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsTiepNhanSuCoTamThoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TiepNhanSuCo>>> dsTiepNhanSuCoTamThoiTheoNhanVien(String maSc, Long nguonScId) {
        List<TiepNhanSuCo> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            // Mặc định sẽ truyền lên nguồn sự cố bằng 1
            if (nguonScId == null) {
                nguonScId = 1
            }
            if (maSc == null) {
                maSc = ""
            }
            loggingManaged.info("[DanhMucController] - [dsTiepNhanSuCoTamThoiTheoNhanVien] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("maSc", maSc)
                    .put("nguonScId", nguonScId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nguoiDungId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call QLSC.PKG_XULY_SUCO_MNV.SP_LAY_DS_SUCO_TAM_THEO_NHAN_VIEN(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maSc, nguonScId,Long.parseLong(nguoiDungId), Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER,]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TiepNhanSuCo(
                                chon: rs.getString("CHON"),
//                                phanVungId: rs.getLong("PHANVUNG_ID"),
                                maSc: rs.getString("MA_SC"),
                                nguonScId: rs.getLong("NGUONSC_ID"),
                                nhomScId: rs.getLong("NHOMSC_ID"),
                                ngaySc: rs.getDate("NGAY_SC")?.format(Constant.FORMAT_DATETIME1),
                                noiDungPhanAnh: rs.getString("NOIDUNG_SC"),
                                noiDungSc: rs.getString("NOIDUNG_SC"),
                                ngayBh: rs.getDate("NGAY_BH")?.format(Constant.FORMAT_DATETIME1),
                                donViBhId: rs.getLong("DONVI_BH_ID"),
                                nhanVienBhId: rs.getLong("NHANVIEN_BH_ID"),
                                dienThoaiLh: rs.getString("DIENTHOAI_LH"),
                                ngayTn: rs.getDate("NGAY_TN")?.format(Constant.FORMAT_DATETIME1),
                                donViTnId: rs.getLong("DONVI_TN_ID"),
                                nhanVienTnId: rs.getLong("NHANVIEN_TN_ID"),
                                henSuaTu: rs.getDate("HENSUATU")?.format(Constant.FORMAT_DATETIME1),
                                henSuaDen: rs.getDate("HENSUADEN")?.format(Constant.FORMAT_DATETIME1),
                                ngayCn: rs.getDate("NGAY_CN")?.format(Constant.FORMAT_DATETIME1),
                                nguoiCn: rs.getString("NGUOI_CN"),
                                mayCn: rs.getString("MAY_CN"),
                                ipCn: rs.getString("IP_CN"),
                                moTa: rs.getString("MOTA"),
                                donViBh: rs.getString("DONVI_BH"),
                                donViTn: rs.getString("DONVI_TN"),
                                nhanVienBh: rs.getString("NHANVIEN_BH"),
                                nhanVienTn: rs.getString("NHANVIEN_TN"),
                                nhomSc: rs.getString("NHOM_SC"),
                                nguoiBh: rs.getString("NGUOI_BH"),
                                nguoiTn: rs.getString("NGUOI_TN"),
                                thoiGian: rs.getString("THOIGIAN"),
                                phanTuLoi: rs.getString("PHANTU_LOI"),
                                idPhanTuLoi : rs.getLong("PTM_ID"),
                                batDau: rs.getString("NGAY_BATDAU"),
                                ketThuc:rs.getString("NGAY_KETTHUC"),
                                flagGiaoViec:rs.getString("FLAG_GIAO_VIEC"),
                                dsIdPhanTuLoi:rs.getString("DS_IDPHANTULOI")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsTiepNhanSuCoTamThoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsTiepNhanSuCoTamThoi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @Override
    ResponseEntity<BaseResponse<ThongTinYeuCau>> chiTietYeuCau(Integer yeuCauId) {

        ThongTinYeuCau result = new ThongTinYeuCau()
        String requestId = genericApi.generateRequestId()

        try {
            if (yeuCauId == null) {
                return ResponseEntity.badRequest()
                        .body(new BaseResponse<>(requestId, "YEUCAU_ID_NULL", null))
            }

            loggingManaged.info("[DanhMucController] - [chiTietYeuCau] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("yeuCauId", yeuCauId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = (String) session_.get(CSS_SCH)

            String sqlString = "{ call QLSC.PKG_XULY_SUCO_MNV.GET_THONGTIN_YEUCAU(?,?) }"

            Map<String, ThietBiYcHoTRo> mapThietBi = [:]

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        yeuCauId,
                        CURSOR_PARAMETER
                ]) { ResultSet rs ->

                    while (rs.next()) {
                        if (result.maSc == null) {
                            result.maSc = rs.getLong("MA_SC")
                            result.donViId = rs.getLong("DONVI_ID")
                            result.tenDonVi = rs.getString("TEN_DV")
                            result.buocXuLy = rs.getString("TEN_BUOC_XULY")
                            result.maBuoc = rs.getLong("MA_BUOC")
                            result.noiDungDeXuat = rs.getString("ND_DEXUAT")

                            result.dsThietBi = []
                        }
                        String ten = rs.getString("TEN_PTM")
                        String congSoi = rs.getString("CONGSOI")
                        String key = "${ten}_${congSoi}"

                        if (ten != null && !mapThietBi.containsKey(key)) {

                            ThietBiYcHoTRo tb = new ThietBiYcHoTRo(
                                    tenThietBi: ten,
                                    congSoi: congSoi
                            )

                            mapThietBi.put(key, tb)
                            result.dsThietBi.add(tb)
                        }
                    }
                }
            }


            return ResponseEntity.ok().body(BaseResponseBuilder.of(result));

        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [chiTietYeuCau] - ERROR", e)

            return ResponseEntity.ok().body(
                    BaseResponseBuilder.of(null, requestId, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<YeuCauHoTro>>> dsYeuCauHoTro(String maSc, String fDate, String tDate, Integer trangThai) {

        List<YeuCauHoTro> ret = new ArrayList<>();
        String requestId = genericApi.generateRequestId();

        try {
            if (maSc == null) {
                maSc = "";
            }
            if(fDate == null){
                fDate =""
            }
            if(tDate == null) {
                tDate = ""
            }

            loggingManaged.info("[DanhMucController] - [dsYeuCauHoTro] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("maSc", maSc)
                            .put("fdate", fDate)
                            .put("tdate", tDate)
                            .put("trangThaiParam", trangThai)
                            .build());

            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = (String) session_.get(CSS_SCH)
            String phanVungId = String.valueOf(session_.get(ID_TINHTHANH))
            String nguoiDungId = String.valueOf(session_.get(ID_NHANVIEN_DHSX))

            String sqlString = "{ call QLSC.PKG_XULY_SUCO_MNV.SP_LAY_DS_HO_TRO(?,?,?,?,?,?,?) }";

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        maSc,
                        fDate,
                        tDate,
                        trangThai,
                        Long.parseLong(nguoiDungId),
                        Long.parseLong(phanVungId),
                        CURSOR_PARAMETER
                ]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new YeuCauHoTro(
                                maSc: rs.getString("MA_SC"),
                                ngayYc: rs.getString("NGAY_YC"),
                                noiDungDx: rs.getString("ND_GIAO"),
                                noiDungPh: rs.getString("GHICHU_TH"),
                                ngayPh: rs.getString("NGAY_TH"),
                                nguoiPh: rs.getLong("NHANVIEN_TH_ID"),
                                trangThai: rs.getString("TRANG_THAI"),
                                netMaqt: rs.getString("NET_MA_QT"),
                                maBuoc: rs.getString("MA_BUOC"),
                                phieuId: rs.getLong("PHIEU_ID"),
                                yeuCauId: rs.getLong("YEUCAU_ID"),
                                tenNguoiPh: rs.getString("TEN_NGUOIPH")
                        ));
                    }
                }
            }

            loggingManaged.info("[DanhMucController] - [dsYeuCauHoTro] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build());

            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret, requestId, "Success", ErrorCodeDefine.SUCCESS));

        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsYeuCauHoTro] - Error:", e);
            return ResponseEntity.ok().body(
                    BaseResponseBuilder.of(null, requestId, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR)
            );
        }
    }
    @Override
    ResponseEntity<BaseResponse<ThongTinPhanHoi>> chiTietPhanHoi(Integer yeuCauId) {

        ThongTinPhanHoi result = new ThongTinPhanHoi()
        String requestId = genericApi.generateRequestId()

        try {
            if (yeuCauId == null) {
                yeuCauId = ""
            }

            loggingManaged.info("[DanhMucController] - [chiTietPhanHoi] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("yeuCauId", yeuCauId)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = (String) session_.get(CSS_SCH)
            String phanVungId = String.valueOf(session_.get(ID_TINHTHANH))

            String sqlString = "{ call QLSC.PKG_XULY_SUCO_MNV.GET_THONGTIN_PHANHOI(?,?) }"

            Map<Long, ThongTinLoaiKiemTra> mapDoKiem = [:]
            Set<String> fileSet = []

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [
                        yeuCauId,
                        CURSOR_PARAMETER
                ]) { ResultSet rs ->

                    while (rs.next()) {

                        // ===== THÔNG TIN CHA =====
                        if (result.trangthai == null) {
                            result.trangthai = rs.getString("trangThai")
                            result.noiDungPhanHoi = rs.getString("noiDungPhanHoi")
                            result.ketQuaXuLy = rs.getString("ketQuaXuLy")

                            result.thongTinLoaiKiemTra = []
                            result.fileDinhKem = []
                        }

                        Long dkId = rs.getObject("id") != null ? rs.getLong("id") : null
                        if (dkId != null && !mapDoKiem.containsKey(dkId)) {

                            ThongTinLoaiKiemTra dk = new ThongTinLoaiKiemTra(
                                    id: dkId,
                                    parentId: rs.getObject("parentId") != null ? rs.getLong("parentId") : null,
                                    ngayTao: rs.getString("ngayTao"),
                                    stt: rs.getObject("stt") != null ? rs.getInt("stt") : null,
                                    loaiKiemTra: rs.getString("loaiKiemTra"),
                                    systemName: rs.getString("systemName"),
                                    cardName: rs.getString("cardName"),
                                    portName: rs.getString("portName"),
                                    txValue: rs.getString("txValue"),
                                    rxValue: rs.getString("rxValue"),
                                    rxTrongNguong: rs.getString("rxTrongNguong"),
                                    rxNgoaiNguong: rs.getString("rxNgoaiNguong"),
                                    crcResult: rs.getString("crcResult"),
                                    luuLuongPortResult: rs.getString("luuLuongPortResult"),
                                    ketQua: rs.getString("ketQua"),
                                    ghiChu: rs.getString("ghiChu")
                            )

                            mapDoKiem.put(dkId, dk)
                            result.thongTinLoaiKiemTra.add(dk)
                        }

                        String url = rs.getString("URL")
                        try {
                            if (url != null && !fileSet.contains(url)) {
                                String preURL = this.objectStoragesManaged.getPreURL(url);
                                fileSet.add(url)
                                result.fileDinhKem.add(preURL)
                            }
                        } catch (Exception ignore) {}

                    }
                }
            }

            loggingManaged.info("[DanhMucController] - [chiTietPhanHoi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", new JsonBuilder(result).toPrettyString())
                            .build())

            return ResponseEntity.ok().body(BaseResponseBuilder.of(result))

        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [chiTietPhanHoi] - Error:", e)
            return ResponseEntity.ok().body(
                    BaseResponseBuilder.of(null, requestId, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR)
            )
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDuLieu(Long kieu) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (kieu == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[DanhMucController] - [dsDuLieu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("kieu", kieu)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_DULIEU_KHONG_INPUT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [kieu, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        switch (kieu) {
                            case 1: // danh sách lý do trả phiếu sự cố
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("LYDOTRA_ID"),
                                        NAME: rs.getString("LYDOTRA")
                                ))
                                break
                            case 2: // Danh sách nhóm nguyên nhân
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("NHOMNN_ID"),
                                        NAME: rs.getString("NHOM_NN")
                                ))
                                break
                            case 3: // Danh sách nguồn sự cố
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("NGUONSC_ID"),
                                        NAME: rs.getString("MOTA")
                                ))
                                break
                            case 4: // Danh sách trạng thái phiếu
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("TRANGTHAI_ID"),
                                        NAME: rs.getString("TRANGTHAI")
                                ))
                                break
                            case 5: // Danh sách mức độ sự cố
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("MUCDO_ID"),
                                        NAME: rs.getString("MUCDO_SC"),
                                        CODE: rs.getString("CANHBAO_DT")
                                ))
                                break
                            case 11:// Danh sách lớp MC
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("LOPMC_ID"),
                                        NAME: rs.getString("LOP_MC")
                                ))
                                break
                            case 12: // Danh sách đơn vị
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("ID"),
                                        NAME: rs.getString("NAME"),
                                        CODE: rs.getString("PARENTID")
                                ))
                                break
                            case 13: // Danh sách nhóm thiết bị
                                ret.add(new DanhMuc(
                                        ID: rs.getLong("NHOMTBI_ID"),
                                        NAME: rs.getString("NHOM_TBI")
                                ))
                                break
                        }

                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsDuLieu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsDuLieu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDungLuongThietBi(Long loaiTbId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (loaiTbId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[DanhMucController] - [dsDungLuongThietBi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiTbId", loaiTbId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_DS_DUNGLUONG_TBI(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [loaiTbId, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("DUNGLUONG_ID"),
                                NAME: rs.getString("DUNGLUONG")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsDungLuongThietBi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsDungLuongThietBi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<KieuThietBi>>> dsKieuThietBi(Long loaiTbId) {
        List<KieuThietBi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (loaiTbId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[DanhMucController] - [dsKieuThietBi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiTbId", loaiTbId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_KIEU_TBI(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [loaiTbId, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new KieuThietBi(
                                id: rs.getString("KIEUTBI_ID"),
                                name: rs.getString("KIEU_TBI")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsKieuThietBi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsKieuThietBi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<LoaiThietBi>>> dsLoaiThietBi(Long nhomTbId) {
        List<LoaiThietBi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (nhomTbId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[DanhMucController] - [dsLoaiThietBi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("nhomTbId", nhomTbId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_LOAI_TBI(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhomTbId, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new LoaiThietBi(
                                loaiTbId: rs.getLong("LOAITBI_ID"),
                                loaiTb: rs.getString("LOAI_TBI"),
                                ghiChu: rs.getString("GHICHU"),
                                maLoaiTb: rs.getString("MA_LOAITBI"),
                                congThuc: rs.getString("CONGTHUC")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsLoaiThietBi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsLoaiThietBi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ChonThietBi>>> dsChonThietBi(ChonThietBiRequest requestBody) {
        List<ChonThietBi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhMucController] - [dsChonThietBi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_CHON_TBI(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [requestBody.tenTb, requestBody.nhomTbId, requestBody.loaiTbId,
                                     requestBody.kieuTbId, requestBody.donViId, requestBody.dungLuongId,
                                     requestBody.lopMcId, requestBody.phanLoaiKcId, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new ChonThietBi(
                                chon: rs.getString("CHON"),
                                loaiTbId: rs.getLong("LOAITBI_ID"),
                                thietBiId: rs.getLong("THIETBI_ID"),
                                tenTb: rs.getString("TEN_TBI"),
                                kieuTbId: rs.getString("KIEUTBI_ID"),
                                donViId: rs.getLong("DONVI_ID"),
                                kieuTbUid: rs.getString("KIEUTBI_UID")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsChonThietBi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsChonThietBi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TinhTrangXuLy>>> dsTinhTrangXuLy(Long suCoId, Long trangThai) {
        List<TinhTrangXuLy> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhMucController] - [dsTinhTrangXuLy] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("suCoId", suCoId)
                    .put("trangThai", trangThai)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_XULY(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, trangThai, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TinhTrangXuLy(
                                chon: rs.getString("CHON"),
                                suCoPtmId: rs.getLong("SUCO_PTM_ID"),
                                tinhTrang: rs.getString("TINHTRANG"),
                                loaiPtmId: rs.getLong("LOAIPTM_ID"),
                                ptmId: rs.getLong("PTM_ID"),
                                congSoi: rs.getString("CONGSOI"),
                                donViId: rs.getLong("DONVI_ID"),
                                donViNhan: rs.getString("DONVI_NHAN"),
                                kieuGiao: rs.getString("KIEU_GIAO"),
                                phieuId: rs.getLong("PHIEU_ID"),
                                ngayGiao: rs.getString("NGAYGIAO"),
                                ndGiao: rs.getString("ND_GIAO"),
                                ttphId: rs.getLong("TTPH_ID"),
                                ngayTra: rs.getString("NGAY_TRA"),
                                ndTra: rs.getString("ND_TRA"),
                                lyDoTra: rs.getString("LYDOTRA"),
                                khan: rs.getString("KHAN"),
                                chiTieuTg: rs.getString("CHITIEU_TG"),
                                conLai: rs.getString("CONLAI"),
                                ngayTh: rs.getString("NGAY_TH"),
                                nguoiTh: rs.getString("NGUOI_TH"),
                                ghiChuTh: rs.getString("GHICHU_TH")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsTinhTrangXuLy] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsTinhTrangXuLy] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<CayThietBi>>> dsCayThietBi(Long loaiTbId, Long thietBiId) {
        List<CayThietBi> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (loaiTbId == null || thietBiId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            loggingManaged.info("[DanhMucController] - [dsCayThietBi] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiTbId", loaiTbId)
                    .put("thietBiId", thietBiId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_CAY_THIETBI(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [loaiTbId, thietBiId, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new CayThietBi(
                                thietBiId: rs.getString("THIETBI_ID"),
                                thietBiChaId: rs.getString("THIETBI_CHA_ID"),
                                tenTb: rs.getString("TEN_TBI"),
                                hinhAnh: rs.getString("HINHANH"),
                                viTri: rs.getString("VITRI"),
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsCayThietBi] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsCayThietBi] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhomSuCoTheoNv(Long nhanVienId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhMucController] - [dsNhomSuCoTheoNv] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("loaiTbId", nhanVienId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_NHOM_SC_THEO_NV(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhanVienId, Long.parseLong(phanVungId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("NHOMSC_ID"),
                                NAME: rs.getString("NHOM_SC")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsNhomSuCoTheoNv] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsNhomSuCoTheoNv] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDieuKienTraCuu() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhMucController] - [dsDieuKienTraCuu] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "{ call CSS.webapi.Post_List(?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [null, 30, 2, 0, 0, 0, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("ID"),
                                NAME: rs.getString("NAME"),
                                CODE: rs.getString("VALUE")
                        ))
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [dsDieuKienTraCuu] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsDieuKienTraCuu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhBaBase>>> traCuuDanhBa(TraCuuDanhBaRequest requestBody) {
        List<DanhBaBase> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[DanhMucController] - [traCuuDanhBa] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId)
                    .put("requestBody", new JsonBuilder(requestBody).toPrettyString())
                    .build())
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI.TRA_CUU_DANH_BA(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), requestBody.id, requestBody.dieuKien, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        if (requestBody.id == 2) { // danh bạ khách hàng
                            ret.add(new DanhBaKhachHang().convertDataFromDatabase(rs))
                        } else if (requestBody.id == 3) { // danh bạ thanh toán
                            ret.add(new DanhBaThanhToan().convertDataFromDatabase(rs))
                        } else if (requestBody.id == 4) { // danh bạ thuê bao
                            ret.add(new DanhBaThueBao().convertDataFromDatabase(rs))
                        }
                    }
                }
            }
            loggingManaged.info("[DanhMucController] - [traCuuDanhBa] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null && ret.size() > 0 ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [traCuuDanhBa] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<KieuDoKiem>>> dsKieuDoKiem() {
        List<KieuDoKiem> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.sp_ds_kieudokiem(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new KieuDoKiem(
                                id: rs.getLong("ID"),
                                name: rs.getString("NAME"),
                                value: rs.getString("VALUE"),
                                shortName: rs.getString("SHORTNAME")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKieuXacThuc() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "{ call ecms.sp_kieu_xt(?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("KIEUXT_ID"),
                                NAME: rs.getString("KIEU_XT"),
                                CODE: rs.getString("TEN_HIENTHI")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsKieuMaHoa(Long kieuXtId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String sqlString = "{ call ecms.sp_kieu_mh(?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [kieuXtId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("KIEUMH_ID"),
                                NAME: rs.getString("KIEU_MH"),
                                CODE: rs.getString("GHICHU")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<NhanVien>>> dsNhanVien(String tenNv) {
        List<NhanVien> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_NHANVIEN(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [tenNv, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new NhanVien(
                                nhanVienId: rs.getLong("NHANVIEN_ID"),
                                tenNv: rs.getString("TEN_NV"),
                                chucDanh: rs.getString("CHUCDANH"),
                                viTri: rs.getString("VITRI"),
                                tenDv: rs.getString("TEN_DV"),
                                sdt: rs.getString("SO_DT"),
                                donViId: rs.getLong("DONVI_ID")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TinhTrangCntt>>> dsTinhTrangCntt(Long suCoId) {
        List<TinhTrangCntt> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_CNTT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TinhTrangCntt(
                                suCoPtmId: rs.getLong("SUCO_PTM_ID"),
                                ptmId: rs.getLong("PTM_ID"),
                                tenHt: rs.getString("TEN_HT"),
                                capDoId: rs.getLong("CAPDO_ID"),
                                ghiChu: rs.getString("GHICHU"),
                                dsTinhTrang: rs.getString("DS_TINHTRANG"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<TinhTrangMvt>>> dsTinhTrangMvt(Long suCoId) {
        List<TinhTrangMvt> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_TINHTRANG_MVT(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new TinhTrangMvt(
                                suCoPtmId: rs.getLong("SUCO_PTM_ID"),
                                loaiPtmId: rs.getLong("LOAIPTM_ID"),
                                ptmId: rs.getLong("PTM_ID"),
                                tenPtm: rs.getString("TEN_PTM"),
                                capDoId: rs.getLong("CAPDO_ID"),
                                ghiChu: rs.getString("GHICHU"),
                                loaiPtm: rs.getString("LOAI_PTM"),
                                congSoi: rs.getString("CONGSOI"),
                                dsTinhTrang: rs.getString("DS_TINHTRANG"),
                                dsTenTinhTrang: rs.getString("DS_TEN_TINHTRANG")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNguyenNhan(Long nhomNguyenNhanId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_NGUYENNHAN(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhomNguyenNhanId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("NGUYENNHAN_ID"),
                                NAME: rs.getString("NGUYENNHAN"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsHuongGiao(Long quyTrinhId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (quyTrinhId == null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, "Bạn vui lòng nhập dữ liệu đầu vào", ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)
            String sqlString = "{ call QLSC.SP_LAY_DS_HUONGGIAO(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [quyTrinhId, Long.parseLong(nhanVienId),
                                     Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("HUONGGIAO_ID"),
                                NAME: rs.getString("HUONGGIAO"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<PhieuXuLy>>> dsPhieuXuLy(PhieuXuLyRequest requestBody) {
        List<PhieuXuLy> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)

            String sqlString = "{ call QLSC.SP_LAY_DS_PHIEU_XULY_NET_V4(?,?,?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId,
                                     requestBody.nguonScId,
                                     requestBody.maSc,
                                     requestBody.maQuytrinh,
                                     "",
                                     requestBody.ngayGiao,
                                     requestBody.trangThai,
                                     requestBody.mucDoScId,
                                     requestBody.keoDai,
                                     requestBody.huongGiaoId,
                                     Long.parseLong(nhanVienId),
                                     CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new PhieuXuLy().convertDataFromDatabase(rs))
                    }
                }
            }
            /*
            if (requestBody.nguonScId == 6) {
                String sqlString = "{ call QLSC.SP_LAY_DS_PHIEU_XULY_NET_V3(?,?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [phanVungId, requestBody.nguonScId, requestBody.maSc, "", requestBody.ngayGiao,
                                         requestBody.trangThai, requestBody.mucDoScId, requestBody.keoDai,
                                         requestBody.huongGiaoId, Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                        while (rs.next()) {
                            ret.add(new PhieuXuLy().convertDataFromDatabase(rs))
                        }
                    }
                }
            } else {
                String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_PHIEU_XULY(?,?,?,?,?,?,?,?,?,?) }"
                Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                    sql.call(sqlString, [requestBody.nguonScId, requestBody.maSc, requestBody.ngayGiao,
                                         requestBody.trangThai, requestBody.mucDoScId, requestBody.keoDai,
                                         requestBody.huongGiaoId, Long.parseLong(nhanVienId), Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                        while (rs.next()) {
                            ret.add(new PhieuXuLy().convertDataFromDatabase(rs))
                        }
                    }
                }
            }
            */
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> dsBuocXL(String maQT) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_QT_BUOC_XULY(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, maQT, 1, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                CODE: rs.getString("MA_BUOC"),
                                NAME: rs.getString("TEN_BUOC_XULY")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> dsBuocXLV2(String maQT, String maBuoc) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)

            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)

            String sqlString = "{ call QLSC.SP_LAY_DS_QT_BUOC_XULY_V2(?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, maQT, maBuoc != null ? maBuoc : "1", nhanVienId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                CODE: rs.getString("MA_BUOC"),
                                NAME: rs.getString("TEN_BUOC_XULY")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsBuocXL_v2] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    } //
    @Override
    ResponseEntity<BaseResponse<List>> dsBuocXLV3(String maQT, String maBuoc, Long idPhieu) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)

            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String nhanVienId = session_.get(ID_NHANVIEN_DHSX)

            String sqlString = "{ call QLSC.SP_LAY_DS_QT_BUOC_XULY_V3(?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, maQT, maBuoc != null ? maBuoc : "1", nhanVienId, idPhieu, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                CODE: rs.getString("MA_BUOC"),
                                NAME: rs.getString("TEN_BUOC_XULY")
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsBuocXL_v3] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @Override
    ResponseEntity<BaseResponse<List>> dsDVTheoBuoc(String maQT, String maBuoc) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DONVI_THEO_BUOC_XULY(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, maQT, maBuoc, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("DONVI_ID"),
                                NAME: rs.getString("TEN_DV"),
                                CODE: rs.getString("MA_DV"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List>> dsPhieuDaGiao(String suCoId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.LAY_DS_PHIEU_DAGIAO(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, suCoId, CURSOR_PARAMETER]) { ResultSet rs ->
                    ret = resultSetToObject.getDynamicData(rs)
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsPhieuDaGiao] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhanVienBaoTon() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String donViId = session_.get(ID_DONVI)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_NHANVIEN_BAOTON(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, donViId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("NHANVIEN_ID"),
                                NAME: rs.getString("TEN_NV"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsNhanVienBaoTon] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsChiTietTon(Long nhomTonId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_CHITIET_TON(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhomTonId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("NNTON_ID"),
                                NAME: rs.getString("NN_TON"),
                        ))
                    }
                }
            }

            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsChiTietTon] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhomSuCo(Long suCoId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_NHOMSC(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [suCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("NHOMSC_ID"),
                                NAME: rs.getString("NHOM_SC"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsNhomSuCo] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhomTon(Long nhomSuCoId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.SP_LAY_DS_NHOMTON(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [nhomSuCoId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: rs.getLong("NHOMTON_ID"),
                                NAME: rs.getString("NHOM_TON"),
                        ))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsNhomTon] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<BaoTon>>> dsBaoTon(Long phieuId) {
        List<BaoTon> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call QLSC.PKG_DANHMUC.SP_LAY_DS_BAOTON(?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phieuId, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new BaoTon().convertDataFromDatabase(rs))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsBaoTon] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<ThongTinPort>>> layThongTinPort(String maTb, Long kieu) {
        List<ThongTinPort> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.SP_lay_port_theomatb(?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maTb, kieu, Long.parseLong(phanVungId), CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new ThongTinPort().convertDataFromDatabase(rs))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [layThongTinPort] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsTinh() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.webapi.Post_List(?,?,?,?,?,?,?) }"
            //FIXME VietNH: V_ID trong hàm này fix cứng là 7, API không hiểu ý nghĩa
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Constant.V_ID_TINH, null, null, null, null, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                    }
                }
            }
            if (ret != null && ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsTinh] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsQuan(Long tinhId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            if (tinhId == null || tinhId == 0) {
                tinhId = Long.parseLong((String) session_.get(ID_TINHTHANH))
            }
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.webapi.Post_List(?,?,?,?,?,?,?) }"
            //FIXME VietNH: V_ID trong hàm này fix cứng là 8, API không hiểu ý nghĩa
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Constant.V_ID_QUAN, tinhId, null, null, null, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                    }
                }
            }
            if (ret != null && ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsQuan] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsPhuong(Long quanId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.webapi.Post_List(?,?,?,?,?,?,?) }"
            //FIXME VietNH: V_ID trong hàm này fix cứng là 9, API không hiểu ý nghĩa
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Constant.V_ID_PHUONG, quanId, null, null, null, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                    }
                }
            }
            if (ret != null && ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsPhuong] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsPhoApKhu(Long phuongId, Integer type) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call css.webapi.Post_List(?,?,?,?,?,?,?) }"
            //FIXME VietNH: V_ID trong hàm này fix cứng là 10, API không hiểu ý nghĩa
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Constant.V_ID_PHO, phuongId, type, null, null, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                    }
                }
            }
            if (ret != null && ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsPhoApKhu] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDacDiem(Long phuongId, Long duongId, Long apId, Long khuId) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            if (duongId == null) {
                duongId = 0
            }
            if (apId == null) {
                apId = 0
            }
            if (khuId == null) {
                khuId = 0
            }
            String sqlString = "{ call css.webapi.Post_List(?,?,?,?,?,?,?) }"
            //FIXME VietNH: V_ID trong hàm này fix cứng là 11, API không hiểu ý nghĩa
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), Constant.V_ID_DACDIEM, phuongId, duongId, apId, khuId, CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("id"), NAME: rs.getString("name")))
                    }
                }
            }
            if (ret != null && ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsDacDiem] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsTrangThaiHD() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId();
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "{ call CSS.WEBAPI_EX.Post_List_v2(?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [Long.parseLong(phanVungId), "TRANGTHAI_HD_DGNV", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(ID: rs.getLong("ID"), NAME: rs.getString("NAME")))
                    }
                }
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [dsTrangThaiHD] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> ds_dichvuvt_kieuyeucau(DichVuVTKieuYeuCauRequest requestBody) {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            if (requestBody != null && requestBody.loai in ["DANHSACH_DICHVU_VT", "KIEULD_DVVT_TDTT"]) {
                Map<String, Object> session_ = genericApi.getSessionData(request)
                String dataSrc = session_.get(CSS_SCH)
                String phanVungId = session_.get(ID_TINHTHANH)
                String sqlString = "{ call CSS.WEBAPI_EX.POST_LIST_V2(?,?,?,?,?,?,?,?) }"

                switch (requestBody.loai) {
                    case "KIEULD_DVVT_TDTT":
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(sqlString, [Long.parseLong(phanVungId), "KIEULD_DVVT_TDTT", requestBody.p_para1, "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                                while (rs.next()) {
                                    ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "kieuld_id"), NAME: Utils.getStringDataByKey(rs, "ten_kieuld")))
                                }
                            }
                        }
                        break;
                    case "DANHSACH_DICHVU_VT":
                        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                            sql.call(sqlString, [Long.parseLong(phanVungId), "DANHSACH_DICHVU_VT", "", "", "", "", "", CURSOR_PARAMETER]) { ResultSet rs ->
                                while (rs.next()) {
                                    ret.add(new DanhMuc(ID: Utils.getLongDataByKey(rs, "ID"), NAME: Utils.getStringDataByKey(rs, "NAME")))
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [ds_dichvuvt_kieuyeucau] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> b2a_phantap() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "select ma_tap, ten_tap, mota from css.b2a_phantap"
            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                CODE: Utils.getStringDataByKey(rs, "ma_tap"),
                                NAME: Utils.getStringDataByKey(rs, "ten_tap"),
                                DESCRIPTION: Utils.getStringDataByKey(rs, "mota")))
                    }
                }
            }
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [b2a_phantap] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> b2a_trangthai() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "select id, name from css.b2a_trangthai"
            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs, "id"),
                                NAME: Utils.getStringDataByKey(rs, "name")))
                    }
                }
            }
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [b2a_trangthai] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> b2a_kenh_tc() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "select kenhtc_id, kenh_tc from css.b2a_kenh_tc"
            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs, "kenhtc_id"),
                                NAME: Utils.getStringDataByKey(rs, "kenh_tc")))
                    }
                }
            }
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [b2a_kenh_tc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<List<DanhMuc>>> b2a_chiendich() {
        List<DanhMuc> ret = []
        String requestId = genericApi.generateRequestId()
        try {
            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)
            String phanVungId = session_.get(ID_TINHTHANH)
            String sqlString = "select cd_id, ma_cd from css.b2a_chiendich"
            sqlString = DataUtils.replaceSchema(dataSrc, sqlString)
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.query(sqlString, []) { ResultSet rs ->
                    while (rs.next()) {
                        ret.add(new DanhMuc(
                                ID: Utils.getLongDataByKey(rs, "cd_id"),
                                NAME: Utils.getStringDataByKey(rs, "ma_cd")))
                    }
                }
            }
            if (ret.size() > 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, ErrorCodeDefine.NO_CONTENT, ErrorCodeDefine.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [b2a_chiendich] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @Override
    ResponseEntity<BaseResponse<QuyTrinhCap5>> getCap5ByBuoc(String maProc, String buocProc) {
        QuyTrinhCap5 ret = new QuyTrinhCap5()
        ret.setMaQtCap5("")
        ret.setTenQtCap5("")
        ret.setChitietQtc5("")
        String requestId = genericApi.generateRequestId()
        try {
            if (!maProc || !buocProc) {
                return ResponseEntity.ok().body(
                        BaseResponseBuilder.of(null, requestId,
                                "Bạn vui lòng nhập đầy đủ maProc và buocProc",
                                ErrorCodeDefine.INTERNAL_SERVER_ERROR))
            }

            loggingManaged.info("[DanhMucController] - [getCap5ByBuoc] - Request: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("maProc", maProc)
                            .put("buocProc", buocProc)
                            .build())

            Map<String, Object> session_ = genericApi.getSessionData(request)
            String dataSrc = session_.get(CSS_SCH)

            String sqlString = "{ call QLSC.PKG_CUONGVK_QUYTRINH_C4_C5.SP_GET_CAP5_BY_BUOC(?, ?, ?) }"

            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [maProc, buocProc, CURSOR_PARAMETER]) { ResultSet rs ->
                    if (rs.next()) {
                        ret.setMaQtCap5(Utils.getStringDataByKey(rs, "MA_QT_CAP_5"))
                        ret.setTenQtCap5(Utils.getStringDataByKey(rs, "TEN_QT_CAP_5"))
                        ret.setChitietQtc5(Utils.getStringDataByKey(rs, "CHITIET_QTC5"))
                    }
                }
            }

            loggingManaged.info("[DanhMucController] - [getCap5ByBuoc] - Response: ",
                    new ImmutableMap.Builder<String, Object>()
                            .put("request_id", requestId)
                            .put("result", ret != null ? new JsonBuilder(ret).toPrettyString() : "")
                            .build())

            if (ret != null) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
            } else {
                return ResponseEntity.ok().body(
                        BaseResponseBuilder.of(null, requestId,
                                ErrorCodeDefine.NO_CONTENT,
                                ErrorCodeDefine.NO_CONTENT))
            }

        } catch (Exception e) {
            loggingManaged.error("[DanhMucController] - [getCap5ByBuoc] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(ret))
        }
    }
}