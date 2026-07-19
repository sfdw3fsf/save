package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
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
public class DVITCareCapnhatKQITCareMTRequest {


    public Integer getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Integer vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVbaohong_id() {
        return vbaohong_id;
    }

    public void setVbaohong_id(Integer vbaohong_id) {
        this.vbaohong_id = vbaohong_id;
    }

    public Integer getVtltb_id() {
        return vtltb_id;
    }

    public void setVtltb_id(Integer vtltb_id) {
        this.vtltb_id = vtltb_id;
    }

    public String getVnguoi_tl() {
        return vnguoi_tl;
    }

    public void setVnguoi_tl(String vnguoi_tl) {
        this.vnguoi_tl = vnguoi_tl;
    }

    public String getVdienthoai_lh() {
        return vdienthoai_lh;
    }

    public void setVdienthoai_lh(String vdienthoai_lh) {
        this.vdienthoai_lh = vdienthoai_lh;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public Integer getVhinhthuc() {
        return vhinhthuc;
    }

    public void setVhinhthuc(Integer vhinhthuc) {
        this.vhinhthuc = vhinhthuc;
    }

    public Integer getVtrangthai() {
        return vtrangthai;
    }

    public void setVtrangthai(Integer vtrangthai) {
        this.vtrangthai = vtrangthai;
    }

    public String getVghichu() {
        return vghichu;
    }

    public void setVghichu(String vghichu) {
        this.vghichu = vghichu;
    }

    public String getVds_traloi() {
        return vds_traloi;
    }

    public void setVds_traloi(String vds_traloi) {
        this.vds_traloi = vds_traloi;
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

    public String getVip_cn() {
        return vip_cn;
    }

    public void setVip_cn(String vip_cn) {
        this.vip_cn = vip_cn;
    }

    public Integer getVnguoidung_id() {
        return vnguoidung_id;
    }

    public void setVnguoidung_id(Integer vnguoidung_id) {
        this.vnguoidung_id = vnguoidung_id;
    }

    @JsonProperty("vhdtb_id")
    private Integer vhdtb_id;
    @JsonProperty("vbaohong_id")
    private Integer vbaohong_id;
    @JsonProperty("vtltb_id")
    private Integer vtltb_id;
    @JsonProperty("vnguoi_tl")
    private String vnguoi_tl;
    @JsonProperty("vdienthoai_lh")
    private String vdienthoai_lh;
    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;
    @JsonProperty("vhinhthuc")
    private Integer vhinhthuc;
    @JsonProperty("vtrangthai")
    private Integer vtrangthai;
    @JsonProperty("vghichu")
    private String vghichu;
    @JsonProperty("vds_traloi")
    private String vds_traloi;
    @JsonProperty("vnguoi_cn")
    private String vnguoi_cn;
    @JsonProperty("vmay_cn")
    private String vmay_cn;
    @JsonProperty("vip_cn")
    private String vip_cn;
    @JsonProperty("vnguoidung_id")
    private Integer vnguoidung_id;

}
