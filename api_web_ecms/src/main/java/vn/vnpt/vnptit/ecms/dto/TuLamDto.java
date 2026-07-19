package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.PositiveOrZero;

public class TuLamDto {

    @JsonProperty("dslam_id")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long dslamId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("turack_id")
    private Long tuRackId;

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getTuRackId() {
        return tuRackId;
    }

    public void setTuRackId(Long tuRackId) {
        this.tuRackId = tuRackId;
    }
}
