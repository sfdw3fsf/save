package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class DVITCareChayTuyenNvtcThunoRequest {

    public String getVma_tt() {
        return vma_tt;
    }

    public void setVma_tt(String vma_tt) {
        this.vma_tt = vma_tt;
    }

    @JsonProperty("vma_tt")
    private String vma_tt;

}
