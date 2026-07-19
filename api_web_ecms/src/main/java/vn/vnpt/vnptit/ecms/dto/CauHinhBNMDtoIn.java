package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CauHinhBNMDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long thuebao_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long port_id ;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long vci_vpi_id ;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loai_td ;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String nguoi_kh;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer chuyen_all;

    private String ds_tb ;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long trangthai;
    private Long dkcc_id ;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String pass_ftth ;

    public Long getThuebao_id() {
        return thuebao_id;
    }

    public void setThuebao_id(Long thuebao_id) {
        this.thuebao_id = thuebao_id;
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

    public Long getLoai_td() {
        return loai_td;
    }

    public void setLoai_td(Long loai_td) {
        this.loai_td = loai_td;
    }

    public String getNguoi_kh() {
        return nguoi_kh;
    }

    public void setNguoi_kh(String nguoi_kh) {
        this.nguoi_kh = nguoi_kh;
    }

    public Integer getChuyen_all() {
        return chuyen_all;
    }

    public void setChuyen_all(Integer chuyen_all) {
        this.chuyen_all = chuyen_all;
    }

    public String getDs_tb() {
        return ds_tb;
    }

    public void setDs_tb(String ds_tb) {
        this.ds_tb = ds_tb;
    }

    public Long getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(Long trangthai) {
        this.trangthai = trangthai;
    }

    public Long getDkcc_id() {
        return dkcc_id;
    }

    public void setDkcc_id(Long dkcc_id) {
        this.dkcc_id = dkcc_id;
    }

    public String getPass_ftth() {
        return pass_ftth;
    }

    public void setPass_ftth(String pass_ftth) {
        this.pass_ftth = pass_ftth;
    }
}
