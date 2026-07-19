package vn.vnpt.vnptit.ecms.dto.idc;

public class DanhMucNhiemVuDto {
    private Long nhiemVuId;
    private String ten;
    private String tenTat;
    private Integer suDung;
    private String moTa;
    private String ghiChu;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getNhiemVuId() {
        return nhiemVuId;
    }

    public void setNhiemVuId(Long nhiemVuId) {
        this.nhiemVuId = nhiemVuId;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getSuDung() {
        return suDung;
    }

    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}


