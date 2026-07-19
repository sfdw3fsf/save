package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class XoaTinhNangNoiBatRequest {
    @JsonProperty("camnang_id")
    private String p_camnang_id;

    public String getP_camnang_id() {
        return p_camnang_id;
    }

    public void setP_camnang_id(String p_camnang_id) {
        this.p_camnang_id = p_camnang_id;
    }
}
