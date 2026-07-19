package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class PortVdcIdDto {

    @JsonProperty("dslamVcdId")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamVcdId = null;

    @JsonProperty("dsPortId")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer[] dsPort = null;

    public Integer[] getDsPort() {
        return dsPort;
    }

    public void setDsPort(Integer[] dsPort) {
        this.dsPort = dsPort;
    }

    public Long getDslamVcdId() {
        return dslamVcdId;
    }

    public void setDslamVcdId(Long dslamVcdId) {
        this.dslamVcdId = dslamVcdId;
    }
}
