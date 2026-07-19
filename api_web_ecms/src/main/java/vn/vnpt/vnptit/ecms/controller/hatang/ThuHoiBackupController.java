package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.YKienThuHoiBackupDTO;
import vn.vnpt.vnptit.ecms.service.hatang.ThuHoiBackUpService;


@RestController
@RequestMapping("hatang/thu-hoi-backup")
public class ThuHoiBackupController {
    private final ThuHoiBackUpService thuHoiBackUpService;
    public ThuHoiBackupController(ThuHoiBackUpService service) {
        this.thuHoiBackUpService = service;
    }


    @Operation(summary = "Danh sách Thu hồi Backup")
    @RequestMapping(value = "/danhsach-thuhoi-backup", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhSachThuHoiBackUp() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiBackUpService.getDSThuHoiBackUp());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Thông tin yêu cầu thu hồi")
    @GetMapping(value = "/danhsach-yeucau/{id}")
    public ResponseEntity<ApiResult> danhSachYeuCau(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiBackUpService.getDSYeuCauThuHoi(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoàn thành Thu hồi Backup")
    @RequestMapping(value = "/hoanthanh-thuhoi-backup/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> hoanThanhThuHoiBackup(@PathVariable(name = "id") Long id,
                                                           @RequestBody YKienThuHoiBackupDTO yKien) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiBackUpService.hoanThanhThuHoiBackup(id, yKien));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
