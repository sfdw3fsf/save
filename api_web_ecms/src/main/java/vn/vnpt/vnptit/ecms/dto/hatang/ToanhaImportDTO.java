package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToanhaImportDTO {
    private Integer stt; // STT từ file Excel
    private String tentatIdc;
    private String ten;
    private String tentat;
    private String tenMucdich;
    private String ghiChu;

    // Getters and Setters
    public Integer getStt() { return stt; }
    public void setStt(Integer stt) { this.stt = stt; }

    public String getTentatIdc() { return tentatIdc; }
    public void setTentatIdc(String tentatIdc) { this.tentatIdc = tentatIdc; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getTentat() { return tentat; }
    public void setTentat(String tentat) { this.tentat = tentat; }

    public String getTenMucdich() { return tenMucdich; }
    public void setTenMucdich(String tenMucdich) { this.tenMucdich = tenMucdich; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
