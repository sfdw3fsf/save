package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucDanhSachChungDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiVLKNDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiVLKNService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/danhmuc-loai-vlkn")
public class DanhMucLoaiVLKNController {
    private final DanhMucLoaiVLKNService danhMucLoaiVLKNService;

    public DanhMucLoaiVLKNController(DanhMucLoaiVLKNService danhMucLoaiVLKNService){
        this.danhMucLoaiVLKNService = danhMucLoaiVLKNService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Loại VLKN")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DanhMucLoaiVLKNDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiVLKNService.upsertLoaiVKLKN(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Loại VLKN")
    @PostMapping("/get-danhmuc-loai-vlkn")
    public ResponseEntity<ApiResult> getDanhMucLoaiVLKN(@RequestBody DanhMucDanhSachChungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiVLKNService.getDanhmucLoaiVLKN(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1(),
                    dto.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa Loại VLKN")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DanhMucLoaiVLKNDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiVLKNService.deleteLoaiVLKN(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều loại rack")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<DanhMucLoaiVLKNDto> danhSachLoaiVLKN) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiVLKNService.validateBatchLoaiVLKN(danhSachLoaiVLKN));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều lớp kn (batch insert)")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucLoaiVLKNDto> danhSachLoaiVLKN) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiVLKNService.batchInsertLoaiVLKN(danhSachLoaiVLKN));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
