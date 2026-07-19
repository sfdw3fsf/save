package vn.vnpt.vnptit.ecms.dto.sync.vcenter;



import java.util.ArrayList;
import java.util.List;


public class SyncDatastoreInfoDTO {
    // thông tin cơ bản
    private String code;
    private String name;
    private Double capacity;
    private Double freeSpace;
    private String dataUnit;

    // thông tin ds host
    List<VCterHostDTO> hostList = new ArrayList<>();

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getCapacity() { return capacity; }
    public void setCapacity(Double capacity) { this.capacity = capacity; }

    public Double getFreeSpace() { return freeSpace; }
    public void setFreeSpace(Double freeSpace) { this.freeSpace = freeSpace; }

    public String getDataUnit() { return dataUnit; }
    public void setDataUnit(String dataUnit) { this.dataUnit = dataUnit; }

    public List<VCterHostDTO> getHostList() { return hostList; }
    public void setHostList(List<VCterHostDTO> hostList) { this.hostList = hostList; }
}
