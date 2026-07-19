package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class getThongTinTocDoADSLTheoDslamInput extends idKieuInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("dslam_id")
    private Number dslamID;

	public Number getDslamID() {
		return dslamID;
	}

	public void setDslamID(Number dslamID) {
		this.dslamID = dslamID;
	}
}
