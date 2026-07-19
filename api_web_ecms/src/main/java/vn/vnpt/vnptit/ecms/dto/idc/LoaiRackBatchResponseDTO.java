package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class LoaiRackBatchResponseDTO implements Serializable {
    
    private Integer successCount;
    private Integer failedCount;
    private Integer result;
    private Boolean isSuccess;
    
    public LoaiRackBatchResponseDTO() {
    }
    
    public LoaiRackBatchResponseDTO(Integer successCount, Integer failedCount, Integer result) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.result = result;
        this.isSuccess = result != null && result == 1;
    }
    
    public Integer getSuccessCount() {
        return successCount;
    }
    
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }
    
    public Integer getFailedCount() {
        return failedCount;
    }
    
    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }
    
    public Integer getResult() {
        return result;
    }
    
    public void setResult(Integer result) {
        this.result = result;
    }
    
    public Boolean getIsSuccess() {
        return isSuccess;
    }
    
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
