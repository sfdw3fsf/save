package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class QLTBPT_CapNhatVanHanhKMInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer kieu = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer vanhanh_id = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer khoanmuc_id = 0;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer khoanmuc_id_2 = 0;
	private Integer soluong = 0;
	private Integer thanhtien = 0;
	private String ghichu ;
	private String tugio ;
	private String dengio ;


	public Integer getKieu() {
		return kieu;
	}

	public void setKieu(Integer kieu) {
		this.kieu = kieu;
	}

	public Integer getVanhanh_id() {
		return vanhanh_id;
	}

	public void setVanhanh_id(Integer vanhanh_id) {
		this.vanhanh_id = vanhanh_id;
	}

	public Integer getKhoanmuc_id() {
		return khoanmuc_id;
	}

	public void setKhoanmuc_id(Integer khoanmuc_id) {
		this.khoanmuc_id = khoanmuc_id;
	}

	public Integer getKhoanmuc_id_2() {
		return khoanmuc_id_2;
	}

	public void setKhoanmuc_id_2(Integer khoanmuc_id_2) {
		this.khoanmuc_id_2 = khoanmuc_id_2;
	}

	public Integer getSoluong() {
		return soluong;
	}

	public void setSoluong(Integer soluong) {
		this.soluong = soluong;
	}

	public Integer getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(Integer thanhtien) {
		this.thanhtien = thanhtien;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getTugio() {
		return tugio;
	}

	public void setTugio(String tugio) {
		this.tugio = tugio;
	}

	public String getDengio() {
		return dengio;
	}

	public void setDengio(String dengio) {
		this.dengio = dengio;
	}
}
