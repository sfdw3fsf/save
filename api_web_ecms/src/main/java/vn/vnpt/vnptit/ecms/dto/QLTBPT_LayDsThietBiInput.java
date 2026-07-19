package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_LayDsThietBiInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer donViID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer kieu = 0;
	public int getDonViID() {
		return donViID;
	}
	public void setDonViID(int donViID) {
		this.donViID = donViID;
	}
	public int getKieu() {
		return kieu;
	}
	public void setKieu(int kieu) {
		this.kieu = kieu;
	}
}
