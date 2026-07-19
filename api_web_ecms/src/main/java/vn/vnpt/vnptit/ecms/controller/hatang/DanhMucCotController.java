package vn.vnpt.vnptit.ecms.controller.hatang;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucCotDto;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucCotService;
@Api(value = "Danh mục cột Controller", description = "Danh mục cột")
@RestController
@RequestMapping("hatang/danhmuc-cot")
public class DanhMucCotController {
    private final DanhMucCotService service;
    public DanhMucCotController(DanhMucCotService service) {
        this.service = service;
    }
    @Operation(summary = "Lấy danh sách phòng")
    @GetMapping("/dsphong")
    public ResponseEntity<ApiResult> listPhong() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getPhong("PHONG"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách Cột")
    @GetMapping("/dscots")
    public ResponseEntity<ApiResult> danhsachCot() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dscots());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "insert and update CỘT")
    @RequestMapping(value = "/addCot", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> upCot(@Valid @RequestBody DanhMucCotDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.upCot(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Delete Cột")
    @RequestMapping(value = "/xoaCot", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.delCot(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Tra cứu cột")
    @PostMapping("/tracuuCot")
    public ResponseEntity<ApiResult> getCot(@RequestBody(required = false) DanhMucCotDto dto) {
        ApiResult rs = new ApiResult();
        try {
            if (dto == null) {
                dto = new DanhMucCotDto();
            }
            rs.setData(this.service.searchCot(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}


