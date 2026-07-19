package vn.vnpt.vnptit.ecms.dto;

public class LogCatChuyenDtoIn {
    public String getMa_tb() {
        return ma_tb;
    }

    public void setMa_tb(String ma_tb) {
        this.ma_tb = ma_tb;
    }

    public Long getDkcc_id() {
        return dkcc_id;
    }

    public void setDkcc_id(Long dkcc_id) {
        this.dkcc_id = dkcc_id;
    }

    public Long getPort_id() {
        return port_id;
    }

    public void setPort_id(Long port_id) {
        this.port_id = port_id;
    }

    public Long getVci_vpi_id() {
        return vci_vpi_id;
    }

    public void setVci_vpi_id(Long vci_vpi_id) {
        this.vci_vpi_id = vci_vpi_id;
    }

    public Long getDslam_vdc_id() {
        return dslam_vdc_id;
    }

    public void setDslam_vdc_id(Long dslam_vdc_id) {
        this.dslam_vdc_id = dslam_vdc_id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String ma_tb;
    private Long dkcc_id;
    private Long port_id;
    private Long vci_vpi_id;
    private Long dslam_vdc_id;
    private Integer flag;
    private String nd;
    private Integer status;
}
