package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DsNhanVienTheoDonViDtoIn implements Serializable {
    @NotNull
    private Long donvi_id;

//    public DsNhanVienTheoDonViDtoIn() {
//    }

    @NotNull
    public Long getDonvi_id() {
        return donvi_id;
    }

    public void setDonvi_id(Long donvi_id) {
        this.donvi_id = donvi_id;
    }
}
