package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class ThemDanhSachMaDoiCapDtoIn {
    private Long DKCC_ID;
    private Long THUEBAO_ID;
    private String MA_TB;
    private String TEN_TB;
    private String DIACHI_LD;
    private Long PORTVL_ID;
    private Long VCI_VPI_ID;
    private Long PORT_ID;
    private String MADOICAP;

    public Long getDKCC_ID() {
        return DKCC_ID;
    }

    public void setDKCC_ID(Long DKCC_ID) {
        this.DKCC_ID = DKCC_ID;
    }

    public Long getTHUEBAO_ID() {
        return THUEBAO_ID;
    }

    public void setTHUEBAO_ID(Long THUEBAO_ID) {
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

    public String getDIACHI_LD() {
        return DIACHI_LD;
    }

    public void setDIACHI_LD(String DIACHI_LD) {
        this.DIACHI_LD = DIACHI_LD;
    }

    public Long getPORTVL_ID() {
        return PORTVL_ID;
    }

    public void setPORTVL_ID(Long PORTVL_ID) {
        this.PORTVL_ID = PORTVL_ID;
    }

    public Long getVCI_VPI_ID() {
        return VCI_VPI_ID;
    }

    public void setVCI_VPI_ID(Long VCI_VPI_ID) {
        this.VCI_VPI_ID = VCI_VPI_ID;
    }

    public Long getPORT_ID() {
        return PORT_ID;
    }

    public void setPORT_ID(Long PORT_ID) {
        this.PORT_ID = PORT_ID;
    }

    public String getMADOICAP() {
        return MADOICAP;
    }

    public void setMADOICAP(String MADOICAP) {
        this.MADOICAP = MADOICAP;
    }
}
