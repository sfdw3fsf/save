package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class GhiChuXuLyDichVuInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer phanvung_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer phieu_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer suco_ptm_id;

    @JsonProperty("ghi_chu")
    private String ghi_chu;

    public Integer getPhanvung_id() { return this.phanvung_id;}
    public  void setPhanvung_id(Integer phanvungId) { this.phanvung_id = phanvungId;}


    public Integer getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Integer phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Integer getSuco_ptm_id() {
        return suco_ptm_id;
    }

    public void setSuco_ptm_id(Integer suco_ptm_id) {
        this.suco_ptm_id = suco_ptm_id;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }
}
