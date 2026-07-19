package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeThongThietBiKhachHangDto {
    @JsonProperty(value = "heThongTbiId", required = false)
    private Long heThongTbiId;
    @JsonProperty(value = "mangTbiId", required = false)
    private Long mangTbiId;
    @JsonProperty(value = "loaiKHId", required = false)
    private Long loaiKHId;
    @JsonProperty(value = "tenHeThong", required = false)
    private String tenHeThong;
    @JsonProperty(value = "ghiChu", required = false)
    private String ghiChu;
    @JsonProperty(value = "donViQLId", required = false)
    private Long donViQLId;
    @JsonProperty(value = "nguoiQLId", required = false)
    private Long nguoiQLId;
    @JsonProperty(value = "idcId", required = false)
    private Long idcId;
    @JsonProperty(value = "ngayLD", required = false)
    private Date ngayLD;
    @JsonProperty(value = "canCuYC", required = false)
    private String canCuYC;
    @JsonProperty(value = "trangThaiTSId", required = false)
    private String trangThaiTSId;
    @JsonProperty(value = "trangThaiVHId", required = false)
    private String trangThaiVHId;
    @JsonProperty(value = "trangThaiVanHanh", required = false)
    private String trangThaiVanHanh;
    @JsonProperty(value = "suDung", required = false)
    private String suDung;
    @JsonProperty(value = "moTa", required = false)
    private String moTa;
    @JsonProperty(value = "hdtbId", required = false)
    private Long hdtbId;
    @JsonProperty(value = "tinhId", required = false)
    private Long tinhId;
    @JsonProperty(value = "ngayThaoGo", required = false)
    private Date ngayThaoGo;

    public HeThongThietBiKhachHangDto() {
    }

    public HeThongThietBiKhachHangDto(Long heThongTbiId, Long mangTbiId, Long loaiKHId, String tenHeThong,
            String ghiChu,
            Long donViQLId, Long nguoiQLId, Long idcId, Date ngayLD, String canCuYC, String trangThaiTSId,
            String trangThaiVHId, String trangThaiVanHanh, String suDung, String moTa, Long hdtbId, Long tinhId, Date ngayThaoGo) {
        this.heThongTbiId = heThongTbiId;
        this.mangTbiId = mangTbiId;
        this.loaiKHId = loaiKHId;
        this.tenHeThong = tenHeThong;
        this.ghiChu = ghiChu;
        this.donViQLId = donViQLId;
        this.nguoiQLId = nguoiQLId;
        this.idcId = idcId;
        this.ngayLD = ngayLD;
        this.canCuYC = canCuYC;
        this.trangThaiTSId = trangThaiTSId;
        this.trangThaiVHId = trangThaiVHId;
        this.trangThaiVanHanh = trangThaiVanHanh;
        this.suDung = suDung;
        this.moTa = moTa;
        this.hdtbId = hdtbId;
        this.tinhId = tinhId;
        this.ngayThaoGo = ngayThaoGo;
    }

    public Long getHeThongTbiId() {
        return heThongTbiId;
    }

    public void setHeThongTbiId(Long heThongTbiId) {
        this.heThongTbiId = heThongTbiId;
    }

    public Long getMangTbiId() {
        return mangTbiId;
    }

    public void setMangTbiId(Long mangTbiId) {
        this.mangTbiId = mangTbiId;
    }

    public Long getLoaiKHId() {
        return loaiKHId;
    }

    public void setLoaiKHId(Long loaiKHId) {
        this.loaiKHId = loaiKHId;
    }

    public String getTenHeThong() {
        return tenHeThong;
    }

    public void setTenHeThong(String tenHeThong) {
        this.tenHeThong = tenHeThong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getDonViQLId() {
        return donViQLId;
    }

    public void setDonViQLId(Long donViQLId) {
        this.donViQLId = donViQLId;
    }

    public Long getNguoiQLId() {
        return nguoiQLId;
    }

    public void setNguoiQLId(Long nguoiQLId) {
        this.nguoiQLId = nguoiQLId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Date getNgayLD() {
        return ngayLD;
    }

    public void setNgayLD(Date ngayLD) {
        this.ngayLD = ngayLD;
    }

    public String getCanCuYC() {
        return canCuYC;
    }

    public void setCanCuYC(String canCuYC) {
        this.canCuYC = canCuYC;
    }

    public String getTrangThaiTSId() {
        return trangThaiTSId;
    }

    public void setTrangThaiTSId(String trangThaiTSId) {
        this.trangThaiTSId = trangThaiTSId;
    }

    public String getTrangThaiVHId() {
        return trangThaiVHId;
    }

    public void setTrangThaiVHId(String trangThaiVHId) {
        this.trangThaiVHId = trangThaiVHId;
    }

    public String getTrangThaiVanHanh() {
        return trangThaiVanHanh;
    }

    public void setTrangThaiVanHanh(String trangThaiVanHanh) {
        this.trangThaiVanHanh = trangThaiVanHanh;
    }

    public String getSuDung() {
        return suDung;
    }

    public void setSuDung(String suDung) {
        this.suDung = suDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getTinhId() {
        return tinhId;
    }

    public void setTinhId(Long tinhId) {
        this.tinhId = tinhId;
    }

    public Date getNgayThaoGo() {
        return ngayThaoGo;
    }

    public void setNgayThaoGo(Date ngayThaoGo) {
        this.ngayThaoGo = ngayThaoGo;
    }
}
