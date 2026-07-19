package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class DkccDtoIn {
    public Long getDKCC_ID() {
        return DKCC_ID;
    }

    public void setDKCC_ID(Long DKCC_ID) {
        this.DKCC_ID = DKCC_ID;
    }
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Long DKCC_ID;
}
