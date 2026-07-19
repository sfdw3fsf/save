package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class XoaDanhSachCatChuyenDtoIn {
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long DKCC_ID;
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long DKCC_CHA_ID;
    @NotNull(message = ModelValidation.NOT_NULL)
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long HSCC_ID;
    public Long getDKCC_ID() {
        return DKCC_ID;
    }

    public void setDKCC_ID(Long DKCC_ID) {
        this.DKCC_ID = DKCC_ID;
    }
    public Long getDKCC_CHA_ID() {
        return DKCC_CHA_ID;
    }

    public void setDKCC_CHA_ID(Long DKCC_CHA_ID) {
        this.DKCC_CHA_ID = DKCC_CHA_ID;
    }
    public Long getHSCC_ID() {
        return HSCC_ID;
    }

    public void setHSCC_ID(Long HSCC_ID) {
        this.HSCC_ID = HSCC_ID;
    }
}
