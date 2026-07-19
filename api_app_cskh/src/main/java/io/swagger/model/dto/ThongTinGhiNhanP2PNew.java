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
public class ThongTinGhiNhanP2PNew {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("service")
    private String service;
    @JsonProperty("idNv")
    private Long idNv;
    @JsonProperty("maNv")
    private String maNv;
    @JsonProperty("tenNv")
    private String tenNv;
    @JsonProperty("mediaStatus")
    private Long mediaStatus;
    @JsonProperty("maTB")
    private String maTB;
    @JsonProperty("ngayCn")
    private String ngayCn;
    @JsonProperty("sdtNv")
    private String sdtNv;
    @JsonProperty("donVi")
    private String donVi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Long getIdNv() {
        return idNv;
    }

    public void setIdNv(Long idNv) {
        this.idNv = idNv;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public Long getMediaStatus() {
        return mediaStatus;
    }

    public void setMediaStatus(Long mediaStatus) {
        this.mediaStatus = mediaStatus;
    }

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getSdtNv() {
        return sdtNv;
    }

    public void setSdtNv(String sdtNv) {
        this.sdtNv = sdtNv;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
}
