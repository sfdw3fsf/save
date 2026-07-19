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
public class DVITCareLayDsHdtbTiepnhanKsRequest {
    @JsonProperty("vhdkh_id")
    private Integer vhdkh_id;

    @JsonProperty("vtthd_id")
    private Integer vtthd_id;

    @JsonProperty("vloaihd_id")
    private Integer vloaihd_id;

    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;

    @JsonProperty("vma_gd")
    private String vma_gd;

    public Integer getVhdkh_id() {
        return vhdkh_id;
    }

    public void setVhdkh_id(Integer vhdkh_id) {
        this.vhdkh_id = vhdkh_id;
    }

    public Integer getVtthd_id() {
        return vtthd_id;
    }

    public void setVtthd_id(Integer vtthd_id) {
        this.vtthd_id = vtthd_id;
    }

    public Integer getVloaihd_id() {
        return vloaihd_id;
    }

    public void setVloaihd_id(Integer vloaihd_id) {
        this.vloaihd_id = vloaihd_id;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public String getVma_gd() {
        return vma_gd;
    }

    public void setVma_gd(String vma_gd) {
        this.vma_gd = vma_gd;
    }
}
