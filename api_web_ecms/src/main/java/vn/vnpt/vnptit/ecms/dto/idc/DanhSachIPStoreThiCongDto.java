package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhSachIPStoreThiCongDto implements Serializable {
    
    private Long phieutcId;
    
    public DanhSachIPStoreThiCongDto() {}
    
    public DanhSachIPStoreThiCongDto(Long phieutcId) {
        this.phieutcId = phieutcId;
    }
    
    public Long getPhieutcId() {
        return phieutcId;
    }
    
    public void setPhieutcId(Long phieutcId) {
        this.phieutcId = phieutcId;
    }
}
