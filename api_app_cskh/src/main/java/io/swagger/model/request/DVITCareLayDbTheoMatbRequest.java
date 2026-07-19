package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 *  Dịch vụ IT Care
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareLayDbTheoMatbRequest {

    @JsonProperty("in_ma_tb")
    private String in_ma_tb;

    @JsonProperty("in_donvi_dl_id")
    private Integer in_donvi_dl_id;

    @JsonProperty("in_dichvuvt_id")
    private Integer in_dichvuvt_id;

    public String getIn_ma_tb() {
        return in_ma_tb;
    }

    public void setIn_ma_tb(String in_ma_tb) {
        this.in_ma_tb = in_ma_tb;
    }

    public Integer getIn_donvi_dl_id() {
        return in_donvi_dl_id;
    }

    public void setIn_donvi_dl_id(Integer in_donvi_dl_id) {
        this.in_donvi_dl_id = in_donvi_dl_id;
    }

    public Integer getIn_dichvuvt_id() {
        return in_dichvuvt_id;
    }

    public void setIn_dichvuvt_id(Integer in_dichvuvt_id) {
        this.in_dichvuvt_id = in_dichvuvt_id;
    }
}
