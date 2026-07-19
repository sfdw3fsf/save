package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;

public class DonViDtoInp {
    @NotNull(message = "donvi_id không được để trống")
    private Integer donvi_id;

    @NotNull(message = "loaidv_id không được để trống")
    private Integer loaidv_id;

    public Integer getDonvi_id() {
        return donvi_id;
    }

    public void setDonvi_id(Integer donvi_id) {
        this.donvi_id = donvi_id;
    }

    public Integer getLoaidv_id() {
        return loaidv_id;
    }

    public void setLoaidv_id(Integer loaidv_id) {
        this.loaidv_id = loaidv_id;
    }
}
