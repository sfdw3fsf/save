package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ChiTietThuTucXuLyInput {

    @JsonProperty("p_id_giaophieu_thutuc")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long id_giaophieu_thutuc;

    public Long getId_giaophieu_thutuc() {
        return id_giaophieu_thutuc;
    }
}
