package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class TiepNhanSuCoTamInput {
	@NotEmpty(message = ModelValidation.NOT_EMPTY)
	private String maSuCo = null;
    private String tinhTrang = null;
	private String anhChup = null;
	public String getMaSuCo() {
		return maSuCo;
	}
	public void setMaSuCo(String maSuCo) {
		this.maSuCo = maSuCo;
	}
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getAnhChup() {
		return anhChup;
	}
	public void setAnhChup(String anhChup) {
		this.anhChup = anhChup;
	}

}
