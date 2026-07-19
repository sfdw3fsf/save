package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BaoTonRequest {

    @JsonProperty("phieuId")
    private Long phieuId;

    @JsonProperty("nnTonId")
    private Long nnTonId;

//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @JsonProperty("ngayBt")
//    private Date ngayBt;

    @JsonProperty("nhanVienBtId")
    private Long nhanVienBtId;
//
//    @JsonProperty("donViBtId")
//    private Long donViBtId;

    @JsonProperty("ghiChu")
    private String ghiChu;

//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @JsonProperty("ngayCn")
//    private Date ngayCn;
//
//    @JsonProperty("nguoiCn")
//    private String nguoiCn;
//
//    @JsonProperty("mayCn")
//    private String mayCn;
//
//    @JsonProperty("ipCn")
//    private String ipCn;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public Long getNnTonId() {
        return nnTonId;
    }

    public void setNnTonId(Long nnTonId) {
        this.nnTonId = nnTonId;
    }

//    public Date getNgayBt() {
//        return ngayBt;
//    }
//
//    public void setNgayBt(Date ngayBt) {
//        this.ngayBt = ngayBt;
//    }

    public Long getNhanVienBtId() {
        return nhanVienBtId;
    }

    public void setNhanVienBtId(Long nhanVienBtId) {
        this.nhanVienBtId = nhanVienBtId;
    }

//    public Long getDonViBtId() {
//        return donViBtId;
//    }
//
//    public void setDonViBtId(Long donViBtId) {
//        this.donViBtId = donViBtId;
//    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

//    public Date getNgayCn() {
//        return ngayCn;
//    }
//
//    public void setNgayCn(Date ngayCn) {
//        this.ngayCn = ngayCn;
//    }
//
//    public String getNguoiCn() {
//        return nguoiCn;
//    }
//
//    public void setNguoiCn(String nguoiCn) {
//        this.nguoiCn = nguoiCn;
//    }
//
//    public String getMayCn() {
//        return mayCn;
//    }
//
//    public void setMayCn(String mayCn) {
//        this.mayCn = mayCn;
//    }
//
//    public String getIpCn() {
//        return ipCn;
//    }
//
//    public void setIpCn(String ipCn) {
//        this.ipCn = ipCn;
//    }
}
