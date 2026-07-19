package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.TuVanCaiApp;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * TuVanMobileMoneyRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TuVanMobileMoneyRequest {

    @JsonProperty("dsGhiNhan")
    private List<TuVanCaiApp> dsGhiNhan;
    @JsonProperty("maTB")
    private String maTB;

    public List<TuVanCaiApp> getDsGhiNhan() {
        return dsGhiNhan;
    }

    public void setDsGhiNhan(List<TuVanCaiApp> dsGhiNhan) {
        this.dsGhiNhan = dsGhiNhan;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }
}
