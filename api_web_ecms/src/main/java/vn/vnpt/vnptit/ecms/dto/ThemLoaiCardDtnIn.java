package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class ThemLoaiCardDtnIn {
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @Min(value = 1, message = "Giá trị nhỏ nhất là 1")
    private Long loaicardId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String loaicard;
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer soPort;
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer portBd;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long cardTbiId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long hangSxId;
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @Min(value = 0, message = "không hợp lệ, chỉ được phép truyền 0,1,2")
    @Max(value = 2, message = "không hợp lệ, chỉ được phép truyền 0,1,2")
    private Integer kieuModule;

    public Long getLoaicardId() {
        return loaicardId;
    }

    public void setLoaicardId(Long loaicardId) {
        this.loaicardId = loaicardId;
    }

    public String getLoaicard() {
        return loaicard;
    }

    public void setLoaicard(String loaicard) {
        this.loaicard = loaicard;
    }

    public Integer getSoPort() {
        return soPort;
    }

    public void setSoPort(Integer soPort) {
        this.soPort = soPort;
    }

    public Integer getPortBd() {
        return portBd;
    }

    public void setPortBd(Integer portBd) {
        this.portBd = portBd;
    }

    public Long getCardTbiId() {
        return cardTbiId;
    }

    public void setCardTbiId(Long cardTbiId) {
        this.cardTbiId = cardTbiId;
    }

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Integer getKieuModule() {
        return kieuModule;
    }

    public void setKieuModule(Integer kieuModule) {
        this.kieuModule = kieuModule;
    }
}
