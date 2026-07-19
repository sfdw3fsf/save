package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KetNoiKenhBanDto {
    private Long idPhieuTc;
    private String maTb;
    @JsonProperty("trangthai_phieuYC")
    private Integer trangThaiPhieuYC;
    private Integer loaicv;

    public Long getIdPhieuTc() {
        return idPhieuTc;
    }

    public String getMaTb() {
        return maTb;
    }

    public Integer getTrangThaiPhieuYC() {
        return trangThaiPhieuYC;
    }
    public Integer getLoaicv() {
        return loaicv;
    }
}
