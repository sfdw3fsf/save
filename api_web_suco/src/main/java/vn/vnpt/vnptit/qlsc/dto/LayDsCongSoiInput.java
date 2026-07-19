package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class LayDsCongSoiInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer loaiThietBiID = null; 
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer thietBiID = null;
	public Integer getLoaiThietBiID() {
		return loaiThietBiID;
	}
	public void setLoaiThietBiID(Integer loaiThietBiID) {
		this.loaiThietBiID = loaiThietBiID;
	}
	public Integer getThietBiID() {
		return thietBiID;
	}
	public void setThietBiID(Integer thietBiID) {
		this.thietBiID = thietBiID;
	}
}
