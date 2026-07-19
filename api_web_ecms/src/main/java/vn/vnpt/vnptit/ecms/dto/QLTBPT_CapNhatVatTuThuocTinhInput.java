package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_CapNhatVatTuThuocTinhInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer thietBiID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer ttvtID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer gtttID = 0;
	@NotNull(message = ModelValidation.NOT_EMPTY)
    private String giaTri = null;
	public Integer getThietBiID() {
		return thietBiID;
	}
	public void setThietBiID(Integer thietBiID) {
		this.thietBiID = thietBiID;
	}
	public Integer getTtvtID() {
		return ttvtID;
	}
	public void setTtvtID(Integer ttvtID) {
		this.ttvtID = ttvtID;
	}
	public Integer getGtttID() {
		return gtttID;
	}
	public void setGtttID(Integer gtttID) {
		this.gtttID = gtttID;
	}
	public String getGiaTri() {
		return giaTri;
	}
	public void setGiaTri(String giaTri) {
		this.giaTri = giaTri;
	}
}
