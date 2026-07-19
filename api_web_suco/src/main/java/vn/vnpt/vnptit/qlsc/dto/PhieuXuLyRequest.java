package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhieuXuLyRequest {
    @JsonProperty("nguonScId")
    private Long nguonScId;

    @JsonProperty("maSc")
    private String maSc;

    @JsonProperty("ngayGiao")
    private String ngayGiao;

    @JsonProperty("trangThai")
    private Long trangThai;

    @JsonProperty("mucDoScId")
    private Long mucDoScId;

    @JsonProperty("keoDai")
    private Long keoDai;

    @JsonProperty("huongGiaoId")
    private Long huongGiaoId;

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public Long getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Long trangThai) {
        this.trangThai = trangThai;
    }

    public Long getMucDoScId() {
        return mucDoScId;
    }

    public void setMucDoScId(Long mucDoScId) {
        this.mucDoScId = mucDoScId;
    }

    public Long getKeoDai() {
        return keoDai;
    }

    public void setKeoDai(Long keoDai) {
        this.keoDai = keoDai;
    }

    public Long getHuongGiaoId() {
        return huongGiaoId;
    }

    public void setHuongGiaoId(Long huongGiaoId) {
        this.huongGiaoId = huongGiaoId;
    }

    @JsonProperty("maQuytrinh")
    private String maQuytrinh;

    public String getMaQuytrinh() {
        return maQuytrinh;
    }

    public void setMaQuytrinh(String maQuytrinh) {
        this.maQuytrinh = maQuytrinh;
    }
}
