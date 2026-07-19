package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CapNhatSerialPortGponDtoInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer port_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private String serial;

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
}
