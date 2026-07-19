package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * CauHoiKhaoSatResponse
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CauHoiKhaoSatData {
    @JsonProperty("dsCauHoiChinh")
    private List<CauHoiKS> dsCauHoiChinh = new ArrayList<>();
    @JsonProperty("dsCauHoiPhu")
    private List<CauHoiKS> dsCauHoiPhu = new ArrayList<>();

    public List<CauHoiKS> getDsCauHoiChinh() {
        return dsCauHoiChinh;
    }

    public void setDsCauHoiChinh(List<CauHoiKS> dsCauHoiChinh) {
        this.dsCauHoiChinh = dsCauHoiChinh;
    }

    public List<CauHoiKS> getDsCauHoiPhu() {
        return dsCauHoiPhu;
    }

    public void setDsCauHoiPhu(List<CauHoiKS> dsCauHoiPhu) {
        this.dsCauHoiPhu = dsCauHoiPhu;
    }
}
