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
public class KhachHangTiemNangRequest {
    @JsonProperty("loaiTBId")
    private Long loaiTBId;
    @JsonProperty("nhanVienId")
    private Long nhanVienId;
    @JsonProperty("kqxlId")
    private Long kqxlId;
    @JsonProperty("tuNgay")
    private String tuNgay;
    @JsonProperty("denNgay")
    private String denNgay;
    @JsonProperty("thangHetHan")
    private String thangHetHan;

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getKqxlId() {
        return kqxlId;
    }

    public void setKqxlId(Long kqxlId) {
        this.kqxlId = kqxlId;
    }

    public Long getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(Long loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public String getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(String tuNgay) {
        this.tuNgay = tuNgay;
    }

    public String getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(String denNgay) {
        this.denNgay = denNgay;
    }

    public String getThangHetHan() {
        return thangHetHan;
    }

    public void setThangHetHan(String thangHetHan) {
        this.thangHetHan = thangHetHan;
    }
}
