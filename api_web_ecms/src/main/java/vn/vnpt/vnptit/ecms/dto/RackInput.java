package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class RackInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 18, message = ModelValidation.INVALID)
    private String RACK;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 10, message = ModelValidation.INVALID)
    private String SHELF;

    public static class RackInputIns extends RackInput {

        @Max(999999999)
        @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
        private Long RACK_ID;
        public Long getRACK_ID() {
            return RACK_ID;
        }

        public void setRACK_ID(Long RACK_ID) {
            this.RACK_ID = RACK_ID;
        }

    }

    public String getRACK() {
        return RACK;
    }

    public void setRACK(String RACK) {
        this.RACK = RACK;
    }

    public String getSHELF() {
        return SHELF;
    }

    public void setSHELF(String SHELF) {
        this.SHELF = SHELF;
    }

}
