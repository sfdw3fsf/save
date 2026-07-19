package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucDanhSachChungDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLopKNDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLopKNService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/danhmuc-lop-kn")
public class DanhMucLopKNController {
    private final DanhMucLopKNService danhMucLopKNService;

    public DanhMucLopKNController(DanhMucLopKNService danhMucLopKNService){
        this.danhMucLopKNService = danhMucLopKNService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Lớp KN")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DanhMucLopKNDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLopKNService.upsertLopKN(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Lớp KN")
    @PostMapping("/get-danhmuc-lopkn")
    public ResponseEntity<ApiResult> getDanhMucLopKN(@RequestBody DanhMucDanhSachChungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLopKNService.getDanhmucLopKN(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1(),
                    dto.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa nhà xuất bản theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DanhMucLopKNDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLopKNService.deleteLopKN(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều loại rack")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<DanhMucLopKNDto> danhSachLopKN) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLopKNService.validateBatchLopKN(danhSachLopKN));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều lớp kn (batch insert)")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucLopKNDto> danhSachLopKN) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLopKNService.batchInsertLopKN(danhSachLopKN));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
