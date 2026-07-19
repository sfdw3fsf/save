package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TraPhieuSuCoCNTTInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long phieu_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long phanvung_id;

    private Integer lydotra_id;

    private String noi_dung;

    public Long getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Long phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Long getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(Long phanvung_id) {
        this.phanvung_id = phanvung_id;
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
