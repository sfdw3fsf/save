package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;

import vn.vnpt.vnptit.ecms.dto.hatang.HaTangCongNgheInDTO;
import vn.vnpt.vnptit.ecms.service.hatang.HaTangCongNgheService;

@Api(value = "HaTangCongNghe Controller", description = "Quản lý kho tài nguyên ảo hoá - Danh mục hạ tầng công nghệ --KHDN-19722")
@RestController
@RequestMapping("hatang/hatang-congnghe")
public class HaTangCongNgheController {

    private final HaTangCongNgheService service;

    public HaTangCongNgheController(HaTangCongNgheService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy tất cả trạng thái hạ tầng công nghệ ")
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

    @Operation(summary = "Lấy trạng thái hạ tầng công nghệ theo id")
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

    @Operation(summary = "Lấy trạng thái hạ tầng công nghệ hiệu lực")
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
    @Operation(summary = "Lấy danh mục hạ tầng")
    @RequestMapping(value = "/dmHaTang", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dmHaTang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDmHaTang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật trạng thái hạ tầng công nghệ ")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody HaTangCongNgheInDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.update(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa trạng thái hạ tầng công nghệ theo khóa chính")
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
