package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class KiemTraDieuKienTiepNhanInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer kieu = null;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String maSuCo = null;

	public Integer getKieu() {
		return kieu;
	}

	public void setKieu(Integer kieu) {
		this.kieu = kieu;
	}

	public String getMaSuCo() {
		return maSuCo;
	}

	public void setMaSuCo(String maSuCo) {
		this.maSuCo = maSuCo;
	}
}
