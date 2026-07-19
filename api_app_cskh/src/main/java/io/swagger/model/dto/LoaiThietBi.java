package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Loại thiết bị
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class LoaiThietBi {

    @JsonProperty("loaiTbId")
    private Long loaiTbId;

    @JsonProperty("loaiTb")
    private String loaiTb;

    @JsonProperty("ghiChu")
    private String ghiChu;

    @JsonProperty("maLoaiTb")
    private String maLoaiTb;

    @JsonProperty("congThuc")
    private String congThuc;

    public Long getLoaiTbId() {
        return loaiTbId;
    }

    public void setLoaiTbId(Long loaiTbId) {
        this.loaiTbId = loaiTbId;
    }

    public String getLoaiTb() {
        return loaiTb;
    }

    public void setLoaiTb(String loaiTb) {
        this.loaiTb = loaiTb;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaLoaiTb() {
        return maLoaiTb;
    }

    public void setMaLoaiTb(String maLoaiTb) {
        this.maLoaiTb = maLoaiTb;
    }

    public String getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(String congThuc) {
        this.congThuc = congThuc;
    }
}
