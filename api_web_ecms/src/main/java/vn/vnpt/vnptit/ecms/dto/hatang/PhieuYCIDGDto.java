package vn.vnpt.vnptit.ecms.dto.hatang;

public class PhieuYCIDGDto {
    private Long id;
    private Integer soThuTu;
    private String tenHaTang;
    private String congNghe;
    private Integer donViVhId;
    private Integer nguoiQtriId;
    private String loaiGoi;
    private Integer giaTriGoi;
    private Integer trangThaiThuHoi;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getSoThuTu() {
        return soThuTu;
    }

    public void setSoThuTu(Integer soThuTu) {
        this.soThuTu = soThuTu;
    }

    public String getTenHaTang() {
        return tenHaTang;
    }

    public void setTenHaTang(String tenHaTang) {
        this.tenHaTang = tenHaTang;
    }

    public String getCongNghe() {
        return congNghe;
    }

    public void setCongNghe(String congNghe) {
        this.congNghe = congNghe;
    }

    public Integer getDonViVhId() {
        return donViVhId;
    }

    public void setDonViVhId(Integer donViVhId) {
        this.donViVhId = donViVhId;
    }

    public Integer getNguoiQtriId() {
        return nguoiQtriId;
    }

    public void setNguoiQtriId(Integer nguoiQtriId) {
        this.nguoiQtriId = nguoiQtriId;
    }

    public String getLoaiGoi() {
        return loaiGoi;
    }

    public void setLoaiGoi(String loaiGoi) {
        this.loaiGoi = loaiGoi;
    }

    public Integer getGiaTriGoi() {
        return giaTriGoi;
    }

    public void setGiaTriGoi(Integer giaTriGoi) {
        this.giaTriGoi = giaTriGoi;
    }

    public Integer getTrangThaiThuHoi() {
        return trangThaiThuHoi;
    }

    public void setTrangThaiThuHoi(Integer trangThaiThuHoi) {
        this.trangThaiThuHoi = trangThaiThuHoi;
    }
}
