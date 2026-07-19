package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class ThuHoiIPThietBiPortDto implements Serializable {
    
    private Long ipId;
    private Long thicongipstoreId;
    private String nguoiCn;
    private Long phieutcId;
    
    public ThuHoiIPThietBiPortDto() {}
    
    public ThuHoiIPThietBiPortDto(Long ipId, Long thicongipstoreId, String nguoiCn, Long phieutcId) {
        this.ipId = ipId;
        this.thicongipstoreId = thicongipstoreId;
        this.nguoiCn = nguoiCn;
        this.phieutcId = phieutcId;
    }
    
    public Long getIpId() {
        return ipId;
    }
    
    public void setIpId(Long ipId) {
        this.ipId = ipId;
    }
    
    public Long getThicongipstoreId() {
        return thicongipstoreId;
    }
    
    public void setThicongipstoreId(Long thicongipstoreId) {
        this.thicongipstoreId = thicongipstoreId;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Long getPhieutcId() {
        return phieutcId;
    }

    public void setPhieutcId(Long phieutcId) {
        this.phieutcId = phieutcId;
    }
}
