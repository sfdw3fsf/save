package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.RackInput;
import vn.vnpt.vnptit.ecms.service.danhmuc.RackService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/rack")
public class RackController {
    private final RackService rackService;

    public RackController(RackService rackService)
    {
        this.rackService = rackService;
    }

    @Operation(summary = "Liệt kê danh sách rack")
    @RequestMapping(value = "/danh-sach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layRack() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(rackService.layRack(AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy một rack", description = "Lấy một rack")
    @RequestMapping(value = "/chi-tiet/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layRackTheoId(@PathVariable("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(rackService.layRackTheoId(id, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm một rack", description = "Thêm một rack")
    @RequestMapping(value = "/them", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themRack(@RequestBody @Valid RackInput rackInput) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(rackService.themRack(rackInput, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sửa một rack", description = "Sửa một rack")
    @RequestMapping(value = "/cap-nhat", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> suaHuongKetNoiBras(@RequestBody @Valid RackInput.RackInputIns rackInput) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(rackService.suaRack(rackInput, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa một rack", description = "Xóa một rack")
    @RequestMapping(value = "/xoa/{id}", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaHuongKetNoiBras(@PathVariable(value = "id") String id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(rackService.xoaRack(id, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
