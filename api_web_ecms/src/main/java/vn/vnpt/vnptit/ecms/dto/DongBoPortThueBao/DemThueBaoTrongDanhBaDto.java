package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DemThueBaoTrongDanhBaDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number dslamID;
	
	private String maThueBao = null;
	private String[] excludeTB = null;
	
	public Number getDslamID() {
		return dslamID;
	}
	public void setDslamID(Number dslamID) {
		this.dslamID = dslamID;
	}
	public String getMaThueBao() {
		return maThueBao;
	}
	public void setMaThueBao(String maThueBao) {
		this.maThueBao = maThueBao;
	}
	public String[] getExcludeTB() {
		return excludeTB;
	}
	public void setExcludeTB(String[] excludeTB) {
		this.excludeTB = excludeTB;
	}

}