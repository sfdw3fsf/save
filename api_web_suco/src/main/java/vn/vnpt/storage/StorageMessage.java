package vn.vnpt.storage;

public class StorageMessage {
	private boolean status = false;
	private Object message = null;
	public StorageMessage() {
		
	}
	public StorageMessage(boolean status, String message) {
		this.status = status;
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
}
