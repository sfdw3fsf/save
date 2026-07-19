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
public class DVITCareObjHDTBDV_KS {


    public long getHDTB_ID() {
        return HDTB_ID;
    }

    public void setHDTB_ID(long HDTB_ID) {
        this.HDTB_ID = HDTB_ID;
    }

    public int getLOAIDV_ID() {
        return LOAIDV_ID;
    }

    public void setLOAIDV_ID(int LOAIDV_ID) {
        this.LOAIDV_ID = LOAIDV_ID;
    }

    public int getDONVI_ID() {
        return DONVI_ID;
    }

    public void setDONVI_ID(int DONVI_ID) {
        this.DONVI_ID = DONVI_ID;
    }

    public int getKIEUDV_ID() {
        return KIEUDV_ID;
    }

    public void setKIEUDV_ID(int KIEUDV_ID) {
        this.KIEUDV_ID = KIEUDV_ID;
    }

    @JsonProperty("HDTB_ID")
    public long HDTB_ID;
    @JsonProperty("LOAIDV_ID")
    public int LOAIDV_ID;
    @JsonProperty("DONVI_ID")
    public int DONVI_ID;
    @JsonProperty("KIEUDV_ID")
    public int KIEUDV_ID;
}
