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
public class DVITCareLayDsCauhoiTheoLoaiNVRequest {

    public Integer getVloainv_id() {
        return vloainv_id;
    }

    public void setVloainv_id(Integer vloainv_id) {
        this.vloainv_id = vloainv_id;
    }

    public Integer getVhinhthucks_id() {
        return vhinhthucks_id;
    }

    public void setVhinhthucks_id(Integer vhinhthucks_id) {
        this.vhinhthucks_id = vhinhthucks_id;
    }

    public Long getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Long vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    @JsonProperty("vloainv_id")
    private Integer vloainv_id;

    @JsonProperty("vhinhthucks_id")
    private Integer vhinhthucks_id;

    @JsonProperty("vhdtb_id")
    private Long vhdtb_id;



}
