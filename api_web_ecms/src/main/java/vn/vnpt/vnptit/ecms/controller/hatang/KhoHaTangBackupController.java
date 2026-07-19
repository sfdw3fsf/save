package vn.vnpt.vnptit.ecms.controller.hatang;

import com.google.common.base.Strings;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangBackupDTO;
import vn.vnpt.vnptit.ecms.service.hatang.KhoHaTangBackupService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("kho-hatang-backup")
public class KhoHaTangBackupController {

    @Autowired
    private KhoHaTangBackupService service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody KhoHaTangBackupDTO dto) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.createOrUpdate(dto));
        } catch (Exception e) {
            rest.setData(e.getMessage());
            e.printStackTrace();
        }
        return rest.getResponseEntity();
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

    @GetMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        rs.setData(this.service.deleteById(id));
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKhoHaTangBackupRelationship(id, "HATANGBACKUP_LICHSU"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thiet-bi")
    public ResponseEntity<ApiResult> getThietBi(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKhoHaTangBackupRelationship(id, "HATANGBACKUP_THIETBI"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/tu-dia")
    public ResponseEntity<ApiResult> getTuDia(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKhoHaTangBackupRelationship(id, "HATANGBACKUP_THIETBITUDIA"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/ip")
    public ResponseEntity<ApiResult> getIP(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKhoHaTangBackupRelationship(id, "HATANGBACKUP_IPADDRESS"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/san-pham-dich-vu")
    public ResponseEntity<ApiResult> getSPDV(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKhoHaTangBackupRelationship(id, "HATANGBACKUP_SPDV"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thietbi-hinhthanh-khohatang")
    public ResponseEntity<ApiResult> getThietBiHinhThanhKhoHaTang(@RequestParam(value = "hatang") String hatang,
                                                                  @RequestParam(value = "idc_id") int idcId,
                                                                  @RequestParam(value = "old_thietbi_ids") String ids) {
        ApiResult rs = new ApiResult();
        try {
            List<Integer> idList = Strings.isNullOrEmpty(ids) ? Arrays.asList() : Arrays.stream(ids.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            rs.setData(this.service.getThietBiHinhThanhKhoHaTang(hatang, idcId, idList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
