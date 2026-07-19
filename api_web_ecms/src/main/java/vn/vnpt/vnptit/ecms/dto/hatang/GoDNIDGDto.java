package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class GoDNIDGDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number id;

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private int phanLoai;

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public int getPhanLoai() {
		return phanLoai;
	}

	public void setPhanLoai(int phanLoai) {
		this.phanLoai = phanLoai;
	}
	
}
