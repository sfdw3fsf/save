package io.swagger.model.dto.DVITCare;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HDTBDV_KS {
    @JsonProperty("HDTB_ID")
    private long HDTB_ID;
    @JsonProperty("LOAIDV_ID")
    private int LOAIDV_ID;
    @JsonProperty("DONVI_ID")
    private int DONVI_ID;
    @JsonProperty("KIEUDV_ID")
    private int KIEUDV_ID;

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
}
