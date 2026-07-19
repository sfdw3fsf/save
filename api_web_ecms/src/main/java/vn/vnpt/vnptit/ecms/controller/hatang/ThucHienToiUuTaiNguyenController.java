package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.ThucHienToiUuTaiNguyen;
import vn.vnpt.vnptit.ecms.dto.lay_ds_thietbi_loaitbi_dto;
import vn.vnpt.vnptit.ecms.service.ThemThietBiService;
import vn.vnpt.vnptit.ecms.service.hatang.ThucHienToiUuTaiNguyenService;

@RestController
@RequestMapping("/thuchien_toiuu-tainguyen")
public class ThucHienToiUuTaiNguyenController {
    private  final ThucHienToiUuTaiNguyenService service;

    public ThucHienToiUuTaiNguyenController(ThucHienToiUuTaiNguyenService thucHienToiUuTaiNguyenService) {
        this.service = thucHienToiUuTaiNguyenService;
    }
    @Operation(summary = "Lấy danh sách phiếu tối ưu tài nguyên", description = "Lấy danh sách phiếu tối ưu tài nguyên")
    @RequestMapping(value = "/lay_ds_yeucau_toiuu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_yeucau_toiuu(@RequestBody ThucHienToiUuTaiNguyen input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsYeuCauToiUu(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xác nhân tối ưu tài nguyên", description = "Xác nhận tối ưu tài nguyên")
    @RequestMapping(value = "/xacnhan_toiuu_tainguyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xacnhan_toiuu_tainguyen(@RequestBody ThucHienToiUuTaiNguyen input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xacNhanToiUu(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách máy ảo cần tối ưu", description = "Xác nhận tối ưu tài nguyên")
    @RequestMapping(value = "/danhsach_mayao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhsach_mayao(@RequestBody ThucHienToiUuTaiNguyen input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsMayAo(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật thông tin máy ảo cần tối ưu", description = "Xác nhận tối ưu tài nguyên")
    @RequestMapping(value = "/update_phieu_toiuu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update_phieu_toiuu(@RequestBody ThucHienToiUuTaiNguyen input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updatePhieuToiUu(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
