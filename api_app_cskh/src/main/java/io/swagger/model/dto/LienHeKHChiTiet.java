package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Schema
public class LienHeKHChiTiet extends BaseDTO {
    @JsonProperty("kieu")
    private Long kieu = null;
    @JsonProperty("thueBaoId")
    private Long thueBaoId = null;

    @JsonProperty("ttlhId")
    private Long ttlhId = null;
    @JsonProperty("tenLh")
    private String tenLh = null;
    @JsonProperty("diaChi")
    private String diaChi = null;
    @JsonProperty("vaiTroId")
    private Long vaiTroId = null;
    @JsonProperty("tenVaiTro")
    private String tenVaiTro = null;

    @JsonProperty("dsKhachHang")
    private List<DanhMuc> dsKhachHang = new ArrayList<>();
    @JsonProperty("dsThanhToan")
    private List<DanhMuc> dsThanhToan = new ArrayList<>();
    @JsonProperty("dsThueBao")
    private List<DanhMuc> dsThueBao = new ArrayList<>();

    @JsonProperty("dienThoaiLoaiId")
    private Long dienThoaiLoaiId = null;
    @JsonProperty("dienThoaiTenLoai")
    private String dienThoaiTenLoai = null;
    @JsonProperty("dienThoaiGiaTri")
    private String dienThoaiGiaTri = null;
    @JsonProperty("dienThoaiDSMucDich")
    private List<DanhMuc> dienThoaiDSMucDich = new ArrayList<>();

    @JsonProperty("facebookGiaTri")
    private String facebookGiaTri = null;
    @JsonProperty("facebookDSMucDich")
    private List<DanhMuc> facebookDSMucDich = new ArrayList<>();

    @JsonProperty("zaloGiaTri")
    private String zaloGiaTri = null;
    @JsonProperty("zaloDSMucDich")
    private List<DanhMuc> zaloDSMucDich = new ArrayList<>();

    @JsonProperty("emailGiaTri")
    private String emailGiaTri = null;
    @JsonProperty("emailDSMucDich")
    private List<DanhMuc> emailDSMucDich = new ArrayList<>();

    public Long getKieu() {
        return kieu;
    }

    public void setKieu(Long kieu) {
        this.kieu = kieu;
    }

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public Long getTtlhId() {
        return ttlhId;
    }

    public void setTtlhId(Long ttlhId) {
        this.ttlhId = ttlhId;
    }

    public String getTenLh() {
        return tenLh;
    }

    public void setTenLh(String tenLh) {
        this.tenLh = tenLh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Long getVaiTroId() {
        return vaiTroId;
    }

    public void setVaiTroId(Long vaiTroId) {
        this.vaiTroId = vaiTroId;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public List<DanhMuc> getDsKhachHang() {
        return dsKhachHang;
    }

    public void setDsKhachHang(List<DanhMuc> dsKhachHang) {
        this.dsKhachHang = dsKhachHang;
    }

    public List<DanhMuc> getDsThanhToan() {
        return dsThanhToan;
    }

    public void setDsThanhToan(List<DanhMuc> dsThanhToan) {
        this.dsThanhToan = dsThanhToan;
    }

    public List<DanhMuc> getDsThueBao() {
        return dsThueBao;
    }

    public void setDsThueBao(List<DanhMuc> dsThueBao) {
        this.dsThueBao = dsThueBao;
    }

    public Long getDienThoaiLoaiId() {
        return dienThoaiLoaiId;
    }

    public void setDienThoaiLoaiId(Long dienThoaiLoaiId) {
        this.dienThoaiLoaiId = dienThoaiLoaiId;
    }

    public String getDienThoaiTenLoai() {
        return dienThoaiTenLoai;
    }

    public void setDienThoaiTenLoai(String dienThoaiTenLoai) {
        this.dienThoaiTenLoai = dienThoaiTenLoai;
    }

    public String getDienThoaiGiaTri() {
        return dienThoaiGiaTri;
    }

    public void setDienThoaiGiaTri(String dienThoaiGiaTri) {
        this.dienThoaiGiaTri = dienThoaiGiaTri;
    }

    public List<DanhMuc> getDienThoaiDSMucDich() {
        return dienThoaiDSMucDich;
    }

    public void setDienThoaiDSMucDich(List<DanhMuc> dienThoaiDSMucDich) {
        this.dienThoaiDSMucDich = dienThoaiDSMucDich;
    }

    public String getFacebookGiaTri() {
        return facebookGiaTri;
    }

    public void setFacebookGiaTri(String facebookGiaTri) {
        this.facebookGiaTri = facebookGiaTri;
    }

    public List<DanhMuc> getFacebookDSMucDich() {
        return facebookDSMucDich;
    }

    public void setFacebookDSMucDich(List<DanhMuc> facebookDSMucDich) {
        this.facebookDSMucDich = facebookDSMucDich;
    }

    public String getZaloGiaTri() {
        return zaloGiaTri;
    }

    public void setZaloGiaTri(String zaloGiaTri) {
        this.zaloGiaTri = zaloGiaTri;
    }

    public List<DanhMuc> getZaloDSMucDich() {
        return zaloDSMucDich;
    }

    public void setZaloDSMucDich(List<DanhMuc> zaloDSMucDich) {
        this.zaloDSMucDich = zaloDSMucDich;
    }

    public String getEmailGiaTri() {
        return emailGiaTri;
    }

    public void setEmailGiaTri(String emailGiaTri) {
        this.emailGiaTri = emailGiaTri;
    }

    public List<DanhMuc> getEmailDSMucDich() {
        return emailDSMucDich;
    }

    public void setEmailDSMucDich(List<DanhMuc> emailDSMucDich) {
        this.emailDSMucDich = emailDSMucDich;
    }
}
