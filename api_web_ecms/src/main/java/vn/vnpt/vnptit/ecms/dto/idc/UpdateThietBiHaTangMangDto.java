package vn.vnpt.vnptit.ecms.dto.idc;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class UpdateThietBiHaTangMangDto {
    @NotEmpty(message = "Danh sách thiết bị không được rỗng")
    private List<Long> thietBiIds;

    // null = gỡ, có giá trị = gán
    private Long haTangMangId;

    public List<Long> getThietBiIds() {
        return thietBiIds;
    }

    public void setThietBiIds(List<Long> thietBiIds) {
        this.thietBiIds = thietBiIds;
    }

    public Long getHaTangMangId() {
        return haTangMangId;
    }

    public void setHaTangMangId(Long haTangMangId) {
        this.haTangMangId = haTangMangId;
    }
}