package vn.vnpt.vnptit.ecms.controller.idc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.CheckMaGdRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.CheckMaTbRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.CheckNguonYcRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.GetFileUploadRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiCVRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuYeuCauTTCRequestDto;
import vn.vnpt.vnptit.ecms.dto.idc.XulyPhieuYeuCauTTCRequestDto;
import vn.vnpt.vnptit.ecms.service.idc.PhieuYeuCauTTCService;

@RestController
@RequestMapping("phieuyc-ttc")
public class PhieuYeuCauTTCController {

    private final PhieuYeuCauTTCService service;

    public PhieuYeuCauTTCController(PhieuYeuCauTTCService service) {
        this.service = service;
    }

    @PostMapping("/dm-nguon-yc")
    @Operation(summary = "Lấy danh mục nguồn yêu cầu")
    public ResponseEntity<ApiResult> getDmNguonYc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmNguonYc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-nhom-cv")
    @Operation(summary = "Lấy danh mục nhóm công việc")
    public ResponseEntity<ApiResult> getDmNhomCv() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmNhomCv());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-loai-cv")
    @Operation(summary = "Lấy danh mục loại công việc theo nhóm")
    public ResponseEntity<ApiResult> getDmLoaiCv(@RequestBody LoaiCVRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmLoaiCv(dto.getNhomcvId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-trangthai")
    @Operation(summary = "Lấy danh mục trạng thái")
    public ResponseEntity<ApiResult> getDmTrangThai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmTrangThai());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/check-ma-gd")
    @Operation(summary = "Kiểm tra mã giao dịch")
    public ResponseEntity<ApiResult> checkMaGd(@RequestBody CheckMaGdRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkMaGd(dto.getMaGd()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/check-ma-tb")
    @Operation(summary = "Kiểm tra mã thuê bao")
    public ResponseEntity<ApiResult> checkMaTb(@RequestBody CheckMaTbRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkMaTb(dto.getMaGd(), dto.getMaTb()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/check-nguon-yc")
    @Operation(summary = "Kiểm tra nguồn yêu cầu")
    public ResponseEntity<ApiResult> checkNguonYc(@RequestBody CheckNguonYcRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkNguonYc(dto.getIms(), dto.getSo()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy file upload")
    @PostMapping("/get-file-upload")
    public ResponseEntity<ApiResult> getFileUpload(@RequestBody GetFileUploadRequestDto request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getFileUpload(request.getHosoId(), request.getFileId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu phiếu yêu cầu thông tin chung")
    @PostMapping("/luu-phieuyc-ttc")
    public ResponseEntity<ApiResult> savePhieuYeuCauTTC(@RequestBody PhieuYeuCauTTCRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.savePhieuYeuCauTTC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật phiếu yêu cầu thông tin chung")
    @PostMapping("/capnhat-phieuyc-ttc")
    public ResponseEntity<ApiResult> updatePhieuYeuCauTTC(@RequestBody PhieuYeuCauTTCRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updatePhieuYeuCauTTC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xử lý phiếu yêu cầu thông tin chung")
    @PostMapping("/xuly-phieuyc-ttc")
    public ResponseEntity<ApiResult> xulyPhieuYeuCauTTC(@RequestBody XulyPhieuYeuCauTTCRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xulyPhieuYeuCauTTC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-muccuoc")
    @Operation(summary = "Lấy danh mục mức cước")
    public ResponseEntity<ApiResult> getDmMucCuoc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmMucCuoc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
