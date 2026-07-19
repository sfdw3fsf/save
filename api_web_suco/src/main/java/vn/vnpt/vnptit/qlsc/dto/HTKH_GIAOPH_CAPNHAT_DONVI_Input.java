package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class HTKH_GIAOPH_CAPNHAT_DONVI_Input {
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number htkhID = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number donViID = null;
	@NotNull(message = ModelValidation.NOT_NULL) 
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number huongGiaoID = null;
	
	public Number getHtkhID() {
		return htkhID;
	}
	public void setHtkhID(Number htkhID) {
		this.htkhID = htkhID;
	}
	public Number getDonViID() {
		return donViID;
	}
	public void setDonViID(Number donViID) {
		this.donViID = donViID;
	}
	public Number getHuongGiaoID() {
		return huongGiaoID;
	}
	public void setHuongGiaoID(Number huongGiaoID) {
		this.huongGiaoID = huongGiaoID;
	}
}
