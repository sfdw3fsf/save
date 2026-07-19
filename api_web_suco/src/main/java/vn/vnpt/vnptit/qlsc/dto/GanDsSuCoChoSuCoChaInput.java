package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class GanDsSuCoChoSuCoChaInput {
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer suCoChaID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Integer[] suCoIDs = null;
	public Integer[] getSuCoIDs() {
		return suCoIDs;
	}
	public void setSuCoIDs(Integer[] suCoIDs) {
		this.suCoIDs = suCoIDs;
	}
	public Integer getSuCoChaID() {
		return suCoChaID;
	}
	public void setSuCoChaID(Integer suCoChaID) {
		this.suCoChaID = suCoChaID;
	}

}
