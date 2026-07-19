package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 3) Danh sách Tài khoản chính
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhSachTaiKhoanChinhRequest {
    @JsonProperty("vkieu")
    private Integer vkieu;

    @JsonProperty("vma_tb")
    private String vma_tb;

    @JsonProperty("vUpper")
    private Integer vUpper;

    @JsonProperty("vtocdo_id")
    private Integer vtocdo_id;

    @JsonProperty("vkhachhang_id")
    private Integer vkhachhang_id;

    @JsonProperty("vhdkh_id")
    private Integer vhdkh_id;

    @JsonProperty("vdiachi_ld")
    private String vdiachi_ld;

    public Integer getVkieu() {
        return vkieu;
    }

    public void setVkieu(Integer vkieu) {
        this.vkieu = vkieu;
    }

    public String getVma_tb() {
        return vma_tb;
    }

    public void setVma_tb(String vma_tb) {
        this.vma_tb = vma_tb;
    }

    public Integer getvUpper() {
        return vUpper;
    }

    public void setvUpper(Integer vUpper) {
        this.vUpper = vUpper;
    }

    public Integer getVtocdo_id() {
        return vtocdo_id;
    }

    public void setVtocdo_id(Integer vtocdo_id) {
        this.vtocdo_id = vtocdo_id;
    }

    public Integer getVkhachhang_id() {
        return vkhachhang_id;
    }

    public void setVkhachhang_id(Integer vkhachhang_id) {
        this.vkhachhang_id = vkhachhang_id;
    }

    public Integer getVhdkh_id() {
        return vhdkh_id;
    }

    public void setVhdkh_id(Integer vhdkh_id) {
        this.vhdkh_id = vhdkh_id;
    }

    public String getVdiachi_ld() {
        return vdiachi_ld;
    }

    public void setVdiachi_ld(String vdiachi_ld) {
        this.vdiachi_ld = vdiachi_ld;
    }
}
