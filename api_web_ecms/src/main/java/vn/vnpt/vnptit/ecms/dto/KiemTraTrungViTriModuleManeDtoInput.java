package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class KiemTraTrungViTriModuleManeDtoInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer cardmane_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer vitri;

    public Integer getCardmane_id() {
        return cardmane_id;
    }

    public void setCardmane_id(Integer cardmane_id) {
        this.cardmane_id = cardmane_id;
    }

    public Integer getVitri() {
        return vitri;
    }

    public void setVitri(Integer vitri) {
        this.vitri = vitri;
    }
}
