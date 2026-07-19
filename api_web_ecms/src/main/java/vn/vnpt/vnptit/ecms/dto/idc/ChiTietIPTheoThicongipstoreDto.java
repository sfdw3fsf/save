package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class ChiTietIPTheoThicongipstoreDto implements Serializable {
    
    private Long thicongipstoreId;
    
    public ChiTietIPTheoThicongipstoreDto() {}
    
    public ChiTietIPTheoThicongipstoreDto(Long thicongipstoreId) {
        this.thicongipstoreId = thicongipstoreId;
    }
    
    public Long getThicongipstoreId() {
        return thicongipstoreId;
    }
    
    public void setThicongipstoreId(Long thicongipstoreId) {
        this.thicongipstoreId = thicongipstoreId;
    }
}
