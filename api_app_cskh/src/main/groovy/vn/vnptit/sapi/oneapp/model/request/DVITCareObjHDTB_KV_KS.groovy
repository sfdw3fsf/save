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
public class DVITCareObjHDTB_KV_KS {



    @JsonProperty("HDTB_ID")
    public long HDTB_ID;
    @JsonProperty("KHUVUC_ID")
    public int KHUVUC_ID;
    @JsonProperty("LOAIKV_ID")
    public int LOAIKV_ID;
    @JsonProperty("KIEUKV_ID")
    public int KIEUKV_ID;
    @JsonProperty("NGAY_CN")
    public Date NGAY_CN;
    @JsonProperty("MAY_CN")
    public String MAY_CN;
    @JsonProperty("NGUOI_CN")
    public String NGUOI_CN;
    @JsonProperty("IP_CN")
    public String IP_CN;
    @JsonProperty("DIACHI_ID")
    public long DIACHI_ID;
    @JsonProperty("TINH_ID")
    public long TINH_ID;
    @JsonProperty("QUAN_ID")
    public long QUAN_ID;
    @JsonProperty("PHUONG_ID")
    public long PHUONG_ID;
    @JsonProperty("PHO_ID")
    public long PHO_ID;
    @JsonProperty("AP_ID")
    public long AP_ID;
    @JsonProperty("KHU_ID")
    public long KHU_ID;
    @JsonProperty("DACDIEM_ID")
    public long DACDIEM_ID;
    @JsonProperty("TramTC_ID")
    public int TramTC_ID;
    @JsonProperty("Ten_KV")
    public String Ten_KV;

    @JsonProperty("SONHA")
    public String SONHA;
    @JsonProperty("DIACHI")
    public String DIACHI;
    @JsonProperty("ERROR")
    public String ERROR;
    @JsonProperty("DONVI_LD")
    public int DONVI_LD;

    public long getHDTB_ID() {
        return HDTB_ID;
    }

    public void setHDTB_ID(long HDTB_ID) {
        this.HDTB_ID = HDTB_ID;
    }

    public int getKHUVUC_ID() {
        return KHUVUC_ID;
    }

    public void setKHUVUC_ID(int KHUVUC_ID) {
        this.KHUVUC_ID = KHUVUC_ID;
    }

    public int getLOAIKV_ID() {
        return LOAIKV_ID;
    }

    public void setLOAIKV_ID(int LOAIKV_ID) {
        this.LOAIKV_ID = LOAIKV_ID;
    }

    public int getKIEUKV_ID() {
        return KIEUKV_ID;
    }

    public void setKIEUKV_ID(int KIEUKV_ID) {
        this.KIEUKV_ID = KIEUKV_ID;
    }

    public Date getNGAY_CN() {
        return NGAY_CN;
    }

    public void setNGAY_CN(Date NGAY_CN) {
        this.NGAY_CN = NGAY_CN;
    }

    public String getMAY_CN() {
        return MAY_CN;
    }

    public void setMAY_CN(String MAY_CN) {
        this.MAY_CN = MAY_CN;
    }

    public String getNGUOI_CN() {
        return NGUOI_CN;
    }

    public void setNGUOI_CN(String NGUOI_CN) {
        this.NGUOI_CN = NGUOI_CN;
    }

    public String getIP_CN() {
        return IP_CN;
    }

    public void setIP_CN(String IP_CN) {
        this.IP_CN = IP_CN;
    }

    public long getDIACHI_ID() {
        return DIACHI_ID;
    }

    public void setDIACHI_ID(long DIACHI_ID) {
        this.DIACHI_ID = DIACHI_ID;
    }

    public long getTINH_ID() {
        return TINH_ID;
    }

    public void setTINH_ID(long TINH_ID) {
        this.TINH_ID = TINH_ID;
    }

    public long getQUAN_ID() {
        return QUAN_ID;
    }

    public void setQUAN_ID(long QUAN_ID) {
        this.QUAN_ID = QUAN_ID;
    }

    public long getPHUONG_ID() {
        return PHUONG_ID;
    }

    public void setPHUONG_ID(long PHUONG_ID) {
        this.PHUONG_ID = PHUONG_ID;
    }

    public long getPHO_ID() {
        return PHO_ID;
    }

    public void setPHO_ID(long PHO_ID) {
        this.PHO_ID = PHO_ID;
    }

    public long getAP_ID() {
        return AP_ID;
    }

    public void setAP_ID(long AP_ID) {
        this.AP_ID = AP_ID;
    }

    public long getKHU_ID() {
        return KHU_ID;
    }

    public void setKHU_ID(long KHU_ID) {
        this.KHU_ID = KHU_ID;
    }

    public long getDACDIEM_ID() {
        return DACDIEM_ID;
    }

    public void setDACDIEM_ID(long DACDIEM_ID) {
        this.DACDIEM_ID = DACDIEM_ID;
    }

    public int getTramTC_ID() {
        return TramTC_ID;
    }

    public void setTramTC_ID(int tramTC_ID) {
        TramTC_ID = tramTC_ID;
    }

    public String getTen_KV() {
        return Ten_KV;
    }

    public void setTen_KV(String ten_KV) {
        Ten_KV = ten_KV;
    }

    public String getSONHA() {
        return SONHA;
    }

    public void setSONHA(String SONHA) {
        this.SONHA = SONHA;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public int getDONVI_LD() {
        return DONVI_LD;
    }

    public void setDONVI_LD(int DONVI_LD) {
        this.DONVI_LD = DONVI_LD;
    }
}
