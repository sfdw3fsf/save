package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;
import java.util.Date;

public class CapPhatIPDto implements Serializable {
    
    private Long thicongipstoreId;
    private Long phieutcId;
    private Long ipstoreId;
    private Long nhanvienId;
    private Date ngayThucHien;
    private String noiDung;
    private Long trangThaiId;
    private String ghiChu;
    private String moTa;
    private String nguoiCn;
    private Long ipID;
    private Long thietbiMangId;
    private String loaiCv;
    
    public CapPhatIPDto() {}
    
    public CapPhatIPDto(Long thicongipstoreId, Long phieutcId, Long ipstoreId, Long nhanvienId, 
                       Date ngayThucHien, String noiDung, Long trangThaiId, String ghiChu, 
                       String moTa, String nguoiCn, Long ipID, Long thietbiMangId) {
        this.thicongipstoreId = thicongipstoreId;
        this.phieutcId = phieutcId;
        this.ipstoreId = ipstoreId;
        this.nhanvienId = nhanvienId;
        this.ngayThucHien = ngayThucHien;
        this.noiDung = noiDung;
        this.trangThaiId = trangThaiId;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
        this.nguoiCn = nguoiCn;
        this.ipID = ipID;
        this.thietbiMangId = thietbiMangId;
        this.loaiCv = loaiCv;
    }
    
    public Long getThicongipstoreId() {
        return thicongipstoreId;
    }
    
    public void setThicongipstoreId(Long thicongipstoreId) {
        this.thicongipstoreId = thicongipstoreId;
    }
    
    public Long getPhieutcId() {
        return phieutcId;
    }
    
    public void setPhieutcId(Long phieutcId) {
        this.phieutcId = phieutcId;
    }
    
    public Long getIpstoreId() {
        return ipstoreId;
    }
    
    public void setIpstoreId(Long ipstoreId) {
        this.ipstoreId = ipstoreId;
    }
    
    public Long getNhanvienId() {
        return nhanvienId;
    }
    
    public void setNhanvienId(Long nhanvienId) {
        this.nhanvienId = nhanvienId;
    }
    
    public Date getNgayThucHien() {
        return ngayThucHien;
    }
    
    public void setNgayThucHien(Date ngayThucHien) {
        this.ngayThucHien = ngayThucHien;
    }
    
    public String getNoiDung() {
        return noiDung;
    }
    
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    
    public Long getTrangThaiId() {
        return trangThaiId;
    }
    
    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getMoTa() {
        return moTa;
    }
    
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    public String getNguoiCn() {
        return nguoiCn;
    }
    
    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Long getIpID() {
        return ipID;
    }
    
    public void setIpID(Long ipID) {
        this.ipID = ipID;
    }

    public Long getThietbiMangId() {
        return thietbiMangId;
    }
    
    public void setThietbiMangId(Long thietbiMangId) {
        this.thietbiMangId = thietbiMangId;
    }

    public String getLoaiCv() {
        return loaiCv;
    }

    public void setLoaiCv(String loaiCv) {
        this.loaiCv = loaiCv;
    }
}
