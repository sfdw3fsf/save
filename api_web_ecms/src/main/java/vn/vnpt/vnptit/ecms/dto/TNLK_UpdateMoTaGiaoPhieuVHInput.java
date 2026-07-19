package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TNLK_UpdateMoTaGiaoPhieuVHInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private int phieuVhId;
    private String moTa;

    public Integer getPhieuVhId() { return this.phieuVhId; }
    public String getMoTa() { return this.moTa; }
}
