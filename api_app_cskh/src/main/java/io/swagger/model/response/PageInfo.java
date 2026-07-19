package io.swagger.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * PageInfo
 */
@Validated
public class PageInfo {
    @JsonProperty("page_num")
    private Integer pageNum = 0;
    @JsonProperty("page_size")
    private Integer pageSize = 0;
    @JsonProperty("record_count")
    private Long recordCount = 0L;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }
}
