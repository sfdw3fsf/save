package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 2) Hàm cập nhật thông tin Khách hàng/ Thanh toán/ Thuê bao theo đối tượng thuê bao tra cứu
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CapNhatHDTDTTTheoThueBaoRequest {
    @JsonProperty("p_hdkh_id")
    private Integer p_hdkh_id;

    @JsonProperty("p_thuebao_id")
    private Integer p_thuebao_id;

    @JsonProperty("p_kieuld_id")
    private Integer p_kieuld_id;

    @JsonProperty("p_dulieu")
    private String p_dulieu;

    public Integer getP_hdkh_id() {
        return p_hdkh_id;
    }

    public void setP_hdkh_id(Integer p_hdkh_id) {
        this.p_hdkh_id = p_hdkh_id;
    }

    public Integer getP_thuebao_id() {
        return p_thuebao_id;
    }

    public void setP_thuebao_id(Integer p_thuebao_id) {
        this.p_thuebao_id = p_thuebao_id;
    }
    
    public Integer getP_kieuld_id() {
        return p_kieuld_id;
    }

    public void setP_kieuld_id(Integer p_kieuld_id) {
        this.p_kieuld_id = p_kieuld_id;
    }

    public String getP_dulieu() {
        return p_dulieu;
    }

    public void setP_dulieu(String p_dulieu) {
        this.p_dulieu = p_dulieu;
    }
}

