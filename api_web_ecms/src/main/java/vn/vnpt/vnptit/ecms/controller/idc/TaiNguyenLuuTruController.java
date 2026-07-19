package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.TaiNguyenLuuTruDto;
import vn.vnpt.vnptit.ecms.service.idc.TaiNguyenLuuTruService;

@RestController("idcTaiNguyenLuuTruController")
@RequestMapping("/tainguyen-luutru")
public class TaiNguyenLuuTruController {
    private final TaiNguyenLuuTruService taiNguyenLuuTruService;

    public TaiNguyenLuuTruController(TaiNguyenLuuTruService taiNguyenLuuTruService) {
        this.taiNguyenLuuTruService = taiNguyenLuuTruService;
    }

    @Operation(summary = "Lấy chi tiết tài nguyên lưu trữ")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> getDetail(@RequestBody TaiNguyenLuuTruDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(taiNguyenLuuTruService.getDetailTaiNguyenLt(dto.getTainguyenltId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật tài nguyên lưu trữ")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody TaiNguyenLuuTruDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(taiNguyenLuuTruService.upsertTaiNguyenLt(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa tài nguyên lưu trữ")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody TaiNguyenLuuTruDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(taiNguyenLuuTruService.deleteTaiNguyenLt(dto.getTainguyenltId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
