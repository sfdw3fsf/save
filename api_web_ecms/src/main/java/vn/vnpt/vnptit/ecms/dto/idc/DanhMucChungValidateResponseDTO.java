package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhMucChungValidateResponseDTO implements Serializable {
    private Integer errorCount;
    private String errorMessage;
    private Boolean isValid;

    public DanhMucChungValidateResponseDTO() {
    }

    public DanhMucChungValidateResponseDTO(Integer errorCount, String errorMessage) {
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

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
