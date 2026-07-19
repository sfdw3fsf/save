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
public class DVITCareGhilaiThuoctinhSUBRequest {

    public Integer getVloai() {
        return vloai;
    }

    public void setVloai(Integer vloai) {
        this.vloai = vloai;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
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

    public List<DVITCareHIENTHITHONGTINDVCNTTResponse> getItemList() {
        return itemList;
    }

    public void setItemList(List<DVITCareHIENTHITHONGTINDVCNTTResponse> itemList) {
        this.itemList = itemList;
    }
    public Long getVhdtb_sub_id() {
        return vhdtb_sub_id;
    }

    public void setVhdtb_sub_id(Long vhdtb_sub_id) {
        this.vhdtb_sub_id = vhdtb_sub_id;
    }

    public Integer getKieu() {
        return kieu;
    }

    public void setKieu(Integer kieu) {
        this.kieu = kieu;
    }

    @JsonProperty("kieu")
    private Integer kieu;
    @JsonProperty("vloai")
    private Integer vloai;
    @JsonProperty("vid")
    private Long vid;
    @JsonProperty("vloaitb_id")
    private Integer vloaitb_id;
    @JsonProperty("vappend_name")
    private String vappend_name;
    @JsonProperty("vhdtb_sub_id")
    private Long vhdtb_sub_id;
    @JsonProperty("itemList")
    private List<DVITCareHIENTHITHONGTINDVCNTTResponse> itemList;


}
