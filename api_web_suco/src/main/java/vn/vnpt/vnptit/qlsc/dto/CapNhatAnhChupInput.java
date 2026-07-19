package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class CapNhatAnhChupInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number phieuID = null; 
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String moTa = null;
	public Number getPhieuID() {
		return phieuID;
	}
	public void setPhieuID(Number phieuID) {
		this.phieuID = phieuID;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
}
