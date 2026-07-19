package vn.vnpt.message;

import java.util.HashMap;
import java.util.Map;

public class DetailKeyValueOutput extends ResultOutput {
	private Map<String, Object> detail = new HashMap<>();

    public DetailKeyValueOutput() {
    	this(false);
    }
    
    public DetailKeyValueOutput(Boolean result) {
    	super(result);
    }
    
    public DetailKeyValueOutput(Boolean result, String key, Object value) {
    	this(result);
    	this.addDetail(key, value);
    }
    
	public Object getDetail() {
		return detail;
	}

	public void addDetail(String key, Object value) {
		this.detail.put(key, value);
	}
}
