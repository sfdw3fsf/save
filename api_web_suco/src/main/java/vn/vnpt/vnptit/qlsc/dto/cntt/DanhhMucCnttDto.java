package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class DanhhMucCnttDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
