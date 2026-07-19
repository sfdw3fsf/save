package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.TraLoiKS;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * CapNhatKSRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CapNhatKSRequest {

    @JsonProperty("tltbId")
    private Long tltbId;
    @JsonProperty("hdtbId")
    private Long hdtbId;
    @JsonProperty("ghiChu")
    private String ghiChu;
    @JsonProperty("tenNV")
    private String tenNV;
    @JsonProperty("sdtNv")
    private String sdtNV;
    @JsonProperty("htks_id")
    private Long htks_id;
    @JsonProperty("dsTraLoi")
    private List<TraLoiKS> dsTraLoi;

    public Long getTltbId() {
        return tltbId;
    }

    public void setTltbId(Long tltbId) {
        this.tltbId = tltbId;
    }

    public Long getHtks_id() {
        return htks_id;
    }

    public void setHtks_id(Long htks_id) {
        this.htks_id = htks_id;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<TraLoiKS> getDsTraLoi() {
        return dsTraLoi;
    }

    public void setDsTraLoi(List<TraLoiKS> dsTraLoi) {
        this.dsTraLoi = dsTraLoi;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdtNV() {
        return sdtNV;
    }

    public void setSdtNV(String sdtNV) {
        this.sdtNV = sdtNV;
    }
}
