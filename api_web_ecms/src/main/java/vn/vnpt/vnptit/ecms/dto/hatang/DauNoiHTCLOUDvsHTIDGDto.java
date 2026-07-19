package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DauNoiHTCLOUDvsHTIDGDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangsmartcloud_id;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangidg_id;
	
	public Number getHatangsmartcloud_id() {
		return hatangsmartcloud_id;
	}
	public void setHatangsmartcloud_id(Number hatangsmartcloud_id) {
		this.hatangsmartcloud_id = hatangsmartcloud_id;
	}
	public Number getHatangidg_id() {
		return hatangidg_id;
	}
	public void setHatangidg_id(Number hatangidg_id) {
		this.hatangidg_id = hatangidg_id;
	}

}
