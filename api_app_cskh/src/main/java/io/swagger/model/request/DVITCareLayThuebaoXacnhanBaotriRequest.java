package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 *  Dịch vụ IT Care
 *  Danh sách phiếu yêu cầu khảo sát
 */

@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-03-13")
@Schema
public class DVITCareLayThuebaoXacnhanBaotriRequest {


    public Integer getVtag() {
        return vtag;
    }

    public void setVtag(Integer vtag) {
        this.vtag = vtag;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getVnhanvien_id() {
        return vnhanvien_id;
    }

    public void setVnhanvien_id(Integer vnhanvien_id) {
        this.vnhanvien_id = vnhanvien_id;
    }

    @JsonProperty("vtag")
    private Integer vtag;

    @JsonProperty("vid")
    private Integer vid;

    @JsonProperty("vnhanvien_id")
    private Integer vnhanvien_id;

}
