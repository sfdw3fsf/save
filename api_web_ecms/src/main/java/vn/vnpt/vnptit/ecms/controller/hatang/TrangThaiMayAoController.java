package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
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

import vn.vnpt.vnptit.ecms.dto.hatang.TrangThaiMayAoInDto;
import vn.vnpt.vnptit.ecms.service.hatang.TrangThaiMayAoService;

import javax.validation.Valid;

@Api(value = "TrangThaiMayAo Controller", description = "Quản lý kho tài nguyên ảo hoá - Danh mục trạng thái máy ảo -- KHDN-20611")
@RestController
@RequestMapping("hatang/trangthai_mayao")
public class TrangThaiMayAoController {
    private final TrangThaiMayAoService service;

    public TrangThaiMayAoController(TrangThaiMayAoService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy tất cả trạng thái máy ảo")
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

    @Operation(summary = "Lấy trạng thái máy ảo theo id")
    @RequestMapping(value = "/get-item", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getItem(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getItem(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy trạng thái máy ảo hiệu lực")
    @RequestMapping(value = "/getValid", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getValid() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getValid());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật trạng thái máy ảo")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody TrangThaiMayAoInDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.update(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa trạng thái máy ảo theo khóa chính")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.delete(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
