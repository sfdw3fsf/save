package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.dto.CNThueBaoChenNo;
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
public class HoanThienHSKhieuNaiRequest {

    @JsonProperty("khieuNaiId")
    private Long khieuNaiId;
    @JsonProperty("phieuKhieuNaiId")
    private Long phieuKhieuNaiId;
    @JsonProperty("nhanVienGQId")
    private Long nhanVienGQId;
    @JsonProperty("dsgc")
    private List<DSGC> dsgc;
    @JsonProperty("kqxlId")
    private Long kqxlId;
    @JsonProperty("nguyenNhan")
    private String nguyenNhan;
    @JsonProperty("traLoi")
    private String traLoi;
    @JsonProperty("xacMinh")
    private String xacMinh;
    @JsonProperty("ngayGQ")
    private String ngayGQ;
    @JsonProperty("paxlId")
    private Long paxlId;
    @JsonProperty("haiLong")
    private Integer haiLong;
    @JsonProperty("kqknId")
    private Long kqknId;

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

    public List<DSGC> getDsgc() {
        return dsgc;
    }

    public void setDsgc(List<DSGC> dsgc) {
        this.dsgc = dsgc;
    }

    public Long getKqxlId() {
        return kqxlId;
    }

    public void setKqxlId(Long kqxlId) {
        this.kqxlId = kqxlId;
    }

    public String getNguyenNhan() {
        return nguyenNhan;
    }

    public void setNguyenNhan(String nguyenNhan) {
        this.nguyenNhan = nguyenNhan;
    }

    public String getTraLoi() {
        return traLoi;
    }

    public void setTraLoi(String traLoi) {
        this.traLoi = traLoi;
    }

    public String getXacMinh() {
        return xacMinh;
    }

    public void setXacMinh(String xacMinh) {
        this.xacMinh = xacMinh;
    }

    public String getNgayGQ() {
        return ngayGQ;
    }

    public void setNgayGQ(String ngayGQ) {
        this.ngayGQ = ngayGQ;
    }

    public Long getPaxlId() {
        return paxlId;
    }

    public void setPaxlId(Long paxlId) {
        this.paxlId = paxlId;
    }

    public Integer getHaiLong() {
        return haiLong;
    }

    public void setHaiLong(Integer haiLong) {
        this.haiLong = haiLong;
    }

    public Long getKqknId() {
        return kqknId;
    }

    public void setKqknId(Long kqknId) {
        this.kqknId = kqknId;
    }
}
