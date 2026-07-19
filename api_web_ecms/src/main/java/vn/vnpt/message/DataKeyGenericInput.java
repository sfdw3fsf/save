package vn.vnpt.message;

import javax.validation.constraints.NotNull;

public class DataKeyGenericInput<T> {
	@NotNull(message = ModelValidation.NOT_NULL)
    private T id = null;

	public T getId() {
		return this.id;
	}

	public void setIds(T id) {
		this.id = id;
	}
}
