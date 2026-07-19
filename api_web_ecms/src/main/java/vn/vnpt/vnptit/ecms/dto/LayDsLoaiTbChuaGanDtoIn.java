package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LayDsLoaiTbChuaGanDtoIn {

    @JsonProperty("prefix")
    private String prefix;

    @JsonProperty("dichvuvt_id")
    private Integer dichvuvtId;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getDichvuvtId() {
        return dichvuvtId;
    }

    public void setDichvuvtId(Integer dichvuvtId) {
        this.dichvuvtId = dichvuvtId;
    }
}
