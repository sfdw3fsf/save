package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DemThueBaoDongBoDto {
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number brasID;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number dslamID;
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number trangThaiID;
	private String maThueBao = null;
	
	public Number getBrasID() {
		return brasID;
	}
	public void setBrasID(Number brasID) {
		this.brasID = brasID;
	}
	public Number getDslamID() {
		return dslamID;
	}
	public void setDslamID(Number dslamID) {
		this.dslamID = dslamID;
	}
	public Number getTrangThaiID() {
		return trangThaiID;
	}
	public void setTrangThaiID(Number trangThaiID) {
		this.trangThaiID = trangThaiID;
	}
	public String getMaThueBao() {
		return maThueBao;
	}
	public void setMaThueBao(String maThueBao) {
		this.maThueBao = maThueBao;
	}

}