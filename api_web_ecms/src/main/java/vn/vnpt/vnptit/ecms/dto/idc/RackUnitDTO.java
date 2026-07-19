package vn.vnpt.vnptit.ecms.dto.idc;


import java.io.Serializable;

public class RackUnitDTO implements Serializable {
  private Integer rackUnitId;   // dùng cho update
  private Integer rackId;
  private String ten;
  private String mota;
  private Integer vitri;
  private Integer trangthaiId;
  private String ghichu;
  private Integer sudung;
  private String nguoiCn;

  // Getter & Setter
  public Integer getRackUnitId() {
    return rackUnitId;
  }

  public void setRackUnitId(Integer rackUnitId) {
    this.rackUnitId = rackUnitId;
  }

  public Integer getRackId() {
    return rackId;
  }

  public void setRackId(Integer rackId) {
    this.rackId = rackId;
  }

  public String getTen() {
    return ten;
  }

  public void setTen(String ten) {
    this.ten = ten;
  }

  public String getMota() {
    return mota;
  }

  public void setMota(String mota) {
    this.mota = mota;
  }

  public Integer getVitri() {
    return vitri;
  }

  public void setVitri(Integer vitri) {
    this.vitri = vitri;
  }

  public Integer getTrangthaiId() {
    return trangthaiId;
  }

  public void setTrangthaiId(Integer trangthaiId) {
    this.trangthaiId = trangthaiId;
  }

  public String getGhichu() {
    return ghichu;
  }

  public void setGhichu(String ghichu) {
    this.ghichu = ghichu;
  }

  public Integer getSudung() {
    return sudung;
  }

  public void setSudung(Integer sudung) {
    this.sudung = sudung;
  }

  public String getNguoiCn() {
    return nguoiCn;
  }

  public void setNguoiCn(String nguoiCn) {
    this.nguoiCn = nguoiCn;
  }
}
