package vn.vnpt.vnptit.ecms.dto.idc;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpsertPhanLoaiHTDto {

    private Long id;

    @NotBlank(message = "Tên phân loại không được để trống")
    @Size(max = 100, message = "Tên phân loại không được vượt quá 100 ký tự")
    private String ten;

    @Size(max = 20, message = "Tên tắt không được vượt quá 20 ký tự")
    private String tenTat;

    @Size(max = 200, message = "Mô tả không được vượt quá 200 ký tự")
    private String moTa;

    private Integer hieuLuc;

    @Size(max = 200, message = "Ghi chú không được vượt quá 200 ký tự")
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Integer hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}