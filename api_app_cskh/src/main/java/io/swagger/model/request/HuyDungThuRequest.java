package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class HuyDungThuRequest extends BaseDTO {
    @JsonProperty("hdtbId")
    private Long hdtbId = null;
    @JsonProperty("lyDoHuyId")
    private Long lyDoHuyId = null;
    @JsonProperty("thangHetHan")
    private Long thangHetHan = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("maTB")
    private String maTB = null;

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getLyDoHuyId() {
        return lyDoHuyId;
    }

    public void setLyDoHuyId(Long lyDoHuyId) {
        this.lyDoHuyId = lyDoHuyId;
    }

    public Long getThangHetHan() {
        return thangHetHan;
    }

    public void setThangHetHan(Long thangHetHan) {
        this.thangHetHan = thangHetHan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }
}
