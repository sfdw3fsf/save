package vn.vnpt.common.success.model;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import vn.vnpt.common.FieldCommon;

public class PageRequestBuilder {
	private PageRequestBuilder() {
		// Do nothing
	}

	private static final int MAXSIZE = 10;

	private static final int PAGE_DEFAULT = 1;

	private static final int NUMBER_ZERO = 0;

	public static PageRequest getPageRequest(int maxSize, int page, String sort, String propertiesSort,
			Class<?> className) {

		if (page == NUMBER_ZERO) {
			page = PAGE_DEFAULT;
		}

		if (maxSize == NUMBER_ZERO) {
			maxSize = MAXSIZE;
		}
		if (!FieldCommon.isFieldDeclared(className, propertiesSort)) {
			Field[] fields = className.getDeclaredFields();
			List<String> actualFieldNames = FieldCommon.getFieldNames(fields);
			propertiesSort = actualFieldNames.get(NUMBER_ZERO);
		}

		if (Sort.Direction.ASC.toString().equalsIgnoreCase(sort)) {
			sort = Sort.Direction.ASC.toString();
		} else {
			sort = Sort.Direction.DESC.toString();
		}

		PageRequest pageRequest = PageRequest.of(page - 1, maxSize, Sort.Direction.fromString(sort), propertiesSort);
		return pageRequest;
	}

	public static PageRequest getPageRequest(int maxSize, int page) {

		if (page == NUMBER_ZERO) {
			page = PAGE_DEFAULT;
		}

		if (maxSize == NUMBER_ZERO) {
			maxSize = MAXSIZE;
		}
		PageRequest pageRequest = PageRequest.of(page - 1, maxSize);
		return pageRequest;
	}
}
