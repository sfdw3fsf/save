package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ModelValidation;

public class QLTB_CaySoDoInput extends DataKeyInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer kieuTu = 0;

	public Integer getKieuTu() {
		return kieuTu;
	}

	public void setKieuTu(Integer kieuTu) {
		this.kieuTu = kieuTu;
	}
}
