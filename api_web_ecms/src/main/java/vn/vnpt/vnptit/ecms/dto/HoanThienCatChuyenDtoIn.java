package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class HoanThienCatChuyenDtoIn {
    private Long DKCC_ID;
    private Long PORT_ID;
    private Long PORTVL_ID;
    private Long VCI_VPI_ID;
    private Long ONT_ID;
    private Long PORTVL_ID_CU;
    private Long PORT_ID_CU;
    private Long VCI_VPI_ID_CU;
    private Long LOAITBI_ID;
    private String MA_TB;
    private Long THIETBI_ID;
    private Long THUEBAO_ID;
    private Long DICHVUVT_ID;

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

    public Long getVCI_VPI_ID() {
        return VCI_VPI_ID;
    }

    public void setVCI_VPI_ID(Long VCI_VPI_ID) {
        this.VCI_VPI_ID = VCI_VPI_ID;
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

    public Long getTHIETBI_ID() {
        return THIETBI_ID;
    }

    public void setTHIETBI_ID(Long THIETBI_ID) {
        this.THIETBI_ID = THIETBI_ID;
    }

    public Long getTHUEBAO_ID() {
        return THUEBAO_ID;
    }

    public void setTHUEBAO_ID(Long THUEBAO_ID) {
        this.THUEBAO_ID = THUEBAO_ID;
    }

    public Long getDICHVUVT_ID() {
        return DICHVUVT_ID;
    }

    public void setDICHVUVT_ID(Long DICHVUVT_ID) {
        this.DICHVUVT_ID = DICHVUVT_ID;
    }

    public Long getPORTVL_ID() {
        return PORTVL_ID;
    }

    public void setPORTVL_ID(Long PORTVL_ID) {
        this.PORTVL_ID = PORTVL_ID;
    }

    public Long getPORTVL_ID_CU() {
        return PORTVL_ID_CU;
    }

    public void setPORTVL_ID_CU(Long PORTVL_ID_CU) {
        this.PORTVL_ID_CU = PORTVL_ID_CU;
    }

    public Long getPORT_ID_CU() {
        return PORT_ID_CU;
    }

    public void setPORT_ID_CU(Long PORT_ID_CU) {
        this.PORT_ID_CU = PORT_ID_CU;
    }

    public Long getVCI_VPI_ID_CU() {
        return VCI_VPI_ID_CU;
    }

    public void setVCI_VPI_ID_CU(Long VCI_VPI_ID_CU) {
        this.VCI_VPI_ID_CU = VCI_VPI_ID_CU;
    }

    public Long getLOAITBI_ID() {
        return LOAITBI_ID;
    }

    public void setLOAITBI_ID(Long LOAITBI_ID) {
        this.LOAITBI_ID = LOAITBI_ID;
    }
}
