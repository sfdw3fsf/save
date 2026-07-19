package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.VungDlDto;
import vn.vnpt.vnptit.ecms.service.idc.VungDlService;

@RestController("idcVungDlController")
@RequestMapping("/vung-du-lieu")
@Tag(name = "Data Area", description = "Vùng dữ liệu")
public class VungDlController {
    private final VungDlService vungDlService;

    public VungDlController(VungDlService vungDlService) {
        this.vungDlService = vungDlService;
    }

    @Operation(summary = "Lấy chi tiết vùng dữ liệu")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> getDetail(@RequestBody VungDlDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.getDetailVungDl(dto.getVungdlId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách vùng dữ liệu cụm hạ tầng ảo hóa được cấp phát trên vùng dữ liệu tủ đĩa")
    @PostMapping("/ds-vungdl-aohoa-by-tudia")
    public ResponseEntity<ApiResult> getDsVungDlAoHoaByTuDia(@RequestBody VungDlDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.getDsVungDlAoHoaByTuDia(dto.getVungdlTudId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật vùng dữ liệu")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody VungDlDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.upsertVungDl(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa vùng dữ liệu")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody VungDlDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.deleteVungDl(dto.getVungdlId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách máy ảo theo vùng dữ liệu")
    @GetMapping("/ds-may-ao")
    public ResponseEntity<ApiResult> getDsMayAo(@RequestParam Long vungdlId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.getDsMayAoByVungId(vungdlId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách file storage theo vùng dữ liệu")
    @GetMapping("/ds-file-storage")
    public ResponseEntity<ApiResult> getDsFileStorage(@RequestParam Long vungdlId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.getDsFileStorageByVungId(vungdlId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị theo vùng dữ liệu")
    @GetMapping("/ds-thiet-bi")
    public ResponseEntity<ApiResult> getDsThietBi(@RequestParam Long vungdlId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.getDsThietBiByVungId(vungdlId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ dữ liệu từ gateway")
    @PostMapping("/sync-datastore")
    public ResponseEntity<ApiResult> syncFromGateway(@RequestBody VungDlDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.syncVungDlFromGateway(dto.getMaVungdl()));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi đồng bộ vùng dữ liệu: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hóa theo vùng dữ liệu")
    @PostMapping("/ds-ha-tang-ao-hoa")
    public ResponseEntity<ApiResult> getDanhSachHaTangAoHoa(@RequestBody VungDlDto dto) {
        ApiResult result = new ApiResult();
        try {
            result.setData(vungDlService.getDanhSachHaTangAoHoaTheoVungDuLieu(dto.getLoaiVungdl(), dto.getVungdlId()));
        } catch (AppSqlException e) {
            result.setException(e);
        }
        return result.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ và lưu trực tiếp vùng dữ liệu từ VCenter Gateway (lưu thẳng vào DB)")
    @PostMapping("/sync-save-datastore")
    public ResponseEntity<ApiResult> syncAndSaveFromGateway(@RequestBody VungDlDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vungDlService.syncAndSaveSingleVungDl(dto.getMaVungdl(), dto.getVungdlId()));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi đồng bộ vùng dữ liệu: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
