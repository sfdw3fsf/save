package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareGiaophieuPhoihopThicongRequest {

    public Integer getVphieu_id() {
        return vphieu_id;
    }

    public void setVphieu_id(Integer vphieu_id) {
        this.vphieu_id = vphieu_id;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    public String getVnoidung_giao() {
        return vnoidung_giao;
    }

    public void setVnoidung_giao(String vnoidung_giao) {
        this.vnoidung_giao = vnoidung_giao;
    }

    public Integer getVdonvi_nhan_id() {
        return vdonvi_nhan_id;
    }

    public void setVdonvi_nhan_id(Integer vdonvi_nhan_id) {
        this.vdonvi_nhan_id = vdonvi_nhan_id;
    }

    @JsonProperty("vphieu_id")
    private Integer vphieu_id;
    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;
    @JsonProperty("vnoidung_giao")
    private String vnoidung_giao;
    @JsonProperty("vdonvi_nhan_id")
    private Integer vdonvi_nhan_id;
}
