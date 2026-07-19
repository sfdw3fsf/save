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
import vn.vnpt.vnptit.ecms.dto.idc.ChungLoaiTuDiaDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteChungLoaiTuDiaDto;
import vn.vnpt.vnptit.ecms.service.idc.ChungLoaiTuDiaService;

@RestController
@RequestMapping("idc/chungloai-tudia")
public class ChungLoaiTuDiaController {

    private final ChungLoaiTuDiaService service;

    public ChungLoaiTuDiaController(ChungLoaiTuDiaService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách hoặc tìm kiếm chủng loại tủ đĩa (truyền param để search, truyền {} để lấy tất cả)")
    @PostMapping("")
    public ResponseEntity<ApiResult> list(@RequestBody(required = false) ChungLoaiTuDiaDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListChungLoaiTuDia(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật chủng loại tủ đĩa (upsert)")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody ChungLoaiTuDiaDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertChungLoaiTuDia(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật nhiều chủng loại tủ đĩa (upsert multi)")
    @PostMapping("/upsert-multi")
    public ResponseEntity<ApiResult> upsertMulti(@RequestBody List<ChungLoaiTuDiaDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertChungLoaiTuDiaMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều chủng loại tủ đĩa")
    @PostMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteMulti(@RequestBody DeleteChungLoaiTuDiaDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteChungLoaiTuDiaMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate một chủng loại tủ đĩa")
    @PostMapping("/validate")
    public ResponseEntity<ApiResult> validate(@RequestBody ChungLoaiTuDiaDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateChungLoaiTuDia(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều chủng loại tủ đĩa")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> validateMulti(@RequestBody List<ChungLoaiTuDiaDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateChungLoaiTuDiaMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}


