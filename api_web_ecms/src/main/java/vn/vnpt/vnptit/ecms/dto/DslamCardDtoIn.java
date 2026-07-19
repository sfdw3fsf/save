package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class DslamCardDtoIn {
    @Max(999999999999l)
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private long shelfDslId;
    @Max(9999999999l)
    @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaiCardId;
    @Max(999999999999l)
//    @Positive(message = ModelValidation.POSITIVE_OR_ZERO) // Vị trí card được = 0
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long viTri;

//    @NotEmpty(message = ModelValidation.NOT_NULL) lvhquan: tên card được null
    @Size(max = 200, message = "Giá trị tham số có không được lớn hơn 200" )
    private String tenCard;
    private String serial;
    private long phienTd;
    private long phienTb;
    private long doiDayTbTu;
    private long doiDayTdTu;
    private String partNumber;

    public static class DslamCardDtoUpd extends DslamCardDtoIn{
        @Max(999999999999999l)
        @Positive(message = ModelValidation.POSITIVE_OR_ZERO)
        @NotNull(message = ModelValidation.NOT_NULL)
        private Long cardDslId;

        public long getCardDslId() {
            return cardDslId;
        }

        public void setCardDslId(long cardDslId) {
            this.cardDslId = cardDslId;
        }


    }

    public long getShelfDslId() {
        return shelfDslId;
    }

    public void setShelfDslId(long shelfDslId) {
        this.shelfDslId = shelfDslId;
    }

    public long getLoaiCardId() {
        return loaiCardId;
    }

    public void setLoaiCardId(long loaiCardId) {
        this.loaiCardId = loaiCardId;
    }

    public long getViTri() {
        return viTri;
    }

    public void setViTri(long viTri) {
        this.viTri = viTri;
    }

    public String getTenCard() {
        return tenCard;
    }

    public void setTenCard(String tenCard) {
        this.tenCard = tenCard;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public long getPhienTd() {
        return phienTd;
    }

    public void setPhienTd(long phienTd) {
        this.phienTd = phienTd;
    }

    public long getPhienTb() {
        return phienTb;
    }

    public void setPhienTb(long phienTb) {
        this.phienTb = phienTb;
    }

    public long getDoiDayTbTu() {
        return doiDayTbTu;
    }

    public void setDoiDayTbTu(long doiDayTbTu) {
        this.doiDayTbTu = doiDayTbTu;
    }

    public long getDoiDayTdTu() {
        return doiDayTdTu;
    }

    public void setDoiDayTdTu(long doiDayTdTu) {
        this.doiDayTdTu = doiDayTdTu;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
}
