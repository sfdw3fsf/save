package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;
import java.io.Serializable;

public class LoaiRackDTO implements Serializable {

    // ID - không bắt buộc cho insert, bắt buộc cho update
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long pId;

    // Tên loại Rack - Bắt buộc nhập, max 100 ký tự
    @NotEmpty(message = "Tên loại Rack không được để trống")
    @Size(max = 100, message = "Tên loại Rack không được vượt quá 100 ký tự")
    private String pTen;

    // Số U trên Rack - Bắt buộc nhập, Number
    @NotNull(message = "Số U trên Rack không được để trống")
    @Positive(message = "Số U trên Rack phải là số dương")
    private Long pSoU;

    @NotNull(message = "Kích thước chiều dài không được để trống")
    @Positive(message = "Kích thước chiều dài phải là số dương")
    private Double pDai;

    @NotNull(message = "Kích thước chiều rộng không được để trống")
    @Positive(message = "Kích thước chiều rộng phải là số dương")
    private Double pRong;

    @NotNull(message = "Kích thước chiều cao không được để trống")
    @Positive(message = "Kích thước chiều cao phải là số dương")
    private Double pCao;

    // Ghi chú - không bắt buộc, max 500 ký tự
    @Size(max = 500, message = "Ghi chú không được vượt quá 500 ký tự")
    private String pGhiChu;

    // Tên tắt - không bắt buộc, max 20 ký tự
    @Size(max = 20, message = "Tên tắt không được vượt quá 20 ký tự")
    private String pTenTat;

    // Mô tả - không bắt buộc, max 200 ký tự
    @Size(max = 200, message = "Mô tả không được vượt quá 200 ký tự")
    private String pMoTa;

    // Sử dụng - không bắt buộc, mặc định là 1 (đang sử dụng)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @Max(value = 1, message = "Sử dụng chỉ có thể là 0 hoặc 1")
    private Long pSuDung;
    @Size(max = 50, message = "Mã màu không được vượt quá 50 ký tự")
    private String pMaMau;
    public String getpMaMau() {
        return pMaMau;
    }
    public void setpMaMau(String pMaMau) {
        this.pMaMau = pMaMau;
    }
    public LoaiRackDTO() {
        // Mặc định sử dụng = 1 (đang sử dụng)
        this.pSuDung = 1L;
    }

    public LoaiRackDTO(Long pId, String pTen, String pTenTat, String pMoTa, Long pSoU, Double pDai, Double pRong, Double pCao, String pGhiChu, Long pSuDung) {
        this.pId = pId;
        this.pTen = pTen;
        this.pSoU = pSoU;
        this.pDai = pDai;
        this.pRong = pRong;
        this.pCao = pCao;
        this.pGhiChu = pGhiChu;
        this.pTenTat = pTenTat;
        this.pMoTa = pMoTa;
        this.pSuDung = pSuDung != null ? pSuDung : 1L;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpTen() {
        return pTen;
    }

    public void setpTen(String pTen) {
        this.pTen = pTen;
    }

    public Long getpSoU() {
        return pSoU;
    }

    public void setpSoU(Long pSoU) {
        this.pSoU = pSoU;
    }

    public Double getpDai() {
        return pDai;
    }

    public void setpDai(Double pDai) {
        this.pDai = pDai;
    }

    public Double getpRong() {
        return pRong;
    }

    public void setpRong(Double pRong) {
        this.pRong = pRong;
    }

    public Double getpCao() {
        return pCao;
    }

    public void setpCao(Double pCao) {
        this.pCao = pCao;
    }

    public String getpGhiChu() {
        return pGhiChu;
    }

    public void setpGhiChu(String pGhiChu) {
        this.pGhiChu = pGhiChu;
    }

        public String getpTenTat() {
        return pTenTat;
    }

    public void setpTenTat(String pTenTat) {
        this.pTenTat = pTenTat;
    }

    public String getpMoTa() {
        return pMoTa;
    }

    public void setpMoTa(String pMoTa) {
        this.pMoTa = pMoTa;
    }

    public Long getpSuDung() {
        return pSuDung;
    }

    public void setpSuDung(Long pSuDung) {
        this.pSuDung = pSuDung;
    }
}