package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.idc.CongSuatDienService;

@RestController
@RequestMapping("tainguyen-idc")
public class CongSuatDienController {

    private final CongSuatDienService service;


    public CongSuatDienController(CongSuatDienService service) {
        this.service = service;
    }
    @GetMapping("/congsuatdien/congviec/{phieutc_id}")
    @Operation(summary = "Lấy công việc công suất điện theo phiếu")
    public ResponseEntity<ApiResult> getCongViecCongSuatDien(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getCongViecCongSuatDien(phieutc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/congsuatdien/congviec/{phieutc_id}/dsthietbi")
    @Operation(summary = "Lấy thiết bị cấp phát điện theo phiếu")
    public ResponseEntity<ApiResult> getDsThietBiCapPhatDien(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsThietBiCapPhatDien(phieutc_id));
        } catch (Exception e) {
            rs.setException((AppSqlException) e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/rack/{rack_id}/dsthanhnguonocam")
    @Operation(summary = "Lấy danh sách thanh nguồn ổ cắm theo rack")
    public ResponseEntity<ApiResult> getDsThanhNguonOCam(@PathVariable Long rack_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsThanhNguonOCam(rack_id));
        } catch (Exception e) {
            rs.setException((AppSqlException) e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/capphat")
    @Operation(summary = "Cấp phát công suất điện")
    public ResponseEntity<ApiResult> capphatCongSuatDien(@RequestBody CapPhatCongSuatDienDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = service.capphatCongSuatDien(dto);
        } catch (AppSqlException e) {
            rs.setException( e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/congsuatdien/congviec/{phieutc_id}/ketquacapphat")
    @Operation(summary = "Lấy kết quả cấp phát công suất điện theo phiếu")
    public ResponseEntity<ApiResult> getKetQuaCatPhat(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getKetQuaDsThietBiCapPhatDien(phieutc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/phieutc/update")
    @Operation(summary = "Cập nhật trạng thái phiếu thi công công suất điện")
    public ResponseEntity<ApiResult> updateTrangThaiPhieuTC(@RequestBody UpdatePhieuCongSuatDienDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = service.updateTrangThaiPhieuTC(dto);
        } catch (AppSqlException e) {
            rs.setException( e);
        }
        return rs.getResponseEntity();
    }
    // thu hoi cong suat dien
    @GetMapping("/congsuatdien/congviec/{phieutc_id}/dsthuhoi" )
    @Operation(summary = "Lấy danh sách thiết bị thu hồi công suất điện theo phiếu")
    public ResponseEntity<ApiResult> getDsThietBiThuHoi(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsThietBiThuHoiDien(phieutc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/thuhoi")
    @Operation(summary = "Thu hồi công suất điện")
    public ResponseEntity<ApiResult> thuHoiCongSuatDien(@RequestBody ThuHoiCongSuatDienDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = service.thuHoiCongSuatDien(dto);
        } catch (AppSqlException e) {
            rs.setException( e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/congsuatdien/congviec/{phieutc_id}/ketquathuhoi")
    @Operation(summary = "Lấy kết quả thu hồi công suất điện theo phiếu")
    public ResponseEntity<ApiResult> getKetQuaThuHoi(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getKetQuaDsThietBiThuHoiDien(phieutc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    //chuyen doi cong suat dien
    @GetMapping("/congsuatdien/congviec/{phieutc_id}/dschuyendoi" )
    @Operation(summary = "Lấy danh sách thiết bị chuyển đổi công suất điện theo phiếu")
    public ResponseEntity<ApiResult> getDsThietBiChuyenDoi(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsThietBiChuyenDoiDien(phieutc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/chuyendoicongsuat")
    @Operation(summary = "Chuyển đổi công suất điện")
    public ResponseEntity<ApiResult> chuyenDoiCongSuatDien(@RequestBody ChuyenDoiCongSuatDienDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = service.chuyenDoiCongSuatDien(dto);
        } catch (AppSqlException e) {
            rs.setException( e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/congsuatdien/congviec/{phieutc_id}/ketquachuyendoi")
    @Operation(summary = "Lấy kết quả chuyển đổi công suất điện theo phiếu")
    public ResponseEntity<ApiResult> getKetQuaChuyenDoi(@PathVariable Long phieutc_id)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getKetQuaDsThietBiChuyenDoiDien(phieutc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/ketquacongviec")
    @Operation(summary = "Xem kết quả công việc cấp phát công suất điện có filter")
    public ResponseEntity<ApiResult> getKetQuaCongViecCapPhatFilter(@RequestBody SearchKetQuaCongSuatDienDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getKetQuaCongSuatDienFiltered(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/ketquacongviec/delete")
    @Operation(summary = "Xóa kết quả công việc công suất điện")
    public ResponseEntity<ApiResult> deleteKetQuaCongViec(@RequestBody DeleteKetQuaCongSuatDien dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = service.deleteKetQuaCongViec(dto);
        } catch (AppSqlException e) {
            rs.setException( e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/congsuatdien/selectthietbi" )
    @Operation(summary = "Chọn thiết bị công suất điện")
    public ResponseEntity<ApiResult> selectThietBiCongSuatDien(@RequestBody SelectThietBiCongSuatDienDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.selectThietBiCongSuatDien(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}