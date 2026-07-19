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
public class GiaoPhieuXuLyTTSRequest extends BaseDTO {
    @JsonProperty("phieuChaId")
    private Long phieuChaId = null;
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("suCoId")
    private String suCoId = null;
    @JsonProperty("donViNhanId")
    private String donViNhanId = null;
    @JsonProperty("maBuoc")
    private String maBuoc = null;

    public Long getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(Long phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public String getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(String suCoId) {
        this.suCoId = suCoId;
    }

    public String getDonViNhanId() {
        return donViNhanId;
    }

    public void setDonViNhanId(String donViNhanId) {
        this.donViNhanId = donViNhanId;
    }

    public String getMaBuoc() {
        return maBuoc;
    }

    public void setMaBuoc(String maBuoc) {
        this.maBuoc = maBuoc;
    }
}
