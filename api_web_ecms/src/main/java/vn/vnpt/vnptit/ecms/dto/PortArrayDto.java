package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class PortArrayDto {

    @JsonProperty("p_port")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer[] dsPort = null;

    public Integer[] getDsPort() {
        return dsPort;
    }

    public void setDsPort(Integer[] dsPort) {
        this.dsPort = dsPort;
    }
}
