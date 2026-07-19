package vn.vnpt.vnptit.ecms.dto.hatang;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
public class DanhMucCotDto {

    //@NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Integer id;
    private Integer phong;
    private String ten;
    private Double chieuDai;
    private Double chieuRong;
    private String moTa;
    private Double toaDoX;
    private Double toaDoY;
    private String ghiChu;
    public DanhMucCotDto() {
    }
    public DanhMucCotDto(Integer id, Integer phong, String ten, Double chieuDai, Double chieuRong, String moTa, Double toaDoX, Double toaDoY,
                         String ghiChu) {
        this.id = id;
        this.phong = phong;
        this.ten = ten;
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
        this.moTa = moTa;
        this.toaDoX = toaDoX;
        this.toaDoY = toaDoY;
        this.ghiChu = ghiChu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhong() {
        return phong;
    }

    public void setPhong(Integer phong) {
        this.phong = phong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Double getChieuDai() {
        return chieuDai;
    }

    public void setChieuDai(Double chieuDai) {
        this.chieuDai = chieuDai;
    }

    public Double getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(Double chieuRong) {
        this.chieuRong = chieuRong;
    }

    public String getMoTa() {
        return moTa;
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

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }



    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


}
