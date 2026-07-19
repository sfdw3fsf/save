package vn.vnpt.vnptit.ecms.dto.danhmuc;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class IDCDto {
    private Long id;
    private Long idDonVi;
    private String ten;
    private double dienTichTongThe;
    private String anhDaiDien;
    private Long trangThaiVanHanh;
    private double toaDoX;
    private double toaDoY;
    private String diaChi;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date thoiDiemVanHanh;
    private String ghiChu;
    private String tenTat;
    private String moTa;
    private boolean suDung;

    public void isValid() {
        boolean isvalid = (getIdDonVi() != null && getIdDonVi() > 0
                && getTen() != null && !(getTen().trim()).isEmpty()
                && getDiaChi() != null && !(getDiaChi().trim()).isEmpty()
                && getTenTat() != null && !(getTenTat().trim()).isEmpty());
        if (!isvalid)
            throw new BadRequestExceptionMessage("Không được để trống các trường bắt buộc");
    }

    public Long getId() {
        return id;
    }

    public Long getIdDonVi() {
        return idDonVi;
    }

    public String getTen() {
        return ten.trim();
    }

    public String getDiaChi() {
        return diaChi != null ? diaChi.trim() : null;
    }

    public Date getThoiDiemVanHanh() {
        return thoiDiemVanHanh;
    }

    public String getGhiChu() {
        return ghiChu != null ? ghiChu.trim() : null;
    }

    public String getTenTat() {
        return tenTat != null ? tenTat.trim() : null;
    }

    public String getMoTa() {
        return moTa != null ? moTa.trim() : null;
    }

    public boolean isSuDung() {
        return suDung;
    }

    public double getDienTichTongThe() {
        return dienTichTongThe;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public Long getTrangThaiVanHanh() {
        return trangThaiVanHanh;
    }

    public double getToaDoX() {
        return toaDoX;
    }

    public double getToaDoY() {
        return toaDoY;
    }
}
