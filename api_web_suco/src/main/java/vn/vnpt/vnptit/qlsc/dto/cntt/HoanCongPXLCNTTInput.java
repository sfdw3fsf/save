package vn.vnpt.vnptit.qlsc.dto.cntt;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class HoanCongPXLCNTTInput {

    @JsonProperty("phanvung_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer phanvung_id;

    public Integer getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(Integer phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    @JsonProperty("phieu_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long phieu_id;
    public Long getphieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Long phieu_id) {
        this.phieu_id = phieu_id;
    }

    @JsonProperty("ghi_chu")
    private String ghi_chu;
    public String getghi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }
}
