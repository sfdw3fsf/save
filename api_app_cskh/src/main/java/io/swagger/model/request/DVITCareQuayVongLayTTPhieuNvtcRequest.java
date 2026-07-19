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
public class DVITCareQuayVongLayTTPhieuNvtcRequest {


    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public Integer getVthuebao_id() {
        return vthuebao_id;
    }

    public void setVthuebao_id(Integer vthuebao_id) {
        this.vthuebao_id = vthuebao_id;
    }

    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;
    @JsonProperty("vthuebao_id")
    private Integer vthuebao_id;
}
