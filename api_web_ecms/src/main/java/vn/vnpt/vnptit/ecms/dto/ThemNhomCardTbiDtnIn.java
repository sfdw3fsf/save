package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class ThemNhomCardTbiDtnIn {
    private Integer phanvungId = AppRequestContext.getCurrent().getPhanVungID();
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long nhomcardId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaitbiId;

    public Integer getPhanvungId() {
        return phanvungId;
    }

    public void setPhanvungId(Integer phanvungId) {
        this.phanvungId = phanvungId;
    }

    public Long getNhomcardId() {
        return nhomcardId;
    }

    public void setNhomcardId(Long nhomcardId) {
        this.nhomcardId = nhomcardId;
    }

    public Long getLoaitbiId() {
        return loaitbiId;
    }

    public void setLoaitbiId(Long loaitbiId) {
        this.loaitbiId = loaitbiId;
    }
}
