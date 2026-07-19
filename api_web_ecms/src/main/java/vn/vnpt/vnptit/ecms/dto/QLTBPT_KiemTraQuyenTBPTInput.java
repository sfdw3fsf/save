package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class QLTBPT_KiemTraQuyenTBPTInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer nguoiDungID = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer quyenID = 0;
	public Integer getNguoiDungID() {
		return nguoiDungID;
	}
	public void setNguoiDungID(Integer nguoiDungID) {
		this.nguoiDungID = nguoiDungID;
	}
	public Integer getQuyenID() {
		return quyenID;
	}
	public void setQuyenID(Integer quyenID) {
		this.quyenID = quyenID;
	}
}
