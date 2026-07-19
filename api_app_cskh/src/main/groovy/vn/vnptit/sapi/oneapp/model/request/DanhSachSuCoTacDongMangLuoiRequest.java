package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 1. Lấy danh sách tác động mạng lưới, sự cố
 */

public class DanhSachSuCoTacDongMangLuoiRequest {
    @JsonProperty("vphanloai")
    private Integer vphanloai;

    @JsonProperty("vtungay")
    private String vtungay;

    @JsonProperty("vdenngay")
    private String vdenngay;

    @JsonProperty("vtrangthai")
    private Integer vtrangthai;

    @JsonProperty("vnoidung_tk")
    private String vnoidung_tk;

    public Integer getVphanloai() {
        return vphanloai;
    }

    public void setVphanloai(Integer vphanloai) {
        this.vphanloai = vphanloai;
    }

    public String getVtungay() {
        return vtungay;
    }

    public void setVtungay(String vtungay) {
        this.vtungay = vtungay;
    }

    public String getVdenngay() {
        return vdenngay;
    }

    public void setVdenngay(String vdenngay) {
        this.vdenngay = vdenngay;
    }

    public Integer getVtrangthai() {
        return vtrangthai;
    }

    public void setVtrangthai(Integer vtrangthai) {
        this.vtrangthai = vtrangthai;
    }

    public String getVnoidung_tk() {
        return vnoidung_tk;
    }

    public void setVnoidung_tk(String vnoidung_tk) {
        this.vnoidung_tk = vnoidung_tk;
    }
}
