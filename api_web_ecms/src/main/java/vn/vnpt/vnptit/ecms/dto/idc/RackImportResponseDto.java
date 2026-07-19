package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.ArrayList;
import java.util.List;

/**
 * Response DTO cho kết quả import Rack.
 * Chứa thông tin số lượng thành công/thất bại và danh sách các dòng lỗi.
 */
public class RackImportResponseDto {
    private Integer successCount;          // Số dòng import thành công
    private Integer failedCount;           // Số dòng import thất bại
    private List<RackImportRowResult> results;  // Chi tiết kết quả từng dòng

    // Default constructor
    public RackImportResponseDto() {
        this.successCount = 0;
        this.failedCount = 0;
        this.results = new ArrayList<>();
    }

    public RackImportResponseDto(Integer successCount, Integer failedCount, List<RackImportRowResult> results) {
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.results = results;
    }

    // Getters and Setters
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

    public List<RackImportRowResult> getResults() {
        return results;
    }

    public void setResults(List<RackImportRowResult> results) {
        this.results = results;
    }

    // Helper methods
    public void addSuccess() {
        this.successCount++;
    }

    public void addFailed() {
        this.failedCount++;
    }

    public void addResult(RackImportRowResult result) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
        this.results.add(result);
    }

    /**
     * Inner class for individual row result
     */
    public static class RackImportRowResult {
        private Integer stt;           // Số thứ tự dòng
        private String status;         // "SUCCESS" hoặc "ERROR"
        private String message;        // Thông báo lỗi (nếu có)
        private Number rackId;         // ID rack mới tạo (nếu thành công)

        public RackImportRowResult() {
        }

        public RackImportRowResult(Integer stt, String status, String message, Number rackId) {
            this.stt = stt;
            this.status = status;
            this.message = message;
            this.rackId = rackId;
        }

        // Getters and Setters
        public Integer getStt() {
            return stt;
        }

        public void setStt(Integer stt) {
            this.stt = stt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Number getRackId() {
            return rackId;
        }

        public void setRackId(Number rackId) {
            this.rackId = rackId;
        }

        public boolean isSuccess() {
            return "SUCCESS".equalsIgnoreCase(this.status);
        }
    }
}
