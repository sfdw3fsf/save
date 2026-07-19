package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareLayDanhSachHDKSCNTTRequest {

    @JsonProperty("vdichvuvt_id")
    private Integer vdichvuvt_id;

    @JsonProperty("vdonvi_id")
    private Integer vdonvi_id;

    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;

    public Integer getVdichvuvt_id() {
        return vdichvuvt_id;
    }

    public void setVdichvuvt_id(Integer vdichvuvt_id) {
        this.vdichvuvt_id = vdichvuvt_id;
    }

    public Integer getVdonvi_id() {
        return vdonvi_id;
    }

    public void setVdonvi_id(Integer vdonvi_id) {
        this.vdonvi_id = vdonvi_id;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }
}
