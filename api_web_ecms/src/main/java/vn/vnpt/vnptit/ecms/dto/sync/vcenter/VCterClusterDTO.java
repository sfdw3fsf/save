package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

public class VCterClusterDTO {
    private Boolean drs_enabled;
    private String cluster;
    private String name;
    private Boolean ha_enabled;

    public Boolean getDrs_enabled() {
        return drs_enabled;
    }

    public void setDrs_enabled(Boolean drs_enabled) {
        this.drs_enabled = drs_enabled;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHa_enabled() {
        return ha_enabled;
    }

    public void setHa_enabled(Boolean ha_enabled) {
        this.ha_enabled = ha_enabled;
    }
}
