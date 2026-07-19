package vn.vnpt.vnptit.ecms.dto.danhmuc;
import org.hibernate.validator.constraints.Range;

public class DonGiaHaTangDtoIn {
    private Long  id;
    @Range(min = 1, message = "Công nghệ sử dụng ID phải lớn hơn 0")
    private Integer congNgheId;
    @Range(min = 1, message = "Phân loại Id phải lớn hơn 0")
    private Integer haTangPhanLoaiId;
    @Range(min = 1, message = "Phân loại hạ tầng tính toán id phải lớn hơn 0")
    private Integer haTangPhanLoaiTtId;
    private String batDau;
    private String ketThuc;
    private String soVanBan;
    private String ngayVanBan;

    @Range(min = 0, max=999999,  message = "Đơn giá CPU từ 0 đến 1 triệu")
    private Double donGiaCPU;
    @Range(min = 0, max=999999, message = "Đơn giá RAM từ 0 đến 1 triệu")
    private Double donGiaRAM;
    @Range(min = 0, max=999999, message = "Đơn giá Disk từ 0 đến 1 triệu")
    private Double donGiaDISK;
    @Range(min = 0, max=100, message = "% Tỷ lệ giảm phải từ 0 đến 100")
    private Double tyLeGiam;
    private String ghiChu;
    private Short hieuLuc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCongNgheId() {
        return congNgheId;
    }

    public void setCongNgheId(Integer congNgheId) {
        this.congNgheId = congNgheId;
    }

    public Integer getHaTangPhanLoaiId() {
        return haTangPhanLoaiId;
    }

    public void setHaTangPhanLoaiId(Integer haTangPhanLoaiId) {
        this.haTangPhanLoaiId = haTangPhanLoaiId;
    }

    public Integer getHaTangPhanLoaiTtId() {
        return haTangPhanLoaiTtId;
    }

    public void setHaTangPhanLoaiTtId(Integer haTangPhanLoaiTtId) {
        this.haTangPhanLoaiTtId = haTangPhanLoaiTtId;
    }

    public String getBatDau() {
        return batDau;
    }

    public void setBatDau(String batDau) {
        this.batDau = batDau;
    }

    public String getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(String ketThuc) {
        this.ketThuc = ketThuc;
    }

    public String getSoVanBan() {
        return soVanBan;
    }

    public void setSoVanBan(String soVanBan) {
        this.soVanBan = soVanBan;
    }

    public String getNgayVanBan() {
        return ngayVanBan;
    }

    public void setNgayVanBan(String ngayVanBan) {
        this.ngayVanBan = ngayVanBan;
    }

    public Double getDonGiaCPU() {
        return donGiaCPU;
    }

    public void setDonGiaCPU(Double donGiaCPU) {
        this.donGiaCPU = donGiaCPU;
    }

    public Double getDonGiaRAM() {
        return donGiaRAM;
    }

    public void setDonGiaRAM(Double donGiaRAM) {
        this.donGiaRAM = donGiaRAM;
    }

    public Double getDonGiaDISK() {
        return donGiaDISK;
    }

    public void setDonGiaDISK(Double donGiaDISK) {
        this.donGiaDISK = donGiaDISK;
    }

    public Double getTyLeGiam() {
        return tyLeGiam;
    }

    public void setTyLeGiam(Double tyLeGiam) {
        this.tyLeGiam = tyLeGiam;
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
