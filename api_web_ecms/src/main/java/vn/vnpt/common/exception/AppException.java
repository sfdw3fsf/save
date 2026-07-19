package vn.vnpt.common.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = -4345298334101639005L;

	public AppException(Exception exception)
	{
		super(exception.getMessage(), exception.getCause(), exception.getSuppressed() != null, exception.getStackTrace() != null);
	}
}