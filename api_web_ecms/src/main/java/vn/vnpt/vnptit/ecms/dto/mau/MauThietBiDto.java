package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_TBI (Mẫu thiết bị)
 */
public class MauThietBiDto {
    private Long mautbiId;              // MAUTBI_ID
    private Long mangtbiId;             // MANGTBI_ID
    private Long loaitbiId;             // LOAITBI_ID
    private Long phanloaitbiId;         // PHANLOAITBI_ID
    private String maMautbi;            // MA_MAUTBI
    private String tenMautbi;           // TEN_MAUTBI
    private Long slRackunit;            // SL_RACKUNIT
    private Long fullDepth;              // FULL_DEPTH
    private Long hangsxId;               // HANGSX_ID
    private Double csDinhdanh;             // CS_DINHDANH
    private String maVtu;                // MA_VTU
    private String hinhanh;              // HINHANH
    private Double daDinhdanh;             // DA_DINHDANH
    private Double ddDinhdanh;             // DD_DINHDANH
    private Double hsCosphi;               // HS_COSPHI
    private Long loaiNdien;              // LOAI_NDIEN
    private Long loaiMay;                // LOAI_MAY
    private Double chieudai;               // CHIEUDAI
    private Double chieurong;               // CHIEURONG
    private Double chieucao;                // CHIEUCAO
    private String cauhinh;               // CAUHINH
    private Long sudung;                 // SUDUNG
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public Long getMangtbiId() {
        return mangtbiId;
    }

    public void setMangtbiId(Long mangtbiId) {
        this.mangtbiId = mangtbiId;
    }

    public Long getLoaitbiId() {
        return loaitbiId;
    }

    public void setLoaitbiId(Long loaitbiId) {
        this.loaitbiId = loaitbiId;
    }

    public Long getPhanloaitbiId() {
        return phanloaitbiId;
    }

    public void setPhanloaitbiId(Long phanloaitbiId) {
        this.phanloaitbiId = phanloaitbiId;
    }

    public String getMaMautbi() {
        return maMautbi;
    }

    public void setMaMautbi(String maMautbi) {
        this.maMautbi = maMautbi;
    }

    public String getTenMautbi() {
        return tenMautbi;
    }

    public void setTenMautbi(String tenMautbi) {
        this.tenMautbi = tenMautbi;
    }

    public Long getSlRackunit() {
        return slRackunit;
    }

    public void setSlRackunit(Long slRackunit) {
        this.slRackunit = slRackunit;
    }

    public Long getFullDepth() {
        return fullDepth;
    }

    public void setFullDepth(Long fullDepth) {
        this.fullDepth = fullDepth;
    }

    public Long getHangsxId() {
        return hangsxId;
    }

    public void setHangsxId(Long hangsxId) {
        this.hangsxId = hangsxId;
    }

    public Double getCsDinhdanh() {
        return csDinhdanh;
    }

    public void setCsDinhdanh(Double csDinhdanh) {
        this.csDinhdanh = csDinhdanh;
    }

    public String getMaVtu() {
        return maVtu;
    }

    public void setMaVtu(String maVtu) {
        this.maVtu = maVtu;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public Double getDaDinhdanh() {
        return daDinhdanh;
    }

    public void setDaDinhdanh(Double daDinhdanh) {
        this.daDinhdanh = daDinhdanh;
    }

    public Double getDdDinhdanh() {
        return ddDinhdanh;
    }

    public void setDdDinhdanh(Double ddDinhdanh) {
        this.ddDinhdanh = ddDinhdanh;
    }

    public Double getHsCosphi() {
        return hsCosphi;
    }

    public void setHsCosphi(Double hsCosphi) {
        this.hsCosphi = hsCosphi;
    }

    public Long getLoaiNdien() {
        return loaiNdien;
    }

    public void setLoaiNdien(Long loaiNdien) {
        this.loaiNdien = loaiNdien;
    }

    public Long getLoaiMay() {
        return loaiMay;
    }

    public void setLoaiMay(Long loaiMay) {
        this.loaiMay = loaiMay;
    }

    public Double getChieudai() {
        return chieudai;
    }

    public void setChieudai(Double chieudai) {
        this.chieudai = chieudai;
    }

    public Double getChieurong() {
        return chieurong;
    }

    public void setChieurong(Double chieurong) {
        this.chieurong = chieurong;
    }

    public Double getChieucao() {
        return chieucao;
    }

    public void setChieucao(Double chieucao) {
        this.chieucao = chieucao;
    }

    public String getCauhinh() {
        return cauhinh;
    }

    public void setCauhinh(String cauhinh) {
        this.cauhinh = cauhinh;
    }

    public Long getSudung() {
        return sudung;
    }

    public void setSudung(Long sudung) {
        this.sudung = sudung;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public LocalDateTime getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(LocalDateTime ngayCn) {
        this.ngayCn = ngayCn;
    }
}

