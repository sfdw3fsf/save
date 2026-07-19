package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.Date;

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareCapnhatHoanthanhKSCNTTRequest {

    @JsonProperty("vhdtb_id")
    private Integer vhdtb_id;

    @JsonProperty("vketquaks")
    private Integer vketquaks;

    @JsonProperty("vloaihd_id")
    private Integer vloaihd_id;

    @JsonProperty("vphieu_id")
    private Integer vphieu_id;

    @JsonProperty("vnhanvien_th_id")
    private Integer vnhanvien_th_id;

    @JsonProperty("vmay_cn")
    private String vmay_cn;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonProperty("vngay_ht")
    private String vngay_ht;

    @JsonProperty("vnguoi_cn")
    private String vnguoi_cn;

    public Integer getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Integer vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVketquaks() {
        return vketquaks;
    }

    public void setVketquaks(Integer vketquaks) {
        this.vketquaks = vketquaks;
    }

    public Integer getVloaihd_id() {
        return vloaihd_id;
    }

    public void setVloaihd_id(Integer vloaihd_id) {
        this.vloaihd_id = vloaihd_id;
    }

    public Integer getVphieu_id() {
        return vphieu_id;
    }

    public void setVphieu_id(Integer vphieu_id) {
        this.vphieu_id = vphieu_id;
    }

    public Integer getVnhanvien_th_id() {
        return vnhanvien_th_id;
    }

    public void setVnhanvien_th_id(Integer vnhanvien_th_id) {
        this.vnhanvien_th_id = vnhanvien_th_id;
    }

    public String getVmay_cn() {
        return vmay_cn;
    }

    public void setVmay_cn(String vmay_cn) {
        this.vmay_cn = vmay_cn;
    }

    public String getVngay_ht() {
        return vngay_ht;
    }

    public void setVngay_ht(String vngay_ht) {
        this.vngay_ht = vngay_ht;
    }

    public String getVnguoi_cn() {
        return vnguoi_cn;
    }

    public void setVnguoi_cn(String vnguoi_cn) {
        this.vnguoi_cn = vnguoi_cn;
    }
}
