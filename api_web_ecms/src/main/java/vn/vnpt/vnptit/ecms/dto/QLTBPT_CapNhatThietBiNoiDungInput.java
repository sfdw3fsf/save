package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_CapNhatThietBiNoiDungInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer thietBiID = 0;
	private String noiDung = null;
	public Integer getThietBiID() {
		return thietBiID;
	}
	public void setThietBiID(Integer thietBiID) {
		this.thietBiID = thietBiID;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
}
