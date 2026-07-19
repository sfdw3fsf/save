package vn.vnpt.vnptit.ecms.dto.idc;

public class ChuyenDoiCongSuatDienDto {
    private Long phieuTcId;
    private Long ocamCuId;
    private Long ocamMoiId;
    private Long nhanvienId;
    private Long thietbiId;
    private Long thanhnguonId;  // de check cong suat dien cua thanh nguon so voi cong suat thiet bi
    private String noiDung;
    private String trangthaiId;
    // getters and setters

    public String getTrangthaiId() {
        return trangthaiId;
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

    public Long getThanhnguonId() {
        return thanhnguonId;
    }

    public void setThanhnguonId(Long thanhnguonId) {
        this.thanhnguonId = thanhnguonId;
    }

    public Long getThietbiId() {
        return thietbiId;
    }

    public void setThietbiId(Long thietbiId) {
        this.thietbiId = thietbiId;
    }

    public Long getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(Long nhanvienId) {
        this.nhanvienId = nhanvienId;
    }

    public Long getOcamMoiId() {
        return ocamMoiId;
    }

    public void setOcamMoiId(Long ocamMoiId) {
        this.ocamMoiId = ocamMoiId;
    }

    public Long getOcamCuId() {
        return ocamCuId;
    }

    public void setOcamCuId(Long ocamCuId) {
        this.ocamCuId = ocamCuId;
    }

    public Long getPhieuTcId() {
        return phieuTcId;
    }

    public void setPhieuTcId(Long phieuTcId) {
        this.phieuTcId = phieuTcId;
    }
}