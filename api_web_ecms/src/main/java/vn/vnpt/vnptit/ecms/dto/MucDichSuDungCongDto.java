package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class MucDichSuDungCongDto {
    private Number id;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private String ghiChu;
    private Number hieuLuc;

    public Number getId() {
        return id;
    }
    public void setId(Number id) {
        this.id = id;
    }
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }
    public String getGhiChu() {
        return ghiChu;
    }
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    public Number getHieuLuc() {
        return hieuLuc;
    }
    public void setHieuLuc(Number hieuLuc) {
        this.hieuLuc = hieuLuc;
    }
}
