package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Chọn thiết bị
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CayThietBi {

    @JsonProperty("thietBiId")
    private String thietBiId;

    @JsonProperty("thietBiChaId")
    private String thietBiChaId;

    @JsonProperty("tenTb")
    private String tenTb;

    @JsonProperty("hinhAnh")
    private String hinhAnh;

    @JsonProperty("viTri")
    private String viTri;

    public String getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(String thietBiId) {
        this.thietBiId = thietBiId;
    }

    public String getThietBiChaId() {
        return thietBiChaId;
    }

    public void setThietBiChaId(String thietBiChaId) {
        this.thietBiChaId = thietBiChaId;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
}
