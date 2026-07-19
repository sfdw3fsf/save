package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class LayDSPhieuGiaoXuLySuCoRequest extends BaseDTO {
    @JsonProperty("nguonScId")
    private Long nguonScId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("trangThai")
    private Long trangThai = null;
    @JsonProperty("mucDoScId")
    private Long mucDoScId = null;
    @JsonProperty("keoDai")
    private Long keoDai = null;
    @JsonProperty("huongGiaoId")
    private Long huongGiaoId = null;

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
}
