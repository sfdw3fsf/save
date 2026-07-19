package vn.vnpt.vnptit.ecms.dto.idc;

public class DanhMucLoaiIPDto {
    private Number id;
    private String ten;
    private String ghiChu;
    private Number hieuLuc;
    private String moTa;
    private String phanLoai;

    public DanhMucLoaiIPDto() {
    }

    public DanhMucLoaiIPDto(Number id, String ten, String ghiChu, Number hieuLuc, String moTa, String phanLoai) {
        this.id = id;
        this.ten = ten;
        this.ghiChu = ghiChu;
        this.hieuLuc = hieuLuc;
        this.moTa = moTa;
        this.phanLoai = phanLoai;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Number hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai = phanLoai;
    }
}
