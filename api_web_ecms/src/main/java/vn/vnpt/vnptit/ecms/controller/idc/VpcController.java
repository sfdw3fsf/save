package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.VpcDto;
import vn.vnpt.vnptit.ecms.service.idc.VpcService;

@RestController("idcVpcController")
@RequestMapping("/vpc")
public class VpcController {
    private final VpcService vpcService;

    public VpcController(VpcService vpcService) {
        this.vpcService = vpcService;
    }

    @Operation(summary = "Lấy Danh sách VPC")
    @PostMapping("/danhsach")
    public ResponseEntity<ApiResult> getDanhSachVPC(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.getDanhSachVPC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết VPC")
    @PostMapping("/chi-tiet")
    public ResponseEntity<ApiResult> getDetailVpc(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.getDetailVpc(dto.getVpcId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật VPC")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertVpc(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.upsertVpc(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa VPC")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> deleteVpcBulk(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.deleteVpcBulk(dto.getVpcIds()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử đồng bộ VPC")
    @PostMapping("/sync-history")
    public ResponseEntity<ApiResult> getLichsuSyncVpc(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.getLichsuSyncVpc(dto.getVpcId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Addon của VPC")
    @PostMapping("/addon")
    public ResponseEntity<ApiResult> getAddonVpc(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.getAddonVpc(dto.getVpcId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Network IP của VPC")
    @PostMapping("/network-ip")
    public ResponseEntity<ApiResult> getNetworkIpVpc(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.getNetworkIpVpc(dto.getVpcId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Network IP của VPC")
    @PostMapping("/cumspdv")
    public ResponseEntity<ApiResult> getCumSPDVVpc(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.getCumSPDVVpc(dto.getVpcId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ và lưu VPC từ SmartCloud")
    @PostMapping("/sync-save-vpc")
    public ResponseEntity<ApiResult> syncAndSaveFromGateway(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.syncAndSaveVpc(
                dto.getMaVpc(),
                dto.getSmCloudId() != null ? dto.getSmCloudId() : 0L,
                dto.getSmCloudServiceType() != null ? dto.getSmCloudServiceType() : 0,
                dto.getMaTB(),
                dto.getVpcId(),
                dto.getCumHaTangId()
            ));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setDataInvalidMessage(e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ và lưu danh sách VPC từ SmartCloud theo regionCode")
    @PostMapping("/sync-save-vpc-list")
    public ResponseEntity<ApiResult> syncAndSaveListFromGateway(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.syncAndSaveVpcList(dto.getRegionCode()));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setDataInvalidMessage(e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ dịch vụ hạ tầng không thuộc VPC (serviceCode != vpc) theo regionCode")
    @PostMapping("/sync-non-vpc-services")
    public ResponseEntity<ApiResult> syncNonVpcServices(@RequestBody VpcDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vpcService.syncNonVpcServices(dto.getRegionCode()));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setDataInvalidMessage(e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
