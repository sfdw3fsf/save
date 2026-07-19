package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_CardManeUpdate extends QLTB_CardManeInsert {
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer cardmane_id = null;

	public Integer getCardmane_id() {
		return cardmane_id;
	}

	public void setCardmane_id(Integer cardmane_id) {
		this.cardmane_id = cardmane_id;
	}

}
