package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DoiPassIMSRequest {

    @JsonProperty("loaiTBId")
    private Long loaiTBId;
    @JsonProperty("kieu")
    private Integer kieu;
    @JsonProperty("thueBaoId")
    private Long thueBaoId;
    @JsonProperty("hdtbId")
    private Long hdtbId;
    @JsonProperty("maTB")
    private String maTB;
    @JsonProperty("newPassword")
    private String newPassword;

    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(Long loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
