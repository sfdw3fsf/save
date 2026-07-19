package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMultiDto {
    
    @JsonProperty("ids")
    private Long[] ids;

    // Constructors
    public DeleteMultiDto() {}

    public DeleteMultiDto(Long[] ids) {
        this.ids = ids;
    }

    // Getters and Setters
    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
