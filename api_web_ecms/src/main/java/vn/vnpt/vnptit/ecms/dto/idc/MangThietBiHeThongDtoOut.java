package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MangThietBiHeThongDtoOut {

    @JsonProperty("mang_thiet_bi_id")
    private Long mangThietBiId;

    @JsonProperty("mang_thiet_bi_ten")
    private String manThietBiTen;

    public Long getMangThietBiId() {
        return mangThietBiId;
    }

    public void setMangThietBiId(Long mangThietBiId) {
        this.mangThietBiId = mangThietBiId;
    }

    public String getManThietBiTen() {
        return manThietBiTen;
    }

    public void setManThietBiTen(String manThietBiTen) {
        this.manThietBiTen = manThietBiTen;
    }
}