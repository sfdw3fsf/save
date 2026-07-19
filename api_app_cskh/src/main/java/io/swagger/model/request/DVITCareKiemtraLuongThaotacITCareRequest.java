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
public class DVITCareKiemtraLuongThaotacITCareRequest {


    public Integer getVluong_id() {
        return vluong_id;
    }

    public void setVluong_id(Integer vluong_id) {
        this.vluong_id = vluong_id;
    }

    public Integer getVthaotac_id() {
        return vthaotac_id;
    }

    public void setVthaotac_id(Integer vthaotac_id) {
        this.vthaotac_id = vthaotac_id;
    }

    @JsonProperty("vluong_id")
    private Integer vluong_id;
    @JsonProperty("vthaotac_id")
    private Integer vthaotac_id;
}
