package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.*;
import vn.vnpt.vnptit.ecms.service.hatang.DichVuBackupService;

import java.util.List;

@RestController
@RequestMapping("/hatang/dichvu-backup")
public class DichVuBackupController {

    @Autowired
    private DichVuBackupService dichVuBackupService;

    @Operation(summary = "Luu dich vu backup")
    @PostMapping("/luu")
    public ResponseEntity<ApiResult> luuDichVuBackup(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.luuDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoa dich vu backup")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaDichVuBackup(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.xoaDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay chi tiet dich vu backup")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> getChiTietDichVuBackup(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.getChiTietDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay cau hinh dich vu backup")
    @PostMapping("/cau-hinh")
    public ResponseEntity<ApiResult> getCauHinhDichVuBackup(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.getCauHinhDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Luu cau hinh dich vu backup")
    @PostMapping("/luu-cau-hinh")
    public ResponseEntity<ApiResult> luuCauHinhDichVuBackup(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.luuCauHinhDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Luu cau hinh dich vu backup")
    @PostMapping("/ds-nguon")
    public ResponseEntity<ApiResult> dsNguonBackup(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.layDsNguonBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm nguon theo type")
    @PostMapping("/tim-kiem-nguon")
    public ResponseEntity<ApiResult> timKiemNguon(@RequestBody TimKiemThanhPhanDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.timKiemNguon(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy log lịch sử cấp phát")
    @PostMapping("/log-lichsu-capphat")
    public ResponseEntity<ApiResult> logLichSuCapPhat(@RequestBody DataKeyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.logLichSuCapPhat(input.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dung lượng thực tế")
    @PostMapping("/dungluong-thucte")
    public ResponseEntity<ApiResult> getDungLuongThucTe(@RequestBody DataKeyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.getDungLuongThucTe(input.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy log lịch sử tác động")
    @RequestMapping(value = "/log-lichsu-thaydoi/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> logLichSuThayDoi(@PathVariable("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.logLichSuThayDoi(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dịch vụ backup")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSach(@RequestBody DichVuBackupSearchDTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.layDanhSach(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate danh sách dịch vụ backup")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatchLoaiDVHT(@RequestBody List<DichVuBackupImportDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.validateBatchDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import danh sách dịch vụ backup")
    @RequestMapping(value = "/import-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> importBatchLoaiDVHT(@RequestBody List<DichVuBackupImportDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dichVuBackupService.batchInsertDichVuBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
