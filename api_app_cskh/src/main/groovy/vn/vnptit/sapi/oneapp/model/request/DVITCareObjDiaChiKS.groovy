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
public class DVITCareObjDiaChiKS {


    public long getDIACHI_ID() {
        return DIACHI_ID;
    }

    public void setDIACHI_ID(long DIACHI_ID) {
        this.DIACHI_ID = DIACHI_ID;
    }

    public long getTINH_ID() {
        return TINH_ID;
    }

    public void setTINH_ID(long TINH_ID) {
        this.TINH_ID = TINH_ID;
    }

    public long getQUAN_ID() {
        return QUAN_ID;
    }

    public void setQUAN_ID(long QUAN_ID) {
        this.QUAN_ID = QUAN_ID;
    }

    public long getPHUONG_ID() {
        return PHUONG_ID;
    }

    public void setPHUONG_ID(long PHUONG_ID) {
        this.PHUONG_ID = PHUONG_ID;
    }

    public long getPHO_ID() {
        return PHO_ID;
    }

    public void setPHO_ID(long PHO_ID) {
        this.PHO_ID = PHO_ID;
    }

    public long getAP_ID() {
        return AP_ID;
    }

    public void setAP_ID(long AP_ID) {
        this.AP_ID = AP_ID;
    }

    public long getKHU_ID() {
        return KHU_ID;
    }

    public void setKHU_ID(long KHU_ID) {
        this.KHU_ID = KHU_ID;
    }

    public long getDACDIEM_ID() {
        return DACDIEM_ID;
    }

    public void setDACDIEM_ID(long DACDIEM_ID) {
        this.DACDIEM_ID = DACDIEM_ID;
    }

    public String getSONHA() {
        return SONHA;
    }

    public void setSONHA(String SONHA) {
        this.SONHA = SONHA;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    @JsonProperty("DIACHI_ID")
    public long DIACHI_ID;
    @JsonProperty("TINH_ID")
    public long TINH_ID;
    @JsonProperty("QUAN_ID")
    public long QUAN_ID;
    @JsonProperty("PHUONG_ID")
    public long PHUONG_ID;
    @JsonProperty("PHO_ID")
    public long PHO_ID;
    @JsonProperty("AP_ID")
    public long AP_ID;
    @JsonProperty("KHU_ID")
    public long KHU_ID;
    @JsonProperty("DACDIEM_ID")
    public long DACDIEM_ID;
    @JsonProperty("SONHA")
    public String SONHA;
    @JsonProperty("DIACHI")
    public String DIACHI;
}
