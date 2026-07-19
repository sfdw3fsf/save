package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class ModuleDtoInput {

    @Max(value = 999,message = ModelValidation.MAX + " 999")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("so_port")
    private Long so_port;

    @Max(value = 9,message = ModelValidation.MAX + " 9")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("port_bd")
    private Long port_bd;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, message = ModelValidation.INVALID)
    @JsonProperty("loai_module")
    private String loai_module;

    public Long getSo_port() {
        return so_port;
    }

    public void setSo_port(Long so_port) {
        this.so_port = so_port;
    }

    public Long getPort_bd() {
        return port_bd;
    }

    public void setPort_bd(Long port_bd) {
        this.port_bd = port_bd;
    }

    public String getLoai_module() {
        return loai_module;
    }

    public void setLoai_module(String loai_module) {
        this.loai_module = loai_module;
    }
}
