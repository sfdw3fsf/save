package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import vn.vnpt.common.Utils;

public class HaTangMangDtoIn {
    private Long id;
    @NotNull(message = "Tên hạ tầng mạng không thể để trống")
    private String ten;
    private String maHaTang;
    private String avatar; // image base64
    @Range(min = 1, message = "IDC Id phải lớn hơn 0")
    private Integer idcId;

    @Range(min = 1, message = "Công nghệ id phải lớn hơn 0")
    private Long congNgheId;
    private String ghiChu;
    private Short hieuLuc;
    private Long nguoiQlyId;
    private Long phongBanQlyId;
    private Long donViQlyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return Utils.trim(this.ten);
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMaHaTang() {
        return Utils.trim(this.maHaTang);
    }

    public void setMaHaTang(String maHaTang) {
        this.maHaTang = maHaTang;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIdcId() {
        return idcId;
    }

    public void setIdcId(Integer idcId) {
        this.idcId = idcId;
    }

    public Long getCongNgheId() {
        return congNgheId;
    }

    public void setCongNgheId(Long congNgheId) {
        this.congNgheId = congNgheId;
    }

    public String getGhiChu() {
        return Utils.trim(this.ghiChu);
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

    public Long getNguoiQlyId() {
        return nguoiQlyId;
    }

    public void setNguoiQlyId(Long nguoiQlyId) {
        this.nguoiQlyId = nguoiQlyId;
    }

    public Long getPhongBanQlyId() {
        return phongBanQlyId;
    }

    public void setPhongBanQlyId(Long phongBanQlyId) {
        this.phongBanQlyId = phongBanQlyId;
    }

    public Long getDonViQlyId() {
        return donViQlyId;
    }

    public void setDonViQlyId(Long donViQlyId) {
        this.donViQlyId = donViQlyId;
    }
}
