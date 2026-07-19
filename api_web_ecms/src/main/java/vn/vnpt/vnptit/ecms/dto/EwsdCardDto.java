package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class EwsdCardDto {

    @JsonProperty("vitri")
    @NotNull(message = ModelValidation.NOT_NULL)
    @Max(value = 9999999999L, message = ModelValidation.MAX)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long viTri;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("shelf_ewsd_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long shelfEwsdId;


    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loaicard_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaiCardId;

    @JsonProperty("ten_card")
    private String tenCard;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    @Max(value = 9999999999L, message = ModelValidation.MAX)
    @JsonProperty("vitri_qu")
    private Long viTriQu;

    public Long getViTri() {
        return viTri;
    }

    public void setViTri(Long viTri) {
        this.viTri = viTri;
    }

    public Long getCard_ewsd_id() {
        return card_ewsd_id;
    }

    public void setCard_ewsd_id(Long card_ewsd_id) {
        this.card_ewsd_id = card_ewsd_id;
    }

    @JsonProperty("card_ewsd_id")
    private Long card_ewsd_id;
    public Long getShelfEwsdId() {
        return shelfEwsdId;
    }

    public void setShelfEwsdId(Long shelfEwsdId) {
        this.shelfEwsdId = shelfEwsdId;
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

    public Long getViTriQu() {
        return viTriQu;
    }

    public void setViTriQu(Long viTriQu) {
        this.viTriQu = viTriQu;
    }

    static public class  EwsdCardUpd extends EwsdCardDto {
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("card_ewsd_id")
        private Long cardEwsdId;

        public Long getCardEwsdId() {
            return cardEwsdId;
        }

        public void setCardEwsdId(Long cardEwsdId) {
            this.cardEwsdId = cardEwsdId;
        }
    }
}
