package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 * Cập nhật dịch kết quả tư vấn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DVITCareLayDsKhuvucTheoHdDbRequest {


    public Integer getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Integer vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVinhd_db() {
        return vinhd_db;
    }

    public void setVinhd_db(Integer vinhd_db) {
        this.vinhd_db = vinhd_db;
    }

    @JsonProperty("vhdtb_id")
    private Integer vhdtb_id;
    @JsonProperty("vinhd_db")
    private Integer vinhd_db;
}
