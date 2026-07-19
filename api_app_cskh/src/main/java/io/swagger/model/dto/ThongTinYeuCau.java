package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ThongTinYeuCau {
    @JsonProperty("maSc")
    private Long maSc;
    @JsonProperty("donViId")
    private Long donViId;
    @JsonProperty("maBuoc")
    private Long maBuoc;
    @JsonProperty("noiDungDeXuat")
    private String noiDungDeXuat;
    @JsonProperty("dsThietBi")
    private List<ThietBiYcHoTRo> dsThietBi;
    @JsonProperty("tenDonVi")
    private String tenDonVi;
    @JsonProperty("buocXuLy")
    private String buocXuLy;

    public Long getMaSc() {
        return maSc;
    }

    public void setMaSc(Long maSc) {
        this.maSc = maSc;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getMaBuoc() {
        return maBuoc;
    }

    public void setMaBuoc(Long maBuoc) {
        this.maBuoc = maBuoc;
    }

    public String getNoiDungDeXuat() {
        return noiDungDeXuat;
    }

    public void setNoiDungDeXuat(String noiDungDeXuat) {
        this.noiDungDeXuat = noiDungDeXuat;
    }

    public List<ThietBiYcHoTRo> getDsThietBi() {
        return dsThietBi;
    }

    public void setDsThietBi(List<ThietBiYcHoTRo> dsThietBi) {
        this.dsThietBi = dsThietBi;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        this.tenDonVi = tenDonVi;
    }

    public String getBuocXuLy() {
        return buocXuLy;
    }

    public void setBuocXuLy(String buocXuLy) {
        this.buocXuLy = buocXuLy;
    }
}
