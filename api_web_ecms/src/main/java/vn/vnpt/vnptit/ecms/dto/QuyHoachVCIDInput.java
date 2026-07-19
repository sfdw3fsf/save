package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuyHoachVCIDInput {
	@JsonProperty("donvi_id")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)    
	@NotNull(message = ModelValidation.NOT_NULL)
    private Integer DONVI_ID;
    public Integer getDONVI_ID() {
        return DONVI_ID;
    }
    public void setDONVI_ID(Integer DONVI_ID) {
        this.DONVI_ID = DONVI_ID;
    }
    
    @JsonProperty("dslam_id")        
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)    
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer DSLAM_ID;
    public Integer getDSLAM_ID() {
        return DSLAM_ID;
    }
    public void setDSLAM_ID(Integer DSLAM_ID) {
        this.DSLAM_ID = DSLAM_ID;
    }
    
    @JsonProperty("vlan_tu")    
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)    
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer VLAN_TU;
    public Integer getVLAN_TU() {
        return VLAN_TU;
    }
    public void setVLAN_TU(Integer VLAN_TU) {
        this.VLAN_TU = VLAN_TU;
    }
                    
    @JsonProperty("vlan_den")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)   
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer VLAN_DEN;
    public Integer getVLAN_DEN() {
        return VLAN_DEN;
    }
    public void setVLAN_DEN(Integer VLAN_DEN) {
        this.VLAN_DEN = VLAN_DEN;
    }
           
    @JsonProperty("vcid_tu")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)    
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer VCID_TU;
    public Integer getVCID_TU() {
        return VCID_TU;
    }
    public void setVCID_TU(Integer VCID_TU) {
        this.VCID_TU = VCID_TU;
    }
               
    @JsonProperty("vcid_den")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)  
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer VCID_DEN;
    public Integer getVCID_DEN() {
        return VCID_DEN;
    }
    public void setVCID_DEN(Integer VCID_DEN) {
        this.VCID_DEN = VCID_DEN;
    }
               
    @JsonProperty("si_tu")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)    
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer SI_TU;
    public Integer getSI_TU() {
        return SI_TU;
    }
    public void setSI_TU(Integer SI_TU) {
        this.SI_TU = SI_TU;
    }
        
    @JsonProperty("si_den")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)   
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer SI_DEN;
    public Integer getSI_DEN() {
        return SI_DEN;
    }
    public void setSI_DEN(Integer SI_DEN) {
        this.SI_DEN = SI_DEN;
    }
            
    
   
}
