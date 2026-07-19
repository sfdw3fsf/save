package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;

public class DanhMucIOPSValidateResponseDto implements Serializable {
    private Integer errorCount;
    private String errorMessage;
    private Boolean isValid;

    public DanhMucIOPSValidateResponseDto() {
    }

    public DanhMucIOPSValidateResponseDto(Integer errorCount, String errorMessage) {
        this.errorCount = errorCount;
        this.errorMessage = errorMessage;
        this.isValid = errorCount != null && errorCount == 0;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
