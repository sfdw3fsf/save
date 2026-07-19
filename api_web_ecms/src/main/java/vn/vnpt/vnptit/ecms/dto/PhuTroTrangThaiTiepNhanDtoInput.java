package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class PhuTroTrangThaiTiepNhanDtoInput {
    private Integer kieu;
    private Long vanhanh_id;
    private String ma_vh;
    private Long loaivh_id;
    private Long thietbi_id;
    private String noidung;
    private String ngay_yc;
    private Long nhanvien_id;
    private Long donvi_id;
    private String ketqua_td;
    private String ketqua;
    private Long ttvh_id;
    private String ngay_ht;
    private String ghichu;
    private String ngay_cn;
    private String nguoi_cn;
    private String may_cn;
    private String ip_cn;
    private String dexuat;
    private String mota;
    private Long phieuvh_id;
    private Long donvi_nhan_id;

    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    public Long getVanhanh_id() {
        return vanhanh_id;
    }

    public void setVanhanh_id(Long vanhanh_id) {
        this.vanhanh_id = vanhanh_id;
    }

    public String getMa_vh() {
        return ma_vh;
    }

    public void setMa_vh(String ma_vh) {
        this.ma_vh = ma_vh;
    }

    public Long getLoaivh_id() {
        return loaivh_id;
    }

    public void setLoaivh_id(Long loaivh_id) {
        this.loaivh_id = loaivh_id;
    }

    public Long getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Long thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgay_yc() {
        return ngay_yc;
    }

    public void setNgay_yc(String ngay_yc) {
        this.ngay_yc = ngay_yc;
    }

    public Long getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(Long nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public Long getDonvi_id() {
        return donvi_id;
    }

    public void setDonvi_id(Long donvi_id) {
        this.donvi_id = donvi_id;
    }

    public String getKetqua_td() {
        return ketqua_td;
    }

    public void setKetqua_td(String ketqua_td) {
        this.ketqua_td = ketqua_td;
    }

    public String getKetqua() {
        return ketqua;
    }

    public void setKetqua(String ketqua) {
        this.ketqua = ketqua;
    }

    public Long getTtvh_id() {
        return ttvh_id;
    }

    public void setTtvh_id(Long ttvh_id) {
        this.ttvh_id = ttvh_id;
    }

    public String getNgay_ht() {
        return ngay_ht;
    }

    public void setNgay_ht(String ngay_ht) {
        this.ngay_ht = ngay_ht;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getNgay_cn() {
        return ngay_cn;
    }

    public void setNgay_cn(String ngay_cn) {
        this.ngay_cn = ngay_cn;
    }

    public String getNguoi_cn() {
        return nguoi_cn;
    }

    public void setNguoi_cn(String nguoi_cn) {
        this.nguoi_cn = nguoi_cn;
    }

    public String getMay_cn() {
        return may_cn;
    }

    public void setMay_cn(String may_cn) {
        this.may_cn = may_cn;
    }

    public String getIp_cn() {
        return ip_cn;
    }

    public void setIp_cn(String ip_cn) {
        this.ip_cn = ip_cn;
    }

    public String getDexuat() {
        return dexuat;
    }

    public void setDexuat(String dexuat) {
        this.dexuat = dexuat;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Long getPhieuvh_id() {
        return phieuvh_id;
    }

    public void setPhieuvh_id(Long phieuvh_id) {
        this.phieuvh_id = phieuvh_id;
    }

    public Long getDonvi_nhan_id() {
        return donvi_nhan_id;
    }

    public void setDonvi_nhan_id(Long donvi_nhan_id) {
        this.donvi_nhan_id = donvi_nhan_id;
    }
}
