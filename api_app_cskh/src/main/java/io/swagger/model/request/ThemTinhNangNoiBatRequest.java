package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class ThemTinhNangNoiBatRequest {
    @JsonProperty("tieu_de")
    @NotNull
    private String p_tieude;

    @JsonProperty("mo_ta")
    @NotNull
    private String p_mota;

    public String getP_tieude() {
        return p_tieude;
    }

    public void setP_tieude(String p_tieude) {
        this.p_tieude = p_tieude;
    }

    public String getP_mota() {
        return p_mota;
    }

    public void setP_mota(String p_mota) {
        this.p_mota = p_mota;
    }
}
