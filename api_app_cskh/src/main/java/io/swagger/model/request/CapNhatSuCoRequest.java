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
public class CapNhatSuCoRequest extends BaseDTO {
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("ngaySc")
    private String ngaySc = null;
    @JsonProperty("noiDungSc")
    private String noiDungSc = null;
    @JsonProperty("ngayBh")
    private String ngayBh = null;
    @JsonProperty("donViBh")
    private Long donViBh = null;
    @JsonProperty("nhanVienBh")
    private Long nhanVienBh = null;
    @JsonProperty("dienThoaiLh")
    private String dienThoaiLh = null;
    @JsonProperty("henSuaTu")
    private String henSuaTu = null;
    @JsonProperty("henSuaDen")
    private String henSuaDen = null;
    @JsonProperty("tinhTrang")
    private String tinhTrang = null;

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getNgaySc() {
        return ngaySc;
    }

    public void setNgaySc(String ngaySc) {
        this.ngaySc = ngaySc;
    }

    public String getNoiDungSc() {
        return noiDungSc;
    }

    public void setNoiDungSc(String noiDungSc) {
        this.noiDungSc = noiDungSc;
    }

    public String getNgayBh() {
        return ngayBh;
    }

    public void setNgayBh(String ngayBh) {
        this.ngayBh = ngayBh;
    }

    public Long getDonViBh() {
        return donViBh;
    }

    public void setDonViBh(Long donViBh) {
        this.donViBh = donViBh;
    }

    public Long getNhanVienBh() {
        return nhanVienBh;
    }

    public void setNhanVienBh(Long nhanVienBh) {
        this.nhanVienBh = nhanVienBh;
    }

    public String getDienThoaiLh() {
        return dienThoaiLh;
    }

    public void setDienThoaiLh(String dienThoaiLh) {
        this.dienThoaiLh = dienThoaiLh;
    }

    public String getHenSuaTu() {
        return henSuaTu;
    }

    public void setHenSuaTu(String henSuaTu) {
        this.henSuaTu = henSuaTu;
    }

    public String getHenSuaDen() {
        return henSuaDen;
    }

    public void setHenSuaDen(String henSuaDen) {
        this.henSuaDen = henSuaDen;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
