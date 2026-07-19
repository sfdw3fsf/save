package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 1) Danh sách Dịch vụ viễn thông
 */
public class DanhSachDichVuVienThongRequest {
    @JsonProperty("p_para1")
    private String p_para1;

    @JsonProperty("p_para2")
    private String p_para2;

    @JsonProperty("p_para3")
    private String p_para3;

    @JsonProperty("p_para4")
    private String p_para4;

    @JsonProperty("p_para5")
    private String p_para5;

    public String getP_para1() {
        return p_para1;
    }

    public void setP_para1(String p_para1) {
        this.p_para1 = p_para1;
    }

    public String getP_para2() {
        return p_para2;
    }

    public void setP_para2(String p_para2) {
        this.p_para2 = p_para2;
    }

    public String getP_para3() {
        return p_para3;
    }

    public void setP_para3(String p_para3) {
        this.p_para3 = p_para3;
    }

    public String getP_para4() {
        return p_para4;
    }

    public void setP_para4(String p_para4) {
        this.p_para4 = p_para4;
    }

    public String getP_para5() {
        return p_para5;
    }

    public void setP_para5(String p_para5) {
        this.p_para5 = p_para5;
    }
}
