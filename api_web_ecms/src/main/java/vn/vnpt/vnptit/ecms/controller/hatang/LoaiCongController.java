package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyGenericInput;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.LoaiCongDto;
import vn.vnpt.vnptit.ecms.service.hatang.LoaiCongService;

@RestController
@RequestMapping("hatang/loai-cong")
public class LoaiCongController {
	private final LoaiCongService service;

	public LoaiCongController(LoaiCongService service) {
		this.service = service;
	}
	
    @Operation(summary = "Lấy tất cả loại cổng")
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
	
    @Operation(summary = "Lấy loại cổng theo khóa chính")
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
	
    @Operation(summary = "Thêm loại cổng")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> insert(@Valid @RequestBody LoaiCongDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.insert(item.getTen(), item.getPhanLoai(), item.getThuocNhom(), item.getCamModule(), item.getTocDoPort(), item.getGhiChu(), item.getHieuLuc())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Cập nhật loại cổng")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody LoaiCongDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.update(item.getId(), item.getTen(), item.getPhanLoai(), item.getThuocNhom(), item.getCamModule(), item.getTocDoPort(), item.getGhiChu(), item.getHieuLuc())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Xóa loại cổng theo khóa chính")
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
	
    @Operation(summary = "Xóa danh sách loại cổng theo khóa chính")
    @RequestMapping(value = "/delete-items", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteItems(@Valid @RequestBody DataKeyGenericInput<Number[]> item) {
        ApiResult rs = new ApiResult();
        try {
			String msg = "";
			Number[] ids = item.getId();
			if (ids.length == 0)
				msg = "Danh sách loại cổng không được để trống";
			else {
				for (Number id : ids) {
					if (id == null || id.intValue() < 1) {
						msg = "Danh sách loại cổng có thông tin không hợp lệ";
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

}
