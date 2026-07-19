package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_CardGpon_KT_ViTriInput {
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer gpon_id = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
//    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer vitri = null;
	
	public Integer getGpon_id() {
		return gpon_id;
	}
	public void setGpon_id(Integer gpon_id) {
		this.gpon_id = gpon_id;
	}
	public Integer getVitri() {
		return vitri;
	}
	public void setVitri(Integer vitri) {
		this.vitri = vitri;
	}

}
