package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CapNhatDslamDtoIn extends ThemDslamDtoIn {

    @JsonProperty("dslam_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer dslamId;

    @JsonProperty("stt_olt")
    private Integer SttOlt;

    public int getDslamId() {
        return dslamId;
    }

    public void setDslamId(Integer dslamId) {
        this.dslamId = dslamId;
    }

    public Integer getSttOlt() {
        return SttOlt;
    }

    public void setSttOlt(Integer sttOlt) {
        SttOlt = sttOlt;
    }
}
