package vn.vnpt.common.success.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class PagingDtoIn {

	private int page;

	@NotNull(message = "IDG-00000001")
	@Range(min = 1, max = 100, message = "IDG-00000004")
	private int maxSize = 100;

	private String sort;

	private String propertiesSort;

	public PagingDtoIn(int page, @NotNull(message = "IDG-00000001") @Range(min = 1, max = 100, message = "IDG-00000004") int maxSize, String sort, String propertiesSort) {
		this.page = page;
		this.maxSize = maxSize;
		this.sort = sort;
		this.propertiesSort = propertiesSort;
	}

	public PagingDtoIn(int page, int maxSize) {
		this.page = page;
		this.maxSize = maxSize;
	}

	public PagingDtoIn() {
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getPropertiesSort() {
		return propertiesSort;
	}

	public void setPropertiesSort(String propertiesSort) {
		this.propertiesSort = propertiesSort;
	}

}
