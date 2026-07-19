package vn.vnpt.vnptit.ecms.dto.hatang;

import java.util.List;

public class ThietBiCumDto {
    private Long cumId;
    private List<Long> thietbiIds;
    
    public Long getCumId() {
        return cumId;
    }
    public void setCumId(Long cumId) {
        this.cumId = cumId;
    }
    public List<Long> getThietbiIds() {
        return thietbiIds;
    }
    public void setThietbiIds(List<Long> thietbiIds) {
        this.thietbiIds = thietbiIds;
    }
}
