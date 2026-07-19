package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatLoaiTuDtoIn;
import vn.vnpt.vnptit.ecms.dto.ThemLoaiTuDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiTuService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/loaitu/")
public class LoaiTuController {

    private final LoaiTuService loaiTuService;

    public LoaiTuController(LoaiTuService loaiTuService) {
        this.loaiTuService = loaiTuService;
    }

    @Operation(summary = "Liệt kê danh sách loại tủ", description = "Liệt kê danh sách loại tủ")
    @RequestMapping(value = "/lietke_danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDanhSachLoaiTu() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(loaiTuService.lietKeDanhSachLoaiTu());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới thông tin loại tủ", description = "Thêm mới thông tin loại tủ")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themLoaiTu(@Valid @RequestBody ThemLoaiTuDtoIn themLoaiTuDtoIn) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(loaiTuService.themLoaiTu(themLoaiTuDtoIn));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật thông tin loại tủ", description = "Cập nhật thông tin loại tủ")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatLoaiTu(@Valid @RequestBody CapNhatLoaiTuDtoIn capNhatLoaiTuDtoIn) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(new ResultOutput(loaiTuService.capNhatLoaiTu(capNhatLoaiTuDtoIn)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thông tin loại tủ", description = "Xóa thông tin loại tủ")
    @RequestMapping(value = "/xoa/{loaitu_id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaLoaiTu(@PathVariable("loaitu_id") int loaiTuId) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(new ResultOutput(loaiTuService.xoaLoaiTu(loaiTuId)));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
}
