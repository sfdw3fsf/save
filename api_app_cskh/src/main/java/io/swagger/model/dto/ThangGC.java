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
public class ThangGC extends BaseDTO {
    @JsonProperty("thangGC")
    private String thangGC = null;
    @JsonProperty("thangGCHT")
    private String thangGCHT = null;

    public String getThangGC() {
        return thangGC;
    }

    public void setThangGC(String thangGC) {
        this.thangGC = thangGC;
    }

    public String getThangGCHT() {
        return thangGCHT;
    }

    public void setThangGCHT(String thangGCHT) {
        this.thangGCHT = thangGCHT;
    }
}
