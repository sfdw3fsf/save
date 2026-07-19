package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.HaTangAoHoaSuDungService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/su-dung")
public class HaTangAoHoaSuDungController {
    private final HaTangAoHoaSuDungService aoHoaSuDungService;

    public HaTangAoHoaSuDungController(HaTangAoHoaSuDungService aoHoaSuDungService) {
        this.aoHoaSuDungService = aoHoaSuDungService;
    }

    @Operation(summary = "hạ tầng mạng zone sử dụng")
    @GetMapping("/zone-ha-tang-mang")
    public ResponseEntity<ApiResult> listZoneHaTangMang(@Valid @RequestParam("zoneId") Long zoneId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("ZONE-HA-TANG-MANG", zoneId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Zone VLAN ")
    @GetMapping("/zone-vlan")
    public ResponseEntity<ApiResult> listZoneVlan(@Valid @RequestParam("zoneId") Long zoneId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("ZONE-VLAN", zoneId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Zone Subnet ")
    @GetMapping("/zone-subnet")
    public ResponseEntity<ApiResult> listZoneSubnet(@Valid @RequestParam("zoneId") Long zoneId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("ZONE-SUBNET", zoneId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    // Phần lịch sử tác động
    @Operation(summary = "Lịch sử tác động Zone")
    @GetMapping("/zone-history")
    public ResponseEntity<ApiResult> listZoneHistory(@Valid @RequestParam("zoneId") Long zoneId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHistoryAction("ZONE", zoneId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Hạ tầng mạng ảo hóa")
    @GetMapping("/ha-tang-mang-ao-hoa")
    public ResponseEntity<ApiResult> listHaTangMangAoHoa(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("HATANG-AOHOA", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng smartcloud")
    @GetMapping("/ha-tang-mang-smartcloud")
    public ResponseEntity<ApiResult> listHaTangMangSmartCloud(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("HATANG-SMART-CLOUD", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Hạ tầng mạng IDG")
    @GetMapping("/ha-tang-mang-idg")
    public ResponseEntity<ApiResult> listHaTangMangIdg(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("HATANG-IDG", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng Subnet")
    @GetMapping("/ha-tang-mang-subnet")
    public ResponseEntity<ApiResult> listHaTangMangSubnet(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("HATANG-SUBNET", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng IP Address")
    @GetMapping("/ha-tang-mang-ip-address")
    public ResponseEntity<ApiResult> listHaTangMangIpAddress(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("HATANG-IP", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng thiết bị")
    @GetMapping("/ha-tang-mang-thiet-bi")
    public ResponseEntity<ApiResult> listHaTangMangThietBi(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHaTangAoHoaSuDung("HATANG-THIET-BI", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lịch sử tác động Hạ tầng mạng")
    @GetMapping("/ha-tang-mang-history")
    public ResponseEntity<ApiResult> listHaTangMangHistory(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(aoHoaSuDungService.getDataHistoryAction("HA-TANG", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
