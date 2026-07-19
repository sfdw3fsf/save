package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTB_TBI_KETNOI extends QLTB_TBI_MAKETNOI {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer porvl_id_n = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer porvl_id_d = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer thietbi_id_n = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer thietbi_id_d = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer link_code = null;
	public Integer getPorvl_id_n() {
		return porvl_id_n;
	}
	public void setPorvl_id_n(Integer porvl_id_n) {
		this.porvl_id_n = porvl_id_n;
	}
	public Integer getPorvl_id_d() {
		return porvl_id_d;
	}
	public void setPorvl_id_d(Integer porvl_id_d) {
		this.porvl_id_d = porvl_id_d;
	}
	public Integer getThietbi_id_n() {
		return thietbi_id_n;
	}
	public void setThietbi_id_n(Integer thietbi_id_n) {
		this.thietbi_id_n = thietbi_id_n;
	}
	public Integer getThietbi_id_d() {
		return thietbi_id_d;
	}
	public void setThietbi_id_d(Integer thietbi_id_d) {
		this.thietbi_id_d = thietbi_id_d;
	}
	public Integer getLink_code() {
		return link_code;
	}
	public void setLink_code(Integer link_code) {
		this.link_code = link_code;
	}
}
