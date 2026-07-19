package vn.vnpt.vnptit.qlsc.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GhiChuXuLyNvInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long giaoviec_id;

    private String ghichu;

    public Long getGiaoviec_id() {
        return giaoviec_id;
    }

    public void setGiaoviec_id(Long giaoviec_id) {
        this.giaoviec_id = giaoviec_id;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
