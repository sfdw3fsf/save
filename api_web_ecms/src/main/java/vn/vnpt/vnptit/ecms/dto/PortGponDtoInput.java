package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class PortGponDtoInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer port_id;
    
    private String serial;
    @NotNull(message = ModelValidation.NOT_NULL)
    private String code;

    public Integer getPort_id() {
        return port_id;
    }

    public void setPort_id(Integer port_id) {
        this.port_id = port_id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
