// MatsanImportDTO.java
package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatsanImportDTO {
    private Integer stt; // STT từ file Excel
    private String tentatIdc;
    private String tentatToanha;
    private String tenMatsan;
    private String tentat;
    private Object dienTich; // Có thể là String hoặc Number
    private String tenTrangthai;
    private String donviIdc;
    private String ghiChu;

    // Getters and Setters
    public Integer getStt() { return stt; }
    public void setStt(Integer stt) { this.stt = stt; }

    public String getTentatIdc() { return tentatIdc; }
    public void setTentatIdc(String tentatIdc) { this.tentatIdc = tentatIdc; }

    public String getTentatToanha() { return tentatToanha; }
    public void setTentatToanha(String tentatToanha) { this.tentatToanha = tentatToanha; }

    public String getTenMatsan() { return tenMatsan; }
    public void setTenMatsan(String tenMatsan) { this.tenMatsan = tenMatsan; }

    public String getTentat() { return tentat; }
    public void setTentat(String tentat) { this.tentat = tentat; }

    public Object getDienTich() { return dienTich; }
    public void setDienTich(Object dienTich) { this.dienTich = dienTich; }

    public String getTenTrangthai() { return tenTrangthai; }
    public void setTenTrangthai(String tenTrangthai) { this.tenTrangthai = tenTrangthai; }

    public String getDonviIdc() { return donviIdc; }
    public void setDonviIdc(String donviIdc) { this.donviIdc = donviIdc; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
