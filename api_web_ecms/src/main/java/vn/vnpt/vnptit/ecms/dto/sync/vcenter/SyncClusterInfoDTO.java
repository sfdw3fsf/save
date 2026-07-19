package vn.vnpt.vnptit.ecms.dto.sync.vcenter;


import java.util.ArrayList;
import java.util.List;

public class SyncClusterInfoDTO {
    private String datecenterCode;

    private String code;
    private String name;

    private List<VCterHostDTO> hostList = new ArrayList<>();
    private List<VCterDatastoreDTO> datastoreList = new ArrayList<>();
    private List<VCterVmwareDTO> vmwareList = new ArrayList<>();

    public String getDatecenterCode() {
        return datecenterCode;
    }

    public void setDatecenterCode(String datecenterCode) {
        this.datecenterCode = datecenterCode;
    }

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

    public List<VCterHostDTO> getHostList() {
        return hostList;
    }

    public void setHostList(List<VCterHostDTO> hostList) {
        this.hostList = hostList;
    }

    public List<VCterDatastoreDTO> getDatastoreList() {
        return datastoreList;
    }

    public void setDatastoreList(List<VCterDatastoreDTO> datastoreList) {
        this.datastoreList = datastoreList;
    }

    public List<VCterVmwareDTO> getVmwareList() {
        return vmwareList;
    }

    public void setVmwareList(List<VCterVmwareDTO> vmwareList) {
        this.vmwareList = vmwareList;
    }
}
