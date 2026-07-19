package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class ToaDoHanhLangDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number hanhLangID;

	@NotNull(message = ModelValidation.NOT_NULL)
	private ToaDoDto[] dsToaDo;

	public Number getHanhLangID() {
		return hanhLangID;
	}

	public void setHanhLangID(Number hanhLangID) {
		this.hanhLangID = hanhLangID;
	}

	public ToaDoDto[] getDsToaDo() {
		return dsToaDo;
	}

	public void setDsToaDo(ToaDoDto[] dsToaDo) {
		this.dsToaDo = dsToaDo;
	}

}
