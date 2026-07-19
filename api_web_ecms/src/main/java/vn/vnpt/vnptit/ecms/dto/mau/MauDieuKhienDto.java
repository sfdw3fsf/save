package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_DKH (Thiết bị là thiết bị điều khiển)
 */
public class MauDieuKhienDto {
    private Long maudkhId;               // MAUDKH_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private Long dlRam;                  // DL_RAM
    private Long slCorecpu;              // SL_CORECPU
    private Long dlCache;                // DL_CACHE
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMaudkhId() {
        return maudkhId;
    }

    public void setMaudkhId(Long maudkhId) {
        this.maudkhId = maudkhId;
    }

    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public Long getDlRam() {
        return dlRam;
    }

    public void setDlRam(Long dlRam) {
        this.dlRam = dlRam;
    }

    public Long getSlCorecpu() {
        return slCorecpu;
    }

    public void setSlCorecpu(Long slCorecpu) {
        this.slCorecpu = slCorecpu;
    }

    public Long getDlCache() {
        return dlCache;
    }

    public void setDlCache(Long dlCache) {
        this.dlCache = dlCache;
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

