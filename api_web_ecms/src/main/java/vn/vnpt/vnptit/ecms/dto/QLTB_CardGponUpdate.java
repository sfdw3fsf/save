package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_CardGponUpdate extends QLTB_CardGponInsert {
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer cardgp_id = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Boolean istaoportlg = false; // lvhquan: Thêm điều kiện tạo portlogic

	public Integer getCardgp_id() {
		return cardgp_id;
	}

	public void setCardgp_id(Integer cardgp_id) {
		this.cardgp_id = cardgp_id;
	}

}
