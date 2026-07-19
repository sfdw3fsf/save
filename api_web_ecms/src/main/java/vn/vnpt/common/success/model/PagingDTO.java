package vn.vnpt.common.success.model;

import java.util.List;

import org.hibernate.validator.constraints.Range;

public class PagingDTO<T> {

	private int page;

	@Range(min = 1, max = 100, message = "IDG-00000004")
	private int maxSize = 100;

	private long totalElement;

	private long totalPages;

	private String sort;

	private String propertiesSort;

	private List<T> data;

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

	public long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
