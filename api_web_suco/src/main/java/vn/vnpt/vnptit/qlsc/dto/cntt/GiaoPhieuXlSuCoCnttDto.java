package vn.vnpt.vnptit.qlsc.dto.cntt;

public class GiaoPhieuXlSuCoCnttDto {
    private Long phieu_cha_id;
    private Integer huonggiao_id;
    private String nd_giao;
    private Long suco_id;
    private String donvi_nhan_id;
    private Long nhanvien_nhan_id;
    private String suco_ptm;
    private Integer khan;
    private Number phanVungId;
//    private Float chitieu_tg;

    public Long getPhieu_cha_id() {
        return phieu_cha_id;
    }

    public void setPhieu_cha_id(Long phieu_cha_id) {
        this.phieu_cha_id = phieu_cha_id;
    }

    public Integer getHuonggiao_id() {
        return huonggiao_id;
    }

    public void setHuonggiao_id(Integer huonggiao_id) {
        this.huonggiao_id = huonggiao_id;
    }

    public String getNd_giao() {
        return nd_giao;
    }

    public void setNd_giao(String nd_giao) {
        this.nd_giao = nd_giao;
    }

    public Long getSuco_id() {
        return suco_id;
    }

    public void setSuco_id(Long suco_id) {
        this.suco_id = suco_id;
    }

    public String getDonvi_nhan_id() {
        return donvi_nhan_id;
    }

    public void setDonvi_nhan_id(String donvi_nhan_id) {
        this.donvi_nhan_id = donvi_nhan_id;
    }

    public Long getNhanvien_nhan_id() {
        return nhanvien_nhan_id;
    }

    public void setNhanvien_nhan_id(Long nhanvien_nhan_id) {
        this.nhanvien_nhan_id = nhanvien_nhan_id;
    }

    public String getSuco_ptm() {
        return suco_ptm;
    }

    public void setSuco_ptm(String suco_ptm) {
        this.suco_ptm = suco_ptm;
    }

    public Integer getKhan() {
        return khan;
    }

    public void setKhan(Integer khan) {
        this.khan = khan;
    }

//    public Float getChitieu_tg() {
//        return chitieu_tg;
//    }
//
//    public void setChitieu_tg(Float chitieu_tg) {
//        this.chitieu_tg = chitieu_tg;
//    }


    public Number getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(Number phanVungId) {
        this.phanVungId = phanVungId;
    }
}
