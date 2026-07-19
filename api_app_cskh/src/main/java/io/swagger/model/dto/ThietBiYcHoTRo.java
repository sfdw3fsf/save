package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThietBiYcHoTRo {
    @JsonProperty("tenThietBi")
    private String tenThietBi;
    @JsonProperty("congSoi")
    private String congSoi;

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getCongSoi() {
        return congSoi;
    }

    public void setCongSoi(String congSoi) {
        this.congSoi = congSoi;
    }
}
