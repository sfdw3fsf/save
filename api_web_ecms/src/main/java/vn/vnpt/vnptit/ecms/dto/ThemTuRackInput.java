package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.checkerframework.checker.units.qual.Length;

import vn.vnpt.message.ModelValidation;

public class ThemTuRackInput {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Long loaitu_id;

	public Long getloaitu_id() {
		return this.loaitu_id;
	}

	public void setloaitu_id(Long value) {
		this.loaitu_id = value;
	}

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Long vitri;

	public Long getvitri() {
		return this.vitri;
	}

	public void setvitri(Long value) {
		this.vitri = value;
	}

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Long kieutu;

	public Long getkieutu() {
		return this.kieutu;
	}

	public void setkieutu(Long value) {
		this.kieutu = value;
	}

	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	@NotNull(message = ModelValidation.NOT_NULL)
	@Size(min = 1, max = 200, message = "Giá trị tham số có chiều dài không được lớn hơn 200" )
	private String ten_tu;

	public String getten_tu() {
		return this.ten_tu;
	}

	public void setten_tu(String value) {
		this.ten_tu = value;
	}

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Long _donvi_id;

	public Long getdonvi_id() {
		return this._donvi_id;
	}

	public void setdonvi_id(Long value) {
		this._donvi_id = value;
	}
}