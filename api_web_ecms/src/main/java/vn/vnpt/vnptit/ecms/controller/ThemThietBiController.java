package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.ThemThietBiService;

@RestController
@RequestMapping("/them_thietbi")
public class ThemThietBiController {

    private final ThemThietBiService service;

    public ThemThietBiController(ThemThietBiService themThietBiService) {
        this.service = themThietBiService;
    }

    @Operation(summary = "Lấy danh sách thiết bị của loại thiết bị", description = "Lấy danh sách thiết bị của loại thiết bị")
    @RequestMapping(value = "/lay_ds_thietbi_loaitbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_thietbi_loaitbi(@RequestBody lay_ds_thietbi_loaitbi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_thietbi_loaitbi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị của loại đơn vị", description = "Lấy danh sách đơn vị của loại đơn vị")
    @RequestMapping(value = "/lay_ds_donvi_cua_loai_dvi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_donvi_cua_loai_dvi(@RequestBody lay_ds_donvi_cua_loai_dvi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_donvi_cua_loai_dvi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị con của loại đơn vị", description = "Lấy danh sách đơn vị con của loại đơn vị")
    @RequestMapping(value = "/lay_ds_donvi_con_loaidv", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_donvi_con_loaidv(@RequestBody lay_ds_donvi_cua_loai_dvi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_donvi_con_loaidv(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị của loại đơn vị", description = "Lấy danh sách đơn vị của loại đơn vị")
    @RequestMapping(value = "/lay_ds_donvi_loai_dvi_v2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_donvi_loai_dvi_v2(@RequestBody lay_ds_donvi_cua_loai_dvi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_donvi_loai_dvi_v2(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tham số mặc định", description = "Lấy danh sách tham số mặc định")
    @RequestMapping(value = "/lay_ds_thamso_md", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_thamso_md() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_thamso_md());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trạm mới", description = "Kiểm tra trạm mới")
    @RequestMapping(value = "/kiemtra_csht_tram_moi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_csht_tram_moi(@RequestBody kiemtra_csht_tram_moi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.kiemtra_csht_tram_moi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Thêm thiết bị", description = "Thêm thiết bị")
    @RequestMapping(value = "/phutro_them_tbi_v3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> phutro_them_tbi_v3(@RequestBody phutro_them_tbi_v3_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_them_tbi_v3(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật thuộc tính thiết bị", description = "Cập nhật thuộc tính thiết bị")
    @RequestMapping(value = "/update_vattu_thuoctinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update_vattu_thuoctinh(@RequestBody update_vattu_thuoctinh_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.update_vattu_thuoctinh(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới địa chỉ thiết bị", description = "Thêm mới địa chỉ thiết bị")
    @RequestMapping(value = "/themmoi_diachi_thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themmoi_diachi_thietbi(@RequestBody themmoi_diachi_thietbi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.themmoi_diachi_thietbi(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị", description = "Lấy danh sách thiết bị")
    @RequestMapping(value = "/lay_ds_thietbi_loaithietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_thietbi_loaithietbi(@RequestParam("vattu_id") Long vattu_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_thietbi_loaithietbi(vattu_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin thuộc tính theo nhóm thiết bị", description = "Lấy thông tin thuộc tính theo nhóm thiết bị")
    @RequestMapping(value = "/lay_thongtin_thuoctinh_theo_nhom_thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_thongtin_thuoctinh_theo_nhom_thietbi(@RequestBody lay_thongtin_thuoctinh_theo_nhom_thietbi_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_thongtin_thuoctinh_theo_nhom_thietbi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị cha", description = "Lấy danh sách thiết bị cha")
    @RequestMapping(value = "/lay_ds_tbi_cha", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_tbi_cha(@RequestBody lay_ds_tbi_cha_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_tbi_cha(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra đối tượng cần nhập số lượng", description = "Kiểm tra đối tượng cần nhập số lượng")
    @RequestMapping(value = "/phutro_lay_doituong_nhap_sl", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> phutro_lay_doituong_nhap_sl(@RequestBody phutro_lay_doituong_nhap_sl_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_lay_doituong_nhap_sl(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách giá trị của thuộc tính", description = "Lấy danh sách giá trị của thuộc tính")
    @RequestMapping(value = "/lay_ds_giatri_thuoctinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_giatri_thuoctinh(@RequestBody lay_ds_giatri_thuoctinh_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_giatri_thuoctinh(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thuộc tính", description = "Lấy danh sách thuộc tính")
    @RequestMapping(value = "/lay_ds_thuoctinh_theo_ttid", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_thuoctinh_theo_ttid(@RequestBody lay_ds_thuoctinh_theo_ttid_dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_thuoctinh_theo_ttid(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
