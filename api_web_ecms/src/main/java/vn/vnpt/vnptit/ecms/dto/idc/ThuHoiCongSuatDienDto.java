package vn.vnpt.vnptit.ecms.dto.idc;

public class ThuHoiCongSuatDienDto {
    private Long phieuTcId;
    private Long ocamId;
    private Long nhanvienId;
    private Long thietbiId;
    private String noiDung;
    private String trangthaiId;  // trang thai bang thu hoi dien

    // getters and setters
    public String getTrangthaiId() {
        return trangthaiId;
    }

    public Long getOcamId() {
        return ocamId;
    }

    public Long getThietbiId() {
        return thietbiId;
    }

    public void setThietbiId(Long thietbiId) {
        this.thietbiId = thietbiId;
    }

    public void setOcamId(Long ocamId) {
        this.ocamId = ocamId;
    }

    public void setTrangthaiId(String trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(Long nhanvienId) {
        this.nhanvienId = nhanvienId;
    }

    public Long getPhieuTcId() {
        return phieuTcId;
    }

    public void setPhieuTcId(Long phieuTcId) {
        this.phieuTcId = phieuTcId;
    }
}