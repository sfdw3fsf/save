package vn.vnpt.vnptit.qlsc.dto.mnv;

public class CapNhatTinhTrangSuCoMNVInput {
    private String tinhTrang = null;
    private Long suCoId;
    private Long nhomSuCoId;

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public Long getNhomSuCoId() {
        return nhomSuCoId;
    }

    public void setNhomSuCoId(Long nhomSuCoId) {
        this.nhomSuCoId = nhomSuCoId;
    }
}
