package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CapNhatSplit {

    @JsonProperty("vrpId")
    private Integer[] vrpId = null;

    @JsonProperty("ketCuoiId")
    private Long ketCuoiId;

    public Integer[] getVrpId() {
        return vrpId;
    }

    public void setVrpId(Integer[] vrpId) {
        this.vrpId = vrpId;
    }

    public Long getKetCuoiId() {
        return ketCuoiId;
    }

    public void setKetCuoiId(Long ketCuoiId) {
        this.ketCuoiId = ketCuoiId;
    }
}
