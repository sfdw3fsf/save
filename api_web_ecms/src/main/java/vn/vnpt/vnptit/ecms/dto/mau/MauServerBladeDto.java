package vn.vnpt.vnptit.ecms.dto.mau;

import java.time.LocalDateTime;

/**
 * DTO cho bảng MAU_SB (Thiết bị là server, blade, server blade)
 */
public class MauServerBladeDto {
    private Long mausbId;                // MAUSB_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private Long slSocketcpu;            // SL_SOCKETCPU
    private Long slCorecpu;              // SL_CORECPU
    private String modelCpu;             // MODEL_CPU
    private Long slThreadcpu;             // SL_THREADCPU
    private Long slKheram;                // SL_KHERAM
    private Long dlRam;                  // DL_RAM
    private Long slKhessd;                // SL_KHESSD
    private Long slKhenvme;              // SL_KHENVME
    private Long slKhehdd;                // SL_KHEHDD
    private Long slGpu;                  // SL_GPU
    private Long slKhegpu;                // SL_KHEGPU
    private Long slFan;                  // SL_FAN
    private Long slKhepci;                // SL_KHEPCI
    private Long slPci;                  // SL_PCI
    private Long slKheblade;              // SL_KHEBLADE
    private Long slCongdl;               // SL_CONGDL
    private Long slKhedl;                // SL_KHEDL
    private Long slCongdkh;               // SL_CONGDKH
    private Long slKhedkh;                // SL_KHEDKH
    private Long slCongnguon;             // SL_CONGNGUON
    private Long slKhenguon;             // SL_KHENGUON
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN

    // Getters and Setters
    public Long getMausbId() {
        return mausbId;
    }

    public void setMausbId(Long mausbId) {
        this.mausbId = mausbId;
    }

    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public Long getSlSocketcpu() {
        return slSocketcpu;
    }

    public void setSlSocketcpu(Long slSocketcpu) {
        this.slSocketcpu = slSocketcpu;
    }

    public Long getSlCorecpu() {
        return slCorecpu;
    }

    public void setSlCorecpu(Long slCorecpu) {
        this.slCorecpu = slCorecpu;
    }

    public String getModelCpu() {
        return modelCpu;
    }

    public void setModelCpu(String modelCpu) {
        this.modelCpu = modelCpu;
    }

    public Long getSlThreadcpu() {
        return slThreadcpu;
    }

    public void setSlThreadcpu(Long slThreadcpu) {
        this.slThreadcpu = slThreadcpu;
    }

    public Long getSlKheram() {
        return slKheram;
    }

    public void setSlKheram(Long slKheram) {
        this.slKheram = slKheram;
    }

    public Long getDlRam() {
        return dlRam;
    }

    public void setDlRam(Long dlRam) {
        this.dlRam = dlRam;
    }

    public Long getSlKhessd() {
        return slKhessd;
    }

    public void setSlKhessd(Long slKhessd) {
        this.slKhessd = slKhessd;
    }

    public Long getSlKhenvme() {
        return slKhenvme;
    }

    public void setSlKhenvme(Long slKhenvme) {
        this.slKhenvme = slKhenvme;
    }

    public Long getSlKhehdd() {
        return slKhehdd;
    }

    public void setSlKhehdd(Long slKhehdd) {
        this.slKhehdd = slKhehdd;
    }

    public Long getSlGpu() {
        return slGpu;
    }

    public void setSlGpu(Long slGpu) {
        this.slGpu = slGpu;
    }

    public Long getSlKhegpu() {
        return slKhegpu;
    }

    public void setSlKhegpu(Long slKhegpu) {
        this.slKhegpu = slKhegpu;
    }

    public Long getSlFan() {
        return slFan;
    }

    public void setSlFan(Long slFan) {
        this.slFan = slFan;
    }

    public Long getSlKhepci() {
        return slKhepci;
    }

    public void setSlKhepci(Long slKhepci) {
        this.slKhepci = slKhepci;
    }

    public Long getSlPci() {
        return slPci;
    }

    public void setSlPci(Long slPci) {
        this.slPci = slPci;
    }

    public Long getSlKheblade() {
        return slKheblade;
    }

    public void setSlKheblade(Long slKheblade) {
        this.slKheblade = slKheblade;
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

