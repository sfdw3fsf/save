package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuyHoachVlanUpeInput {

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long vlanTu;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long vlanDen;

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getVlanTu() {
        return vlanTu;
    }

    public void setVlanTu(Long vlanTu) {
        this.vlanTu = vlanTu;
    }

    public Long getVlanDen() {
        return vlanDen;
    }

    public void setVlanDen(Long vlanDen) {
        this.vlanDen = vlanDen;
    }
}
