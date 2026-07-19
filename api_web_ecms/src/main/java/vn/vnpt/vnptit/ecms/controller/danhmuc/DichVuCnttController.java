package vn.vnpt.vnptit.ecms.controller.danhmuc;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DichVuCnttDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.DichVuCnttService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dichvu-cntt")
public class DichVuCnttController {

  @Autowired
  private DichVuCnttService dichVuCnttService;

  @Operation(summary = "Thêm mới dịch vụ CNTT")
  @PostMapping("/insert")
  public ResponseEntity<ApiResult> insert(@Valid @RequestBody DichVuCnttDTO dto) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(dichVuCnttService.insert(dto));
    } catch (AppSqlException e) {
      rs.setException(e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Cập nhật dịch vụ CNTT")
  @PostMapping("/update")
  public ResponseEntity<ApiResult> update(@Valid @RequestBody DichVuCnttDTO dto) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(dichVuCnttService.update(dto));
    } catch (AppSqlException e) {
      rs.setException(e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Xóa dịch vụ CNTT")
  @PostMapping("/delete/{id}")
  public ResponseEntity<ApiResult> delete(@PathVariable(name = "id") Integer id) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(dichVuCnttService.delete(id));
    } catch (AppSqlException e) {
      rs.setException(e);
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Lấy tất cả dịch vụ CNTT")
  @PostMapping("/get-all")
  public ResponseEntity<ApiResult> getAll() {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(dichVuCnttService.getAll());
    } catch (AppSqlException e) {
      rs.setException(e);
    }
    return rs.getResponseEntity();
  }
  @Operation(summary = "Validate danh sách danh mục Dịch vụ cung cấp (batch)")
  @PostMapping("/validate-batch")
  public ResponseEntity<ApiResult> validateBatchDanhMucDichVuDichVuCungCap(@RequestBody List<Map<String, Object>> payload) {

    ApiResult apiResult = new ApiResult();

    try {
      apiResult.setData(dichVuCnttService.validateBatchDanhMucDichVuDichVuCungCap(payload));
    } catch (AppSqlException e) {
      apiResult.setException(e);
    }

    return apiResult.getResponseEntity();
  }

  @Operation(summary = "Thêm mới danh mục Dịch vụ cung cấp theo danh sách (batch)")
  @PostMapping("/batch-insert")
  public ResponseEntity<ApiResult> batchInsertDanhMucDichVuCungCap(@RequestBody List<Map<String, Object>> payload) {

    ApiResult apiResult = new ApiResult();

    try {
      apiResult.setData(dichVuCnttService.batchInsertDanhMucDichVuCungCap(payload));
    } catch (AppSqlException e) {
      apiResult.setException(e);
    }

    return apiResult.getResponseEntity();
  }
}