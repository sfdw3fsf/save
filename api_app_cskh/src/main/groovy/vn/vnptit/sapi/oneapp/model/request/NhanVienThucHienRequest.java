package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 8) Lấy nhân viên thực hiện
 */
public class NhanVienThucHienRequest {
    @JsonProperty("p_baohong_id")
    private Integer p_baohong_id;

    public Integer getP_baohong_id() {
        return p_baohong_id;
    }

    public void setP_baohong_id(Integer p_baohong_id) {
        this.p_baohong_id = p_baohong_id;
    }
}
