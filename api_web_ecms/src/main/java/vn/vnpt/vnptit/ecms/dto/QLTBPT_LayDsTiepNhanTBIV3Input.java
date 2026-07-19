package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class QLTBPT_LayDsTiepNhanTBIV3Input {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer ttvh_id = 0;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer thietbi_id = 0;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer loaivh_id = 0;

    public Integer getTtvh_id() {
        return ttvh_id;
    }

    public void setTtvh_id(Integer ttvh_id) {
        this.ttvh_id = ttvh_id;
    }

    public Integer getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Integer thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public Integer getLoaivh_id() {
        return loaivh_id;
    }

    public void setLoaivh_id(Integer loaivh_id) {
        this.loaivh_id = loaivh_id;
    }
}
