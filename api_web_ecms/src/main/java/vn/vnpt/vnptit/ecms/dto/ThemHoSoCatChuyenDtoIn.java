package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class ThemHoSoCatChuyenDtoIn {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(min = 1, max = 200, message = "Giá trị tham số có không được lớn hơn 200")
    private String tenHoso;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaihsId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long kieuhsId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long ttccId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}", message="Không hợp lệ theo định dạng dd/MM/yyyy")
    private String ngayBd;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}", message="Không hợp lệ theo định dạng dd/MM/yyyy")
    private String ngayKt;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long ttvtId;
    @NotNull(message = ModelValidation.NOT_NULL)
    @Max(value = 1, message = ModelValidation.INVALID)
    @Min(value = 0, message = ModelValidation.INVALID)
    private Integer chanBaohong;
    @Size(min = 0, max = 4000, message = "Giá trị tham số có không được lớn hơn 4000")
    private String ghiChu;

    public String getTenHoso() {
        return tenHoso;
    }

    public void setTenHoso(String tenHoso) {
        this.tenHoso = tenHoso;
    }

    public Long getLoaihsId() {
        return loaihsId;
    }

    public void setLoaihsId(Long loaihsId) {
        this.loaihsId = loaihsId;
    }

    public Long getKieuhsId() {
        return kieuhsId;
    }

    public void setKieuhsId(Long kieuhsId) {
        this.kieuhsId = kieuhsId;
    }

    public Long getTtccId() {
        return ttccId;
    }

    public void setTtccId(Long ttccId) {
        this.ttccId = ttccId;
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

    public Long getTtvtId() {
        return ttvtId;
    }

    public void setTtvtId(Long ttvtId) {
        this.ttvtId = ttvtId;
    }

    public Integer getChanBaohong() {
        return chanBaohong;
    }

    public void setChanBaohong(Integer chanBaohong) {
        this.chanBaohong = chanBaohong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
