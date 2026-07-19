package vn.vnptit.sapi.oneapp.managed;

public class ResponseWithTraceId<T> {
    private final T response;
    private final String traceId;

    public ResponseWithTraceId(T response, String traceId) {
        this.response = response;
        this.traceId = traceId;
    }

    public T getResponse() {
        return response;
    }

    public String getTraceId() {
        return traceId;
    }
}
