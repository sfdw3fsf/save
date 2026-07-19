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
public class GiaoPhieuXuLyRequest extends BaseDTO {
    @JsonProperty("phieuChaId")
    private Long phieuChaId = null;
    @JsonProperty("huongGiaoId")
    private Long huongGiaoId = null;
    @JsonProperty("ndGiao")
    private String ndGiao = null;
    @JsonProperty("suCoId")
    private Long suCoId = null;
    @JsonProperty("donViNhanId")
    private String donViNhanId = null;
    @JsonProperty("suCoPtm")
    private String suCoPtm = null;
    @JsonProperty("khan")
    private Long khan = null;
    @JsonProperty("chiTieuTg")
    private Long chiTieuTg = null;

    public Long getPhieuChaId() {
        return phieuChaId;
    }

    public void setPhieuChaId(Long phieuChaId) {
        this.phieuChaId = phieuChaId;
    }

    public Long getHuongGiaoId() {
        return huongGiaoId;
    }

    public void setHuongGiaoId(Long huongGiaoId) {
        this.huongGiaoId = huongGiaoId;
    }

    public String getNdGiao() {
        return ndGiao;
    }

    public void setNdGiao(String ndGiao) {
        this.ndGiao = ndGiao;
    }

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public String getDonViNhanId() {
        return donViNhanId;
    }

    public void setDonViNhanId(String donViNhanId) {
        this.donViNhanId = donViNhanId;
    }

    public String getSuCoPtm() {
        return suCoPtm;
    }

    public void setSuCoPtm(String suCoPtm) {
        this.suCoPtm = suCoPtm;
    }

    public Long getKhan() {
        return khan;
    }

    public void setKhan(Long khan) {
        this.khan = khan;
    }

    public Long getChiTieuTg() {
        return chiTieuTg;
    }

    public void setChiTieuTg(Long chiTieuTg) {
        this.chiTieuTg = chiTieuTg;
    }

}
