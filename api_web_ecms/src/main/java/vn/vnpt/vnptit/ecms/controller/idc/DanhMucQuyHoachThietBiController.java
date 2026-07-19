package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucQuyHoachThietBiDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucQuyHoachThietBiService;

@RestController
@RequestMapping("/danhmuc-qh-thietbi")
public class DanhMucQuyHoachThietBiController {
    private final DanhMucQuyHoachThietBiService danhMucQuyHoachThietBiService;

    public DanhMucQuyHoachThietBiController(DanhMucQuyHoachThietBiService danhMucQuyHoachThietBiService) {
        this.danhMucQuyHoachThietBiService = danhMucQuyHoachThietBiService;
    }

    @Operation(summary = "Thêm hoặc cập nhật danh mục quy hoạch thiết bị")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody DanhMucQuyHoachThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucQuyHoachThietBiService.upserQuyHoachThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách quy hoạch thiết bị")
    @PostMapping("/get-all")
    public ResponseEntity<ApiResult> getAll() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucQuyHoachThietBiService.getAllQuyHoachThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa quy hoạch thiết bị theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody DanhMucQuyHoachThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucQuyHoachThietBiService.deleteQuyHoachThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều quy hoạch thiết bị")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(
            @Valid @RequestBody List<DanhMucQuyHoachThietBiDto> danhSachQuyHoach) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucQuyHoachThietBiService.validateBatchQuyHoachThietBi(danhSachQuyHoach));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều quy hoạch thiết bị")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucQuyHoachThietBiDto> danhSachQuyHoach) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucQuyHoachThietBiService.batchInsertQuyHoach(danhSachQuyHoach));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
