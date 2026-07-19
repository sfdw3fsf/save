package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TuVanCaiApp
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TuVanCaiApp {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("ketQua")
    private Integer ketQua;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKetQua() {
        return ketQua;
    }

    public void setKetQua(Integer ketQua) {
        this.ketQua = ketQua;
    }
}
