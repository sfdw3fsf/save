package vn.vnpt.common.exception.model;

public class messageObject {

	private String objectName;
	private String message;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param fieldName
	 * @param messages
	 */
	public messageObject(String objectName, String message) {
		super();
		this.objectName = objectName;
		this.message = message;
	}

}
