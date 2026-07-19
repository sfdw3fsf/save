package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DevTestPhLanDto {

    @NotNull(message = ModelValidation.NOT_NULL)
    private String hoten;
    @NotNull(message = ModelValidation.NOT_NULL)
    @Min(100) @Max(300)
    private int chieucao;

    private String email;
    private String sodienthoai;

    private String sothich;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Date ngaysinh;

    private String diachi;
    private int xaphuong_id;

    public int getXaphuong_id() {
        return xaphuong_id;
    }

    public void setXaphuong_id(int xaphuong_id) {
        this.xaphuong_id = xaphuong_id;
    }

    //public DevTestPhLanDto() {}

    public String getHoten() {
        return hoten;
    }

    public int getChieucao() {
        return chieucao;
    }

    public String getEmail() {
        return email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public String getSothich() {
        return sothich;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public void setChieucao(int chieucao) {
        this.chieucao = chieucao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setSothich(String sothich) {
        this.sothich = sothich;
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
}
