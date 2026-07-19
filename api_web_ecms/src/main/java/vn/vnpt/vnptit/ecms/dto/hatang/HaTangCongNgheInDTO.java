package vn.vnpt.vnptit.ecms.dto.hatang;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.common.Utils;
import vn.vnpt.message.ModelValidation;

public class HaTangCongNgheInDTO {

    private Number id;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private Number haTang;
    private String ghiChu;
    private Number hieuLuc;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTen() {
        return Utils.trim(this.ten);
    }

    public void setTen(String ten) {
        this.ten = ten;
    }


    public String getGhiChu() {
        return Utils.trim(this.ghiChu);
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

    public Number getHaTang() {
        return haTang;
    }

    public void setHaTang(Number haTang) {
        this.haTang = haTang;
    }

}
