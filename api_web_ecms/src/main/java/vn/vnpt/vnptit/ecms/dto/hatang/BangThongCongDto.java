package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class BangThongCongDto {
    private Number id;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number bangThong;
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
    public Number getBangThong() {
        return bangThong;
    }
    public void setBangThong(Number bangThong) {
        this.bangThong = bangThong;
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
