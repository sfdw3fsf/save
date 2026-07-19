package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class ToaDoLongQuayDto{
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number longQuayID;

	@NotNull(message = ModelValidation.NOT_NULL)
	private ToaDoDto[] dsToaDo;

	public Number getLongQuayID() {
		return longQuayID;
	}

	public void setLongQuayID(Number longQuayID) {
		this.longQuayID = longQuayID;
	}

	public ToaDoDto[] getDsToaDo() {
		return dsToaDo;
	}

	public void setDsToaDo(ToaDoDto[] dsToaDo) {
		this.dsToaDo = dsToaDo;
	}

}
