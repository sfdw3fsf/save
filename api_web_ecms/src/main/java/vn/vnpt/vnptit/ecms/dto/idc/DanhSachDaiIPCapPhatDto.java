package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhSachDaiIPCapPhatDto implements Serializable {
    
    private String tenDaiIp;
    private String tenSubnet;
    private String tenDonviQl;
    private Integer trangThaiId;
    private Integer phieutcId;
    
    public DanhSachDaiIPCapPhatDto() {}
    
    public DanhSachDaiIPCapPhatDto(String tenDaiIp, String tenSubnet, String tenDonviQl) {
        this.tenDaiIp = tenDaiIp;
        this.tenSubnet = tenSubnet;
        this.tenDonviQl = tenDonviQl;
        this.trangThaiId = trangThaiId;
        this.phieutcId = phieutcId;
    }
    
    public String getTenDaiIp() {
        return tenDaiIp;
    }
    
    public void setTenDaiIp(String tenDaiIp) {
        this.tenDaiIp = tenDaiIp;
    }
    
    public String getTenSubnet() {
        return tenSubnet;
    }
    
    public void setTenSubnet(String tenSubnet) {
        this.tenSubnet = tenSubnet;
    }
    
    public String getTenDonviQl() {
        return tenDonviQl;
    }
    
    public void setTenDonviQl(String tenDonviQl) {
        this.tenDonviQl = tenDonviQl;
    }

    public Integer getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Integer trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Integer getPhieutcId() {
        return phieutcId;
    }

    public void setPhieutcId(Integer phieutcId) {
        this.phieutcId = phieutcId;
    }
}
