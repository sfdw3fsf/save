package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NapDanhSachHoSoDtoIn {
    private String maThueBao;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer cboTths;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}", message="Không hợp lệ theo định dạng dd/MM/yyyy")
    private String tuNgay;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}", message="Không hợp lệ theo định dạng dd/MM/yyyy")
    private String denNgay;

    @NotNull (message = ModelValidation.NOT_NULL)
    private Integer tag;

    public String getMaThueBao() {
        return maThueBao;
    }

    public void setMaThueBao(String maThueBao) {
        this.maThueBao = maThueBao;
    }

    public Integer getCboTths() {
        return cboTths;
    }

    public void setCboTths(Integer cboTths) {
        this.cboTths = cboTths;
    }

    public String getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(String tuNgay) {
        this.tuNgay = tuNgay;
    }

    public String getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(String denNgay) {
        this.denNgay = denNgay;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
