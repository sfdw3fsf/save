package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class kiemTraChonPortInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("hdtb_id")
    private Number hdtbID;
    
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("thuebao_id")
    private Number thueBaoID;
    
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("port_id")
    private Number portID;
    
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vci_vpi_id")
    private Number vciVpiID;

	public Number getHdtbID() {
		return hdtbID;
	}

	public void setHdtbID(Number hdtbID) {
		this.hdtbID = hdtbID;
	}

	public Number getThueBaoID() {
		return thueBaoID;
	}

	public void setThueBaoID(Number thueBaoID) {
		this.thueBaoID = thueBaoID;
	}

	public Number getPortID() {
		return portID;
	}

	public void setPortID(Number portID) {
		this.portID = portID;
	}

	public Number getVciVpiID() {
		return vciVpiID;
	}

	public void setVciVpiID(Number vciVpiID) {
		this.vciVpiID = vciVpiID;
	}
}
