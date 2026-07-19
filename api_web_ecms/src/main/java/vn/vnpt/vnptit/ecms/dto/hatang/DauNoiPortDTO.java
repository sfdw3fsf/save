package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;

public class DauNoiPortDTO implements Serializable {
    private Long thietBiNguonId;
    private Long thietBiDichId;
    private Long portNguonId;
    private Long portDichId;
    private String ghiChu;
 
    public DauNoiPortDTO(Long thietBiNguonId, Long thietBiDichId, Long portNguonId, Long portDichId, String ghiChu) {
        this.thietBiNguonId = thietBiNguonId;
        this.thietBiDichId = thietBiDichId;
        this.portNguonId = portNguonId;
        this.portDichId = portDichId;
        this.ghiChu = ghiChu;
    }

    public DauNoiPortDTO() {
    }

    public Long getThietBiNguonId() {
        return thietBiNguonId;
    }

    public Long getThietBiDichId() {
        return thietBiDichId;
    }

    public Long getPortNguonId() {
        return portNguonId;
    }   

    public Long getPortDichId() {   
        return portDichId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setThietBiNguonId(Long thietBiNguonId) {
        this.thietBiNguonId = thietBiNguonId;
    }

    public void setThietBiDichId(Long thietBiDichId) {
        this.thietBiDichId = thietBiDichId;
    }

    public void setPortNguonId(Long portNguonId) {
        this.portNguonId = portNguonId;
    }
    
    public void setPortDichId(Long portDichId) {
        this.portDichId = portDichId;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
