package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class DmThuTucHaTangInput {

    @JsonProperty("ma_quytrinh")
    @NotNull(message = ModelValidation.NOT_NULL)
    private String ma_quytrinh;

    @JsonProperty("loaicanhbao")
    @NotNull(message = ModelValidation.NOT_NULL)
    private String loaicanhbao;

    @JsonProperty("ma_diemcham")
    private String ma_diemcham;

    public String getMa_quytrinh() {
        return ma_quytrinh;
    }

    public String getLoaicanhbao() {
        return loaicanhbao;
    }

    public String getMa_diemcham() {
        return ma_diemcham;
    }
}
