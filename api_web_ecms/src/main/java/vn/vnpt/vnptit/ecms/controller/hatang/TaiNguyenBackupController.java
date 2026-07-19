package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.vnptit.ecms.dto.hatang.BackupMayAoDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.TaiNguyenBackupDTO;
import vn.vnpt.vnptit.ecms.service.hatang.TaiNguyenBackupService;

@RestController
@RequestMapping(value = "hatang/tainguyen-backup")
public class TaiNguyenBackupController {
    @Autowired
    private TaiNguyenBackupService service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody TaiNguyenBackupDTO dto) {
        ApiResult rest = new ApiResult();
        boolean valid = dto.valid();
        if (!valid) {
            rest.setData(ModelValidation.INVALID);
            return rest.getResponseEntity();
        }
        try {
            rest.setData(service.createOrUpdate(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam(value = "id") Number id) {
        ApiResult rs = new ApiResult();
        rs.setData(this.service.deleteById(id));
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<ApiResult> getAll() {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @GetMapping(value = "/change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.taiNguyenBackupRelationship(id, "BACKUP_LICHSU"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/may-ao")
    public ResponseEntity<ApiResult> getMayAoList(@RequestParam(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.taiNguyenBackupRelationship(id, "BACKUP_MAYAO"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/bien-dong")
    public ResponseEntity<ApiResult> getHaTangIDGList(@RequestParam(value = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.taiNguyenBackupRelationship(id, "BACKUP_BIENDONG"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/phieu-capphat")
    public ResponseEntity<ApiResult> getPhieuCapPhat(@RequestParam(value = "trangthai") int trangthai) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getPhieuCapTheoTrangThai(trangthai));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/danhsach-mayao")
    public ResponseEntity<ApiResult> getDanhSachMayAo(@RequestParam(value = "nameSearch") String nameSearch,
            @RequestParam(value = "backupId") Long backupId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachMayAo(nameSearch, backupId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping(value = "/them-mayao-backup")
    public ResponseEntity<ApiResult> themMayAoBackup(@RequestBody BackupMayAoDTO dto) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.themMayAoBackup(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @PostMapping(value = "/xoa-mayao-backup")
    public ResponseEntity<ApiResult> xoaMayAoBackup(@RequestBody BackupMayAoDTO dto) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.xoaMayAoBackup(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }
}
