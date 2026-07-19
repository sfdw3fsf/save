package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class RackLongQuayDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number rackID;
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number longQuayID;
	public Number getRackID() {
		return rackID;
	}
	public void setRackID(Number rackID) {
		this.rackID = rackID;
	}
	public Number getLongQuayID() {
		return longQuayID;
	}
	public void setLongQuayID(Number longQuayID) {
		this.longQuayID = longQuayID;
	}
}
