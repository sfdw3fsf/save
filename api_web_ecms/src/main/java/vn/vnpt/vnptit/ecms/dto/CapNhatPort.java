package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CapNhatPort {

    @JsonProperty("vrpId")
    private Integer[] vrpId = null;

    @JsonProperty("trangThaiId")
    private Long trangThaiId;

    public Integer[] getVrpId() {
        return vrpId;
    }

    public void setVrpId(Integer[] vrpId) {
        this.vrpId = vrpId;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }
}
