package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Constant;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class BaoTon {

    @JsonProperty("phieuId")
    private Long phieuId;

    @JsonProperty("nnTonId")
    private Long nnTonId;

    @JsonProperty("nhomTonId")
    private Long nhomTonId;

    @JsonProperty("nnTon")
    private String nnTon;

    @JsonProperty("ngayBt")
    private String ngayBt;

    @JsonProperty("ghiChu")
    private String ghiChu;

    @JsonProperty("nhanVienBt")
    private String nhanVienBt;

    @JsonProperty("donViBt")
    private String donViBt;

    @JsonProperty("ngayCn")
    private String ngayCn;

    @JsonProperty("nguoiCn")
    private String nguoiCn;

    @JsonProperty("nhomTon")
    private String nhomTon;

    public BaoTon convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME1);
            phieuId = rs.getLong("PHIEU_ID");
            nnTonId = rs.getLong("NNTON_ID");
            nhomTonId = rs.getLong("NHOMTON_ID");
            nnTon = rs.getString("NN_TON");
            ghiChu = rs.getString("GHICHU");
            nhanVienBt = rs.getString("NHANVIEN_BT");
            donViBt = rs.getString("DONVI_BT");
            nguoiCn = rs.getString("NGUOI_CN");
            nhomTon = rs.getString("NHOMTON");
            try {
                Date dateBt = rs.getDate("NGAY_BT");
                ngayBt = df.format(dateBt);
            } catch (Exception e) {
                ngayBt = "";
            }
            try {
                Date dateBt = rs.getDate("NGAY_CN");
                ngayCn = df.format(dateBt);
            } catch (Exception e) {
                ngayCn = "";
            }
            return this;
        } else {
            return null;
        }
    }

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

    public Long getNhomTonId() {
        return nhomTonId;
    }

    public void setNhomTonId(Long nhomTonId) {
        this.nhomTonId = nhomTonId;
    }

    public String getNnTon() {
        return nnTon;
    }

    public void setNnTon(String nnTon) {
        this.nnTon = nnTon;
    }

    public String getNgayBt() {
        return ngayBt;
    }

    public void setNgayBt(String ngayBt) {
        this.ngayBt = ngayBt;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNhanVienBt() {
        return nhanVienBt;
    }

    public void setNhanVienBt(String nhanVienBt) {
        this.nhanVienBt = nhanVienBt;
    }

    public String getDonViBt() {
        return donViBt;
    }

    public void setDonViBt(String donViBt) {
        this.donViBt = donViBt;
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

    public String getNhomTon() {
        return nhomTon;
    }

    public void setNhomTon(String nhomTon) {
        this.nhomTon = nhomTon;
    }
}
