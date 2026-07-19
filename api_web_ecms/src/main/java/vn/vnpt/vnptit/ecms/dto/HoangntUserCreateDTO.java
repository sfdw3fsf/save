package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class HoangntUserCreateDTO {
    @NotNull(message = "Không được để trống") //annotation @NotNull để kiểm tra ràng buộc dữ liệu
    private String hoTen;
    @NotNull(message = "Không được để trống")
    private int chieuCao;
    private String soDienThoai;
    private String soThich;
    @NotNull(message = "Không được để trống") //
    private Date ngaySinh;
    private String email;
    private String diaChi;
    private int xaPhuongId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(int chieuCao) {
        this.chieuCao = chieuCao;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getXaPhuongId() {
        return xaPhuongId;
    }

    public void setXaPhuongId(int xaPhuongId) {
        this.xaPhuongId = xaPhuongId;
    }
}
