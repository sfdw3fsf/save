package vn.vnpt.entities;

import java.io.Serializable;

/**
 * AuditLog DTO for sending audit logs to the Gateway via RestTemplate.
 * This class replaces the previous JPA entity and manual builder.
 */
public class AuditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long duration;
    private Object requestValue;
    private Object responseValue;
    private String className;
    private String methodName;
    private String description;

    public AuditLog() {
    }

    public AuditLog(Long duration, Object requestValue, Object responseValue, String className, String methodName, String description) {
        this.duration = duration;
        this.requestValue = requestValue;
        this.responseValue = responseValue;
        this.className = className;
        this.methodName = methodName;
        this.description = description;
    }

    // Getters and Setters
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Object getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(Object requestValue) {
        this.requestValue = requestValue;
    }

    public Object getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(Object responseValue) {
        this.responseValue = responseValue;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Builder Pattern
    public static AuditLogBuilder builder() {
        return new AuditLogBuilder();
    }

    public static class AuditLogBuilder {
        private Long duration;
        private Object requestValue;
        private Object responseValue;
        private String className;
        private String methodName;
        private String description;

        public AuditLogBuilder duration(Long duration) {
            this.duration = duration;
            return this;
        }

        public AuditLogBuilder requestValue(Object requestValue) {
            this.requestValue = requestValue;
            return this;
        }

        public AuditLogBuilder responseValue(Object responseValue) {
            this.responseValue = responseValue;
            return this;
        }

        public AuditLogBuilder className(String className) {
            this.className = className;
            return this;
        }

        public AuditLogBuilder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public AuditLogBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(duration, requestValue, responseValue, className, methodName, description);
        }
    }
}
