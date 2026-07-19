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
import java.util.ArrayList;
import java.util.List;

/**
 * CauHoiKS
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChuyenNetMediaXLKN {
    @JsonProperty("maTB")
    private String maTB;
    @JsonProperty("kieu")
    private Integer kieu;
    @JsonProperty("dsPhieu")
    private List<PhieuChuyenNetMedia> dsPhieu;
    @JsonProperty("phieuKNId")
    private Long phieuKNId;

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    public List<PhieuChuyenNetMedia> getDsPhieu() {
        return dsPhieu;
    }

    public void setDsPhieu(List<PhieuChuyenNetMedia> dsPhieu) {
        this.dsPhieu = dsPhieu;
    }

    public Long getPhieuKNId() {
        return phieuKNId;
    }

    public void setPhieuKNId(Long phieuKNId) {
        this.phieuKNId = phieuKNId;
    }
}
