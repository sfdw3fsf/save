package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.message.ModelValidation;

public class CapNhatHeThongDichVuDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	private Integer hethong_id;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String dvi_chuquan;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ma_hethong;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ten_thietbi;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String ten_hethong;

	private String linh_vuc;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String email_nv;
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String so_dt_nv;
	private String province_code = AppRequestContext.getCurrent().getMaTinh();

	public Integer getHethong_id() {
		return hethong_id;
	}

	public void setHethong_id(Integer hethong_id) {
		this.hethong_id = hethong_id;
	}

	public String getDvi_chuquan() {
		return dvi_chuquan;
	}

	public void setDvi_chuquan(String dvi_chuquan) {
		this.dvi_chuquan = dvi_chuquan;
	}

	public String getMa_hethong() {
		return ma_hethong;
	}

	public void setMa_hethong(String ma_hethong) {
		this.ma_hethong = ma_hethong;
	}

	public String getTen_thietbi() {
		return ten_thietbi;
	}

	public void setTen_thietbi(String ten_thietbi) {
		this.ten_thietbi = ten_thietbi;
	}

	public String getTen_hethong() {
		return ten_hethong;
	}

	public void setTen_hethong(String ten_hethong) {
		this.ten_hethong = ten_hethong;
	}

	public String getLinh_vuc() {
		return linh_vuc;
	}

	public void setLinh_vuc(String linh_vuc) {
		this.linh_vuc = linh_vuc;
	}

	public String getEmail_nv() {
		return email_nv;
	}

	public void setEmail_nv(String email_nv) {
		this.email_nv = email_nv;
	}

	public String getSo_dt_nv() {
		return so_dt_nv;
	}

	public void setSo_dt_nv(String so_dt_nv) {
		this.so_dt_nv = so_dt_nv;
	}

	public String getProvince_code() {
		return province_code;
	}

	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
}
