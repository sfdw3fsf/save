package vn.vnpt.vnptit.ecms.dto.idc;

public class ThiCongRackDto {
    private Number tcRackId;
    private Number phieuTCId;
    private Number rackId;
    private Number nvId;
    private String noiDung;
    private String ghiChu;
    private String moTa;

    public ThiCongRackDto() {
    }

    public ThiCongRackDto(Number tcRackId,Number phieuTCId, Number rackId, Number nvId, String noiDung, String ghiChu, String moTa) {
        this.tcRackId = tcRackId;
        this.phieuTCId = phieuTCId;
        this.rackId = rackId;
        this.nvId = nvId;
        this.noiDung = noiDung;
        this.ghiChu = ghiChu;
        this.moTa = moTa;
    }

    public Number getTcRackId() {
        return tcRackId;
    }

    public void setTcRackId(Number tcRackId) {
        this.tcRackId = tcRackId;
    }

    public Number getPhieuTCId() {
        return phieuTCId;
    }

    public void setPhieuTCId(Number phieuTCId) {
        this.phieuTCId = phieuTCId;
    }

    public Number getRackId() {
        return rackId;
    }

    public void setRackId(Number rackId) {
        this.rackId = rackId;
    }

    public Number getNvId() {
        return nvId;
    }

    public void setNvId(Number nvId) {
        this.nvId = nvId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
