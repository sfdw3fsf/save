package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.MayAoSearchDto;
import vn.vnpt.vnptit.ecms.dto.idc.MayAoDto;
import vn.vnpt.vnptit.ecms.dto.idc.NangLucMayAoDto;
import vn.vnpt.vnptit.ecms.dto.idc.OcungMayAoDto;
import vn.vnpt.vnptit.ecms.service.idc.MayAoService;

@RestController("idcMayAoController")
@RequestMapping("/may-ao")
public class MayAoController {
    private final MayAoService mayAoService;

    public MayAoController(MayAoService mayAoService) {
        this.mayAoService = mayAoService;
    }

    @Operation(summary = "Lấy danh sách máy ảo")
    @PostMapping("/danh-sach")
    public ResponseEntity<ApiResult> getDanhSachMayAo(@RequestBody MayAoSearchDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.getDanhSachMayAo(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết máy ảo")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> getDetailMayAo(@RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.getDetailMayAo(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật máy ảo")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertMayAo(@RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.upsertMayAo(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa máy ảo")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> deleteMayAo(@RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.deleteMayAo(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết năng lực máy ảo (gồm storageDetail)")
    @PostMapping("/nang-luc/chi-tiet")
    public ResponseEntity<ApiResult> getDetailNangLucMayAo(@RequestBody NangLucMayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.getDetailNangLucWithStorage(dto.getMayaoId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi lấy năng lực máy ảo: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật năng lực máy ảo")
    @PostMapping("/nang-luc/upsert")
    public ResponseEntity<ApiResult> upsertNangLucMayAo(@RequestBody NangLucMayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.upsertNangLucMayAo(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật ổ cứng máy ảo")
    @PostMapping("/nang-luc/ocung/upsert")
    public ResponseEntity<ApiResult> upsertOcungMayAo(@RequestBody OcungMayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.upsertOcungMayAo(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa ổ cứng máy ảo")
    @PostMapping("/nang-luc/ocung/delete")
    public ResponseEntity<ApiResult> deleteOcungMayAo(@RequestBody OcungMayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.deleteOcungMayAo(dto.getOcungMayaoId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // ==================== Đồng bộ từ Gateway ====================

    @Operation(summary = "Đồng bộ dữ liệu máy ảo từ VCenter Gateway (chỉ trả DTO, không lưu DB)")
    @PostMapping("/sync-vm")
    public ResponseEntity<ApiResult> syncVmFromGateway(@RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.syncVmFromGateway(dto.getMaMayao()));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi đồng bộ máy ảo: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Đồng bộ và lưu trực tiếp dữ liệu máy ảo từ VCenter Gateway (lưu thẳng vào DB)")
    @PostMapping("/sync-save-vm")
    public ResponseEntity<ApiResult> syncAndSaveVmFromGateway(@RequestBody MayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(mayAoService.syncAndSaveSingleVm(dto.getMaMayao(), dto.getMayaoId()));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi đồng bộ máy ảo: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
