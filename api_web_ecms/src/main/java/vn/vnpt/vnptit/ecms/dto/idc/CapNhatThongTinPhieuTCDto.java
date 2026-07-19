package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class CapNhatThongTinPhieuTCDto implements Serializable {
    
    private Long phieutcId;
    private Long nguoiChuyenXl;
    private String trangthai;
    private String lydoTraphieu;
    private Long isHoanThanh; // 1: Hoàn thành | 0: Không hoàn thành
    private String nguoiCn;
    
    public CapNhatThongTinPhieuTCDto() {}
    
    public CapNhatThongTinPhieuTCDto(Long phieutcId, Long nguoiChuyenXl, String trangthai, 
                                    String lydoTraphieu, Long isHoanThanh, String nguoiCn) {
        this.phieutcId = phieutcId;
        this.nguoiChuyenXl = nguoiChuyenXl;
        this.trangthai = trangthai;
        this.lydoTraphieu = lydoTraphieu;
        this.isHoanThanh = isHoanThanh;
        this.nguoiCn = nguoiCn;
    }
    
    public Long getPhieutcId() {
        return phieutcId;
    }
    
    public void setPhieutcId(Long phieutcId) {
        this.phieutcId = phieutcId;
    }
    
    public Long getNguoiChuyenXl() {
        return nguoiChuyenXl;
    }
    
    public void setNguoiChuyenXl(Long nguoiChuyenXl) {
        this.nguoiChuyenXl = nguoiChuyenXl;
    }
    
    public String gettrangthai() {
        return trangthai;
    }
    
    public void settrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
    
    public String getLydoTraphieu() {
        return lydoTraphieu;
    }
    
    public void setLydoTraphieu(String lydoTraphieu) {
        this.lydoTraphieu = lydoTraphieu;
    }
    
    public Long getIsHoanThanh() {
        return isHoanThanh;
    }
    
    public void setIsHoanThanh(Long isHoanThanh) {
        this.isHoanThanh = isHoanThanh;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}
