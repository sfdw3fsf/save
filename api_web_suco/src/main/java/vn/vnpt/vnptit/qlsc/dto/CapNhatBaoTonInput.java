package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CapNhatBaoTonInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer phieu_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nnton_id;

    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String ngay_bt;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nhanvien_bt_id;

    private String ghichu;

    public Integer getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Integer phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Integer getNnton_id() {
        return nnton_id;
    }

    public void setNnton_id(Integer nnton_id) {
        this.nnton_id = nnton_id;
    }

    public String getNgay_bt() {
        return ngay_bt;
    }

    public void setNgay_bt(String ngay_bt) {
        this.ngay_bt = ngay_bt;
    }

    public Integer getNhanvien_bt_id() {
        return nhanvien_bt_id;
    }

    public void setNhanvien_bt_id(Integer nhanvien_bt_id) {
        this.nhanvien_bt_id = nhanvien_bt_id;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
