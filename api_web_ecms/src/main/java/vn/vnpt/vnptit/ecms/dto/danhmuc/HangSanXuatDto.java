package vn.vnpt.vnptit.ecms.dto.danhmuc;

import java.io.Serializable;

public class HangSanXuatDto implements Serializable {
    private Long pId;
    private String pTen;
    private String pMaHSX;
    private String pGhiChu;
    private Boolean pHieuLuc;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpTen() {
        return pTen;
    }

    public void setpTen(String pTen) {
        this.pTen = pTen;
    }

    public String getpMaHSX() {
        return pMaHSX;
    }

    public void setpMaHSX(String pMaHSX) {
        this.pMaHSX = pMaHSX;
    }

    public String getpGhiChu() {
        return pGhiChu;
    }

    public void setpGhiChu(String pGhiChu) {
        this.pGhiChu = pGhiChu;
    }

    public Boolean getpHieuLuc() {
        return pHieuLuc;
    }

    public void setpHieuLuc(Boolean pHieuLuc) {
        this.pHieuLuc = pHieuLuc;
    }
}
