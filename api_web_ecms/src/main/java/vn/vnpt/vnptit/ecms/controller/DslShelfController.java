package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatDslamShelfDtoInput;
import vn.vnpt.vnptit.ecms.dto.TaoDslamShelfDtoInput;
import vn.vnpt.vnptit.ecms.service.DslamShelfService;

import javax.validation.Valid;

@RestController
@RequestMapping("dslamShelf")
public class DslShelfController {
    private final DslamShelfService dslamShelfService;

    public DslShelfController(DslamShelfService dslamShelfService) {
        this.dslamShelfService = dslamShelfService;
    }

    @Operation(summary = "Liệt kê chi tiết dslam shelf theo phân vùng id", description = "Liệt kê chi tiết dslam shelf theo phân vùng id")
    @RequestMapping(value = "/chitiet", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDslShelf(@RequestParam("dslshelf_id") int dslshelf_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dslamShelfService.layDslShelfTheoId(dslshelf_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới  dslam shelf ", description = "Thêm mới  dslam shelf ")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoDslShelf(@Valid @RequestBody TaoDslamShelfDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dslamShelfService.taoDslShelf(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật dslam shelf ", description = "Cập nhật dslam shelf ")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatDslShelf(@Valid @RequestBody CapNhatDslamShelfDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(dslamShelfService.capNhatDslShelf(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá dslam shelf ", description = "Xoá dslam shelf ")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaDslShelf( @RequestParam("dslshelf_id") int dslshelf_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(dslamShelfService.xoaDslShelf(dslshelf_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
