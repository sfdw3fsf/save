package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpsertVLANDto {
    final Long id;
    final String ten;
    @JsonProperty("idc_id")
    final Long idcId;
    @JsonProperty("vrf_id")
    final Long vrfId;
    @JsonProperty("zone_id")
    final Long zoneId;
    @JsonProperty("ghi_chu")
    final String ghiChu;
    @JsonProperty("hieu_luc")
    final int hieuLuc;

    public UpsertVLANDto(Long id, String ten, Long idcId, Long vrfId, Long zoneId, String ghiChu,
            int hieuLuc) {
        this.id = id;
        this.ten = ten;
        this.idcId = idcId;
        this.vrfId = vrfId;
        this.zoneId = zoneId;
        this.ghiChu = ghiChu;
        this.hieuLuc = hieuLuc;
    }

    public Long getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public Long getIdcId() {
        return idcId;
    }

    public Long getVrfId() {
        return vrfId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public int getHieuLuc() {
        return hieuLuc;
    }
}
