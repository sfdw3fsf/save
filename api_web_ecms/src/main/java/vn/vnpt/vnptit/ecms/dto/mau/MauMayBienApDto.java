package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_MBA (Thiết bị là máy biến áp)
 */
public class MauMayBienApDto {
    private Long maumbaId;               // MAUMBA_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private Long hsCosphi;               // HS_COSPHI
    private String loaimay;              // LOAIMAY
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMaumbaId() {
        return maumbaId;
    }

    public void setMaumbaId(Long maumbaId) {
        this.maumbaId = maumbaId;
    }

    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public Long getHsCosphi() {
        return hsCosphi;
    }

    public void setHsCosphi(Long hsCosphi) {
        this.hsCosphi = hsCosphi;
    }

    public String getLoaimay() {
        return loaimay;
    }

    public void setLoaimay(String loaimay) {
        this.loaimay = loaimay;
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

