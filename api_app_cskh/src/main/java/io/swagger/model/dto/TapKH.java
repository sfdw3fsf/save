package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Constant;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Báo tồn
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TapKH {
    @JsonProperty("khachHangId")
    private Long khachHangId;
    @JsonProperty("maKH")
    private String maKH;
    @JsonProperty("tenKH")
    private String tenKH;
    @JsonProperty("diaChiKH")
    private String diaChiKH;
    @JsonProperty("lienHe")
    private String lienHe;
    @JsonProperty("maTB")
    private String maTB;
    @JsonProperty("loaiHinh")
    private String loaiHinh;
    @JsonProperty("hds")
    private String hds;

    public TapKH convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME1);
            khachHangId = rs.getLong("KHACHHANG_ID");
            maKH = rs.getString("MA_KH");
            tenKH = rs.getString("TEN_KH");
            diaChiKH = rs.getString("DIACHI_KH");
            lienHe = rs.getString("LIENHE");
            maTB = rs.getString("MA_TB");
            loaiHinh = rs.getString("LOAIHINH");
            hds = rs.getString("HDS");
            return this;
        } else {
            return null;
        }
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }

    public String getLienHe() {
        return lienHe;
    }

    public void setLienHe(String lienHe) {
        this.lienHe = lienHe;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getLoaiHinh() {
        return loaiHinh;
    }

    public void setLoaiHinh(String loaiHinh) {
        this.loaiHinh = loaiHinh;
    }

    public String getHds() {
        return hds;
    }

    public void setHds(String hds) {
        this.hds = hds;
    }
}
