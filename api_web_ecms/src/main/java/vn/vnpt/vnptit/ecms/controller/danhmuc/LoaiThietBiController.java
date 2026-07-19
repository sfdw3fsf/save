package vn.vnpt.vnptit.ecms.controller.danhmuc;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.LoaiThietBiInput;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiThietBiService;

/* Nguyễn Hữu Minh- IT3 :20/05/2021*/
@RestController
@RequestMapping("/danhmuc/loaitbi")
public class LoaiThietBiController {
    private final LoaiThietBiService loaiThietBiService;
    public LoaiThietBiController(LoaiThietBiService loaiThietBiService)
    {
        this.loaiThietBiService = loaiThietBiService;
    }
    @Operation(summary = "Liệt kê danh sách loại thiết bị")
    @RequestMapping(value = "/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietke_loai_thieti() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(loaiThietBiService.lietke_loai_thieti());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy một loại thiết bị", description = "Lấy một loại thiết bị")
    @RequestMapping(value = "/chitiet/{someID}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_loai_thietbi_id( @Valid @PathVariable(value = "someID") int id) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(loaiThietBiService.lay_loai_thietbi_id(id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm một loại thiết bị", description = "Thêm một loại thiết bị")
    @RequestMapping(value = "/them", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_loai_thietbi(@Valid @RequestBody LoaiThietBiInput.LoaiTbiIns loaithietbi) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiThietBiService.them_loai_thietbi(loaithietbi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }
    @Operation(summary = "Sửa một loại thiết bị", description = "Sửa một loại thiết bị")
    @RequestMapping(value = "/capnhat", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> sua_loai_thietbi( @Valid @RequestBody LoaiThietBiInput.LoaiTbiUpd loaithietbi) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(loaiThietBiService.sua_loai_thietbi(loaithietbi));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa một loại thiết bị", description = "Xóa một loại thiết bị")
    @RequestMapping(value = "/xoa/{id}", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_loai_tbi(@Valid @PathVariable(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id =  AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(new ResultOutput(loaiThietBiService.xoa_loai_tbi(id,phanvung_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

