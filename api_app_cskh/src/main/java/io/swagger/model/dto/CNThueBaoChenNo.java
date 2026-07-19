package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CNThueBaoChenNo {
    @JsonProperty("maTT")
    private String maTT = null;
    @JsonProperty("dichVuVTId")
    private Long dichVuVTId = null;
    @JsonProperty("maTB")
    private String maTB = null;
    @JsonProperty("chuKyNo")
    private String chuKyNo = null;

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public Long getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Long dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getChuKyNo() {
        return chuKyNo;
    }

    public void setChuKyNo(String chuKyNo) {
        this.chuKyNo = chuKyNo;
    }
}
