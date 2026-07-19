package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TracuuMDFAccDtoIn {
    public Long getPhanvung_id() {
        return phanvung_id;
    }
    @NotNull(message = ModelValidation.NOT_NULL)
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    public String getMa_tb() {
        return ma_tb;
    }

    public Long getDonvi_dl_id() {
        return donvi_dl_id;
    }



    public void setPhanvung_id(Long phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public void setMa_tb(String ma_tb) {
        this.ma_tb = ma_tb;
    }

    public void setDonvi_dl_id(Long donvi_dl_id) {
        this.donvi_dl_id = donvi_dl_id;
    }
    private Long phanvung_id;
    private String ma_tb;
    private Long donvi_dl_id;
}
