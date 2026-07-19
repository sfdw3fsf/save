package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;

public class LoaiMayAoDTO {
    private Long id;
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String ten;
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String tenTat;
    private Long vpc;
    private String moTa;
    private Long suDung;
    private String ghiChu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVpc() {
        return vpc;
    }

    public void setVpc(Long vpc) {
        this.vpc = vpc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Long getSuDung() {
        return suDung;
    }

    public void setSuDung(Long suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
