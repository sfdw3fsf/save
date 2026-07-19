package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CapPhatURequestDTO implements Serializable {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idPhieuThiCong;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idU;

    public CapPhatURequestDTO() {
    }

    public CapPhatURequestDTO(Long idPhieuThiCong, Long idU) {
        this.idPhieuThiCong = idPhieuThiCong;
        this.idU = idU;
    }

    public Long getIdPhieuThiCong() {
        return idPhieuThiCong;
    }

    public void setIdPhieuThiCong(Long idPhieuThiCong) {
        this.idPhieuThiCong = idPhieuThiCong;
    }

    public Long getIdU() {
        return idU;
    }

    public void setIdU(Long idU) {
        this.idU = idU;
    }
}
