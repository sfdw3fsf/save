package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CapNhatLoaiTuDtoIn extends ThemLoaiTuDtoIn {

    @JsonProperty("loaitu_id")
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private Long loaiTuId;

    public Long getLoaiTuId() {
        return loaiTuId;
    }

    public void setLoaiTuId(Long loaiTuId) {
        this.loaiTuId = loaiTuId;
    }
}
