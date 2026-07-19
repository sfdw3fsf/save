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
import vn.vnpt.vnptit.ecms.dto.hatang.DeleteRequestDto;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhSachPortChuaDauNoiDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhSachThietBiDauNoiDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.DauNoiPortDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DauNoiThietBiService;

import javax.validation.Valid;

@RestController
@RequestMapping("hatang/daunoi-thietbi")
public class DauNoiThietBiController {
    private final DauNoiThietBiService service;

    public DauNoiThietBiController(DauNoiThietBiService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy danh sách loại thiết bị")
    @RequestMapping(value = "/get-device-types", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDeviceTypes() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDeviceTypes());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị đầu nối")
    @RequestMapping(value = "/get-devices", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDevices(@Valid @RequestBody DanhSachThietBiDauNoiDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.getDevices(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách port chưa đấu nối")
    @RequestMapping(value = "/get-ports-free", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPortFree(@Valid @RequestBody DanhSachPortChuaDauNoiDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.getPortFree(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm đầu nối thiết bị")
    @RequestMapping(value = "/add-link", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> addLink(@Valid @RequestBody DauNoiPortDTO item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.addLink(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa đầu nối thiết bị")
    @RequestMapping(value = "/delete-link", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteLink(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.deleteLink(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin đầu nối thiết bị")
    @RequestMapping(value = "/get-link-info", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getLinkInfo(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.getLinkInfo(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
