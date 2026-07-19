package vn.vnpt.vnptit.ecms.dto;

public class CapNhatPortDtoIn {
    private Long tdtt_port_id;
    private Long port_id;
    private Long dslam_id;
    private Long dslam_vdc_id_moi;
    private Long dslam_vdc_id_cu;
    private Long thuebao_id;
    private Long hdtb_id;
    private String ma_tb;
    private String may_cn;
    private String ngay_cn;
    private String nguoi_cn;

    public Long getTdtt_port_id() {
        return tdtt_port_id;
    }

    public void setTdtt_port_id(Long tdtt_port_id) {
        this.tdtt_port_id = tdtt_port_id;
    }

    public Long getPort_id() {
        return port_id;
    }

    public void setPort_id(Long port_id) {
        this.port_id = port_id;
    }

    public Long getDslam_id() {
        return dslam_id;
    }

    public void setDslam_id(Long dslam_id) {
        this.dslam_id = dslam_id;
    }

    public Long getDslam_vdc_id_moi() {
        return dslam_vdc_id_moi;
    }

    public void setDslam_vdc_id_moi(Long dslam_vdc_id_moi) {
        this.dslam_vdc_id_moi = dslam_vdc_id_moi;
    }

    public Long getDslam_vdc_id_cu() {
        return dslam_vdc_id_cu;
    }

    public void setDslam_vdc_id_cu(Long dslam_vdc_id_cu) {
        this.dslam_vdc_id_cu = dslam_vdc_id_cu;
    }

    public Long getThuebao_id() {
        return thuebao_id;
    }

    public void setThuebao_id(Long thuebao_id) {
        this.thuebao_id = thuebao_id;
    }

    public Long getHdtb_id() {
        return hdtb_id;
    }

    public void setHdtb_id(Long hdtb_id) {
        this.hdtb_id = hdtb_id;
    }

    public String getMa_tb() {
        return ma_tb;
    }

    public void setMa_tb(String ma_tb) {
        this.ma_tb = ma_tb;
    }

    public String getMay_cn() {
        return may_cn;
    }

    public void setMay_cn(String may_cn) {
        this.may_cn = may_cn;
    }

    public String getNgay_cn() {
        return ngay_cn;
    }

    public void setNgay_cn(String ngay_cn) {
        this.ngay_cn = ngay_cn;
    }

    public String getNguoi_cn() {
        return nguoi_cn;
    }

    public void setNguoi_cn(String nguoi_cn) {
        this.nguoi_cn = nguoi_cn;
    }
}
