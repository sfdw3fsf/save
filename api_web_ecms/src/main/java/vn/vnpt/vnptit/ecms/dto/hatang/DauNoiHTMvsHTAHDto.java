package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DauNoiHTMvsHTAHDto {

	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangmang_id;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
	private Number hatangaohoa_id;
	
	public Number getHatangmang_id() {
		return hatangmang_id;
	}
	public void setHatangmang_id(Number hatangmang_id) {
		this.hatangmang_id = hatangmang_id;
	}
	public Number getHatangaohoa_id() {
		return hatangaohoa_id;
	}
	public void setHatangaohoa_id(Number hatangaohoa_id) {
		this.hatangaohoa_id = hatangaohoa_id;
	}

}
