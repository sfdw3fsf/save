package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class CheckNguonYcRequestDto implements Serializable {
    private String ims;
    private String so;

    public CheckNguonYcRequestDto() {
    }

    public String getIms() {
        return ims;
    }

    public void setIms(String ims) {
        this.ims = ims;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }
}
