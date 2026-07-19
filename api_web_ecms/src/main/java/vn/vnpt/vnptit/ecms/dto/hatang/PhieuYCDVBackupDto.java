package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PhieuYCDVBackupDto {
    private Long phieuycId;
    private Integer kieuBk;
    private Long cumhtBkId;
    private String ipD;
    private Long ipDId;
    private String duongdanD;
    private Long dungluongHt;
    private Long dungluongYc;
    private String nguoiCn;
    private Long idDichVu;
    private Long kieuCapphatId;
    private Long cumHatangBackupId;
    private Long loaiDoiTuongNguon;
    private String nguonBackupSearch;
    private String dichBackup;
    private String ipNguon;
    private Long loaiDuLieuBackup;
    private Long trangthaiId;
    private Long cumHatangNguon;
    private String tanSuatBackup;
    private Integer tinhPhi;
    private Long chuquanDvId;
    private Long donviQlId;
    private Long canhanQlId;
    private String ngayHetHan;

    @JsonProperty("ds_nguon_backup")
    private List<NguonBackupDto> dsNguonBackup;

    public PhieuYCDVBackupDto() {
    }

    public List<NguonBackupDto> getDsNguonBackup() {
        return dsNguonBackup;
    }

    public void setDsNguonBackup(List<NguonBackupDto> dsNguonBackup) {
        this.dsNguonBackup = dsNguonBackup;
    }

    public Long getPhieuycId() {
        return phieuycId;
    }

    public void setPhieuycId(Long phieuycId) {
        this.phieuycId = phieuycId;
    }

    public Integer getKieuBk() {
        return kieuBk;
    }

    public void setKieuBk(Integer kieuBk) {
        this.kieuBk = kieuBk;
    }

    public Long getCumhtBkId() {
        return cumhtBkId;
    }

    public void setCumhtBkId(Long cumhtBkId) {
        this.cumhtBkId = cumhtBkId;
    }

    public String getIpD() {
        return ipD;
    }

    public void setIpD(String ipD) {
        this.ipD = ipD;
    }

    public Long getIpDId() {
        return ipDId;
    }

    public void setIpDId(Long ipDId) {
        this.ipDId = ipDId;
    }

    public String getDuongdanD() {
        return duongdanD;
    }

    public void setDuongdanD(String duongdanD) {
        this.duongdanD = duongdanD;
    }

    public Long getDungluongHt() {
        return dungluongHt;
    }

    public void setDungluongHt(Long dungluongHt) {
        this.dungluongHt = dungluongHt;
    }

    public Long getDungluongYc() {
        return dungluongYc;
    }

    public void setDungluongYc(Long dungluongYc) {
        this.dungluongYc = dungluongYc;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Long getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(Long idDichVu) {
        this.idDichVu = idDichVu;
    }

    public Long getKieuCapphatId() {
        return kieuCapphatId;
    }

    public void setKieuCapphatId(Long kieuCapphatId) {
        this.kieuCapphatId = kieuCapphatId;
    }

    public Long getCumHatangBackupId() {
        return cumHatangBackupId;
    }

    public void setCumHatangBackupId(Long cumHatangBackupId) {
        this.cumHatangBackupId = cumHatangBackupId;
    }

    public Long getLoaiDoiTuongNguon() {
        return loaiDoiTuongNguon;
    }

    public void setLoaiDoiTuongNguon(Long loaiDoiTuongNguon) {
        this.loaiDoiTuongNguon = loaiDoiTuongNguon;
    }

    public String getNguonBackupSearch() {
        return nguonBackupSearch;
    }

    public void setNguonBackupSearch(String nguonBackupSearch) {
        this.nguonBackupSearch = nguonBackupSearch;
    }

    public String getDichBackup() {
        return dichBackup;
    }

    public void setDichBackup(String dichBackup) {
        this.dichBackup = dichBackup;
    }

    public String getIpNguon() {
        return ipNguon;
    }

    public void setIpNguon(String ipNguon) {
        this.ipNguon = ipNguon;
    }

    public Long getLoaiDuLieuBackup() {
        return loaiDuLieuBackup;
    }

    public void setLoaiDuLieuBackup(Long loaiDuLieuBackup) {
        this.loaiDuLieuBackup = loaiDuLieuBackup;
    }

    public Long getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(Long trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

    public Long getCumHatangNguon() {
        return cumHatangNguon;
    }

    public void setCumHatangNguon(Long cumHatangNguon) {
        this.cumHatangNguon = cumHatangNguon;
    }

    public String getTanSuatBackup() {
        return tanSuatBackup;
    }

    public void setTanSuatBackup(String tanSuatBackup) {
        this.tanSuatBackup = tanSuatBackup;
    }

    public Integer getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Integer tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Long getChuquanDvId() {
        return chuquanDvId;
    }

    public void setChuquanDvId(Long chuquanDvId) {
        this.chuquanDvId = chuquanDvId;
    }

    public Long getDonviQlId() {
        return donviQlId;
    }

    public void setDonviQlId(Long donviQlId) {
        this.donviQlId = donviQlId;
    }

    public Long getCanhanQlId() {
        return canhanQlId;
    }

    public void setCanhanQlId(Long canhanQlId) {
        this.canhanQlId = canhanQlId;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
}