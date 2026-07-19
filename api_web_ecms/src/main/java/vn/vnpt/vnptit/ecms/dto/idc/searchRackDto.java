package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class searchRackDto implements Serializable {
    Long idcId;
    Long toaNhaId;
    Long matSanId;
    Long phongId;
    String ten;

    public searchRackDto() {
    }

    public searchRackDto(Long idcId, Long toaNhaId, Long matSanId, Long phongId, String ten) {
        this.idcId = idcId;
        this.toaNhaId = toaNhaId;
        this.matSanId = matSanId;
        this.phongId = phongId;
        this.ten = ten;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(Long toaNhaId) {
        this.toaNhaId = toaNhaId;
    }

    public Long getMatSanId() {
        return matSanId;
    }

    public void setMatSanId(Long matSanId) {
        this.matSanId = matSanId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
