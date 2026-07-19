package vn.vnpt.vnptit.ecms.dto.idc;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpsertLoaiVaiTroDto {

    private Long id;

    @NotBlank(message = "Tên loại vai trò không được để trống")
    @Size(max = 100, message = "Tên loại vai trò không được vượt quá 100 ký tự")
    private String ten;

    @Size(max = 50, message = "Tên tắt không được vượt quá 50 ký tự")
    private String tenTat;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String moTa;

    @Size(max = 200, message = "Ghi chú không được vượt quá 200 ký tự")
    private String ghiChu;

    private Integer hieuLuc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}