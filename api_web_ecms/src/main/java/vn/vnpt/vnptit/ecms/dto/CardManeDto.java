package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class CardManeDto {
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("dslam_id")
    private Long dslamId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vitri")
    private Long viTri;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loaicard_id")
    private Long loaiCardId;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("ten_card")
    private String tenCard;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("partnumber")
    private String partNumber;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("serial")
    private String serial;

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getViTri() {
        return viTri;
    }

    public void setViTri(Long viTri) {
        this.viTri = viTri;
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

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    static public class CardManeUpdDto extends CardManeDto{

        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("cardmane_id")
        private Long cardManeId;

        public Long getCardManeId() {
            return cardManeId;
        }

        public void setCardManeId(Long cardManeId) {
            this.cardManeId = cardManeId;
        }
    }

}
