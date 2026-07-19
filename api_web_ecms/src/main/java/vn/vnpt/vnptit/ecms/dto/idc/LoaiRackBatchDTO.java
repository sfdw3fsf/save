package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class LoaiRackBatchDTO implements Serializable {
    
    @Valid
    @Size(min = 1, message = "Danh sách loại rack không được rỗng")
    private List<LoaiRackDTO> danhSachLoaiRack;

    public List<LoaiRackDTO> getDanhSachLoaiRack() {
        return danhSachLoaiRack;
    }

    public void setDanhSachLoaiRack(List<LoaiRackDTO> danhSachLoaiRack) {
        this.danhSachLoaiRack = danhSachLoaiRack;
    }
}
