package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VCterVmwareDTO {
    @JsonProperty("memory_size_MiB")
    private Long memorySizeMiB;
    private String vm;
    private String name;
    private String power_state;
    private Integer cpu_count;

    public Long getMemorySizeMiB() {
        return memorySizeMiB;
    }

    public void setMemorySizeMiB(Long memorySizeMiB) {
        this.memorySizeMiB = memorySizeMiB;
    }

    public String getVm() {
        return vm;
    }

    public void setVm(String vm) {
        this.vm = vm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPower_state() {
        return power_state;
    }

    public void setPower_state(String power_state) {
        this.power_state = power_state;
    }

    public Integer getCpu_count() {
        return cpu_count;
    }

    public void setCpu_count(Integer cpu_count) {
        this.cpu_count = cpu_count;
    }
}
