package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import vn.vnpt.message.ModelValidation;

public class ViTriHanhLangDto extends ToaDoDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@Positive(message = ModelValidation.POSITIVE)
	private Number hanhLangID;

	public Number getHanhLangID() {
		return hanhLangID;
	}

	public void setHanhLangID(Number hanhLangID) {
		this.hanhLangID = hanhLangID;
	}

}
