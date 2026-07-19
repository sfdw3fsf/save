package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class ThietBiLongQuayDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number thietBiID;
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number longQuayID;
	public Number getThietBiID() {
		return thietBiID;
	}
	public void setThietBiID(Number thietBiID) {
		this.thietBiID = thietBiID;
	}
	public Number getLongQuayID() {
		return longQuayID;
	}
	public void setLongQuayID(Number longQuayID) {
		this.longQuayID = longQuayID;
	}
}
