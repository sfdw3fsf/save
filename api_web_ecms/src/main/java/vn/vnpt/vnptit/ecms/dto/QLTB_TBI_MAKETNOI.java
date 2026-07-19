package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_TBI_MAKETNOI {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer loaitbi_id_n = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer loaitbi_id_d = null;
	
	public Integer getLoaitbi_id_n() {
		return loaitbi_id_n;
	}
	public void setLoaitbi_id_n(Integer loaitbi_id_n) {
		this.loaitbi_id_n = loaitbi_id_n;
	}
	public Integer getLoaitbi_id_d() {
		return loaitbi_id_d;
	}
	public void setLoaitbi_id_d(Integer loaitbi_id_d) {
		this.loaitbi_id_d = loaitbi_id_d;
	}

}
