package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DsHTIDGDChuaDauNoiDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number id;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Boolean dauNoiHTAH;
	
	public Number getId() {
		return id;
	}
	public void setId(Number id) {
		this.id = id;
	}
	public Boolean getDauNoiHTAH() {
		return dauNoiHTAH;
	}
	public void setDauNoiHTAH(Boolean dauNoiHTAH) {
		this.dauNoiHTAH = dauNoiHTAH;
	}
	
}
