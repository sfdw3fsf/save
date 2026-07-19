package vn.vnpt.common;

public class NameValue {

	private String name = null;
	
	private Object value = null;

	public NameValue() {}

	public NameValue(String name) {
		this(name, null);
	}

	public NameValue(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
