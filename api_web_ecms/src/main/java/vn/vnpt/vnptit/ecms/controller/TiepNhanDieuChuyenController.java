package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.LayDsTiepNhanTbiDtoInput;
import vn.vnpt.vnptit.ecms.dto.TNDC_UpdateTrangThaiTiepNhanDtoInput;
import vn.vnpt.vnptit.ecms.service.TiepNhanDieuChuyenService;

@RestController
@RequestMapping("tiepnhan-dieuchuyen")
public class TiepNhanDieuChuyenController {
    private final TiepNhanDieuChuyenService service;

    public TiepNhanDieuChuyenController(TiepNhanDieuChuyenService tiepNhanDieuChuyenService) {
        this.service = tiepNhanDieuChuyenService;
    }

    @Operation(summary = "Phụ trợ  lấy danh sách tiếp nhận thiết bị")
    @RequestMapping(value = "/phutro-layds-tiepnhan-thietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> PhuTroLayDsTiepNhanThietBi(@RequestParam("vttvh_id") int ttvh_id, @RequestParam("vthietbi_id") int thietbi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.PhuTroLayDsTiepNhanThietBi(ttvh_id, thietbi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị cha")
    @RequestMapping(value = "/lay-ds-thietbi-cha", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayDSThietBiCha(@RequestParam("vvattu_id") int vattu_id, @RequestParam("vdonvi_id") int donvi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ThietBiCha(vattu_id, donvi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Phụ trợ  lấy danh sách tiếp nhận thiết bị V3")
    @RequestMapping(value = "/phutro-layds-tiepnhan-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> PhuTroLayDsTiepNhanThietBiV3(@RequestBody LayDsTiepNhanTbiDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.PhuTroLayDsTiepNhanThietBiV3(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin thuộc tính theo vật tư thiết bị")
    @RequestMapping(value = "/lay-tt-thuoctinh-theo-vtu-tbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayThongTinThuocTinhTheoVatTuThietBi(@RequestParam("vattu_id") int vattu_id, @RequestParam("thietbi_id") int thietbi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.LayThongTinThuocTinhTheoVatTuThietBi(vattu_id, thietbi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhập trạng thái tiếp nhận V2")
    @RequestMapping(value = "/phutro-capnhap-trangthai-tiepnhan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> UpdateTrangThaiTiepNhan(@RequestBody TNDC_UpdateTrangThaiTiepNhanDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.UpdateTrangThaiTiepNhan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị con")
    @RequestMapping(value = "/lay-ds-donvicon", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayDSDonViCon(int donvi_id, int loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.LayDSDonViCon(donvi_id, loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị")
    @RequestMapping(value = "/lay-ds-donvi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayDSDonVi(int donvi_id, int loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.LayDSDonVi(donvi_id, loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị V2")
    @RequestMapping(value = "/lay-ds-donvi-v2", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayDSDonViV2(int donvi_id, int loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.LayDSDonViV2(donvi_id, loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
