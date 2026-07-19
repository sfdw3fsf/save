package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 3) Hiển thị thông số kỹ thuật
 */
public class HienThiThongSoKyThuatRequest {
    @JsonProperty("p_thuebao_id")
    private Integer p_thuebao_id;

    public Integer getP_thuebao_id() {
        return p_thuebao_id;
    }

    public void setP_thuebao_id(Integer p_thuebao_id) {
        this.p_thuebao_id = p_thuebao_id;
    }
}
