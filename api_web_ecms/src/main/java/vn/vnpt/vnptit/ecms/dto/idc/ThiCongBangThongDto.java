package vn.vnpt.vnptit.ecms.dto.idc;


public class ThiCongBangThongDto {
  private Integer phieutcId;
  private Integer phieutcBtId;

  private String nguoiCn;
  private Integer thicongBtId;

  public Integer getThicongBtId() {
    return thicongBtId;
  }

  public void setThicongBtId(Integer thicongBtId) {
    this.thicongBtId = thicongBtId;
  }

  public String getNguoiCn() {
    return nguoiCn;
  }

  public void setNguoiCn(String nguoiCn) {
    this.nguoiCn = nguoiCn;
  }

  public Integer getPhieutcBtId() {
    return phieutcBtId;
  }

  public void setPhieutcBtId(Integer phieutcBtId) {
    this.phieutcBtId = phieutcBtId;
  }

  private Integer idcId;
  private Integer matSanId;
  private Integer goiCuocId;
  private Integer thietbimangId;
  private Integer thietbiportId;
  private String quocTe;
  private String trongNuoc;

  public Integer getPhieutcId() {
    return phieutcId;
  }

  public void setPhieutcId(Integer phieutcId) {
    this.phieutcId = phieutcId;
  }

  public Integer getIdcId() {
    return idcId;
  }

  public void setIdcId(Integer idcId) {
    this.idcId = idcId;
  }

  public Integer getMatSanId() {
    return matSanId;
  }

  public void setMatSanId(Integer matSanId) {
    this.matSanId = matSanId;
  }

  public Integer getGoiCuocId() {
    return goiCuocId;
  }

  public void setGoiCuocId(Integer goiCuocId) {
    this.goiCuocId = goiCuocId;
  }

  public Integer getThietbimangId() {
    return thietbimangId;
  }

  public void setThietbimangId(Integer thietbimangId) {
    this.thietbimangId = thietbimangId;
  }

  public Integer getThietbiportId() {
    return thietbiportId;
  }

  public void setThietbiportId(Integer thietbiportId) {
    this.thietbiportId = thietbiportId;
  }

  public String getQuocTe() {
    return quocTe;
  }

  public void setQuocTe(String quocTe) {
    this.quocTe = quocTe;
  }

  public String getTrongNuoc() {
    return trongNuoc;
  }

  public void setTrongNuoc(String trongNuoc) {
    this.trongNuoc = trongNuoc;
  }
}
