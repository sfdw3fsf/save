package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Nhân viên
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class NhanVienResponse extends BaseDTO {
    @JsonProperty("nhanvien_id")
    private String nhanvien_id = null;
    @JsonProperty("ma_nv")
    private String ma_nv = null;
    @JsonProperty("ten_nv")
    private String ten_nv = null;
    @JsonProperty("diachi_nv")
    private String diachi_nv = null;
    @JsonProperty("so_dt")
    private String so_dt = null;
    @JsonProperty("ten_dv")
    private String ten_dv = null;
    @JsonProperty("ma_dv")
    private String ma_dv = null;
    @JsonProperty("ten_ht")
    private String ten_ht = null;
    @JsonProperty("ma_the")
    private String ma_the = null;

    public String getNhanvien_id() {
        return nhanvien_id;
    }

    public void setNhanvien_id(String nhanvien_id) {
        this.nhanvien_id = nhanvien_id;
    }

    public String getMa_nv() {
        return ma_nv;
    }

    public void setMa_nv(String ma_nv) {
        this.ma_nv = ma_nv;
    }

    public String getTen_nv() {
        return ten_nv;
    }

    public void setTen_nv(String ten_nv) {
        this.ten_nv = ten_nv;
    }

    public String getDiachi_nv() {
        return diachi_nv;
    }

    public void setDiachi_nv(String diachi_nv) {
        this.diachi_nv = diachi_nv;
    }

    public String getSo_dt() {
        return so_dt;
    }

    public void setSo_dt(String so_dt) {
        this.so_dt = so_dt;
    }

    public String getTen_dv() {
        return ten_dv;
    }

    public void setTen_dv(String ten_dv) {
        this.ten_dv = ten_dv;
    }

    public String getMa_dv() {
        return ma_dv;
    }

    public void setMa_dv(String ma_dv) {
        this.ma_dv = ma_dv;
    }

    public String getTen_ht() {
        return ten_ht;
    }

    public void setTen_ht(String ten_ht) {
        this.ten_ht = ten_ht;
    }

    public String getMa_the() {
        return ma_the;
    }

    public void setMa_the(String ma_the) {
        this.ma_the = ma_the;
    }
}
