package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TaoPortLGCardSwitch {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer cardsw_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Boolean istaoportlg = false;

    public Integer getCardsw_id() {
        return cardsw_id;
    }

    public void setCardsw_id(Integer cardsw_id) {
        this.cardsw_id = cardsw_id;
    }

    public Boolean getIstaoportlg() {
        return istaoportlg;
    }

    public void setIstaoportlg(Boolean istaoportlg) {
        this.istaoportlg = istaoportlg;
    }
}
