package vn.vnpt.vnptit.ecms.dto.idc;

import java.math.BigDecimal;

public class ViTriThietBiRequest {

    private Long thietBiId;

    private Long idcId;

    private Long donViQLyId;

    private Long matSanId;

    private Long toaNhaId;

    private Long phongId;

    private Long idcRackId;

    private Long slRackUnit;

    private Long kieuLapDat;

    private Long upUnitId;

    private Long downUnitId;

    private String matRackUnit;

    private String canhRackUnit;

    private String mota;

    private BigDecimal rackUnitId;

    public BigDecimal getRackUnitId() {
        return rackUnitId;
    }

    public Long getKieuLapDat() {
        return kieuLapDat;
    }

    public void setKieuLapDat(Long kieuLapDat) {
        this.kieuLapDat = kieuLapDat;
    }

    public void setRackUnitId(BigDecimal rackUnitId) {
        this.rackUnitId = rackUnitId;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getDonViQLyId() {
        return donViQLyId;
    }

    public void setDonViQLyId(Long donViQLyId) {
        this.donViQLyId = donViQLyId;
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

    public Long getIdcRackId() {
        return idcRackId;
    }

    public void setIdcRackId(Long idcRackId) {
        this.idcRackId = idcRackId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public Long getSlRackUnit() {
        return slRackUnit;
    }

    public void setSlRackUnit(Long slRackUnit) {
        this.slRackUnit = slRackUnit;
    }

    public Long getUpUnitId() {
        return upUnitId;
    }

    public void setUpUnitId(Long upUnitId) {
        this.upUnitId = upUnitId;
    }

    public Long getDownUnitId() {
        return downUnitId;
    }

    public void setDownUnitId(Long downUnitId) {
        this.downUnitId = downUnitId;
    }

    public String getMatRackUnit() {
        return matRackUnit;
    }

    public void setMatRackUnit(String matRackUnit) {
        this.matRackUnit = matRackUnit;
    }

    public String getCanhRackUnit() {
        return canhRackUnit;
    }

    public void setCanhRackUnit(String canhRackUnit) {
        this.canhRackUnit = canhRackUnit;
    }
}
