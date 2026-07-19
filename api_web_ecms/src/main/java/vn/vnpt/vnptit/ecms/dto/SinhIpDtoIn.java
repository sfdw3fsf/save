package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;

public class SinhIpDtoIn {

    @JsonProperty("prefix")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
