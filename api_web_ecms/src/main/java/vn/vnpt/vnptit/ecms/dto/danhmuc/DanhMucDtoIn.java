package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class DanhMucDtoIn {
    private Integer id;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private String ghiChu;
    private Short hieuLuc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Short getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Short hieuLuc) {
        this.hieuLuc = hieuLuc;
    }



}
