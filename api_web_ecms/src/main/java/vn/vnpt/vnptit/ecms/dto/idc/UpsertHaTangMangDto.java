package vn.vnpt.vnptit.ecms.dto.idc;

public class UpsertHaTangMangDto {
    private Long id;
    private String ten;
    private String maHaTangMang;
    private Long idcId;
    private Long congNgheId;
    private Long trangThaiCCDVId;
    private Integer hieuLuc;
    private Long donViQuanLyId;
    private Long phongQuanLyId;
    private String ghiChu;
    private String hinhAnh;

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Long getTrangThaiCCDVId() {
        return trangThaiCCDVId;
    }

    public void setTrangThaiCCDVId(Long trangThaiCCDVId) {
        this.trangThaiCCDVId = trangThaiCCDVId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhongQuanLyId() {
        return phongQuanLyId;
    }

    public void setPhongQuanLyId(Long phongQuanLyId) {
        this.phongQuanLyId = phongQuanLyId;
    }

    public Long getDonViQuanLyId() {
        return donViQuanLyId;
    }

    public void setDonViQuanLyId(Long donViQuanLyId) {
        this.donViQuanLyId = donViQuanLyId;
    }

    public Integer getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getCongNgheId() {
        return congNgheId;
    }

    public void setCongNgheId(Long congNgheId) {
        this.congNgheId = congNgheId;
    }

    public String getMaHaTangMang() {
        return maHaTangMang;
    }

    public void setMaHaTangMang(String maHaTangMang) {
        this.maHaTangMang = maHaTangMang;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}