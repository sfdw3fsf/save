package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class LtbaoTestDto {
    @NotNull(message = "id may not be null")
    private Long id;

    @NotNull(message = "hoTen may not be null")
    private String hoTen;

    @NotNull(message = "chieuCao may not be null")
    private Long chieuCao;

    private String email;

    private String soDienThoai;

    private String soThich;

    @NotNull(message = "ngaySinh may not be null")
    private Date ngaySinh;

    @NotNull(message = "thoiGianCapNhat may not be null")
    private Date thoiGianCapNhat;

    private String diaChi;

    private Long xaPhuongId;

    public LtbaoTestDto(Long id, String hoTen, Long chieuCao, String email, String soDienThoai, String soThich, Date ngaySinh, Date thoiGianCapNhat, String diaChi, Long xaPhuongId) {
        this.id = id;
        this.hoTen = hoTen;
        this.chieuCao = chieuCao;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.soThich = soThich;
        this.ngaySinh = ngaySinh;
        this.thoiGianCapNhat = thoiGianCapNhat;
        this.diaChi = diaChi;
        this.xaPhuongId = xaPhuongId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Long getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Long chieuCao) {
        this.chieuCao = chieuCao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getSoThich() {
        return soThich;
    }

    public void setSoThich(String soThich) {
        this.soThich = soThich;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Date getThoiGianCapNhat() {
        return thoiGianCapNhat;
    }

    public void setThoiGianCapNhat(Date thoiGianCapNhat) {
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Long getXaPhuongId() {
        return xaPhuongId;
    }

    public void setXaPhuongId(Long xaPhuongId) {
        this.xaPhuongId = xaPhuongId;
    }
}
