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
public class HuongGiaoPhieuKN {
    @JsonProperty("sms")
    private String sms = null;
    @JsonProperty("huongGiaoId")
    private Long huongGiaoId = null;
    @JsonProperty("huongGiao")
    private String huongGiao = null;
    @JsonProperty("giaoViec")
    private String giaoViec = null;
    @JsonProperty("giaoViecNVKD")
    private String giaoViecNVKD = null;

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public Long getHuongGiaoId() {
        return huongGiaoId;
    }

    public void setHuongGiaoId(Long huongGiaoId) {
        this.huongGiaoId = huongGiaoId;
    }

    public String getHuongGiao() {
        return huongGiao;
    }

    public void setHuongGiao(String huongGiao) {
        this.huongGiao = huongGiao;
    }

    public String getGiaoViec() {
        return giaoViec;
    }

    public void setGiaoViec(String giaoViec) {
        this.giaoViec = giaoViec;
    }

    public String getGiaoViecNVKD() {
        return giaoViecNVKD;
    }

    public void setGiaoViecNVKD(String giaoViecNVKD) {
        this.giaoViecNVKD = giaoViecNVKD;
    }
}
