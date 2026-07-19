package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vn.vnpt.annotations.Backoff;
import vn.vnpt.annotations.FeignRetry;
import vn.vnpt.exceptions.RetryableException;
import vn.vnpt.vnptit.ecms.config.MiddlewarePropeties;

import java.util.Map;

@Service
public class VCenterService {

    private final RestTemplate restTemplate;
    private final MiddlewarePropeties resConfig;

    public VCenterService(@Qualifier("vcenterRestTemplate") RestTemplate restTemplate, MiddlewarePropeties middlewarePropeties) {
        this.restTemplate = restTemplate;
        this.resConfig = middlewarePropeties;
    }

    @SuppressWarnings("unchecked")
    private Object extractData(Object response) {
        if (response instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) response;
            if (map.containsKey("data")) {
                return map.get("data");
            }
        }
        return response;
    }

    /**
     * Danh sách Datacenter
     * GET /datacenters
     */
    public Object syncDataCenters() {
        String url = resConfig.getVcenter().getBaseUrl() + "/datacenters";
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi vCenter API datacenters: " + e.getMessage(), e);
        }
    }

    /**
     * Danh sách Datastore
     * GET /datastores
     */
    public Object getDatastores() {
        String url = resConfig.getVcenter().getBaseUrl() + "/datastores";
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi vCenter API datastores: " + e.getMessage(), e);
        }
    }

    /**
     * Danh sách Clusters trong Datacenter
     * GET /datacenter-clusters?datacenterCode=xxx
     */
    public Object getDatacenterClusters(String datacenterCode) {
        String url = UriComponentsBuilder.fromHttpUrl(resConfig.getVcenter().getBaseUrl() + "/datacenter-clusters")
                .queryParam("datacenterCode", datacenterCode)
                .toUriString();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi vCenter API datacenter-clusters: " + e.getMessage(), e);
        }
    }

    /**
     * Danh sách VMWare (máy ảo) trong một Cluster
     * GET /cluster-vms?clusterCode=xxx
     */
    public Object getClusterVms(String clusterCode) {
        String url = UriComponentsBuilder.fromHttpUrl(resConfig.getVcenter().getBaseUrl() + "/cluster-vms")
                .queryParam("clusterCode", clusterCode)
                .toUriString();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi vCenter API cluster-vms: " + e.getMessage(), e);
        }
    }

    /**
     * Thông tin chi tiết VMWare theo mã
     * GET /vm-detail?vmCode=xxx
     */
    public Object getVmDetail(String vmCode) {
        String url = UriComponentsBuilder.fromHttpUrl(resConfig.getVcenter().getBaseUrl() + "/vm-detail")
                .queryParam("vmCode", vmCode)
                .toUriString();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi vCenter API vm-detail: " + e.getMessage(), e);
        }
    }

    /**
     * Thông tin chi tiết Datastore
     * GET /datastore-detail?datastoreCode=xxx
     */
    public Object getDatastoreDetail(String datastoreCode) {
        String url = UriComponentsBuilder.fromHttpUrl(resConfig.getVcenter().getBaseUrl() + "/datastore-detail")
                .queryParam("datastoreCode", datastoreCode)
                .toUriString();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi gọi vCenter API datastore-detail: " + e.getMessage(), e);
        }
    }

    /**
     * Thông tin chi tiết Cluster
     * GET /cluster-detail?clusterCode=xxx
     */
    @FeignRetry(maxAttempt = 3, include = { RetryableException.class }, backoff = @Backoff(delay = 2000))
    public Object getClusterDetail(String clusterCode) {
        String url = UriComponentsBuilder.fromHttpUrl(resConfig.getVcenter().getBaseUrl() + "/cluster-detail")
                .queryParam("clusterCode", clusterCode)
                .toUriString();
        try {
            Object response = restTemplate.getForObject(url, Object.class);
            return extractData(response);
        } catch (ResourceAccessException e) {
            throw new RetryableException("Lỗi gọi vCenter API cluster-detail: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi gọi vCenter API cluster-detail: " + e.getMessage(), e);
        }
    }
}
