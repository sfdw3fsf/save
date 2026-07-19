package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.SubnetRequestDto;
import vn.vnpt.vnptit.ecms.dto.hatang.RequestVlanDto;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLySubNetService;

@Api(value = "Quản lý Subnet - Controller", description = "Quản lý kho tài nguyên ảo hoá - Quản lý Subnet")
@RestController
@RequestMapping("quanly-subnet")
public class QuanLySubNetController {
    private final QuanLySubNetService service;

    public QuanLySubNetController(QuanLySubNetService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy danh sách Subnet theo VLAN và loại IP")
    @PostMapping("/get-subnet-by-vlan-loai-ip")
    public ResponseEntity<ApiResult> getZoneByVRF(@RequestBody Map<String, Long> request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.get_subnet_by_vlan_loai_ip(request.get("vlan_id"), request.get("ip_loai_id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Danh sách subnet")
    @PostMapping("/ds-subnet")
    public ResponseEntity<ApiResult> dsSubnet() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.ds_subnet());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật subnet")
    @PostMapping("/update-subnet")
    public ResponseEntity<ApiResult> updateSubnet(@RequestBody SubnetRequestDto subnetRequestDto  ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.updateSubnet(subnetRequestDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "xóa subnet")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.xoa(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Danh sách VLAN")
    @PostMapping("/ds-vlan")
    public ResponseEntity<ApiResult> dsValn(@RequestBody RequestVlanDto  item   ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dsVlan(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
        
    }
    @Operation(summary = "Danh sách Hạ tầng ảo hóa")
    @PostMapping("/ds-hatang-aohoa")
    public ResponseEntity<ApiResult> dsHaTangAoHoa(@RequestBody DataKeyInput item ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dsHaTangAoHoa(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách ip store ")
    @PostMapping("/ds-ipstore")
    public ResponseEntity<ApiResult> dsIpStore(@RequestBody DataKeyInput item ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dsIpStore(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách subnetcon")
    @PostMapping("/ds-subnet-con")
    public ResponseEntity<ApiResult> dsSubnetCon(@RequestBody DataKeyInput item ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dsSubnetCon(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách lịch sử")
    @PostMapping("/ds-lichsu")
    public ResponseEntity<ApiResult> dsLichSu(@RequestBody DataKeyInput item ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dsLichSu(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
