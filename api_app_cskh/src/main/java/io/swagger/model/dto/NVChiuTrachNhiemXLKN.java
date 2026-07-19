package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Nhân viên
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class NVChiuTrachNhiemXLKN extends BaseDTO {

    @JsonProperty("nhanVienId")
    private Long nhanVienId;
    @JsonProperty("donViId")
    private Long donViId;
    @JsonProperty("donViDLId")
    private Long donViDLId;
    @JsonProperty("doiTacId")
    private Long doiTacId;
    @JsonProperty("viTriId")
    private Long viTriId;
    @JsonProperty("hthdId")
    private Long hthdId;
    @JsonProperty("tenNv")
    private String tenNv;
    @JsonProperty("maNv")
    private String maNv;
    @JsonProperty("stt")
    private String stt;
    @JsonProperty("diaChiNV")
    private String diaChiNV;
    @JsonProperty("gioiTinh")
    private String gioiTinh;
    @JsonProperty("chucDanh")
    private String chucDanh;
    @JsonProperty("sdt")
    private String sdt;
    @JsonProperty("ngaySn")
    private String ngaySn;
    @JsonProperty("chuKy")
    private String chuKy;
    @JsonProperty("flag")
    private String flag;
    @JsonProperty("ghiChu")
    private String ghiChu;
    @JsonProperty("tienDC")
    private String tienDC;
    @JsonProperty("email")
    private String email;
    @JsonProperty("ngayLogin")
    private String ngayLogin;
    @JsonProperty("maOTP")
    private String maOTP;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("emailTmp")
    private String emailTmp;
    @JsonProperty("tenTN")
    private String tenTN;
    @JsonProperty("maNVVnp")
    private String maNVVnp;
    @JsonProperty("userVi")
    private String userVi;
    @JsonProperty("maThe")
    private String maThe;
    @JsonProperty("maKenh")
    private String maKenh;
    @JsonProperty("nhom")
    private String nhom;
    @JsonProperty("maDV")
    private String maDV;
    @JsonProperty("tenDV")
    private String tenDV;

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getDonViDLId() {
        return donViDLId;
    }

    public void setDonViDLId(Long donViDLId) {
        this.donViDLId = donViDLId;
    }

    public Long getDoiTacId() {
        return doiTacId;
    }

    public void setDoiTacId(Long doiTacId) {
        this.doiTacId = doiTacId;
    }

    public Long getViTriId() {
        return viTriId;
    }

    public void setViTriId(Long viTriId) {
        this.viTriId = viTriId;
    }

    public Long getHthdId() {
        return hthdId;
    }

    public void setHthdId(Long hthdId) {
        this.hthdId = hthdId;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getDiaChiNV() {
        return diaChiNV;
    }

    public void setDiaChiNV(String diaChiNV) {
        this.diaChiNV = diaChiNV;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgaySn() {
        return ngaySn;
    }

    public void setNgaySn(String ngaySn) {
        this.ngaySn = ngaySn;
    }

    public String getChuKy() {
        return chuKy;
    }

    public void setChuKy(String chuKy) {
        this.chuKy = chuKy;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTienDC() {
        return tienDC;
    }

    public void setTienDC(String tienDC) {
        this.tienDC = tienDC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgayLogin() {
        return ngayLogin;
    }

    public void setNgayLogin(String ngayLogin) {
        this.ngayLogin = ngayLogin;
    }

    public String getMaOTP() {
        return maOTP;
    }

    public void setMaOTP(String maOTP) {
        this.maOTP = maOTP;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmailTmp() {
        return emailTmp;
    }

    public void setEmailTmp(String emailTmp) {
        this.emailTmp = emailTmp;
    }

    public String getTenTN() {
        return tenTN;
    }

    public void setTenTN(String tenTN) {
        this.tenTN = tenTN;
    }

    public String getMaNVVnp() {
        return maNVVnp;
    }

    public void setMaNVVnp(String maNVVnp) {
        this.maNVVnp = maNVVnp;
    }

    public String getUserVi() {
        return userVi;
    }

    public void setUserVi(String userVi) {
        this.userVi = userVi;
    }

    public String getMaThe() {
        return maThe;
    }

    public void setMaThe(String maThe) {
        this.maThe = maThe;
    }

    public String getMaKenh() {
        return maKenh;
    }

    public void setMaKenh(String maKenh) {
        this.maKenh = maKenh;
    }

    public String getNhom() {
        return nhom;
    }

    public void setNhom(String nhom) {
        this.nhom = nhom;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }
}
