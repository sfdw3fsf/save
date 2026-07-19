package vn.vnpt.vnptit.ecms.dto.idc;

public class ThroughputDto {
    private Number throughputId;
    private String ten;
    private String tenTat;
    private String moTa;
    private Number suDung;
    private String ghiChu;

    public ThroughputDto() {
    }

    public ThroughputDto(Number throughputId, String ten, String tenTat, String moTa, Number suDung, String ghiChu) {
        this.throughputId = throughputId;
        this.ten = ten;
        this.tenTat = tenTat;
        this.moTa = moTa;
        this.suDung = suDung;
        this.ghiChu = ghiChu;
    }

    public Number getThroughputId() {
        return throughputId;
    }

    public void setThroughputId(Number throughputId) {
        this.throughputId = throughputId;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Number getSuDung() {
        return suDung;
    }

    public void setSuDung(Number suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
