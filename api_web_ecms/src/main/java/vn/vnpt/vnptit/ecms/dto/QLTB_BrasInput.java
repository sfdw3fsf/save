package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_BrasInput extends QLTB_BrasInsertInput {
	private Integer phanvung_id = null;

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer bras_id = 0;

	public Integer getPhanvung_id() {
		return phanvung_id;
	}

	public void setPhanvung_id(Integer phanvung_id) {
		this.phanvung_id = phanvung_id;
	}

	public Integer getBras_id() {
		return bras_id;
	}

	public void setBras_id(Integer bras_id) {
		this.bras_id = bras_id;
	}
}
