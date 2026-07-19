package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-05-23")
@Schema
public class CapNhat_traloiks_mytvRequest {
    private Long p_khachhang_id;

    private String p_ten_kh;

    private String p_sodt_kh;

    private String p_diachi_kh;

    private String p_ghichu;

    private String p_traloi;

    private String p_may_cn;

    private String p_nguoi_cn;

    public String getP_may_cn() {
        return p_may_cn;
    }

    public void setP_may_cn(String p_may_cn) {
        this.p_may_cn = p_may_cn;
    }

    public String getP_nguoi_cn() {
        return p_nguoi_cn;
    }

    public void setP_nguoi_cn(String p_nguoi_cn) {
        this.p_nguoi_cn = p_nguoi_cn;
    }

    public Long getP_khachhang_id() {
        return p_khachhang_id;
    }

    public void setP_khachhang_id(Long p_khachhang_id) {
        this.p_khachhang_id = p_khachhang_id;
    }

    public String getP_ten_kh() {
        return p_ten_kh;
    }

    public void setP_ten_kh(String p_ten_kh) {
        this.p_ten_kh = p_ten_kh;
    }

    public String getP_sodt_kh() {
        return p_sodt_kh;
    }

    public void setP_sodt_kh(String p_sodt_kh) {
        this.p_sodt_kh = p_sodt_kh;
    }

    public String getP_diachi_kh() {
        return p_diachi_kh;
    }

    public void setP_diachi_kh(String p_diachi_kh) {
        this.p_diachi_kh = p_diachi_kh;
    }

    public String getP_ghichu() {
        return p_ghichu;
    }

    public void setP_ghichu(String p_ghichu) {
        this.p_ghichu = p_ghichu;
    }

    public String getP_traloi() {
        return p_traloi;
    }

    public void setP_traloi(String p_traloi) {
        this.p_traloi = p_traloi;
    }
}
