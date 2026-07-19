package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DauNoiHTMvsHTCLOUDDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangmang_id;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangmartcloud_id;
	
	public Number getHatangmang_id() {
		return hatangmang_id;
	}
	public void setHatangmang_id(Number hatangmang_id) {
		this.hatangmang_id = hatangmang_id;
	}
	public Number gethatangmartcloud_id() {
		return hatangmartcloud_id;
	}
	public void sethatangmartcloud_id(Number hatangmartcloud_id) {
		this.hatangmartcloud_id = hatangmartcloud_id;
	}

}
