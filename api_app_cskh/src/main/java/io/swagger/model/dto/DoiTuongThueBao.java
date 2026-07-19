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
public class DoiTuongThueBao extends BaseDTO {
    @JsonProperty("doituong_id")
    private Long doituong_id = null;
    @JsonProperty("ten_dt")
    private String ten_dt = null;
    @JsonProperty("status")
    private String status = null;

    public Long getDoituong_id() {
        return doituong_id;
    }

    public void setDoituong_id(Long doituong_id) {
        this.doituong_id = doituong_id;
    }

    public String getTen_dt() {
        return ten_dt;
    }

    public void setTen_dt(String ten_dt) {
        this.ten_dt = ten_dt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
