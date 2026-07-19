package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteThanhPhanThietBiDto {
    
    @JsonProperty("ids")
    private Long[] ids;

    public DeleteThanhPhanThietBiDto() {}

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
