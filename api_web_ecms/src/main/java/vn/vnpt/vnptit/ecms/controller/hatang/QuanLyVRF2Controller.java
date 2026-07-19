package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.VRFDto;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLyVRF2Service;

@Api(value = "Quản lý VRF - Controller", description = "Quản lý kho tài nguyên ảo hoá - Quản lý VRF")
@RestController
@RequestMapping("quanly-vrf")
public class QuanLyVRF2Controller {
    @Autowired
    private QuanLyVRF2Service service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody VRFDto dto) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.createOrUpdate(dto));
        } catch (Exception e) {
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
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")Number vrfId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getVRFDanhMucSuDung(vrfId, "VRF_LICHSU"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hatangmang")
    public ResponseEntity<ApiResult> vrfHaTangMangListById(@RequestParam(value = "id")Number vrfId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getVRFDanhMucSuDung(vrfId, "VRF_HATANGMANG"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/subnet")
    public ResponseEntity<ApiResult> vrfSubnetListById(@RequestParam(value = "id")Number vrfId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getVRFDanhMucSuDung(vrfId, "VRF_SUBNET"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/vlan")
    public ResponseEntity<ApiResult> vrfVLANListById(@RequestParam(value = "id")Number vrfId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getVRFDanhMucSuDung(vrfId, "VRF_VLAN"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/zone")
    public ResponseEntity<ApiResult> vrfZoneListById(@RequestParam(value = "id")Number vrfId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getVRFDanhMucSuDung(vrfId, "VRF_ZONE"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/thietbimang-by-idc-id")
    public ResponseEntity<ApiResult> thietBiMangByIDCID(@RequestParam(value = "id")Number IDCID) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThietBiMangByIDCID(IDCID, "THIETBIMANG_BY_IDC_ID"));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hatangmang-detail")
    public ResponseEntity<ApiResult> haTangMangDetailById(@RequestParam(value = "id")Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getHaTangMangById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
