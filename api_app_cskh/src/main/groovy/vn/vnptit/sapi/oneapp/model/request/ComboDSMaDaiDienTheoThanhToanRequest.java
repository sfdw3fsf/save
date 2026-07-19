package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 5) Combo Ds mã đại diện theo thanh toán
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ComboDSMaDaiDienTheoThanhToanRequest {
    @JsonProperty("p_thanhtoan_id")
    private Integer p_thanhtoan_id;

    public Integer getP_thanhtoan_id() {
        return p_thanhtoan_id;
    }

    public void setP_thanhtoan_id(Integer p_thanhtoan_id) {
        this.p_thanhtoan_id = p_thanhtoan_id;
    }
}
