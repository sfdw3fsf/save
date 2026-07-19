package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CapNhatChuKyNhanTinDtoInp {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer chuky_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer mucdo_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer chuky_dh;

    public Integer getChuky_id() {
        return chuky_id;
    }

    public void setChuky_id(Integer chuky_id) {
        this.chuky_id = chuky_id;
    }

    public Integer getMucdo_id() {
        return mucdo_id;
    }

    public void setMucdo_id(Integer mucdo_id) {
        this.mucdo_id = mucdo_id;
    }

    public Integer getChuky_dh() {
        return chuky_dh;
    }

    public void setChuky_dh(Integer chuky_dh) {
        this.chuky_dh = chuky_dh;
    }
}
