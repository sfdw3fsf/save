package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiOCungDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLopKNDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiOCungService;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLopKNService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/danhmuc-loai-ocung")
public class DanhMucLoaiOCungController {
    private final DanhMucLoaiOCungService danhMucLoaiOCungService;

    public DanhMucLoaiOCungController(DanhMucLoaiOCungService danhMucLoaiOCungService){
        this.danhMucLoaiOCungService = danhMucLoaiOCungService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Lớp KN")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DanhMucLoaiOCungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiOCungService.upsertLoaiOCung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Lớp KN")
    @PostMapping("/get-danhsach-loai-ocung")
    public ResponseEntity<ApiResult> getLopKN() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiOCungService.getLoaiOCung());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa nhà xuất bản theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DanhMucLoaiOCungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiOCungService.deleteLoaiOCung(dto.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều loại rack")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<DanhMucLoaiOCungDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiOCungService.validateBatchLoaiOCung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều lớp kn (batch insert)")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucLoaiOCungDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucLoaiOCungService.batchInsertLoaiOCung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
