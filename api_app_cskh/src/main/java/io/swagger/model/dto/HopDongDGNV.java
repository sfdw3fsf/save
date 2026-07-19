package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import vn.vnptit.sapi.oneapp.util.Constant;

import javax.annotation.Generated;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * HopDongDGNV
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class HopDongDGNV {

    @JsonProperty("tbtlId")
    private Long tbtlId;
    @JsonProperty("hdkhId")
    private Long hdkhId;
    @JsonProperty("hdtbId")
    private Long hdtbId;
    @JsonProperty("maGD")
    private String maGD;
    @JsonProperty("maTB")
    private String maTB;
    @JsonProperty("loaiHinhTB")
    private String loaiHinhTB;
    @JsonProperty("tenTB")
    private String tenTB;
    @JsonProperty("diaChiLD")
    private String diaChiLD;
    @JsonProperty("ngayYc")
    private String ngayYc;
    @JsonProperty("ngayHt")
    private String ngayHt;
    @JsonProperty("loaiHD")
    private String loaiHD;

    public HopDongDGNV convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME2);
            tbtlId = rs.getLong("TBTL_ID");
            hdkhId = rs.getLong("HDKH_ID");
            hdtbId = rs.getLong("HDTB_ID");
            maGD = rs.getString("MA_GD");
            maTB = rs.getString("MA_TB");
            loaiHinhTB = rs.getString("LOAIHINH_TB");
            tenTB = rs.getString("TEN_TB");
            diaChiLD = rs.getString("DIACHI_LD");
            loaiHD = rs.getString("TEN_LOAIHD");
            try {
                Date dateBt = rs.getDate("NGAY_YC");
                ngayYc = df.format(dateBt);
            } catch (Exception e) {
                ngayYc = "";
            }
            try {
                Date dateBt = rs.getDate("NGAY_HT");
                ngayHt = df.format(dateBt);
            } catch (Exception e) {
                ngayHt = "";
            }
            return this;
        } else {
            return null;
        }
    }

    public Long getTbtlId() {
        return tbtlId;
    }

    public void setTbtlId(Long tbtlId) {
        this.tbtlId = tbtlId;
    }

    public Long getHdkhId() {
        return hdkhId;
    }

    public void setHdkhId(Long hdkhId) {
        this.hdkhId = hdkhId;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public String getMaGD() {
        return maGD;
    }

    public void setMaGD(String maGD) {
        this.maGD = maGD;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getLoaiHinhTB() {
        return loaiHinhTB;
    }

    public void setLoaiHinhTB(String loaiHinhTB) {
        this.loaiHinhTB = loaiHinhTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getDiaChiLD() {
        return diaChiLD;
    }

    public void setDiaChiLD(String diaChiLD) {
        this.diaChiLD = diaChiLD;
    }

    public String getNgayYc() {
        return ngayYc;
    }

    public void setNgayYc(String ngayYc) {
        this.ngayYc = ngayYc;
    }

    public String getNgayHt() {
        return ngayHt;
    }

    public void setNgayHt(String ngayHt) {
        this.ngayHt = ngayHt;
    }

    public String getLoaiHD() {
        return loaiHD;
    }

    public void setLoaiHD(String loaiHD) {
        this.loaiHD = loaiHD;
    }
}
