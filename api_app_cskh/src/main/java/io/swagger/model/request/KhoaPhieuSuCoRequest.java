package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;
import java.sql.Date;

/**
 * Khóa phiếu sự cố
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class KhoaPhieuSuCoRequest {

    @JsonProperty("suCoId")
    private Long suCoId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("ngayTh")
    private Date ngayTh;

    @JsonProperty("nhanVienThId")
    private Long nhanVienThId;

    @JsonProperty("dsNguyenNhan")
    private String dsNguyenNhan;

    @JsonProperty("ghiChuTh")
    private String ghiChuTh;

    @JsonProperty("nhanVienId")
    private Long nhanVienId;

    @JsonProperty("khoaPhieu")
    private Long khoaPhieu = 1L;

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

    public Long getSuCoId() {
        return suCoId;
    }

    public void setSuCoId(Long suCoId) {
        this.suCoId = suCoId;
    }

    public Date getNgayTh() {
        return ngayTh;
    }

    public void setNgayTh(Date ngayTh) {
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

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getKhoaPhieu() {
        return khoaPhieu;
    }

    public void setKhoaPhieu(Long khoaPhieu) {
        this.khoaPhieu = khoaPhieu;
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
