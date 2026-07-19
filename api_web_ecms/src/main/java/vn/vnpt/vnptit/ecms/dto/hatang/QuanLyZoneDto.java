package vn.vnpt.vnptit.ecms.dto.hatang;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class QuanLyZoneDto {
    private Number zoneId;
    private String tenZone;
    private Number idcId;
    private Number hieuluc;
    private String ghichu;

    public QuanLyZoneDto() {
    }

    public QuanLyZoneDto(Number zoneId, String tenZone, Number idcId, Number hieuluc, String ghichu) {
        this.zoneId = zoneId;
        this.tenZone = tenZone;
        this.idcId = idcId;
        this.hieuluc = hieuluc;
        this.ghichu = ghichu;
    }


    public Number getZoneId() {
        return zoneId;
    }

    public void setZoneId(Number zoneId) {
        this.zoneId = zoneId;
    }

    public String getTenZone() {
        return tenZone;
    }

    public void setTenZone(String tenZone) {
        this.tenZone = tenZone;
    }

    public Number getIdcId() {
        return idcId;
    }

    public void setIdcId(Number idcId) {
        this.idcId = idcId;
    }

    public Number getHieuluc() {
        return hieuluc;
    }

    public void setHieuluc(Number hieuluc) {
        this.hieuluc = hieuluc;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}

