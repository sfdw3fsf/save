package vn.vnpt.vnptit.ecms.dto.idc;

public class UpsertCongNgheDto {
    private Long id;
    private String ten;
    private String tenTat;
    private Long loaiHaTangId;
    private Long loaiHaTang;
    private String moTa;
    private String ghiChu;
    private Integer hieuLuc;

    public Integer getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getLoaiHaTangId() {
        return loaiHaTangId;
    }

    public void setLoaiHaTangId(Long loaiHaTangId) {
        this.loaiHaTangId = loaiHaTangId;
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