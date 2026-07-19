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
public class KhoaPhieuRequest extends BaseDTO {
    @JsonProperty("suCoId")
    private Long suCoId = null;
    @JsonProperty("ngayTh")
    private String ngayTh = null;
    @JsonProperty("nhanVienThId")
    private Long nhanVienThId = null;
    @JsonProperty("dsNguyenNhan")
    private String dsNguyenNhan = null;
    @JsonProperty("ghiChuTh")
    private String ghiChuTh = null;
    @JsonProperty("khoaPhieu")
    private Long khoaPhieu = null;

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public String getNgayTh() {
        return ngayTh;
    }

    public void setNgayTh(String ngayTh) {
        this.ngayTh = ngayTh;
    }

    public Long getNhanVienThId() {
        return nhanVienThId;
    }

    public void setNhanVienThId(Long nhanVienThId) {
        this.nhanVienThId = nhanVienThId;
    }

    public String getDsNguyenNhan() {
        return dsNguyenNhan;
    }

    public void setDsNguyenNhan(String dsNguyenNhan) {
        this.dsNguyenNhan = dsNguyenNhan;
    }

    public String getGhiChuTh() {
        return ghiChuTh;
    }

    public void setGhiChuTh(String ghiChuTh) {
        this.ghiChuTh = ghiChuTh;
    }

    public Long getKhoaPhieu() {
        return khoaPhieu;
    }

    public void setKhoaPhieu(Long khoaPhieu) {
        this.khoaPhieu = khoaPhieu;
    }
}
