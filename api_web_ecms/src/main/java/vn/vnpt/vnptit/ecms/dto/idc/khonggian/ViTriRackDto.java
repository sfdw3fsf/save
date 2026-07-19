package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import vn.vnpt.message.ModelValidation;

public class ViTriRackDto extends ToaDoDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@Positive(message = ModelValidation.POSITIVE)
	private Number rackID;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Boolean huongNgang;

	public Number getRackID() {
		return rackID;
	}

	public void setRackID(Number rackID) {
		this.rackID = rackID;
	}

	public Boolean getHuongNgang() {
		return huongNgang;
	}

	public void setHuongNgang(Boolean huongNgang) {
		this.huongNgang = huongNgang;
	}

}
