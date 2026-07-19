package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class PortDtoInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min = 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_slot")
    private String slot;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min = 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_port")
    private String port;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_rack_id")
    private Long rackId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_tt_port_id")
    private Long ttPortId;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_selfid01")
    private String selfid01;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_selfid02")
    private String selfid02;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_selfid03")
    private String selfid03;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min = 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_dslam_id")
    private String dslamId;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_selfid04")
    private String selfid04;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_ngay_cn")
    private String ngayCn;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_may_cn")
    private String mayCn;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_nguoi_cn")
    private String nguoiCn;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_dslam_vdc_id")
    private Long dslamVdcId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("p_vlan_int")
    private Long vlanInt;

    @Size(max = 100, message = ModelValidation.INVALID)
    @JsonProperty("p_ghichu")
    private String ghiChu;

    @JsonProperty("p_card_id")
    private Long cardId;

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getPort() {
        return port;
    }

    public String getDslamId() {
        return dslamId;
    }

    public void setDslamId(String dslamId) {
        this.dslamId = dslamId;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getTtPortId() {
        return ttPortId;
    }

    public void setTtPortId(Long ttPortId) {
        this.ttPortId = ttPortId;
    }

    public String getSelfid01() {
        return selfid01;
    }

    public void setSelfid01(String selfid01) {
        this.selfid01 = selfid01;
    }

    public String getSelfid02() {
        return selfid02;
    }

    public void setSelfid02(String selfid02) {
        this.selfid02 = selfid02;
    }

    public String getSelfid03() {
        return selfid03;
    }

    public void setSelfid03(String selfid03) {
        this.selfid03 = selfid03;
    }

    public String getSelfid04() {
        return selfid04;
    }

    public void setSelfid04(String selfid04) {
        this.selfid04 = selfid04;
    }

    public String getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(String ngayCn) {
        this.ngayCn = ngayCn;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Long getDslamVdcId() {
        return dslamVdcId;
    }

    public void setDslamVdcId(Long dslamVdcId) {
        this.dslamVdcId = dslamVdcId;
    }

    public Long getVlanInt() {
        return vlanInt;
    }

    public void setVlanInt(Long vlanInt) {
        this.vlanInt = vlanInt;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public static class PortDtoUpdInput extends PortDtoInput {

        @NotNull(message = ModelValidation.NOT_NULL)
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("p_port_id")
        private Long portId;

        public Long getPortId() {
            return portId;
        }

        public void setPortId(Long portId) {
            this.portId = portId;
        }
    }
}
