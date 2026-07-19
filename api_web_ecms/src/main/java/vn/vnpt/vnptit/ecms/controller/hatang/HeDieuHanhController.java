package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.MucDichSuDungCongDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DeleteRequestDto;
import vn.vnpt.vnptit.ecms.service.hatang.HeDieuHanhService;

import javax.validation.Valid;

@RestController
@RequestMapping("hatang/he-dieuhanh")
public class HeDieuHanhController {
    private final HeDieuHanhService service;

    public HeDieuHanhController(HeDieuHanhService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy tất cả danh mục hệ điều hành")
    @RequestMapping(value = "/get-items", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getItems() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getItems());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm danh mục hệ điều hành")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> insert(@Valid @RequestBody MucDichSuDungCongDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.insert(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật danh mục hệ điều hành")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody MucDichSuDungCongDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.update(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục hệ điều hành")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@Valid @RequestBody DeleteRequestDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.delete(item.getIds())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
