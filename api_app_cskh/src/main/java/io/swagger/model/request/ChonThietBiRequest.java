package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;

/**
 * Danh sách chọn thiết bị
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChonThietBiRequest {

    @JsonProperty("tenTb")
    private String tenTb;

    @JsonProperty("nhomTbId")
    private Long nhomTbId;

    @JsonProperty("loaiTbId")
    private Long loaiTbId;

    @JsonProperty("kieuTbId")
    private String kieuTbId;

    @JsonProperty("donViId")
    private Long donViId;

    @JsonProperty("dungLuongId")
    private Long dungLuongId;

    @JsonProperty("lopMcId")
    private Long lopMcId;

    @JsonProperty("phanLoaiKcId")
    private Long phanLoaiKcId;

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public Long getNhomTbId() {
        return nhomTbId;
    }

    public void setNhomTbId(Long nhomTbId) {
        this.nhomTbId = nhomTbId;
    }

    public Long getLoaiTbId() {
        return loaiTbId;
    }

    public void setLoaiTbId(Long loaiTbId) {
        this.loaiTbId = loaiTbId;
    }

    public String getKieuTbId() {
        return kieuTbId;
    }

    public void setKieuTbId(String kieuTbId) {
        this.kieuTbId = kieuTbId;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getDungLuongId() {
        return dungLuongId;
    }

    public void setDungLuongId(Long dungLuongId) {
        this.dungLuongId = dungLuongId;
    }

    public Long getLopMcId() {
        return lopMcId;
    }

    public void setLopMcId(Long lopMcId) {
        this.lopMcId = lopMcId;
    }

    public Long getPhanLoaiKcId() {
        return phanLoaiKcId;
    }

    public void setPhanLoaiKcId(Long phanLoaiKcId) {
        this.phanLoaiKcId = phanLoaiKcId;
    }
}
