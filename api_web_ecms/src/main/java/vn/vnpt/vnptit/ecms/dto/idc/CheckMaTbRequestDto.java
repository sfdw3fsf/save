package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class CheckMaTbRequestDto implements Serializable {
    private String maTb;
    private String maGd;

    public CheckMaTbRequestDto() {
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getMaGd() {
        return maGd;
    }

    public void setMaGd(String maGd) {
        this.maGd = maGd;
    }
}
