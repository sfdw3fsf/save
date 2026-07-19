package vn.vnpt.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class DataKeyInput {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer id = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
