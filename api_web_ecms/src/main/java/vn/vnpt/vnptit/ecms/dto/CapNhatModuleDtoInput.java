package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class CapNhatModuleDtoInput extends ModuleDtoInput{

    @NotNull(message = ModelValidation.NOT_NULL)
    @Max(999999999)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loai_module_id")
    private Long loai_module_id;

    public Long getLoai_module_id() {
        return loai_module_id;
    }

    public void setLoai_module_id(Long loai_module_id) {
        this.loai_module_id = loai_module_id;
    }
}
