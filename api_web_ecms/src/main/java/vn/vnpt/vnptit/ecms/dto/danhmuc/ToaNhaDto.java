package vn.vnpt.vnptit.ecms.dto.danhmuc;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class ToaNhaDto {
    private Long id;
    private Long idIDC;
    private Long idDonViIDC;
    private String tenToaNha;
    private String ghiChu;
    private String tenTat;
    private String moTa;
    private boolean suDung;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date thoiDiemVanHanh;
    @JsonProperty("mucDichSuDung")
    private Integer mucDichSuDung;
    @JsonProperty("dienTichTongThe")
    private double dienTichTongThe;
    private double toaDoX;
    private double toaDoY;
    private String anhDaiDien;
    @JsonProperty("trangThaiVanHanh")
    private Integer trangThaiVanHanh;

    public void isValid() {
        boolean isvalid = (getIdIDC() != null && getIdIDC() > 0 && getTenToaNha() != null
                && !(getTenToaNha()).isEmpty() && getMucDichSdId() != null && getMucDichSdId() > 0
                && getIdDonViIDC() != null
                && getIdDonViIDC() > 0);
        if (!isvalid)
            throw new BadRequestExceptionMessage("Không được để trống các trường bắt buộc");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdIDC() {
        return idIDC;
    }

    public Long getIdDonViIDC() {
        return idDonViIDC;
    }

    public String getTenToaNha() {
        return tenToaNha != null ? tenToaNha.trim() : null;
    }

    public String getGhiChu() {
        return ghiChu != null ? ghiChu.trim() : null;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
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

    public Date getThoiDiemVanHanh() {
        return thoiDiemVanHanh;
    }

    public Integer getMucDichSdId() {
        return mucDichSuDung;
    }

    public double getDienTich() {
        return dienTichTongThe;
    }

    public double getToaDoX() {
        return toaDoX;
    }

    public double getToaDoY() {
        return toaDoY;
    }

    public Integer getTrangThaiVh() {
        return trangThaiVanHanh;
    }

    public String getAnhDaiDien() {
        return anhDaiDien != null ? anhDaiDien.trim() : null;
    }

}
