package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * AnhNghiemThuSc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class AnhNghiemThuSc extends BaseDTO {
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("moTa")
    private List<FileApp> moTa = null;
    @JsonProperty("phieuId")
    private Long phieuId = null;

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public List<FileApp> getMoTa() {
        return moTa;
    }

    public void setMoTa(List<FileApp> moTa) {
        this.moTa = moTa;
    }

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }
}
