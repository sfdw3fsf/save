package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.TiepNhanLuuKhoTBPTService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/tiepnhan_luukho")
public class TiepNhanLuuKhoTBPTController {
    private final TiepNhanLuuKhoTBPTService service;

    public TiepNhanLuuKhoTBPTController(TiepNhanLuuKhoTBPTService tiepNhanLuuKhoTBPTService) {
        this.service = tiepNhanLuuKhoTBPTService;
    }

    @Operation(summary = "Danh sách phụ trợ theo thiết bị")
    @RequestMapping(value = "/ds-phutro-theo-thietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachPhuTroTheoThietBi(@RequestParam("maVT") String maVT, @RequestParam("kieu") int kieu) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsPhuTroTheoThietBi(maVT, kieu));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin thuộc tính theo vật tư thiết bị")
    @RequestMapping(value = "/tt-thuoctinh-theo-vattu-thietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> TTThuocTinhVatTuThietBi(@RequestParam("vatTuId") int vatTuId, @RequestParam("thietBiId") int thietBiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ttThuocTinhTheoVatTuThietBi(vatTuId, thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tnhan thiết bị v3")
    @RequestMapping(value = "/ds-tnhan-thietbi-v3", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachTNhanThietBiv3(@RequestParam("thietBiId") int thietBiId, @RequestParam("loaiVhId") int loaiVhId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsTNhanThietBiv3(thietBiId, loaiVhId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tnhan thiết bị")
    @RequestMapping(value = "/ds-tnhan-thietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachTNhanThietBi(@RequestParam("thietBiId") int thietBiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsTNhanThietBi(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật trạng thái tiếp nhận")
    @RequestMapping(value = "/update-trangthai-tiepnhan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> UpdateTrangThaiTiepNhan(@Valid @RequestBody TNLK_UpdateTrangThaiTiepNhanInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updateTrangThaiTiepNhan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật đơn vị vận hành")
    @RequestMapping(value = "/update-donvi-vanhanh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> UpdateDonViVanHanh(@Valid @RequestBody TNLK_UpdateDonViVanHanhInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updateDonViVanHanh(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật mô tả giao phiếu vận hành")
    @RequestMapping(value = "/update-mota-giaophieu-vh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> UpdateMoTaGiaoPhieuVh(@Valid @RequestBody TNLK_UpdateMoTaGiaoPhieuVHInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updateMoTaGiaoPhieuVh(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
