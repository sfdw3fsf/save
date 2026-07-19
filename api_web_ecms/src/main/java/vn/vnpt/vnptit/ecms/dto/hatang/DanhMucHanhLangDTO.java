package vn.vnpt.vnptit.ecms.dto.hatang;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class DanhMucHanhLangDTO {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Integer hanhLangId;
    private Integer phongId;
    private String ten;
    private String tenTat;
    private Double dientich;
    private Double toado_x;
    private Double toado_y;
    private String ghiChu;
    private String moTa;
    private Integer suDung;

    // Nóng / ưu tiên (0/1)
    private Integer nong;

    public Double getDientich() {
        return dientich;
    }

    public void setDientich(Double dientich) {
        this.dientich = dientich;
    }

    public Double getToado_x() {
        return toado_x;
    }

    public void setToado_x(Double toado_x) {
        this.toado_x = toado_x;
    }

    public Double getToado_y() {
        return toado_y;
    }

    public void setToado_y(Double toado_y) {
        this.toado_y = toado_y;
    }

    public void setSuDung_id(Integer suDung) {
        this.suDung = suDung;
    }



    /* ================= Getter / Setter ================= */

    public Integer getHanhLangId() {
        return hanhLangId;
    }

    public void setHanhLangId(Integer hanhLangId) {
        this.hanhLangId = hanhLangId;
    }

    public Integer getPhongId() {
        return phongId;
    }

    public void setPhongId(Integer phongId) {
        this.phongId = phongId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public Integer getNong() {
        return nong;
    }

    public void setNong(Integer nong) {
        this.nong = nong;
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

    public Integer getSuDung() {
        return suDung;
    }

    public void setSuDung(Integer suDung) {
        this.suDung = suDung;
    }
}
