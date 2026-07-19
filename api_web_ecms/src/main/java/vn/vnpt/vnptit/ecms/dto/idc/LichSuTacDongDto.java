package vn.vnpt.vnptit.ecms.dto.idc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LichSuTacDongDto {
    @JsonProperty("hanhDong")
    private String hanhDong;

    @JsonProperty("tenBang")
    private String tenBang;

    @JsonProperty("tenCot")
    private String tenCot;

    @JsonProperty("khoaDuLieu")
    private String khoaDuLieu;

    @JsonProperty("giaTriCu")
    private String giaTriCu;

    @JsonProperty("giaTriMoi")
    private String giaTriMoi;

    @JsonProperty("moTa")
    private String moTa;

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getTenBang() {
        return tenBang;
    }

    public void setTenBang(String tenBang) {
        this.tenBang = tenBang;
    }

    public String getTenCot() {
        return tenCot;
    }

    public void setTenCot(String tenCot) {
        this.tenCot = tenCot;
    }

    public String getKhoaDuLieu() {
        return khoaDuLieu;
    }

    public void setKhoaDuLieu(String khoaDuLieu) {
        this.khoaDuLieu = khoaDuLieu;
    }

    public String getGiaTriCu() {
        return giaTriCu;
    }

    public void setGiaTriCu(String giaTriCu) {
        this.giaTriCu = giaTriCu;
    }

    public String getGiaTriMoi() {
        return giaTriMoi;
    }

    public void setGiaTriMoi(String giaTriMoi) {
        this.giaTriMoi = giaTriMoi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}