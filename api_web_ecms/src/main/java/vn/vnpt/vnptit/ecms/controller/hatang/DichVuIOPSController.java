package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.DichVuIOPSDto;
import vn.vnpt.vnptit.ecms.dto.hatang.GoiDichVuIOPSDto;
import vn.vnpt.vnptit.ecms.service.hatang.DichVuIOPSService;

import java.util.List;

@RestController
@RequestMapping("hatang/dichvu-iops")
public class DichVuIOPSController {
	private final DichVuIOPSService service;

	public DichVuIOPSController(DichVuIOPSService service) {
		this.service = service;
	}
	
    @Operation(summary = "Lấy tất cả dịch vụ IOPS")
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
	
    @Operation(summary = "Lấy dịch vụ IOPS theo khóa chính")
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
	
    @Operation(summary = "Thêm dịch vụ IOPS")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> insert(@Valid @RequestBody DichVuIOPSDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.insert(item.getTen(), item.getGiaTri(), item.getGhiChu(), item.getHieuLuc())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Cập nhật dịch vụ IOPS")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody DichVuIOPSDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.update(item.getId(), item.getTen(), item.getGiaTri(), item.getGhiChu(), item.getHieuLuc())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Xóa dịch vụ IOPS theo khóa chính")
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
	
    @Operation(summary = "Xóa danh sách dịch vụ IOPS theo khóa chính")
    @RequestMapping(value = "/delete-items", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteItems(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
			String msg = "";
			Number[] ids = item.getId();
			if (ids.length == 0)
				msg = "Danh sách dịch vụ IOPS không được để trống";
			else {
				for (Number id : ids) {
					if (id == null || id.intValue() < 1) {
						msg = "Danh sách dịch vụ IOPS có thông tin không hợp lệ";
						break;
					}
				}
			}
			if (msg.equals(""))
	            rs.setData(new ResultOutput(this.service.deleteItems(ids)));
			else
				rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tất cả dịch vụ IOPS v2")
    @RequestMapping(value = "/danh-sach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getListIOPS() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListIOPS());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới/Cập nhật dịch vụ IOPS")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody @Valid GoiDichVuIOPSDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsert(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh sách dịch vụ IOPS")
    @RequestMapping(value = "/xoa-iops", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteIOPS(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
            String msg = "";
            Number[] ids = item.getId();
            if (ids.length == 0)
                msg = "Danh sách dịch vụ IOPS không được để trống";
            else {
                for (Number id : ids) {
                    if (id == null || id.intValue() < 1) {
                        msg = "Danh sách dịch vụ IOPS có thông tin không hợp lệ";
                        break;
                    }
                }
            }
            if (msg.equals(""))
                rs.setData(new ResultOutput(this.service.deleteIOPS(ids)));
            else
                rs.setDataInvalidMessage(msg);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody List<GoiDichVuIOPSDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateBatch(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều IOPS")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody List<GoiDichVuIOPSDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.batchInsert(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
