package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhSachPortTheoThietBiDto implements Serializable {
    
    private Long thietBiId;
    private String tenPort;
    private Integer congId;
    private String type;
    private Integer thuocNhom;
    
    public DanhSachPortTheoThietBiDto() {}
    
    public DanhSachPortTheoThietBiDto(Long thietBiId, String tenPort, Integer congId, String type, Integer thuocNhom) {
        this.thietBiId = thietBiId;
        this.tenPort = tenPort;
        this.congId = congId;
        this.type = type;
        this.thuocNhom = thuocNhom;
    }
    
    public Long getThietBiId() {
        return thietBiId;
    }
    
    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }
    
    public String getTenPort() {
        return tenPort;
    }
    
    public void setTenPort(String tenPort) {
        this.tenPort = tenPort;
    }

    public Integer getCongId() {
        return congId;
    }

    public void setCongId(Integer congId) {
        this.congId = congId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getThuocNhom() {
        return thuocNhom;
    }

    public void setThuocNhom(Integer thuocNhom) {
        this.thuocNhom = thuocNhom;
    }
}
