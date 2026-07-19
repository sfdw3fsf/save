package io.swagger.model.dto;

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
public class TinhTrang extends BaseDTO {
    @JsonProperty("phanVungId")
    private Long phanVungId = null;
    @JsonProperty("tinhTrangId")
    private Long tinhTrangId = null;
    @JsonProperty("tinhTrangSc")
    private String tinhTrangSc = null;
    @JsonProperty("nhomScId")
    private Long nhomScId = null;
    @JsonProperty("trangThai")
    private String trangThai = null;

    public Long getPhanVungId() {
        return phanVungId;
    }

    public void setPhanVungId(Long phanVungId) {
        this.phanVungId = phanVungId;
    }

    public Long getTinhTrangId() {
        return tinhTrangId;
    }

    public void setTinhTrangId(Long tinhTrangId) {
        this.tinhTrangId = tinhTrangId;
    }

    public String getTinhTrangSc() {
        return tinhTrangSc;
    }

    public void setTinhTrangSc(String tinhTrangSc) {
        this.tinhTrangSc = tinhTrangSc;
    }

    public Long getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Long nhomScId) {
        this.nhomScId = nhomScId;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
