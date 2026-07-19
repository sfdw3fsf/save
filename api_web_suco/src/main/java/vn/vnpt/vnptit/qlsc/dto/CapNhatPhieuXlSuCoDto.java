package vn.vnpt.vnptit.qlsc.dto;

public class CapNhatPhieuXlSuCoDto {

    private Long phieu_id;

    private Integer donvi_nhan_id;

    private String nd_giao;

    private Integer khan;

    private Float chitieu_tg;

    private Number nhanvien_nhan_id;

    private Number phanVungId;

    public Long getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Long phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Integer getDonvi_nhan_id() {
        return donvi_nhan_id;
    }

    public void setDonvi_nhan_id(Integer donvi_nhan_id) {
        this.donvi_nhan_id = donvi_nhan_id;
    }

    public String getNd_giao() {
        return nd_giao;
    }

    public void setNd_giao(String nd_giao) {
        this.nd_giao = nd_giao;
    }

    public Integer getKhan() {
        return khan;
    }

    public void setKhan(Integer khan) {
        this.khan = khan;
    }

    public Float getChitieu_tg() {
        return chitieu_tg;
    }

    public void setChitieu_tg(Float chitieu_tg) {
        this.chitieu_tg = chitieu_tg;
    }

    public Number getNhanvien_nhan_id() {
        return nhanvien_nhan_id;
    }

    public void setNhanvien_nhan_id(Number nhanvien_nhan_id) {
        this.nhanvien_nhan_id = nhanvien_nhan_id;
    }

    public Number getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(Number phanVungId) {
        this.phanVungId = phanVungId;
    }
}
