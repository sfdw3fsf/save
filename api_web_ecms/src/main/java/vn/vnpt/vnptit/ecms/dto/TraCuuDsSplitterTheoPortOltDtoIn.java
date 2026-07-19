package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TraCuuDsSplitterTheoPortOltDtoIn {

    @JsonProperty("ttvt_id")
    private String ttvtId;

    @JsonProperty("tokt_id")
    private String toktId;

    @JsonProperty("tramtb_id")
    private String tramtbId;

    @JsonProperty("olt_id")
    private String oltId;

    @JsonProperty("cardolt_id")
    private String cardOltId;

    @JsonProperty("lst_port_vl_id")
    private String lstPortVlId;

    @JsonProperty("ky_hieu")
    private String kyHieu;

    @JsonProperty("dia_chi")
    private String diaChi;

    public String getTtvtId() {
        return ttvtId;
    }

    public void setTtvtId(String ttvtId) {
        this.ttvtId = ttvtId;
    }

    public String getToktId() {
        return toktId;
    }

    public void setToktId(String toktId) {
        this.toktId = toktId;
    }

    public String getTramtbId() {
        return tramtbId;
    }

    public void setTramtbId(String tramtbId) {
        this.tramtbId = tramtbId;
    }

    public String getOltId() {
        return oltId;
    }

    public void setOltId(String oltId) {
        this.oltId = oltId;
    }

    public String getCardOltId() {
        return cardOltId;
    }

    public void setCardOltId(String cardOltId) {
        this.cardOltId = cardOltId;
    }

    public String getLstPortVlId() {
        return lstPortVlId;
    }

    public void setLstPortVlId(String lstPortVlId) {
        this.lstPortVlId = lstPortVlId;
    }

    public String getKyHieu() {
        return kyHieu;
    }

    public void setKyHieu(String kyHieu) {
        this.kyHieu = kyHieu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
