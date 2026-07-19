package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;
import java.util.Date;

public class TuanDdDTO {
private  String hoten;
@NotNull(message =  ModelValidation.NOT_NULL)
@PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
private  int chieucao;
private  String sodienthoai;
private  String sothich;
private Date ngaysinh;
private String email;
private String diachi;
private  int xaphuongid;

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getChieucao() {
        return chieucao;
    }

    public void setChieucao(int chieucao) {
        this.chieucao = chieucao;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getXaphuongid() {
        return xaphuongid;
    }

    public void setXaphuongid(int xaphuongid) {
        this.xaphuongid = xaphuongid;
    }
}
