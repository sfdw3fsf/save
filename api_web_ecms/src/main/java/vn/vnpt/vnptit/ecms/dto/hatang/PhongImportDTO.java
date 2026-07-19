// PhongImportDTO.java
package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhongImportDTO {
    private Integer stt; // STT từ file Excel
    private String tenDonviIdc;
    private String tentatIdc;
    private String tentatToanha;
    private String tentatMatsan;
    private String ten;
    private String tentat;
    private Object dienTich; // Có thể là String hoặc Number
    private String tenMucdich;
    private String tenTrangthai;
    private String ghiChu;

    // Getters and Setters
    public Integer getStt() { return stt; }
    public void setStt(Integer stt) { this.stt = stt; }

    public String getTenDonviIdc() { return tenDonviIdc; }
    public void setTenDonviIdc(String tenDonviIdc) { this.tenDonviIdc = tenDonviIdc; }

    public String getTentatIdc() { return tentatIdc; }
    public void setTentatIdc(String tentatIdc) { this.tentatIdc = tentatIdc; }

    public String getTentatToanha() { return tentatToanha; }
    public void setTentatToanha(String tentatToanha) { this.tentatToanha = tentatToanha; }

    public String getTentatMatsan() { return tentatMatsan; }
    public void setTentatMatsan(String tentatMatsan) { this.tentatMatsan = tentatMatsan; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getTentat() { return tentat; }
    public void setTentat(String tentat) { this.tentat = tentat; }

    public Object getDienTich() { return dienTich; }
    public void setDienTich(Object dienTich) { this.dienTich = dienTich; }

    public String getTenMucdich() { return tenMucdich; }
    public void setTenMucdich(String tenMucdich) { this.tenMucdich = tenMucdich; }

    public String getTenTrangthai() { return tenTrangthai; }
    public void setTenTrangthai(String tenTrangthai) { this.tenTrangthai = tenTrangthai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}