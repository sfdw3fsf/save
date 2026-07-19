package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DoiTuongCapPhatIPDtoIn implements Serializable {
    private Long pId;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String pTen;
    @NotNull(message = ModelValidation.NOT_NULL)
    private String pTenDB;

    private String pGhiChu;

    @NotNull(message = ModelValidation.NOT_NULL)
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

    public String getpTenDB() {
        return pTenDB;
    }

    public void setpTenDB(String pTenDB) {
        this.pTenDB = pTenDB;
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
}
