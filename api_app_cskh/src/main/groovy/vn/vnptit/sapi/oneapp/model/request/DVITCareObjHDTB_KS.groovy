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
public class DVITCareObjHDTB_KS {



    @JsonProperty("HDTB_ID")
    public long HDTB_ID;
    @JsonProperty("HDKH_ID")
    public long HDKH_ID;
    @JsonProperty("THUEBAO_ID")
    public long THUEBAO_ID;
    @JsonProperty("MA_TB")
    public String MA_TB;
    @JsonProperty("TEN_TB")
    public String TEN_TB;
    @JsonProperty("DIACHI_TB")
    public String DIACHI_TB;
    @JsonProperty("GHICHU")
    public String GHICHU;
    @JsonProperty("KIEULD_ID")
    public int KIEULD_ID;
    @JsonProperty("LOAITB_ID")
    public int LOAITB_ID;
    @JsonProperty("DONVI_ID")
    public int DONVI_ID;
    @JsonProperty("DOITUONG_ID")
    public int DOITUONG_ID;
    @JsonProperty("TTHD_ID")
    public int TTHD_ID;
    @JsonProperty("DICHVUVT_ID")
    public int DICHVUVT_ID;
    @JsonProperty("KIEU_TB")
    public String KIEU_TB;
    @JsonProperty("NGUOI_CN")
    public String NGUOI_CN;
    @JsonProperty("NGAY_CN")
    public String NGAY_CN;
    @JsonProperty("MAY_CN")
    public String MAY_CN;
    @JsonProperty("IP_CN")
    public String IP_CN;
    @JsonProperty("SOLUONG")
    public int SOLUONG;
    @JsonProperty("DIACHI_LD")
    public String DIACHI_LD;
    @JsonProperty("QUYTRINH_ID")
    public int QUYTRINH_ID;
    @JsonProperty("NGAY_SN")
    public String NGAY_SN;

    public long getHDTB_ID() {
        return HDTB_ID;
    }

    public void setHDTB_ID(long HDTB_ID) {
        this.HDTB_ID = HDTB_ID;
    }

    public long getHDKH_ID() {
        return HDKH_ID;
    }

    public void setHDKH_ID(long HDKH_ID) {
        this.HDKH_ID = HDKH_ID;
    }

    public long getTHUEBAO_ID() {
        return THUEBAO_ID;
    }

    public void setTHUEBAO_ID(long THUEBAO_ID) {
        this.THUEBAO_ID = THUEBAO_ID;
    }

    public String getMA_TB() {
        return MA_TB;
    }

    public void setMA_TB(String MA_TB) {
        this.MA_TB = MA_TB;
    }

    public String getTEN_TB() {
        return TEN_TB;
    }

    public void setTEN_TB(String TEN_TB) {
        this.TEN_TB = TEN_TB;
    }

    public String getDIACHI_TB() {
        return DIACHI_TB;
    }

    public void setDIACHI_TB(String DIACHI_TB) {
        this.DIACHI_TB = DIACHI_TB;
    }

    public String getGHICHU() {
        return GHICHU;
    }

    public void setGHICHU(String GHICHU) {
        this.GHICHU = GHICHU;
    }

    public int getKIEULD_ID() {
        return KIEULD_ID;
    }

    public void setKIEULD_ID(int KIEULD_ID) {
        this.KIEULD_ID = KIEULD_ID;
    }

    public int getLOAITB_ID() {
        return LOAITB_ID;
    }

    public void setLOAITB_ID(int LOAITB_ID) {
        this.LOAITB_ID = LOAITB_ID;
    }

    public int getDONVI_ID() {
        return DONVI_ID;
    }

    public void setDONVI_ID(int DONVI_ID) {
        this.DONVI_ID = DONVI_ID;
    }

    public int getDOITUONG_ID() {
        return DOITUONG_ID;
    }

    public void setDOITUONG_ID(int DOITUONG_ID) {
        this.DOITUONG_ID = DOITUONG_ID;
    }

    public int getTTHD_ID() {
        return TTHD_ID;
    }

    public void setTTHD_ID(int TTHD_ID) {
        this.TTHD_ID = TTHD_ID;
    }

    public int getDICHVUVT_ID() {
        return DICHVUVT_ID;
    }

    public void setDICHVUVT_ID(int DICHVUVT_ID) {
        this.DICHVUVT_ID = DICHVUVT_ID;
    }

    public String getKIEU_TB() {
        return KIEU_TB;
    }

    public void setKIEU_TB(String KIEU_TB) {
        this.KIEU_TB = KIEU_TB;
    }

    public String getNGUOI_CN() {
        return NGUOI_CN;
    }

    public void setNGUOI_CN(String NGUOI_CN) {
        this.NGUOI_CN = NGUOI_CN;
    }

    public String getNGAY_CN() {
        return NGAY_CN;
    }

    public void setNGAY_CN(String NGAY_CN) {
        this.NGAY_CN = NGAY_CN;
    }

    public String getMAY_CN() {
        return MAY_CN;
    }

    public void setMAY_CN(String MAY_CN) {
        this.MAY_CN = MAY_CN;
    }

    public String getIP_CN() {
        return IP_CN;
    }

    public void setIP_CN(String IP_CN) {
        this.IP_CN = IP_CN;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public String getDIACHI_LD() {
        return DIACHI_LD;
    }

    public void setDIACHI_LD(String DIACHI_LD) {
        this.DIACHI_LD = DIACHI_LD;
    }

    public int getQUYTRINH_ID() {
        return QUYTRINH_ID;
    }

    public void setQUYTRINH_ID(int QUYTRINH_ID) {
        this.QUYTRINH_ID = QUYTRINH_ID;
    }

    public String getNGAY_SN() {
        return NGAY_SN;
    }

    public void setNGAY_SN(String NGAY_SN) {
        this.NGAY_SN = NGAY_SN;
    }
}
