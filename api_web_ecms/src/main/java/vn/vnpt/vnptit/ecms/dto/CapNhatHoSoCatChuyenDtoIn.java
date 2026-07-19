package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CapNhatHoSoCatChuyenDtoIn {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long hsccId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(min = 1, max = 200, message = "Giá trị tham số có không được lớn hơn 200")
    private String tenHoso;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}", message="Không hợp lệ theo định dạng dd/MM/yyyy")
    private String ngayBd;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}", message="Không hợp lệ theo định dạng dd/MM/yyyy")
    private String ngayKt;
    @Size(min = 0, max = 4000, message = "Giá trị tham số có không được lớn hơn 4000")
    private String ghiChu;

    public Long getHsccId() {
        return hsccId;
    }

    public void setHsccId(Long hsccId) {
        this.hsccId = hsccId;
    }

    public String getTenHoso() {
        return tenHoso;
    }

    public void setTenHoso(String tenHoso) {
        this.tenHoso = tenHoso;
    }

    public String getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(String ngayBd) {
        this.ngayBd = ngayBd;
    }

    public String getNgayKt() {
        return ngayKt;
    }

    public void setNgayKt(String ngayKt) {
        this.ngayKt = ngayKt;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
