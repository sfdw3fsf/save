package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class CapNhatTTDsPortInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	private Number[] ids = null;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	@NotNull(message = ModelValidation.NOT_NULL)
	private int trangThaiID = 0;
	public Number[] getIds() {	
		return this.ids;
	}
	public void setIds(Number[] ids) {
		this.ids = ids;
	}
	public int getTrangThaiID() {	
		return this.trangThaiID;
	}
	public void setTrangThaiID(int trangThaiID) {
		this.trangThaiID = trangThaiID;
	}
}
