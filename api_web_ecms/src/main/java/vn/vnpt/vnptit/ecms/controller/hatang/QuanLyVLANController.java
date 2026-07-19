package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.vnptit.ecms.dto.hatang.InsertBatchSubnetVLanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.InsertSubnetVLanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.UpsertVLANDto;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLyVLANService;

@Api(value = "Quản lý VLAN - Controller", description = "Quản lý kho tài nguyên ảo hoá - Quản lý VRF")
@RestController
@RequestMapping("quanly-vlan")
public class QuanLyVLANController {
    private final QuanLyVLANService service;

    public QuanLyVLANController(QuanLyVLANService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy danh sách VLAN theo Zone")
    @PostMapping("/get-vlan-by-zone")
    public ResponseEntity<ApiResult> getZoneByVRF(@RequestBody Map<String, Long> request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.get_vlan_by_zone(request.get("id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResult> searchVLAN(@RequestBody JsonNode searchJsonData) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.searchVLAN(searchJsonData));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertVLAN(@RequestBody UpsertVLANDto request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.upsertVLAN(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResult> deleteVLAN(@RequestBody Map<String, Long> request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteVLAN(request.get("vlanId")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Subnet theo VLAN")
    @PostMapping("/subnet-by-vlan")
    public ResponseEntity<ApiResult> getSubnetByVLAN(@RequestBody Map<String, Long> request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getSubnetByVLan(request.get("id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm subnet")
    @PostMapping("/search-subnet")
    public ResponseEntity<ApiResult> searchSubnet(@RequestBody JsonNode searchJsonData) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.searchSubnet(searchJsonData));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/insert-subnet-vlan")
    public ResponseEntity<ApiResult> insertSubnetVLan(@RequestBody InsertSubnetVLanDto request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.insertSubnetVLan(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/insert-batch-subnet-vlan")
    public ResponseEntity<ApiResult> insertBatchSubnetVLan(@RequestBody InsertBatchSubnetVLanDto request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.insertBatchSubnetVLan(request.getVlanId(), request.getSubnetList()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/delete-subnet-vlan")
    public ResponseEntity<ApiResult> deleteSubnetVLan(@RequestBody Map<String, Long> request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.deleteSubnetVLan(request.get("id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
