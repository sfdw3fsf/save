package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.common.converter.JsonConvert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class IdcRackBatchValidateResponseDTO {
    private Integer errorCount;
    private String errorMessage;
    private ArrayList<Map<String, Object>> response;

    private Boolean isValid;

    public ArrayList<Map<String, Object>> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Map<String, Object>> response) {
        this.response = response;
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

    public IdcRackBatchValidateResponseDTO(Integer errorCount, String errorMessage, ArrayList<Map<String, Object>> response, Boolean isValid) throws IOException {
        this.errorCount = errorCount;
        this.errorMessage = errorMessage;
        this.response = response;

        this.isValid = isValid;
    }

    public IdcRackBatchValidateResponseDTO() {
    }
}
