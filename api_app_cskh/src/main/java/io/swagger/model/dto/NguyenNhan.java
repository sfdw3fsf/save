package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class NguyenNhan extends BaseDTO {
    @JsonProperty("nguyenNhanId")
    private Long nguyenNhanId = null;
    @JsonProperty("nguyenNhan")
    private String nguyenNhan = null;

    public Long getNguyenNhanId() {
        return nguyenNhanId;
    }

    public void setNguyenNhanId(Long nguyenNhanId) {
        this.nguyenNhanId = nguyenNhanId;
    }

    public String getNguyenNhan() {
        return nguyenNhan;
    }

    public void setNguyenNhan(String nguyenNhan) {
        this.nguyenNhan = nguyenNhan;
    }
}
