package vn.vnpt.vnptit.qlsc.dto;

public class DanhSachVatTuMNVDto {
    private Number nghiepVuID;
    private Number phieuId ;
    private Number suCoId ;
    private Number kieuTBi  ;
    private Number status;
    private String dsPhieu;
    private Number thuHoiVT;
    private Number lyDoId;
    private String ghiChu;

    public Number getNghiepVuID() {
        return nghiepVuID;
    }

    public void setNghiepVuID(Number nghiepVuID) {
        this.nghiepVuID = nghiepVuID;
    }

    public Number getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Number phieuId) {
        this.phieuId = phieuId;
    }

    public Number getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Number suCoId) {
        this.suCoId = suCoId;
    }

    public Number getKieuTBi() {
        return kieuTBi;
    }

    public void setKieuTBi(Number kieuTBi) {
        this.kieuTBi = kieuTBi;
    }

    public Number getStatus() {
        return status;
    }

    public void setStatus(Number status) {
        this.status = status;
    }

    public String getDsPhieu() {
        return dsPhieu;
    }

    public void setDsPhieu(String dsPhieu) {
        this.dsPhieu = dsPhieu;
    }

    public Number getThuHoiVT() {
        return thuHoiVT;
    }

    public void setThuHoiVT(Number thuHoiVT) {
        this.thuHoiVT = thuHoiVT;
    }

    public Number getLyDoId() {
        return lyDoId;
    }

    public void setLyDoId(Number lyDoId) {
        this.lyDoId = lyDoId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
