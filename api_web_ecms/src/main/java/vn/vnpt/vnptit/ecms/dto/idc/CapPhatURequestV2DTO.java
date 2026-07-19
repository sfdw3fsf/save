package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CapPhatURequestV2DTO implements Serializable {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idPhieuThiCong;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idRack;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String listOfIdUnit;

    public CapPhatURequestV2DTO() {
    }

    public CapPhatURequestV2DTO(Long idPhieuThiCong, Long idRack, String listOfIdUnit) {
        this.idPhieuThiCong = idPhieuThiCong;
        this.idRack = idRack;
        this.listOfIdUnit = listOfIdUnit;
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

    public String getListOfIdUnit() {
        return listOfIdUnit;
    }

    public void setListOfIdUnit(String listOfIdUnit) {
        this.listOfIdUnit = listOfIdUnit;
    }
}
