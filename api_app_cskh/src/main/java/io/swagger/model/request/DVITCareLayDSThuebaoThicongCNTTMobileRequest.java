package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.Date;

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareLayDSThuebaoThicongCNTTMobileRequest {

    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;

    @JsonProperty("vtthd_id")
    private Integer vtthd_id;

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public Integer getVtthd_id() {
        return vtthd_id;
    }

    public void setVtthd_id(Integer vtthd_id) {
        this.vtthd_id = vtthd_id;
    }
}
