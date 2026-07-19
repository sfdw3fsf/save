package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhMucLoaiOCungBatchResponseDto implements Serializable {
    private Integer successCount;
    private Integer failedCount;
    private Integer result;
    private Boolean isSuccess;

    public DanhMucLoaiOCungBatchResponseDto() {
    }

    public DanhMucLoaiOCungBatchResponseDto(Integer successCount, Integer failedCount, Integer result) {
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

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
