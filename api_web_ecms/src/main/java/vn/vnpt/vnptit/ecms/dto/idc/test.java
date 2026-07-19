package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

public class test {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Long capGocId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Long tuDoi;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Long denDoi;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private Long nhanVienId;

    public Long getCapGocId() {
        return capGocId;
    }

    public void setCapGocId(Long capGocId) {
        this.capGocId = capGocId;
    }

    public Long getTuDoi() {
        return tuDoi;
    }

    public void setTuDoi(Long tuDoi) {
        this.tuDoi = tuDoi;
    }

    public Long getDenDoi() {
        return denDoi;
    }

    public void setDenDoi(Long denDoi) {
        this.denDoi = denDoi;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }
}
