package vn.vnpt.vnptit.ecms.dto.idc;

import javax.validation.constraints.NotBlank;

public class CheckMaHaTangDto {
    private Long id;   // null khi insert
    @NotBlank(message = "Ma hạ tầng không được để trống")
    private String maHaTang;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMaHaTang() {
        return maHaTang;
    }
    public void setMaHaTang(String maHaTang) {
        this.maHaTang = maHaTang;
    }
}