package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DslamDtoInp {
    @NotNull(message = "dslam_id không được để trống")
    private Integer dslam_id;
    private String ip;
    private String ten_dslam;

    public int getDslam_id() {
        return dslam_id;
    }

    public void setDslam_id(int dslam_id) {
        this.dslam_id = dslam_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTen_dslam() {
        return ten_dslam;
    }

    public void setTen_dslam(String ten_dslam) {
        this.ten_dslam = ten_dslam;
    }
}
