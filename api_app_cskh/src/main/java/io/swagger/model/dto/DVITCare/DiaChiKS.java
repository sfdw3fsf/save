package io.swagger.model.dto.DVITCare;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiaChiKS {

    @JsonProperty("DIACHI_ID")
    private long DIACHI_ID;
    @JsonProperty("TINH_ID")
    private long TINH_ID;
    @JsonProperty("QUAN_ID")
    private long QUAN_ID;
    @JsonProperty("PHUONG_ID")
    private long PHUONG_ID;
    @JsonProperty("PHO_ID")
    private long PHO_ID;
    @JsonProperty("AP_ID")
    private long AP_ID;
    @JsonProperty("KHU_ID")
    private long KHU_ID;
    @JsonProperty("DACDIEM_ID")
    private long DACDIEM_ID;
    @JsonProperty("SONHA")
    private String SONHA;
    @JsonProperty("DIACHI")
    private String DIACHI;

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
}
