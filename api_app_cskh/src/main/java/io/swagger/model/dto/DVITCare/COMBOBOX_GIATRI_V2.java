package io.swagger.model.dto.DVITCare;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
public class COMBOBOX_GIATRI_V2 {
    public String getCbb_value() {
        return cbb_value;
    }

    public void setCbb_value(String cbb_value) {
        this.cbb_value = cbb_value;
    }

    public String getCbb_display() {
        return cbb_display;
    }

    public void setCbb_display(String cbb_display) {
        this.cbb_display = cbb_display;
    }

    @JsonProperty("cbb_value")
    private String cbb_value;
    @JsonProperty("cbb_display")
    private String cbb_display;
}
