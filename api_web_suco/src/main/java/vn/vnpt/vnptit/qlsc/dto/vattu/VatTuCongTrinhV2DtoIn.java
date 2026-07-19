package vn.vnpt.vnptit.qlsc.dto.vattu;

import java.util.List;

public class VatTuCongTrinhV2DtoIn {
  private Integer congTrinhId;
  private Integer suCoId;
  private Integer phieuId;
  private Integer mucDichId;
  private Integer lyDoCapId;
  private Integer tinhTrangId;
  private Integer trangBiId;
  private Integer nhanVienId;
  private Integer donViId;
  private String nguoiCn;
  private String ipCn;
  private String mayCn;
  private String ngayCap;
  private String ghiChu;
  private List<Object> dsCongTrinh;

  private List<Object> dsUpdateVatTu;


  public Integer getCongTrinhId() {
    return congTrinhId;
  }

  public void setCongTrinhId(Integer congTrinhId) {
    this.congTrinhId = congTrinhId;
  }

  public Integer getMucDichId() {
    return mucDichId;
  }

  public void setMucDichId(Integer mucDichId) {
    this.mucDichId = mucDichId;
  }

  public Integer getLyDoCapId() {
    return lyDoCapId;
  }

  public void setLyDoCapId(Integer lyDoCapId) {
    this.lyDoCapId = lyDoCapId;
  }

  public Integer getTinhTrangId() {
    return tinhTrangId;
  }

  public void setTinhTrangId(Integer tinhTrangId) {
    this.tinhTrangId = tinhTrangId;
  }

  public Integer getTrangBiId() {
    return trangBiId;
  }

  public void setTrangBiId(Integer trangBiId) {
    this.trangBiId = trangBiId;
  }

  public Integer getDonViId() {
    return donViId;
  }

  public void setDonViId(Integer donViId) {
    this.donViId = donViId;
  }

  public String getNguoiCn() {
    return nguoiCn;
  }

  public void setNguoiCn(String nguoiCn) {
    this.nguoiCn = nguoiCn;
  }

  public String getIpCn() {
    return ipCn;
  }

  public void setIpCn(String ipCn) {
    this.ipCn = ipCn;
  }

  public String getMayCn() {
    return mayCn;
  }

  public void setMayCn(String mayCn) {
    this.mayCn = mayCn;
  }

  public String getNgayCap() {
    return ngayCap;
  }

  public void setNgayCap(String ngayCap) {
    this.ngayCap = ngayCap;
  }

  public String getGhiChu() {
    return ghiChu;
  }

  public void setGhiChu(String ghiChu) {
    this.ghiChu = ghiChu;
  }

  public List<Object> getDsCongTrinh() {
    return dsCongTrinh;
  }

  public void setDsCongTrinh(List<Object> dsCongTrinh) {
    this.dsCongTrinh = dsCongTrinh;
  }

  public Integer getNhanVienId() {
    return nhanVienId;
  }

  public void setNhanVienId(Integer nhanVienId) {
    this.nhanVienId = nhanVienId;
  }

  public Integer getSuCoId() {
    return suCoId;
  }

  public void setSuCoId(Integer suCoId) {
    this.suCoId = suCoId;
  }

  public Integer getPhieuId() {
    return phieuId;
  }
  public void setPhieuId(Integer phieuId) {
    this.phieuId = phieuId;
  }

  public List<Object> getDsUpdateVatTu() {
    return dsUpdateVatTu;
  }

  public void setDsUpdateVatTu(List<Object> dsUpdateVatTu) {
    this.dsUpdateVatTu = dsUpdateVatTu;
  }
}