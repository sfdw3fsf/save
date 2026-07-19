package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class EwsdInsDto {

    @JsonProperty("ten_ewsd")
    @NotNull(message = ModelValidation.NOT_NULL)
    private String tenEwsd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loai_ewsd_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaiEwsdId;


    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("turack_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long turackId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    @JsonProperty("host_id")
    private Long hostId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("orig")
    private Long orig;

    public String getTenEwsd() {
        return tenEwsd;
    }

    public void setTenEwsd(String tenEwsd) {
        this.tenEwsd = tenEwsd;
    }

    public Long getLoaiEwsdId() {
        return loaiEwsdId;
    }

    public void setLoaiEwsdId(Long loaiEwsdId) {
        this.loaiEwsdId = loaiEwsdId;
    }

    public Long getTurackId() {
        return turackId;
    }

    public void setTurackId(Long turackId) {
        this.turackId = turackId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Long getOrig() {
        return orig;
    }

    public void setOrig(Long orig) {
        this.orig = orig;
    }

    static public class EwsdUpdateDto extends EwsdInsDto{
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("ewsd_id")
        private Long ewsdId;

        public Long getEwsdId() {
            return ewsdId;
        }

        public void setEwsdId(Long ewsdId) {
            this.ewsdId = ewsdId;
        }
    }
}
