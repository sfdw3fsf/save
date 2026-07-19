package vn.vnpt.vnptit.ecms.dto.danhmuc;

public class DichVuCnttDTO {
  private Integer id;
  private String ten;
  private String tenTat;
  private String moTa;

  private Integer suDung;

  private String ghiChu;


  private String nguoiCn;

  private String ngayCn;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public Integer getSuDung() {
    return suDung;
  }

  public void setSuDung(Integer suDung) {
    this.suDung = suDung;
  }

  public String getGhiChu() {
    return ghiChu;
  }

  public void setGhiChu(String ghiChu) {
    this.ghiChu = ghiChu;
  }

  public String getNguoiCn() {
    return nguoiCn;
  }

  public void setNguoiCn(String nguoiCn) {
    this.nguoiCn = nguoiCn;
  }

  public String getNgayCn() {
    return ngayCn;
  }

  public void setNgayCn(String ngayCn) {
    this.ngayCn = ngayCn;
  }
}