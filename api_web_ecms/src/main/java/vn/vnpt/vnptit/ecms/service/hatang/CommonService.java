package vn.vnpt.vnptit.ecms.service.hatang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	/*
	 * private final DbContext dbContext;
	 * public CommonService(DbContext dbContext) { this.dbContext = dbContext; }
	 */

	public ArrayList<Map<String, Object>> getDsPhanLoaiCong() {
		ArrayList<Map<String, Object>> items = new ArrayList<>();
		items.add(this.createItem(1, "Interface"));
		items.add(this.createItem(2, "Power"));
		items.add(this.createItem(3, "Console"));
		return items;
	}

	private Map<String, Object> createItem(Number id, String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ID", id);
		result.put("Name", name);
		return result;
	}

	public ArrayList<Map<String, Object>> getDsNhomCong() {
		ArrayList<Map<String, Object>> items = new ArrayList<>();
		items.add(this.createItem(1, "Serial"));
		items.add(this.createItem(2, "USB"));
		items.add(this.createItem(3, "Other"));
		return items;
	}
}
