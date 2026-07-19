package io.swagger.model.dto.DVITCare;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HDTB_ADSL_KS {
    @JsonProperty("HDTB_ID")
    private long HDTB_ID;
    @JsonProperty("CULY")
    private int CULY;

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
}
