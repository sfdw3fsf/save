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
public class DVITCareLayCTThuebaoThicongMobileRequest {

    @JsonProperty("VHDTB_ID")
    private Integer VHDTB_ID;

    public Integer getVHDTB_ID() {
        return VHDTB_ID;
    }

    public void setVHDTB_ID(Integer VHDTB_ID) {
        this.VHDTB_ID = VHDTB_ID;
    }
}
