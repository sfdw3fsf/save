package vn.vnpt.vnptit.ecms.dto.idc;

public class DanhMucLoaiVLKNDto {
    private Number id;
    private String ten;
    private Number doiTuong;
    private Number suDung;
    private String ghiChu;

    public DanhMucLoaiVLKNDto() {
    }

    public DanhMucLoaiVLKNDto(Number id, String ten, Number doiTuong, Number suDung, String ghiChu) {
        this.id = id;
        this.ten = ten;
        this.doiTuong = doiTuong;
        this.suDung = suDung;
        this.ghiChu = ghiChu;
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

    public Number getDoiTuong() {
        return doiTuong;
    }

    public void setDoiTuong(Number doiTuong) {
        this.doiTuong = doiTuong;
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
