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
public class ChuyenToKNRequest extends BaseDTO {
    @JsonProperty("khieuNaiId")
    private Long khieuNaiId = null;
    @JsonProperty("phieuKNId")
    private Long phieuKNId = null;
    @JsonProperty("noiDung")
    private String noiDung = null;
    @JsonProperty("donViNhanId")
    private Long donViNhanId = null;


    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public Long getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(Long phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getDonViNhanId() {
        return donViNhanId;
    }

    public void setDonViNhanId(Long donViNhanId) {
        this.donViNhanId = donViNhanId;
    }
}
