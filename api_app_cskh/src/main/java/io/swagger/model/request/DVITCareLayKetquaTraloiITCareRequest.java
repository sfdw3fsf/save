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
public class DVITCareLayKetquaTraloiITCareRequest {


    public Integer getVtbtl_id() {
        return vtbtl_id;
    }

    public void setVtbtl_id(Integer vtbtl_id) {
        this.vtbtl_id = vtbtl_id;
    }

    @JsonProperty("vtbtl_id")
    private Integer vtbtl_id;

}
