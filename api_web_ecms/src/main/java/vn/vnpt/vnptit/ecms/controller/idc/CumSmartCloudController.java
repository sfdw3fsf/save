package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.CumSmartCloudDto;
import vn.vnpt.vnptit.ecms.service.idc.CumSmartCloudService;

@RestController
@RequestMapping("/cum-smartcloud")
public class CumSmartCloudController {
    @Autowired
    private CumSmartCloudService smartCloudService;

    @Operation(summary = "Lấy Danh sách Cụm SmartCloud")
    @PostMapping("/danhsach")
    public ResponseEntity<ApiResult> getDanhSachCumSmartCloud(@RequestBody CumSmartCloudDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(smartCloudService.getDanhSachCumSmartCloud(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Cụm SmartCloud")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> deleteCumSmartCloud(@RequestBody CumSmartCloudDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(smartCloudService.deleteCumSmartCloud(dto.getCumHTId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Đồng bộ và Lưu DB Cụm SmartCloud")
    @PostMapping("/sync-save-cumsmartcloud")
    public ResponseEntity<ApiResult> syncAndSaveFromGateway(@RequestBody CumSmartCloudDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(smartCloudService.syncAndSaveFromGateway(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Chi tiết Cụm SmartCloud")
    @PostMapping("/chitiet")
    public ResponseEntity<ApiResult> getChiTietCumSmartCloud(@RequestBody CumSmartCloudDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(smartCloudService.getChiTietCumHT(dto.getCumHTId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Năng lực Cụm SmartCloud")
    @PostMapping("/nangluc")
    public ResponseEntity<ApiResult> getNangLucCumHT(@RequestBody CumSmartCloudDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(smartCloudService.getNangLucCumHT(dto.getCumHTId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Năng lực Lưu trữ Cụm SmartCloud")
    @PostMapping("/nanglucluutru")
    public ResponseEntity<ApiResult> getNangLucLuuTruCumHT(@RequestBody CumSmartCloudDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(smartCloudService.getNangLucLuuTruCumHT(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

