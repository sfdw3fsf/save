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
public class DVITCareGiaoviecThuquayvongNvktRequest {


    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
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

    public Integer getVnhanvien_th_id() {
        return vnhanvien_th_id;
    }

    public void setVnhanvien_th_id(Integer vnhanvien_th_id) {
        this.vnhanvien_th_id = vnhanvien_th_id;
    }

    public Integer getVnhanvien_giao_id() {
        return vnhanvien_giao_id;
    }

    public void setVnhanvien_giao_id(Integer vnhanvien_giao_id) {
        this.vnhanvien_giao_id = vnhanvien_giao_id;
    }

    public String getVghichu() {
        return vghichu;
    }

    public void setVghichu(String vghichu) {
        this.vghichu = vghichu;
    }

    public Integer getVnhiemvu_id() {
        return vnhiemvu_id;
    }

    public void setVnhiemvu_id(Integer vnhiemvu_id) {
        this.vnhiemvu_id = vnhiemvu_id;
    }

    public String getVnhiemvu() {
        return vnhiemvu;
    }

    public void setVnhiemvu(String vnhiemvu) {
        this.vnhiemvu = vnhiemvu;
    }

    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vphieu_id")
    private Integer vphieu_id;

    @JsonProperty("vthuebao_id")
    private Integer vthuebao_id;

    @JsonProperty("vnhanvien_th_id")
    private Integer vnhanvien_th_id;

    @JsonProperty("vnhanvien_giao_id")
    private Integer vnhanvien_giao_id;

    @JsonProperty("vghichu")
    private String vghichu;

    @JsonProperty("vnhiemvu_id")
    private Integer vnhiemvu_id;

    @JsonProperty("vnhiemvu")
    private String vnhiemvu;


}
