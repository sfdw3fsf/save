package vn.vnpt.vnptit.qlsc.dto.cntt;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class GhiChuPXLCNTTInput {
    @JsonProperty("phanvung_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer phanvung_id;
    public Integer getphanvung_id() {
        return phanvung_id;
    }

    @JsonProperty("phieu_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer phieu_id;
    public Integer getphieu_id() {
        return phieu_id;
    }

    @JsonProperty("ghi_chu")
    private String ghi_chu;
    public String getghi_chu() {
        return ghi_chu;
    }
}
