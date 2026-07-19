package vn.vnpt.vnptit.ecms.dto.danhmuc;

public class ThongTinModule {
    private Integer id;
    private String ten;
    private Long bangThong;
    private String ghiChu;
    private Short hieuLuc;
    private Integer kieu;

    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getBangThong() {
        return bangThong;
    }

    public void setBangThong(Long bangThong) {
        this.bangThong = bangThong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Short getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Short hieuLuc) {
        this.hieuLuc = hieuLuc;
    }
}
