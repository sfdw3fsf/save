package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class DsThueBaoTrongDanhBaDto extends DemThueBaoTrongDanhBaDto {
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number pageIndex;
	@NotNull(message = ModelValidation.NOT_NULL)
	@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number pageSize;
	public Number getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Number pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Number getPageSize() {
		return pageSize;
	}
	public void setPageSize(Number pageSize) {
		this.pageSize = pageSize;
	}
}