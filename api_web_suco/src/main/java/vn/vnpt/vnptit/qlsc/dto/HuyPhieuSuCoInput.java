package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class HuyPhieuSuCoInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer traPhieu = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Integer[] suCoIDs = null;
	public Integer getTraPhieu() {
		return traPhieu;
	}
	public void setTraPhieu(Integer traPhieu) {
		this.traPhieu = traPhieu;
	}
	public Integer[] getSuCoIDs() {
		return suCoIDs;
	}
	public void setSuCoIDs(Integer[] suCoIDs) {
		this.suCoIDs = suCoIDs;
	}

}
