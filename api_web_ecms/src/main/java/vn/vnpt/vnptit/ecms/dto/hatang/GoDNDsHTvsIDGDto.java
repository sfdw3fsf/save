package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class GoDNDsHTvsIDGDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number haTangIDGID;
	private GoDNIDGDto[] dsHT;
	
	public Number getHaTangIDGID() {
		return haTangIDGID;
	}

	public void setHaTangIDGID(Number haTangIDGID) {
		this.haTangIDGID = haTangIDGID;
	}

	public GoDNIDGDto[] getDsHT() {
		return dsHT;
	}

	public void setDsHT(GoDNIDGDto[] dsHT) {
		this.dsHT = dsHT;
	}
	
}
