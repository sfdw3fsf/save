package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class XoaFixScvlanDtoIn {
    private Integer phanvungId = AppRequestContext.getCurrent().getPhanVungID();
    @NotNull(message = ModelValidation.NOT_NULL)
    @Min(value = 1 , message = "Gíá trị nhỏ nhất là 1")
    private Long id;
    public Integer getPhanvungId() {
        return phanvungId;
    }

    public void setPhanvungId(Integer phanvungId) {
        this.phanvungId = phanvungId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
