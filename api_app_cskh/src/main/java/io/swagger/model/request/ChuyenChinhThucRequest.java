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
public class ChuyenChinhThucRequest extends BaseDTO {
    @JsonProperty("hdtbId")
    private Long hdtbId = null;
    @JsonProperty("doiTuongId")
    private Long doiTuongId = null;
    @JsonProperty("ctvId")
    private Long ctvId = null;
    @JsonProperty("nhanVienGTId")
    private Long nhanVienGTId = null;
    @JsonProperty("maTB")
    private String maTB = null;

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public Long getDoiTuongId() {
        return doiTuongId;
    }

    public void setDoiTuongId(Long doiTuongId) {
        this.doiTuongId = doiTuongId;
    }

    public Long getCtvId() {
        return ctvId;
    }

    public void setCtvId(Long ctvId) {
        this.ctvId = ctvId;
    }

    public Long getNhanVienGTId() {
        return nhanVienGTId;
    }

    public void setNhanVienGTId(Long nhanVienGTId) {
        this.nhanVienGTId = nhanVienGTId;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }
}
