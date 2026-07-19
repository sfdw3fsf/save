package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TraPhieuSuCoInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long phieu_id;

    private Integer lydotra_id;

    private String noi_dung;
    private String lydo_tra;

    public String getLydo_tra() {
        return lydo_tra;
    }

    public void setLydo_tra(String lydo_tra) {
        this.lydo_tra = lydo_tra;
    }

    public Long getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Long phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Integer getLydotra_id() {
        return lydotra_id;
    }

    public void setLydotra_id(Integer lydotra_id) {
        this.lydotra_id = lydotra_id;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }
}
