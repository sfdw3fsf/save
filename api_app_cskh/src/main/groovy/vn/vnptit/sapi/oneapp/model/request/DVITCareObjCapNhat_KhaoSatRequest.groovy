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
public class DVITCareObjCapNhat_KhaoSatRequest {

    @JsonProperty("isAdd")
    public Boolean isAdd;
    @JsonProperty("objHDKH_KS")
    public List<DVITCareObjHDKH_KS> objHDKH_KS;
    @JsonProperty("objHDTB_KS")
    public List<DVITCareObjHDTB_KS> objHDTB_KS;
    @JsonProperty("objHDTBDV_KS")
    public List<DVITCareObjHDTBDV_KS> objHDTBDV_KS;
    @JsonProperty("objHDTB_ADSL_KS")
    public List<DVITCareObjHDTB_ADSL_KS> objHDTB_ADSL_KS;
    @JsonProperty("objDiaChiKH")
    public List<DVITCareObjDiaChiKS> objDiaChiKH;
    @JsonProperty("objDiaChiLD")
    public List<DVITCareObjDiaChiKS> objDiaChiLD;
    @JsonProperty("objDiaChiTB")
    public List<DVITCareObjDiaChiKS> objDiaChiTB;
    @JsonProperty("objHDTB_KV_KS")
    public List<DVITCareObjHDTB_KV_KS> objHDTB_KV_KS;

    public Boolean getAdd() {
        return isAdd;
    }

    public void setAdd(Boolean add) {
        isAdd = add;
    }

    public List<DVITCareObjHDKH_KS> getObjHDKH_KS() {
        return objHDKH_KS;
    }

    public void setObjHDKH_KS(List<DVITCareObjHDKH_KS> objHDKH_KS) {
        this.objHDKH_KS = objHDKH_KS;
    }

    public List<DVITCareObjHDTB_KS> getObjHDTB_KS() {
        return objHDTB_KS;
    }

    public void setObjHDTB_KS(List<DVITCareObjHDTB_KS> objHDTB_KS) {
        this.objHDTB_KS = objHDTB_KS;
    }

    public List<DVITCareObjHDTBDV_KS> getObjHDTBDV_KS() {
        return objHDTBDV_KS;
    }

    public void setObjHDTBDV_KS(List<DVITCareObjHDTBDV_KS> objHDTBDV_KS) {
        this.objHDTBDV_KS = objHDTBDV_KS;
    }

    public List<DVITCareObjHDTB_ADSL_KS> getObjHDTB_ADSL_KS() {
        return objHDTB_ADSL_KS;
    }

    public void setObjHDTB_ADSL_KS(List<DVITCareObjHDTB_ADSL_KS> objHDTB_ADSL_KS) {
        this.objHDTB_ADSL_KS = objHDTB_ADSL_KS;
    }

    public List<DVITCareObjDiaChiKS> getObjDiaChiKH() {
        return objDiaChiKH;
    }

    public void setObjDiaChiKH(List<DVITCareObjDiaChiKS> objDiaChiKH) {
        this.objDiaChiKH = objDiaChiKH;
    }

    public List<DVITCareObjDiaChiKS> getObjDiaChiLD() {
        return objDiaChiLD;
    }

    public void setObjDiaChiLD(List<DVITCareObjDiaChiKS> objDiaChiLD) {
        this.objDiaChiLD = objDiaChiLD;
    }

    public List<DVITCareObjDiaChiKS> getObjDiaChiTB() {
        return objDiaChiTB;
    }

    public void setObjDiaChiTB(List<DVITCareObjDiaChiKS> objDiaChiTB) {
        this.objDiaChiTB = objDiaChiTB;
    }

    public List<DVITCareObjHDTB_KV_KS> getObjHDTB_KV_KS() {
        return objHDTB_KV_KS;
    }

    public void setObjHDTB_KV_KS(List<DVITCareObjHDTB_KV_KS> objHDTB_KV_KS) {
        this.objHDTB_KV_KS = objHDTB_KV_KS;
    }
}
