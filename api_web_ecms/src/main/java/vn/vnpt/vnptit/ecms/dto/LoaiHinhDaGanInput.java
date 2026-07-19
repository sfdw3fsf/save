package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class LoaiHinhDaGanInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@JsonProperty("vrpId")
	private Integer[] vrpId = null;

	@JsonProperty("loaiTbId")
	@NotNull(message = ModelValidation.NOT_NULL)
	private Integer[] loaiTbId = null;

	public Integer[] getVrpId() {
		return vrpId;
	}

	public void setVrpId(Integer[] vrpId) {
		this.vrpId = vrpId;
	}

	public Integer[] getLoaiTbId() {
		return loaiTbId;
	}

	public void setLoaiTbId(Integer[] loaiTbId) {
		this.loaiTbId = loaiTbId;
	}
}
