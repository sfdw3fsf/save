package vn.vnpt.vnptit.ecms.dto.idc;

public class ThaoDoRackRequestDto {
    private Number phieuTCId;
    private Number tcRackId;
    private Number rackId;
    private String liDo;
    private String ghiChu;

    public ThaoDoRackRequestDto() {
    }

    public ThaoDoRackRequestDto(Number phieuTCId, Number tcRackId, Number rackId, String liDo, String ghiChu) {
        this.phieuTCId = phieuTCId;
        this.tcRackId = tcRackId;
        this.rackId = rackId;
        this.liDo = liDo;
        this.ghiChu = ghiChu;
    }

    public Number getPhieuTCId() {
        return phieuTCId;
    }

    public void setPhieuTCId(Number phieuTCId) {
        this.phieuTCId = phieuTCId;
    }

    public Number getTcRackId() {
        return tcRackId;
    }

    public void setTcRackId(Number tcRackId) {
        this.tcRackId = tcRackId;
    }

    public Number getRackId() {
        return rackId;
    }

    public void setRackId(Number rackId) {
        this.rackId = rackId;
    }

    public String getLiDo() {
        return liDo;
    }

    public void setLiDo(String liDo) {
        this.liDo = liDo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
