package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class EwsdShelfIns {

    @JsonProperty("ten_shelf")
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String tenShelf;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("ewsd_id")
    private Long ewsdId;


    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("so_slot")
    @Max(value = 9999L, message = ModelValidation.MAX)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long soSlot;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @Max(value = 9999L, message = ModelValidation.MAX)
    @JsonProperty("vi_tri")
    private Long viTri;

    public String getTenShelf() {
        return tenShelf;
    }

    public void setTenShelf(String tenShelf) {
        this.tenShelf = tenShelf;
    }

    public Long getEwsdId() {
        return ewsdId;
    }

    public void setEwsdId(Long ewsdId) {
        this.ewsdId = ewsdId;
    }

    public Long getSoSlot() {
        return soSlot;
    }

    public void setSoSlot(Long soSlot) {
        this.soSlot = soSlot;
    }

    public Long getViTri() {
        return viTri;
    }

    public void setViTri(Long viTri) {
        this.viTri = viTri;
    }

    static public class EwsdShelfUpd extends EwsdShelfIns {
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("shelf_ewsd_id")
        private Long shelfEwsdId;

        public Long getShelfEwsdId() {
            return shelfEwsdId;
        }

        public void setShelfEwsdId(Long shelfEwsdId) {
            this.shelfEwsdId = shelfEwsdId;
        }
    }
}
