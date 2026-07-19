package vn.vnpt.vnptit.ecms.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import vn.vnpt.message.ModelValidation;

public class DuanDemoDto  {
    @NotNull(message = ModelValidation.NOT_NULL)
    private String hoten;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Double chieucao;

    private String email;

    @Pattern(regexp = "\\d+", message = "Số điện thoại chỉ chứa các ký tự số")
    private String sodienthoai;

    private String sothich;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Date ngaysinh;

    private String diachi;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long xaphuongId;

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Double getChieucao() {
        return chieucao;
    }

    public void setChieucao(Double chieucao) {
        this.chieucao = chieucao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getSothich() {
        return sothich;
    }

    public void setSothich(String sothich) {
        this.sothich = sothich;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Long getXaphuongId() {
        return xaphuongId;
    }

    public void setXaphuongId(Long xaphuongId) {
        this.xaphuongId = xaphuongId;
    }
}
