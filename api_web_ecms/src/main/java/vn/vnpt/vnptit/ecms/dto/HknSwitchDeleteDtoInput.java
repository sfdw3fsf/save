package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class HknSwitchDeleteDtoInput {

    @JsonProperty("phanvung_id")
    private long phanvung_id;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("porvl_id_n")
    private long porvl_id_n;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("porvl_id_d")
    private long porvl_id_d;

    @Max(999)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("tt_port_id")
    private long tt_port_id;

    @Max(9)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("link_code")
    private long link_code;

    public long getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(long phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public long getPorvl_id_n() {
        return porvl_id_n;
    }

    public void setPorvl_id_n(long porvl_id_n) {
        this.porvl_id_n = porvl_id_n;
    }

    public long getPorvl_id_d() {
        return porvl_id_d;
    }

    public void setPorvl_id_d(long porvl_id_d) {
        this.porvl_id_d = porvl_id_d;
    }

    public long getTt_port_id() {
        return tt_port_id;
    }

    public void setTt_port_id(long tt_port_id) {
        this.tt_port_id = tt_port_id;
    }

    public long getLink_code() {
        return link_code;
    }

    public void setLink_code(long link_code) {
        this.link_code = link_code;
    }
}
