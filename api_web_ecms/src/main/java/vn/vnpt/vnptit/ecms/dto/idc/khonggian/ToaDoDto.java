package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class ToaDoDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number toaDoX;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number toaDoY;
	public Number getToaDoX() {
		return toaDoX;
	}
	public void setToaDoX(Number toaDoX) {
		this.toaDoX = toaDoX;
	}
	public Number getToaDoY() {
		return toaDoY;
	}
	public void setToaDoY(Number toaDoY) {
		this.toaDoY = toaDoY;
	}
}
