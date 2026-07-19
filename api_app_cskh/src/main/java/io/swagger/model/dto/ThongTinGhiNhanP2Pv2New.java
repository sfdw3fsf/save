package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * ThongTinGhiNhanP2Pv2New
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThongTinGhiNhanP2Pv2New {
    @JsonProperty("hopLe")
    private Integer hopLe;
    @JsonProperty("thongTinGhiNhan")
    private List<ThongTinGhiNhanP2PNew> thongTinGhiNhan;

    public Integer getHopLe() {
        return hopLe;
    }

    public void setHopLe(Integer hopLe) {
        this.hopLe = hopLe;
    }

    public List<ThongTinGhiNhanP2PNew> getThongTinGhiNhan() {
        return thongTinGhiNhan;
    }

    public void setThongTinGhiNhan(List<ThongTinGhiNhanP2PNew> thongTinGhiNhan) {
        this.thongTinGhiNhan = thongTinGhiNhan;
    }
}
