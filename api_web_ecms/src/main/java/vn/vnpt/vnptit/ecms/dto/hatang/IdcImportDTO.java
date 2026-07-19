package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdcImportDTO {
    private Integer stt; // STT từ file Excel
    private String ten;
    private String tentat;
    private String diaChi;
    private String donviQuanly;
    private Object trangthaiVh; // Có thể là số hoặc chuỗi
    private Object donviId;
    private Object trangthaiId;



    // Getters and Setters
    public Integer getStt() { return stt; }
    public void setStt(Integer stt) { this.stt = stt; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getTentat() { return tentat; }
    public void setTentat(String tentat) { this.tentat = tentat; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getDonviQuanly() { return donviQuanly; }
    public void setDonviQuanly(String donviQuanly) { this.donviQuanly = donviQuanly; }

    public Object getTrangthaiVh() { return trangthaiVh; }
    public void setTrangthaiVh(Object trangthaiVh) { this.trangthaiVh = trangthaiVh; }



    public Object getDonviId() { return donviId; }
    public void setDonviId(Object donviId) { this.donviId = donviId; }

    public Object getTrangthaiId() { return trangthaiId; }
    public void setTrangthaiId(Object donviId) { this.donviId = donviId; }
}
