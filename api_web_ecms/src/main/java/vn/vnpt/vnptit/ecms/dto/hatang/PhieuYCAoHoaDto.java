package vn.vnpt.vnptit.ecms.dto.hatang;

public class PhieuYCAoHoaDto {
    private Long id;
    private Integer soThuTu;
    private String cumDichVu;
    private Integer mayAoId;
    private Integer donViVhId;
    private Integer nguoiQtriId;
    private String ipAddressId;
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

    public String getCumDichVu() {
        return cumDichVu;
    }

    public void setCumDichVu(String cumDichVu) {
        this.cumDichVu = cumDichVu;
    }

    public Integer getMayAoId() {
        return mayAoId;
    }

    public void setMayAoId(Integer mayAoId) {
        this.mayAoId = mayAoId;
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

    public String getIpAddressId() {
        return ipAddressId;
    }

    public void setIpAddressId(String ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    public Integer getTrangThaiThuHoi() {
        return trangThaiThuHoi;
    }

    public void setTrangThaiThuHoi(Integer trangThaiThuHoi) {
        this.trangThaiThuHoi = trangThaiThuHoi;
    }
}
