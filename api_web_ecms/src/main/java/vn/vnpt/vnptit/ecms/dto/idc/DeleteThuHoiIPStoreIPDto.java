package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class DeleteThuHoiIPStoreIPDto implements Serializable {
    
    private Long thicongipstoreId;
    private Long thicongipId;
    private String type; // 1: dải ip (THICONG_IPSTORE) | 2: ip (THICONG_IP)
    
    public DeleteThuHoiIPStoreIPDto() {}
    
    public DeleteThuHoiIPStoreIPDto(Long thicongipstoreId, Long thicongipId, String type) {
        this.thicongipstoreId = thicongipstoreId;
        this.thicongipId = thicongipId;
        this.type = type;
    }
    
    public Long getThicongipstoreId() {
        return thicongipstoreId;
    }
    
    public void setThicongipstoreId(Long thicongipstoreId) {
        this.thicongipstoreId = thicongipstoreId;
    }
    
    public Long getThicongipId() {
        return thicongipId;
    }
    
    public void setThicongipId(Long thicongipId) {
        this.thicongipId = thicongipId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
