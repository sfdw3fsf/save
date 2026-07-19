package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class NguonBackupDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("ten_doi_tuong")
    private String tenDoiTuong;

    @JsonProperty("loai_doi_tuong_id")
    private Long loaiDoiTuongId;

    @JsonProperty("cum_hatang_id")
    private Long cumHatangId;

    @JsonProperty("nguon_bk_khac")
    private String nguonBkKhac;

    @JsonProperty("idc_id")
    private Long idcId;

    @JsonProperty("ip_nguon_id")
    private Long ipNguonId;

    @JsonProperty("ip_nguon_text")
    private String ipNguonText;

    @JsonProperty("loai_dulieu_id")
    private Long loaiDuLieuId;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("duong_dan_nguon")
    private String duongDanNguon;

    @JsonProperty("tan_suat_id")
    private Long tanSuatId;

    @JsonProperty("thoi_gian")
    private String thoiGian;

    @JsonProperty("mayao_id")
    private Long mayaoId;

    @JsonProperty("dichvu_hatang_id")
    private Long dichvuHatangId;

    @JsonProperty("thietbi_id")
    private Long thietbiId;

    @JsonProperty("ngay_bat_dau")
    private String ngayBatDau;

    @JsonProperty("chu_ky")
    private Integer chuKy;

    @JsonProperty("lich_backup_id")
    private Long lichBackupId;

    public NguonBackupDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenDoiTuong() {
        return tenDoiTuong;
    }

    public void setTenDoiTuong(String tenDoiTuong) {
        this.tenDoiTuong = tenDoiTuong;
    }

    public Long getLoaiDoiTuongId() {
        return loaiDoiTuongId;
    }

    public void setLoaiDoiTuongId(Long loaiDoiTuongId) {
        this.loaiDoiTuongId = loaiDoiTuongId;
    }

    public Long getCumHatangId() {
        return cumHatangId;
    }

    public void setCumHatangId(Long cumHatangId) {
        this.cumHatangId = cumHatangId;
    }

    public String getNguonBkKhac() {
        return nguonBkKhac;
    }

    public void setNguonBkKhac(String nguonBkKhac) {
        this.nguonBkKhac = nguonBkKhac;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getIpNguonId() {
        return ipNguonId;
    }

    public void setIpNguonId(Long ipNguonId) {
        this.ipNguonId = ipNguonId;
    }

    public String getIpNguonText() {
        return ipNguonText;
    }

    public void setIpNguonText(String ipNguonText) {
        this.ipNguonText = ipNguonText;
    }

    public Long getLoaiDuLieuId() {
        return loaiDuLieuId;
    }

    public void setLoaiDuLieuId(Long loaiDuLieuId) {
        this.loaiDuLieuId = loaiDuLieuId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDuongDanNguon() {
        return duongDanNguon;
    }

    public void setDuongDanNguon(String duongDanNguon) {
        this.duongDanNguon = duongDanNguon;
    }

    public Long getTanSuatId() {
        return tanSuatId;
    }

    public void setTanSuatId(Long tanSuatId) {
        this.tanSuatId = tanSuatId;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Long getMayaoId() {
        return mayaoId;
    }

    public void setMayaoId(Long mayaoId) {
        this.mayaoId = mayaoId;
    }

    public Long getDichvuHatangId() {
        return dichvuHatangId;
    }

    public void setDichvuHatangId(Long dichvuHatangId) {
        this.dichvuHatangId = dichvuHatangId;
    }

    public Long getThietbiId() {
        return thietbiId;
    }

    public void setThietbiId(Long thietbiId) {
        this.thietbiId = thietbiId;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Integer getChuKy() {
        return chuKy;
    }

    public void setChuKy(Integer chuKy) {
        this.chuKy = chuKy;
    }

    public Long getLichBackupId() {
        return lichBackupId;
    }

    public void setLichBackupId(Long lichBackupId) {
        this.lichBackupId = lichBackupId;
    }
}
