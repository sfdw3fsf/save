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
 * ThongTinGhiNhanP2P
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThongTinGhiNhanP2P {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nhanVienGTViId")
    private Long nhanVienGTViId;
    @JsonProperty("nhanVienGTMMId")
    private Long nhanVienGTMMId;
    @JsonProperty("nhanVienGTP2PId")
    private Long nhanVienGTP2PId;
    @JsonProperty("maTB")
    private String maTB;
    @JsonProperty("viVNPTPay")
    private String viVNPTPay;
    @JsonProperty("mobileMoney")
    private String mobileMoney;
    @JsonProperty("p2p")
    private String p2p;
    @JsonProperty("ngayCnVi")
    private String ngayCnVi;
    @JsonProperty("ngayCnMM")
    private String ngayCnMM;
    @JsonProperty("ngayCnP2P")
    private String ngayCnP2P;
    @JsonProperty("maNhanVienGTVi")
    private String maNhanVienGTVi;
    @JsonProperty("maNhanVienGTMM")
    private String maNhanVienGTMM;
    @JsonProperty("maNhanVienGTP2P")
    private String maNhanVienGTP2P;
    @JsonProperty("tenNhanVienGTVi")
    private String tenNhanVienGTVi;
    @JsonProperty("tenNhanVienGTMM")
    private String tenNhanVienGTMM;
    @JsonProperty("tenNhanVienGTP2P")
    private String tenNhanVienGTP2P;
    @JsonProperty("sdtNhanVienGTVi")
    private String sdtNhanVienGTVi;
    @JsonProperty("sdtNhanVienGTMM")
    private String sdtNhanVienGTMM;
    @JsonProperty("sdtNhanVienGTP2P")
    private String sdtNhanVienGTP2P;
    @JsonProperty("dvNhanVienGTVi")
    private String dvNhanVienGTVi;
    @JsonProperty("dvNhanVienGTMM")
    private String dvNhanVienGTMM;
    @JsonProperty("dvNhanVienGTP2P")
    private String dvNhanVienGTP2P;

    public ThongTinGhiNhanP2P convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            DateFormat df = new SimpleDateFormat(Constant.FORMAT_DATETIME);
            nhanVienGTViId = rs.getLong("NHANVIENGT_VI_ID");
            nhanVienGTMMId = rs.getLong("NHANVIENGT_MM_ID");
            nhanVienGTP2PId = rs.getLong("NHANVIENGT_P2P_ID");
            maTB = rs.getString("MA_TB");
            viVNPTPay = rs.getString("VI_VNPTPAY");
            mobileMoney = rs.getString("MOBILE_MONEY");
            p2p = rs.getString("P2P");
            try {
                Date dateBt = rs.getDate("NGAYCN_VI");
                ngayCnVi = df.format(dateBt);
            } catch (Exception e) {
                try {
                    ngayCnVi = rs.getString("NGAYCN_VI");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ngayCnVi = "";
                }
            }
            try {
                Date dateBt = rs.getDate("NGAYCN_MM");
                ngayCnMM = df.format(dateBt);
            } catch (Exception e) {
                try {
                    ngayCnMM = rs.getString("NGAYCN_MM");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ngayCnMM = "";
                }
            }
            try {
                Date dateBt = rs.getDate("NGAYCN_P2P");
                ngayCnP2P = df.format(dateBt);
            } catch (Exception e) {
                try {
                    ngayCnP2P = rs.getString("NGAYCN_P2P");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ngayCnP2P = "";
                }
            }
            return this;
        } else {
            return null;
        }
    }

    public Long getNhanVienGTViId() {
        return nhanVienGTViId;
    }

    public void setNhanVienGTViId(Long nhanVienGTViId) {
        this.nhanVienGTViId = nhanVienGTViId;
    }

    public Long getNhanVienGTMMId() {
        return nhanVienGTMMId;
    }

    public void setNhanVienGTMMId(Long nhanVienGTMMId) {
        this.nhanVienGTMMId = nhanVienGTMMId;
    }

    public Long getNhanVienGTP2PId() {
        return nhanVienGTP2PId;
    }

    public void setNhanVienGTP2PId(Long nhanVienGTP2PId) {
        this.nhanVienGTP2PId = nhanVienGTP2PId;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getViVNPTPay() {
        return viVNPTPay;
    }

    public void setViVNPTPay(String viVNPTPay) {
        this.viVNPTPay = viVNPTPay;
    }

    public String getMobileMoney() {
        return mobileMoney;
    }

    public void setMobileMoney(String mobileMoney) {
        this.mobileMoney = mobileMoney;
    }

    public String getP2p() {
        return p2p;
    }

    public void setP2p(String p2p) {
        this.p2p = p2p;
    }

    public String getNgayCnVi() {
        return ngayCnVi;
    }

    public void setNgayCnVi(String ngayCnVi) {
        this.ngayCnVi = ngayCnVi;
    }

    public String getNgayCnMM() {
        return ngayCnMM;
    }

    public void setNgayCnMM(String ngayCnMM) {
        this.ngayCnMM = ngayCnMM;
    }

    public String getNgayCnP2P() {
        return ngayCnP2P;
    }

    public void setNgayCnP2P(String ngayCnP2P) {
        this.ngayCnP2P = ngayCnP2P;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaNhanVienGTVi() {
        return maNhanVienGTVi;
    }

    public void setMaNhanVienGTVi(String maNhanVienGTVi) {
        this.maNhanVienGTVi = maNhanVienGTVi;
    }

    public String getMaNhanVienGTMM() {
        return maNhanVienGTMM;
    }

    public void setMaNhanVienGTMM(String maNhanVienGTMM) {
        this.maNhanVienGTMM = maNhanVienGTMM;
    }

    public String getMaNhanVienGTP2P() {
        return maNhanVienGTP2P;
    }

    public void setMaNhanVienGTP2P(String maNhanVienGTP2P) {
        this.maNhanVienGTP2P = maNhanVienGTP2P;
    }

    public String getTenNhanVienGTVi() {
        return tenNhanVienGTVi;
    }

    public void setTenNhanVienGTVi(String tenNhanVienGTVi) {
        this.tenNhanVienGTVi = tenNhanVienGTVi;
    }

    public String getTenNhanVienGTMM() {
        return tenNhanVienGTMM;
    }

    public void setTenNhanVienGTMM(String tenNhanVienGTMM) {
        this.tenNhanVienGTMM = tenNhanVienGTMM;
    }

    public String getTenNhanVienGTP2P() {
        return tenNhanVienGTP2P;
    }

    public void setTenNhanVienGTP2P(String tenNhanVienGTP2P) {
        this.tenNhanVienGTP2P = tenNhanVienGTP2P;
    }

    public String getSdtNhanVienGTVi() {
        return sdtNhanVienGTVi;
    }

    public void setSdtNhanVienGTVi(String sdtNhanVienGTVi) {
        this.sdtNhanVienGTVi = sdtNhanVienGTVi;
    }

    public String getSdtNhanVienGTMM() {
        return sdtNhanVienGTMM;
    }

    public void setSdtNhanVienGTMM(String sdtNhanVienGTMM) {
        this.sdtNhanVienGTMM = sdtNhanVienGTMM;
    }

    public String getSdtNhanVienGTP2P() {
        return sdtNhanVienGTP2P;
    }

    public void setSdtNhanVienGTP2P(String sdtNhanVienGTP2P) {
        this.sdtNhanVienGTP2P = sdtNhanVienGTP2P;
    }

    public String getDvNhanVienGTVi() {
        return dvNhanVienGTVi;
    }

    public void setDvNhanVienGTVi(String dvNhanVienGTVi) {
        this.dvNhanVienGTVi = dvNhanVienGTVi;
    }

    public String getDvNhanVienGTMM() {
        return dvNhanVienGTMM;
    }

    public void setDvNhanVienGTMM(String dvNhanVienGTMM) {
        this.dvNhanVienGTMM = dvNhanVienGTMM;
    }

    public String getDvNhanVienGTP2P() {
        return dvNhanVienGTP2P;
    }

    public void setDvNhanVienGTP2P(String dvNhanVienGTP2P) {
        this.dvNhanVienGTP2P = dvNhanVienGTP2P;
    }
}
