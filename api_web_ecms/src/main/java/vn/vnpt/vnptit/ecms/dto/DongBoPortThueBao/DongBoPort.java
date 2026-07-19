package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DongBoPort {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String maTB;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer dichVuVTId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer loaiTBId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long brasIdVDC;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamIdVDC;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer thamSo;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long portId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long vciVpiId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String system;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String rack;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String shelf;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String slot;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String port;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String vci;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String vpi;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ip;
    private String selfId01;
    private String selfId02;
    private String selfId03;
    private String selfId04;

    public String getMaTB() {
        return maTB;
    }

    public void setMaTB(String maTB) {
        this.maTB = maTB;
    }

    public Integer getDichVuVTId() {
        return dichVuVTId;
    }

    public void setDichVuVTId(Integer dichVuVTId) {
        this.dichVuVTId = dichVuVTId;
    }

    public Integer getLoaiTBId() {
        return loaiTBId;
    }

    public void setLoaiTBId(Integer loaiTBId) {
        this.loaiTBId = loaiTBId;
    }

    public Long getBrasIdVDC() {
        return brasIdVDC;
    }

    public void setBrasIdVDC(Long brasIdVDC) {
        this.brasIdVDC = brasIdVDC;
    }

    public Long getDslamIdVDC() {
        return dslamIdVDC;
    }

    public void setDslamIdVDC(Long dslamIdVDC) {
        this.dslamIdVDC = dslamIdVDC;
    }

    public Integer getThamSo() {
        return thamSo;
    }

    public void setThamSo(Integer thamSo) {
        this.thamSo = thamSo;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public Long getVciVpiId() {
        return vciVpiId;
    }

    public void setVciVpiId(Long vciVpiId) {
        this.vciVpiId = vciVpiId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVci() {
        return vci;
    }

    public void setVci(String vci) {
        this.vci = vci;
    }

    public String getVpi() {
        return vpi;
    }

    public void setVpi(String vpi) {
        this.vpi = vpi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSelfId01() {
        return selfId01;
    }

    public void setSelfId01(String selfId01) {
        this.selfId01 = selfId01;
    }

    public String getSelfId02() {
        return selfId02;
    }

    public void setSelfId02(String selfId02) {
        this.selfId02 = selfId02;
    }

    public String getSelfId03() {
        return selfId03;
    }

    public void setSelfId03(String selfId03) {
        this.selfId03 = selfId03;
    }

    public String getSelfId04() {
        return selfId04;
    }

    public void setSelfId04(String selfId04) {
        this.selfId04 = selfId04;
    }
}
