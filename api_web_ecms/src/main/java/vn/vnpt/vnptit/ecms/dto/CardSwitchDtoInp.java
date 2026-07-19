package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CardSwitchDtoInp {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer switch_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer loaicard_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer slot;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer vitri;

    private String ten_card;
    private String serial;
    private String partnumber;

    public Integer getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(Integer switch_id) {
        this.switch_id = switch_id;
    }

    public Integer getLoaicard_id() {
        return loaicard_id;
    }

    public void setLoaicard_id(Integer loaicard_id) {
        this.loaicard_id = loaicard_id;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Integer getVitri() {
        return vitri;
    }

    public void setVitri(Integer vitri) {
        this.vitri = vitri;
    }

    public String getTen_card() {
        return ten_card;
    }

    public void setTen_card(String ten_card) {
        this.ten_card = ten_card;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    static public class CardSwitchDtoInpUpd extends CardSwitchDtoInp {
        @NotNull(message = ModelValidation.NOT_NULL)
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        private Integer cardsw_id;

        public Integer getCardsw_id() {
            return cardsw_id;
        }

        public void setCardsw_id(Integer cardsw_id) {
            this.cardsw_id = cardsw_id;
        }
    }
}
