package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.*;

public class TNLK_UpdateTrangThaiTiepNhanInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private int kieu;
    private int vanHanhId;
    private int loaiVhId;
    private int thietBiId;
    private String noiDung;
    private String ngayYc;
    private String ketQuaTd;
    private String ketQua;
    private int ttvhId;
    private String ngayHt;
    private String ghiChu;
    private String deXuat;
    private String moTa;
    private int phieuVhId;
    private int donViNhanId;

    public Integer getKieu() { return this.kieu; }
    public Integer getVanHanhId() { return this.vanHanhId; }
    public Integer getLoaiVhId() { return this.loaiVhId; }
    public Integer getThietBiId() { return this.thietBiId; }
    public String getNoiDung() { return this.noiDung; }
    public String getNgayYc() { return this.ngayYc; }
    public String getKetQuaTd() { return this.ketQuaTd; }
    public String getKetQua() { return this.ketQua; }
    public Integer getTtvhId() { return this.ttvhId; }
    public String getNgayHt() { return this.ngayHt; }
    public String getGhiChu() { return this.ghiChu; }
    public String getDeXuat() { return this.deXuat; }
    public String getMoTa() { return this.moTa; }
    public Integer getPhieuVhId() { return this.phieuVhId; }
    public Integer getDonViNhanId() { return this.donViNhanId; }
}
