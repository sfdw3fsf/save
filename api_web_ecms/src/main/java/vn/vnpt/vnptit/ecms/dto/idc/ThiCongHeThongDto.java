package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThiCongHeThongDto {
    @JsonProperty(value = "thiCongHeThongId", required = false)
    private Long thiCongHeThongId;
    @JsonProperty(value = "phieuThiCongId", required = false)
    private Long phieuThiCongId;
    @JsonProperty(value = "heThongTbiId", required = false)
    private Long heThongTbiId;
    @JsonProperty(value = "nhanVienId", required = false)
    private Long nhanVienId;
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

    public ThiCongHeThongDto() {
    }

    public ThiCongHeThongDto(Long thiCongHeThongId, Long phieuThiCongId, Long heThongTbiId, Long nhanVienId,
            String ngayTh, String noiDung, Long trangThaiId, String ghiChu, String moTa) {
        this.thiCongHeThongId = thiCongHeThongId;
        this.phieuThiCongId = phieuThiCongId;
        this.heThongTbiId = heThongTbiId;
        this.nhanVienId = nhanVienId;
        this.ngayTh = ngayTh;
        this.noiDung = noiDung;
        this.trangThaiId = trangThaiId;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
    }

    public Long getThiCongHeThongId() {
        return thiCongHeThongId;
    }

    public void setThiCongHeThongId(Long thiCongHeThongId) {
        this.thiCongHeThongId = thiCongHeThongId;
    }

    public Long getPhieuThiCongId() {
        return phieuThiCongId;
    }

    public void setPhieuThiCongId(Long phieuThiCongId) {
        this.phieuThiCongId = phieuThiCongId;
    }

    public Long getHeThongTbiId() {
        return heThongTbiId;
    }

    public void setHeThongTbiId(Long heThongTbiId) {
        this.heThongTbiId = heThongTbiId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
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
