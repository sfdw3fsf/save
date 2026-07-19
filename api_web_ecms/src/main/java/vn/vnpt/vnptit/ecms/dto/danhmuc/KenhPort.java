package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class KenhPort {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long portVlId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String port;

    public void setPortVlId(Long portVlId) {
        this.portVlId = portVlId;
    }
    public Long getPortVlId() {
        return portVlId;
    }

    public void setPort(String port) {
        this.port = port;
    }
    public String getPort() {
        return port;
    }
}
