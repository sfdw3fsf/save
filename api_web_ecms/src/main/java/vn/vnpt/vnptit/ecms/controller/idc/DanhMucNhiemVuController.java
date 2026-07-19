package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucNhiemVuDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucNhiemVuService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/danhmuc/nhiemvu")
public class DanhMucNhiemVuController {


    @Autowired
    private DanhMucNhiemVuService danhMucNhiemVuService;

    @Operation(summary = "Lấy danh sách nhiệm vụ")
    @PostMapping("/get-danhsach-nhiemvu")
    public ResponseEntity<ApiResult> getDanhSachIDG() {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucNhiemVuService.getDanhSachNhiemVu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhập danh mục nhiệm vụ")
    @PostMapping(value = "/upsert")
    public ResponseEntity<ApiResult> upsert(@Valid @RequestBody DanhMucNhiemVuDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhMucNhiemVuService.upsertDanhMucNhiemVu(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Validate danh sách danh mục nhiệm vụ")
    @PostMapping("/validate-batch")
    public ResponseEntity<ApiResult> validateBatchDanhMucNhiemVu(@RequestBody List<Map<String, Object>> payload) {

        ApiResult apiResult = new ApiResult();

        try {
            apiResult.setData(danhMucNhiemVuService.validateBatchDanhMucNhiemVu(payload));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Thêm mới danh mục nhiệm vụ theo danh sách (batch)")
    @PostMapping("/batch-insert")
    public ResponseEntity<ApiResult> batchInsertDanhMucIdg(@RequestBody List<Map<String, Object>> payload) {

        ApiResult apiResult = new ApiResult();

        try {
            apiResult.setData(danhMucNhiemVuService.batchInsertDanhMucNhiemVu(payload));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục nhiệm vụ theo ID")
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResult> deleteDonVi(@PathVariable("id") Number id) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucNhiemVuService.deleteNhiemVu(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}


