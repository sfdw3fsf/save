package vn.vnpt.vnptit.ecms.dto.sync.vcenter;


import java.util.List;

public class SyncDatacenterInfoDTO {
    private String code;
    private String name;

    private List<VCterClusterDTO> clusterList;
    private List<VCterHostDTO> hostList;
    private List<VCterVmwareDTO> vmList;
    private List<VCterIpInfoDTO> ipList;
    private List<VCterDatastoreDTO> datastoreList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VCterClusterDTO> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<VCterClusterDTO> clusterList) {
        this.clusterList = clusterList;
    }

    public List<VCterHostDTO> getHostList() {
        return hostList;
    }

    public void setHostList(List<VCterHostDTO> hostList) {
        this.hostList = hostList;
    }

    public List<VCterVmwareDTO> getVmList() {
        return vmList;
    }

    public void setVmList(List<VCterVmwareDTO> vmList) {
        this.vmList = vmList;
    }

    public List<VCterIpInfoDTO> getIpList() {
        return ipList;
    }

    public void setIpList(List<VCterIpInfoDTO> ipList) {
        this.ipList = ipList;
    }

    public List<VCterDatastoreDTO> getDatastoreList() {
        return datastoreList;
    }

    public void setDatastoreList(List<VCterDatastoreDTO> datastoreList) {
        this.datastoreList = datastoreList;
    }
}
