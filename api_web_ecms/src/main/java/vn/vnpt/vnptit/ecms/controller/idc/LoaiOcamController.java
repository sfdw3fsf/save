package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteLoaiOcamDto;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiOcamDto;
import vn.vnpt.vnptit.ecms.service.idc.LoaiOcamService;

@RestController
@RequestMapping("idc/loai-ocam")
public class LoaiOcamController {
    private final LoaiOcamService service;

    public LoaiOcamController(LoaiOcamService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách hoặc tìm kiếm loại ổ cắm (truyền param để search, truyền {} để lấy tất cả)")
    @PostMapping("")
    public ResponseEntity<ApiResult> listLoaiOcam(@RequestBody(required = false) LoaiOcamDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListLoaiOcam(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật loại ổ cắm (upsert)")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertLoaiOcam(@RequestBody LoaiOcamDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertLoaiOcam(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều loại ổ cắm")
    @PostMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteLoaiOcamMulti(@RequestBody DeleteLoaiOcamDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteLoaiOcamMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate một loại ổ cắm")
    @PostMapping("/validate")
    public ResponseEntity<ApiResult> validateLoaiOcam(@RequestBody LoaiOcamDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateLoaiOcam(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều loại ổ cắm")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> validateLoaiOcamMulti(@RequestBody List<LoaiOcamDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateLoaiOcamMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

