package vn.vnpt.vnptit.ecms.dto.hatang;
import org.hibernate.validator.constraints.Range;
import vn.vnpt.common.Utils;

import javax.validation.constraints.NotNull;

public class TaiNguyenNFSDtoIn {
    private Long id;
    @NotNull(message = "Tên NFS không thể để trống")
    private String ten;
    private String link_backup;
    private Long phieuyeucau_id;
    @Range(min = 1, max = 2, message = "Loại hạ tầng phải 1 hoặc 2")
    private Number loaihatang_luutru;
    private Number hatang_luutru_id;
    @Range(min = 1, max = 2, message = "Loại lưu trữ phải 1 hoặc 2")
    private Number loailuutru;
    private String nangluc;
    private String ghiChu;
    private Short hieuLuc;
    @Range(min = 1, message = "Dung lượng gb phải lớn hơn 0")
    private Long dungLuong;
    @Range(min = 1, message = "Người quản lý phải lớn hơn 0")
    private Long nguoiQlyId;
    @Range(min = 1, message = "Chủ quản dịch vụ phải lớn hơn 0")
    private Long phongBanQlyId;

    @Range(min = 1, message = "Đơn vị quản lý phải lớn hơn 0")
    private Long donViQlyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return Utils.trim(ten);
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLink_backup() {
        return link_backup;
    }

    public void setLink_backup(String link_backup) {
        this.link_backup = link_backup;
    }

    public Long getPhieuyeucau_id() {
        return phieuyeucau_id;
    }

    public void setPhieuyeucau_id(Long phieuyeucau_id) {
        this.phieuyeucau_id = phieuyeucau_id;
    }

    public Number getLoaihatang_luutru() {
        return loaihatang_luutru;
    }

    public void setLoaihatang_luutru(byte loaihatang_luutru) {
        this.loaihatang_luutru = loaihatang_luutru;
    }

    public Number getHatang_luutru_id() {
        return hatang_luutru_id;
    }

    public void setHatang_luutru_id(Number hatang_luutru_id) {
        this.hatang_luutru_id = hatang_luutru_id;
    }

    public Number getLoailuutru() {
        return loailuutru;
    }

    public void setLoailuutru(byte loailuutru) {
        this.loailuutru = loailuutru;
    }

    public String getNangluc() {
        return nangluc;
    }

    public void setNangluc(String nangluc) {
        this.nangluc = nangluc;
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

    public Long getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(Long dungLuong) {
        this.dungLuong = dungLuong;
    }
}
