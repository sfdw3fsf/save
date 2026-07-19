package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class DaiPortDto {

    @JsonProperty("dslamId")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long dslamId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("rackId")
    private Long rackId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("slotBd")
    private Long slotBd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("slotKt")
    private Long slotKt;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long pageIndex;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long pageSize;

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getSlotBd() {
        return slotBd;
    }

    public void setSlotBd(Long slotBd) {
        this.slotBd = slotBd;
    }

    public Long getSlotKt() {
        return slotKt;
    }

    public void setSlotKt(Long slotKt) {
        this.slotKt = slotKt;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
