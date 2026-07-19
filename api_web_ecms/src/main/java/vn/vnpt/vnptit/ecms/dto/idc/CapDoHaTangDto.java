package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class CapDoHaTangDto {
    @JsonProperty("id")
    public Long capDoHaTangId;
    @JsonProperty("ten")
    public String ten;
    @JsonProperty("tentat")
    public String tenTat;
    @JsonProperty("mota")
    public String moTa;
    @JsonProperty("ghichu")
    public String ghiChu;
    @JsonProperty("sudung")
    public Integer sudung;

    public void isValid() {
        boolean isValid = (getTen() != null && !getTen().trim().isEmpty());

        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: Tên");
        }
    }

    public Long getCapDoHaTangId() {
        return capDoHaTangId;
    }

    public void setCapDoHaTangId(Long capDoHaTangId) {
        this.capDoHaTangId = capDoHaTangId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getSudung() {
        return sudung;
    }

    public void setSudung(Integer sudung) {
        this.sudung = sudung;
    }
}
