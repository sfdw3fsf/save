package vn.vnpt.vnptit.ecms.dto.idc;

public class DanhMucLoaiHaTangDto {
    private Number id;
    private String ten;
    private String tenTat;
    private Number suDung;
    private String ghiChu;

    public DanhMucLoaiHaTangDto() {
    }

    public DanhMucLoaiHaTangDto(String ghiChu, Number id, Number suDung, String ten, String tenTat) {
        this.ghiChu = ghiChu;
        this.id = id;
        this.suDung = suDung;
        this.ten = ten;
        this.tenTat = tenTat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getSuDung() {
        return suDung;
    }

    public void setSuDung(Number suDung) {
        this.suDung = suDung;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }
}
