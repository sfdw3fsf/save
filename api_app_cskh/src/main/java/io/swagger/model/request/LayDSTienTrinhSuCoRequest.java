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
public class LayDSTienTrinhSuCoRequest extends PageRequest {
    @JsonProperty("maSc")
    private String maSc = null;
    @JsonProperty("ttscId")
    private Long ttscId = null;
    @JsonProperty("nguonScId")
    private Long nguonScId = null;
    @JsonProperty("tuNgayYc")
    private String tuNgayYc = null;
    @JsonProperty("denNgayYc")
    private String denNgayYc = null;
    @JsonProperty("tuNgayHt")
    private String tuNgayHt = null;
    @JsonProperty("denNgayHt")
    private String denNgayHt = null;

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public Long getTtscId() {
        return ttscId;
    }

    public void setTtscId(Long ttscId) {
        this.ttscId = ttscId;
    }

    public Long getNguonScId() {
        return nguonScId;
    }

    public void setNguonScId(Long nguonScId) {
        this.nguonScId = nguonScId;
    }

    public String getTuNgayYc() {
        return tuNgayYc;
    }

    public void setTuNgayYc(String tuNgayYc) {
        this.tuNgayYc = tuNgayYc;
    }

    public String getDenNgayYc() {
        return denNgayYc;
    }

    public void setDenNgayYc(String denNgayYc) {
        this.denNgayYc = denNgayYc;
    }

    public String getTuNgayHt() {
        return tuNgayHt;
    }

    public void setTuNgayHt(String tuNgayHt) {
        this.tuNgayHt = tuNgayHt;
    }

    public String getDenNgayHt() {
        return denNgayHt;
    }

    public void setDenNgayHt(String denNgayHt) {
        this.denNgayHt = denNgayHt;
    }
}
