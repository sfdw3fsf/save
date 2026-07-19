package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_TBI_GOKETNOI extends QLTB_TBI_MAKETNOI {
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private int porvl_id_n = 0;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private int porvl_id_d = 0;
	public int getPorvl_id_n() {
		return porvl_id_n;
	}
	public void setPorvl_id_n(int porvl_id_n) {
		this.porvl_id_n = porvl_id_n;
	}
	public int getPorvl_id_d() {
		return porvl_id_d;
	}
	public void setPorvl_id_d(int porvl_id_d) {
		this.porvl_id_d = porvl_id_d;
	}
}
