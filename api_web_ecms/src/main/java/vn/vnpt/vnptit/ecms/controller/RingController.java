package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.RingInsDto;
import vn.vnpt.vnptit.ecms.service.RingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ring")
public class RingController {

    private final RingService ringService;

    public RingController(RingService ringService) {
        this.ringService = ringService;
    }

    @Operation(summary = "Lấy danh sách ring", description = "Lấy danh sách ring")
    @RequestMapping(value = "/danh-sach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKe() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ringService.lietKe());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin một ring", description = "Lấy thông tin một ring")
    @RequestMapping(value = "/chi-tiet/{id}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> chiTiet(@PathVariable(value = "id") long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ringService.chiTiet(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm một ring", description = "Thêm một ring")
    @RequestMapping(value = "/them", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them(@RequestBody @Valid RingInsDto ringInsDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ringService.them(ringInsDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật một ring", description = "Thêm/cập nhật một ring")
    @RequestMapping(value = "/cap-nhat", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhat(@RequestBody @Valid RingInsDto.RingUpdDto ringUpdDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ringService.capNhat(ringUpdDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa một ring", description = "Xóa một ring")
    @RequestMapping(value = "/xoa/{id}", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@PathVariable(value = "id") long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ringService.xoa(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
