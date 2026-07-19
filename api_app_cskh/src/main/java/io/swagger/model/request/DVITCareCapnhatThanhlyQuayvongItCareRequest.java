package io.swagger.model.request;

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
public class DVITCareCapnhatThanhlyQuayvongItCareRequest {


    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }

    public Integer getVhdtb_id() {
        return vhdtb_id;
    }

    public void setVhdtb_id(Integer vhdtb_id) {
        this.vhdtb_id = vhdtb_id;
    }

    public Integer getVphieu_id() {
        return vphieu_id;
    }

    public void setVphieu_id(Integer vphieu_id) {
        this.vphieu_id = vphieu_id;
    }

    public Integer getVthuebao_id() {
        return vthuebao_id;
    }

    public void setVthuebao_id(Integer vthuebao_id) {
        this.vthuebao_id = vthuebao_id;
    }

    public Integer getVkqxl_id() {
        return vkqxl_id;
    }

    public void setVkqxl_id(Integer vkqxl_id) {
        this.vkqxl_id = vkqxl_id;
    }

    public String getVghichu() {
        return vghichu;
    }

    public void setVghichu(String vghichu) {
        this.vghichu = vghichu;
    }

    public String getVngay_hen() {
        return vngay_hen;
    }

    public void setVngay_hen(String vngay_hen) {
        this.vngay_hen = vngay_hen;
    }

    public String getVghichu_hen() {
        return vghichu_hen;
    }

    public void setVghichu_hen(String vghichu_hen) {
        this.vghichu_hen = vghichu_hen;
    }

    public String getVten_kh() {
        return vten_kh;
    }

    public void setVten_kh(String vten_kh) {
        this.vten_kh = vten_kh;
    }

    public String getVdienthoai_kh() {
        return vdienthoai_kh;
    }

    public void setVdienthoai_kh(String vdienthoai_kh) {
        this.vdienthoai_kh = vdienthoai_kh;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public String getVma_nd() {
        return vma_nd;
    }

    public void setVma_nd(String vma_nd) {
        this.vma_nd = vma_nd;
    }

    @JsonProperty("vkieu")
    private Integer vkieu;
    @JsonProperty("vhdtb_id")
    private Integer vhdtb_id;
    @JsonProperty("vphieu_id")
    private Integer vphieu_id;
    @JsonProperty("vthuebao_id")
    private Integer vthuebao_id;
    @JsonProperty("vkqxl_id")
    private Integer vkqxl_id;
    @JsonProperty("vghichu")
    private String vghichu;
    @JsonProperty("vngay_hen")
    private String vngay_hen;
    @JsonProperty("vghichu_hen")
    private String vghichu_hen;
    @JsonProperty("vten_kh")
    private String vten_kh;
    @JsonProperty("vdienthoai_kh")
    private String vdienthoai_kh;
    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;
    @JsonProperty("vma_nd")
    private String vma_nd;
}
