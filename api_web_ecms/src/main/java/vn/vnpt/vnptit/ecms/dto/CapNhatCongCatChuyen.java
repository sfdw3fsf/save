package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class CapNhatCongCatChuyen {
    private Long DKCC_ID;
    private Long PORT_ID;
    private Long PORTVL_ID;
    private Long VCI_VPI_ID;
    private Long THIETBI_ID;

    private Long RP_ID;
    private Long ONT_ID;
    private String MA_TB;
    private String ADSL_PORT;

    public Long getDKCC_ID() {
        return DKCC_ID;
    }

    public void setDKCC_ID(Long DKCC_ID) {
        this.DKCC_ID = DKCC_ID;
    }

    public Long getPORT_ID() {
        return PORT_ID;
    }

    public void setPORT_ID(Long PORT_ID) {
        this.PORT_ID = PORT_ID;
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

    public Long getTHIETBI_ID() {
        return THIETBI_ID;
    }

    public void setTHIETBI_ID(Long THIETBI_ID) {
        this.THIETBI_ID = THIETBI_ID;
    }

    public Long getRP_ID() {
        return RP_ID;
    }

    public void setRP_ID(Long RP_ID) {
        this.RP_ID = RP_ID;
    }

    public Long getONT_ID() {
        return ONT_ID;
    }

    public void setONT_ID(Long ONT_ID) {
        this.ONT_ID = ONT_ID;
    }

    public String getMA_TB() {
        return MA_TB;
    }

    public void setMA_TB(String MA_TB) {
        this.MA_TB = MA_TB;
    }

    public String getADSL_PORT() {
        return ADSL_PORT;
    }

    public void setADSL_PORT(String ADSL_PORT) {
        this.ADSL_PORT = ADSL_PORT;
    }
}
