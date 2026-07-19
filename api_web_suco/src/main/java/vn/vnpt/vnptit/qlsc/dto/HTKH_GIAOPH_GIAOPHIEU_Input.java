package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class HTKH_GIAOPH_GIAOPHIEU_Input {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number htkhID = null;
	@NotNull(message = ModelValidation.NOT_EMPTY)
	private String noiDung = null;
	private Number phieuChaID = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number huongGiaoID = null;
	private String soCongVan = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Number[] dsDonViNhanID = null;

	public Number getHtkhID() {
		return htkhID;
	}

	public void setHtkhID(Number htkhID) {
		this.htkhID = htkhID;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public Number getPhieuChaID() {
		return phieuChaID;
	}

	public void setPhieuChaID(Number phieuChaID) {
		this.phieuChaID = phieuChaID;
	}

	public Number getHuongGiaoID() {
		return huongGiaoID;
	}

	public void setHuongGiaoID(Number huongGiaoID) {
		this.huongGiaoID = huongGiaoID;
	}

	public String getSoCongVan() {
		return soCongVan;
	}

	public void setSoCongVan(String soCongVan) {
		this.soCongVan = soCongVan;
	}

	public Number[] getDsDonViNhanID() {
		return dsDonViNhanID;
	}

	public void setDsDonViNhanID(Number[] dsDonViNhanID) {
		this.dsDonViNhanID = dsDonViNhanID;
	}
}
