package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DanhSachIPCapPhatDto implements Serializable {
    
    private String ipAddress;
    private String tenSubnet;
    private Integer trangThaiId;
    private Integer phieutcId;
    
    public DanhSachIPCapPhatDto() {}
    
    public DanhSachIPCapPhatDto(String ipAddress, String tenSubnet) {
        this.ipAddress = ipAddress;
        this.tenSubnet = tenSubnet;
        this.trangThaiId = trangThaiId;
        this.phieutcId = phieutcId;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getTenSubnet() {
        return tenSubnet;
    }
    
    public void setTenSubnet(String tenSubnet) {
        this.tenSubnet = tenSubnet;
    }

    public Integer gettrangThaiId() {
        return trangThaiId;
    }

    public void settrangThaiId(Integer trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Integer getPhieutcId() {
        return phieutcId;
    }

    public void setPhieutcId(Integer phieutcId) {
        this.phieutcId = phieutcId;
    }
}
