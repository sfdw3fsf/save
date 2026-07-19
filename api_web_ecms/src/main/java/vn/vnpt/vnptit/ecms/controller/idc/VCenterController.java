package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.idc.VCenterService;

@RestController
@RequestMapping("/vcenter")
public class VCenterController {

    private final VCenterService vCenterService;

    public VCenterController(VCenterService vCenterService) {
        this.vCenterService = vCenterService;
    }

    @Operation(summary = "Danh sách Datacenter")
    @RequestMapping(value = "/datacenters", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> syncDataCenters() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.syncDataCenters());
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Datastore")
    @RequestMapping(value = "/datastores", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDatastores() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.getDatastores());
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách Clusters trong Datacenter")
    @RequestMapping(value = "/datacenter-clusters", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDatacenterClusters(@RequestParam String datacenterCode) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.getDatacenterClusters(datacenterCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách VMWare (máy ảo) trong Cluster")
    @RequestMapping(value = "/cluster-vms", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getClusterVms(@RequestParam String clusterCode) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.getClusterVms(clusterCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thông tin chi tiết VMWare theo mã")
    @RequestMapping(value = "/vm-detail", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getVmDetail(@RequestParam String vmCode) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.getVmDetail(vmCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thông tin chi tiết Datastore")
    @RequestMapping(value = "/datastore-detail", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDatastoreDetail(@RequestParam String datastoreCode) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.getDatastoreDetail(datastoreCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thông tin chi tiết Cluster")
    @RequestMapping(value = "/cluster-detail", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getClusterDetail(@RequestParam String clusterCode) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(vCenterService.getClusterDetail(clusterCode));
        } catch (Exception e) {
            rs.setDataInvalidMessage("Lỗi gọi vCenter API: " + e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
