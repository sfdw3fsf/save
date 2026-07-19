package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_timKiemThietBi {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer kieuTu = 0;
	
	@NotNull(message = ModelValidation.NOT_EMPTY)
    private String text = null;

	public Integer getKieuTu() {
		return kieuTu;
	}

	public void setKieuTu(Integer kieuTu) {
		this.kieuTu = kieuTu;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
