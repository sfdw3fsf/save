package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CapNhatFixScvlanDtoIn {
    private Integer phanvungId = AppRequestContext.getCurrent().getPhanVungID();
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long portvlId;
    private Integer svlan;
    private Integer cvlanBd;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long quyhoachId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhanvungId() {
        return phanvungId;
    }

    public void setPhanvungId(Integer phanvungId) {
        this.phanvungId = phanvungId;
    }

    public Long getPortvlId() {
        return portvlId;
    }

    public void setPortvlId(Long portvlId) {
        this.portvlId = portvlId;
    }

    public Integer getSvlan() {
        return svlan;
    }

    public void setSvlan(Integer svlan) {
        this.svlan = svlan;
    }

    public Integer getCvlanBd() {
        return cvlanBd;
    }

    public void setCvlanBd(Integer cvlanBd) {
        this.cvlanBd = cvlanBd;
    }

    public Long getQuyhoachId() {
        return quyhoachId;
    }

    public void setQuyhoachId(Long quyhoachId) {
        this.quyhoachId = quyhoachId;
    }
}
