package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class GanNhomSuCoRequest extends BaseDTO {
    @JsonProperty("dsSuCoId")
    private Long dsSuCoId = null;
    @JsonProperty("suCoChaId ")
    private Long suCoChaId = null;

    public Long getDsSuCoId() {
        return dsSuCoId;
    }

    public void setDsSuCoId(Long dsSuCoId) {
        this.dsSuCoId = dsSuCoId;
    }

    public Long getSuCoChaId() {
        return suCoChaId;
    }

    public void setSuCoChaId(Long suCoChaId) {
        this.suCoChaId = suCoChaId;
    }
}
