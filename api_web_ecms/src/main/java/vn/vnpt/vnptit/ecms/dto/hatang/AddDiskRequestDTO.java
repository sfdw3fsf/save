package vn.vnpt.vnptit.ecms.dto.hatang;

public class AddDiskRequestDTO {
  private int diskId;
  private String ten;
  private String loai; // VD: "NFS", "SSD", "HDD"
  private int dungluong; // Đơn vị: MB
  private int hieu_luc; // "Y" hoặc "N"

  // Getters & setters
  public int getDiskId() { return diskId; }
  public void setDiskId(int diskId) { this.diskId = diskId; }
  public String getTen() { return ten; }
  public void setTen(String ten) { this.ten = ten; }

  public String getLoai() { return loai; }
  public void setLoai(String loai) { this.loai = loai; }

  public int getDungluong() { return dungluong; }
  public void setDungluong(int dungluong) { this.dungluong = dungluong; }

  public int getHieuLuc() { return hieu_luc; }
  public void setHieuLuc(int hieu_luc) { this.hieu_luc = hieu_luc; }
}
