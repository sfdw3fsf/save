package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucDonViSHDto;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucDonViSHService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/danhmuc-don-vi")
public class DanhMucDonViSHController {
    private final DanhMucDonViSHService danhMucDonViSHService;

    public DanhMucDonViSHController(DanhMucDonViSHService danhMucDonViSHService) {
        this.danhMucDonViSHService = danhMucDonViSHService;
    }

    @Operation(summary = "Lấy danh sách đơn vị")
    @GetMapping("/get-danhsach-don-vi")
    public ResponseEntity<ApiResult> getDanhSachDonVi() {
        ApiResult rs = new ApiResult();

        try{
            rs.setData(danhMucDonViSHService.getDonViSH());
        }

        catch(AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới Đơn vị SH")
    @PostMapping("/add")
    public ResponseEntity<ApiResult> insertDonVi(@Valid @RequestBody DanhMucDonViSHDto dto) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucDonViSHService.addDonVi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhập Đơn vị SH")
    @PutMapping("/update")
    public ResponseEntity<ApiResult> updateDonVi(@Valid @RequestBody DanhMucDonViSHDto dto) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucDonViSHService.updateDonVi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Đơn vị SH theo ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResult> deleteDonVi(@PathVariable("id") Number id) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucDonViSHService.deleteDonVi(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều Đơn vị SH (batch insert)")
    @RequestMapping(value = "/multiple-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<DanhMucDonViSHDto> danhSachDonVi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucDonViSHService.batchInsertDonVi(danhSachDonVi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều Đơn vị SH")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<DanhMucDonViSHDto> danhSachDonVi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucDonViSHService.validateBatchDonVi(danhSachDonVi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
