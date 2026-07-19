package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CapDtoInput {
    @JsonProperty("phanvung_id")
    private String phanvung_id;

    @JsonProperty("ketcuoi_id")
    private String ketcuoi_id;

    public String getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(String phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public String getKetcuoi_id() {
        return ketcuoi_id;
    }

    public void setKetcuoi_id(String ketcuoi_id) {
        this.ketcuoi_id = ketcuoi_id;
    }
}
