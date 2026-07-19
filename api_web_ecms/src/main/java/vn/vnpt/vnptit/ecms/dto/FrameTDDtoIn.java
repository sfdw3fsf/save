package vn.vnpt.vnptit.ecms.dto;

import org.springframework.format.annotation.NumberFormat;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class FrameTDDtoIn {
    private long phanvung_id;

    public Long getFrametd_id() {
        return frametd_id;
    }

    private Long frametd_id;
    private String ten_frame;
    private Long turack_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long so_slot;
    private String serial;
    @NotNull(message = ModelValidation.NOT_NULL)
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long  loai_dslam_id;
    private Long loaidb_vao_id;
    private Long loaidb_ra_id;
    private Long so_soiquang;
    @NotNull(message = ModelValidation.NOT_NULL)
//    @Positive(message = ModelValidation.POSITIVE_OR_ZERO) // do có ring có id = 0
    private Long ring_id;
    private Long dongbo_vao_id;
    private Long hangsx_id;
    private Long giaodien_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long slot_bd;
    private Long rack;

    public Long getRack() {
        return rack;
    }

    public void setRack(Long rack) {
        this.rack = rack;
    }



    public Long getPhanvung_id() {
        return phanvung_id;
    }

    public String getTen_frame() {
        return ten_frame;
    }

    public Long getTurack_id() {
        return turack_id;
    }

    public Long getSo_slot() {
        return so_slot;
    }

    public String getSerial() {
        return serial;
    }

    public Long getLoai_dslam_id() {
        return loai_dslam_id;
    }

    public Long getLoaidb_vao_id() {
        return loaidb_vao_id;
    }

    public Long getLoaidb_ra_id() {
        return loaidb_ra_id;
    }

    public Long getSo_soiquang() {
        return so_soiquang;
    }

    public Long getRing_id() {
        return ring_id;
    }

    public Long getDongbo_vao_id() {
        return dongbo_vao_id;
    }

    public Long getHangsx_id() {
        return hangsx_id;
    }

    public Long getGiaodien_id() {
        return giaodien_id;
    }

    public Long getSlot_bd() {
        return slot_bd;
    }




}
