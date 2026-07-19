package vn.vnpt.vnptit.qlsc.dto.mnv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrangThaiHoTroDTO {
    @JsonProperty("TRANGTHAI_ID")
    private int trangThaiId;
    @JsonProperty("TRANGTHAI")
    private String trangThai;

    public TrangThaiHoTroDTO(int trangThaiId, String trangThai) {
        this.trangThaiId = trangThaiId;
        this.trangThai = trangThai;
    }

}
