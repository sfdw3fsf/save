package vn.vnpt.vnptit.qlsc.dto;

public class GiaoPhieuXuLyNetDto {

    private Integer type;
    private Long phieu_cha_id;
    private String nd_giao;
    private Integer donvi_nhan_id;
    private String ma_quytrinh;
    private String ma_buoc;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPhieu_cha_id() {
        return phieu_cha_id;
    }

    public void setPhieu_cha_id(Long phieu_cha_id) {
        this.phieu_cha_id = phieu_cha_id;
    }

    public String getNd_giao() {
        return nd_giao;
    }

    public void setNd_giao(String nd_giao) {
        this.nd_giao = nd_giao;
    }

    public Integer getDonvi_nhan_id() {
        return donvi_nhan_id;
    }

    public void setDonvi_nhan_id(Integer donvi_nhan_id) {
        this.donvi_nhan_id = donvi_nhan_id;
    }

    public String getMa_quytrinh() {
        return ma_quytrinh;
    }

    public void setMa_quytrinh(String ma_quytrinh) {
        this.ma_quytrinh = ma_quytrinh;
    }

    public String getMa_buoc() {
        return ma_buoc;
    }

    public void setMa_buoc(String ma_buoc) {
        this.ma_buoc = ma_buoc;
    }
}
