package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CapNhatODFDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long odfId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long turackId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String tenOdf;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long soPort;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long portBd;
    private String ghichu;
    private String loaicong;
    private Long duanId;
    private Long taisanId;

    public Long getOdfId() {
        return odfId;
    }

    public void setOdfId(Long odfId) {
        this.odfId = odfId;
    }

    public Long getTurackId() {
        return turackId;
    }

    public void setTurackId(Long turackId) {
        this.turackId = turackId;
    }

    public String getTenOdf() {
        return tenOdf;
    }

    public void setTenOdf(String tenOdf) {
        this.tenOdf = tenOdf;
    }

    public Long getSoPort() {
        return soPort;
    }

    public void setSoPort(Long soPort) {
        this.soPort = soPort;
    }

    public Long getPortBd() {
        return portBd;
    }

    public void setPortBd(Long portBd) {
        this.portBd = portBd;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getLoaicong() {
        return loaicong;
    }

    public void setLoaicong(String loaicong) {
        this.loaicong = loaicong;
    }

    public Long getDuanId() {
        return duanId;
    }

    public void setDuanId(Long duanId) {
        this.duanId = duanId;
    }

    public Long getTaisanId() {
        return taisanId;
    }

    public void setTaisanId(Long taisanId) {
        this.taisanId = taisanId;
    }
}
