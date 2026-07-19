package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * ThangGC
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class KyApGC extends BaseDTO {
    @JsonProperty("kyGC")
    private String kyGC = null;
    @JsonProperty("thangAPHT")
    private String thangAPHT = null;

    public String getKyGC() {
        return kyGC;
    }

    public void setKyGC(String kyGC) {
        this.kyGC = kyGC;
    }

    public String getThangAPHT() {
        return thangAPHT;
    }

    public void setThangAPHT(String thangAPHT) {
        this.thangAPHT = thangAPHT;
    }
}
