package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DSGC {
    @JsonProperty("tgc")
    private String tgc = null;
    @JsonProperty("tiengc")
    private Long tiengc = null;
    @JsonProperty("vat")
    private Long vat = null;
    @JsonProperty("kygc")
    private Long kygc = null;

    public String getTgc() {
        return tgc;
    }

    public void setTgc(String tgc) {
        this.tgc = tgc;
    }

    public Long getTiengc() {
        return tiengc;
    }

    public void setTiengc(Long tiengc) {
        this.tiengc = tiengc;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public Long getKygc() {
        return kygc;
    }

    public void setKygc(Long kygc) {
        this.kygc = kygc;
    }
}
