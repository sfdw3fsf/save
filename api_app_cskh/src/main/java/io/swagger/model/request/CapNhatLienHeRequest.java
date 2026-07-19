package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CapNhatLienHeRequest extends BaseDTO {
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

    @JsonProperty("dsKhachHangId")
    private List<Long> dsKhachHangId = new ArrayList<>();
    @JsonProperty("dsThanhToanId")
    private List<Long> dsThanhToanId = new ArrayList<>();
    @JsonProperty("dsThueBaoId")
    private List<Long> dsThueBaoId = new ArrayList<>();

    @JsonProperty("dienThoaiLoaiId")
    private Long dienThoaiLoaiId = null;
    @JsonProperty("dienThoaiGiaTri")
    private String dienThoaiGiaTri = null;
    @JsonProperty("dienThoaiDSMucDichId")
    private List<Long> dienThoaiDSMucDichId = new ArrayList<>();

    @JsonProperty("facebookGiaTri")
    private String facebookGiaTri = null;
    @JsonProperty("facebookDSMucDichId")
    private List<Long> facebookDSMucDichId = new ArrayList<>();

    @JsonProperty("zaloGiaTri")
    private String zaloGiaTri = null;
    @JsonProperty("zaloDSMucDichId")
    private List<Long> zaloDSMucDichId = new ArrayList<>();

    @JsonProperty("emailGiaTri")
    private String emailGiaTri = null;
    @JsonProperty("emailDSMucDichId")
    private List<Long> emailDSMucDichId = new ArrayList<>();

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

    public List<Long> getDsKhachHangId() {
        return dsKhachHangId;
    }

    public void setDsKhachHangId(List<Long> dsKhachHangId) {
        this.dsKhachHangId = dsKhachHangId;
    }

    public List<Long> getDsThanhToanId() {
        return dsThanhToanId;
    }

    public void setDsThanhToanId(List<Long> dsThanhToanId) {
        this.dsThanhToanId = dsThanhToanId;
    }

    public List<Long> getDsThueBaoId() {
        return dsThueBaoId;
    }

    public void setDsThueBaoId(List<Long> dsThueBaoId) {
        this.dsThueBaoId = dsThueBaoId;
    }

    public Long getDienThoaiLoaiId() {
        return dienThoaiLoaiId;
    }

    public void setDienThoaiLoaiId(Long dienThoaiLoaiId) {
        this.dienThoaiLoaiId = dienThoaiLoaiId;
    }

    public String getDienThoaiGiaTri() {
        return dienThoaiGiaTri;
    }

    public void setDienThoaiGiaTri(String dienThoaiGiaTri) {
        this.dienThoaiGiaTri = dienThoaiGiaTri;
    }

    public List<Long> getDienThoaiDSMucDichId() {
        return dienThoaiDSMucDichId;
    }

    public void setDienThoaiDSMucDichId(List<Long> dienThoaiDSMucDichId) {
        this.dienThoaiDSMucDichId = dienThoaiDSMucDichId;
    }

    public String getFacebookGiaTri() {
        return facebookGiaTri;
    }

    public void setFacebookGiaTri(String facebookGiaTri) {
        this.facebookGiaTri = facebookGiaTri;
    }

    public List<Long> getFacebookDSMucDichId() {
        return facebookDSMucDichId;
    }

    public void setFacebookDSMucDichId(List<Long> facebookDSMucDichId) {
        this.facebookDSMucDichId = facebookDSMucDichId;
    }

    public String getZaloGiaTri() {
        return zaloGiaTri;
    }

    public void setZaloGiaTri(String zaloGiaTri) {
        this.zaloGiaTri = zaloGiaTri;
    }

    public List<Long> getZaloDSMucDichId() {
        return zaloDSMucDichId;
    }

    public void setZaloDSMucDichId(List<Long> zaloDSMucDichId) {
        this.zaloDSMucDichId = zaloDSMucDichId;
    }

    public String getEmailGiaTri() {
        return emailGiaTri;
    }

    public void setEmailGiaTri(String emailGiaTri) {
        this.emailGiaTri = emailGiaTri;
    }

    public List<Long> getEmailDSMucDichId() {
        return emailDSMucDichId;
    }

    public void setEmailDSMucDichId(List<Long> emailDSMucDichId) {
        this.emailDSMucDichId = emailDSMucDichId;
    }
}
