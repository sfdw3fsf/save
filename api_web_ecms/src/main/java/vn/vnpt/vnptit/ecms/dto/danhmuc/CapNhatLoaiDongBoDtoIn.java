package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CapNhatLoaiDongBoDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaidbId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(min = 1, max = 20, message = "Giá trị tham số có không được lớn hơn 20")
    private String loaiDongbo;
    public Long getLoaidbId() {
        return loaidbId;
    }

    public void setLoaidbId(Long loaidbId) {
        this.loaidbId = loaidbId;
    }

    public String getLoaiDongbo() {
        return loaiDongbo;
    }

    public void setLoaiDongbo(String loaiDongbo) {
        this.loaiDongbo = loaiDongbo;
    }
}
