package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DauNoiHTAHvsHTIDGDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangaohoa_id;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangidg_id;
	
	public Number getHatangaohoa_id() {
		return hatangaohoa_id;
	}
	public void setHatangaohoa_id(Number hatangaohoa_id) {
		this.hatangaohoa_id = hatangaohoa_id;
	}
	public Number getHatangidg_id() {
		return hatangidg_id;
	}
	public void setHatangidg_id(Number hatangidg_id) {
		this.hatangidg_id = hatangidg_id;
	}

}
