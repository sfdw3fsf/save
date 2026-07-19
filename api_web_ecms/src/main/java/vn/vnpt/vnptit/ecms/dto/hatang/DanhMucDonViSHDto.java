package vn.vnpt.vnptit.ecms.dto.hatang;

public class DanhMucDonViSHDto {
    private Number donViSH_id;
    private String ten;
    private String tenTat;
    private String moTa;
    private Number suDung;
    private String ghiChu;

    public Number getDonViSH_id() {
        return donViSH_id;
    }

    public void setDonViSH_id(Number donViSH_id) {
        this.donViSH_id = donViSH_id;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Number getSuDung() {
        return suDung;
    }

    public void setSuDung(Number suDung) {
        this.suDung = suDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
