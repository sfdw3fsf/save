package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class KhoaPhieuCnttDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long suco_id;

    private String ngay_th;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer nhanvien_th_id;


    private String ghichu_th;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer khoaphieu;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Number phanvung_id;

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

    public Number getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(Number phanvung_id) {
        this.phanvung_id = phanvung_id;
    }
}
