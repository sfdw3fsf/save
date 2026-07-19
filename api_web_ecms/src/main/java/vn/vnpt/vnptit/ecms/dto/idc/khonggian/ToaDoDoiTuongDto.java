package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class ToaDoDoiTuongDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE)
	private Number doiTuongID;

	@NotNull(message = ModelValidation.NOT_NULL)
	private String loaiDoiTuong;

	@NotNull(message = ModelValidation.NOT_NULL)
	private ToaDoDto[] dsToaDo;

	public Number getDoiTuongID() {
		return doiTuongID;
	}

	public void setDoiTuongID(Number doiTuongID) {
		this.doiTuongID = doiTuongID;
	}

	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	public ToaDoDto[] getDsToaDo() {
		return dsToaDo;
	}

	public void setDsToaDo(ToaDoDto[] dsToaDo) {
		this.dsToaDo = dsToaDo;
	}
}
