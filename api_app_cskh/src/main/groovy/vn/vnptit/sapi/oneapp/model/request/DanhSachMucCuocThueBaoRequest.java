package vn.vnptit.sapi.oneapp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * 4) Danh sách Mức cước thuê bao
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhSachMucCuocThueBaoRequest {
    @JsonProperty("vkhuld_id")
    private Integer vkhuld_id;

    @JsonProperty("vapld_id")
    private Integer vapld_id;

    @JsonProperty("vphold_id")
    private Integer vphold_id;

    @JsonProperty("vphuongld_id")
    private Integer vphuongld_id;

    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;

    @JsonProperty("vdoituong_id")
    private Integer vdoituong_id;

    @JsonProperty("vtocdo_id")
    private Integer vtocdo_id;

    @JsonProperty("vmuccuoc_id")
    private Integer vmuccuoc_id;

    @JsonProperty("vngay")
    private String vngay;

    @JsonProperty("vmuccuoctb_id")
    private Integer vmuccuoctb_id;

    @JsonProperty("vkieuld_id")
    private Integer vkieuld_id;

    public Integer getVmuccuoctb_id() {
        return vmuccuoctb_id;
    }

    public void setVmuccuoctb_id(Integer vmuccuoctb_id) {
        this.vmuccuoctb_id = vmuccuoctb_id;
    }

    public Integer getVkieuld_id() {
        return vkieuld_id;
    }

    public void setVkieuld_id(Integer vkieuld_id) {
        this.vkieuld_id = vkieuld_id;
    }

    public Integer getVkhuld_id() {
        return vkhuld_id;
    }

    public void setVkhuld_id(Integer vkhuld_id) {
        this.vkhuld_id = vkhuld_id;
    }

    public Integer getVapld_id() {
        return vapld_id;
    }

    public void setVapld_id(Integer vapld_id) {
        this.vapld_id = vapld_id;
    }

    public Integer getVphold_id() {
        return vphold_id;
    }

    public void setVphold_id(Integer vphold_id) {
        this.vphold_id = vphold_id;
    }

    public Integer getVphuongld_id() {
        return vphuongld_id;
    }

    public void setVphuongld_id(Integer vphuongld_id) {
        this.vphuongld_id = vphuongld_id;
    }

    public Integer getVloaitb_id() {
        return vloaitb_id;
    }

    public void setVloaitb_id(Integer vloaitb_id) {
        this.vloaitb_id = vloaitb_id;
    }

    public Integer getVdoituong_id() {
        return vdoituong_id;
    }

    public void setVdoituong_id(Integer vdoituong_id) {
        this.vdoituong_id = vdoituong_id;
    }

    public Integer getVtocdo_id() {
        return vtocdo_id;
    }

    public void setVtocdo_id(Integer vtocdo_id) {
        this.vtocdo_id = vtocdo_id;
    }

    public Integer getVmuccuoc_id() {
        return vmuccuoc_id;
    }

    public void setVmuccuoc_id(Integer vmuccuoc_id) {
        this.vmuccuoc_id = vmuccuoc_id;
    }

    public String getVngay() {
        return vngay;
    }

    public void setVngay(String vngay) {
        this.vngay = vngay;
    }
}
