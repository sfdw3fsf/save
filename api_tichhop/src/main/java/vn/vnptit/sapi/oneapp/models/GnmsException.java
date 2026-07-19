package vn.vnptit.sapi.oneapp.models;

public class GnmsException extends Exception {
    private int code;
    public GnmsException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
