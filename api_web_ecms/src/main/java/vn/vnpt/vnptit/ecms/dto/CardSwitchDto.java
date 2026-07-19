package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class CardSwitchDto {
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_switch_id")
    private Long switchId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_vitri")
    private Long viTri;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_slot")
    private Long slot;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("p_serial")
    private String serial;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("p_partnumber")
    private String partNumber;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_loaicard_id")
    private Long loaiCardId;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("p_ten_card")
    private String tenCard;

    static public class CardSwitchUpDto extends CardSwitchDto {

        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("p_cardsw_id")
        private Long cardswId;

        public Long getCardswId() {
            return cardswId;
        }

        public void setCardswId(Long cardswId) {
            this.cardswId = cardswId;
        }
    }

    public Long getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Long switchId) {
        this.switchId = switchId;
    }

    public Long getViTri() {
        return viTri;
    }

    public void setViTri(Long viTri) {
        this.viTri = viTri;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Long getLoaiCardId() {
        return loaiCardId;
    }

    public void setLoaiCardId(Long loaiCardId) {
        this.loaiCardId = loaiCardId;
    }

    public String getTenCard() {
        return tenCard;
    }

    public void setTenCard(String tenCard) {
        this.tenCard = tenCard;
    }
}
