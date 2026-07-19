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
public class DVITCareObjHDKH_KS {

    @JsonProperty("HDKH_ID")
    public long HDKH_ID;
    @JsonProperty("MA_GD")
    public String MA_GD;
    @JsonProperty("KHACHHANG_ID")
    public long KHACHHANG_ID;
    @JsonProperty("MA_KH")
    public String MA_KH;
    @JsonProperty("TEN_KH")
    public String TEN_KH;
    @JsonProperty("MST")
    public String MST;
    @JsonProperty("NGAY_SN")
    public String NGAY_SN;
    @JsonProperty("LOAIGT_ID")
    public int LOAIGT_ID;
    @JsonProperty("SO_GT")
    public String SO_GT;
    @JsonProperty("NGAYCAP")
    public String NGAYCAP;
    @JsonProperty("NOICAP")
    public String NOICAP;
    @JsonProperty("DONVI_NV_ID")
    public int DONVI_NV_ID;
    @JsonProperty("DONVI_ID")
    public int DONVI_ID;
    @JsonProperty("NHANVIEN_ID")
    public int NHANVIEN_ID;
    @JsonProperty("KHLON_ID")
    public int KHLON_ID;
    @JsonProperty("LOAIHD_ID")
    public int LOAIHD_ID;
    @JsonProperty("NGUOI_CN")
    public String NGUOI_CN;
    @JsonProperty("NGAY_CN")
    public String NGAY_CN;
    @JsonProperty("MAY_CN")
    public String MAY_CN;
    @JsonProperty("IP_CN")
    public String IP_CN;
    @JsonProperty("CTV_ID")
    public int CTV_ID;
    @JsonProperty("NHANVIENGT_ID")
    public int NHANVIENGT_ID;
    @JsonProperty("NGAYLAP_HD")
    public String NGAYLAP_HD;
    @JsonProperty("NGAY_YC")
    public String NGAY_YC;
    @JsonProperty("GIOITINH")
    public int GIOITINH;
    @JsonProperty("SO_DT")
    public String SO_DT;
    @JsonProperty("QUOCTICH_ID")
    public int QUOCTICH_ID;
    @JsonProperty("NGANHNGHE_ID")
    public int NGANHNGHE_ID;
    @JsonProperty("NGAYHETHAN_GT")
    public String NGAYHETHAN_GT;
    @JsonProperty("TNC1_ID")
    public int TNC1_ID;
    @JsonProperty("TNC2_ID")
    public int TNC2_ID;
    @JsonProperty("TNC3_ID")
    public int TNC3_ID;
    @JsonProperty("LOAI_KH")
    public int LOAI_KH;

    public long getHDKH_ID() {
        return HDKH_ID;
    }

    public void setHDKH_ID(long HDKH_ID) {
        this.HDKH_ID = HDKH_ID;
    }

    public String getMA_GD() {
        return MA_GD;
    }

    public void setMA_GD(String MA_GD) {
        this.MA_GD = MA_GD;
    }

    public long getKHACHHANG_ID() {
        return KHACHHANG_ID;
    }

    public void setKHACHHANG_ID(long KHACHHANG_ID) {
        this.KHACHHANG_ID = KHACHHANG_ID;
    }

    public String getMA_KH() {
        return MA_KH;
    }

    public void setMA_KH(String MA_KH) {
        this.MA_KH = MA_KH;
    }

    public String getTEN_KH() {
        return TEN_KH;
    }

    public void setTEN_KH(String TEN_KH) {
        this.TEN_KH = TEN_KH;
    }

    public String getMST() {
        return MST;
    }

    public void setMST(String MST) {
        this.MST = MST;
    }

    public String getNGAY_SN() {
        return NGAY_SN;
    }

    public void setNGAY_SN(String NGAY_SN) {
        this.NGAY_SN = NGAY_SN;
    }

    public int getLOAIGT_ID() {
        return LOAIGT_ID;
    }

    public void setLOAIGT_ID(int LOAIGT_ID) {
        this.LOAIGT_ID = LOAIGT_ID;
    }

    public String getSO_GT() {
        return SO_GT;
    }

    public void setSO_GT(String SO_GT) {
        this.SO_GT = SO_GT;
    }

    public String getNGAYCAP() {
        return NGAYCAP;
    }

    public void setNGAYCAP(String NGAYCAP) {
        this.NGAYCAP = NGAYCAP;
    }

    public String getNOICAP() {
        return NOICAP;
    }

    public void setNOICAP(String NOICAP) {
        this.NOICAP = NOICAP;
    }

    public int getDONVI_NV_ID() {
        return DONVI_NV_ID;
    }

    public void setDONVI_NV_ID(int DONVI_NV_ID) {
        this.DONVI_NV_ID = DONVI_NV_ID;
    }

    public int getDONVI_ID() {
        return DONVI_ID;
    }

    public void setDONVI_ID(int DONVI_ID) {
        this.DONVI_ID = DONVI_ID;
    }

    public int getNHANVIEN_ID() {
        return NHANVIEN_ID;
    }

    public void setNHANVIEN_ID(int NHANVIEN_ID) {
        this.NHANVIEN_ID = NHANVIEN_ID;
    }

    public int getKHLON_ID() {
        return KHLON_ID;
    }

    public void setKHLON_ID(int KHLON_ID) {
        this.KHLON_ID = KHLON_ID;
    }

    public int getLOAIHD_ID() {
        return LOAIHD_ID;
    }

    public void setLOAIHD_ID(int LOAIHD_ID) {
        this.LOAIHD_ID = LOAIHD_ID;
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

    public int getCTV_ID() {
        return CTV_ID;
    }

    public void setCTV_ID(int CTV_ID) {
        this.CTV_ID = CTV_ID;
    }

    public int getNHANVIENGT_ID() {
        return NHANVIENGT_ID;
    }

    public void setNHANVIENGT_ID(int NHANVIENGT_ID) {
        this.NHANVIENGT_ID = NHANVIENGT_ID;
    }

    public String getNGAYLAP_HD() {
        return NGAYLAP_HD;
    }

    public void setNGAYLAP_HD(String NGAYLAP_HD) {
        this.NGAYLAP_HD = NGAYLAP_HD;
    }

    public String getNGAY_YC() {
        return NGAY_YC;
    }

    public void setNGAY_YC(String NGAY_YC) {
        this.NGAY_YC = NGAY_YC;
    }

    public int getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(int GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getSO_DT() {
        return SO_DT;
    }

    public void setSO_DT(String SO_DT) {
        this.SO_DT = SO_DT;
    }

    public int getQUOCTICH_ID() {
        return QUOCTICH_ID;
    }

    public void setQUOCTICH_ID(int QUOCTICH_ID) {
        this.QUOCTICH_ID = QUOCTICH_ID;
    }

    public int getNGANHNGHE_ID() {
        return NGANHNGHE_ID;
    }

    public void setNGANHNGHE_ID(int NGANHNGHE_ID) {
        this.NGANHNGHE_ID = NGANHNGHE_ID;
    }

    public String getNGAYHETHAN_GT() {
        return NGAYHETHAN_GT;
    }

    public void setNGAYHETHAN_GT(String NGAYHETHAN_GT) {
        this.NGAYHETHAN_GT = NGAYHETHAN_GT;
    }

    public int getTNC1_ID() {
        return TNC1_ID;
    }

    public void setTNC1_ID(int TNC1_ID) {
        this.TNC1_ID = TNC1_ID;
    }

    public int getTNC2_ID() {
        return TNC2_ID;
    }

    public void setTNC2_ID(int TNC2_ID) {
        this.TNC2_ID = TNC2_ID;
    }

    public int getTNC3_ID() {
        return TNC3_ID;
    }

    public void setTNC3_ID(int TNC3_ID) {
        this.TNC3_ID = TNC3_ID;
    }

    public int getLOAI_KH() {
        return LOAI_KH;
    }

    public void setLOAI_KH(int LOAI_KH) {
        this.LOAI_KH = LOAI_KH;
    }
}
