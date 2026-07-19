package vn.vnpt.vnptit.ecms.controller.hatang;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.TaiSanThietBiRequestDTO;
import vn.vnpt.vnptit.ecms.service.hatang.TaiSanThietBiService;

@RestController
@RequestMapping("/hatang/taisan-thietbi")
public class TaiSanThietBiController {
    private final TaiSanThietBiService service;

    public TaiSanThietBiController(TaiSanThietBiService service) {
        this.service = service;
    }

    @Operation(summary = "Cập nhật danh sách nhân viên quản lý thiết bị")
    @PostMapping("/nhan-vien/batch")
    public ResponseEntity<ApiResult> saveNhanVienBatch(@RequestBody Map<String, Object> payload) {
        ApiResult res = new ApiResult();
        try {
            Long thietbiId = Long.valueOf(payload.get("thietbiId").toString());
            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");

            res.setData(service.saveThietBiNhanVienBatch(thietbiId, items));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Tạo mới hoặc cập nhật tài sản thiết bị")
    @PostMapping("")
    public ResponseEntity<ApiResult> saveTaiSanThietBi(@RequestBody TaiSanThietBiRequestDTO dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.saveTaiSanThietBi(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên quản lý thiết bị")
    @GetMapping("/nhan-vien/{thietbiId}")
    public ResponseEntity<ApiResult> getNhanVien(@PathVariable Long thietbiId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getThietBiNhanVien(thietbiId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin tài sản thiết bị")
    @GetMapping("/{thietbiId}")
    public ResponseEntity<ApiResult> getTaiSanThietBi(@PathVariable Long thietbiId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getThietBiById(thietbiId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "đồng bộ đơn vị quản lý từ đơn vị lắp đặt thiết bị")
    @GetMapping("/dong-bo-idc/{thietbiId}")
    public ResponseEntity<ApiResult> getIDCLapDat(@PathVariable Long thietbiId) {
        ApiResult res = new ApiResult();
        try {
            res.setData(service.getIDCLapDat(thietbiId));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

}
