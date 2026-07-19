package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ChuanDauNoiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteChuanDauNoiDto;
import vn.vnpt.vnptit.ecms.service.idc.ChuanDauNoiService;

@RestController
@RequestMapping("idc/chuan-daunoi")
public class ChuanDauNoiController {
    private final ChuanDauNoiService service;

    public ChuanDauNoiController(ChuanDauNoiService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách hoặc tìm kiếm chuẩn đấu nối (truyền param để search, truyền {} để lấy tất cả)")
    @PostMapping("")
    public ResponseEntity<ApiResult> listChuanDauNoi(@RequestBody(required = false) ChuanDauNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListChuanDauNoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật chuẩn đấu nối (upsert)")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertChuanDauNoi(@RequestBody ChuanDauNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertChuanDauNoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều chuẩn đấu nối")
    @PostMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteChuanDauNoiMulti(@RequestBody DeleteChuanDauNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteChuanDauNoiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate một chuẩn đấu nối")
    @PostMapping("/validate")
    public ResponseEntity<ApiResult> validateChuanDauNoi(@RequestBody ChuanDauNoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateChuanDauNoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều chuẩn đấu nối")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> validateChuanDauNoiMulti(@RequestBody List<ChuanDauNoiDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateChuanDauNoiMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

