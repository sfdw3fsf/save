package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DichVuBackupDto implements Serializable {

    @JsonProperty("id_dich_vu")
    private Long idDichVu;

    @JsonProperty("ma_dich_vu")
    private String maDichVu;

    @JsonProperty("hieu_luc")
    private Long hieuLuc;

    @JsonProperty("dung_luong")
    private Long dungLuong;

    @JsonProperty("ngay_het_han")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date ngayHetHan;

    @JsonProperty("tinh_phi")
    private Long tinhPhi;

    @JsonProperty("kieu_capphat_id")
    private Long kieuCapphatId;

    @JsonProperty("trangthai_id")
    private Long trangthaiId;

    @JsonProperty("ghi_chu")
    private String ghiChu;

    @JsonProperty("donvi_ql_id")
    private Long donviQlId;

    @JsonProperty("phong_ql_id")
    private Long phongQlId;

    @JsonProperty("canhan_ql_id")
    private Long canhanQlId;

    @JsonProperty("chuquan_dv_id")
    private Long chuquanDvId;

    @JsonProperty("cum_ht_dich_id")
    private Long cumHtDichId;

    @JsonProperty("ip_dich_id")
    private Long ipDich;

    @JsonProperty("ip_dich_text")
    private String ipDichText;

    @JsonProperty("duong_dan_dich")
    private String duongDanDich;

    @JsonProperty("ds_nguon_backup")
    private List<NguonBackupDto> nguonBackup;

    public DichVuBackupDto() {
    }

    public Long getIdDichVu() {
        return idDichVu;
    }

    public void setIdDichVu(Long idDichVu) {
        this.idDichVu = idDichVu;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public Long getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Long hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Long getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(Long dungLuong) {
        this.dungLuong = dungLuong;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public Long getTinhPhi() {
        return tinhPhi;
    }

    public void setTinhPhi(Long tinhPhi) {
        this.tinhPhi = tinhPhi;
    }

    public Long getKieuCapphatId() {
        return kieuCapphatId;
    }

    public void setKieuCapphatId(Long kieuCapphatId) {
        this.kieuCapphatId = kieuCapphatId;
    }

    public Long getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(Long trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getDonviQlId() {
        return donviQlId;
    }

    public void setDonviQlId(Long donviQlId) {
        this.donviQlId = donviQlId;
    }

    public Long getPhongQlId() {
        return phongQlId;
    }

    public void setPhongQlId(Long phongQlId) {
        this.phongQlId = phongQlId;
    }

    public Long getCanhanQlId() {
        return canhanQlId;
    }

    public void setCanhanQlId(Long canhanQlId) {
        this.canhanQlId = canhanQlId;
    }

    public Long getChuquanDvId() {
        return chuquanDvId;
    }

    public void setChuquanDvId(Long chuquanDvId) {
        this.chuquanDvId = chuquanDvId;
    }

    public Long getCumHtDichId() {
        return cumHtDichId;
    }

    public void setCumHtDichId(Long cumHtDichId) {
        this.cumHtDichId = cumHtDichId;
    }

    public Long getIpDich() {
        return ipDich;
    }

    public void setIpDich(Long ipDich) {
        this.ipDich = ipDich;
    }

    public String getIpDichText() {
        return ipDichText;
    }

    public void setIpDichText(String ipDichText) {
        this.ipDichText = ipDichText;
    }

    public String getDuongDanDich() {
        return duongDanDich;
    }

    public void setDuongDanDich(String duongDanDich) {
        this.duongDanDich = duongDanDich;
    }

    public List<NguonBackupDto> getNguonBackup() {
        return nguonBackup;
    }

    public void setNguonBackup(List<NguonBackupDto> nguonBackup) {
        this.nguonBackup = nguonBackup;
    }
}
