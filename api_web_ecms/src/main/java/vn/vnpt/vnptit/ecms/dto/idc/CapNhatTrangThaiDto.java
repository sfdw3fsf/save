package vn.vnpt.vnptit.ecms.dto.idc;

public class CapNhatTrangThaiDto {
  private int thicongbtId;  // map đúng key từ FE
  private int trangThai;      // FE gửi "0" hoặc "1" dạng string
  private int phieutcId;      // FE gửi "0" hoặc "1" dạng string

  private String nguoiCn;      // FE gửi "0" hoặc "1" dạng string

  public int getPhieutcId() {
    return phieutcId;
  }

  public void setPhieutcId(int phieutcId) {
    this.phieutcId = phieutcId;
  }

  public int getThicongbtId() {
    return thicongbtId;
  }

  public void setThicongbtId(int thicongbtId) {
    this.thicongbtId = thicongbtId;
  }

  public int getTrangThai() {
    return trangThai;
  }

  public void setTrangThai(int trangThai) {
    this.trangThai = trangThai;
  }

  public String getNguoiCn() {
    return nguoiCn;
  }

  public void setNguoiCn(String nguoiCn) {
    this.nguoiCn = nguoiCn;
  }
}

