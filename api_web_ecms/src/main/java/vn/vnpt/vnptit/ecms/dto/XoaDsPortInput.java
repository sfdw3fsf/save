package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;

import vn.vnpt.message.ModelValidation;

public class XoaDsPortInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	private Number[] ids = null;
	public Number[] getIds() {	
		return this.ids;
	}
	public void setIds(Number[] ids) {
		this.ids = ids;
	}
}
