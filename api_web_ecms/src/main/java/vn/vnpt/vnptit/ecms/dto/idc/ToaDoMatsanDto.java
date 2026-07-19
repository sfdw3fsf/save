package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class ToaDoMatsanDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer matsanId;
    private String matsanTen;

    private Integer toadoMatsanId;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String toadoMatsanTen;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer hieuLuc;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String trucNgang;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String trucDoc;

    private String ghiChu;

    public Integer getMatsanId() {
        return matsanId;
    }

    public void setMatsanId(Integer matsanId) {
        this.matsanId = matsanId;
    }

    public String getMatsanTen() {
        return matsanTen;
    }

    public void setMatsanTen(String matsanTen) {
        this.matsanTen = matsanTen;
    }

    public Integer getToadoMatsanId() {
        return toadoMatsanId;
    }

    public void setToadoMatsanId(Integer toadoMatsanId) {
        this.toadoMatsanId = toadoMatsanId;
    }

    public String getToadoMatsanTen() {
        return toadoMatsanTen;
    }

    public void setToadoMatsanTen(String toadoMatsanTen) {
        this.toadoMatsanTen = toadoMatsanTen;
    }

    public String getTrucNgang() {
        return trucNgang;
    }

    public void setTrucNgang(String trucNgang) {
        this.trucNgang = trucNgang;
    }

    public String getTrucDoc() {
        return trucDoc;
    }

    public void setTrucDoc(String trucDoc) {
        this.trucDoc = trucDoc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }
}
