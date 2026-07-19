package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class DmLyDoHaTangInput {

    @JsonProperty("ma_proc")
    @NotNull(message = ModelValidation.NOT_NULL)
    private String ma_proc;

    public String getMa_proc() {
        return ma_proc;
    }
}
