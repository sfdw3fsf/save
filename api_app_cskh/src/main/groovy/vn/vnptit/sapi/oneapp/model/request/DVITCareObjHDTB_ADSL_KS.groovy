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
public class DVITCareObjHDTB_ADSL_KS {


    public long getHDTB_ID() {
        return HDTB_ID;
    }

    public void setHDTB_ID(long HDTB_ID) {
        this.HDTB_ID = HDTB_ID;
    }

    public int getCULY() {
        return CULY;
    }

    public void setCULY(int CULY) {
        this.CULY = CULY;
    }

    @JsonProperty("HDTB_ID")
    public long HDTB_ID;
    @JsonProperty("CULY")
    public int CULY;
}
