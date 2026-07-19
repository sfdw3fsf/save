package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_CardGpon_KT_ThayCardInput {
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer cardgp_id = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer soport_moi = null;
	public Integer getCardgp_id() {
		return cardgp_id;
	}
	public void setCardgp_id(Integer cardgp_id) {
		this.cardgp_id = cardgp_id;
	}
	public Integer getSoport_moi() {
		return soport_moi;
	}
	public void setSoport_moi(Integer soport_moi) {
		this.soport_moi = soport_moi;
	}
}
