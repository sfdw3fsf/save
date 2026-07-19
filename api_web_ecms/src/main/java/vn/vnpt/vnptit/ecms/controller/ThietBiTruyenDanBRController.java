package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.ThietBiTruyenDanBRDto;
import vn.vnpt.vnptit.ecms.service.ThietBiChuyenMachService;
import vn.vnpt.vnptit.ecms.service.ThietBiTruyenDanService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/thietbi-truyendan")
public class ThietBiTruyenDanBRController {
    private final ThietBiTruyenDanService service;

    public ThietBiTruyenDanBRController(ThietBiTruyenDanService thietBiChuyenMachService) {
        this.service = thietBiChuyenMachService;
    }
    @Operation(summary = "Lấy Bras")
    @RequestMapping(value = "bras-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layBras(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layBras(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layBrasCard")
    @RequestMapping(value = "bras-card-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layBrasCard(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layBrasCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layBrasPort")
    @RequestMapping(value = "bras-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layBrasPort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layBrasPort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layDslamGponSwitch")
    @RequestMapping(value = "dslam-theo-id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDslamGponSwitch(@NotNull @Valid @RequestParam("id") int id,
                                             @NotNull @Valid @RequestParam("loaitbi") int loaitbi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDslamGponSwitch(id,loaitbi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layGponCard")
    @RequestMapping(value = "gpon-card-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layGponCard(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layGponCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layGponPort")
    @RequestMapping(value = "gpon-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layGponPort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layGponPort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layManeCard")
    @RequestMapping(value = "mane-card-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layManeCard(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layManeCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layManePort")
    @RequestMapping(value = "mane-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layManePort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layManePort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layManeModule")
    @RequestMapping(value = "mane-module-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layManeModule(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layManeModule(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layManeModulePort")
    @RequestMapping(value = "mane-module-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layManeModulePort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layManeModulePort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "laySwitchCard")
    @RequestMapping(value = "switch-card-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laySwitchCard(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.laySwitchCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "laySwitchPort")
    @RequestMapping(value = "switch-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laySwitchPort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.laySwitchPort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "laySwitchCardPort")
    @RequestMapping(value = "switch-card-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laySwitchCardPort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.laySwitchCardPort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layDsLamFrame")
    @RequestMapping(value = "dslam-frame-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLamFrame(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsLamFrame(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layDsLamShelf")
    @RequestMapping(value = "dslam-shelf-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLamShelf(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsLamShelf(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layDslamCard")
    @RequestMapping(value = "dslam-card-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDslamCard(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDslamCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layDsLamPort")
    @RequestMapping(value = "dslam-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLamPort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsLamPort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layDsLamModule")
    @RequestMapping(value = "dslam-module-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLamModule(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsLamModule(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy Bras")
    @RequestMapping(value = "dslam-module-port-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLamModulePort(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsLamModulePort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layFrameTruyenDan")
    @RequestMapping(value = "frame-td-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layFrameTruyenDan(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layFrameTruyenDan(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layFrameTruyenDan")
    @RequestMapping(value = "frame-card-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layFrameCard(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layFrameCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Odf")
    @RequestMapping(value = "odf-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layOdf(@PathVariable int  id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layOdf(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy ds Port của Switch theo loại")
    @RequestMapping(value = "port-switch-theo-loai", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layPortSwitchTheoLoai(@Valid @RequestBody ThietBiTruyenDanBRDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layPortSwitchTheoLoai(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Check Port switch")
    @RequestMapping(value = "check-port-switch/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> checkPortSwitch(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkPortSwitch(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Port Bras Card sử dụng")
    @RequestMapping(value = "port-bras-card-sd/{cardbras_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layPortBrasCardSD(@PathVariable int cardbras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layPortBrasCardSD(cardbras_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin thiết bị theo bras card")
    @RequestMapping(value = "ds-thietbi-theo-bras-card/{cardbras_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layPortTheoBrasCard(@PathVariable int cardbras_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layPortTheoBrasCard(cardbras_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
