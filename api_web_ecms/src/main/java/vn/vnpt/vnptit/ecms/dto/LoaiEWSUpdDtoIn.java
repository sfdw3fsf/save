package vn.vnpt.vnptit.ecms.dto;

import org.hibernate.validator.constraints.Length;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class LoaiEWSUpdDtoIn {
    private Long loai_ewsd_id;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    @Length(message = "Trường loai_ewsd chỉ được phép nhập tối đa 100 ký tự.")
    private String loai_ewsd;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    public Long getLoai_ewsd_id() {
        return loai_ewsd_id;
    }

    public String getLoai_ewsd() {
        return loai_ewsd;
    }
}
