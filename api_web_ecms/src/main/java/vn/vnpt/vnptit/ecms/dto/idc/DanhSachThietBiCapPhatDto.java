package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhSachThietBiCapPhatDto implements Serializable {
    
    private String tenThietBi;
    private Integer thietbiId;
    private String type;
    
    public DanhSachThietBiCapPhatDto() {}
    
    public DanhSachThietBiCapPhatDto(String tenThietBi, Integer thietbiId, String type) {
        this.tenThietBi = tenThietBi;
        this.thietbiId = thietbiId;
        this.type = type;
    }
    
    public String getTenThietBi() {
        return tenThietBi;
    }
    
    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public Integer getThietbiId() {
        return thietbiId;
    }

    public void setThietbiId(Integer thietbiId) {
        this.thietbiId = thietbiId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
