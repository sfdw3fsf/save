package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiIPDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLopKNDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiIPService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/danhmuc-loai-ip")
public class DanhMucLoaiIPController {
    private final DanhMucLoaiIPService danhMucLoaiIPService;

    public DanhMucLoaiIPController(DanhMucLoaiIPService danhMucLoaiIPService){
        this.danhMucLoaiIPService = danhMucLoaiIPService;
    }

    @Operation(summary = "Thêm hoặc cập nhật IP")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DanhMucLoaiIPDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiIPService.upsertLoaiIP(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loại IP")
    @PostMapping("/get-danhsach-ip")
    public ResponseEntity<ApiResult> getIP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiIPService.getLoaiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Loại IP theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DanhMucLoaiIPDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiIPService.deleteLoaiIP(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều loại ip")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<DanhMucLoaiIPDto> danhSachLoaiIP) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiIPService.validateBatchLoaiIP(danhSachLoaiIP));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều loại ip (batch insert)")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucLoaiIPDto> danhSachLoaiIP) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiIPService.batchInsertLoaiIP(danhSachLoaiIP));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
