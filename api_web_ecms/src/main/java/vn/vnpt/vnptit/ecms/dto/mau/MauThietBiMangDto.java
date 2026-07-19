package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_TBIMANG (Thiết bị là thiết bị mạng)
 */
public class MauThietBiMangDto {
    private Long mautbimangId;           // MAUTBIMANG_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private Long slCongdl;               // SL_CONGDL
    private Long slKhedl;                // SL_KHEDL
    private Long slCongnguon;             // SL_CONGNGUON
    private Long slKhenguon;             // SL_KHENGUON
    private Long slCongdkh;               // SL_CONGDKH
    private Long slKhedkh;                // SL_KHEDKH
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMautbimangId() {
        return mautbimangId;
    }

    public void setMautbimangId(Long mautbimangId) {
        this.mautbimangId = mautbimangId;
    }

    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public Long getSlCongdl() {
        return slCongdl;
    }

    public void setSlCongdl(Long slCongdl) {
        this.slCongdl = slCongdl;
    }

    public Long getSlKhedl() {
        return slKhedl;
    }

    public void setSlKhedl(Long slKhedl) {
        this.slKhedl = slKhedl;
    }

    public Long getSlCongnguon() {
        return slCongnguon;
    }

    public void setSlCongnguon(Long slCongnguon) {
        this.slCongnguon = slCongnguon;
    }

    public Long getSlKhenguon() {
        return slKhenguon;
    }

    public void setSlKhenguon(Long slKhenguon) {
        this.slKhenguon = slKhenguon;
    }

    public Long getSlCongdkh() {
        return slCongdkh;
    }

    public void setSlCongdkh(Long slCongdkh) {
        this.slCongdkh = slCongdkh;
    }

    public Long getSlKhedkh() {
        return slKhedkh;
    }

    public void setSlKhedkh(Long slKhedkh) {
        this.slKhedkh = slKhedkh;
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

