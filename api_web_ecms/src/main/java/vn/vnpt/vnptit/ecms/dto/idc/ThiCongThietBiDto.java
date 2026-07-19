package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThiCongThietBiDto {
    @JsonProperty(value = "thiCongThietBiId", required = false)
    private Long thiCongThietBiId;
    @JsonProperty(value = "thiCongHeThongId", required = false)
    private Long thiCongHeThongId;
    @JsonProperty(value = "thietBiId", required = false)
    private Long thietBiId;
    @JsonProperty(value = "phieuThiCongId", required = false)
    private Long phieuThiCongId;
    @JsonProperty(value = "ngayTh", required = false)
    private String ngayTh;
    @JsonProperty(value = "noiDung", required = false)
    private String noiDung;
    @JsonProperty(value = "trangThaiId", required = false)
    private Long trangThaiId;
    @JsonProperty(value = "ghiChu", required = false)
    private String ghiChu;
    @JsonProperty(value = "moTa", required = false)
    private String moTa;

    public ThiCongThietBiDto() {
    }

    public ThiCongThietBiDto(Long thiCongThietBiId, Long thiCongHeThongId, Long thietBiId, Long phieuThiCongId,
            String ngayTh, String noiDung, Long trangThaiId, String ghiChu, String moTa) {
        this.thiCongThietBiId = thiCongThietBiId;
        this.thiCongHeThongId = thiCongHeThongId;
        this.thietBiId = thietBiId;
        this.phieuThiCongId = phieuThiCongId;
        this.ngayTh = ngayTh;
        this.noiDung = noiDung;
        this.trangThaiId = trangThaiId;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
    }

    public Long getThiCongThietBiId() {
        return thiCongThietBiId;
    }

    public void setThiCongThietBiId(Long thiCongThietBiId) {
        this.thiCongThietBiId = thiCongThietBiId;
    }

    public Long getThiCongHeThongId() {
        return thiCongHeThongId;
    }

    public void setThiCongHeThongId(Long thiCongHeThongId) {
        this.thiCongHeThongId = thiCongHeThongId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public Long getPhieuThiCongId() {
        return phieuThiCongId;
    }

    public void setPhieuThiCongId(Long phieuThiCongId) {
        this.phieuThiCongId = phieuThiCongId;
    }

    public String getNgayTh() {
        return ngayTh;
    }

    public void setNgayTh(String ngayTh) {
        this.ngayTh = ngayTh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
