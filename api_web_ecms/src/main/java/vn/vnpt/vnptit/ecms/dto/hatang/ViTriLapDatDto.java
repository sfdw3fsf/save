package vn.vnpt.vnptit.ecms.dto.hatang;

public class ViTriLapDatDto {
    private Long toaNhaId;
    private Long matSanId;
    private Long phongId;
    private Long rackId;
    private Long uTren;   // up_unit_id (rackunit_id)
    private Long uDuoi;   // down_unit_id (rackunit_id)
    private boolean matTruoc; // 0/1
    private boolean matSau;   // 0/1
    private  boolean canhTrai; // 0/1
    private  boolean canhPhai; // 0/1
    private Integer kieuLapDat; // 1: doc (stand); 2: ngang (wallmount)
    private Double toaDoX;
    private Double toaDoY;
    // Getters and Setters


    public Integer getKieuLapDat() {
        return kieuLapDat;
    }

    public void setKieuLapDat(Integer kieuLapDat) {
        this.kieuLapDat = kieuLapDat;
    }

    public Double getToaDoX() {
        return toaDoX;
    }

    public void setToaDoX(Double toaDoX) {
        this.toaDoX = toaDoX;
    }

    public Double getToaDoY() {
        return toaDoY;
    }

    public void setToaDoY(Double toaDoY) {
        this.toaDoY = toaDoY;
    }

    public boolean isMatTruoc() {
        return matTruoc;
    }

    public void setMatTruoc(boolean matTruoc) {
        this.matTruoc = matTruoc;
    }

    public boolean isCanhPhai() {
        return canhPhai;
    }

    public void setCanhPhai(boolean canhPhai) {
        this.canhPhai = canhPhai;
    }

    public boolean isCanhTrai() {
        return canhTrai;
    }

    public void setCanhTrai(boolean canhTrai) {
        this.canhTrai = canhTrai;
    }

    public boolean isMatSau() {
        return matSau;
    }

    public void setMatSau(boolean matSau) {
        this.matSau = matSau;
    }

    public Long getuTren() {
        return uTren;
    }

    public void setuTren(Long uTren) {
        this.uTren = uTren;
    }

    public Long getuDuoi() {
        return uDuoi;
    }

    public void setuDuoi(Long uDuoi) {
        this.uDuoi = uDuoi;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public Long getMatSanId() {
        return matSanId;
    }

    public void setMatSanId(Long matSanId) {
        this.matSanId = matSanId;
    }

    public Long getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(Long toaNhaId) {
        this.toaNhaId = toaNhaId;
    }
}