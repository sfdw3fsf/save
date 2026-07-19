package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class ToaDoPhongDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number phongID;

	@NotNull(message = ModelValidation.NOT_NULL)
	private ToaDoDto[] dsToaDo;
	
	public Number getPhongID() {
		return phongID;
	}

	public void setPhongID(Number phongID) {
		this.phongID = phongID;
	}

	public ToaDoDto[] getDsToaDo() {
		return dsToaDo;
	}

	public void setDsToaDo(ToaDoDto[] dsToaDo) {
		this.dsToaDo = dsToaDo;
	}
}
