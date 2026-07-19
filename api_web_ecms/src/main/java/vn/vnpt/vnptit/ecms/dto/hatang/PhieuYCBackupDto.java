package vn.vnpt.vnptit.ecms.dto.hatang;

public class PhieuYCBackupDto {
    private Long id;
    private Integer soThuTu;
    private String tenHaTang;
    private Long loaiHaTangId;
    private Long backupId;
    private Integer donViVhId;
    private Integer nguoiQtriId;
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

    public Long getLoaiHaTangId() {
        return loaiHaTangId;
    }

    public void setLoaiHaTangId(Long loaiHaTangId) {
        this.loaiHaTangId = loaiHaTangId;
    }

    public Long getBackupId() {
        return backupId;
    }

    public void setBackupId(Long backupId) {
        this.backupId = backupId;
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

    public Integer getTrangThaiThuHoi() {
        return trangThaiThuHoi;
    }

    public void setTrangThaiThuHoi(Integer trangThaiThuHoi) {
        this.trangThaiThuHoi = trangThaiThuHoi;
    }
}
