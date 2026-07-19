package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareKiemtraDKGiaoPhoihopRequest {


    public Integer getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Integer vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVphieu_id() {
        return vphieu_id;
    }

    public void setVphieu_id(Integer vphieu_id) {
        this.vphieu_id = vphieu_id;
    }

    public Integer getVdonvi_id() {
        return vdonvi_id;
    }

    public void setVdonvi_id(Integer vdonvi_id) {
        this.vdonvi_id = vdonvi_id;
    }

    public String getVten_dv() {
        return vten_dv;
    }

    public void setVten_dv(String vten_dv) {
        this.vten_dv = vten_dv;
    }

    @JsonProperty("vhdtb_id")
    private Integer vhdtb_id;
    @JsonProperty("vphieu_id")
    private Integer vphieu_id;
    @JsonProperty("vdonvi_id")
    private Integer vdonvi_id;
    @JsonProperty("vten_dv")
    private String vten_dv;
}
