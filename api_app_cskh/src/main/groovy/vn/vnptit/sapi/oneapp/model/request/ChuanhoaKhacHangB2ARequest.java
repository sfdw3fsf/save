package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * API kiểm tra chuẩn hóa dữ liệu - phiếu khảo sát B2A - ONEAPP-18133
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChuanhoaKhacHangB2ARequest {
    @JsonProperty("v_khachhang_id")
    private Integer v_khachhang_id;

    public Integer getV_khachhang_id() {
        return v_khachhang_id;
    }

    public void setV_khachhang_id(Integer v_khachhang_id) {
        this.v_khachhang_id = v_khachhang_id;
    }
}
