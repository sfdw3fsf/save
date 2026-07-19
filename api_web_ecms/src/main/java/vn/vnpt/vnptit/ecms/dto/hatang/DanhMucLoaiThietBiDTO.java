package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class DanhMucLoaiThietBiDTO {
    private Number id = 0;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String phanLoai;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ma;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private String ghiChu;
    private Number hieuLuc;

    public Number getId() {
        return id;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getPhanLoai() {
        return phanLoai;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getMa() {
        return ma;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getTen() {
        return ten;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }
}
