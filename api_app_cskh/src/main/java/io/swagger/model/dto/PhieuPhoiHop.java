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
public class PhieuPhoiHop {
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("khieuNaiId")
    private Long khieuNaiId = null;
    @JsonProperty("donViNhanId")
    private Long donViNhanId = null;
    @JsonProperty("huongGiaoId")
    private Long huongGiaoId = null;
    @JsonProperty("phieuChaId")
    private Long phieuChaId = null;
    @JsonProperty("soCV")
    private String soCV = null;

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public Long getDonViNhanId() {
        return donViNhanId;
    }

    public void setDonViNhanId(Long donViNhanId) {
        this.donViNhanId = donViNhanId;
    }

    public Long getHuongGiaoId() {
        return huongGiaoId;
    }

    public void setHuongGiaoId(Long huongGiaoId) {
        this.huongGiaoId = huongGiaoId;
    }

    public Long getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(Long phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public String getSoCV() {
        return soCV;
    }

    public void setSoCV(String soCV) {
        this.soCV = soCV;
    }
}
