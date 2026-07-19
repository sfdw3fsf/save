package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiVatLieuKetNoiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteLoaiVatLieuKetNoiDto;
import vn.vnpt.vnptit.ecms.service.idc.LoaiVatLieuKetNoiService;

import java.util.List;

@RestController
@RequestMapping("idc/loai-vatlieu-ketnoi")
public class LoaiVatLieuKetNoiController {
    private final LoaiVatLieuKetNoiService service;

    public LoaiVatLieuKetNoiController(LoaiVatLieuKetNoiService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách loại vật liệu kết nối")
    @GetMapping("")
    public ResponseEntity<ApiResult> listLoaiVatLieuKetNoi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListLoaiVatLieuKetNoi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật loại vật liệu kết nối (upsert)")
    @PostMapping("")
    public ResponseEntity<ApiResult> upsertLoaiVatLieuKetNoi(@RequestBody LoaiVatLieuKetNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertLoaiVatLieuKetNoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều loại vật liệu kết nối")
    @DeleteMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteLoaiVatLieuKetNoiMulti(@RequestBody DeleteLoaiVatLieuKetNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteLoaiVatLieuKetNoiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật nhiều loại vật liệu kết nối (upsert multi)")
    @PostMapping("/upsert-multi")
    public ResponseEntity<ApiResult> upsertLoaiVatLieuKetNoiMulti(@RequestBody List<LoaiVatLieuKetNoiDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertLoaiVatLieuKetNoiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều loại vật liệu kết nối (validate multi)")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> checkValidPhongZoneMulti(@RequestBody List<LoaiVatLieuKetNoiDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkValidVatLieuKetNoiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
