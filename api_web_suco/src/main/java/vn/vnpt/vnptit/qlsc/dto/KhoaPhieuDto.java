package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;
import vn.vnpt.validator.StringDateTimeConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class KhoaPhieuDto {

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long suco_id;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @StringDateTimeConstraint(message = ModelValidation.YYYYMMDDHHMMSS_INVALID)
    private String ngay_th;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer nhanvien_th_id;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ds_nguyennhan;

    private String ghichu_th;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer khoaphieu;

    public Long getSuco_id() {
        return suco_id;
    }

    public void setSuco_id(Long suco_id) {
        this.suco_id = suco_id;
    }

    public String getNgay_th() {
        return ngay_th;
    }

    public void setNgay_th(String ngay_th) {
        this.ngay_th = ngay_th;
    }

    public Integer getNhanvien_th_id() {
        return nhanvien_th_id;
    }

    public void setNhanvien_th_id(Integer nhanvien_th_id) {
        this.nhanvien_th_id = nhanvien_th_id;
    }

    public String getDs_nguyennhan() {
        return ds_nguyennhan;
    }

    public void setDs_nguyennhan(String ds_nguyennhan) {
        this.ds_nguyennhan = ds_nguyennhan;
    }

    public String getGhichu_th() {
        return ghichu_th;
    }

    public void setGhichu_th(String ghichu_th) {
        this.ghichu_th = ghichu_th;
    }

    public Integer getKhoaphieu() {
        return khoaphieu;
    }

    public void setKhoaphieu(Integer khoaphieu) {
        this.khoaphieu = khoaphieu;
    }
}
