package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class PhieuYcTaiNguyenDto implements Serializable {
    private Long phieuycId;
    private Long donviCqId;
    private Long spdvId;
    private Long loaihtId;
    private Long cumspdvId;
    private String noidung;
    private Long loaiycId;
    private Long trangthaiphId;
    private Long nhanvienYcId;
    private Long donviYcId;
    private String ngayYc;
    private String ngayHt;
    private String fileDinhkem;
    private String mota;
    private Integer sudung;
    private String ghichu;
    public PhieuYcTaiNguyenDto() {
    }

    public Long getPhieuycId() {
        return phieuycId;
    }

    public void setPhieuycId(Long phieuycId) {
        this.phieuycId = phieuycId;
    }

    public Long getDonviCqId() {
        return donviCqId;
    }

    public void setDonviCqId(Long donviCqId) {
        this.donviCqId = donviCqId;
    }

    public Long getSpdvId() {
        return spdvId;
    }

    public void setSpdvId(Long spdvId) {
        this.spdvId = spdvId;
    }

    public Long getLoaihtId() {
        return loaihtId;
    }

    public void setLoaihtId(Long loaihtId) {
        this.loaihtId = loaihtId;
    }

    public Long getCumspdvId() {
        return cumspdvId;
    }

    public void setCumspdvId(Long cumspdvId) {
        this.cumspdvId = cumspdvId;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Long getLoaiycId() {
        return loaiycId;
    }

    public void setLoaiycId(Long loaiycId) {
        this.loaiycId = loaiycId;
    }

    public Long getTrangthaiphId() {
        return trangthaiphId;
    }

    public void setTrangthaiphId(Long trangthaiphId) {
        this.trangthaiphId = trangthaiphId;
    }

    public Long getNhanvienYcId() {
        return nhanvienYcId;
    }

    public void setNhanvienYcId(Long nhanvienYcId) {
        this.nhanvienYcId = nhanvienYcId;
    }

    public Long getDonviYcId() {
        return donviYcId;
    }

    public void setDonviYcId(Long donviYcId) {
        this.donviYcId = donviYcId;
    }

    public String getNgayYc() {
        return ngayYc;
    }

    public void setNgayYc(String ngayYc) {
        this.ngayYc = ngayYc;
    }

    public String getNgayHt() {
        return ngayHt;
    }

    public void setNgayHt(String ngayHt) {
        this.ngayHt = ngayHt;
    }

    public String getFileDinhkem() {
        return fileDinhkem;
    }

    public void setFileDinhkem(String fileDinhkem) {
        this.fileDinhkem = fileDinhkem;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

}
