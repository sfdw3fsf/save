package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class PhieuTra {
    @JsonProperty("ndTra")
    private String ndTra = null;
    @JsonProperty("ghiChuTH")
    private String ghiChuTH = null;
    @JsonProperty("phieuKNId")
    private Long phieuKNId = null;
    @JsonProperty("khieuNaiId")
    private Long khieuNaiId = null;

    public String getNdTra() {
        return ndTra;
    }

    public void setNdTra(String ndTra) {
        this.ndTra = ndTra;
    }

    public String getGhiChuTH() {
        return ghiChuTH;
    }

    public void setGhiChuTH(String ghiChuTH) {
        this.ghiChuTH = ghiChuTH;
    }

    public Long getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(Long phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }
}
