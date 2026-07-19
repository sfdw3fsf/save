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
public class LayDSPhieuNghiemThuXLRequest extends PageRequest {
    @JsonProperty("nhomScId")
    private Long nhomScId = null;
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("nguoiBaoId")
    private Long nguoiBaoId = null;
    @JsonProperty("nguonScId")
    private Long nguonScId = null;
    @JsonProperty("tinhTrang")
    private Long tinhTrang = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngayScTu")
    private String ngayScTu = null;
    @JsonProperty("ngayScDen")
    private String ngayScDen = null;
    @JsonProperty("ngayBaoTu")
    private String ngayBaoTu = null;
    @JsonProperty("ngayBaoDen")
    private String ngayBaoDen = null;
    @JsonProperty("mucDoScId")
    private Long mucDoScId = null;
    @JsonProperty("keoDaiTuOp")
    private Long keoDaiTuOp = null;
    @JsonProperty("keoDaiTu")
    private Long keoDaiTu = null;
    @JsonProperty("keoDaiDenOp")
    private Long keoDaiDenOp = null;
    @JsonProperty("keoDaiDen")
    private Long keoDaiDen = null;
    @JsonProperty("donViId")
    private Long donViId = null;
    @JsonProperty("maQuytrinh")
    private String maQuytrinh = "-1";

    public Long getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Long nhomScId) {
        this.nhomScId = nhomScId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public Long getNguoiBaoId() {
        return nguoiBaoId;
    }

    public void setNguoiBaoId(Long nguoiBaoId) {
        this.nguoiBaoId = nguoiBaoId;
    }

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
    }

    public Long getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(Long tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getNgayScTu() {
        return ngayScTu;
    }

    public void setNgayScTu(String ngayScTu) {
        this.ngayScTu = ngayScTu;
    }

    public String getNgayScDen() {
        return ngayScDen;
    }

    public void setNgayScDen(String ngayScDen) {
        this.ngayScDen = ngayScDen;
    }

    public String getNgayBaoTu() {
        return ngayBaoTu;
    }

    public void setNgayBaoTu(String ngayBaoTu) {
        this.ngayBaoTu = ngayBaoTu;
    }

    public String getNgayBaoDen() {
        return ngayBaoDen;
    }

    public void setNgayBaoDen(String ngayBaoDen) {
        this.ngayBaoDen = ngayBaoDen;
    }

    public Long getMucDoScId() {
        return mucDoScId;
    }

    public void setMucDoScId(Long mucDoScId) {
        this.mucDoScId = mucDoScId;
    }

    public Long getKeoDaiTuOp() {
        return keoDaiTuOp;
    }

    public void setKeoDaiTuOp(Long keoDaiTuOp) {
        this.keoDaiTuOp = keoDaiTuOp;
    }

    public Long getKeoDaiTu() {
        return keoDaiTu;
    }

    public void setKeoDaiTu(Long keoDaiTu) {
        this.keoDaiTu = keoDaiTu;
    }

    public Long getKeoDaiDenOp() {
        return keoDaiDenOp;
    }

    public void setKeoDaiDenOp(Long keoDaiDenOp) {
        this.keoDaiDenOp = keoDaiDenOp;
    }

    public Long getKeoDaiDen() {
        return keoDaiDen;
    }

    public void setKeoDaiDen(Long keoDaiDen) {
        this.keoDaiDen = keoDaiDen;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public String getMaQuytrinh() {
        return maQuytrinh;
    }

    public void setMaQuytrinh(String maQuytrinh) {
        this.maQuytrinh = maQuytrinh;
    }
}
