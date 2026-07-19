package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class ThanhNguonTrenRackBatchDto implements Serializable {
    
    @Valid
    @Size(min = 1, message = "Danh sách thanh nguồn không được rỗng")
    private List<ThanhNguonTrenRackDto> danhSachThanhNguon;

    public List<ThanhNguonTrenRackDto> getDanhSachThanhNguon() {
        return danhSachThanhNguon;
    }

    public void setDanhSachThanhNguon(List<ThanhNguonTrenRackDto> danhSachThanhNguon) {
        this.danhSachThanhNguon = danhSachThanhNguon;
    }
}
