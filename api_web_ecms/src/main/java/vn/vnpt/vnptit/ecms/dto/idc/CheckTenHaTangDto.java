package vn.vnpt.vnptit.ecms.dto.idc;

import javax.validation.constraints.NotBlank;

public class CheckTenHaTangDto {
    private Long id;   // null khi insert
    @NotBlank(message = "Tên hạ tầng không được để trống")
    private String ten;
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
}