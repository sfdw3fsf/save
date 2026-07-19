package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CapPhatRackRequestDTO implements Serializable {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idPhieuThiCong;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idRack;

    public CapPhatRackRequestDTO() {
    }

    public CapPhatRackRequestDTO(Long idPhieuThiCong, Long idRack) {
        this.idPhieuThiCong = idPhieuThiCong;
        this.idRack = idRack;
    }

    public Long getIdPhieuThiCong() {
        return idPhieuThiCong;
    }

    public void setIdPhieuThiCong(Long idPhieuThiCong) {
        this.idPhieuThiCong = idPhieuThiCong;
    }

    public Long getIdRack() {
        return idRack;
    }

    public void setIdRack(Long idRack) {
        this.idRack = idRack;
    }
}
