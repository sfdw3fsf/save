package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CayThietBiInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer loaitbi_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer thietbi_id;

    public Integer getLoaitbi_id() {
        return loaitbi_id;
    }

    public void setLoaitbi_id(Integer loaitbi_id) {
        this.loaitbi_id = loaitbi_id;
    }

    public Integer getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Integer thietbi_id) {
        this.thietbi_id = thietbi_id;
    }
}
