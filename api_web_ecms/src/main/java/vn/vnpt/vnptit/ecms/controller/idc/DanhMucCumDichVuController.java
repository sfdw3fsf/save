package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucCumDichVuDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucCumDichVuService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/danhmuc/cum-dichvu")
public class DanhMucCumDichVuController {
    @Autowired
    private DanhMucCumDichVuService danhMucCumDichVuService;

    @Operation(summary = "Lấy danh sách cụm dịch vụ")
    @PostMapping ("/get-danhsach-cumdichvu")
    public ResponseEntity<ApiResult> getDanhSachIDG() {
        ApiResult rs = new ApiResult();

        try{
            rs.setData(danhMucCumDichVuService.getDanhSachCumDichVu());
        }

        catch(AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhập danh mục cụm dịch vụ")
    @PostMapping(value = "/upsert")
    public ResponseEntity<ApiResult> upsert(@Valid @RequestBody DanhMucCumDichVuDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhMucCumDichVuService.upsertCumDichVu(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa Danh muc cụm dịch vụ theo ID")
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResult> deleteDonVi(@PathVariable("id") Number id) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucCumDichVuService.deleteDanhMucCumDichVu(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate danh sách danh mục cụm dịch vụ (batch)")
    @PostMapping("/validate-batch")
    public ResponseEntity<ApiResult> validateBatchDanhMucIdg(@RequestBody List<Map<String, Object>> payload) {

        ApiResult apiResult = new ApiResult();

        try {
            apiResult.setData(danhMucCumDichVuService.validateBatchDanhMucCumDichVu(payload));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Thêm mới danh mục Cụm dịch vụ theo danh sách (batch)")
    @PostMapping("/batch-insert")
    public ResponseEntity<ApiResult> batchInsertDanhMucIdg(@RequestBody List<Map<String, Object>> payload) {

        ApiResult apiResult = new ApiResult();

        try {
            apiResult.setData(danhMucCumDichVuService.batchInsertDanhMucCumDichVu(payload));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }
}
