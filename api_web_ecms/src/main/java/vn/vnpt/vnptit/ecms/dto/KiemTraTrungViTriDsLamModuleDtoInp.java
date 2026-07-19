package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class KiemTraTrungViTriDsLamModuleDtoInp {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer vitri;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer cardDslId;

    public Integer getVitri() {
        return vitri;
    }

    public void setVitri(Integer vitri) {
        this.vitri = vitri;
    }

    public Integer getCardDslId() {
        return cardDslId;
    }

    public void setCardDslId(Integer cardDslId) {
        this.cardDslId = cardDslId;
    }
}
