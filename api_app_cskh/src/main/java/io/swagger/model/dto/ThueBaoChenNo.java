package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThueBaoChenNo extends BaseDTO {
    @JsonProperty("maTT")
    private String maTT = null;
    @JsonProperty("dichVuVTId")
    private Long dichVuVTId = null;
    @JsonProperty("thangNo")
    private String thangNo = null;
    @JsonProperty("thueBao")
    private String thueBao = null;
    @JsonProperty("chuKyNo")
    private String chuKyNo = null;
    @JsonProperty("tenDVVT")
    private String tenDVVT = null;
    @JsonProperty("tongNo")
    private String tongNo = null;
    @JsonProperty("noGoc")
    private String noGoc = null;
    @JsonProperty("thue")
    private String thue = null;
    @JsonProperty("hoaHong")
    private String hoaHong = null;

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public Long getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Long dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public String getThangNo() {
        return thangNo;
    }

    public void setThangNo(String thangNo) {
        this.thangNo = thangNo;
    }

    public String getThueBao() {
        return thueBao;
    }

    public void setThueBao(String thueBao) {
        this.thueBao = thueBao;
    }

    public String getChuKyNo() {
        return chuKyNo;
    }

    public void setChuKyNo(String chuKyNo) {
        this.chuKyNo = chuKyNo;
    }

    public String getTenDVVT() {
        return tenDVVT;
    }

    public void setTenDVVT(String tenDVVT) {
        this.tenDVVT = tenDVVT;
    }

    public String getTongNo() {
        return tongNo;
    }

    public void setTongNo(String tongNo) {
        this.tongNo = tongNo;
    }

    public String getNoGoc() {
        return noGoc;
    }

    public void setNoGoc(String noGoc) {
        this.noGoc = noGoc;
    }

    public String getThue() {
        return thue;
    }

    public void setThue(String thue) {
        this.thue = thue;
    }

    public String getHoaHong() {
        return hoaHong;
    }

    public void setHoaHong(String hoaHong) {
        this.hoaHong = hoaHong;
    }
}
