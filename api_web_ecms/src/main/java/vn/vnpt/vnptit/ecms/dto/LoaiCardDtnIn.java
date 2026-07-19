package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.PositiveOrZero;

public class LoaiCardDtnIn {
    private String loaicard;
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer soPort;
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer portBd;
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long cardTbiId;
    private Long hangSxId;
    private Integer kieuModule;
    private String nhomCard;
    private String loaiTbi;
    private String hangSx;

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

    public String getNhomCard() {
        return nhomCard;
    }

    public void setNhomCard(String nhomCard) {
        this.nhomCard = nhomCard;
    }

    public String getLoaiTbi() {
        return loaiTbi;
    }

    public void setLoaiTbi(String loaiTbi) {
        this.loaiTbi = loaiTbi;
    }

    public String getHangSx() {
        return hangSx;
    }

    public void setHangSx(String hangSx) {
        this.hangSx = hangSx;
    }
}
