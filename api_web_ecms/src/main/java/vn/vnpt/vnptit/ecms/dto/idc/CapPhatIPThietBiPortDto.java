package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class CapPhatIPThietBiPortDto implements Serializable {
    
    private Long ipId;
    private Long thietbiId;
    private Long portId;
    private Long trangThaiCapPhat;
    private Long phieutcId;
    private Long thicongipstoreId;
    
    public CapPhatIPThietBiPortDto() {}
    
    public CapPhatIPThietBiPortDto(Long ipId, Long thietbiId, Long portId, Long trangThaiCapPhat, Long phieutcId, Long thicongipstoreId) {
        this.ipId = ipId;
        this.thietbiId = thietbiId;
        this.portId = portId;
        this.trangThaiCapPhat = trangThaiCapPhat;
        this.phieutcId = phieutcId;
        this.thicongipstoreId = thicongipstoreId;
    }
    
    public Long getIpId() {
        return ipId;
    }
    
    public void setIpId(Long ipId) {
        this.ipId = ipId;
    }
    
    public Long getThietbiId() {
        return thietbiId;
    }
    
    public void setThietbiId(Long thietbiId) {
        this.thietbiId = thietbiId;
    }
    
    public Long getPortId() {
        return portId;
    }
    
    public void setPortId(Long portId) {
        this.portId = portId;
    }
    
    public Long getTrangThaiCapPhat() {
        return trangThaiCapPhat;
    }
    
    public void setTrangThaiCapPhat(Long trangThaiCapPhat) {
        this.trangThaiCapPhat = trangThaiCapPhat;
    }

    public Long getPhieutcId() {
        return phieutcId;
    }

    public void setPhieutcId(Long phieutcId) {
        this.phieutcId = phieutcId;
    }

    public Long getThicongipstoreId() {
        return thicongipstoreId;
    }

    public void setThicongipstoreId(Long thicongipstoreId) {
        this.thicongipstoreId = thicongipstoreId;
    }
}
