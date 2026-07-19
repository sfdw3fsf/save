package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class CheckMaGdRequestDto implements Serializable {
    private String maGd;

    public CheckMaGdRequestDto() {
    }

    public String getMaGd() {
        return maGd;
    }

    public void setMaGd(String maGd) {
        this.maGd = maGd;
    }
}
