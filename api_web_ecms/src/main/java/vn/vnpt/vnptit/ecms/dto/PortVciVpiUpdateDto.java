package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class PortVciVpiUpdateDto {


    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_port_id")
    private String portId;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_vci_vpi_id")
    private Long VciVpiId;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_trangthai")
    private Long trangThai;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_port_mdf")
    private String portMdf;

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public Long getVciVpiId() {
        return VciVpiId;
    }

    public void setVciVpiId(Long vciVpiId) {
        VciVpiId = vciVpiId;
    }

    public Long getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Long trangThai) {
        this.trangThai = trangThai;
    }

    public String getPortMdf() {
        return portMdf;
    }

    public void setPortMdf(String portMdf) {
        this.portMdf = portMdf;
    }
}
