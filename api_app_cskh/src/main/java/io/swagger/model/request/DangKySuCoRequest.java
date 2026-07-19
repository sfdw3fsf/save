package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;
import java.sql.Date;

/**
 * Đăng ký sự cố
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DangKySuCoRequest {

    @JsonProperty("maSc")
    private String maSc;

    @JsonProperty("nguonScId")
    private Long nguonScId;

    @JsonProperty("nhomScId")
    private Long nhomScId;

    @JsonProperty("ngaySc")
    private String ngaySc;

    @JsonProperty("noiDungSc")
    private String noiDungSc;

    @JsonProperty("ngayBh")
    private String ngayBh;

    @JsonProperty("donViBh")
    private Long donViBh;

    @JsonProperty("nhanVienBh")
    private Long nhanVienBh;

    @JsonProperty("dienThoaiLh")
    private String dienThoaiLh;

    @JsonProperty("ngayTn")
    private String ngayTn;

    @JsonProperty("donViTn")
    private Long donViTn;

    @JsonProperty("nhanVienTn")
    private Long nhanVienTn;


    @JsonProperty("henSuaTu")
    private String henSuaTu;


    @JsonProperty("henSuaDen")
    private String henSuaDen;

    @JsonProperty("ngayCn")
    private String ngayCn;

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
    }

    public Long getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Long nhomScId) {
        this.nhomScId = nhomScId;
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

    public String getNgayTn() {
        return ngayTn;
    }

    public void setNgayTn(String ngayTn) {
        this.ngayTn = ngayTn;
    }

    public Long getDonViTn() {
        return donViTn;
    }

    public void setDonViTn(Long donViTn) {
        this.donViTn = donViTn;
    }

    public Long getNhanVienTn() {
        return nhanVienTn;
    }

    public void setNhanVienTn(Long nhanVienTn) {
        this.nhanVienTn = nhanVienTn;
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

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }
}
