package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThanhNguonTrenRackBatchResponseDto {
    
    @JsonProperty("successCount")
    private Long successCount;
    
    @JsonProperty("failedCount")
    private Long failedCount;
    
    @JsonProperty("result")
    private Long result;

    // Default constructor
    public ThanhNguonTrenRackBatchResponseDto() {}

    // Constructor with parameters
    public ThanhNguonTrenRackBatchResponseDto(Long successCount, Long failedCount, Long result) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.result = result;
    }

    // Getters and Setters
    public Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public Long getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Long failedCount) {
        this.failedCount = failedCount;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
