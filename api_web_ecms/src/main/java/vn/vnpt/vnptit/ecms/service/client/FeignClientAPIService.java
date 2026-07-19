package vn.vnpt.vnptit.ecms.service.client;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties.SystemType;
import vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCloudVPCDetailDTO;
import vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCloudVpcItemDTO;
import vn.vnpt.vnptit.ecms.dto.sync.vcenter.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FeignClientAPIService {

    private final ExternalApiFactory factory;

    public FeignClientAPIService(ExternalApiFactory factory) {
        this.factory = factory;
    }

    // VCENTER START
    /*
    * Danh sách tất cả datastore(vùng dữ liêu)
    * */
    public List<VCterDatastoreDTO> vcterDataStoreList() {
        List<VCterDatastoreDTO> rsList = this.factory.getClient(SystemType.VCENTER)
                .callList("/datastores", HttpMethod.GET, null, VCterDatastoreDTO.class);
        return rsList;
    }

    /*
     * Danh sách tất cả cluster(Cụm hạ tầng)
     * */
    public List<VCterClusterDTO> vcterClusterList() {
        List<VCterClusterDTO> rsList = this.factory.getClient(SystemType.VCENTER)
                .callList("/clusters", HttpMethod.GET, null, VCterClusterDTO.class);
        return rsList;
    }

    /*
     * Danh sách Host(Thiết bị) trên 1 cluster(Cụm hạ tầng)
     * */
    public List<VCterHostDTO> vcterHostListByClusterCode(String clusterCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("clusterCode", clusterCode);
        List<VCterHostDTO> rsList = this.factory.getClient(SystemType.VCENTER)
                .callList("/cluster-hosts", HttpMethod.GET, params, VCterHostDTO.class);
        return rsList;
    }

    /*
     * Danh sách Datastore(Vùng dữ liệu) trên 1 Host(Thiết bị)
     * */
    public List<VCterDatastoreDTO> vcterDataStoreListByHostCode(String hostCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("hostCode", hostCode);
        List<VCterDatastoreDTO> rsList = this.factory.getClient(SystemType.VCENTER)
                .callList("/host-datastores", HttpMethod.GET, params, VCterDatastoreDTO.class);
        return rsList;
    }

    /*
     * Danh sách vmware(Máy ảo) trên 1 cluster(Cụm hạ tầng)
     * */
    public List<VCterVmwareDTO> vcterVMwareListByClusterCode(String clusterCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("clusterCode", clusterCode);
        List<VCterVmwareDTO> rsList = this.factory.getClient(SystemType.VCENTER)
                .callList("/cluster-vms", HttpMethod.GET, params, VCterVmwareDTO.class);
        return rsList;
    }

    /*
     * Thông tin chi tiết vmware(Máy ảo) theo mã
     * */
    public SyncVMwareInfoDTO vcterVMwareDetail(String vmCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("vmCode", vmCode);
        SyncVMwareInfoDTO rs = this.factory.getClient(SystemType.VCENTER)
                .call("/vm-detail", HttpMethod.GET, params, SyncVMwareInfoDTO.class);
        return rs;
    }

    /*
     * Thông tin chi tiết datastore(vùng dữ liệu) theo mã
     * */
    public SyncDatastoreInfoDTO vcterDatastoreDetailByCode(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("datastoreCode", code);
        SyncDatastoreInfoDTO rs = this.factory.getClient(SystemType.VCENTER)
                .call("/datastore-detail", HttpMethod.GET, params, SyncDatastoreInfoDTO.class);
        return rs;
    }

    /*
     * Thông tin chi tiết cluster(cụm hạ tầng) theo mã
     * */
    public SyncClusterInfoDTO vcterClusterDetailByCode(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("clusterCode", code);
        SyncClusterInfoDTO rs = this.factory.getClient(SystemType.VCENTER)
                .call("/cluster-detail", HttpMethod.GET, params, SyncClusterInfoDTO.class);
        return rs;
    }

    /*
     * Thông tin chi tiết datacenter theo mã
     * */
    public SyncDatacenterInfoDTO vcterDatacenterDetailByCode(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("clusterCode", code);
        SyncDatacenterInfoDTO rs = this.factory.getClient(SystemType.VCENTER)
                .call("/datacenter-detailt", HttpMethod.GET, params, SyncDatacenterInfoDTO.class);
        return rs;
    }
    // VCENTER END

    // SMARTCLOUD START
    public SMCloudVPCDetailDTO smCloudVPCDetailExt(String maVPC, long id, int serviceType, String maTB) {
        String buildPath = String.format("/subscription/detail/" +
                "%s?" +
                "id=%s" +
                "&serviceType=%s" +
                "&maTB=%s",maVPC,id,serviceType,maTB);
        SMCloudVPCDetailDTO rs = this.factory.getClient(SystemType.SMARTCLOUD_EXTERNAL)
                .call(buildPath, HttpMethod.GET, null, SMCloudVPCDetailDTO.class);
        return rs;
    }

    public List<SMCloudVpcItemDTO> smCloudVPCListExt(String regionCode) {
        String buildPath = String.format("/subscription/vpcs?regionCode=%s", regionCode);
        List<SMCloudVpcItemDTO> rs = this.factory.getClient(SystemType.SMARTCLOUD_EXTERNAL)
                .callList(buildPath, HttpMethod.GET, null, SMCloudVpcItemDTO.class);
        return rs;
    }

    public vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCapacityExtDto smCloudCapacityExt(String regionCode) {
        String buildPath = String.format("/capacity?regionCode=%s", regionCode);
        vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCapacityExtDto rs = this.factory.getClient(SystemType.SMARTCLOUD_EXTERNAL)
                .call(buildPath, HttpMethod.GET, null, vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCapacityExtDto.class);
        return rs;
    }

    /**
     * Lấy tất cả dịch vụ hạ tầng theo regionCode từ BCA SmartCloud.
     * API: /subscription/service/all?regionCode={regionCode}
     */
    public List<SMCloudVpcItemDTO> smCloudServiceAllExt(String regionCode) {
        String buildPath = String.format("/subscription/service/all?regionCode=%s", regionCode);
        List<SMCloudVpcItemDTO> rs = this.factory.getClient(SystemType.SMARTCLOUD_EXTERNAL)
                .callList(buildPath, HttpMethod.GET, null, SMCloudVpcItemDTO.class);
        return rs;
    }

    /**
     * Lấy chi tiết 1 dịch vụ hạ tầng theo id + serviceType từ BCA SmartCloud.
     * API: /subscription/service-detail?id={id}&serviceType={serviceType}
     * Response giống ServiceItemsDTO (có details[] chứa CPU/RAM/Storage).
     */
    public SMCloudVPCDetailDTO.ServiceItemsDTO smCloudServiceDetailExt(long id, int serviceType) {
        String buildPath = String.format("/subscription/service-detail?id=%d&serviceType=%d", id, serviceType);
        SMCloudVPCDetailDTO.ServiceItemsDTO rs = this.factory.getClient(SystemType.SMARTCLOUD_EXTERNAL)
                .call(buildPath, HttpMethod.GET, null, SMCloudVPCDetailDTO.ServiceItemsDTO.class);
        return rs;
    }
    // SMARTCLOUD END
}
