package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Constant;

import javax.annotation.Generated;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ThongTinGhiNhanP2Pv2
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThongTinGhiNhanP2Pv2 {
    @JsonProperty("hopLe")
    private Integer hopLe;
    @JsonProperty("thongTinGhiNhan")
    private List<ThongTinGhiNhanP2P> thongTinGhiNhan;

    public Integer getHopLe() {
        return hopLe;
    }

    public void setHopLe(Integer hopLe) {
        this.hopLe = hopLe;
    }

    public List<ThongTinGhiNhanP2P> getThongTinGhiNhan() {
        return thongTinGhiNhan;
    }

    public void setThongTinGhiNhan(List<ThongTinGhiNhanP2P> thongTinGhiNhan) {
        this.thongTinGhiNhan = thongTinGhiNhan;
    }
}
