package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class XoaCapPhatThuHoiURequestDTO implements Serializable {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long idPhieuThiCong;

    @NotNull(message = ModelValidation.NOT_NULL)
    private String listOfIdUnit;

    public XoaCapPhatThuHoiURequestDTO() {
    }

    public XoaCapPhatThuHoiURequestDTO(Long idPhieuThiCong, String listOfIdUnit) {
        this.idPhieuThiCong = idPhieuThiCong;
        this.listOfIdUnit = listOfIdUnit;
    }

    public Long getIdPhieuThiCong() {
        return idPhieuThiCong;
    }

    public void setIdPhieuThiCong(Long idPhieuThiCong) {
        this.idPhieuThiCong = idPhieuThiCong;
    }

    public String getListOfIdUnit() {
        return listOfIdUnit;
    }

    public void setListOfIdUnit(String listOfIdUnit) {
        this.listOfIdUnit = listOfIdUnit;
    }
}
