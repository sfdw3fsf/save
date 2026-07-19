package vn.vnpt.vnptit.ecms.controller.hatang;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.CumHaTangAoHoaService;
import vn.vnpt.vnptit.ecms.dto.hatang.ThietBiCumDto;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchThietBiV2Dto;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchHistorySyncMayAoDto;

@RestController
@RequestMapping("/cum-ao-hoa")
public class CumHaTangAoHoaController {

    @Autowired
    private CumHaTangAoHoaService service;

    @Operation(summary = "Lấy danh sách thiết bị theo cụm ảo hóa")
    @RequestMapping(value = "/thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachThietBiCum(@RequestParam("cumId") Long cumId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachThietBiCum(cumId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị v2")
    @RequestMapping(value = "/search-thiet-bi-v2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachThietBiV2(@RequestBody(required = false) SearchThietBiV2Dto dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getDanhSachThietBiV2(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách vùng dữ liệu (DATASTORE) theo thiết bị")
    @RequestMapping(value = "/vung-du-lieu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachVungDuLieuByThietBi(@RequestParam("thietBiId") Long thietBiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachVungDuLieuByThietBi(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm danh sách thiết bị vào cụm ảo hóa")
    @RequestMapping(value = "/them-thiet-bi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> insertBatchThietBiCum(@RequestBody ThietBiCumDto input) {
        ApiResult rs = new ApiResult();
        try {
            String idsStr = "";
            if (input.getThietbiIds() != null) {
                idsStr = input.getThietbiIds().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
            }
            rs.setData(service.insertBatchThietBiCum(input.getCumId(), idsStr));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (IllegalArgumentException ex) {
            rs.setMessage(ex.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gỡ thiết bị khỏi cụm ảo hóa")
    @RequestMapping(value = "/go-thiet-bi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteThietBiCum(@RequestBody Map<String, Object> payload) {
        ApiResult rs = new ApiResult();
        try {
            Long cumId = Long.valueOf(payload.get("cumId").toString());
            String idsStr = payload.get("idsStr").toString();
            rs.setData(service.deleteThietBiCum(cumId, idsStr));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách máy ảo của cụm hạ tầng")
    @RequestMapping(value = "/may-ao", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachMayAoCum(@RequestParam("cumId") Long cumId, @RequestParam("type") Integer type) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachMayAoCum(cumId, type));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tài nguyên khác (dịch vụ hạ tầng) của cụm")
    @RequestMapping(value = "/tai-nguyen-khac", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getTaiNguyenKhacCum(@RequestParam("cumId") Long cumId, @RequestParam("type") Integer type) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getTaiNguyenKhacCum(cumId, type));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // ==== Trường hợp cụm HT IDG

    @Operation(summary = "Lấy danh sách máy ảo của cụm hạ tầng IDG")
    @RequestMapping(value = "/may-ao-idg", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachMayAoIDG(@RequestParam("cumId") Long cumId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachMayAoIDG(cumId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị và máy ảo của cụm hạ tầng IDG")
    @RequestMapping(value = "/all-thiet-bi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachThietBiMayAo(@RequestParam("cumId") Long cumId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachThietBiMayAo(cumId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tài nguyên IDG của cụm hạ tầng IDG")
    @RequestMapping(value = "/tai-nguyen-idg", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDanhSachTaiNguyenIDG(@RequestParam("cumId") Long cumId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhSachTaiNguyenIDG(cumId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // =================== ĐỒNG BỘ TỪ VCENTER =====================

    @Operation(summary = "Đồng bộ danh sách máy ảo từ vCenter")
    @RequestMapping(value = "/sync-vms", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> syncVMs(@RequestParam("clusterCode") String clusterCode) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.syncVMs(clusterCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi đồng bộ danh sách máy ảo từ vCenter: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử đồng bộ các máy ảo của cụm")
    @RequestMapping(value = "/sync-vm-history", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> syncVMHistory(@RequestBody SearchHistorySyncMayAoDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.syncVMHistory(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ thủ công năng lực Cụm Hạ tầng SmartCloud")
    @RequestMapping(value = "/sync-smartcloud-capacity", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> syncSmartCloudCapacity(@RequestBody Map<String, Object> payload) {
        ApiResult rs = new ApiResult();
        try {
            String regionCode = payload.get("regionCode").toString();
            rs.setData(service.syncNangLucCumSmartCloud(regionCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi đồng bộ: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
