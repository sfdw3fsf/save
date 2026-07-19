package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TTKH_TrangBi
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TTKH_TrangBi extends BaseDTO {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("ten")
    private String ten = null;
    @JsonProperty("htMacDinh")
    private String htMacDinh = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHtMacDinh() {
        return htMacDinh;
    }

    public void setHtMacDinh(String htMacDinh) {
        this.htMacDinh = htMacDinh;
    }
}
