package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class LoaiDSlamDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private  Long nhom_dslam_id;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private  String loai_dslam;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private  Long thamso;
    private  String ghichu;
    @Min(value = 0, message = "Trường Port này không được nhỏ hơn 0")
    @Max(value = 1, message = "Trường Port này không được lớn hơn 1")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private  Long port_td;
    @Min(value = 0, message = "Trường Bras này không được nhỏ hơn 0")
    @Max(value = 1, message = "Trường Bras này không được lớn hơn 1")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private  Long bras_td;

    public void setP_nhom_dslam_id(Long p_nhom_dslam_id) {
        this.nhom_dslam_id = p_nhom_dslam_id;
    }

    public void setP_loai_dslam(String p_loai_dslam) {
        this.loai_dslam = p_loai_dslam;
    }

    public void setP_thamso(Long p_thamso) {
        this.thamso = p_thamso;
    }

    public void setP_ghichu(String p_ghichu) {
        this.ghichu = p_ghichu;
    }

    public void setP_port_td(Long p_port_td) {
        this.port_td = p_port_td;
    }

    public void setP_bras_td(Long p_bras_td) {
        this.bras_td = p_bras_td;
    }

    public Long getP_nhom_dslam_id() {
        return nhom_dslam_id;
    }

    public String getP_loai_dslam() {
        return loai_dslam;
    }

    public Long getP_thamso() {
        return thamso;
    }

    public String getP_ghichu() {
        return ghichu;
    }

    public Long getP_port_td() {
        return port_td;
    }

    public Long getP_bras_td() {
        return bras_td;
    }
}
