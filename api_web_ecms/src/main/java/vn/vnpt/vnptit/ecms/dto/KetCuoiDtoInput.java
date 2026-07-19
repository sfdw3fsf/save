package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KetCuoiDtoInput {

    @JsonProperty("phanvung_id")
    private String phanvung_id;

    @JsonProperty("p_cap_id")
    private String p_cap_id;

    public String getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(String phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public String getP_cap_id() {
        return p_cap_id;
    }

    public void setP_cap_id(String p_cap_id) {
        this.p_cap_id = p_cap_id;
    }
}
