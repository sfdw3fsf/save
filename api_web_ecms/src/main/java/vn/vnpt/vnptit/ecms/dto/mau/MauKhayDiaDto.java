package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_KHD (Thiết bị là khay đĩa)
 */
public class MauKhayDiaDto {
    private Long maukhdId;               // MAUKHD_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private Long slHdd;                  // SL_HDD
    private Long slSsd;                   // SL_SSD
    private Long slNvme;                 // SL_NVME
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMaukhdId() {
        return maukhdId;
    }

    public void setMaukhdId(Long maukhdId) {
        this.maukhdId = maukhdId;
    }

    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public Long getSlHdd() {
        return slHdd;
    }

    public void setSlHdd(Long slHdd) {
        this.slHdd = slHdd;
    }

    public Long getSlSsd() {
        return slSsd;
    }

    public void setSlSsd(Long slSsd) {
        this.slSsd = slSsd;
    }

    public Long getSlNvme() {
        return slNvme;
    }

    public void setSlNvme(Long slNvme) {
        this.slNvme = slNvme;
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

