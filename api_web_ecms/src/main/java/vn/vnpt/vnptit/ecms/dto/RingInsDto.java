package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class RingInsDto {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(max = 50, message = ModelValidation.MAX + " 50")
    private String tenRing;
    
    @Size(max = 200, message = ModelValidation.MAX + " 200")
    private String ghiChu;

    public static class RingUpdDto extends RingInsDto {
        @NotNull(message = ModelValidation.NOT_NULL)
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @Max(value = 99999999999999l, message = ModelValidation.MAX)
        private Long ringId;

        public long getRingId() {
            return ringId;
        }

        public void setRingId(long ringId) {
            this.ringId = ringId;
        }
    }

    public String getTenRing() {
        return tenRing;
    }

    public void setTenRing(String tenRing) {
        this.tenRing = tenRing;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
