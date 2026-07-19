package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.PositiveOrZero;

public class XoaDaiPortDto {

    @JsonProperty("slot_bd")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long slot_bd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("slot_kt")
    private Long slot_kt;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("port_bd")
    private Long port_bd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("port_kt")
    private Long port_kt;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vci_bd")
    private Long vci_bd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vci_kt")
    private Long vci_kt;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vpi_bd")
    private Long vpi_bd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vpi_kt")
    private Long vpi_kt;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("dslam_id")
    private Long dslam_id;


    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("selfid01")
    private Long selfid01;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("selfid02")
    private Long selfid02;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("selfid03_bd")
    private Long selfid03_bd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("selfid03_kt")
    private Long selfid03_kt;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("selfid04_bd")
    private Long selfid04_bd;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("selfid04_kt")
    private Long selfid04_kt;


    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("rack_id")
    private Long rack_id;

    @JsonProperty("system")
    private String system;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("thamso")
    private Long thamso;

    public Long getSlot_bd() {
        return slot_bd;
    }

    public void setSlot_bd(Long slot_bd) {
        this.slot_bd = slot_bd;
    }

    public Long getSlot_kt() {
        return slot_kt;
    }

    public void setSlot_kt(Long slot_kt) {
        this.slot_kt = slot_kt;
    }

    public Long getPort_bd() {
        return port_bd;
    }

    public void setPort_bd(Long port_bd) {
        this.port_bd = port_bd;
    }

    public Long getPort_kt() {
        return port_kt;
    }

    public void setPort_kt(Long port_kt) {
        this.port_kt = port_kt;
    }

    public Long getVci_bd() {
        return vci_bd;
    }

    public void setVci_bd(Long vci_bd) {
        this.vci_bd = vci_bd;
    }

    public Long getVci_kt() {
        return vci_kt;
    }

    public void setVci_kt(Long vci_kt) {
        this.vci_kt = vci_kt;
    }

    public Long getVpi_bd() {
        return vpi_bd;
    }

    public void setVpi_bd(Long vpi_bd) {
        this.vpi_bd = vpi_bd;
    }

    public Long getVpi_kt() {
        return vpi_kt;
    }

    public void setVpi_kt(Long vpi_kt) {
        this.vpi_kt = vpi_kt;
    }

    public Long getDslam_id() {
        return dslam_id;
    }

    public void setDslam_id(Long dslam_id) {
        this.dslam_id = dslam_id;
    }

    public Long getSelfid01() {
        return selfid01;
    }

    public void setSelfid01(Long selfid01) {
        this.selfid01 = selfid01;
    }

    public Long getSelfid02() {
        return selfid02;
    }

    public void setSelfid02(Long selfid02) {
        this.selfid02 = selfid02;
    }

    public Long getSelfid03_bd() {
        return selfid03_bd;
    }

    public void setSelfid03_bd(Long selfid03_bd) {
        this.selfid03_bd = selfid03_bd;
    }

    public Long getSelfid03_kt() {
        return selfid03_kt;
    }

    public void setSelfid03_kt(Long selfid03_kt) {
        this.selfid03_kt = selfid03_kt;
    }

    public Long getSelfid04_bd() {
        return selfid04_bd;
    }

    public void setSelfid04_bd(Long selfid04_bd) {
        this.selfid04_bd = selfid04_bd;
    }

    public Long getSelfid04_kt() {
        return selfid04_kt;
    }

    public void setSelfid04_kt(Long selfid04_kt) {
        this.selfid04_kt = selfid04_kt;
    }

    public Long getRack_id() {
        return rack_id;
    }

    public void setRack_id(Long rack_id) {
        this.rack_id = rack_id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Long getThamso() {
        return thamso;
    }

    public void setThamso(Long thamso) {
        this.thamso = thamso;
    }
}
