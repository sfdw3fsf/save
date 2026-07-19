package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Chọn thiết bị
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChonThietBi {

    @JsonProperty("chon")
    private String chon;

    @JsonProperty("loaiTbId")
    private Long loaiTbId;

    @JsonProperty("thietBiId")
    private Long thietBiId;

    @JsonProperty("tenTb")
    private String tenTb;

    @JsonProperty("kieuTbId")
    private String kieuTbId;

    @JsonProperty("donViId")
    private Long donViId;

    @JsonProperty("kieuTbUid")
    private String kieuTbUid;

    public String getKieuTbId() {
        return kieuTbId;
    }

    public void setKieuTbId(String kieuTbId) {
        this.kieuTbId = kieuTbId;
    }

    public String getChon() {
        return chon;
    }

    public void setChon(String chon) {
        this.chon = chon;
    }

    public Long getLoaiTbId() {
        return loaiTbId;
    }

    public void setLoaiTbId(Long loaiTbId) {
        this.loaiTbId = loaiTbId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public String getKieuTbUid() {
        return kieuTbUid;
    }

    public void setKieuTbUid(String kieuTbUid) {
        this.kieuTbUid = kieuTbUid;
    }
}
