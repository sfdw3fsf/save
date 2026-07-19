package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangAoHoaCloudDTO;
import vn.vnpt.vnptit.ecms.service.hatang.KhoHaTangAoHoaCloudService;

@RestController
@RequestMapping(value = "aohoa-cloud")
public class KhoHaTangAoHoaCloudController {
    @Autowired
    KhoHaTangAoHoaCloudService service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody KhoHaTangAoHoaCloudDTO dto) {
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

    @Operation(summary = "Lấy chi tiết hạ tầng ảo hóa theo id")
    @RequestMapping(value = "/get-item", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getItem(@Valid @RequestBody DataKeyInput item) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.getItem(item.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @GetMapping(value = "/change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.aoHoaCloudRelationship(id, "HATANGAOHOA_LICHSU"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/may-ao")
    public ResponseEntity<ApiResult> getMayAoList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.aoHoaCloudRelationship(id, "HATANGAOHOA_MAYAO"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hatang-idg")
    public ResponseEntity<ApiResult> getHaTangIDGList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.aoHoaCloudRelationship(id, "HATANGAOHOA_HATANGIDG"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thiet-bi")
    public ResponseEntity<ApiResult> getTuDiaList(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.aoHoaCloudRelationship(id, "HATANGAOHOA_THIETBI"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/sanpham-dichvu")
    public ResponseEntity<ApiResult> getSanPhamDVList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.aoHoaCloudRelationship(id, "HATANGAOHOA_SPDV"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thietbi-tudia")
    public ResponseEntity<ApiResult> getThietBiList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.aoHoaCloudRelationship(id, "HATANGAOHOA_THIETBITUDIA"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
