package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import vn.vnpt.message.ModelValidation;

public class ViTriThietBiDto extends ToaDoDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@Positive(message = ModelValidation.POSITIVE)
	private Number thietBiID;

	public Number getThietBiID() {
		return thietBiID;
	}

	public void setThietBiID(Number thietBiID) {
		this.thietBiID = thietBiID;
	}
	
}
