package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import vn.vnpt.message.ModelValidation;

public class ViTriLongQuayDto extends ToaDoDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@Positive(message = ModelValidation.POSITIVE)
	private Number longQuayID;

	public Number getLongQuayID() {
		return longQuayID;
	}

	public void setLongQuayID(Number longQuayID) {
		this.longQuayID = longQuayID;
	}
	
}
