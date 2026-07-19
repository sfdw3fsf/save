package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareCapnhatLichhenKhaosatRequest {


    public Integer getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Integer vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public Integer getVdonvi_id() {
        return vdonvi_id;
    }

    public void setVdonvi_id(Integer vdonvi_id) {
        this.vdonvi_id = vdonvi_id;
    }

    public String getVnoidung_hen() {
        return vnoidung_hen;
    }

    public void setVnoidung_hen(String vnoidung_hen) {
        this.vnoidung_hen = vnoidung_hen;
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

    public String getVnguoi_cn() {
        return vnguoi_cn;
    }

    public void setVnguoi_cn(String vnguoi_cn) {
        this.vnguoi_cn = vnguoi_cn;
    }

    public String getVmay_cn() {
        return vmay_cn;
    }

    public void setVmay_cn(String vmay_cn) {
        this.vmay_cn = vmay_cn;
    }

    @JsonProperty("vhdtb_id")
    private Integer vhdtb_id;
    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;
    @JsonProperty("vdonvi_id")
    private Integer vdonvi_id;
    @JsonProperty("vnoidung_hen")
    private String vnoidung_hen;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("vtungay")
    private String vtungay;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("vdenngay")
    private String vdenngay;
    @JsonProperty("vnguoi_cn")
    private String vnguoi_cn;
    @JsonProperty("vmay_cn")
    private String vmay_cn;
}
