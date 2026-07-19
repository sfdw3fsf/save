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
public class LayDSThietBiTheoThueBaoRequest {
    @JsonProperty("vma_tb")
    private String vma_tb;

    public String getVma_tb() {
        return vma_tb;
    }

    public void setVma_tb(String vma_tb) {
        this.vma_tb = vma_tb;
    }
}
