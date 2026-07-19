package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucDanhSachChungDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiHaTangDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiHaTangService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/danhmuc-loai-hatang")
public class DanhMucLoaiHaTangController {
    private final DanhMucLoaiHaTangService danhMucLoaiHaTangService;

    public DanhMucLoaiHaTangController(DanhMucLoaiHaTangService danhMucLoaiHaTangService){
        this.danhMucLoaiHaTangService = danhMucLoaiHaTangService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Loại VLKN")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DanhMucLoaiHaTangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiHaTangService.upsertLoaiHaTang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Loại VLKN")
    @PostMapping("/get-danhmuc-loai-hatang")
    public ResponseEntity<ApiResult> getDanhMucLoaiVLKN(@RequestBody DanhMucDanhSachChungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiHaTangService.getDanhmucLoaiHaTang(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1(),
                    dto.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa Loại VLKN")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DanhMucLoaiHaTangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiHaTangService.deleteLoaiHaTang(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều loại rack")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<DanhMucLoaiHaTangDto> danhSachLoaiHaTang) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiHaTangService.validateBatchLoaiHaTang(
                    danhSachLoaiHaTang));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều lớp kn (batch insert)")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucLoaiHaTangDto> danhSachLoaiHaTang) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiHaTangService.batchInsertLoaiHaTang(danhSachLoaiHaTang));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
