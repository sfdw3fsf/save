package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.IPAddressDTO;
import vn.vnpt.vnptit.ecms.dto.idc.CongKetNoiThietBiDto;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLyDiaChiIPService;

@Api(value = "QuanLyDiaChiIP Controller",
        description = "Quản lý kho tài nguyên ảo hoá - Quản lý Địa chỉ IP (IP Address)")
@RestController
@RequestMapping("hatang/quanly-diachiip")
public class QuanLyDiaChiIPController {
    @Autowired
    private QuanLyDiaChiIPService quanLyDiaChiIPService;

    @Operation(summary = "Lấy dữ liệu selection")
    @GetMapping(value = "/selection-data")
    public ResponseEntity<ApiResult> selectionItems() {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(quanLyDiaChiIPService.selectionItems());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @Operation(summary = "Insert hoặc Update địa chỉ ip")
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody IPAddressDTO dto) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(quanLyDiaChiIPService.createOrUpdate(dto));
        } catch (AppSqlException e) {
            rest.setException(e);
            e.printStackTrace();
        }
        return rest.getResponseEntity();

    }

    @Operation(summary = "Lấy danh sách IP Address")
    @GetMapping(value = "/get-all")
    public ResponseEntity<ApiResult> getAll() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.quanLyDiaChiIPService.getAll());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách lịch sử thay đổi theo ip address id")
    @GetMapping(value = "/get-change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")Number ipAddressId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.quanLyDiaChiIPService.getChangeHistoryById(ipAddressId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá IP Address")
    @GetMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        rs.setData(this.quanLyDiaChiIPService.deleteIPAddress(id));
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/health-check")
    public String healthCheck() {
        return "OK";
    }

    @Operation(summary = "Danh sách IpAddress")
    @PostMapping("/danh-sach")
    public ResponseEntity<ApiResult> getListIP(@RequestBody IPAddressDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDiaChiIPService.getListIPAddress(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin chi tiết IP Address")
    @PostMapping("/chitiet")
    public ResponseEntity<ApiResult> getChiTietIP(@RequestBody IPAddressDTO input) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(quanLyDiaChiIPService.getChiTietIP(input));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Lưu thông tin IP Address")
    @PostMapping("/save")
    public ResponseEntity<ApiResult> save(@RequestBody IPAddressDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDiaChiIPService.saveIpAddress(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Subnet theo Hạ tầng mạng, loại IP và phân loa IP")
    @PostMapping("/ds-subnet")
    public ResponseEntity<ApiResult> getListSubnet(@RequestBody IPAddressDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyDiaChiIPService.getListSubnet(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
