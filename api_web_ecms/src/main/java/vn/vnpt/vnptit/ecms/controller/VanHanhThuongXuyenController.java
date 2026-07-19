package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.LayDsTiepNhanTbiDtoInput;
import vn.vnpt.vnptit.ecms.dto.PhuTroTrangThaiTiepNhanDtoInput;
import vn.vnpt.vnptit.ecms.service.VanHanhThuongXuyenService;

@RestController
@RequestMapping("/vanhanh-thuongxuyen")
public class VanHanhThuongXuyenController {
    private final VanHanhThuongXuyenService service;

    public VanHanhThuongXuyenController(VanHanhThuongXuyenService service) {
        this.service = service;
    }
    @Operation(summary = "Phụ trợ - Lấy danh sách tiếp nhận thiết bị v3", description = "Phụ trợ - Lấy danh sách tiếp nhận thiết bị v3")
    @RequestMapping(value = "/phutro-lay-ds-tiepnhan-tbi-v3", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> phutro_lay_ds_tiepnhan_tbi_v3(@RequestBody LayDsTiepNhanTbiDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_lay_ds_tiepnhan_tbi_v3(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Phụ trợ - Lấy danh sách tiếp nhận thiết bị", description = "Phụ trợ - Lấy danh sách tiếp nhận thiết bị")
    @RequestMapping(value = "/phutro-lay-ds-tiepnhan-tbi", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> phutro_lay_ds_tiepnhan_tbi(@RequestBody LayDsTiepNhanTbiDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_lay_ds_tiepnhan_tbi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Phụ trợ - Lấy danh sách theo thiết bị", description = "Phụ trợ - Lấy danh sách theo thiết bị")
    @RequestMapping(value = "/phutro-lay-ds-theo-tbi", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> phutro_lay_ds_theo_tbi(String ma_vt, Integer kieu, Long nhanvien_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_lay_ds_theo_tbi(ma_vt, kieu, nhanvien_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Phụ trợ - Cập nhật trạng thái tiếp nhận(1 thêm, 2 sửa,3 xóa)", description = "Phụ trợ - Cập nhật trạng thái tiếp nhận(1 thêm, 2 sửa,3 xóa)")
    @RequestMapping(value = "/phutro-trangthai-tiepnhan", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> phutro_trangthai_tiepnhan(@RequestBody PhuTroTrangThaiTiepNhanDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_trangthai_tiepnhan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Phụ trợ - Cập nhật donvi_nhan_id ECMS.VANHANH", description = "Phụ trợ - Cập nhật donvi_nhan_id ECMS.VANHANH")
    @RequestMapping(value = "/capnhat-donvinhanid-vanhanh", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_donvinhanid_vanhanh(Long donvi_nhan_id, Long vanhanh_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capnhat_donvinhanid_vanhanh(donvi_nhan_id, vanhanh_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
