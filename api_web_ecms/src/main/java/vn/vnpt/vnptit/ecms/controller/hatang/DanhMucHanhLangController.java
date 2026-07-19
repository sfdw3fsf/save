package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucHanhLangDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucHanhLangService;

import javax.validation.Valid;

@Api(value = "Danh mục hành lang Controller", description = " Danh mục hành lang")
@RestController
@RequestMapping("hatang/danhmuc-hanhlang")
public class DanhMucHanhLangController {
    private final DanhMucHanhLangService service;

    public DanhMucHanhLangController(DanhMucHanhLangService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy tất cả danh sách hành lang")
    @RequestMapping(value = "/getall", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getItems() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getAll());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phòng")
    @RequestMapping(value = "/getphong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsPhong() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsPhongAll());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo mới - chỉnh sửa hanh lang")
    @RequestMapping(value = "/update", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themKieuDslam(@RequestBody DanhMucHanhLangDTO hanhlang) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.update(hanhlang));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa hành lang theo hanhlang_id")
    @RequestMapping(value = "/delete", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaDmHanhLangbyId(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteById(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


}
