package vn.vnpt.vnptit.ecms.dto.danhmuc;


import java.io.Serializable;

public class TinhTrangIPDTO  implements Serializable {
    private Long pId;
    private String pTen;
    private String pGhiChu;
    private Long pHieuLuc;

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

    public String getpGhiChu() {
        return pGhiChu;
    }

    public void setpGhiChu(String pGhiChu) {
        this.pGhiChu = pGhiChu;
    }

    public Long getpHieuLuc() {
        return pHieuLuc;
    }

    public void setpHieuLuc(Long pHieuLuc) {
        this.pHieuLuc = pHieuLuc;
    }

    public TinhTrangIPDTO(Long pId, String pTen, String pGhiChu, Long pHieuLuc) {
        this.pId = pId;
        this.pTen = pTen;
        this.pGhiChu = pGhiChu;
        this.pHieuLuc = pHieuLuc;
    }
}
