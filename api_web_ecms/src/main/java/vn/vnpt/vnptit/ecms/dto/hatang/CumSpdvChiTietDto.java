package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CumSpdvChiTietDto implements Serializable {

    @JsonProperty("cumspdvId")
    private Long cumspdvId;

    public CumSpdvChiTietDto() {
    }

    public Long getCumspdvId() {
        return cumspdvId;
    }

    public void setCumspdvId(Long cumspdvId) {
        this.cumspdvId = cumspdvId;
    }
}

