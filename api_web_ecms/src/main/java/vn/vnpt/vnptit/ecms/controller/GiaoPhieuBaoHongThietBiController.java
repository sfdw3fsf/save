package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatGiaoPhieuVanHanhDtoInput;
import vn.vnpt.vnptit.ecms.service.GiaoPhieuBaoHongThietBiService;

import javax.validation.Valid;

@RestController
@RequestMapping("giaophieubaohongthietbi")
public class GiaoPhieuBaoHongThietBiController {
    @Autowired
    private GiaoPhieuBaoHongThietBiService giaoPhieuBaoHongThietBiService;

    @Operation(summary = "Lấy danh sách thiết bị theo vận hành id")
    @GetMapping("lay-ds-thietbi-theo-vhanhid/{phieuvh_cha_id}")
    public ResponseEntity<ApiResult> LayDSThietBiTheoVHanhId(@PathVariable int phieuvh_cha_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoPhieuBaoHongThietBiService.LayDSThietBiTheoVHanhId(phieuvh_cha_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trạng thái vận hành")
    @GetMapping("lay-ds-trangthai-vanhanh")
    public ResponseEntity<ApiResult> LayDSTrangThaiVanHanh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoPhieuBaoHongThietBiService.LayDSTrangThaiVanHanh());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị")
    @GetMapping("lay-ds-donvi")
    public ResponseEntity<ApiResult> LayDSDonVi(@RequestParam("phieuvh_id") int phieuvh_id, @RequestParam("vanhanh_id") int vanhanh_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoPhieuBaoHongThietBiService.LayDSDonVi(phieuvh_id, vanhanh_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật giao phiếu vận hành")
    @PostMapping("capnhat-giaophieu-vanhanh")
    public ResponseEntity<ApiResult> CapNhatGiaoPhieuVanHanh(@RequestBody @Valid CapNhatGiaoPhieuVanHanhDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoPhieuBaoHongThietBiService.CapNhatGiaoPhieuVanHanh(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds giao phiếu vận hành đã giao")
    @GetMapping("lay-ds-phieuvanhanh-dagiao/{vanhanh_id}")
    public ResponseEntity<ApiResult> LayDsPhieuVanHanhDaGiao(@PathVariable("vanhanh_id") int vanhanh_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoPhieuBaoHongThietBiService.LayDsPhieuVanHanhDaGiao(vanhanh_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hủy giao")
    @PostMapping("huy-giao")
    public ResponseEntity<ApiResult> HuyGiao(@RequestParam("vanhanh_id") int vanhanh_id, @RequestParam("phieuvh_id") int phieuvh_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoPhieuBaoHongThietBiService.HuyGiao(vanhanh_id, phieuvh_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
