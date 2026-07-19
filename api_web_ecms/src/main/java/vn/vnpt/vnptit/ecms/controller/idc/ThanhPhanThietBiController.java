package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ThanhPhanThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteThanhPhanThietBiDto;
import vn.vnpt.vnptit.ecms.service.idc.ThanhPhanThietBiService;

import java.util.List;

@RestController
@RequestMapping("idc/thanhphan-thietbi")
public class ThanhPhanThietBiController {

    private final ThanhPhanThietBiService service;

    public ThanhPhanThietBiController(ThanhPhanThietBiService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách thành phần thiết bị")
    @GetMapping("")
    public ResponseEntity<ApiResult> listThanhPhanThietBi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListThanhPhanThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật thành phần thiết bị (upsert)")
    @PostMapping("")
    public ResponseEntity<ApiResult> upsertThanhPhanThietBi(@RequestBody ThanhPhanThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertThanhPhanThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều thành phần thiết bị")
    @DeleteMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteThanhPhanThietBiMulti(@RequestBody DeleteThanhPhanThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteThanhPhanThietBiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật nhiều thành phần thiết bị (upsert multi)")
    @PostMapping("/upsert-multi")
    public ResponseEntity<ApiResult> upsertThanhPhanThietBiMulti(@RequestBody List<ThanhPhanThietBiDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertThanhPhanThietBiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều thành phần thiết bị (validate multi)")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> checkValidThanhPhanThietBiMulti(@RequestBody List<ThanhPhanThietBiDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkValidThanhPhanThietBiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
