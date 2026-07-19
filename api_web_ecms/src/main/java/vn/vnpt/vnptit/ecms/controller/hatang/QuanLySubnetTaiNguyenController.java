package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLySubnetTaiNguyenService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subnet-tai-nguyen")
public class QuanLySubnetTaiNguyenController {
    private final QuanLySubnetTaiNguyenService service;

    public QuanLySubnetTaiNguyenController(QuanLySubnetTaiNguyenService service) {
        this.service = service;
    }

    @Operation(summary = "Get danh sách subnet")
    @GetMapping("/danh-sach-subnet")
    public ResponseEntity<ApiResult> getDanhSachSubnetTaiNguyen() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.get_ds_subnet_tainguyen());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách subnet droplist")
    @GetMapping("/danhsach-subnet-droplist")
    public ResponseEntity<ApiResult> getDanhSachSubnetDropListTaiNguyen() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.get_ds_subnet_droplist());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách subnet con by parent Id")
    @GetMapping("/danhsach-subnet-con/parent-{parentId}")
    public ResponseEntity<ApiResult> getDanhSachSubnetConBySubnetParentID(@PathVariable Integer parentId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSubnetConByParentId(parentId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get subnet by ID")
    @GetMapping("/chi-tiet-subnet/{id}")
    public ResponseEntity<ApiResult> getSubnetById(@PathVariable Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSubnetById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách phân loại IP")
    @GetMapping("/phanloai-ip")
    public ResponseEntity<ApiResult> getPhanLoaiIP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getPhanLoaiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách loại IP")
    @GetMapping("/loai-ip")
    public ResponseEntity<ApiResult> getLoaiIP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getLoaiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách vlan droplist")
    @GetMapping("/danhsach-vlan")
    public ResponseEntity<ApiResult> getDanhSachVlanDropList() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.get_ds_vlan_droplist());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get VLAN droplist theo IDC")
    @PostMapping("/danhsach-vlan")
    public ResponseEntity<ApiResult> getVlanDroplistByIdc(
            @RequestBody Map<String, Object> payload
    ) {
        ApiResult rs = new ApiResult();
        try {
            Integer idcId = payload.get("idcId") == null
                    ? null
                    : Integer.valueOf(payload.get("idcId").toString());

            rs.setData(service.getVlanDroplistByIdc(idcId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Create new subnet")
    @PostMapping("/create-subnet")
    public ResponseEntity<ApiResult> createSubnet(@RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.createSubnet(body));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update subnet by ID")
    @PostMapping("/update-subnet/{id}")
    public ResponseEntity<ApiResult> updateSubnet(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updateSubnet(id, body));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/ds-ip-{id}")
    public ResponseEntity<ApiResult> getSubnetAndIps(@PathVariable Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getSubnetAndIps(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách vrf by subnet id")
    @GetMapping("/danhsach-vrf-{id}")
    public ResponseEntity<ApiResult> getVrfBySubnetId(@PathVariable Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getVrfBySubnetId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách zone by subnet id")
    @GetMapping("/danhsach-zone-{id}")
    public ResponseEntity<ApiResult> getZoneById(@PathVariable Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getZoneBySubnetId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách htm droplist")
    @GetMapping("/danhsach-htm")
    public ResponseEntity<ApiResult> getDanhSachHtmDroplist() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.get_ds_htm_droplist());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get danh sách HTM theo IDC")
    @PostMapping("/danhsach-htm")
    public ResponseEntity<ApiResult> getDanhSachHtmByIdc(
            @RequestBody Map<String, Object> payload
    ) {
        ApiResult rs = new ApiResult();
        try {
            Integer idcId = payload.get("idcId") == null
                    ? null
                    : Integer.valueOf(payload.get("idcId").toString());

            rs.setData(service.getHtmDroplistByIdc(idcId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Sinh IP tự động cho subnet")
    @PostMapping("/sinh-ip/{subnetId}")
    public ResponseEntity<ApiResult> generateIp(@PathVariable Integer subnetId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.generateIp(subnetId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get lịch sử tác động by subnet id")
    @GetMapping("/lichsu-subnet-{id}")
    public ResponseEntity<ApiResult> getLichSuTacDongById(@PathVariable Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getLichSuBySubnetId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa subnet")
    @PostMapping("/delete-subnet-{subnetId}")
    public ResponseEntity<ApiResult> deleteSubnet(@PathVariable Integer subnetId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteSubnet(subnetId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Search subnet")
    @PostMapping("/search-subnet")
    public ResponseEntity<ApiResult> searchSubnet(
            @RequestBody String searchJson
    ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.searchSubnet(searchJson));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get thiết bị by subnet id")
    @GetMapping("/thietbi-subnet-{id}")
    public ResponseEntity<ApiResult> getThietBiBySubnetId(@PathVariable Integer id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThietBiBySubnetId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}