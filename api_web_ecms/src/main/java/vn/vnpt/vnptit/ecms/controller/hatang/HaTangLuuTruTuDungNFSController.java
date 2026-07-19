package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.vnptit.ecms.dto.hatang.LuuTruTuDungNFSDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.AddLinkRequestDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.LuuTruTuDungNFSDTO;
import vn.vnpt.vnptit.ecms.service.hatang.HaTangLuuTruTuDungNFSService;

@RestController
@RequestMapping(value = "hatang/luutru-tudung-nfs")
public class HaTangLuuTruTuDungNFSController {
    @Autowired
    private HaTangLuuTruTuDungNFSService service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody LuuTruTuDungNFSDTO dto) {
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

    @GetMapping(value = "/change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.haTangLuuTruTuDungNFSRelationship(id, "HATANGLUUTRU_LICHSU"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/may-ao/{id}")
    public ResponseEntity<ApiResult> getMayAoList(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.haTangLuuTruTuDungNFSRelationshipV2(id, "HATANGLUUTRU_MAYAO"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hatang-aohoa/{id}")
    public ResponseEntity<ApiResult> getHaTangIDGList(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.haTangLuuTruTuDungNFSRelationshipV2(id, "HATANGLUUTRU_HATANGAOHOA"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/nfs-smb/{id}")
    public ResponseEntity<ApiResult> getTuDiaList(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.haTangLuuTruTuDungNFSRelationshipV2(id, "HATANGLUUTRU_NFSSMB"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thiet-bi/{id}")
    public ResponseEntity<ApiResult> getSanPhamDVList(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.haTangLuuTruTuDungNFSRelationshipV2(id, "HATANGLUUTRU_THIETBI"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm liên kết Máy ảo")
    @PostMapping(path = "/add-vms")
    public ResponseEntity<ApiResult> addVms(@RequestBody AddLinkRequestDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.addVms(dto.getId(), dto.getLinkIds()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm liên kết Hạ tầng ảo hóa/SmartCloud")
    @PostMapping(path = "/add-virtual-infra-and-smart-cloud")
    public ResponseEntity<ApiResult> addVirtualInfraAndSmartCloud(@RequestBody AddLinkRequestDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.addVirtualInfraAndSmartCloud(dto.getId(), dto.getLinkIds()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm liên kết NFS/SMB")
    @PostMapping(path = "/add-nfs")
    public ResponseEntity<ApiResult> addNFS(@RequestBody AddLinkRequestDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.addNFS(dto.getId(), dto.getLinkIds()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm liên kết Thiết bị")
    @PostMapping(path = "/add-devices")
    public ResponseEntity<ApiResult> addDevices(@RequestBody AddLinkRequestDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.addDevices(dto.getId(), dto.getLinkIds()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy DS Máy ảo có thể thêm")
    @GetMapping(path = "/get-source-vms/{id}")
    public ResponseEntity<ApiResult> getSourceVM(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSourceVM(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy DS Hạ tầng ảo hóa/SmartCloud có thể thêm")
    @GetMapping(path = "/get-source-v-smart-cloud/{id}")
    public ResponseEntity<ApiResult> getSourceVSmartCloud(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSourceVSmartCloud(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy DS NFS có thể thêm")
    @GetMapping(path = "/get-source-nfs/{id}")
    public ResponseEntity<ApiResult> getSourceNFS(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSourceNFS(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy DS Thiết bị có thể thêm")
    @GetMapping(path = "/get-source-devices/{id}")
    public ResponseEntity<ApiResult> getSourceDevices(@PathVariable(name = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSourceDevices(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
