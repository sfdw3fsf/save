package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpsertListThietBiPortKhachHangDto implements Serializable {

    @JsonProperty("thietBiId")
    private Long thietBiId;

    @JsonProperty("portList")
    private List<ThietBiPortDto> portList;

    public UpsertListThietBiPortKhachHangDto() {
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public List<ThietBiPortDto> getPortList() {
        return portList;
    }

    public void setPortList(List<ThietBiPortDto> portList) {
        this.portList = portList;
    }
}


