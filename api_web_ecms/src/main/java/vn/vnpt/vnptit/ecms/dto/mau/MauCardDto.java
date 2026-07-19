package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_CARD (Thiết bị là card)
 */
public class MauCardDto {
    private Long maucardId;              // MAUCARD_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private Long slCongdl;               // SL_CONGDL
    private Long slCongnguon;             // SL_CONGNGUON
    private Long slCongdkh;               // SL_CONGDKH
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMaucardId() {
        return maucardId;
    }

    public void setMaucardId(Long maucardId) {
        this.maucardId = maucardId;
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

    public Long getSlCongnguon() {
        return slCongnguon;
    }

    public void setSlCongnguon(Long slCongnguon) {
        this.slCongnguon = slCongnguon;
    }

    public Long getSlCongdkh() {
        return slCongdkh;
    }

    public void setSlCongdkh(Long slCongdkh) {
        this.slCongdkh = slCongdkh;
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

