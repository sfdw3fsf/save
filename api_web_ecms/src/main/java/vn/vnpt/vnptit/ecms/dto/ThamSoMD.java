package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ThamSoMD {
    @NotNull(message = "kieu_id không được để trống")
    private Integer kieu_id;
    @NotEmpty(message = "ma_ts không được để trống")
    private String ma_ts;

    public int getKieu_id() {
        return kieu_id;
    }

    public void setKieu_id(int kieu_id) {
        this.kieu_id = kieu_id;
    }

    public String getMa_ts() {
        return ma_ts;
    }

    public void setMa_ts(String ma_ts) {
        this.ma_ts = ma_ts;
    }
}
