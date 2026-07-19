package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class CapNhatRacksDslamInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Integer dslam_id = null;
	@NotNull(message = ModelValidation.NOT_NULL)
	private Integer[] racks_id = null;
	public Integer getDslam_id() {
		return dslam_id;
	}
	public void setDslam_id(Integer dslam_id) {
		this.dslam_id = dslam_id;
	}
	public Integer[] getRacks_id() {
		return racks_id;
	}
	public void setRacks_id(Integer[] racks_id) {
		this.racks_id = racks_id;
	}
}
