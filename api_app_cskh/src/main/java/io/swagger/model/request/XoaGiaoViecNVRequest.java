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
public class XoaGiaoViecNVRequest {
    @JsonProperty("phieuKNId")
    private Long phieuKNId;
    @JsonProperty("nhanVienId")
    private Long nhanVienId;
    @JsonProperty("nhiemVuId")
    private Long nhiemVuId;

    public Long getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(Long phieuKNId) {
        this.phieuKNId = phieuKNId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getNhiemVuId() {
        return nhiemVuId;
    }

    public void setNhiemVuId(Long nhiemVuId) {
        this.nhiemVuId = nhiemVuId;
    }
}
