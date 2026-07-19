package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class HknSwitchUpdateDtoInput {
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("porvl_id_n")
    private Long porvl_id_n;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loaitbi_id_n")
    private Long loaitbi_id_n;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("porvl_id_d")
    private Long porvl_id_d;

    @Max(99999)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loaitbi_id_d")
    private Long loaitbi_id_d;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("thietbi_id_n")
    private Long thietbi_id_n;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("thietbi_id_d")
    private Long thietbi_id_d;

    @Max(999)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("tt_port_id")
    private Long tt_port_id;

    @Max(9)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("link_code")
    private Long link_code;

    public Long getPorvl_id_n() {
        return porvl_id_n;
    }

    public void setPorvl_id_n(Long porvl_id_n) {
        this.porvl_id_n = porvl_id_n;
    }

    public Long getLoaitbi_id_n() {
        return loaitbi_id_n;
    }

    public void setLoaitbi_id_n(Long loaitbi_id_n) {
        this.loaitbi_id_n = loaitbi_id_n;
    }

    public Long getPorvl_id_d() {
        return porvl_id_d;
    }

    public void setPorvl_id_d(Long porvl_id_d) {
        this.porvl_id_d = porvl_id_d;
    }

    public Long getLoaitbi_id_d() {
        return loaitbi_id_d;
    }

    public void setLoaitbi_id_d(Long loaitbi_id_d) {
        this.loaitbi_id_d = loaitbi_id_d;
    }

    public Long getThietbi_id_n() {
        return thietbi_id_n;
    }

    public void setThietbi_id_n(Long thietbi_id_n) {
        this.thietbi_id_n = thietbi_id_n;
    }

    public Long getThietbi_id_d() {
        return thietbi_id_d;
    }

    public void setThietbi_id_d(Long thietbi_id_d) {
        this.thietbi_id_d = thietbi_id_d;
    }

    public Long getTt_port_id() {
        return tt_port_id;
    }

    public void setTt_port_id(Long tt_port_id) {
        this.tt_port_id = tt_port_id;
    }

    public Long getLink_code() {
        return link_code;
    }

    public void setLink_code(Long link_code) {
        this.link_code = link_code;
    }
}
