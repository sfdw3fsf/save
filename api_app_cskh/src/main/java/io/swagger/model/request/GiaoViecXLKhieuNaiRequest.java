package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.DSGC;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class GiaoViecXLKhieuNaiRequest {

    @JsonProperty("khieuNaiId")
    private Long khieuNaiId;
    @JsonProperty("phieuKhieuNaiId")
    private Long phieuKhieuNaiId;
    @JsonProperty("nhanVienGQId")
    private Long nhanVienGQId;
    @JsonProperty("ghiChu")
    private String ghiChu;

    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public Long getPhieuKhieuNaiId() {
        return phieuKhieuNaiId;
    }

    public void setPhieuKhieuNaiId(Long phieuKhieuNaiId) {
        this.phieuKhieuNaiId = phieuKhieuNaiId;
    }

    public Long getNhanVienGQId() {
        return nhanVienGQId;
    }

    public void setNhanVienGQId(Long nhanVienGQId) {
        this.nhanVienGQId = nhanVienGQId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
