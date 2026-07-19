package vn.vnpt.message;

public class DetailResultOutput extends ResultOutput {
    private Object detail = null;

    public DetailResultOutput() {
    	this(false);
    }
    
    public DetailResultOutput(Boolean result) {
    	super(result);
    }
    
    public DetailResultOutput(Boolean result, Object detail) {
    	this(result);
    	this.setDetail(detail);
    }
   
	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}
}
