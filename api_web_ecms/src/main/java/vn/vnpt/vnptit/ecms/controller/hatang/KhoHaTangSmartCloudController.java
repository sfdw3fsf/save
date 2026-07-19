package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangSmartCloudDTO;
import vn.vnpt.vnptit.ecms.service.hatang.KhoHaTangSmartCloudService;

@RestController
@RequestMapping(value = "smart-cloud")
public class KhoHaTangSmartCloudController {
    @Autowired
    KhoHaTangSmartCloudService service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody KhoHaTangSmartCloudDTO dto) {
        ApiResult rest = new ApiResult();
        dto.valid();
        try {
            rest.setData(service.createOrUpdate(dto));
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

    @Operation(summary = "Lấy thông tin hạ tầng cloud")
    @RequestMapping(value = "/get-item", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getItem(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getItem(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.smartCloudRelationship(id, "HATANGSMARTCLOUD_LICHSU"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/may-ao")
    public ResponseEntity<ApiResult> getMayAoList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.smartCloudRelationship(id, "HATANGSMARTCLOUD_MAYAO"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hatang-idg")
    public ResponseEntity<ApiResult> getHaTangIDGList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.smartCloudRelationship(id, "HATANGSMARTCLOUD_HATANGIDG"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/tu-dia")
    public ResponseEntity<ApiResult> getTuDiaList(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.smartCloudRelationship(id, "HATANGSMARTCLOUD_TUDIA"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/sanpham-dichvu")
    public ResponseEntity<ApiResult> getSanPhamDVList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.smartCloudRelationship(id, "HATANGSMARTCLOUD_SPDV"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thietbi-daunoi")
    public ResponseEntity<ApiResult> getThietBiList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.smartCloudRelationship(id, "HATANGSMARTCLOUD_THIETBI"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
