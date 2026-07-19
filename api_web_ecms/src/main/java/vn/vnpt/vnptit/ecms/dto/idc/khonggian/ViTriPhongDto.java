package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import vn.vnpt.message.ModelValidation;

public class ViTriPhongDto extends ToaDoDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@Positive(message = ModelValidation.POSITIVE)
	private Number phongID;
	public Number getPhongID() {
		return phongID;
	}
	public void setPhongID(Number phongID) {
		this.phongID = phongID;
	}

}
