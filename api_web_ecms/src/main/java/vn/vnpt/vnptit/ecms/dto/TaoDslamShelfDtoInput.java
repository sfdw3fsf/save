package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class TaoDslamShelfDtoInput{

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("framedsl_id")
    private Long framedsl_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("ten_shelf")
    private String ten_shelf;

    @Max(999)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vi_tri")
    private Long vi_tri;

    @Max(999)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("so_slot")
    private Long so_slot;

    @Max(999)
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("slot_bd")
    private Long slot_bd;

    public Long getFramedsl_id() {
        return framedsl_id;
    }

    public void setFramedsl_id(Long framedsl_id) {
        this.framedsl_id = framedsl_id;
    }

    public String getTen_shelf() {
        return ten_shelf;
    }

    public void setTen_shelf(String ten_shelf) {
        this.ten_shelf = ten_shelf;
    }

    public Long getVi_tri() {
        return vi_tri;
    }

    public void setVi_tri(Long vi_tri) {
        this.vi_tri = vi_tri;
    }

    public Long getSo_slot() {
        return so_slot;
    }

    public void setSo_slot(Long so_slot) {
        this.so_slot = so_slot;
    }

    public Long getSlot_bd() {
        return slot_bd;
    }

    public void setSlot_bd(Long slot_bd) {
        this.slot_bd = slot_bd;
    }
}
