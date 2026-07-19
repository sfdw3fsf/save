package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.annotation.Validated

import javax.annotation.Generated


/**
 *  Dịch vụ IT Care
 *
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareGhilaiThuoctinhSUBBDDVRequest {


    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    public Integer getVloai() {
        return vloai;
    }

    public void setVloai(Integer vloai) {
        this.vloai = vloai;
    }

    public Long getVthuebao_id() {
        return vthuebao_id;
    }

    public void setVthuebao_id(Long vthuebao_id) {
        this.vthuebao_id = vthuebao_id;
    }

    public Long getVbaohong_id() {
        return vbaohong_id;
    }

    public void setVbaohong_id(Long vbaohong_id) {
        this.vbaohong_id = vbaohong_id;
    }

    public Long getVthuebao_sub_id() {
        return vthuebao_sub_id;
    }

    public void setVthuebao_sub_id(Long vthuebao_sub_id) {
        this.vthuebao_sub_id = vthuebao_sub_id;
    }

    public Integer getVloaitb_id() {
        return vloaitb_id;
    }

    public void setVloaitb_id(Integer vloaitb_id) {
        this.vloaitb_id = vloaitb_id;
    }

    public String getVappend_name() {
        return vappend_name;
    }

    public void setVappend_name(String vappend_name) {
        this.vappend_name = vappend_name;
    }

    public Long getVhdtb_sub_id() {
        return vhdtb_sub_id;
    }

    public void setVhdtb_sub_id(Long vhdtb_sub_id) {
        this.vhdtb_sub_id = vhdtb_sub_id;
    }

    public List<DVITCareHIENTHITHONGTINDVCNTTResponse> getItemList() {
        return itemList;
    }

    public void setItemList(List<DVITCareHIENTHITHONGTINDVCNTTResponse> itemList) {
        this.itemList = itemList;
    }

    @JsonProperty("kieu")
    private Integer kieu;
    @JsonProperty("vloai")
    private Integer vloai;
    @JsonProperty("vthuebao_id")
    private Long vthuebao_id;
    @JsonProperty("vbaohong_id")
    private Long vbaohong_id;
    @JsonProperty("vthuebao_sub_id")
    private Long vthuebao_sub_id;
    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;
    @JsonProperty("vappend_name")
    private String vappend_name;
    @JsonProperty("vhdtb_sub_id")
    private Long vhdtb_sub_id;
    @JsonProperty("itemList")
    private List<DVITCareHIENTHITHONGTINDVCNTTResponse> itemList;
}
