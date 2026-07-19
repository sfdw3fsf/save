package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.Date;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class NghiemThuPhieuXLRequest extends BaseDTO {
    @JsonProperty("phieuId")
    private Long phieuId = null;
    @JsonProperty("ghiChu")
    private String ghiChu = null;
    @JsonProperty("nhanVienId")
    private Long nhanVienId = null;
    @JsonProperty("ngayCn")
    private String ngayCn = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;
    @JsonProperty("mayCn")
    private String mayCn = null;
    @JsonProperty("ipCn")
    private String ipCn = null;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
    }

    public String getIpCn() {
        return ipCn;
    }

    public void setIpCn(String ipCn) {
        this.ipCn = ipCn;
    }
}
