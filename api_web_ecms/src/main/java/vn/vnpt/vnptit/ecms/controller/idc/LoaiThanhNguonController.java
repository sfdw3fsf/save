package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiThanhNguonDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteLoaiThanhNguonDto;
import vn.vnpt.vnptit.ecms.service.idc.LoaiThanhNguonService;

import java.util.List;

@RestController
@RequestMapping("idc/loai-thanh-nguon")
public class LoaiThanhNguonController {

    private final LoaiThanhNguonService service;

    public LoaiThanhNguonController(LoaiThanhNguonService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách loại thanh nguồn")
    @GetMapping("")
    public ResponseEntity<ApiResult> listLoaiThanhNguon() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListLoaiThanhNguon());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm loại thanh nguồn theo điều kiện")
    @PostMapping("/search")
    public ResponseEntity<ApiResult> searchLoaiThanhNguon(@RequestBody LoaiThanhNguonDto searchDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.searchLoaiThanhNguon(searchDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật loại thanh nguồn (upsert)")
    @PostMapping("")
    public ResponseEntity<ApiResult> upsertLoaiThanhNguon(@RequestBody LoaiThanhNguonDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertLoaiThanhNguon(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều loại thanh nguồn")
    @DeleteMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteLoaiThanhNguonMulti(@RequestBody DeleteLoaiThanhNguonDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteLoaiThanhNguonMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật nhiều loại thanh nguồn (upsert multi)")
    @PostMapping("/upsert-multi")
    public ResponseEntity<ApiResult> upsertLoaiThanhNguonMulti(@RequestBody List<LoaiThanhNguonDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertLoaiThanhNguonMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều loại thanh nguồn (validate multi)")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> checkValidLoaiThanhNguonMulti(@RequestBody List<LoaiThanhNguonDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkValidLoaiThanhNguonMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
