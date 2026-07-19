package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.QLTBPT_CapNhatVanHanhKMInput;
import vn.vnpt.vnptit.ecms.service.BaoDuongThietBiPhuTroService;
import vn.vnpt.vnptit.ecms.service.LogSerrvice;

import javax.validation.Valid;

@RestController
@RequestMapping("/baoduong-phutro")
public class BaoDuongThietBiPhuTroController {
    private final BaoDuongThietBiPhuTroService service;
    private final LogSerrvice logSerrvice;
    public BaoDuongThietBiPhuTroController(BaoDuongThietBiPhuTroService service, LogSerrvice logSerrvice) {
        this.service = service;
        this.logSerrvice = logSerrvice;
    }
    @Operation(summary = "Lấy danhh sách vật tư")
    @RequestMapping(value = "/lay_ds_vattu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_vattu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_vattu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        logSerrvice.log(null, "/lay_ds_vattu", null , rs);
        return rs.getResponseEntity();
    }
    @Operation(summary = "phutro_lay_ds_vanhanh_km")
    @RequestMapping(value = "/phutro_lay_ds_vanhanh_km/{vanhanh_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> phutro_lay_ds_vanhanh_km(@PathVariable int vanhanh_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_lay_ds_vanhanh_km(vanhanh_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        logSerrvice.log(null, "phutro_lay_ds_vanhanh_km", vanhanh_id , rs);
        return rs.getResponseEntity();
    }
    @Operation(summary = "phutro_lay_ds_khmuc")
    @RequestMapping(value = "/phutro_lay_ds_khmuc/{kieu}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> phutro_lay_ds_khmuc(@PathVariable int kieu) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_lay_ds_khmuc(kieu));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        logSerrvice.log(null, "phutro_lay_ds_khmuc", kieu , rs);
        return rs.getResponseEntity();
    }
    @Operation(summary = "phutro_capnhap_vanhanh_km")
    @RequestMapping(value = "/phutro_capnhap_vanhanh_km", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> phutro_capnhap_vanhanh_km(@Valid @RequestBody QLTBPT_CapNhatVanHanhKMInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.phutro_capnhap_vanhanh_km(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        logSerrvice.log(null, "phutro_capnhap_vanhanh_km", input , rs);
        return rs.getResponseEntity();
    }
}
