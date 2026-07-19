package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ThemLoaiDongBoDtoIn {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(min = 1, max = 20, message = "Giá trị tham số có không được lớn hơn 20")
    private String loaiDongbo;

    public String getLoaiDongbo() {
        return loaiDongbo;
    }

    public void setLoaiDongbo(String loaiDongbo) {
        this.loaiDongbo = loaiDongbo;
    }
}
