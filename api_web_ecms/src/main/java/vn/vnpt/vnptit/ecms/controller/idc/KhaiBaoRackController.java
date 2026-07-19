package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DsKhaiBaoRackThiCongRequestDto;
import vn.vnpt.vnptit.ecms.service.idc.KhaiBaoRackService;

import java.util.Map;

@RestController
@RequestMapping("khai-bao-rack")
public class KhaiBaoRackController {

    private final KhaiBaoRackService service;

    public KhaiBaoRackController(KhaiBaoRackService service) {
        this.service = service;
    }

    @PostMapping("/dm-loai-khaibao")
    @Operation(summary = "Lấy danh mục loại khai báo Rack")
    public ResponseEntity<ApiResult> getDmLoaiKhaiBao() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmLoaiKhaiBao());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-donvi-ql")
    @Operation(summary = "Lấy danh mục đơn vị quản lý Rack")
    public ResponseEntity<ApiResult> getDmDonViQl() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmDonViQl());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-sohuu")
    @Operation(summary = "Lấy danh mục sở hữu Rack")
    public ResponseEntity<ApiResult> getDmSoHuu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDmSoHuu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-toanha")
    @Operation(summary = "Lấy danh mục tòa nhà theo IDC")
    public ResponseEntity<ApiResult> getDmToaNha(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            Object raw = body.get("idcId");
            Long idcId = raw != null ? Long.valueOf(raw.toString()) : null;
            rs.setData(service.getDmToaNha(idcId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-matsan")
    @Operation(summary = "Lấy danh mục mặt sàn theo IDC và tòa nhà")
    public ResponseEntity<ApiResult> getDmMatSan(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            Object rawIdc = body.get("idcId");
            Object rawTn = body.get("toanhaId");
            Long idcId = rawIdc != null ? Long.valueOf(rawIdc.toString()) : null;
            Long toanhaId = rawTn != null ? Long.valueOf(rawTn.toString()) : null;
            rs.setData(service.getDmMatSan(idcId, toanhaId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/dm-phong")
    @Operation(summary = "Lấy danh mục phòng/zone theo mặt sàn")
    public ResponseEntity<ApiResult> getDmPhong(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            Object raw = body.get("matsanId");
            Long matsanId = raw != null ? Long.valueOf(raw.toString()) : null;
            rs.setData(service.getDmPhong(matsanId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/chitiet-rack")
    @Operation(summary = "Lấy chi tiết thông tin một Rack theo ID")
    public ResponseEntity<ApiResult> getChiTietRack(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            Object raw = body.get("idChitiet");
            Long idChitiet = raw != null ? Long.valueOf(raw.toString()) : null;
            rs.setData(service.getChiTietRack(idChitiet));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-rack-thaythe")
    @Operation(summary = "Lấy danh sách Rack thay thế có cùng số Unit")
    public ResponseEntity<ApiResult> getDsRackThayThe(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            Object rawIdc = body.get("idcId");
            Object rawRack = body.get("rackId");
            Long idcId = rawIdc != null ? Long.valueOf(rawIdc.toString()) : null;
            Long rackId = rawRack != null ? Long.valueOf(rawRack.toString()) : null;
            rs.setData(service.getDsRackThayThe(idcId, rackId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/ds-rack-thicong")
    @Operation(summary = "Lấy danh sách Rack thi công theo điều kiện tìm kiếm")
    public ResponseEntity<ApiResult> getDsKhaiBaoRackThiCong(@RequestBody DsKhaiBaoRackThiCongRequestDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsKhaiBaoRackThiCong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
