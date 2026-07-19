package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PhieuKN {
    @JsonProperty("stt")
    private String stt = null;
    @JsonProperty("maKN")
    private String maKN = null;
    @JsonProperty("phieuKNId")
    private Long phieuKNId = null;
    @JsonProperty("phieuChaId")
    private Long phieuChaId = null;
    @JsonProperty("loaiTBId")
    private Long loaiTBId = null;
    @JsonProperty("dvGiaoId")
    private Long dvGiaoId = null;
    @JsonProperty("maTB")
    private String maTB = null;
    @JsonProperty("tenTB")
    private String tenTB = null;
    @JsonProperty("diaChiTB")
    private String diaChiTB = null;
    @JsonProperty("loaiHinhTB")
    private String loaiHinhTB = null;
    @JsonProperty("ngayGiao")
    private String ngayGiao = null;
    @JsonProperty("ngayTN")
    private String ngayTN = null;
    @JsonProperty("nguoiTN")
    private String nguoiTN = null;
    @JsonProperty("nguoiKN")
    private String nguoiKN = null;
    @JsonProperty("henKQTu")
    private String henKQTu = null;
    @JsonProperty("henKQDen")
    private String henKQDen = null;
    @JsonProperty("ghiChuKN")
    private String ghiChuKN = null;
    @JsonProperty("noiDungKN")
    private String noiDungKN = null;
    @JsonProperty("dvGiao")
    private String dvGiao = null;
    @JsonProperty("nvGiao")
    private String nvGiao = null;

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getMaKN() {
        return maKN;
    }

    public void setMaKN(String maKN) {
        this.maKN = maKN;
    }

    public Long getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(Long phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public Long getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(Long phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public Long getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(Long loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public Long getDvGiaoId() {
        return dvGiaoId;
    }

    public void setDvGiaoId(Long dvGiaoId) {
        this.dvGiaoId = dvGiaoId;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getDiaChiTB() {
        return diaChiTB;
    }

    public void setDiaChiTB(String diaChiTB) {
        this.diaChiTB = diaChiTB;
    }

    public String getLoaiHinhTB() {
        return loaiHinhTB;
    }

    public void setLoaiHinhTB(String loaiHinhTB) {
        this.loaiHinhTB = loaiHinhTB;
    }

    public String getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public String getNgayTN() {
        return ngayTN;
    }

    public void setNgayTN(String ngayTN) {
        this.ngayTN = ngayTN;
    }

    public String getNguoiTN() {
        return nguoiTN;
    }

    public void setNguoiTN(String nguoiTN) {
        this.nguoiTN = nguoiTN;
    }

    public String getNguoiKN() {
        return nguoiKN;
    }

    public void setNguoiKN(String nguoiKN) {
        this.nguoiKN = nguoiKN;
    }

    public String getHenKQTu() {
        return henKQTu;
    }

    public void setHenKQTu(String henKQTu) {
        this.henKQTu = henKQTu;
    }

    public String getHenKQDen() {
        return henKQDen;
    }

    public void setHenKQDen(String henKQDen) {
        this.henKQDen = henKQDen;
    }

    public String getGhiChuKN() {
        return ghiChuKN;
    }

    public void setGhiChuKN(String ghiChuKN) {
        this.ghiChuKN = ghiChuKN;
    }

    public String getNoiDungKN() {
        return noiDungKN;
    }

    public void setNoiDungKN(String noiDungKN) {
        this.noiDungKN = noiDungKN;
    }

    public String getDvGiao() {
        return dvGiao;
    }

    public void setDvGiao(String dvGiao) {
        this.dvGiao = dvGiao;
    }

    public String getNvGiao() {
        return nvGiao;
    }

    public void setNvGiao(String nvGiao) {
        this.nvGiao = nvGiao;
    }
}
