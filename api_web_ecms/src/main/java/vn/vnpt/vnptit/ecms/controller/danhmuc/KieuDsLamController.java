package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.KieuDsLamInput;
import vn.vnpt.vnptit.ecms.service.danhmuc.KieuDsLamService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/kieuDslam")
public class KieuDsLamController {

    private final KieuDsLamService kieuDsLamService;

    public KieuDsLamController(KieuDsLamService kieuDsLamService)
    {
        this.kieuDsLamService = kieuDsLamService;
    }

    @Operation(summary = "Liệt kê danh sách kiểu dslam")
    @RequestMapping(value = "/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeKieuDslam() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(kieuDsLamService.getDsKieuDsLam());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy kiểu dslam theo id")
    @RequestMapping(value = "/getKieuDsLamId/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kieuDslamId(@Valid @PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(kieuDsLamService.getDsKieuDsLamById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo mới kiểu dslam")
    @RequestMapping(value = "/themKieuDsLam", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themKieuDslam(@RequestBody KieuDsLamInput kieuDslam) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(kieuDsLamService.taoKieuDsLam(kieuDslam));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật kiểu dslam")
    @RequestMapping(value = "/capNhatKieuDsLam", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatKieuDslam(@RequestBody KieuDsLamInput kieuDslam) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(kieuDsLamService.capNhatKieuDsLam(kieuDslam));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa kiểu dslam")
    @RequestMapping(value = "/xoaKieuDsLam", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaKieuDslam(@Valid @RequestParam("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(kieuDsLamService.xoaKieuDsLamById(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
